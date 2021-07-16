package com.meilancycling.mema.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: 首页目标进度条
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class HomeTargetProgress extends View {
    private Paint mBackgroundPaint, mValuePaint, paint;
    private int mTotal;
    private int mCurrent;
    private int drawableId;

    public HomeTargetProgress(Context context) {
        super(context);
        init();
    }

    public HomeTargetProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeTargetProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 9));

        mValuePaint = new Paint();
        mValuePaint.setAntiAlias(true);
        mValuePaint.setDither(true);
        mValuePaint.setStyle(Paint.Style.STROKE);
        mValuePaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 15));
        mValuePaint.setStrokeCap(Paint.Cap.ROUND);

        paint = new Paint();
    }

    private PointF startF;
    private PointF circleCenter;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        int topPadding = AppUtils.dipToPx(getContext(), 14);
        int totalWidth = getHeight() - 2 * topPadding;
        int leftPadding = (getWidth() - totalWidth) / 2;
        int padding = AppUtils.dipToPx(getContext(), 7.5f);
        RectF rectF = new RectF(leftPadding + padding, topPadding + padding, leftPadding + totalWidth - padding, getHeight() - topPadding - padding);
        canvas.drawArc(rectF, -90, 360, false, mBackgroundPaint);
        float progress = ((float) mCurrent / mTotal) * 360;
        canvas.drawArc(rectF, -90, progress, false, mValuePaint);
        PointF centerPoint = getCenterPoint(rectF.centerX(), rectF.centerY(), progress, rectF.centerX() - padding - leftPadding);
        effectiveRectF = getRectF(centerPoint);
        canvas.drawBitmap(mBitmap, null, effectiveRectF, paint);
        startF = new PointF(rectF.centerX(), topPadding + padding);
        circleCenter = new PointF(rectF.centerX(), rectF.centerY());
    }

    /**
     * 获取中心点坐标
     */
    private PointF getCenterPoint(float centerX, float centerY, float angle, float radius) {
        float x = (float) (centerX + radius * Math.cos((angle - 90) * Math.PI / 180));
        float y = (float) (centerY + radius * Math.sin((angle - 90) * Math.PI / 180));
        return new PointF(x, y);
    }

    /**
     * 获取图片范围
     */
    private RectF getRectF(PointF pointF) {
        int radius = AppUtils.dipToPx(getContext(), 19.5f);
        return new RectF(pointF.x - radius, pointF.y - radius, pointF.x + radius, pointF.y + radius);
    }

    private int mType;

    public void setProgressData(int total, int current, int type) {
        mType = type;
        mTotal = total;
        mCurrent = current;
        switch (type) {
            case HomeScheduleView.DISTANCE:
                mBackgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color_bg));
                mValuePaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color));
                drawableId = R.drawable.distance_progress;
                break;
            case HomeScheduleView.TIME:
                mBackgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.blue_color_bg));
                mValuePaint.setColor(ContextCompat.getColor(getContext(), R.color.blue_color));
                drawableId = R.drawable.time_progress;
                break;
            case HomeScheduleView.KAL:
                mBackgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.orange_color_bg));
                mValuePaint.setColor(ContextCompat.getColor(getContext(), R.color.orange_color));
                drawableId = R.drawable.cal_progress;
                break;
            default:
        }
    }

    private boolean effective;
    /**
     * 有效范围
     */
    private RectF effectiveRectF;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                effective = event.getX() >= effectiveRectF.left
                        && event.getX() <= effectiveRectF.right
                        && event.getY() >= effectiveRectF.top
                        && event.getY() <= effectiveRectF.bottom;
                break;
            case MotionEvent.ACTION_MOVE:
                if (effective) {
                    countCurrent(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (effective) {
                    if (mProgressChange != null) {
                        mProgressChange.saveData(mCurrent);
                    }
                }
                break;
            default:
        }
        return true;
    }

    /**
     * 计算当前值
     */
    private void countCurrent(float x, float y) {
        float a_b_x = startF.x - circleCenter.x;
        float a_b_y = startF.y - circleCenter.y;
        float c_b_x = x - circleCenter.x;
        float c_b_y = y - circleCenter.y;
        double ab_mul_cb = a_b_x * c_b_x + a_b_y * c_b_y;
        double dist_ab = Math.sqrt(a_b_x * a_b_x + a_b_y * a_b_y);
        double dist_cd = Math.sqrt(c_b_x * c_b_x + c_b_y * c_b_y);
        double cosValue = ab_mul_cb / (dist_ab * dist_cd);
        double angle = Math.toDegrees(Math.acos(cosValue));
        if (x <= startF.x) {
            angle = 360 - angle;
        }
        mCurrent = (int) (angle / 360 * mTotal);
        if (mType == HomeScheduleView.DISTANCE) {
            int halfValue = 810;
            if (mCurrent <= halfValue) {
                mCurrent = mTotal;
            }
        } else {
            if (mCurrent == 0) {
                mCurrent = mTotal;
            }
        }
        if (mProgressChange != null) {
            mProgressChange.changeData(mCurrent);
        }
        postInvalidate();
    }

    public interface ProgressChange {
        /**
         * 数据改变
         *
         * @param value
         */
        void changeData(int value);

        /**
         * 保存数据
         *
         * @param result
         */
        void saveData(int result);

    }

    private ProgressChange mProgressChange;

    public void setProgressChange(ProgressChange progressChange) {
        mProgressChange = progressChange;
    }
}
