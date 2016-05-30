package com.waterchen.android_photosignapp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.adapter.LessonStudentAdapter;
import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.tool.StorageUtil;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.model.entity.StudentEntity;
import com.waterchen.android_photosignapp.presenter.LessonInfoPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.LessonInfoView;
import com.waterchen.android_photosignapp.weight.RecylerViewItemDivider;

import net.grandcentrix.tray.AppPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class LessonInfoActivity extends BaseActivity implements LessonInfoView, View.OnClickListener {

    private final int INTENT_FLAG_NULL = -1;
    private final int INTENT_FLAG_PHOTO = 0;
    private final int INTENT_FLAG_NEW_STUDENT = 1;

    private final String STRING_NULL_VALUE = "NULL";
    private final int INT_NULL_VALUE = 0;

    private final int MENU_SHOWDATA = 0;
    private final int MENU_ADDSTU = 1;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mTakePhotoButton;
    private TextView mNameTv;
    private TextView mWeekTv;
    private TextView mCreateTv;
    private TextView mCountTv;
    private TextView mIdTv;

    private LessonStudentAdapter mAdapter;


    private String className;   //课程名
    private String classCreateTime; //创建时间
    private String classLastEditTime;   //最后编辑时间
    private String classLastUploadTime; //最后提交时间
    private int classUploadTimes;    //提交次数
    private String classWeek;   //上课周数
    private int classId;    //课程id
    private int classCount;// 改班级人数
    private boolean isFirstResume = true;
    private boolean isLoadStudent = true;
    private String account;

    private LessonInfoPresenter mLessonInfoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessoninfo);
        initViews();
        mLessonInfoPresenter = new LessonInfoPresenter();
        mLessonInfoPresenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstResume) {
            Bundle bundle = getIntent().getBundleExtra("classinfo");
            className = bundle.getString("class_name", STRING_NULL_VALUE);
            classCreateTime = bundle.getString("class_createtime", STRING_NULL_VALUE);
            classLastEditTime = bundle.getString("class_lastedittime", STRING_NULL_VALUE);
            classLastUploadTime = bundle.getString("class_lastuploadtime", STRING_NULL_VALUE);
            classUploadTimes = bundle.getInt("class_uploadtimes", INT_NULL_VALUE);
            classWeek = bundle.getString("class_week", STRING_NULL_VALUE);
            classId = bundle.getInt("class_classid", INT_NULL_VALUE);
            classCount = bundle.getInt("class_count", INT_NULL_VALUE);
            initClassInfo(className, classWeek, classCreateTime, classCount, classId);

            account = new AppPreferences(this).getString(ConstantValues.PREFERENCE_KEY_USER_ACCOUNT,
                    ConstantValues.USER_ACCOUNT_NULL);
            if (isLoadStudent) {
                mLessonInfoPresenter.loadStudentList(account, classId);
                isLoadStudent = false;
            }
            isFirstResume = false;
        }
    }

    private void initClassInfo(String name, String week, String create, int count, int id) {
        mNameTv.setText(name);
        mWeekTv.setText("每周" + week);
        mCreateTv.setText("创建日期:" + create);
        mCountTv.setText(count + "人");
        mIdTv.setText("ID:" + id);

    }


    private void initViews() {

        mToolbar = findView(R.id.classinfo_toolbar);
        mRecyclerView = findView(R.id.classinfo_students_rv);
        mTakePhotoButton = findView(R.id.classinfo_fab);
        mNameTv = findView(R.id.classinfo_name_tv);
        mWeekTv = findView(R.id.classinfo_week_tv);
        mCreateTv = findView(R.id.classinfo_create_tv);
        mCountTv = findView(R.id.classinfo_count_tv);
        mIdTv = findView(R.id.classinfo_id_tv);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new LessonStudentAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecylerViewItemDivider(this, LinearLayoutManager.HORIZONTAL, 10, Color.parseColor("#CCCCCC")));
        mRecyclerView.setAdapter(mAdapter);

        mTakePhotoButton.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        } else if (item.getItemId() == MENU_SHOWDATA) {
            //显示该班级的签到数据
            startActivity(new Intent(this, LessonRecordActivity.class));
        } else if (item.getItemId() == MENU_ADDSTU) {
            //显示添加学生页面
            startActivity(new Intent(this, LessonAddStudentActivity.class).putExtra("classid", classId));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SHOWDATA, 0, getString(R.string.classinfo_check_data));
        menu.add(0, MENU_ADDSTU, 0, "添加学生");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intentFlag = intent.getIntExtra("intent_flag", INTENT_FLAG_NULL);
        if (intentFlag == INTENT_FLAG_PHOTO) {
            //保存到数据库中
            final int photoCount = intent.getIntExtra("photo_count", 0);
            if (photoCount > 0) {
                mLessonInfoPresenter.saveRecord(account,
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        StorageUtil.getRootFilePath(classId),
                        classId,
                        className,
                        photoCount);
            }
        } else if (intentFlag == INTENT_FLAG_NEW_STUDENT) {
            if (intent.getBooleanExtra("new_student", false)) {
                isFirstResume = true;
                isLoadStudent = true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        //检查数据库中是否存在相应记录
        mLessonInfoPresenter.checkExistRecordAndUpload(classId,
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    @Override
    public void loadStudentsSuccesss(List<StudentEntity.LessonStudent> students) {
        if (students != null) {
            mAdapter.addStudentData(students);
        }
    }

    @Override
    public void loadStudentsError() {

    }

    @Override
    public void ckeckRecordExist(boolean existAndUpload, int count) {
        if (!existAndUpload) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("student_count", classCount);
            bundle.putInt("classid", classId);
            bundle.putInt("existcount", count);
            intent.putExtra("classinfo", bundle);
            intent.setClass(this, PhotoActivity.class);
            startActivity(intent);
        } else {
            ToastUtil.showToast(this, "该课程今天已经提交过签到数据");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLessonInfoPresenter.destroyPresenter();
    }
}
