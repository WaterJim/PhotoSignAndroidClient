package com.waterchen.android_photosignapp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.model.entity.RecordEntity;
import com.waterchen.android_photosignapp.presenter.LessonRecordPresenter;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;
import com.waterchen.android_photosignapp.ui.view.LessonRecordView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class LessonRecordActivity extends BaseActivity implements LessonRecordView {

    private Toolbar mToolbar;
    private BarChart mBarChart;
    private PieChart mPieChart;
    private View mContentView;
    private ImageView mEmptyIv;

    private LessonRecordPresenter mRecordPresenter;
    private String account;
    private int classId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessonrecord);
        initViews();
        mRecordPresenter = new LessonRecordPresenter();
        mRecordPresenter.attachView(this);
    }

    private void initViews() {
        mToolbar = findView(R.id.lessonrecord_toolbar);
        mBarChart = findView(R.id.lessonrecord_barchart);
        mPieChart = findView(R.id.lessonrecord_piechart);
        mContentView = findView(R.id.lessonrecord_content_sv);
        mEmptyIv = findView(R.id.lessonrecord_empty_iv);

        initBarChart();
        initPieChart();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        classId = getIntent().getIntExtra("classId", 1);
        account = getIntent().getStringExtra("account");
        mRecordPresenter.loadLessonRecord(account, classId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, LessonInfoActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void loadLessonRecordSuccess() {
        //隐藏Empty 图标
        mContentView.setVisibility(View.VISIBLE);
        mEmptyIv.setVisibility(View.GONE);
    }

    @Override
    public void loadLessonRecordError() {

    }

    @Override
    public void loadPieChartData(int signCount, int totalCount) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(signCount, 0));
        yVals1.add(new Entry(totalCount - signCount, 1));

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add(getString(R.string.l_record_signed));
        xVals.add(getString(R.string.l_record_no_sign));

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.parseColor("#3F51B5"));
        colors.add(Color.parseColor("#555555"));

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }

    @Override
    public void loadBarChartData(List<RecordEntity.Record> recordList) {
        int xSize = recordList.size();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xSize; i++) {
            xVals.add((recordList.get(i).date));
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

//        float mult = y * 1000f;

        for (int i = 0; i < xSize; i++) {
            float val = recordList.get(i).count;
            yVals1.add(new BarEntry(val, i));
        }

        for (int i = 0; i < xSize; i++) {
            float val = recordList.get(i).totalcount
                    - recordList.get(i).count;
            yVals2.add(new BarEntry(val, i));
        }
        BarDataSet set1, set2;

        set1 = new BarDataSet(yVals1, getString(R.string.l_record_signed));
        set1.setColor(Color.parseColor("#3F51B5"));
        set2 = new BarDataSet(yVals2, getString(R.string.l_record_no_sign));
        set2.setColor(Color.parseColor("#555555"));

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(80f);
        data.setValueTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf"));

        mBarChart.setData(data);
        mBarChart.invalidate();
    }


    /**
     * 初始化圆饼图
     */
    private void initPieChart() {

        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("");
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        mPieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf"));
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("最近一次签到记录\n记录日期 2016-05-24");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 8, 0);

        s.setSpan(new StyleSpan(Typeface.NORMAL), 8, 13, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, 13, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 8, 13, 0);

        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 10, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 10, s.length(), 0);
        return s;
    }

    /**
     * 初始化柱状图
     */
    private void initBarChart() {
        mBarChart.setDescription("");
        mBarChart.setDescriptionPosition(mBarChart.getX(), mBarChart.getY());
        mBarChart.setPinchZoom(false);

        mBarChart.setDrawBarShadow(false);

        mBarChart.setDrawGridBackground(false);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setTypeface(tf);
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xl = mBarChart.getXAxis();
        xl.setTypeface(tf);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        mBarChart.getAxisRight().setEnabled(false);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecordPresenter != null) {
            mRecordPresenter.releasePresenter();
        }
    }
}
