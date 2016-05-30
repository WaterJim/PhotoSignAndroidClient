package com.waterchen.android_photosignapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.presenter.LessonCreatePresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.LessonCreateView;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by 橘子哥 on 2016/5/24.
 */
public class LessonCreateActivity extends BaseActivity implements LessonCreateView, View.OnClickListener {

    private Toolbar mToolbar;
//    private TextInputLayout mNameWrapper;
//    private TextInputLayout mWeekWrapper;

    private View mNameContent;
    private View mWeekContent;
    private TextView mNameTv;
    private TextView mWeekTv;

    private Button mCleanBtn;
    private Button mCreateBtn;

    private boolean createFlag = false;
    private String account;
    private String selectWeek;
    private boolean isSelectWeek = false;

    private LessonCreatePresenter mLessonPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlesson);
        initViews();
        mLessonPresenter = new LessonCreatePresenter();
        mLessonPresenter.attachView(this);
    }

    private void initViews() {
        mToolbar = findView(R.id.createlesson_toolbar);
        mNameContent = findView(R.id.createlesson_name_content_rl);
        mWeekContent = findView(R.id.createlesson_week_content_rl);
        mNameTv = findView(R.id.createlesson_name_tv);
        mWeekTv = findView(R.id.createlesson_week_tv);

        mCleanBtn = findView(R.id.createlesson_clean_btn);
        mCreateBtn = findView(R.id.createlesson_create_btn);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCleanBtn.setOnClickListener(this);
        mCreateBtn.setOnClickListener(this);
        mNameContent.setOnClickListener(this);
        mWeekContent.setOnClickListener(this);
    }

    @Override
    public void createLessonSuccess() {
        ToastUtil.showToast(this, "新增课程成功");
        mNameTv.setText(R.string.createlesson_lesson_name_default);
        mWeekTv.setText(R.string.createlesson_lesson_week_default);
        createFlag = true;
    }

    @Override
    public void createLessonError() {
        ToastUtil.showToast(this, "新增课程失败，请稍后再试");
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        account = new AppPreferences(this).getString(
                ConstantValues.PREFERENCE_KEY_USER_ACCOUNT,
                ConstantValues.USER_ACCOUNT_NULL
        );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createlesson_name_content_rl) {
            showEditNameDialog();
        } else if (v.getId() == R.id.createlesson_week_content_rl) {
            showEditWeekDialog();
        } else if (v.getId() == R.id.createlesson_clean_btn) {
            mNameTv.setText(R.string.createlesson_lesson_name_default);
            mWeekTv.setText(R.string.createlesson_lesson_week_default);
        } else if (v.getId() == R.id.createlesson_create_btn) {
            mLessonPresenter.createLesson(account,
                    mNameTv.getText().toString(),
                    selectWeek);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("main_intent", 0x004);
            intent.putExtra("create_flag", createFlag);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("main_intent", 0x004);
        intent.putExtra("create_flag", createFlag);
        startActivity(intent);
        finish();
    }

    private void showEditNameDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.createlesson_dialog_name_title)
                .titleColorRes(R.color.colorAccent)
                .content(R.string.createlesson_dialog_name_content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 10)
                .positiveText(R.string.person_dialog_submit)
                .input(R.string.createlesson_lesson_name_default,
                        R.string.createlesson_lesson_name_default,
                        false,
                        new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                mNameTv.setText(input);
                            }
                        }).show();
    }

    private void showEditWeekDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.createlesson_dialog_week_title)
                .titleColorRes(R.color.colorAccent)
                .items(R.array.week_array)
                .itemsCallbackMultiChoice(getSelectedWeeks(), new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder str = new StringBuilder();
                        if (which.length > 0) {
                            isSelectWeek = true;
                        }
                        for (int i = 0; i < which.length; i++) {
                            str.append(which[i]);
                            if (i < which.length - 1) {
                                str.append(",");
                            }
                        }
                        selectWeek = str.toString();
                        Logger.e(selectWeek);
                        return true; // allow selection
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Logger.e("clear");
                        dialog.clearSelectedIndices();
                        selectWeek = null;
                        isSelectWeek = false;
                        mWeekTv.setText(R.string.createlesson_lesson_week_default);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //检验是否选择最少一个
                        if (!isSelectWeek) {
                            ToastUtil.showToast(LessonCreateActivity.this, "最少选择一个周数");
                        } else {
                            dialog.dismiss();
                            mWeekTv.setText(R.string.createlesson_lesson_week_select);
                        }
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.createlesson_dialog_week_submit)
                .autoDismiss(false)
                .neutralText(R.string.createlesson_dialog_week_clear)
                .show();
    }

    private Integer[] getSelectedWeeks() {
        if (!isSelectWeek) {
            return new Integer[]{};
        } else {
            String[] weeks = selectWeek.split(",");
            Integer[] weekArray = new Integer[weeks.length];
            for (int i = 0; i < weeks.length; i++) {
                weekArray[i] = Integer.parseInt(weeks[i]);
            }
            return weekArray;
        }
    }

}
