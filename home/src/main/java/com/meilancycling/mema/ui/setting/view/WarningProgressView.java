package com.meilancycling.mema.ui.setting.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 警告
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class WarningProgressView extends View {

    private Paint mValuePaint, mBackgroundPaint, mProgressPaint;
    private boolean switchValue;
    private int padding;
    private int mCurrent, mMaxValue, mMinValue;
    private Path mPath;
    private float mPointX;
    private float mPointY;
    private int touchPadding;

    public WarningProgressView(Context context) {
        super(context);
        init();
    }

    public WarningProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WarningProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setColor(Color.parseColor("#FFEEEEEE"));
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mValuePaint = new Paint();
        mValuePaint.setAntiAlias(true);
        mValuePaint.setDither(true);
        mValuePaint.setTextSize(AppUtils.spToPx(getContext(), 13));
        mValuePaint.setTextAlign(Paint.Align.CENTER);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setDither(true);
        mProgressPaint.setStyle(Paint.Style.FILL);

        padding = AppUtils.dipToPx(getContext(), 12);
        mPath = new Path();
        touchPadding = AppUtils.dipToPx(getContext(), 15);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景进度条
        int topPadding = AppUtils.dipToPx(getContext(), 33);
        int bottomPadding = AppUtils.dipToPx(getContext(), 37);
        RectF rectF = new RectF(padding, topPadding, getWidth() - padding, bottomPadding);
        int rx = AppUtils.dipToPx(getContext(), 2);
        canvas.drawRoundRect(rectF, rx, rx, mBackgroundPaint);

        //计算进度条
        float valueX = ((float) (mCurrent - mMinValue) / (mMaxValue - mMinValue)) * (getWidth() - 2 * padding) + padding;
        if (switchValue) {
            mValuePaint.setColor(Color.parseColor("#FF333333"));
            mProgressPaint.setColor(Color.parseColor("#FF6FB92C"));
        } else {
            mValuePaint.setColor(Color.parseColor("#FF999999"));
            mProgressPaint.setColor(Color.parseColor("#FFCFCFCF"));
        }

        //三角标
        mPath.reset();
        int pathLeft = AppUtils.dipToPx(getContext(), 2.5f);
        int pathBottom = AppUtils.dipToPx(getContext(), 4);
        int pathTop = AppUtils.dipToPx(getContext(), 19);
        mPath.moveTo(valueX - pathLeft, pathTop);
        mPath.lineTo(valueX + pathLeft, pathTop);
        mPath.lineTo(valueX, pathTop + pathBottom);
        canvas.drawPath(mPath, mProgressPaint);

        //进度值
        Paint.FontMetrics fontMetrics = mValuePaint.getFontMetrics();
        int valueLeft = AppUtils.dipToPx(getContext(), 12);
        int valueBottom = AppUtils.dipToPx(getContext(), 19);
        RectF valueRectF = new RectF(valueX - valueLeft, 0, valueX + valueLeft, valueBottom);
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = valueRectF.centerY() + distance;
        canvas.drawText(String.valueOf(mCurrent), valueRectF.centerX(), baseline, mValuePaint);

        RectF progressRectF = new RectF(padding, topPadding, valueX, bottomPadding);
        canvas.drawRoundRect(progressRectF, rx, rx, mProgressPaint);

        int valueY = AppUtils.dipToPx(getContext(), 35);
        int radiusValue = AppUtils.dipToPx(getContext(), 6);
        mPointX = valueX;
        mPointY = valueY;
        canvas.drawCircle(valueX, valueY, radiusValue, mProgressPaint);
    }

    /**
     * 设置数据和开关
     */
    public void setProgressValue(int current, int maxValue, int minValue, boolean toggle) {
        mCurrent = current;
        mMaxValue = maxValue;
        mMinValue = minValue;
        switchValue = toggle;
        postInvalidate();
    }

    /**
     * 设置开关
     */
    public void setProgressToggle(boolean toggle) {
        switchValue = toggle;
        postInvalidate();
    }

    /**
     * 是否有效
     */
    private boolean isEffective;

    /**
     * 事件分发, 请求父控件及祖宗控件不要拦截事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 用getParent去请求
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (switchValue) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //判断down事件是否有效
                    boolean xEffective = (event.getX() >= mPointX - touchPadding && event.getX() <= mPointX + touchPadding);
                    boolean yEffective = (event.getY() >= mPointY - touchPadding && event.getY() <= mPointY + touchPadding);
                    isEffective = xEffective && yEffective;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isEffective) {
                        float valueX = event.getX();
                        if (valueX <= padding) {
                            valueX = padding;
                        }
                        if (valueX >= getWidth() - padding) {
                            valueX = getWidth() - padding;
                        }
                        getCurrentValue(valueX);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isEffective = false;
                    break;
                default:
            }
        }
        return true;
    }

    private void getCurrentValue(float xValue) {
        float totalWidth = getWidth() - 2 * padding;
        if (xValue == padding) {
            mCurrent = mMinValue;
        } else if (xValue == getWidth() - padding) {
            mCurrent = mMaxValue;
        } else {
            float current = xValue - padding;
            mCurrent = (int) ((float) (mMaxValue - mMinValue) / totalWidth * current + mMinValue);
        }
        postInvalidate();
        if (mWarningProgressChangeListener != null) {
            mWarningProgressChangeListener.warningValueCallback(mCurrent);
        }
    }

    public interface WarningProgressChangeListener {
        /**
         * 当前值回调
         *
         * @param value 当前值
         */
        void warningValueCallback(int value);
    }

    private WarningProgressChangeListener mWarningProgressChangeListener;

    public void setWarningProgressChangeListener(WarningProgressChangeListener
                                                         warningProgressChangeListener) {
        mWarningProgressChangeListener = warningProgressChangeListener;
    }
}
