package com.waterchen.android_photosignapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.tool.ScreenUtils;
import com.waterchen.android_photosignapp.extra.tool.StorageUtil;
import com.waterchen.android_photosignapp.presenter.PhotoPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.PhotoView;

import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/25.
 */
public class PhotoActivity extends BaseActivity implements PhotoView, View.OnClickListener {

    private SurfaceView mSurfaceView;
    private View mGetPhotoView;
    private TextView mTakeNumTv;
    private Toolbar mToolbar;
    private ImageView mTakePhotoIv;
    private Camera mCamera;

    private int takePictureNum = 0;

    private double percent = 1.0;

    private MaterialDialog mMaterialDialog;

    private PhotoPresenter mPhotoPresenter;

    /**
     * 记录返回键点击次数
     */
    private int backPresstimes = 0;
    private int classId;
    private int classStudentCount;
    private int existPicCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initViews();
        initAlertDialog();
        mPhotoPresenter = new PhotoPresenter();
        mPhotoPresenter.attachView(this);
    }

    private void initViews() {

        mToolbar = findView(R.id.photo_toolbar);
        mSurfaceView = findView(R.id.photo_view_sf);
        mTakeNumTv = findView(R.id.photo_num_tv);
        mGetPhotoView = findView(R.id.photo_getview_v);
        mTakePhotoIv = findView(R.id.photo_take_btn);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTakePhotoIv.setOnClickListener(this);
        mSurfaceView.setOnClickListener(this);

        SurfaceHolder holder = mSurfaceView.getHolder();
        if (holder != null) {
            // 画面分辨率
            holder.setFixedSize(480, 640);
            // 保持屏幕长亮
            holder.setKeepScreenOn(true);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        // 打开摄像头
                        mCamera = Camera.open();
                        // 摄像头参数
                        Camera.Parameters parameters = mCamera.getParameters();
                        // 将摄像头参数打印出来,各种机型的参数不一样,所以得根据具体机型来设置参数
                        Log.i("MainActivity", parameters.flatten());
                        // 设置预览图片大小
                        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
                        Camera.Size preSize = sizes.get(0);
                        for (int i = 0; i < sizes.size(); i++) {
                            Camera.Size size = sizes.get(i);
                            if (Math.abs(size.width - ScreenUtils.getScreenWidth(PhotoActivity.this))
                                    < Math.abs(preSize.width - ScreenUtils.getScreenWidth(PhotoActivity.this))) {
                                preSize = size;
                            }
                        }
                        parameters.setPreviewSize(preSize.width, preSize.height);

                        // 设置图片格式
                        parameters.setPictureFormat(ImageFormat.JPEG);
                        // 设置图片大小
                        List<Camera.Size> pSizes = parameters.getSupportedPictureSizes();
                        Camera.Size picSize = pSizes.get(0);
                        for (int i = 0; i < pSizes.size(); i++) {
                            Camera.Size size = pSizes.get(i);
                            if (Math.abs(size.width - ScreenUtils.getScreenWidth(PhotoActivity.this))
                                    < Math.abs(picSize.width - ScreenUtils.getScreenWidth(PhotoActivity.this))) {
                                picSize = size;
                            }
                        }
                        parameters.setPictureSize(picSize.width, picSize.height);
                        // 设置图片质量
                        parameters.setJpegQuality(80);
                        // 将参数设置给摄像头
                        parameters.setRotation(90);

                        mCamera.setParameters(parameters);
                        // 设置预览显示
                        mCamera.setDisplayOrientation(90);

                        mCamera.setPreviewDisplay(holder);
                        // 开始预览
                        mCamera.startPreview();
                    } catch (Exception exception) {
                        Logger.e(exception.getMessage());
                        // 释放摄像头
                        if (mCamera != null) {
                            mCamera.release();
                            mCamera = null;
                        }
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    // 释放摄像头
                    if (mCamera != null) {
                        mCamera.release();
                        mCamera = null;
                    }
                }
            });

        }
        //这里调整了取像框的大小
        RelativeLayout.LayoutParams strokeParams = (RelativeLayout.LayoutParams) mGetPhotoView.getLayoutParams();
        int size = Math.min(strokeParams.width, strokeParams.height);
        strokeParams.width = size;
        strokeParams.height = size;
        mGetPhotoView.setLayoutParams(strokeParams);

        updateTakePicNum();

    }

    private void updateTakePicNum() {
        mTakeNumTv.post(new Runnable() {
            @Override
            public void run() {
                mTakeNumTv.setText("当前已拍摄 " + takePictureNum + " 张");
            }
        });
    }

    private void initAlertDialog() {

        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog.Builder(this)
                    .title(R.string.photo_dialog_title)
                    .content(R.string.photo_dialog_content)
                    .positiveText(R.string.photo_dialog_positive)
                    .negativeText(R.string.photo_dialog_negative)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(PhotoActivity.this, LessonInfoActivity.class);
                            intent.putExtra("intent_flag", 0);
                            intent.putExtra("photo_count", takePictureNum);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mMaterialDialog.dismiss();
                        }
                    })
                    .build();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化课程信息
        Bundle classInfo = getIntent().getBundleExtra("classinfo");
        classId = classInfo.getInt("classid", 0);
        classStudentCount = classInfo.getInt("student_count", 0);
        existPicCount = classInfo.getInt("existcount", 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closeActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 关闭当前信息，同时回传信息
     */
    private void closeActivity() {
        backPresstimes++;
        if (backPresstimes == 1) {
            mHandler.sendEmptyMessageDelayed(0, 2000);
            Toast.makeText(this, "再次点击返回键退出", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * 判断当前拍摄数与班级人数的数量
             * 当拍摄数少于班级人数，弹框提示
             */
            backPresstimes = 0;
            if (takePictureNum < classStudentCount) {
                mMaterialDialog.show();
            } else {
                Intent intent = new Intent(this, LessonInfoActivity.class);
                intent.putExtra("intent_flag", 0);
                intent.putExtra("photo_count", takePictureNum + existPicCount);
                startActivity(intent);
                finish();
            }
        }

    }

    /**
     * 控制返回键
     */
    @Override
    public void onBackPressed() {
        closeActivity();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            backPresstimes = 0;
        }
    };

    private double getPercent() {
        if (percent == 1.0) {
            percent = 1.0 * mGetPhotoView.getHeight() * mGetPhotoView.getWidth()
                    / mSurfaceView.getHeight() / mSurfaceView.getWidth();
        }
        return percent;
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.photo_view_sf) {
            if (mCamera != null) {
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {

                    }
                });
            }

        } else if (viewId == R.id.photo_take_btn) {
            StorageUtil.savePicture(PhotoActivity.this, mCamera, getPercent(), "" + classId,
                    takePictureNum + existPicCount, new StorageUtil.OnSaveCallback() {
                        @Override
                        public void onSaved(boolean isSucceed) {
                            if (isSucceed) {
                                Toast.makeText(PhotoActivity.this, "拍摄成功", Toast.LENGTH_SHORT).show();
                                takePictureNum++;
                                updateTakePicNum();
                            }
                        }
                    });
        }
    }


}
