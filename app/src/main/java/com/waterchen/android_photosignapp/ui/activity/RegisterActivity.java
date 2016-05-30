package com.waterchen.android_photosignapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.presenter.RegisterPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.RegisterView;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {

    private Toolbar mToolbar;
    private Button mCleanButton;
    private Button mRegisterButton;
    private TextInputLayout mAccountWrapper;
    private TextInputLayout mPasswordWrapper;
    private TextInputLayout mPhoneWrapper;
    private TextInputLayout mEmailWrapper;
    private MaterialDialog mSuccessDialog;

    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initSuccessDialog();
        mRegisterPresenter = new RegisterPresenter();
        mRegisterPresenter.attachView(this);
    }

    private void initViews() {

        mToolbar = findView(R.id.register_toolbar);
        mCleanButton = findView(R.id.register_clean_btn);
        mRegisterButton = findView(R.id.register_register_btn);
        mAccountWrapper = findView(R.id.register_account_wrapper_til);
        mPasswordWrapper = findView(R.id.register_password_wrapper_til);
        mEmailWrapper = findView(R.id.register_email_wrapper_til);
        mPhoneWrapper = findView(R.id.register_phone_wrapper_til);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegisterButton.setOnClickListener(this);
        mCleanButton.setOnClickListener(this);
    }


    private void initSuccessDialog() {
        if (mSuccessDialog == null) {
            mSuccessDialog = new MaterialDialog.Builder(this)
                    .title(R.string.register_dialog_title)
                    .content(R.string.register_dialog_content)
                    .positiveText(R.string.register_dialog_positive)
                    .negativeText(R.string.register_dialog_negative)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            mAccountWrapper.getEditText().clearComposingText();
                            mPasswordWrapper.getEditText().clearComposingText();
                            mEmailWrapper.getEditText().clearComposingText();
                            mPhoneWrapper.getEditText().clearComposingText();

                        }
                    })
                    .build();
        }
    }


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkInputText(String input) {
        if (input == null || input.length() < 6) {
            return false;
        }
        return true;
    }


    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void registerSuccess() {
        mSuccessDialog.show();
    }

    @Override
    public void registerError() {
        ToastUtil.showToast(this, "注册失败，请稍后重试");
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        if (v.getId() == R.id.register_clean_btn) {
            mAccountWrapper.getEditText().setText("");
            mPasswordWrapper.getEditText().setText("");
            mEmailWrapper.getEditText().setText("");
            mPhoneWrapper.getEditText().setText("");
        } else if (v.getId() == R.id.register_register_btn) {
            if (!checkInputText(mAccountWrapper.getEditText().getText().toString())) {
                mAccountWrapper.setError("账号不能为空或少于6个字符");
            } else if (!checkInputText(mPasswordWrapper.getEditText().getText().toString())) {
                mPhoneWrapper.setError("密码不能为空或少于6个字符");
            } else if (!checkInputText(mEmailWrapper.getEditText().getText().toString())) {
                mPhoneWrapper.setError("手机号码不能为空或少于11位");
            } else if (!checkInputText(mPhoneWrapper.getEditText().getText().toString())) {
                mEmailWrapper.setError("邮箱格式不正确");
            } else {
                mAccountWrapper.setErrorEnabled(false);
                mPasswordWrapper.setErrorEnabled(false);
                mEmailWrapper.setErrorEnabled(false);
                mPhoneWrapper.setErrorEnabled(false);
                mRegisterPresenter.register(mAccountWrapper.getEditText().getText().toString(),
                        mPasswordWrapper.getEditText().getText().toString(),
                        mEmailWrapper.getEditText().getText().toString(),
                        mPhoneWrapper.getEditText().getText().toString());
            }
        }
    }
}
