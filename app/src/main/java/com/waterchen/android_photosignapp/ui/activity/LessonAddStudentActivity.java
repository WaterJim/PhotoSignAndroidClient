package com.waterchen.android_photosignapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.presenter.LessonAddStuPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.LessonAddStudentView;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class LessonAddStudentActivity extends BaseActivity implements LessonAddStudentView, View.OnClickListener {

    private final String STRING_NULL_VALUE = "NULL";
    private final int INT_NULL_VALUE = 0;

    private Toolbar mToolbar;
    private TextInputLayout mStuAccountWrapper;
    private Button mAddBtn;

    private int classId;
    private String account;

    private LessonAddStuPresenter mPresenter;

    private boolean isNewStudent = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessonaddstu);
        initViews();
        mPresenter = new LessonAddStuPresenter();
        mPresenter.attachView(this);
    }

    private void initViews() {
        mToolbar = findView(R.id.addstu_toolbar);
        mStuAccountWrapper = findView(R.id.addstu_account_wrapper_til);
        mAddBtn = findView(R.id.addstu_add_btn);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        classId = getIntent().getIntExtra("classid", INT_NULL_VALUE);
        account = new AppPreferences(this).getString(ConstantValues.PREFERENCE_KEY_USER_ACCOUNT,
                ConstantValues.USER_ACCOUNT_NULL);
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        if (!checkInputText(mStuAccountWrapper.getEditText().getText().toString())) {
            mStuAccountWrapper.setError("学号格式不正确");
        } else {
            mStuAccountWrapper.setErrorEnabled(false);
            mPresenter.addStudent(account,
                    mStuAccountWrapper.getEditText().getText().toString(),
                    classId);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, LessonInfoActivity.class)
                    .putExtra("intent_flag", 1)
                    .putExtra("new_student", isNewStudent));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean checkInputText(String input) {
        if (input == null || input.length() < 10) {
            return false;
        }
        return true;
    }

    @Override
    public void addStudentSuccess() {
        mStuAccountWrapper.getEditText().setText("");
        ToastUtil.showToast(this, R.string.addstu_success);
        isNewStudent = true;
    }

    @Override
    public void addStudentError() {
        ToastUtil.showToast(this, R.string.addstu_error);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, LessonInfoActivity.class)
                .putExtra("intent_flag", 1)
                .putExtra("new_student", isNewStudent));
        finish();
    }
}
