package com.waterchen.android_photosignapp.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.View;


import com.orhanobut.logger.Logger;
import com.waterchen.android_photosignapp.R;

import java.text.DecimalFormat;

/**
 * Created by 橘子哥 on 2016/4/30.
 * 显示磁盘容量剩余大小
 */
public class ProgressView extends View {

    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#111111");
    private static final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#3F51B5");
    private static final int DEFAULT_NUPROGRESS_COLOR = Color.parseColor("#888888");
    private static final int DEFAULT_TEXT_SIZE = 12;


    private Context mContext;
    private Paint mTextPainter;
    private Paint mProgressPainter;

    private String mMainText;
    private String mTailText;
    private int mTextColor;
    private int mProgressColor;
    private int mUnProgressColor;
    private int mTextSize;
    private double mMaxProgress;
    private double mCurProgress;

    private DecimalFormat mNumberFormat;


    private String mResult;
    private Rect mBound;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        mNumberFormat = new DecimalFormat("######0.0");

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressView, defStyleAttr, 0);
        mMainText = typedArray.getString(R.styleable.ProgressView_mainText);
        mTailText = typedArray.getString(R.styleable.ProgressView_tailText);
        mTextColor = typedArray.getColor(R.styleable.ProgressView_textColor, DEFAULT_TEXT_COLOR);
        mProgressColor = typedArray.getColor(R.styleable.ProgressView_progressColor, DEFAULT_PROGRESS_COLOR);
        mUnProgressColor = typedArray.getColor(R.styleable.ProgressView_unprogressColor, DEFAULT_NUPROGRESS_COLOR);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.ProgressView_textSize, DEFAULT_TEXT_SIZE);
        typedArray.recycle();

        mCurProgress = 0.0;
        mMaxProgress = 0.0;

        if (mMainText == null) {
            mMainText = context.getString(R.string.progressview_default_main_text);
        }
        if (mTailText == null) {
            mTailText = context.getString(R.string.progressview_default_tail_text);
        }
        mTextPainter = new Paint();
        mTextPainter.setColor(mTextColor);
        mTextPainter.setTextSize(mTextSize);
        mBound = new Rect();

        mProgressPainter = new Paint();

//        mResult = mMainText + " 0.0GB / " + mTailText + " 0.0GB";

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        mResult = mMainText + " " + Formatter.formatFileSize(mContext, (long) mMaxProgress) + " / " + mTailText + " " + Formatter.formatFileSize(mContext, (long) mCurProgress);
        //绘制底层
        mProgressPainter.setColor(mUnProgressColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mProgressPainter);
        //绘制进度
        mProgressPainter.setColor(mProgressColor);
        canvas.drawRect(0, 0, (float) (getMeasuredWidth() * getPrecent()), getMeasuredHeight(), mProgressPainter);
        mTextPainter.getTextBounds(mResult, 0, mResult.length(), mBound);
        canvas.drawText(mResult, 12, getMeasuredHeight() / 2 + mBound.height() / 2, mTextPainter);
    }

    /**
     * 设置当前进度值
     *
     * @param progress
     */
    public void setProgress(double progress) {
        mCurProgress = (progress > 0 ? progress : 0);
        invalidate();
    }

    /**
     * 设置最大的进度值
     *
     * @param max
     */
    public void setMaxProgress(double max) {
        mMaxProgress = (max > 0 ? max : 0);
        invalidate();
    }

    private double getPrecent() {
        if (mCurProgress > mMaxProgress) {
            return 0.0f;
        }
        double result = (mMaxProgress - mCurProgress) / mMaxProgress;
        Logger.e("percent : " + result);
        return (mMaxProgress - mCurProgress) / mMaxProgress;
    }
}
