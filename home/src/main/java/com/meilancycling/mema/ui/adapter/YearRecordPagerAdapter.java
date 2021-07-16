package com.meilancycling.mema.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.customview.AggregateDataView;
import com.meilancycling.mema.customview.YearChartView;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/6/9 4:43
 */
public class YearRecordPagerAdapter extends RecyclerView.Adapter<YearRecordPagerAdapter.RecordHolder> {
    private List<MotionSumResponse> mList = new ArrayList<>();
    private List<Long> startTimeList = new ArrayList<>();
    private List<Long> endTimeList = new ArrayList<>();
    private Context mContext;

    public YearRecordPagerAdapter(Context context) {
        this.mContext = context;
    }

    public MotionSumResponse getData(int position) {
        if (mList.size() > position) {
            return mList.get(position);
        }
        return null;
    }

    public void clearData() {
        mList.clear();
        startTimeList.clear();
        endTimeList.clear();
    }

    public long getStartTime(int position) {
        return startTimeList.get(position);
    }

    public long getEndTime(int position) {
        return endTimeList.get(position);
    }

    public void setData(int position, MotionSumResponse motionSumResponse, long startTime, long endTime) {
        mList.add(position, motionSumResponse);
        startTimeList.add(position, startTime);
        endTimeList.add(position, endTime);
    }

    public void setData(MotionSumResponse motionSumResponse, long startTime, long endTime) {
        mList.add(motionSumResponse);
        startTimeList.add(startTime);
        endTimeList.add(endTime);
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_record_year, parent, false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder holder, int position) {
        MotionSumResponse motionSumResponse = mList.get(position);
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, motionSumResponse.getSumDistance());
        String time = UnitConversionUtil.getUnitConversionUtil().timeFormat(motionSumResponse.getSumTime());
        holder.advYear.setAggregateData(
                unitBean.getValue(),
                mContext.getString(R.string.distance) + "(" + unitBean.getUnit() + ")",
                time,
                null,
                String.valueOf(motionSumResponse.getSumMotion()));
        holder.yearView.setData(startTimeList.get(position), motionSumResponse.getYearListData());

    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class RecordHolder extends RecyclerView.ViewHolder {
        YearChartView yearView;
        AggregateDataView advYear;


        RecordHolder(View itemView) {
            super(itemView);
            yearView = itemView.findViewById(R.id.scv_year);
            advYear = itemView.findViewById(R.id.adv_year);
        }
    }
}
