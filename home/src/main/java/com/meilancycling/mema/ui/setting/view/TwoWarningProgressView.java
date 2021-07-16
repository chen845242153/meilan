package com.meilancycling.mema.ui.setting.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 两个值警告控件
 * @Author: lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class TwoWarningProgressView extends View {

    private Paint minValuePaint, maxValuePaint, mBackgroundPaint, minProgressPaint, maxProgressPaint;
    private boolean minSwitch, maxSwitch;
    private int padding;
    private int minCurrentValue, maxCurrentValue, mMaxValue, mMinValue;
    private Path mPath;
    private float minPointX;
    private float pointY;
    private float maxPointX;
    private int touchPadding;
    private boolean minFlag;
    private boolean maxFlag;

    public TwoWarningProgressView(Context context) {
        super(context);
        init();
    }

    public TwoWarningProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TwoWarningProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setColor(Color.parseColor("#FFEEEEEE"));
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        minValuePaint = new Paint();
        minValuePaint.setAntiAlias(true);
        minValuePaint.setDither(true);
        minValuePaint.setTextSize(AppUtils.spToPx(getContext(), 13));
        minValuePaint.setTextAlign(Paint.Align.RIGHT);

        maxValuePaint = new Paint();
        maxValuePaint.setAntiAlias(true);
        maxValuePaint.setDither(true);
        maxValuePaint.setTextSize(AppUtils.spToPx(getContext(), 13));
        maxValuePaint.setTextAlign(Paint.Align.LEFT);

        minProgressPaint = new Paint();
        minProgressPaint.setAntiAlias(true);
        minProgressPaint.setDither(true);
        minProgressPaint.setStyle(Paint.Style.FILL);

        maxProgressPaint = new Paint();
        maxProgressPaint.setAntiAlias(true);
        maxProgressPaint.setDither(true);
        maxProgressPaint.setStyle(Paint.Style.FILL);

        padding = AppUtils.dipToPx(getContext(), 25);
        mPath = new Path();
        touchPadding = AppUtils.dipToPx(getContext(), 20);
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
        float minValueX = ((float) (minCurrentValue - mMinValue) / (mMaxValue - mMinValue)) * (getWidth() - 2 * padding) + padding;
        float maxValueX = ((float) (maxCurrentValue - mMinValue) / (mMaxValue - mMinValue)) * (getWidth() - 2 * padding) + padding;

        if (minSwitch) {
            minValuePaint.setColor(Color.parseColor("#FF333333"));
            minProgressPaint.setColor(Color.parseColor("#FF6FB92C"));
        } else {
            minValuePaint.setColor(Color.parseColor("#FF999999"));
            minProgressPaint.setColor(Color.parseColor("#FFCFCFCF"));
        }
        if (maxSwitch) {
            maxValuePaint.setColor(Color.parseColor("#FF333333"));
            maxProgressPaint.setColor(Color.parseColor("#FF6FB92C"));
        } else {
            maxValuePaint.setColor(Color.parseColor("#FF999999"));
            maxProgressPaint.setColor(Color.parseColor("#FFCFCFCF"));
        }

        //进度值
        Paint.FontMetrics fontMetrics = minValuePaint.getFontMetrics();
        int valueLeft = AppUtils.dipToPx(getContext(), 12);
        int valueBottom = AppUtils.dipToPx(getContext(), 19);
        RectF minRectF = new RectF(minValueX - valueLeft, 0, minValueX + valueLeft, valueBottom);
        float minDistance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float minBaseline = minRectF.centerY() + minDistance;

        fontMetrics = maxValuePaint.getFontMetrics();
        RectF maxRectF = new RectF(maxValueX - valueLeft, 0, maxValueX + valueLeft, valueBottom);
        float maxDistance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float maxBaseline = maxRectF.centerY() + maxDistance;

        RectF minProgressRectF = new RectF(padding, topPadding, minValueX, bottomPadding);

        RectF maxProgressRectF = new RectF(maxValueX, topPadding, getWidth() - padding, bottomPadding);

        int valueY = AppUtils.dipToPx(getContext(), 35);
        int radiusValue = AppUtils.dipToPx(getContext(), 6);

        minPointX = minValueX;
        maxPointX = maxValueX;
        pointY = valueY;

        int pathLeft = AppUtils.dipToPx(getContext(), 2.5f);
        int pathBottom = AppUtils.dipToPx(getContext(), 4);
        int pathTop = AppUtils.dipToPx(getContext(), 19);

        if (minFlag) {
            mPath.reset();
            mPath.moveTo(minValueX - pathLeft, pathTop);
            mPath.lineTo(minValueX + pathLeft, pathTop);
            mPath.lineTo(minValueX, pathTop + pathBottom);
            canvas.drawPath(mPath, minProgressPaint);
            canvas.drawText(String.valueOf(minCurrentValue), minRectF.centerX(), minBaseline, minValuePaint);
            canvas.drawRoundRect(minProgressRectF, rx, rx, minProgressPaint);
            canvas.drawCircle(minValueX, valueY, radiusValue, minProgressPaint);
        }
        if (maxFlag) {
            mPath.reset();
            mPath.moveTo(maxValueX - pathLeft, pathTop);
            mPath.lineTo(maxValueX + pathLeft, pathTop);
            mPath.lineTo(maxValueX, pathTop + pathBottom);
            canvas.drawPath(mPath, maxProgressPaint);
            canvas.drawText(String.valueOf(maxCurrentValue), maxRectF.centerX(), maxBaseline, maxValuePaint);
            canvas.drawRoundRect(maxProgressRectF, rx, rx, maxProgressPaint);
            canvas.drawCircle(maxValueX, valueY, radiusValue, maxProgressPaint);
        }
    }

    /**
     * 设置数据和开关
     */
    public void setProgressValue(int minCurrent, int maxCurrent, int maxValue, int minValue, boolean minToggle, boolean maxToggle) {
        minCurrentValue = minCurrent;
        maxCurrentValue = maxCurrent;
        mMaxValue = maxValue;
        mMinValue = minValue;
        minSwitch = minToggle;
        maxSwitch = maxToggle;
        checkFlag();
        postInvalidate();
    }

    /**
     * 设置最小值开关
     */
    public void setMinProgressToggle(boolean toggle) {
        minSwitch = toggle;
        checkFlag();
        postInvalidate();
    }

    /**
     * 设置最大值开关
     */
    public void setMaxProgressToggle(boolean toggle) {
        maxSwitch = toggle;
        checkFlag();
        postInvalidate();
    }

    /**
     * 是否有效
     */
    private boolean isMinEffective;
    private boolean isMaxEffective;

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
        if (minSwitch || maxSwitch) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //判断down事件是否有效
                    boolean xMinEffective = (event.getX() >= minPointX - touchPadding && event.getX() <= minPointX + touchPadding);
                    boolean xMaxEffective = (event.getX() >= maxPointX - touchPadding && event.getX() <= maxPointX + touchPadding);
                    boolean yEffective = (event.getY() >= pointY - touchPadding && event.getY() <= pointY + touchPadding);
                    isMinEffective = xMinEffective && yEffective;
                    isMaxEffective = !isMinEffective && xMaxEffective && yEffective;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isMinEffective) {
                        float valueX = event.getX();
                        float maxX;
                        if (maxFlag) {
                            maxX = ((float) (maxCurrentValue - mMaxValue * 0.04) / (mMaxValue - mMinValue)) * (getWidth() - 2 * padding) + padding;
                        } else {
                            maxX = ((float) (mMaxValue - mMaxValue * 0.04) / (mMaxValue - mMinValue)) * (getWidth() - 2 * padding) + padding;
                        }
                        if (valueX <= padding) {
                            valueX = padding;
                        }
                        if (valueX >= maxX) {
                            valueX = maxX;
                        }
                        getMinCurrentValue(valueX);
                    }
                    if (isMaxEffective) {
                        float valueX = event.getX();
                        float minX;
                        if (minFlag) {
                            minX = ((float) (minCurrentValue + mMaxValue * 0.04) / (mMaxValue - mMinValue)) * (getWidth() - 2 * padding) + padding;
                        } else {
                            minX = ((float) (mMinValue + mMaxValue * 0.04) / (mMaxValue - mMinValue)) * (getWidth() - 2 * padding) + padding;
                        }
                        if (valueX <= minX) {
                            valueX = minX;
                        }
                        if (valueX >= getWidth() - padding) {
                            valueX = getWidth() - padding;
                        }
                        getMaxCurrentValue(valueX);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isMinEffective = false;
                    isMaxEffective = false;
                    break;
                default:
            }
        }
        return true;
    }

    private void getMinCurrentValue(float xValue) {
        float totalWidth = getWidth() - 2 * padding;
        if (xValue <= padding) {
            minCurrentValue = mMinValue;
        } else {
            float current = xValue - padding;
            minCurrentValue = (int) ((float) (mMaxValue - mMinValue) / totalWidth * current);
            if (minCurrentValue <= mMinValue) {
                minCurrentValue = mMinValue;
            }
        }

        if (mTwoWarningProgressChangeListener != null) {
            mTwoWarningProgressChangeListener.minValueCallback(minCurrentValue);
        }
        if (!maxFlag && maxCurrentValue - minCurrentValue <= 1) {
            maxCurrentValue = mMaxValue;
            if (mTwoWarningProgressChangeListener != null) {
                mTwoWarningProgressChangeListener.maxValueCallback(maxCurrentValue);
            }
        }
        postInvalidate();
    }

    private void getMaxCurrentValue(float xValue) {
        float totalWidth = getWidth() - 2 * padding;
        if (xValue == getWidth() - padding) {
            maxCurrentValue = mMaxValue;
        } else {
            float current = xValue - padding;
            maxCurrentValue = (int) ((float) (mMaxValue - mMinValue) / totalWidth * current);
        }
        if (mTwoWarningProgressChangeListener != null) {
            mTwoWarningProgressChangeListener.maxValueCallback(maxCurrentValue);
        }
        if (!minFlag && maxCurrentValue - minCurrentValue <= 1) {
            minCurrentValue = mMinValue;
            if (mTwoWarningProgressChangeListener != null) {
                mTwoWarningProgressChangeListener.minValueCallback(minCurrentValue);
            }
        }
        postInvalidate();
    }

    public interface TwoWarningProgressChangeListener {
        /**
         * 最小值
         *
         * @param value 当前值
         */
        void minValueCallback(int value);

        /**
         * 最大值
         *
         * @param value 当前值
         */
        void maxValueCallback(int value);
    }

    private TwoWarningProgressChangeListener mTwoWarningProgressChangeListener;

    public void setTwoWarningProgressChangeListener(TwoWarningProgressChangeListener twoWarningProgressChangeListener) {
        mTwoWarningProgressChangeListener = twoWarningProgressChangeListener;
    }

    private void checkFlag() {
        if (minSwitch) {
            minFlag = true;
        } else {
            minFlag = !maxSwitch;
        }
        if (maxSwitch) {
            maxFlag = true;
        } else {
            maxFlag = !minSwitch;
        }
    }
}
