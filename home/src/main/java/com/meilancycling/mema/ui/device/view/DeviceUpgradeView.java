package com.meilancycling.mema.ui.device.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 设备升级
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class DeviceUpgradeView extends View {
    private Paint backgroundPaint, valuePaint, textValuePaint;
    private int mProgress;

    public DeviceUpgradeView(Context context) {
        super(context);
        init();
    }

    public DeviceUpgradeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DeviceUpgradeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setDither(true);
        backgroundPaint.setColor(Color.parseColor("#FFD4EAC0"));
        backgroundPaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 3));
        backgroundPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setDither(true);
        valuePaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color));
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setStrokeCap(Paint.Cap.ROUND);
        valuePaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 5));

        textValuePaint = new Paint();
        textValuePaint.setAntiAlias(true);
        textValuePaint.setDither(true);
        textValuePaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color));
        textValuePaint.setTextSize(AppUtils.spToPx(getContext(), 18));
        textValuePaint.setTextAlign(Paint.Align.CENTER);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = AppUtils.dipToPx(getContext(), 2.5f);
        RectF oval = new RectF(left, left, getWidth() - 2 * left, getHeight() - 2 * left);
        canvas.drawArc(oval, -90, 360, false, backgroundPaint);
        canvas.drawArc(oval, -90, (float) mProgress / 100 * 360, false, valuePaint);

        Paint.FontMetrics fontMetrics = textValuePaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = oval.centerY() + distance;
        canvas.drawText(mProgress + getContext().getString(R.string.percent), oval.centerX(), baseline, textValuePaint);
    }

    public void updateProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }
}
