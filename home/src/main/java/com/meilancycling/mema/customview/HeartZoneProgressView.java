package com.meilancycling.mema.customview;

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

import java.util.List;


/**
 * 心率区间
 *
 * @author lion
 */
public class HeartZoneProgressView extends View {
    /**
     * 画笔
     */
    private Paint valuePaint;
    private List<Integer> mPercentage;

    public HeartZoneProgressView(Context context) {
        this(context, null);
    }

    public HeartZoneProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartZoneProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        valuePaint = new Paint();
        valuePaint.setStyle(Paint.Style.STROKE);
        // 设置抗锯齿
        valuePaint.setAntiAlias(true);
        // 设置抖动
        valuePaint.setDither(true);
        valuePaint.setStrokeWidth(AppUtils.dipToPx(context, 15));
        valuePaint.setStrokeCap(Paint.Cap.BUTT);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingBg = AppUtils.dipToPx(getContext(), 10);
        RectF rectF = new RectF(paddingBg, paddingBg, getWidth() - paddingBg, getHeight() - paddingBg);
        valuePaint.setColor(Color.parseColor("#FFDCDCDC"));
        canvas.drawArc(rectF, 0, 360, false, valuePaint);
        if (mPercentage != null && mPercentage.size() == 5) {
            float angle1 = mPercentage.get(0) * 3.6f;
            float angle2 = mPercentage.get(1) * 3.6f;
            float angle3 = mPercentage.get(2) * 3.6f;
            float angle4 = mPercentage.get(3) * 3.6f;
            float angle5 = mPercentage.get(4) * 3.6f;
            valuePaint.setColor(Color.parseColor("#FF999999"));
            canvas.drawArc(rectF, 0, angle1, false, valuePaint);
            valuePaint.setColor(Color.parseColor("#FF579FC5"));
            canvas.drawArc(rectF, angle1, angle2, false, valuePaint);
            valuePaint.setColor(Color.parseColor("#FF6FB92C"));
            canvas.drawArc(rectF, angle1 + angle2, angle3, false, valuePaint);
            valuePaint.setColor(Color.parseColor("#FFEE8D1F"));
            canvas.drawArc(rectF, angle1 + angle2 + angle3, angle4, false, valuePaint);
            valuePaint.setColor(Color.parseColor("#FFB71010"));
            canvas.drawArc(rectF, angle1 + angle2 + angle3 + angle4, angle5, false, valuePaint);
        }
    }

    /**
     * 更新进度条
     */
    public void setProgress(List<Integer> percentage) {
        mPercentage = percentage;
        postInvalidate();
    }
}
