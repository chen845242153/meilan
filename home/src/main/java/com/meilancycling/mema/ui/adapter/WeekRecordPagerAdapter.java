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
import com.meilancycling.mema.customview.WeekChartView;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/6/9 4:43 P
 * todo 每次都是在前面加数据 ，后面需要优化一下
 */
public class WeekRecordPagerAdapter extends RecyclerView.Adapter<WeekRecordPagerAdapter.RecordHolder> {
    private List<MotionSumResponse> mList = new ArrayList<>();
    private List<Long> startTimeList = new ArrayList<>();
    private List<Long> endTimeList = new ArrayList<>();
    private Context mContext;

    public WeekRecordPagerAdapter(Context context) {
        this.mContext = context;
    }

    public MotionSumResponse getData(int position) {
        if (mList.size() > position) {
            return mList.get(position);
        }
        return null;
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

    public void clearData() {
        mList.clear();
        startTimeList.clear();
        endTimeList.clear();
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_record_week, parent, false);
        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder holder, int position) {
        MotionSumResponse motionSumResponse = mList.get(position);
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, motionSumResponse.getSumDistance());
        String time = UnitConversionUtil.getUnitConversionUtil().timeFormat(motionSumResponse.getSumTime());
        holder.advWeek.setAggregateData(
                unitBean.getValue(),
                mContext.getString(R.string.distance) + "(" + unitBean.getUnit() + ")",
                time,
                String.valueOf(motionSumResponse.getSumKcal()),
                String.valueOf(motionSumResponse.getSumMotion()));
        holder.sevenView.setData(startTimeList.get(position), motionSumResponse.getMotionList());
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class RecordHolder extends RecyclerView.ViewHolder {
        WeekChartView sevenView;
        AggregateDataView advWeek;

        RecordHolder(View itemView) {
            super(itemView);
            sevenView = itemView.findViewById(R.id.scv_week);
            advWeek = itemView.findViewById(R.id.adv_week);
        }
    }
}
