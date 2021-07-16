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
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.List;



/**
 * @Description: 公共条目适配器
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class ItemCommonDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MotionSumResponse.MotionListBean> mList;
    private Context mContext;

    public ItemCommonDataAdapter(Context context, List<MotionSumResponse.MotionListBean> list) {
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itme_common_data, parent, false);
        return new RecyclerHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder recyclerHolder = (RecyclerHolder) holder;
        MotionSumResponse.MotionListBean motionListBean = mList.get(position);
        switch (motionListBean.getMotionType()) {
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
        if ("0".equals(motionListBean.getTimeType())) {
            recyclerHolder.time.setText(AppUtils.timeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_PATTERN));
        } else {
            recyclerHolder.time.setText(AppUtils.zeroTimeToString(Long.parseLong(motionListBean.getMotionDate()), Config.TIME_PATTERN));
        }

        recyclerHolder.title.setText(motionListBean.getMotionName());

        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(mContext, motionListBean.getDistance());
        recyclerHolder.distance.setText(unitBean.getValue());
        recyclerHolder.distanceUnit.setText(unitBean.getUnit());
        recyclerHolder.during.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(motionListBean.getActiveTime()));
        UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(mContext, ((double) motionListBean.getAvgSpeed()) / 10);
        recyclerHolder.speed.setText(speedSetting.getValue() + speedSetting.getUnit());

        recyclerHolder.llCommon.setOnClickListener(v -> {
            MotionSumResponse.MotionListBean motion = mList.get(position);
            DetailsHomeActivity.enterDetailsHome(mContext,  motion.getId());
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView title;
        ImageView type;
        TextView distance;
        TextView distanceUnit;
        TextView during;
        TextView speed;
        ShadowLayout llCommon;

        RecyclerHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_common_time);
            title = itemView.findViewById(R.id.tv_common_title);
            type = itemView.findViewById(R.id.iv_common_type);
            distance = itemView.findViewById(R.id.tv_common_distance);
            distanceUnit = itemView.findViewById(R.id.tv_common_distance_unit);
            during = itemView.findViewById(R.id.tv_common_during);
            speed = itemView.findViewById(R.id.tv_common_speed);
            llCommon = itemView.findViewById(R.id.ll_common);
        }
    }
}
