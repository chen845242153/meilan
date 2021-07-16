package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lihang.ShadowLayout;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionInfoResponse;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;

/**
 * @Description: 竞赛适配器
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.FavoritesHolder> {

    private List<MotionInfoResponse.RowsBean> adapterList;
    private Context mContext;

    public void setAdapterData(List<MotionInfoResponse.RowsBean> adapterData) {
        adapterList = adapterData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.itme_competition, parent, false);
        return new FavoritesHolder(view);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull FavoritesHolder holder, int position) {
        MotionInfoResponse.RowsBean rowsBean = adapterList.get(position);
        switch (rowsBean.getMotionType()) {
            case Config.SPORT_INDOOR:
                holder.type.setImageDrawable(mContext.getDrawable(R.drawable.item_indoor));
                break;
            case Config.SPORT_OUTDOOR:
                holder.type.setImageDrawable(mContext.getDrawable(R.drawable.item_outdoor));
                break;
            case Config.SPORT_COMPETITION:
                holder.type.setImageDrawable(mContext.getDrawable(R.drawable.item_competition));
                break;
            default:
                holder.type.setImageDrawable(null);
        }
        holder.time.setText(AppUtils.timeToString(rowsBean.getMotionDate(), Config.TIME_PATTERN));
        holder.title.setText(rowsBean.getMotionName());

        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, rowsBean.getDistance());
        holder.distance.setText(unitBean.getValue());
        holder.distanceUnit.setText(unitBean.getUnit());
        holder.during.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(rowsBean.getActiveTime()));
        UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(mContext, ((double) rowsBean.getAvgSpeed()) / 10);
        holder.speed.setText(speedSetting.getValue() + speedSetting.getUnit());
        holder.itemView.setOnClickListener(v -> DetailsHomeActivity.enterDetailsHome(mContext, adapterList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return adapterList == null ? 0 : adapterList.size();
    }

    static class FavoritesHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView title;
        ImageView type;
        TextView distance;
        TextView distanceUnit;
        TextView during;
        TextView speed;


        FavoritesHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_common_time);
            title = itemView.findViewById(R.id.tv_common_title);
            type = itemView.findViewById(R.id.iv_common_type);
            distance = itemView.findViewById(R.id.tv_common_distance);
            distanceUnit = itemView.findViewById(R.id.tv_common_distance_unit);
            during = itemView.findViewById(R.id.tv_common_during);
            speed = itemView.findViewById(R.id.tv_common_speed);
        }
    }
}
