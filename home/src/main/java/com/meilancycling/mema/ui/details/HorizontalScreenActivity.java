package com.meilancycling.mema.ui.details;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.meilancycling.mema.R;

import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.customview.ChartDetailsView;
import com.meilancycling.mema.databinding.ActivityHorizontalScreenBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 横屏列表
 *
 * @author sorelion
 */
public class HorizontalScreenActivity extends BaseActivity implements View.OnClickListener, OnChartValueSelectedListener, OnChartGestureListener {
    public static int LINE_CHART_TYPE;
    private ActivityHorizontalScreenBinding mActivityHorizontalScreenBinding;
    private YAxis mAxisLeft;
    private UserInfoEntity userInfoEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivityHorizontalScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_horizontal_screen);
        initLineChart();
        showUi();
        mActivityHorizontalScreenBinding.viewBack.setOnClickListener(this);
        mActivityHorizontalScreenBinding.viewSelect.setOnClickListener(this);
        isShow = false;
        mActivityHorizontalScreenBinding.llSpeed.setOnClickListener(this);
        mActivityHorizontalScreenBinding.llCadence.setOnClickListener(this);
        mActivityHorizontalScreenBinding.llAltitude.setOnClickListener(this);
        mActivityHorizontalScreenBinding.llHr.setOnClickListener(this);
        mActivityHorizontalScreenBinding.llPower.setOnClickListener(this);
        mActivityHorizontalScreenBinding.llTemperature.setOnClickListener(this);
        userInfoEntity = getUserInfoEntity();
        if (((userInfoEntity.getGuideFlag() & 64) >> 6) == Config.NEED_GUIDE) {
            mActivityHorizontalScreenBinding.groupGuide.setVisibility(View.VISIBLE);
        }
        mActivityHorizontalScreenBinding.guideBg.setOnClickListener(this);
    }

    /**
     * 显示界面
     */
    @SuppressLint("SetTextI18n")
    private void showUi() {
        switch (LINE_CHART_TYPE) {
            case ChartDetailsView.CHART_SPEED:
                if (Config.unit == Unit.METRIC.value) {
                    mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.speed) + "(" + getString(R.string.unit_kmh) + ")");
                } else {
                    mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.speed) + "(" + getString(R.string.unit_mph) + ")");
                }
                mActivityHorizontalScreenBinding.tvItem1.setText(getString(R.string.maximum) + ChartDetailsFragment.maxSpeed);
                mActivityHorizontalScreenBinding.tvItem2.setText(getString(R.string.average) + ChartDetailsFragment.limitSpeed);
                showLineChart(ChartDetailsFragment.speedChart, ContextCompat.getDrawable(this, R.drawable.fade_speed), ChartDetailsFragment.limitSpeed, Color.parseColor("#FF5E9D24")
                        , ChartDetailsFragment.minSpeedDiff * 5 + ChartDetailsFragment.minSpeed, ChartDetailsFragment.minSpeed);
                break;
            case ChartDetailsView.CHART_CADENCE:
                mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.cadence) + "(" + getString(R.string.cadence_unit) + ")");
                mActivityHorizontalScreenBinding.tvItem1.setText(getString(R.string.maximum) + ChartDetailsFragment.maxCadence);
                mActivityHorizontalScreenBinding.tvItem2.setText(getString(R.string.average) + ChartDetailsFragment.limitCadence);
                showLineChart(ChartDetailsFragment.cadenceChart, ContextCompat.getDrawable(this, R.drawable.fade_cadence), ChartDetailsFragment.limitCadence, Color.parseColor("#FFEE8D1F")
                        , ChartDetailsFragment.minCadenceDiff * 5 + ChartDetailsFragment.minCadence, ChartDetailsFragment.minCadence);
                break;
            case ChartDetailsView.CHART_HEART_RATE:
                mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.heart_rate) + "(" + getString(R.string.heart_unit) + ")");
                mActivityHorizontalScreenBinding.tvItem1.setText(getString(R.string.maximum) + ChartDetailsFragment.maxHeartRate);
                mActivityHorizontalScreenBinding.tvItem2.setText(getString(R.string.average) + ChartDetailsFragment.limitHeartRate);
                showLineChart(ChartDetailsFragment.heartRateChart, ContextCompat.getDrawable(this, R.drawable.fade_hr), ChartDetailsFragment.limitHeartRate, Color.parseColor("#FFB71010")
                        , ChartDetailsFragment.minHeartRateDiff * 5 + ChartDetailsFragment.minHeartRate, ChartDetailsFragment.minHeartRate);
                break;
            case ChartDetailsView.CHART_POWER:
                mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.power) + "(" + getString(R.string.unit_w) + ")");
                mActivityHorizontalScreenBinding.tvItem1.setText(getString(R.string.maximum) + ChartDetailsFragment.maxPower);
                mActivityHorizontalScreenBinding.tvItem2.setText(getString(R.string.average) + ChartDetailsFragment.limitPower);
                showLineChart(ChartDetailsFragment.powerChart, ContextCompat.getDrawable(this, R.drawable.fade_power), ChartDetailsFragment.limitPower, Color.parseColor("#FF589FC5")
                        , ChartDetailsFragment.minPowerDiff * 5 + ChartDetailsFragment.minPower, ChartDetailsFragment.minPower);
                break;
            case ChartDetailsView.CHART_ALTITUDE:
                if (Config.unit == Unit.METRIC.value) {
                    mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.altitude) + "(" + getString(R.string.unit_m) + ")");
                } else {
                    mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.altitude) + "(" + getString(R.string.unit_feet) + ")");
                }
                mActivityHorizontalScreenBinding.tvItem1.setText(getString(R.string.maximum) + ChartDetailsFragment.maxAltitude);
                mActivityHorizontalScreenBinding.tvItem2.setText(getString(R.string.minimum) + ChartDetailsFragment.limitAltitude);
                showLineChart(ChartDetailsFragment.altitudeChart, ContextCompat.getDrawable(this, R.drawable.fade_elevation), ChartDetailsFragment.limitAltitude, Color.parseColor("#FF798A52")
                        , ChartDetailsFragment.minAltitudeDiff * 5 + ChartDetailsFragment.minAltitude, ChartDetailsFragment.minAltitude);
                break;
            case ChartDetailsView.CHART_TEMPERATURE:
                if (Config.unit == Unit.METRIC.value) {
                    mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.temperature) + "(" + getString(R.string.unit_c) + ")");
                } else {
                    mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.temperature) + "(" + getString(R.string.unit_f) + ")");
                }
                mActivityHorizontalScreenBinding.tvItem1.setText(getString(R.string.maximum) + ChartDetailsFragment.maxTemperature);
                mActivityHorizontalScreenBinding.tvItem2.setText(getString(R.string.average) + ChartDetailsFragment.limitTemperature);
                showLineChart(ChartDetailsFragment.temperatureChart, ContextCompat.getDrawable(this, R.drawable.fade_temperature), ChartDetailsFragment.limitTemperature, Color.parseColor("#FF84536A")
                        , ChartDetailsFragment.minTemperatureDiff * 5 + ChartDetailsFragment.minTemperature, ChartDetailsFragment.minTemperature);
                break;
            default:
        }
        mActivityHorizontalScreenBinding.lineChart.setOnChartGestureListener(this);
    }

    /**
     * 图表基本设置
     */
    private void initLineChart() {
        mActivityHorizontalScreenBinding.lineChart.setVisibleXRangeMinimum(6);
        mActivityHorizontalScreenBinding.lineChart.setDrawGridBackground(false);
        mActivityHorizontalScreenBinding.lineChart.getLegend().setEnabled(false);
        mActivityHorizontalScreenBinding.lineChart.getDescription().setEnabled(false);
        mActivityHorizontalScreenBinding.lineChart.getAxisRight().setEnabled(false);
        mActivityHorizontalScreenBinding.lineChart.setDrawBorders(false);
        mActivityHorizontalScreenBinding.lineChart.setScaleYEnabled(false);
        mAxisLeft = mActivityHorizontalScreenBinding.lineChart.getAxisLeft();
        mAxisLeft.setTextColor(ContextCompat.getColor(this, R.color.black_9));
        mAxisLeft.setTextSize(8);
        mAxisLeft.setDrawAxisLine(false);
        mAxisLeft.setDrawGridLines(false);
        mAxisLeft.setXOffset(10);
        XAxis xAxis = mActivityHorizontalScreenBinding.lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineColor(Color.parseColor("#FFD0D0D0"));
        xAxis.setTextColor(ContextCompat.getColor(this, R.color.black_6));
        xAxis.setTextSize(8);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return UnitConversionUtil.getUnitConversionUtil().timeToHMS((int) value);
            }
        });
    }

    /**
     * 展示图表
     */
    private void showLineChart(List<List<Long>> chartValue, Drawable drawable, float limit, int color, float max, float min) {
        mActivityHorizontalScreenBinding.lineChart.clear();
        YAxis leftAxis = mActivityHorizontalScreenBinding.lineChart.getAxisLeft();
        leftAxis.setLabelCount(6, false);
        leftAxis.removeAllLimitLines();

        LimitLine limitLine = new LimitLine(limit);
        //设置限制线的宽
        limitLine.setLineWidth(0.5f);
        //设置限制线的颜色
        limitLine.setLineColor(ContextCompat.getColor(this, R.color.black_6));
        //设置基线的位置
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        //设置限制线为虚线
        limitLine.enableDashedLine(20f, 5f, 0f);
        leftAxis.addLimitLine(limitLine);

        if (mActivityHorizontalScreenBinding.lineChart.getData() != null && mActivityHorizontalScreenBinding.lineChart.getData().getDataSetCount() > 0) {
            mActivityHorizontalScreenBinding.lineChart.getData().removeDataSet(0);
        }
        leftAxis.setSpaceBottom(0);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int data = Math.round(value);
                DecimalFormat df = new DecimalFormat("0.0");
                String format = df.format(value);
                if (value == data) {
                    return String.valueOf(data);
                } else {
                    return format;
                }
            }
        });
        leftAxis.setAxisMinimum(min);
        leftAxis.setAxisMaximum(max);
        leftAxis.setLabelCount(6, true);
        Long aLong = chartValue.get(0).get(0);
        List<Entry> entries = new ArrayList<>();
        if (LINE_CHART_TYPE == ChartDetailsView.CHART_SPEED) {
            for (List<Long> datum : chartValue) {
                Entry entry = new Entry(datum.get(0) - aLong, speedData(datum.get(1)));
                entries.add(entry);
            }
        } else if (LINE_CHART_TYPE == ChartDetailsView.CHART_ALTITUDE) {
            for (List<Long> datum : chartValue) {
                Entry entry = new Entry(datum.get(0) - aLong, altitudeData(datum.get(1)));
                entries.add(entry);
            }
        } else if (LINE_CHART_TYPE == ChartDetailsView.CHART_TEMPERATURE) {
            for (List<Long> datum : chartValue) {
                Entry entry = new Entry(datum.get(0) - aLong, temperatureData(datum.get(1)));
                entries.add(entry);
            }
        } else {
            for (List<Long> datum : chartValue) {
                Entry entry = new Entry(datum.get(0) - aLong, datum.get(1));
                entries.add(entry);
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setDrawIcons(false);
        lineDataSet.setLineWidth(0.2f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(color);
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(5.f);
        lineDataSet.setHighlightEnabled(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        mActivityHorizontalScreenBinding.lineChart.setData(data);
        if (mActivityHorizontalScreenBinding.lineChart.getData() != null && mActivityHorizontalScreenBinding.lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) mActivityHorizontalScreenBinding.lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            mActivityHorizontalScreenBinding.lineChart.invalidate();
        }
    }

    /**
     * 速度数据
     */
    private float speedData(double value) {
        DecimalFormat df = new DecimalFormat("0.0");
        if (Config.unit == Unit.METRIC.value) {
            double data = value / 10;
            String format = df.format(data);
            return AppUtils.stringToFloat(format.replace(",", "."));
        } else {
            return AppUtils.stringToFloat(df.format(AppUtils.multiplyDouble(value, 0.6213712) / 10));
        }
    }

    /**
     * 海拔数据
     */
    private int altitudeData(long value) {
        int data;
        if (Config.unit == Unit.METRIC.value) {
            data = (int) (value);
        } else {
            data = (int) Math.round(AppUtils.multiplyDouble(value, 3.2808399));
        }
        return data;
    }

    /**
     * 温度
     */
    private int temperatureData(long value) {
        int data;
        if (Config.unit == Unit.METRIC.value) {
            data = (int) (value);
        } else {
            data = (int) Math.round((double) value * 9 / 5 + 32);
        }
        return data;
    }

    private boolean isShow;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                if (isShow) {
                    isShow = false;
                    mActivityHorizontalScreenBinding.svSelect.setVisibility(View.GONE);
                    showUi();
                } else {
                    finish();
                }
                break;
            case R.id.view_select:
                if (!isShow) {
                    isShow = true;
                    mActivityHorizontalScreenBinding.svSelect.setVisibility(View.VISIBLE);
                    mActivityHorizontalScreenBinding.tvTitle.setText(getString(R.string.switch_list));
                    if (ChartDetailsFragment.speedChart.size() < 4) {
                        mActivityHorizontalScreenBinding.llSpeed.setVisibility(View.GONE);
                        mActivityHorizontalScreenBinding.viewSpeed.setVisibility(View.GONE);
                    }
                    if (ChartDetailsFragment.cadenceChart.size() < 4) {
                        mActivityHorizontalScreenBinding.llCadence.setVisibility(View.GONE);
                        mActivityHorizontalScreenBinding.viewCadence.setVisibility(View.GONE);
                    }
                    if (ChartDetailsFragment.heartRateChart.size() < 4) {
                        mActivityHorizontalScreenBinding.llHr.setVisibility(View.GONE);
                        mActivityHorizontalScreenBinding.viewHr.setVisibility(View.GONE);
                    }
                    if (ChartDetailsFragment.powerChart.size() < 4) {
                        mActivityHorizontalScreenBinding.llPower.setVisibility(View.GONE);
                        mActivityHorizontalScreenBinding.viewPower.setVisibility(View.GONE);
                    }
                    if (ChartDetailsFragment.altitudeChart.size() < 4) {
                        mActivityHorizontalScreenBinding.llAltitude.setVisibility(View.GONE);
                        mActivityHorizontalScreenBinding.viewAltitude.setVisibility(View.GONE);
                    }
                    if (ChartDetailsFragment.temperatureChart.size() < 4) {
                        mActivityHorizontalScreenBinding.llTemperature.setVisibility(View.GONE);
                        mActivityHorizontalScreenBinding.viewTemperature.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.ll_speed:
                isShow = false;
                LINE_CHART_TYPE = ChartDetailsView.CHART_SPEED;
                mActivityHorizontalScreenBinding.svSelect.setVisibility(View.GONE);
                showUi();
                break;
            case R.id.ll_cadence:
                isShow = false;
                LINE_CHART_TYPE = ChartDetailsView.CHART_CADENCE;
                mActivityHorizontalScreenBinding.svSelect.setVisibility(View.GONE);
                showUi();
                break;
            case R.id.ll_altitude:
                isShow = false;
                LINE_CHART_TYPE = ChartDetailsView.CHART_ALTITUDE;
                mActivityHorizontalScreenBinding.svSelect.setVisibility(View.GONE);
                showUi();
                break;
            case R.id.ll_hr:
                isShow = false;
                LINE_CHART_TYPE = ChartDetailsView.CHART_HEART_RATE;
                mActivityHorizontalScreenBinding.svSelect.setVisibility(View.GONE);
                showUi();
                break;
            case R.id.ll_power:
                isShow = false;
                LINE_CHART_TYPE = ChartDetailsView.CHART_POWER;
                mActivityHorizontalScreenBinding.svSelect.setVisibility(View.GONE);
                showUi();
                break;
            case R.id.ll_temperature:
                isShow = false;
                LINE_CHART_TYPE = ChartDetailsView.CHART_TEMPERATURE;
                mActivityHorizontalScreenBinding.svSelect.setVisibility(View.GONE);
                showUi();
                break;
            case R.id.guide_bg:
                mActivityHorizontalScreenBinding.groupGuide.setVisibility(View.GONE);
                int guideFlag = userInfoEntity.getGuideFlag();
                userInfoEntity.setGuideFlag(guideFlag & 0xBF);
                updateUserInfoEntity(userInfoEntity);
                break;
            default:
        }
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        float visibleXRange = mActivityHorizontalScreenBinding.lineChart.getVisibleXRange();
        if (visibleXRange < 6) {
            mActivityHorizontalScreenBinding.lineChart.setVisibleXRangeMinimum(6);
            mActivityHorizontalScreenBinding.lineChart.postInvalidate();
        }
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}