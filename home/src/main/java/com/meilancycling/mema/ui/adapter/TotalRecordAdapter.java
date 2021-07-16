package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lihang.ShadowLayout;
import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionInfoResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class TotalRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MotionInfoResponse.RowsBean> mList;
    private Context mContext;

    public TotalRecordAdapter(Context context, List<MotionInfoResponse.RowsBean> list) {
        this.mList = list;
        this.mContext = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itme_total_record, parent, false);
        return new RecyclerHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        MotionInfoResponse.RowsBean rowsBean = mList.get(position);
        switch (rowsBean.getMotionType()) {
            case Config.SPORT_INDOOR:
                recyclerHolder.type.setImageDrawable(mContext.getDrawable(R.drawable.item_indoor));
                break;
            case Config.SPORT_OUTDOOR:
                recyclerHolder.type.setImageDrawable(mContext.getDrawable(R.drawable.item_outdoor));
                break;
            case Config.SPORT_COMPETITION:
                recyclerHolder.type.setImageDrawable(mContext.getDrawable(R.drawable.item_competition));
                break;
            default:
                recyclerHolder.type.setImageDrawable(null);
        }
        Calendar calendar = Calendar.getInstance();
        if (!"0".equals(rowsBean.getTimeType())) {
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        }
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(rowsBean.getMotionDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (position == 0) {
            recyclerHolder.lineTime.setVisibility(View.VISIBLE);
            if (year == currentYear) {
                recyclerHolder.year.setVisibility(View.GONE);
            } else {
                recyclerHolder.year.setVisibility(View.VISIBLE);
                recyclerHolder.year.setText(String.valueOf(year));
            }
            recyclerHolder.lineTime.setText(formatData(month) + "-" + formatData(day));
        } else {
            calendar.setTimeInMillis(mList.get(position - 1).getMotionDate());
            int year1 = calendar.get(Calendar.YEAR);
            int month1 = calendar.get(Calendar.MONTH) + 1;
            int day1 = calendar.get(Calendar.DAY_OF_MONTH);
            if (year1 == year) {
                recyclerHolder.year.setVisibility(View.GONE);
                if (month == month1 && day == day1) {
                    recyclerHolder.lineTime.setVisibility(View.GONE);
                } else {
                    recyclerHolder.lineTime.setVisibility(View.VISIBLE);
                    recyclerHolder.lineTime.setText(formatData(month) + "-" + formatData(day));
                }
            } else {
                recyclerHolder.year.setVisibility(View.VISIBLE);
                recyclerHolder.lineTime.setVisibility(View.VISIBLE);
                recyclerHolder.year.setText(String.valueOf(year));
                recyclerHolder.lineTime.setText(formatData(month) + "-" + formatData(day));
            }
        }
        recyclerHolder.title.setText(rowsBean.getMotionName());
        recyclerHolder.itemTime.setText(formatData(hour) + ":" + formatData(minute));
        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, rowsBean.getDistance());
        recyclerHolder.distance.setText(unitBean.getValue());
        recyclerHolder.distanceUnit.setText(unitBean.getUnit());
        recyclerHolder.duration.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(rowsBean.getActiveTime()));
        UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(mContext, ((double) rowsBean.getAvgSpeed()) / 10);
        recyclerHolder.speed.setText(speedSetting.getValue() + speedSetting.getUnit());
        recyclerHolder.slRoot.setOnClickListener(v -> {
            if (position < mList.size()) {
                DetailsHomeActivity.enterDetailsHome(mContext, mList.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView year;
        TextView lineTime;
        ImageView type;
        TextView itemTime;
        TextView distance;
        TextView distanceUnit;
        TextView duration;
        TextView speed;
        LinearLayout content;
        TextView title;
        ShadowLayout slRoot;

        RecyclerHolder(View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.tv_total_year);
            lineTime = itemView.findViewById(R.id.tv_total_time);
            itemTime = itemView.findViewById(R.id.tv_time);
            type = itemView.findViewById(R.id.iv_item_type);
            distance = itemView.findViewById(R.id.tv_distance);
            distanceUnit = itemView.findViewById(R.id.tv_distance_unit);
            duration = itemView.findViewById(R.id.tv_item_time);
            speed = itemView.findViewById(R.id.tv_item_speed);
            content = itemView.findViewById(R.id.rl_content);
            title = itemView.findViewById(R.id.tv_title);
            slRoot = itemView.findViewById(R.id.sl_root);
        }
    }

    private String formatData(int data) {
        String defaultValue = "0";
        int limitData = 10;
        if (data < limitData) {
            return defaultValue + data;
        } else {
            return String.valueOf(data);
        }
    }
}
