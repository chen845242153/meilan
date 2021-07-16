package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lihang.ShadowLayout;
import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.ui.record.SelectMonthActivity;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;


/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class RecordYearAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MotionSumResponse.YearListDataBean> mList;
    private Context mContext;

    public RecordYearAdapter(Context context, List<MotionSumResponse.YearListDataBean> yearListDataBeans) {
        this.mList = yearListDataBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_record_year, parent, false);
        return new RecyclerHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MotionSumResponse.YearListDataBean yearListDataBean = mList.get(position);
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        recyclerHolder.month.setText(timeFormat(yearListDataBean.getMotionDate()));
        recyclerHolder.year.setText(String.valueOf(yearListDataBean.getMotionDate()).substring(0, 4));

        UnitBean distanceSetting = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, yearListDataBean.getDistance());
        recyclerHolder.distance.setText(distanceSetting.getValue() + distanceSetting.getUnit());
        recyclerHolder.calories.setText(yearListDataBean.getKcal() + mContext.getString(R.string.unit_cal));

        recyclerHolder.slItem.setOnClickListener(v -> SelectMonthActivity.enterSelectMonth(mContext,
                getStartTime(mList.get(position).getMotionDate()),
                getEndTime(mList.get(position).getMotionDate())));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private String getStartTime(int date) {
        String year = String.valueOf(date).substring(0, 4);
        String month = String.valueOf(date).substring(4, 6);
        return year + "-" + month + "-01";
    }

    private String getEndTime(int date) {
        String year = String.valueOf(date).substring(0, 4);
        String month = String.valueOf(date).substring(4, 6);
        String result = "";
        switch (Integer.parseInt(month)) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                result = year + "-" + month + "-31";
                break;
            case 2:
                if (Integer.parseInt(year) % 4 == 0) {
                    result = year + "-" + month + "-29";
                } else {
                    result = year + "-" + month + "-28";
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                result = year + "-" + month + "-30";
                break;
            default:
        }
        return result;
    }

    private String timeFormat(int date) {
        String month = String.valueOf(date).substring(4, 6);
        String result = "";
        switch (Integer.parseInt(month)) {
            case 1:
                result = mContext.getString(R.string.January);
                break;
            case 2:
                result = mContext.getString(R.string.February);
                break;
            case 3:
                result = mContext.getString(R.string.March);
                break;
            case 4:
                result = mContext.getString(R.string.April);
                break;
            case 5:
                result = mContext.getString(R.string.May);
                break;
            case 6:
                result = mContext.getString(R.string.June);
                break;
            case 7:
                result = mContext.getString(R.string.July);
                break;
            case 8:
                result = mContext.getString(R.string.August);
                break;
            case 9:
                result = mContext.getString(R.string.September);
                break;
            case 10:
                result = mContext.getString(R.string.October);
                break;
            case 11:
                result = mContext.getString(R.string.November);
                break;
            case 12:
                result = mContext.getString(R.string.December);
                break;
            default:
        }
        return result;
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView month;
        TextView year;
        TextView distance;
        TextView calories;
        ShadowLayout slItem;

        RecyclerHolder(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.tv_month);
            year = itemView.findViewById(R.id.tv_year);
            distance = itemView.findViewById(R.id.tv_year_distance);
            calories = itemView.findViewById(R.id.tv_year_cal);
            slItem = itemView.findViewById(R.id.sl_item);
        }
    }
}
