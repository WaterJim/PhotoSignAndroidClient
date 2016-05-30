package com.waterchen.android_photosignapp.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.adapter.OfflineAdapter;
import com.waterchen.android_photosignapp.extra.db.beans.OfflineBean;
import com.waterchen.android_photosignapp.extra.tool.StorageUtil;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.presenter.OfflinePresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.OfflineView;
import com.waterchen.android_photosignapp.weight.ProgressView;
import com.waterchen.android_photosignapp.weight.RecylerViewItemDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class OfflineActivity extends BaseActivity implements OfflineView, View.OnClickListener {


    private Toolbar mToolbar;
    private ProgressView mProgressView;
    private RecyclerView mRecylerView;
    private TextView mUploadTv;
    private TextView mEditTv;
    private TextView mSelectAllTv;
    private TextView mDeleteTv;
    private TextView mCancelTv;
    private ImageView mEmptyIv;

    /**
     * 编辑面板控件（全部选择，删除）
     */
    private View mEditContentView;
    /**
     * 基础面板控件（全部上传，编辑）
     */
    private View mBaseContentView;

    private boolean isEditing;

    private MaterialDialog mProgressDialog;
    private MaterialDialog mAlertDialog;

    private OfflineAdapter mAdapter;
    private OfflinePresenter mOfflinePresenter;

    private List<OfflineBean> mRecordList;
    private OfflineBean mSeletedRecord;
    private int mSeletedPosition = 0;

    private boolean isFirstResume = true;
    private String account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        initViews();
        initAlertDialog(this);
        mOfflinePresenter = new OfflinePresenter();
        mOfflinePresenter.attachView(this);
    }

    private void initViews() {
        mToolbar = findView(R.id.offline_toolbar);
        mProgressView = findView(R.id.offline_pv);
        mRecylerView = findView(R.id.offline_rv);
        mBaseContentView = findView(R.id.offline_base_ll);
        mEditContentView = findView(R.id.offline_edit_ll);

        mUploadTv = findView(R.id.offline_upload_tv);
        mEditTv = findView(R.id.offline_edit_tv);
        mSelectAllTv = findView(R.id.offline_selectall_tv);
        mDeleteTv = findView(R.id.offline_delete_tv);
        mCancelTv = findView(R.id.offline_cancel_tv);
        mEmptyIv = findView(R.id.offline_empty_iv);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new OfflineAdapter(this);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mRecylerView.addItemDecoration(new RecylerViewItemDivider(this, LinearLayoutManager.HORIZONTAL, 10, Color.parseColor("#CCCCCC")));
        mRecylerView.setAdapter(mAdapter);

        mUploadTv.setOnClickListener(this);
        mEditTv.setOnClickListener(this);
        mSelectAllTv.setOnClickListener(this);
        mDeleteTv.setOnClickListener(this);
        mCancelTv.setOnClickListener(this);

        mAdapter.setOnItemClickListener(new OfflineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mSeletedPosition = position;
                mSeletedRecord = mRecordList.get(position);
                if (mSeletedRecord != null) {
                    mAlertDialog.show();
                }
            }
        });

        isEditing = false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstResume) {
            //查找数据库，获取记录列表
            mOfflinePresenter.loadOfflineRecords("309613128");
//            Logger.e("total : " + StorageUtil.getRomTotalSize(this));
            mProgressView.setMaxProgress(StorageUtil.getRomTotalSize());
            mProgressView.setProgress(StorageUtil.getRomAvailableSize());
            isFirstResume = false;
        }
    }

    private void changeEditContentVisiblity() {
        isEditing = !isEditing;
        if (isEditing) {
            mEditContentView.setVisibility(View.VISIBLE);
            mEditContentView.setClickable(true);
            mBaseContentView.setVisibility(View.INVISIBLE);
            mBaseContentView.setClickable(false);
        } else {
            mEditContentView.setVisibility(View.INVISIBLE);
            mEditContentView.setClickable(false);
            mBaseContentView.setVisibility(View.VISIBLE);
            mBaseContentView.setClickable(true);
        }
    }


    public void initAlertDialog(Context context) {
        if (mAlertDialog == null) {
            mAlertDialog = new MaterialDialog.Builder(context)
                    .title(R.string.offline_progress_dialog_title)
                    .content(R.string.offline_alert_dialog_content)
                    .positiveText(R.string.offline_alert_dialog_positive)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mOfflinePresenter.recordFaceIdentify("309613128", mSeletedRecord.path, mSeletedRecord.classid);
                        }
                    })
                    .negativeText(R.string.offline_alert_dialog_negative)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .build();
        }

    }

    @Override
    public void showProgressDialog(int max) {
        mProgressDialog = new MaterialDialog.Builder(this)
                .title(R.string.offline_progress_dialog_title)
                .content(R.string.offline_progress_dialog_content)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, max, true)
                .canceledOnTouchOutside(false)
                .build();
        mProgressDialog.show();
    }

    public void dismissProgressDialog(boolean success) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            if (success) {
                ToastUtil.showToast(this, R.string.offline_progress_dialog_finish);
            } else {
                ToastUtil.showToast(this, "上传失败,请重新尝试");
            }

        }
    }

    @Override
    public void incrementProgress(int progress) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.incrementProgress(progress);
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.offline_upload_tv:
                Logger.e("upload");
                break;
            case R.id.offline_edit_tv:
                Logger.e("edit");
                changeEditContentVisiblity();
                break;
            case R.id.offline_selectall_tv:
                Logger.e("selectAll");
                break;
            case R.id.offline_delete_tv:
                Logger.e("delete");
                break;
            case R.id.offline_cancel_tv:
                Logger.e("cancel");
                changeEditContentVisiblity();
                break;
        }
    }

    @Override
    public void loadRecordListSuccess(List<OfflineBean> recordList) {
        mRecordList = recordList;
        if (recordList != null) {
            if (recordList.size() > 0) {
                mEmptyIv.setVisibility(View.GONE);
            }
            mAdapter.addRecord(recordList);
        }

    }

    @Override
    public void loadRecordListError() {
        mRecordList = new ArrayList<>();
        mAdapter.addRecord(mRecordList);
    }

    @Override
    public void uploadRecordSuccess() {
        dismissProgressDialog(true);
        mAdapter.removeRecord(mSeletedPosition);
        //修改记录上传状态
        mOfflinePresenter.updateOfflineRecord(mSeletedRecord.classid, mSeletedRecord.date);
    }

    @Override
    public void uploadRecordError() {
        dismissProgressDialog(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mOfflinePresenter.destroyPresenter();
        super.onDestroy();

    }
}
