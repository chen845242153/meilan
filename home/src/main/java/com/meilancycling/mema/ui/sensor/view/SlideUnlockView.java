package com.meilancycling.mema.ui.sensor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 滑动解锁
 * @Author: sore_lion
 * @CreateDate: 2/20/21 4:50 PM
 */
public class SlideUnlockView extends View {

    private Paint mPaint, backgroundLinePaint, textPaint;
    /**
     * 结束点坐标
     */
    private float endValue;

    /**
     *
     */
    private int minEndValue;

    public SlideUnlockView(Context context) {
        super(context);
        init();
    }

    public SlideUnlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideUnlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isEffective = false;

        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color));

        backgroundLinePaint = new Paint();
        backgroundLinePaint.setDither(true);
        backgroundLinePaint.setAntiAlias(true);
        backgroundLinePaint.setStyle(Paint.Style.STROKE);
        backgroundLinePaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 2));
        backgroundLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color));

        textPaint = new Paint();
        textPaint.setDither(true);
        textPaint.setAntiAlias(true);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color));
        textPaint.setTextSize(AppUtils.spToPx(getContext(), 12));
        textPaint.setTextAlign(Paint.Align.CENTER);

        minEndValue = AppUtils.dipToPx(getContext(), 49);
        endValue = minEndValue;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int padding = AppUtils.dipToPx(getContext(), 1);
        RectF rectF = new RectF(padding, padding, getWidth() - padding, getHeight() - padding);
        int rx = AppUtils.dipToPx(getContext(), 19);
        canvas.drawRoundRect(rectF, rx, rx, backgroundLinePaint);
        RectF valueRect = new RectF(padding, padding, endValue, getHeight() - padding);
        canvas.drawRoundRect(valueRect, rx, rx, mPaint);
        RectF rect = new RectF(endValue - AppUtils.dipToPx(getContext(), 33), AppUtils.dipToPx(getContext(), 11), endValue - AppUtils.dipToPx(getContext(), 15), getHeight() - AppUtils.dipToPx(getContext(), 11));
        canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.slide_unlock), null, rect, null);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        RectF textRect = new RectF(0, 0, getWidth(), getHeight());
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = textRect.centerY() + distance;
        canvas.drawText(getContext().getString(R.string.slide_right_unlock), textRect.centerX(), baseline, textPaint);
    }

    private boolean isEffective;
    private float lastPointX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                float downY = event.getY();
                float start = endValue - AppUtils.dipToPx(getContext(), 49);
                float end = endValue;
                float top = 0;
                float bottom = getHeight();
                if (downX >= start && downX <= end && downY >= top && downY <= bottom) {
                    mHandler.removeMessages(0);
                    isEffective = true;
                    lastPointX = downX;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isEffective) {
                    float endX = event.getX();
                    float dx = endX - lastPointX;
                    endValue = endValue + dx;
                    if (endValue <= minEndValue) {
                        endValue = minEndValue;
                    } else if (endValue >= getWidth()) {
                        endValue = minEndValue;
                        if (mSlideUnlockViewCallback != null) {
                            mSlideUnlockViewCallback.unlock();
                        }
                    }
                    lastPointX = endX;
                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                isEffective = false;
                if (endValue < getWidth()) {
                    mHandler.sendEmptyMessage(0);
                }
            default:
        }
        return true;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            endValue = endValue - AppUtils.dipToPx(getContext(), 80);
            if (endValue <= minEndValue) {
                endValue = minEndValue;
            } else {
                mHandler.sendEmptyMessageDelayed(0, 100);
            }
            postInvalidate();
        }
    };

    public interface SlideUnlockViewCallback {
        void unlock();
    }

    private SlideUnlockViewCallback mSlideUnlockViewCallback;

    public void setSlideUnlockViewCallback(SlideUnlockViewCallback slideUnlockViewCallback) {
        mSlideUnlockViewCallback = slideUnlockViewCallback;
    }
}


