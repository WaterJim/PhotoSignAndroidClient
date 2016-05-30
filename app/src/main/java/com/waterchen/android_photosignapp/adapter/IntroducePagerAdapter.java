package com.waterchen.android_photosignapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘子哥 on 2016/3/9.
 */
public class IntroducePagerAdapter extends PagerAdapter {

    private ArrayList<View> pageList;
    private int mPageSize;

    public IntroducePagerAdapter(List pages) {
        pageList = (ArrayList<View>) pages;
        mPageSize = pageList != null ? pageList.size() : 0;
    }

    @Override
    public int getCount() {
        return mPageSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        if (pageList != null) {
            container.removeView(pageList.get(position));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        View pageView = null;
        if (pageList != null) {
            pageView = pageList.get(position);
            container.addView(pageView);
        }

        return pageView;
    }
}
