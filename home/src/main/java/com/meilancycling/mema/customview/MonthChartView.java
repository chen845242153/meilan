package com.meilancycling.mema.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.ui.record.RecordHomeFragment;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ChartUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 记录顶部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class MonthChartView extends View {

    private Paint mLinePaint, verticalPaint, textPaint, verticalTextPaint, valueLinePaint, emptyPaint;

    private int lineWith;
    /**
     * 条目宽度
     */
    private float itemWidth;
    /**
     * 条目高度
     */
    private float itemHeight;
    /**
     * 列表横向数
     */
    private int numberX;
    /**
     * 列表竖向数
     */
    private final int numberY = 5;
    /**
     * y轴数据宽度
     */
    private int leftWidth;

    public MonthChartView(Context context) {
        super(context);
        init();
    }

    public MonthChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        leftWidth = AppUtils.dipToPx(getContext(), 48);
        lineWith = AppUtils.dipToPx(getContext(), 1);

        //横线
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.parseColor("#FFD0D0D0"));
        mLinePaint.setStrokeWidth(lineWith);
        //虚线
        verticalPaint = new Paint();
        verticalPaint.setAntiAlias(true);
        verticalPaint.setStyle(Paint.Style.STROKE);
        verticalPaint.setStrokeWidth(lineWith);
        verticalPaint.setColor(Color.parseColor("#FFE4E4E4"));
        PathEffect pathEffect = new DashPathEffect(new float[]{5, 5, 5, 5}, 2);
        verticalPaint.setPathEffect(pathEffect);
        linePath = new Path();
        //y轴值
        verticalTextPaint = new Paint();
        verticalTextPaint.setAntiAlias(true);
        verticalTextPaint.setColor(Color.parseColor("#FF444444"));
        verticalTextPaint.setTextSize(AppUtils.spToPx(getContext(), 9));
        verticalTextPaint.setStyle(Paint.Style.FILL);
        verticalTextPaint.setTextAlign(Paint.Align.CENTER);
        //x轴
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getContext().getResources().getColor(R.color.black_6));
        textPaint.setTextSize(AppUtils.spToPx(getContext(), 10));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        //绿色画笔
        valueLinePaint = new Paint();
        valueLinePaint.setAntiAlias(true);
        valueLinePaint.setColor(getContext().getResources().getColor(R.color.main_color));
        valueLinePaint.setStyle(Paint.Style.FILL);


        emptyPaint = new Paint();
        emptyPaint.setAntiAlias(true);
        emptyPaint.setColor(getContext().getResources().getColor(R.color.white));
        emptyPaint.setTextSize(AppUtils.spToPx(getContext(), 6));
        emptyPaint.setStyle(Paint.Style.FILL);
        emptyPaint.setTextAlign(Paint.Align.CENTER);
    }

    private Path linePath;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int totalWidth = getWidth() - AppUtils.dipToPx(getContext(), 80);
        float totalHeight = getHeight() - AppUtils.dipToPx(getContext(), 30);
        itemHeight = totalHeight / (numberY + 0.5f);
        totalHeight = itemHeight * numberY;
        itemWidth = (float) totalWidth / (numberX - 1);

        //x轴时间和圆点
        int paddingTop = AppUtils.dipToPx(getContext(), 6);

        if (isEmpty) {
            leftWidth = AppUtils.dipToPx(getContext(), 38);
            for (int i = 0; i < timeList.length; i++) {

                float pointX = leftWidth + i * itemWidth;
                if (i == 0 || i == 10 || i == 20 || i == timeList.length - 1) {
                    if (!TextUtils.isEmpty(timeList[i])) {
                        canvas.drawText(timeList[i], leftWidth + i * itemWidth, totalHeight + itemHeight * 0.5f + AppUtils.dipToPx(getContext(), 20), textPaint);
                    }
                    //canvas.drawCircle(pointX, totalHeight + itemHeight * 0.5f + paddingTop, AppUtils.dipToPx(getContext(), 2), valueLinePaint);
                } else {
                    // canvas.drawCircle(pointX, totalHeight + itemHeight * 0.5f + paddingTop, AppUtils.dipToPx(getContext(), 1.5f), valueLinePaint);
                }
            }
            int paddingLeft = AppUtils.dipToPx(getContext(), 24);
            int paddingRight = AppUtils.dipToPx(getContext(), 24);
            canvas.drawLine(paddingLeft, totalHeight + itemHeight * 0.5f, getWidth() - paddingRight, totalHeight + itemHeight * 0.5f, mLinePaint);
            verticalTextPaint.setTextSize(AppUtils.spToPx(getContext(), 11));
            Paint.FontMetrics fontMetrics = verticalTextPaint.getFontMetrics();
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            float baseline = rectF.centerY() + distance;
            canvas.drawText(getContext().getString(R.string.no_record), rectF.centerX(), baseline, verticalTextPaint);
        } else {
            leftWidth = AppUtils.dipToPx(getContext(), 48);

            for (int i = 0; i < timeList.length; i++) {
                float pointX = leftWidth + i * itemWidth;
                if (i == 0 || i == 10 || i == 20 ||i == timeList.length - 1) {
                    if (!TextUtils.isEmpty(timeList[i])) {
                        canvas.drawText(timeList[i], leftWidth + i * itemWidth, totalHeight + itemHeight * 0.5f + AppUtils.dipToPx(getContext(), 20), textPaint);
                    }
                    //x轴的绿点
//                    canvas.drawCircle(pointX, totalHeight + itemHeight * 0.5f + paddingTop, AppUtils.dipToPx(getContext(), 2), valueLinePaint);
                } else {
//                    canvas.drawCircle(pointX, totalHeight + itemHeight * 0.5f + paddingTop, AppUtils.dipToPx(getContext(), 1.5f), valueLinePaint);
                }
            }
            int paddingLeft = AppUtils.dipToPx(getContext(), 34);
            int paddingRight = AppUtils.dipToPx(getContext(), 15);
            canvas.drawLine(paddingLeft, totalHeight + itemHeight * 0.5f, getWidth() - paddingRight, totalHeight + itemHeight * 0.5f, mLinePaint);
            // y轴值
            for (int i = 0; i <= numberY; i++) {
                int padding = AppUtils.spToPx(getContext(), 5);
                canvasValueY(canvas, (i + 0.5f) * itemHeight - padding, (i + 0.5f) * itemHeight + padding, String.valueOf(diff * (numberY - i)));
            }
            // 虚线
            for (int i = 0; i < timeList.length; i++) {
                float pointX = leftWidth + i * itemWidth;
                linePath.reset();
                linePath.moveTo(pointX, 0);
                linePath.lineTo(pointX + lineWith, totalHeight + itemHeight * 0.5f);
                canvas.drawPath(linePath, verticalPaint);
            }
            //值
            for (int i = 0; i < dataList.size(); i++) {
                float pointX = leftWidth + i * itemWidth;
                float pointY = (float) ((1 - (dataList.get(i) / (diff * numberY))) * totalHeight + itemHeight * 0.5f);
                int padding = AppUtils.dipToPx(getContext(), 2.5f);
                RectF rectF = new RectF(pointX - padding, totalHeight + itemHeight * 0.5f, pointX + padding, pointY);
                if (dataList.get(i) / diff > 0.2f) {
                    int rx = AppUtils.dipToPx(getContext(), 55);
                    canvas.drawRoundRect(rectF, rx, rx, valueLinePaint);
                    RectF ovalRect = new RectF(pointX - padding, totalHeight + itemHeight * 0.3f, pointX + padding, totalHeight + itemHeight * 0.5f);
                    canvas.drawRect(ovalRect, valueLinePaint);
                } else {
                    canvas.drawRect(rectF, valueLinePaint);
                }
            }
        }
    }

    private void canvasValueY(Canvas canvas, float startY, float endY, String value) {
        Paint.FontMetrics fontMetrics = verticalTextPaint.getFontMetrics();
        RectF rectF = new RectF(0, startY, leftWidth, endY);
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = rectF.centerY() + distance;
        canvas.drawText(value, rectF.centerX(), baseline, verticalTextPaint);
    }

    /**
     * 间隔
     */
    private int diff;

    private String[] timeList;
    private List<Double> dataList;
    /**
     * 是否是空数据
     */
    private boolean isEmpty;

    /**
     * 设置数据
     */
    public void setData(long firstTime, List<MotionSumResponse.MotionListBean> motionList) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(firstTime);
        numberX = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        timeList = new String[numberX];
        dataList = new ArrayList<>(numberX);
        dataList.clear();
        for (int i = 0; i < numberX; i++) {
            timeList[i] = AppUtils.timeToString(firstTime + i * RecordHomeFragment.ONE_DAY, Config.TIME_RECORD_CHART);
            dataList.add(0.0);
        }
        if (motionList != null && motionList.size() > 0) {
            isEmpty = false;
            for (MotionSumResponse.MotionListBean motionListBean : motionList) {
                String time;
                if ("0".equals(motionListBean.getTimeType())) {
                    time = AppUtils.timeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_RECORD_CHART);
                } else {
                    time = AppUtils.zeroTimeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_RECORD_CHART);
                }
                for (int i = 0; i < timeList.length; i++) {
                    if (time.equals(timeList[i])) {
                        double value = dataList.get(i) + UnitConversionUtil.getUnitConversionUtil().formatDistance(motionListBean.getDistance());
                        dataList.set(i, value);
                    }
                }
            }
            diff = ChartUtils.getDifference(Collections.max(dataList));
        } else {
            isEmpty = true;
        }
        postInvalidate();

    }
}
