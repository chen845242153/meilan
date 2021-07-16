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


/**
 * @author lion
 * 坡度进度
 */
public class SlopeProgressView extends View {
    /**
     * 画笔
     */
    private Paint mBackPaint, mProgressPaint;

    /**
     * 圆环进度(0-100)
     */
    private double mProgress = 0;

    public final static int UPHILL = 0;
    public final static int LEVEL = 1;
    public final static int DOWNHILL = 2;

    public SlopeProgressView(Context context) {
        this(context, null);
    }

    public SlopeProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlopeProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackPaint = new Paint();
        mBackPaint.setStyle(Paint.Style.STROKE);
        mBackPaint.setAntiAlias(true);
        mBackPaint.setDither(true);
        mBackPaint.setStrokeWidth(AppUtils.dipToPx(context, 5));
        mBackPaint.setColor(Color.parseColor("#FFF2F2F2"));

        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setDither(true);
        mProgressPaint.setStrokeWidth(AppUtils.dipToPx(context, 8));
    }

    public void initView(int slopeType) {
        switch (slopeType) {
            case UPHILL:
                mProgressPaint.setColor(Color.parseColor("#FFB71010"));
                break;
            case LEVEL:
                mProgressPaint.setColor(Color.parseColor("#FFEE8C1E"));
                break;
            case DOWNHILL:
                mProgressPaint.setColor(Color.parseColor("#FF6FB92C"));
                break;
            default:
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingBg = AppUtils.dipToPx(getContext(), 5);
        RectF rectF = new RectF(paddingBg, paddingBg, getWidth() - paddingBg, getHeight() - paddingBg);
        canvas.drawArc(rectF, 0, 360, false, mBackPaint);
        canvas.drawArc(rectF, 270, (float) (360 * mProgress), false, mProgressPaint);
    }

    /**
     * 更新进度条
     */
    public void setProgress(double progress) {
        mProgress = progress;
        postInvalidate();
    }
}
