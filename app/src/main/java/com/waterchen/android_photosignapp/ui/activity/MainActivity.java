package com.waterchen.android_photosignapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.adapter.MainLessonAdapter;
import com.waterchen.android_photosignapp.extra.ConstantValues;
import com.waterchen.android_photosignapp.extra.db.beans.UserBean;
import com.waterchen.android_photosignapp.extra.tool.ToastUtil;
import com.waterchen.android_photosignapp.model.entity.LessonInfoEntity;
import com.waterchen.android_photosignapp.presenter.MainPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.MainView;

import net.grandcentrix.tray.AppPreferences;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {


    public final int LOGIN_ACTIVITY_INTENT = 0x001;
    public final int PERSONAL_ACTIVITY_INTENT = 0x002;
    public final int TOTAL_DATA_ACTIVITY_INTENT = 0x003;
    public final int CREATE_CLASS_ACTIVITY_INTENT = 0x004;


    private boolean isLogin = false;
    private boolean isFirstResume = true;
    private boolean isRefreshLessonList = true;

    private DrawerLayout mDrawer;
    private FloatingActionButton mCreateClassBtn;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private RecyclerView mRecylerView;
    private TextView mEmptyTv;
    private TextView mNickNameTv;
    private TextView mTagTv;
    private CircleImageView mIconIv;
    private Button mLogoutBtn;
    private View mEmptyListContent;

    private MainLessonAdapter mAdapter;
    private List<LessonInfoEntity.LessonInfo> mLessonList;
    private MainPresenter mMainPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(this);
        addLauchTimes();
    }

    private void initViews() {
        mToolbar = findView(R.id.toolbar);
        mCreateClassBtn = findView(R.id.fab);
        mDrawer = findView(R.id.drawer_layout);
        mNavigationView = findView(R.id.nav_view);
        mEmptyTv = findView(R.id.main_empty_tv);
        mRecylerView = findView(R.id.main_classes_rv);
        mNickNameTv = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.main_nickname_tv);
        mTagTv = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.main_tag_tv);
        mIconIv = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.main_icon_iv);
        mLogoutBtn = (Button) mNavigationView.getHeaderView(0).findViewById(R.id.main_logout_btn);
        mEmptyListContent = findView(R.id.main_empty_list_content);

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (isLogin) {
                    int id = item.getItemId();
                    if (id == R.id.nav_userinfo) {
                        //用户信息页面
                        openActivityPage(PersonInfoActivity.class);
                    } else if (id == R.id.nav_dataview) {
                        //数据统计页面
                        openActivityPage(TotalRecordActivity.class);
                    } else if (id == R.id.nav_about) {
                        //应用信息页面
                        openActivityPage(AboutActivity.class);
                    } else if (id == R.id.nav_setting) {
                        //设置页面
                        openActivityPage(SettingsActivity.class);
                    } else if (id == R.id.nav_savedlist) {
                        //缓存列表页面
                        openActivityPage(OfflineActivity.class);
                    } else if (id == R.id.nav_cleansaved) {
                        //清理缓存操作
                    }
                } else {
                    ToastUtil.showToast(MainActivity.this, R.string.main_no_login);
                }
                return true;
            }
        });

        mCreateClassBtn.setOnClickListener(this);
        mLogoutBtn.setOnClickListener(this);


        mAdapter = new MainLessonAdapter(this);
        mAdapter.setOnItemClickListener(new MainLessonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openClassInfoPage(position);
            }
        });
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mRecylerView.setAdapter(mAdapter);

    }


    private void openActivityPage(Class activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        startActivity(intent);
    }

    private void addLauchTimes() {
        AppPreferences appPreferences = new AppPreferences(this);
        int times = appPreferences.getInt(ConstantValues.PREFERENCE_KEY_APP_LAUCH, ConstantValues.APP_LAUCH_DEFAULT);
        appPreferences.put(ConstantValues.PREFERENCE_KEY_APP_LAUCH, times + 1);
    }

    /**
     * 打开课程信息页面
     *
     * @param pos 点击的课程列表的position
     */
    private void openClassInfoPage(int pos) {
        if (mLessonList != null) {
            LessonInfoEntity.LessonInfo classInfo = mLessonList.get(pos);
            Intent intent = new Intent();
            intent.setClass(this, LessonInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("class_name", classInfo.name);
            bundle.putString("class_createtime", classInfo.createtime);
            bundle.putString("class_lastedittime", classInfo.lastedittime);
            bundle.putInt("class_uploadtimes", classInfo.uploadtimes);
            bundle.putString("class_lastuploadtime", classInfo.lastuploadtime);
            bundle.putString("class_week", classInfo.week);
            bundle.putInt("class_classid", classInfo.classid);
            bundle.putInt("class_count", classInfo.count);
            intent.putExtra("classinfo", bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstResume) {
            mMainPresenter.loadUserInfo(this);
            isFirstResume = false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intentId = intent.getIntExtra("main_intent", 0);
        switch (intentId) {
            case LOGIN_ACTIVITY_INTENT:
                //判断是否已经登陆成功
                if (intent.getBooleanExtra("login_flag", false)) {
                    //登陆成功,
                    isFirstResume = true;
                    isRefreshLessonList = true;
                }
                break;
            case CREATE_CLASS_ACTIVITY_INTENT:
                //重新刷新课程列表
                if (intent.getBooleanExtra("create_flag", false)) {
                    //登陆成功,
                    isFirstResume = true;
                    isRefreshLessonList = true;
                }
                break;
            case PERSONAL_ACTIVITY_INTENT:
                if (intent.getBooleanExtra("update_flag", false)) {
                    isFirstResume = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void loadLessonListSuccess(List<LessonInfoEntity.LessonInfo> lessonList) {
        //获取课程列表成功，显示课程列表
        mLessonList = lessonList;
        if (mLessonList.size() > 0) {
            mEmptyListContent.setVisibility(View.GONE);
        }
        mAdapter.addLessonInfo(mLessonList);
        isRefreshLessonList = false;

    }

    @Override
    public void loadLessonListError() {
        //获取课程列表失败
    }

    @Override
    public void loadUserInfoSuccess(UserBean userInfo) {
        //获取到已经登陆的账号，初始化界面
        isLogin = true;
        mLogoutBtn.setText(R.string.main_logout);
        mIconIv.setImageResource(R.drawable.icon);
        mNickNameTv.setText(userInfo.nickname);
        mTagTv.setText(userInfo.tag);
        //继续加载课程列表
        if (isRefreshLessonList) {
            mMainPresenter.loadLessonList(userInfo.account);
        }

    }

    @Override
    public void loadUserInfoError() {
        //不存在已经登录的账号
        //修改注销账号为登陆
        mLogoutBtn.setText(R.string.main_login);
    }

    @Override
    public void afterLogout() {
        //已经登出，清理页面
        isLogin = false;
        mEmptyListContent.setVisibility(View.VISIBLE);
        mLogoutBtn.setText(R.string.main_login);
        mIconIv.setImageResource(R.drawable.ic_default_avatar);
        mNickNameTv.setText(R.string.main_name_default);
        mTagTv.setText(R.string.main_tag_default);
        mAdapter.cleanLessonInfo();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_icon_iv) {
            if (!isLogin) {
                openActivityPage(LoginActivity.class);
            }
        } else if (v.getId() == R.id.fab) {
            openActivityPage(LessonCreateActivity.class);
        } else if (v.getId() == R.id.main_logout_btn) {
            if (!isLogin) {
                openActivityPage(LoginActivity.class);
            } else {
                //实现登出功能
                mMainPresenter.logoutUser(this);
            }
        }

    }
}
