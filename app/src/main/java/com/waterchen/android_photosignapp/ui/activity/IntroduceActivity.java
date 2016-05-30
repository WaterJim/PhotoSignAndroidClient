package com.waterchen.android_photosignapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.adapter.IntroducePagerAdapter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class IntroduceActivity extends BaseActivity {


    private CircleIndicator mIndicatior;
    private ViewPager mViewPager;
    private Button mNextBtn;
    private ArrayList<View> pageList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        initViews();

    }


    private void initViews() {
        mIndicatior = findView(R.id.v_indicator);
        mViewPager = findView(R.id.vp_show);

        pageList = new ArrayList<>();

        pageList.add(getLayoutInflater().inflate(R.layout.layout_introduce_page, null));
        pageList.add(getLayoutInflater().inflate(R.layout.layout_introduce_page, null));
        pageList.add(getLayoutInflater().inflate(R.layout.layout_introduce_page, null));
        pageList.add(getLayoutInflater().inflate(R.layout.layout_introduce_page, null));

        setLastPage(pageList.size() - 1);
        mViewPager.setAdapter(new IntroducePagerAdapter(pageList));
        mIndicatior.setViewPager(mViewPager);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroduceActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void setLastPage(int position) {
        View page = pageList.get(position);
        if (page != null) {
            ((ViewStub) page.findViewById(R.id.introduce_page_vs)).inflate();
            mNextBtn = (Button) page.findViewById(R.id.btn_next);
        }
    }

}
