package com.meilancycling.mema.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ChartUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 首页图表
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class HomeChartView extends View implements GestureDetector.OnGestureListener {
    private Paint mBackgroundPaint, verticalPaint, mLinePaint, mDatePaint, valuePaint, valueLinePaint, mLinePaintX, mNumberPaint;
    private Path linePath;
    /**
     * 表格单条目高度
     */
    private float chartItemHeight;
    /**
     * 单位高度
     */
    private float unitHeight;
    /**
     * 手势识别器
     */
    private GestureDetector mGestureDetector;
    /**
     * 显示编号
     */
    private int showNumber;

    public HomeChartView(Context context) {
        super(context);
        init();
    }

    public HomeChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //背景画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setColor(Color.parseColor("#80F6FFEE"));
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        //y轴画笔
        verticalPaint = new Paint();
        verticalPaint.setAntiAlias(true);
        verticalPaint.setDither(true);
        verticalPaint.setColor(ContextCompat.getColor(getContext(), R.color.black_6));
        verticalPaint.setTextSize(AppUtils.spToPx(getContext(), 9));
        verticalPaint.setTextAlign(Paint.Align.LEFT);
        //虚线
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 1));
        mLinePaint.setColor(Color.parseColor("#FFE4E4E4"));
        PathEffect pathEffect = new DashPathEffect(new float[]{5, 5, 5, 5}, 2);
        mLinePaint.setPathEffect(pathEffect);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setDither(true);
        valuePaint.setStyle(Paint.Style.FILL);

        valueLinePaint = new Paint();
        valueLinePaint.setAntiAlias(true);
        valueLinePaint.setDither(true);
        valueLinePaint.setStyle(Paint.Style.FILL);
        valueLinePaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 1));
        valueLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.main_color));
        //日期
        mDatePaint = new Paint();
        mDatePaint.setAntiAlias(true);
        mDatePaint.setDither(true);
        mDatePaint.setColor(ContextCompat.getColor(getContext(), R.color.black_6));
        mDatePaint.setTextSize(AppUtils.spToPx(getContext(), 10));
        mDatePaint.setTextAlign(Paint.Align.CENTER);
        //x轴
        mLinePaintX = new Paint();
        mLinePaintX.setAntiAlias(true);
        mLinePaintX.setDither(true);
        mLinePaintX.setColor(Color.parseColor("#FFD0D0D0"));
        mLinePaintX.setStyle(Paint.Style.STROKE);
        mLinePaintX.setStrokeWidth(AppUtils.dipToPx(getContext(), 1));

        mNumberPaint = new Paint();
        mNumberPaint.setAntiAlias(true);
        mNumberPaint.setDither(true);
        mNumberPaint.setColor(ContextCompat.getColor(getContext(), R.color.black_9));
        mNumberPaint.setTextSize(AppUtils.spToPx(getContext(), 12));

        linePath = new Path();
        chartItemHeight = AppUtils.dipToPx(getContext(), 28);
        unitHeight = AppUtils.dipToPx(getContext(), 27);
        mGestureDetector = new GestureDetector(getContext(), this);
        showNumber = -1;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
