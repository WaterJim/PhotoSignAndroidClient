package com.waterchen.android_photosignapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.model.entity.UserEnetity;
import com.waterchen.android_photosignapp.presenter.LoginPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.LoginView;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {


    private Toolbar mToolbar;
    private TextInputLayout mAccountWrapper;
    private TextInputLayout mPasswordWrapper;
    private AutoCompleteTextView mAccountEt;
    private EditText mPasswordEt;
    private Button mLoginBtn;
    private Button mRegisterBtn;

    private LoginPresenter mLoginPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);
    }

    private void initViews() {
        mToolbar = findView(R.id.login_toolbar);
        mAccountEt = findView(R.id.login_account_et);
        mPasswordEt = findView(R.id.login_password_et);
        mAccountWrapper = findView(R.id.login_account_wrapper_til);
        mPasswordWrapper = findView(R.id.login_password_wrapper_til);
        mLoginBtn = findView(R.id.login_login_btn);
        mRegisterBtn = findView(R.id.login_register_btn);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAccountWrapper.setHint("你的账号/手机号/邮箱");
        mPasswordWrapper.setHint("请输入密码");


        mAccountEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mAccountEt.getText().length() <= 0) {
                    if (hasFocus) {
                        mAccountWrapper.setHint("用户名");
                    } else {
                        mAccountWrapper.setHint("你的手机号/邮箱");
                    }
                }
            }
        });


        mPasswordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mPasswordEt.getText().length() <= 0) {
                    if (hasFocus) {
                        mPasswordWrapper.setHint("密码");
                    } else {
                        mPasswordWrapper.setHint("请输入密码");
                    }
                }
            }
        });

        mLoginBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "忘记密码");
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("main_intent", 0x001);
            intent.putExtra("login_flag", false);
            startActivity(intent);
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

    @Override
    public void onFailure(Throwable e) {

    }


    @Override
    public void onClick(View v) {
        hideKeyboard();
        if (v.getId() == R.id.login_login_btn) {
            final String account = mAccountWrapper.getEditText().getText().toString();
            final String password = mPasswordWrapper.getEditText().getText().toString();
            if (account == null || account.length() < 6) {
                mAccountWrapper.setError("账号不能为空或者少于6位字符");
            } else if (password == null || password.length() < 6) {
                mPasswordWrapper.setError("密码不能为空或者少于6位字符");
            } else {
                mAccountWrapper.setErrorEnabled(false);
                mPasswordWrapper.setErrorEnabled(false);
                mLoginPresenter.login(mAccountWrapper.getEditText().getText().toString(),
                        mPasswordWrapper.getEditText().getText().toString());
            }
        } else if (v.getId() == R.id.login_register_btn) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }


    @Override
    public void loginSuccess(UserEnetity.User userInfo) {
        userInfo.password = mPasswordWrapper.getEditText().getText().toString();
        mLoginPresenter.saveUserInfo(this, userInfo);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("main_intent", 0x001);
        intent.putExtra("login_flag", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError() {
        ToastUtil.showToast(this, "账号或密码不正确，请确认后重试");
    }
}
