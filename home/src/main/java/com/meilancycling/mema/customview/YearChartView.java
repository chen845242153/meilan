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
import android.view.View;

import androidx.annotation.Nullable;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ChartUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @Description: 记录顶部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class YearChartView extends View {

    private Paint mLinePaint, verticalPaint, textPaint, verticalTextPaint, ValueLinePaint, emptyPaint;

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
    private final int numberX = 12;
    /**
     * 列表竖向数
     */
    private final int numberY = 5;
    /**
     * y轴数据宽度
     */
    private int leftWidth;

    public YearChartView(Context context) {
        super(context);
        init();
    }

    public YearChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YearChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        ValueLinePaint = new Paint();
        ValueLinePaint.setAntiAlias(true);
        ValueLinePaint.setColor(getContext().getResources().getColor(R.color.main_color));
        ValueLinePaint.setStyle(Paint.Style.FILL);

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

        if (isEmpty) {
            int paddingLeft = AppUtils.dipToPx(getContext(), 24);
            int paddingRight = AppUtils.dipToPx(getContext(), 24);
            canvas.drawLine(paddingLeft, totalHeight + itemHeight * 0.5f, getWidth() - paddingRight, totalHeight + itemHeight * 0.5f, mLinePaint);
            leftWidth = AppUtils.dipToPx(getContext(), 38);
            //x轴时间
            for (int i = 0; i < timeList.length; i++) {
                if (!TextUtils.isEmpty(timeList[i])) {
                    canvas.drawText(timeList[i], leftWidth + i * itemWidth, totalHeight + itemHeight * 0.5f + AppUtils.dipToPx(getContext(), 20), textPaint);
                }
            }
            verticalTextPaint.setTextSize(AppUtils.spToPx(getContext(), 11));
            Paint.FontMetrics fontMetrics = verticalTextPaint.getFontMetrics();
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            float baseline = rectF.centerY() + distance;
            canvas.drawText(getContext().getString(R.string.no_record), rectF.centerX(), baseline, verticalTextPaint);
        } else {
            leftWidth = AppUtils.dipToPx(getContext(), 48);
            int paddingLeft = AppUtils.dipToPx(getContext(), 34);
            int paddingRight = AppUtils.dipToPx(getContext(), 15);
            canvas.drawLine(paddingLeft, totalHeight + itemHeight * 0.5f, getWidth() - paddingRight, totalHeight + itemHeight * 0.5f, mLinePaint);
            //x轴时间
            for (int i = 0; i < timeList.length; i++) {
                if (!TextUtils.isEmpty(timeList[i])) {
                    canvas.drawText(timeList[i], leftWidth + i * itemWidth, totalHeight + itemHeight * 0.5f + AppUtils.dipToPx(getContext(), 20), textPaint);
                }
            }
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
                int padding = AppUtils.dipToPx(getContext(), 3);
                RectF rectF = new RectF(pointX - padding, totalHeight + itemHeight * 0.5f, pointX + padding, pointY);
                if (dataList.get(i) / diff > 0.2f) {
                    int rx = AppUtils.dipToPx(getContext(), 55);
                    canvas.drawRoundRect(rectF, rx, rx, ValueLinePaint);
                    RectF ovalRect = new RectF(pointX - padding, totalHeight + itemHeight * 0.3f, pointX + padding, totalHeight + itemHeight * 0.5f);
                    canvas.drawRect(ovalRect, ValueLinePaint);
                } else {
                    canvas.drawRect(rectF, ValueLinePaint);
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

    private String[] timeList = new String[numberX];
    private List<Double> dataList = new ArrayList<>(numberX);
    /**
     * 是否是空数据
     */
    private boolean isEmpty;

    /**
     * 设置数据
     */
    public void setData(long firstTime, List<MotionSumResponse.YearListDataBean> motionList) {
        dataList.clear();
        int month = Integer.parseInt( AppUtils.timeToString(firstTime, Config.TIME_RECORD_CHART_YEAR));
        int totalNum = 12;
        for (int i = 0; i < numberX; i++) {
            int current;
            if (month + i > totalNum) {
                current = month + i - totalNum;
            } else {
                current = month + i;
            }
            if (current > 9) {
                timeList[i] = String.valueOf(current);
            } else {
                timeList[i] = "0" + current;
            }
            dataList.add(0.0);
        }
        if (motionList != null && motionList.size() > 0) {
            isEmpty = false;
            for (MotionSumResponse.YearListDataBean motionListBean : motionList) {
                int currentMonth = motionListBean.getMotionDate() - (motionListBean.getMotionDate() / 100) * 100;
                for (int i = 0; i < timeList.length; i++) {
                    if (currentMonth==Integer.parseInt(timeList[i])) {
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
