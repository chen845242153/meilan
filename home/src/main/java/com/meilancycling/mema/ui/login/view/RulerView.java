package com.meilancycling.mema.ui.login.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 1/28/21 5:28 PM
 */
public class RulerView extends View {
    private float maxValue;
    private float minValue;
    private float currentValue;
    private float itemValue;

    public RulerView(Context context) {
        super(context);
        init();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint backgroundPaint, line6Paint, line9Paint, valuePaint;
    private float eachWidth;

    /**
     * 初始化
     */
    private void init() {

        int oneWidth = AppUtils.dipToPx(getContext(), 1);
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setDither(true);
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_f2));
        backgroundPaint.setStyle(Paint.Style.FILL);

        line6Paint = new Paint();
        line6Paint.setAntiAlias(true);
        line6Paint.setDither(true);
        line6Paint.setColor(ContextCompat.getColor(getContext(), R.color.black_6));
        line6Paint.setStyle(Paint.Style.STROKE);
        line6Paint.setStrokeWidth(oneWidth);

        line9Paint = new Paint();
        line9Paint.setAntiAlias(true);
        line9Paint.setDither(true);
        line9Paint.setColor(ContextCompat.getColor(getContext(), R.color.black_9));
        line9Paint.setStyle(Paint.Style.STROKE);
        line9Paint.setStrokeWidth(oneWidth);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setDither(true);
        valuePaint.setColor(ContextCompat.getColor(getContext(), R.color.black_9));
        valuePaint.setTextSize(AppUtils.spToPx(getContext(), 14));
        valuePaint.setTextAlign(Paint.Align.CENTER);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //每一小格的宽度
        eachWidth = (float) getWidth() / 48;
        //总长度=大小值间隔和两边的空格
        int totalNumber = (int) ((maxValue - minValue) / itemValue) + 48;
        float totalWidth = totalNumber * eachWidth;
        RectF rectF = new RectF(0, 0, totalWidth, getHeight());
        canvas.drawRect(rectF, backgroundPaint);
        int longHeight = AppUtils.dipToPx(getContext(), 29);
        int mediumHeight = AppUtils.dipToPx(getContext(), 21);
        int shortHeight = AppUtils.dipToPx(getContext(), 14);
        int valueWidth = AppUtils.dipToPx(getContext(), 17);

        float currentMinValue = minValue - 24 * itemValue;
        if (currentMinValue < 0) {
            currentMinValue = 0;
        }
        canvas.translate((currentValue - currentMinValue) / itemValue * eachWidth * -1 + (float) getWidth() / 2, 0);
        for (int i = 0; i < totalNumber; i++) {
            float currentData = currentMinValue + itemValue * i;
            if ((currentData * 10) % (itemValue * 100) == 0) {
                canvas.drawLine(eachWidth * i, 0, eachWidth * i, longHeight, line6Paint);
                Paint.FontMetrics fontMetrics = valuePaint.getFontMetrics();
                RectF valueF = new RectF(eachWidth * i - valueWidth, AppUtils.dipToPx(getContext(), 47), eachWidth * i + valueWidth, AppUtils.dipToPx(getContext(), 67));
                float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                float baseline = valueF.centerY() + distance;
                canvas.drawText(String.valueOf((int) currentData), valueF.centerX(), baseline, valuePaint);
            } else if ((currentData * 10) % (itemValue * 100) == 5) {
                canvas.drawLine(eachWidth * i, 0, eachWidth * i, mediumHeight, line9Paint);
            } else {
                canvas.drawLine(eachWidth * i, 0, eachWidth * i, shortHeight, line9Paint);
            }
        }
    }

    private float startX;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float dx = endX - startX;
                float value = currentValue - (dx / eachWidth * itemValue);
                if (Math.abs(currentValue - value) >= itemValue / 10) {
                    if (value >= minValue && value <= maxValue) {
                        currentValue = value;
                        postInvalidate();
                        startX = endX;
                        if (mCurrentValueCallback != null) {
                            mCurrentValueCallback.currentValueCallback(currentValue);
                        }
                    }
                }
                break;
            default:
        }
        return true;
    }

    /**
     * 设置当前值和类型
     *
     * @param type 0 身高
     *             1 体重
     */
    public void setCurrentAndType(float value, int unit, int type) {
        currentValue = value;
        if (type == 0) {
            if (unit == Unit.METRIC.value) {
                maxValue = Config.G_MAX_HEIGHT;
                minValue = Config.G_MIN_HEIGHT;
                itemValue = 1;
            } else {
                maxValue = Config.Y_MAX_HEIGHT;
                minValue = Config.Y_MIN_HEIGHT;
                itemValue = 0.1f;
            }
        } else {
            if (unit == Unit.METRIC.value) {
                maxValue = Config.G_MAX_WEIGHT;
                minValue = Config.G_MIN_WEIGHT;
            } else {
                maxValue = Config.Y_MAX_WEIGHT;
                minValue = Config.Y_MIN_WEIGHT;
            }
            itemValue = 1;
        }
    }

    public interface CurrentValueCallback {
        /**
         * 返回当前值
         *
         * @param currentValue 当前值
         */
        void currentValueCallback(float currentValue);
    }

    private CurrentValueCallback mCurrentValueCallback;

    public void setCurrentValueCallback(CurrentValueCallback currentValueCallback) {
        mCurrentValueCallback = currentValueCallback;
    }
}
