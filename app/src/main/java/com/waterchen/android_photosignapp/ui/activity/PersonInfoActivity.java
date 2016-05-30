package com.waterchen.android_photosignapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;
import com.waterchen.android_photosignapp.presenter.PersonInfoPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.PersonInfoView;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by 橘子哥 on 2016/5/26.
 */
public class PersonInfoActivity extends BaseActivity implements PersonInfoView, View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView mNameArrowIv;
    private ImageView mTagArrowIv;
    private TextView mNameTv;
    private TextView mTagTv;
    private TextView mEmailTv;
    private TextView mPhoneTv;
    private View mNameContent;
    private View mTagContent;
    private boolean isEdit = true;
    private String preName;
    private String preTag;

    private PersonInfoPresenter mPersonPresenter;
    private String mAccount;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);
        initViews();
        mPersonPresenter = new PersonInfoPresenter();
        mPersonPresenter.attachView(this);

    }

    private void initViews() {
        mToolbar = findView(R.id.person_toolbar);
        mNameArrowIv = findView(R.id.person_name_iv);
        mTagArrowIv = findView(R.id.person_tag_iv);
        mNameTv = findView(R.id.person_name_tv);
        mTagTv = findView(R.id.person_tag_tv);
        mEmailTv = findView(R.id.person_email_tv);
        mPhoneTv = findView(R.id.person_phone_tv);
        mNameContent = findView(R.id.person_name_content_rl);
        mTagContent = findView(R.id.person_tag_content_rl);

        mNameContent.setOnClickListener(this);
        mTagContent.setOnClickListener(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化页面
        AppPreferences appPreferences = new AppPreferences(this);
        if (appPreferences.getBoolean(ConstantValues.PREFERENCE_KEY_LOGIN_FLAG, ConstantValues.LOGIN_FLAG_DEFAULT)) {
            mNameTv.setText(appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_NAME, ConstantValues.USER_NAME_NULL));
            mTagTv.setText(appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_TAG, ConstantValues.USER_TAG_NULL));
            mEmailTv.setText(appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_EMAIL, ConstantValues.USER_EMAIL_NULL));
            mPhoneTv.setText(appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_PHONE, ConstantValues.USER_PHONE_NULL));
        }
        mAccount = appPreferences.getString(ConstantValues.PREFERENCE_KEY_USER_ACCOUNT, ConstantValues.USER_ACCOUNT_NULL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "修改");
        menu.getItem(0).setIcon(R.drawable.ic_edit_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            if (isEdit) {
                preName = mNameTv.getText().toString();
                preTag = mTagTv.getText().toString();
                item.setIcon(R.drawable.ic_done_white_24dp);
                mNameArrowIv.setVisibility(View.VISIBLE);
                mTagArrowIv.setVisibility(View.VISIBLE);
            } else {
                item.setIcon(R.drawable.ic_edit_white_24dp);
                mNameArrowIv.setVisibility(View.GONE);
                mTagArrowIv.setVisibility(View.GONE);
                //上传修改信息
                mPersonPresenter.updateUserInfo(mAccount,
                        mNameTv.getText().toString(),
                        mTagTv.getText().toString());
            }
            isEdit = !isEdit;
        } else if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class)
                    .putExtra("main_intent", 0x002)
                    .putExtra("update_flag", isUpdate));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (!isEdit) {
            if (v.getId() == R.id.person_name_content_rl) {
                //显示输入名字对话框
                showEditNameDialog();
            } else if (v.getId() == R.id.person_tag_content_rl) {
                //显示输入Tag对话框
                showEditTagDialog();
            }
        }
    }

    @Override
    public void updateSuccess(UserEnetity.User userInfo) {
        ToastUtil.showToast(this, R.string.person_update_success);
        isUpdate = true;
        mPersonPresenter.saveUserInfo(this, userInfo);
    }

    @Override
    public void updateError() {
        ToastUtil.showToast(this, R.string.person_update_error);
        //转变回之前的数据
        mNameTv.setText(preName);
        mTagTv.setText(preTag);
    }

    @Override
    public void onFailure(Throwable e) {

    }

    private void showEditNameDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.person_dialog_name_title)
                .content(R.string.person_dialog_name_content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 10)
                .positiveText(R.string.person_dialog_submit)
                .input(preName, preName, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        mNameTv.setText(input);
                    }
                }).show();
    }

    private void showEditTagDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.person_dialog_tag_title)
                .content(R.string.person_dialog_tag_content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 10)
                .positiveText(R.string.person_dialog_submit)
                .input(preTag, preTag, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        mTagTv.setText(input);
                    }
                }).show();
    }

}