//        RectF rectF = new RectF(0, 0, getWidth(), unitHeight + chartItemHeight * 5);
//        canvas.drawRect(rectF, mBackgroundPaint);
        //画y轴数值
        canvasYValue(canvas);
        //画虚线
        canvasLine(canvas);
        int left = AppUtils.dipToPx(getContext(), 34);
        int right = AppUtils.dipToPx(getContext(), 22);
        int totalWidth = getWidth() - left - right;
        int itemWidth = totalWidth / 6;
        int top = AppUtils.dipToPx(getContext(), 20);
        // 数据 日期
        canvasDataAndDate(canvas, left, itemWidth, totalWidth);
        if (dataList.size() == 7) {
            switch (showNumber) {
                case 0:
                    mDatePaint.setTextAlign(Paint.Align.LEFT);
                    if (dataList.get(0) == 0) {
                        canvas.drawText("0.00", left, top, mDatePaint);
                    } else {
                        canvas.drawText(String.valueOf(dataList.get(0)), left, top, mDatePaint);
                    }
                    break;
                case 1:
                    mDatePaint.setTextAlign(Paint.Align.CENTER);
                    if (dataList.get(1) == 0) {
                        canvas.drawText("0.00", left + itemWidth, top, mDatePaint);
                    } else {
                        canvas.drawText(String.valueOf(dataList.get(1)), left + itemWidth, top, mDatePaint);
                    }
                    break;
                case 2:
                    mDatePaint.setTextAlign(Paint.Align.CENTER);
                    if (dataList.get(2) == 0) {
                        canvas.drawText("0.00", left + 2 * itemWidth, top, mDatePaint);
                    } else {
                        canvas.drawText(String.valueOf(dataList.get(2)), left + 2 * itemWidth, top, mDatePaint);
                    }
                    break;
                case 3:
                    mDatePaint.setTextAlign(Paint.Align.CENTER);
                    if (dataList.get(3) == 0) {
                        canvas.drawText("0.00", left + 3 * itemWidth, top, mDatePaint);
                    } else {
                        canvas.drawText(String.valueOf(dataList.get(3)), left + 3 * itemWidth, top, mDatePaint);
                    }
                    break;
                case 4:
                    mDatePaint.setTextAlign(Paint.Align.CENTER);
                    if (dataList.get(4) == 0) {
                        canvas.drawText("0.00", left + 4 * itemWidth, top, mDatePaint);
                    } else {
                        canvas.drawText(String.valueOf(dataList.get(4)), left + 4 * itemWidth, top, mDatePaint);
                    }
                    break;
                case 5:
                    mDatePaint.setTextAlign(Paint.Align.CENTER);
                    if (dataList.get(5) == 0) {
                        canvas.drawText("0.00", left + 5 * itemWidth, top, mDatePaint);
                    } else {
                        canvas.drawText(String.valueOf(dataList.get(5)), left + 5 * itemWidth, top, mDatePaint);
                    }
                    break;
                case 6:
                    mDatePaint.setTextAlign(Paint.Align.RIGHT);
                    if (dataList.get(6) == 0) {
                        canvas.drawText("0.00", left + 6 * itemWidth, top, mDatePaint);
                    } else {
                        canvas.drawText(String.valueOf(dataList.get(6)), left + 6 * itemWidth, top, mDatePaint);
                    }
                    break;
                default:
            }
            mDatePaint.setTextAlign(Paint.Align.CENTER);
        }
    }

    /**
     * y轴值
     */
    private void canvasYValue(Canvas canvas) {
        String unitValue;
        if (Config.unit == Unit.METRIC.value) {
            unitValue = "(" + getContext().getString(R.string.unit_km) + ")";
        } else {
            unitValue = "(" + getContext().getString(R.string.unit_mile) + ")";
        }
        int left = AppUtils.dipToPx(getContext(), 8);
        RectF rectF = new RectF(left, 0, getWidth(), unitHeight);
        Paint.FontMetrics fontMetrics = verticalPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(unitValue, rectF.left, rectF.centerY() + distance, verticalPaint);
        rectF = new RectF(left, 0, getWidth(), 2 * unitHeight);
        canvas.drawText(String.valueOf(diff * 5), rectF.left, rectF.centerY() + distance, verticalPaint);
        rectF = new RectF(left, chartItemHeight, getWidth(), chartItemHeight + 2 * unitHeight);
        canvas.drawText(String.valueOf(diff * 4), rectF.left, rectF.centerY() + distance, verticalPaint);
        rectF = new RectF(left, 2 * chartItemHeight, getWidth(), 2 * chartItemHeight + 2 * unitHeight);
        canvas.drawText(String.valueOf(diff * 3), rectF.left, rectF.centerY() + distance, verticalPaint);
        rectF = new RectF(left, 3 * chartItemHeight, getWidth(), 3 * chartItemHeight + 2 * unitHeight);
        canvas.drawText(String.valueOf(diff * 2), rectF.left, rectF.centerY() + distance, verticalPaint);
        rectF = new RectF(left, 4 * chartItemHeight, getWidth(), 4 * chartItemHeight + 2 * unitHeight);
        canvas.drawText(String.valueOf(diff), rectF.left, rectF.centerY() + distance, verticalPaint);
        rectF = new RectF(left, 5 * chartItemHeight, getWidth(), 5 * chartItemHeight + 2 * unitHeight);
        canvas.drawText(String.valueOf(0), rectF.left, rectF.centerY() + distance, verticalPaint);
    }

    /**
     * 画线
     */
    private void canvasLine(Canvas canvas) {
        int left = AppUtils.dipToPx(getContext(), 34);
        int right = getWidth() - AppUtils.dipToPx(getContext(), 22);
        canvas.drawLine(left, unitHeight + chartItemHeight, right, unitHeight + chartItemHeight, mLinePaint);
        canvas.drawLine(left, unitHeight + 2 * chartItemHeight, right, unitHeight + 2 * chartItemHeight, mLinePaint);
        canvas.drawLine(left, unitHeight + 3 * chartItemHeight, right, unitHeight + 3 * chartItemHeight, mLinePaint);
        canvas.drawLine(left, unitHeight + 4 * chartItemHeight, right, unitHeight + 4 * chartItemHeight, mLinePaint);
        canvas.drawLine(left, unitHeight + 5 * chartItemHeight, right, unitHeight + 5 * chartItemHeight, mLinePaintX);
    }

    /**
     * 画日期和数据
     */
    private void canvasDataAndDate(Canvas canvas, int left, int itemWidth, int totalWidth) {
        float valueY = (getHeight() - unitHeight - 5 * chartItemHeight) / 2 + unitHeight + 5 * chartItemHeight;
        Paint.FontMetrics fontMetrics = mDatePaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        for (int i = 0; i < timeList.size(); i++) {
            canvas.drawText(timeList.get(i), left + i * itemWidth, valueY + distance, mDatePaint);
        }
        linePath.reset();
        linePath.moveTo(left, unitHeight + 5 * chartItemHeight);
        for (int i = 0; i < dataList.size(); i++) {
            double progress = dataList.get(i) / (diff * 5);
            double pointY = (1 - progress) * 5 * chartItemHeight + unitHeight;
            linePath.lineTo(left + i * itemWidth, (float) pointY);
            if (i > 0) {
                canvas.drawLine(left + (i - 1) * itemWidth, (float) (1 - dataList.get(i - 1) / (diff * 5)) * 5 * chartItemHeight + unitHeight, left + i * itemWidth, (float) pointY, valueLinePaint);
            }
        }
        linePath.lineTo(totalWidth + left, unitHeight + 5 * chartItemHeight);
        int[] colors = new int[]{Color.parseColor("#FFC3F397"), Color.parseColor("#FFF5FFE6")};
        Shader mShader = new LinearGradient(left, unitHeight, left, unitHeight + 5 * chartItemHeight, colors, null, Shader.TileMode.REPEAT);
        valuePaint.setShader(mShader);
        canvas.drawPath(linePath, valuePaint);
        for (int i = 0; i < dataList.size(); i++) {
            double progress = dataList.get(i) / (diff * 5);
            double pointY = (1 - progress) * 5 * chartItemHeight + unitHeight;
            canvas.drawCircle(left + i * itemWidth, (float) pointY, AppUtils.dipToPx(getContext(), 2.5f), valueLinePaint);
        }
    }

    /**
     * 间隔
     */
    private int diff;
    private List<String> timeList = new ArrayList<>();
    private List<Double> dataList = new ArrayList<>();
    private List<Integer> resultList = new ArrayList<>();

    /**
     * 设置数据
     */
    public void setData(List<MotionSumResponse.MotionListBean> mList) {
        int numberX = 7;
        long oneDay = 24 * 60 * 60 * 1000;
        timeList.clear();
        timeList.add(AppUtils.timeToString(System.currentTimeMillis() - 6 * oneDay, Config.TIME_RECORD_CHART));
        timeList.add(AppUtils.timeToString(System.currentTimeMillis() - 5 * oneDay, Config.TIME_RECORD_CHART));
        timeList.add(AppUtils.timeToString(System.currentTimeMillis() - 4 * oneDay, Config.TIME_RECORD_CHART));
        timeList.add(AppUtils.timeToString(System.currentTimeMillis() - 3 * oneDay, Config.TIME_RECORD_CHART));
        timeList.add(AppUtils.timeToString(System.currentTimeMillis() - 2 * oneDay, Config.TIME_RECORD_CHART));
        timeList.add(AppUtils.timeToString(System.currentTimeMillis() - oneDay, Config.TIME_RECORD_CHART));
        timeList.add(AppUtils.timeToString(System.currentTimeMillis(), Config.TIME_RECORD_CHART));
        dataList.clear();
        resultList.clear();
        for (int i = 0; i < numberX; i++) {
            resultList.add(0);
        }
        if (mList != null && mList.size() > 0) {
            for (MotionSumResponse.MotionListBean motionListBean : mList) {
                for (int i = 0; i < timeList.size(); i++) {
                    if ("0".equals(motionListBean.getTimeType())) {
                        String s = AppUtils.timeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_RECORD_CHART);
                        if (timeList.get(i).equals(AppUtils.timeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_RECORD_CHART))) {
                            Integer data = resultList.get(i);
                            resultList.set(i, data + motionListBean.getDistance());
                        }
                    } else {
                        String s = AppUtils.zeroTimeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_RECORD_CHART);
                        if (timeList.get(i).equals(AppUtils.zeroTimeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_RECORD_CHART))) {
                            Integer data = resultList.get(i);
                            resultList.set(i, data + motionListBean.getDistance());
                        }
                    }
                }
            }
            for (Integer integer : resultList) {
                UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(getContext(), integer);
                dataList.add(AppUtils.stringToDouble(unitBean.getValue()));
            }
            double max = Collections.max(dataList);
            diff = ChartUtils.getDistanceDifference(max);
        }
        postInvalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            showNumber = -1;
            postInvalidate();
        }
    };

    @Override
    public boolean onDown(MotionEvent e) {
        mHandler.removeMessages(0);
        int left = AppUtils.dipToPx(getContext(), 34);
        int right = AppUtils.dipToPx(getContext(), 22);
        int totalWidth = getWidth() - left - right;
        int itemWidth = totalWidth / 6;
        int halfItemWidth = totalWidth / 12;
        float x = e.getX();
        if (x <= left + halfItemWidth) {
            showNumber = 0;
        } else if (x <= left + itemWidth + halfItemWidth) {
            showNumber = 1;
        } else if (x <= left + 2 * itemWidth + halfItemWidth) {
            showNumber = 2;
        } else if (x <= left + 3 * itemWidth + halfItemWidth) {
            showNumber = 3;
        } else if (x <= left + 4 * itemWidth + halfItemWidth) {
            showNumber = 4;
        } else if (x <= left + 5 * itemWidth + halfItemWidth) {
            showNumber = 5;
        } else {
            showNumber = 6;
        }
        postInvalidate();
        mHandler.sendEmptyMessageDelayed(0, 500);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
