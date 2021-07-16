package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.LapBean;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 3/26/21 11:35 AM
 */
public class LapDetailsAdapter extends RecyclerView.Adapter<LapDetailsAdapter.ViewHolder> {
    private List<LapBean> dataList;
    private Context mContext;

    public LapDetailsAdapter(List<LapBean> lapBeanList) {
        dataList = lapBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lap_details, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LapBean lapBean = dataList.get(position);
        holder.tv2.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(lapBean.getTotalTime()));
        holder.tv3.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(lapBean.getActivityTime()));
        UnitBean distanceSetting = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, lapBean.getDistance());
        holder.tv4.setText(distanceSetting.getValue() + distanceSetting.getUnit());
        UnitBean avgSpeed = UnitConversionUtil.getUnitConversionUtil().speedSetting(mContext, (double) lapBean.getAvgSpeed() / 10);
        holder.tv5.setText(avgSpeed.getValue() + avgSpeed.getUnit());
        UnitBean maxSpeed = UnitConversionUtil.getUnitConversionUtil().speedSetting(mContext, (double) lapBean.getMaxSpeed() / 10);
        holder.tv6.setText(maxSpeed.getValue() + maxSpeed.getUnit());
        if (lapBean.getAvgCadence() == null || lapBean.getAvgCadence() == 0) {
            holder.tv7.setText("--");
        } else {
            holder.tv7.setText(lapBean.getAvgCadence() + mContext.getString(R.string.cadence_unit));
        }
        if (lapBean.getMaxCadence() == null || lapBean.getMaxCadence() == 0) {
            holder.tv8.setText("--");
        } else {
            holder.tv8.setText(lapBean.getMaxCadence() + mContext.getString(R.string.cadence_unit));
        }
        if (lapBean.getMaxAltitude() != null) {
            UnitBean maxAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mContext, lapBean.getMaxAltitude());
            holder.tv9.setText(maxAltitude.getValue() + maxAltitude.getUnit());
        } else {
            holder.tv9.setText("--");
        }
        if (lapBean.getMinAltitude() != null) {
            UnitBean minAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mContext, lapBean.getMinAltitude());
            holder.tv10.setText(minAltitude.getValue() + minAltitude.getUnit());
        } else {
            holder.tv10.setText("--");
        }
        if (lapBean.getAscent() != null) {
            UnitBean ascentAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mContext, lapBean.getAscent());
            holder.tv11.setText(ascentAltitude.getValue() + ascentAltitude.getUnit());
        } else {
            holder.tv11.setText("--");
        }
        if (lapBean.getDescent() != null) {
            UnitBean descentAltitude = UnitConversionUtil.getUnitConversionUtil().altitudeSetting(mContext, lapBean.getDescent());
            holder.tv12.setText(descentAltitude.getValue() + descentAltitude.getUnit());
        } else {
            holder.tv12.setText("--");
        }
        if (lapBean.getAvgPower() == null || lapBean.getAvgPower() == 0) {
            holder.tv13.setText("--");
        } else {
            holder.tv13.setText(lapBean.getAvgPower() + mContext.getString(R.string.unit_w));
        }
        if (lapBean.getMaxCadence() == null || lapBean.getMaxCadence() == 0) {
            holder.tv14.setText("--");
        } else {
            holder.tv14.setText(lapBean.getMaxCadence() + mContext.getString(R.string.unit_w));
        }
        if (lapBean.getAvgHrm() == null || lapBean.getAvgHrm() == 0) {
            holder.tv15.setText("--");
        } else {
            holder.tv15.setText(lapBean.getAvgHrm() + mContext.getString(R.string.heart_unit));
        }
        if (lapBean.getMaxHrm() == null || lapBean.getMaxHrm() == 0) {
            holder.tv16.setText("--");
        } else {
            holder.tv16.setText(lapBean.getMaxHrm() + mContext.getString(R.string.heart_unit));
        }
        if (lapBean.getAvgTemperature() != null) {
            UnitBean avgTemperature = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(mContext, lapBean.getAvgTemperature());
            holder.tv17.setText(avgTemperature.getValue() + avgTemperature.getUnit());
        } else {
            holder.tv17.setText("--");
        }
        if (lapBean.getMaxTemperature() != null) {
            UnitBean maxTemperature = UnitConversionUtil.getUnitConversionUtil().temperatureSetting(mContext, lapBean.getMaxTemperature());
            holder.tv18.setText(maxTemperature.getValue() + maxTemperature.getUnit());
        } else {
            holder.tv18.setText("--");
        }
        if (lapBean.getTotalCalories() == null || lapBean.getTotalCalories() == 0) {
            holder.tv19.setText("--");
        } else {
            holder.tv19.setText(lapBean.getTotalCalories() + mContext.getString(R.string.unit_cal));
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
        TextView tv7;
        TextView tv8;
        TextView tv9;
        TextView tv10;
        TextView tv11;
        TextView tv12;
        TextView tv13;
        TextView tv14;
        TextView tv15;
        TextView tv16;
        TextView tv17;
        TextView tv18;
        TextView tv19;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv2 = itemView.findViewById(R.id.tv_2);
            tv3 = itemView.findViewById(R.id.tv_3);
            tv4 = itemView.findViewById(R.id.tv_4);
            tv5 = itemView.findViewById(R.id.tv_5);
            tv6 = itemView.findViewById(R.id.tv_6);
            tv7 = itemView.findViewById(R.id.tv_7);
            tv8 = itemView.findViewById(R.id.tv_8);
            tv9 = itemView.findViewById(R.id.tv_9);
            tv10 = itemView.findViewById(R.id.tv_10);
            tv11 = itemView.findViewById(R.id.tv_11);
            tv12 = itemView.findViewById(R.id.tv_12);
            tv13 = itemView.findViewById(R.id.tv_13);
            tv14 = itemView.findViewById(R.id.tv_14);
            tv15 = itemView.findViewById(R.id.tv_15);
            tv16 = itemView.findViewById(R.id.tv_16);
            tv17 = itemView.findViewById(R.id.tv_17);
            tv18 = itemView.findViewById(R.id.tv_18);
            tv19 = itemView.findViewById(R.id.tv_19);
        }
    }
}
