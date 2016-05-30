package com.waterchen.android_photosignapp.presenter;

import android.graphics.BitmapFactory;
import android.util.SparseArray;

import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.extra.db.beans.OfflineBean;
import com.waterchen.android_photosignapp.extra.mvp.BasePresenter;
import com.waterchen.android_photosignapp.extra.tool.StorageUtil;
import com.waterchen.android_photosignapp.extra.youtu.Youtu;
import com.waterchen.android_photosignapp.model.RecordModel;
import com.waterchen.android_photosignapp.model.impl.RecordModelImpl;
import com.waterchen.android_photosignapp.presenter.listener.OnLoadRecordsListener;
import com.waterchen.android_photosignapp.presenter.listener.OnRecordUploadListener;
import com.waterchen.android_photosignapp.ui.view.OfflineView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class OfflinePresenter extends BasePresenter<OfflineView> implements OnRecordUploadListener, OnLoadRecordsListener {

    private RecordModel mRecordModel;
    private SparseArray<List<String>> mContainer;

    public OfflinePresenter() {
        mRecordModel = new RecordModelImpl();
        mContainer = new SparseArray<>();
    }


    public void loadOfflineRecords(String token) {
        mRecordModel.loadRecords(token, this);
    }

    /**
     * 对记录进行服务器验证，并记录相关结果
     * 调用方法：FaceIdentify
     * 给定一张待识别的人脸图片和一个已有的Group，在该Group中的所有Person个体中，
     * 识别出最相似的Top5 Person作为其身份返回，返回的Top5中按照相似度从大到小排列。
     * 过程：获取指定路径下的照片的数量，并一张张提交到优图服务器，记录结果，并最后
     * 提交到服务器
     */
    public void recordFaceIdentify(final String token, final String path, final int classid) {
        File photoFiles = new File(StorageUtil.getRootFilePath(classid));
        if (photoFiles.exists() && photoFiles.isDirectory()) {
            //获取其中的所有照片
            File[] photos = photoFiles.listFiles();
            Logger.e("record photo count : " + photos.length);
            if (photos.length > 0) {
                final Youtu faceYoutu = new Youtu("1007073", "AKIDVbxj7kOfOvv7vurfqgZnZQrAyB1ct8zZ",
                        "JVboPsk7iQgxl7pKEKXC5qBs8lMLptbV", Youtu.API_YOUTU_END_POINT);
                getMvpView().showProgressDialog(photos.length);
                mContainer.put(classid, new ArrayList<String>());
                Observable.from(photos)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(Schedulers.io())
                        .map(new Func1<File, JSONObject>() {
                            @Override
                            public JSONObject call(File file) {
                                JSONObject resultJSON = null;
                                try {
                                    resultJSON = faceYoutu.FaceIdentify(BitmapFactory.decodeFile(file.getPath()), classid + "");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (KeyManagementException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                }
                                return resultJSON;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JSONObject>() {
                            @Override
                            public void onCompleted() {
                                uploadOfflineRecord(token, classid);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Logger.e(e.getMessage());
                            }

                            @Override
                            public void onNext(JSONObject jsonObject) {
                                String matchStudent = handleJsonResult(jsonObject);
                                getMvpView().incrementProgress(1);
                                if (matchStudent != null) {
                                    mContainer.get(classid).add(matchStudent);
                                }
                            }
                        });
            }
        }
    }

    /**
     * 上传离线记录
     */
    public void uploadOfflineRecord(String token, int classId) {
        //去重，拼接字符串
        StringBuilder sb = new StringBuilder();
        List<String> stuList = new ArrayList<>(new HashSet<>(mContainer.get(classId)));
        int count = stuList.size();
        for (int index = 0; index < count; index++) {
            sb.append(stuList.get(index));
            if (index < (count - 1)) {
                sb.append(",");
            }
        }
        String studentString = sb.toString();

        //上传至服务器
        mRecordModel.uploadRecord(token, classId, studentString, this);
    }

    public void updateOfflineRecord(int classId, String date) {
        mRecordModel.updateRecord(classId, date);
    }


    /**
     * 处理返回结果
     *
     * @param result
     * @return 若请求错误或不存在匹配的个体，返回空
     * 否则返回个体ID
     */

    private String handleJsonResult(JSONObject result) {
        try {
            //判断返回返回码
            if (result.getInt("errorcode") == 0) {
                JSONArray array = result.getJSONArray("candidates");
                if (array.length() > 0) {
                    JSONObject student = array.getJSONObject(0);
                    if (student.getDouble("confidence") > 30) {
                        return student.getString("person_id");
                    }
                }
            }
        } catch (JSONException e) {
            Logger.e(e.getMessage());
        }

        return null;
    }


    @Override
    public void loadRecordSuccess(List<OfflineBean> records) {
        getMvpView().loadRecordListSuccess(records);
    }

    @Override
    public void loadRecordError() {
        getMvpView().loadRecordListError();
    }

    @Override
    public void uploadRecordSuccesss() {
        getMvpView().uploadRecordSuccess();
    }

    @Override
    public void uploadRecordError() {
        getMvpView().uploadRecordError();
    }

    public void destroyPresenter() {
        mRecordModel.releaseModel();
    }
}
