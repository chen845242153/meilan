package com.meilancycling.mema.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 首页目标进度条
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class HomeScheduleView extends View {

    private Paint mBackgroundPaint;
    private Paint mTargetPaint;
    private Paint mPaint;
    private int mTotal;
    private int mTarget;
    private int mCurrent;
    public final static int DISTANCE = 0;
    public final static int TIME = 1;
    public final static int KAL = 2;

    public HomeScheduleView(Context context) {
        super(context);
        init();
    }

    public HomeScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 4));
        mBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        mBackgroundPaint.setColor(Color.parseColor("#FFDCDCDC"));

        mTargetPaint = new Paint();
        mTargetPaint.setAntiAlias(true);
        mTargetPaint.setDither(true);
        mTargetPaint.setStyle(Paint.Style.STROKE);
        mTargetPaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 6));
        mTargetPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 6));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTotal != 0) {
            float angle = (float) Math.toDegrees(Math.asin(0.6883));
            int powerPadding = AppUtils.dipToPx(getContext(), 3);
            RectF oval = new RectF(powerPadding, powerPadding, getWidth() - powerPadding, getHeight() - powerPadding);
            canvas.drawArc(oval, 180 - angle, 180 + 2 * angle, false, mBackgroundPaint);
            canvas.drawArc(oval, 180 - angle, (float) mTarget / mTotal * (180 + 2 * angle), false, mTargetPaint);
            canvas.drawArc(oval, 180 - angle, (float) mCurrent / mTotal * (180 + 2 * angle), false, mPaint);
        }
    }

    public void setScheduleValue(int total, int target, int current, int type) {
        switch (type) {
            case DISTANCE:
                mPaint.setColor(Color.parseColor("#FF6FB92C"));
                mTargetPaint.setColor(Color.parseColor("#FFD4EAC0"));
                break;
            case TIME:
                mPaint.setColor(Color.parseColor("#FF589FC5"));
                mTargetPaint.setColor(Color.parseColor("#FFABCFE2"));
                break;
            case KAL:
                mPaint.setColor(Color.parseColor("#FFEE8D1F"));
                mTargetPaint.setColor(Color.parseColor("#FFF6C68F"));
                break;
            default:
        }
        mTotal = total;
        mTarget = target;
        mCurrent = current;
        if (mCurrent > mTotal) {
            mCurrent = mTotal;
        }
        postInvalidate();
    }
}
