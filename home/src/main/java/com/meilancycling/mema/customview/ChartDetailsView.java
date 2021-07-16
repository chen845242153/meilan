package com.meilancycling.mema.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.ui.details.ChartDetailsFragment;
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
public class ChartDetailsView extends View {
    /**
     * 列表横向数
     */
    private final int numberX = 4;
    /**
     * 列表竖向数
     */
    private final int numberY = 5;
    /**
     * 间隔
     */
    private int diff;
    /**
     * 最小值
     */
    private float minValue;
    /**
     * 最大值
     */
    private float maxValue;

    private Paint mLinePaint, valueYPaint, valueXPaint, unitPaint, valuePaint, valueBackgroundPaint, avgPaint;
    private int bottomPadding;
    private int leftPadding;
    private int rightPadding;


    public ChartDetailsView(Context context) {
        super(context);
        init();
    }

    public ChartDetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bottomPadding = AppUtils.dipToPx(getContext(), 45);
        leftPadding = AppUtils.dipToPx(getContext(), 37);
        rightPadding = AppUtils.dipToPx(getContext(), 30);
        //x轴
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.parseColor("#FFD0D0D0"));
        mLinePaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 1));

        //y轴值
        valueYPaint = new Paint();
        valueYPaint.setAntiAlias(true);
        valueYPaint.setColor(Color.parseColor("#FF999999"));
        valueYPaint.setStyle(Paint.Style.FILL);
        valueYPaint.setTextSize(AppUtils.spToPx(getContext(), 9));
        valueYPaint.setTextAlign(Paint.Align.CENTER);
        //x轴值
        valueXPaint = new Paint();
        valueXPaint.setAntiAlias(true);
        valueXPaint.setColor(Color.parseColor("#FF666666"));
        valueXPaint.setStyle(Paint.Style.FILL);
        valueXPaint.setTextSize(AppUtils.spToPx(getContext(), 10));
        valueXPaint.setTextAlign(Paint.Align.CENTER);
        //单位
        unitPaint = new Paint();
        unitPaint.setAntiAlias(true);
        unitPaint.setColor(Color.parseColor("#FF999999"));
        unitPaint.setStyle(Paint.Style.FILL);
        unitPaint.setTextSize(AppUtils.spToPx(getContext(), 9));
        unitPaint.setTextAlign(Paint.Align.CENTER);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL);
        valuePaint.setStrokeWidth(AppUtils.spToPx(getContext(), 2));

        valueBackgroundPaint = new Paint();
        valueBackgroundPaint.setAntiAlias(true);
        valueBackgroundPaint.setStyle(Paint.Style.FILL);

        avgPaint = new Paint();
        avgPaint.setAntiAlias(true);
        avgPaint.setStyle(Paint.Style.STROKE);
        avgPaint.setStrokeWidth(AppUtils.dipToPx(getContext(), 1));
        avgPaint.setColor(Color.parseColor("#FF666666"));
        PathEffect pathEffect = new DashPathEffect(new float[]{5, 5, 5, 5}, 2);
        avgPaint.setPathEffect(pathEffect);
        linePath = new Path();
    }

    private Path linePath;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() - bottomPadding;
        float itemHeight = (float) height / (numberY + 0.5f);
        int itemWidth = (getWidth() - leftPadding - rightPadding) / numberX;
        //y值
        for (int i = 0; i <= numberY; i++) {
            canvas.drawText(String.valueOf((int) ((numberY - i) * diff + minValue)), (float) leftPadding / 2, (0.5f + i) * itemHeight, valueYPaint);
        }
        //x轴
        canvas.drawLine(leftPadding, height, getWidth() - rightPadding, height, mLinePaint);
        int topPadding = AppUtils.dipToPx(getContext(), 19);
        for (int i = 0; i < listX.size(); i++) {
            canvas.drawText(listX.get(i), leftPadding + itemWidth * i, height + topPadding, valueXPaint);
        }
        /*
         * 渐变色路径
         */
        Path bgPath = new Path();
        bgPath.moveTo(leftPadding, height);
        for (int i = 1; i < mList.size(); i++) {
            PointF pointF = mList.get(i - 1);
            PointF currentPointF = mList.get(i);
            float startX = leftPadding + pointF.x / totalX * (getWidth() - leftPadding - rightPadding);
            float endX = leftPadding + currentPointF.x / totalX * (getWidth() - leftPadding - rightPadding);
            float startY = (1 - (pointF.y - minValue) / (maxValue - minValue)) * itemHeight * numberY + 0.5f * itemHeight;
            float endY = (1 - (currentPointF.y - minValue) / (maxValue - minValue)) * itemHeight * numberY + 0.5f * itemHeight;
            canvas.drawLine(startX, startY, endX, endY, valuePaint);
            bgPath.lineTo(startX, startY);
            if (i == mList.size() - 1) {
                bgPath.lineTo(endX, endY);
                bgPath.lineTo(endX, height);
            }
        }
        if (colors != null) {
            Shader mShader = new LinearGradient(leftPadding + (float) (getWidth() - leftPadding - rightPadding) / 2, height, leftPadding + (float) (getWidth() - leftPadding - rightPadding) / 2, 0.5f * itemHeight, colors, null, Shader.TileMode.REPEAT);
            valueBackgroundPaint.setShader(mShader);
            canvas.drawPath(bgPath, valueBackgroundPaint);
        }
        //平均值
        float avgValue = (1 - (lineData - minValue) / (maxValue - minValue)) * itemHeight * numberY + 0.5f * itemHeight;
        linePath.reset();
        linePath.moveTo(leftPadding, avgValue);
        linePath.lineTo(getWidth() - rightPadding, avgValue);
        canvas.drawPath(linePath, avgPaint);
    }

    public static final int CHART_SPEED = 0;
    public static final int CHART_CADENCE = 1;
    public static final int CHART_ALTITUDE = 2;
    public static final int CHART_HEART_RATE = 3;
    public static final int CHART_POWER = 4;
    public static final int CHART_TEMPERATURE = 5;

    private List<String> listX = new ArrayList<>();
    private float lineData;
    private List<PointF> mList = new ArrayList();
    private long totalX;
    private int[] colors;

    /**
     * 设置数据
     */
    public void setData(int type, List<List<Long>> data, int avgData, int maxData, int minData) {
        listX.clear();
        mList.clear();
        Long startTime = data.get(0).get(0);
        Long endTime = data.get(data.size() - 1).get(0);
        totalX = endTime - startTime;
        long timeDiff = (endTime - startTime) / numberX;
        listX.add(UnitConversionUtil.getUnitConversionUtil().timeToHMS(0));
        listX.add(UnitConversionUtil.getUnitConversionUtil().timeToHMS((int) timeDiff));
        listX.add(UnitConversionUtil.getUnitConversionUtil().timeToHMS((int) timeDiff * 2));
        listX.add(UnitConversionUtil.getUnitConversionUtil().timeToHMS((int) timeDiff * 3));
        listX.add(UnitConversionUtil.getUnitConversionUtil().timeToHMS((int) (endTime - startTime)));
        //公制
        switch (type) {
            case CHART_SPEED:
                List<Float> valueList = new ArrayList<>();
                UnitBean avgSpeed = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) avgData / 10);
                lineData = AppUtils.stringToFloat(avgSpeed.getValue());
                for (List<Long> datum : data) {
                    int valueX = (int) (datum.get(0) - startTime);
                    UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) datum.get(1) / 10);
                    mList.add(new PointF(valueX, AppUtils.stringToFloat(speedSetting.getValue())));
                    valueList.add(AppUtils.stringToFloat(speedSetting.getValue()));
                }
                minValue = 0;
                UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(getContext(), (double) maxData / 10);
                diff = ChartUtils.getDifference(AppUtils.stringToFloat(speedSetting.getValue()));
                valuePaint.setColor(Color.parseColor("#FF5E9D24"));
                colors = new int[]{Color.parseColor("#FFF1FFE4"), Color.parseColor("#FF7FD432")};
                maxValue = minValue + diff * numberY;
                ChartDetailsFragment.minSpeedDiff = diff;
                ChartDetailsFragment.minSpeed = minValue;
                break;
            case CHART_CADENCE:
                valueList = new ArrayList<>();
                lineData = avgData;
                for (List<Long> datum : data) {
                    int valueX = (int) (datum.get(0) - startTime);
                    mList.add(new PointF(valueX, datum.get(1)));
                    valueList.add(AppUtils.stringToFloat(String.valueOf(datum.get(1))));
                }
                diff = ChartUtils.getDifference(maxData);
                valuePaint.setColor(Color.parseColor("#FFEE8D1F"));
                colors = new int[]{Color.parseColor("#FFFFDEB9"), Color.parseColor("#FFF2A146")};
                minValue = 0;
                maxValue = minValue + diff * numberY;
                ChartDetailsFragment.minCadenceDiff = diff;
                ChartDetailsFragment.minCadence = minValue;
                break;
            case CHART_ALTITUDE:
                valueList = new ArrayList<>();
                UnitBean avgAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), avgData);
                lineData = AppUtils.stringToFloat(avgAltitude.getValue());
                for (List<Long> datum : data) {
                    int valueX = (int) (datum.get(0) - startTime);
                    UnitBean altitudeSetting = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), (double) datum.get(1));
                    mList.add(new PointF(valueX, AppUtils.stringToFloat(altitudeSetting.getValue())));
                    valueList.add(AppUtils.stringToFloat(altitudeSetting.getValue()));
                }
                maxData = Integer.parseInt(UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), maxData).getValue());
                minData = Integer.parseInt(UnitConversionUtil.getUnitConversionUtil().altitudeSetting(getContext(), minData).getValue());
                int[] diffData = ChartUtils.getAltitudeDifference(maxData, minData);
                diff = diffData[0];
                valuePaint.setColor(Color.parseColor("#FF798A52"));
                colors = new int[]{Color.parseColor("#FFF5FFDF"), Color.parseColor("#FFB9D27F")};
                minValue = diffData[1];
                maxValue = minValue + diff * numberY;
                ChartDetailsFragment.minAltitudeDiff = diff;
                ChartDetailsFragment.minAltitude = minValue;
                break;
            case CHART_HEART_RATE:
                valueList = new ArrayList<>();
                lineData = avgData;
                for (List<Long> datum : data) {
                    int valueX = (int) (datum.get(0) - startTime);
                    mList.add(new PointF(valueX, datum.get(1)));
                    valueList.add(AppUtils.stringToFloat(String.valueOf(datum.get(1))));
                }
                diff = ChartUtils.getDifference(maxData);
                valuePaint.setColor(Color.parseColor("#FFB71010"));
                colors = new int[]{Color.parseColor("#FFFFD2D2"), Color.parseColor("#FFF16A6A")};
                minValue = 0;
                maxValue = minValue + diff * numberY;
                ChartDetailsFragment.minHeartRateDiff = diff;
                ChartDetailsFragment.minHeartRate = minValue;
                break;
            case CHART_POWER:
                valueList = new ArrayList<>();
                lineData = avgData;
                for (List<Long> datum : data) {
                    int valueX = (int) (datum.get(0) - startTime);
                    mList.add(new PointF(valueX, datum.get(1)));
                    valueList.add(AppUtils.stringToFloat(String.valueOf(datum.get(1))));
                }
                diff = ChartUtils.getDifference(maxData);
                valuePaint.setColor(Color.parseColor("#FF589FC5"));
                colors = new int[]{Color.parseColor("#FFC3E6F8"), Color.parseColor("#FF75BBE1")};
                minValue = 0;
                maxValue = minValue + diff * numberY;
                ChartDetailsFragment.minPowerDiff = diff;
                ChartDetailsFragment.minPower = minValue;
                break;
            case CHART_TEMPERATURE:
                valueList = new ArrayList<>();
                UnitBean avgTemperature = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(getContext(), avgData);
                lineData = AppUtils.stringToFloat(avgTemperature.getValue());
                for (List<Long> datum : data) {
                    int valueX = (int) (datum.get(0) - startTime);
                    UnitBean temperatureSetting = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(getContext(), Integer.parseInt(String.valueOf(datum.get(1))));
                    mList.add(new PointF(valueX, AppUtils.stringToFloat(temperatureSetting.getValue())));
                    valueList.add(AppUtils.stringToFloat(temperatureSetting.getValue()));
                }
                minData = Math.round(Collections.min(valueList));
                maxData = Integer.parseInt(UnitConversionUtil.getUnitConversionUtil().temperatureSetting(getContext(), maxData).getValue());
                diffData = ChartUtils.getAltitudeDifference(maxData, minData);
                diff = diffData[0];
                valuePaint.setColor(Color.parseColor("#FF84536A"));
                colors = new int[]{Color.parseColor("#FFFFD4E8"), Color.parseColor("#FFD987AD")};
                minValue = diffData[1];
                maxValue = minValue + diff * numberY;
                ChartDetailsFragment.minTemperatureDiff = diff;
                ChartDetailsFragment.minTemperature = minValue;
                break;
            default:
        }
        postInvalidate();
    }
}
