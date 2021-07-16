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
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionSumResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.ui.home.view.HomeChartView;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;
import java.util.List;

/**
 * @Description: 公共条目适配器
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class HomeDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private final int TOP = 0;
    private final int CONTENT = 1;
    private final int BOTTOM = 2;
    private List<MotionSumResponse.MotionListBean> mList;
    private int mCurrentDistance;
    private int mCurrentTime;
    private int mCurrentCal;
    private UserInfoEntity mUserInfoEntity;

    public HomeDataAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP;
        } else {
            if (mList == null || mList.size() == 0) {
                return BOTTOM;
            } else {
                return CONTENT;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TOP:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_top, parent, false);
                return new HomeTopHolder(view);
            case BOTTOM:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_empty, parent, false);
                return new HomeBottomHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.itme_common_data, parent, false);
                return new HomeDataHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TOP:
                HomeTopHolder homeTopHolder = (HomeTopHolder) holder;
                updateTopUi(homeTopHolder);
                break;
            case CONTENT:
                HomeDataHolder recyclerHolder = (HomeDataHolder) holder;
                updateContentUi(recyclerHolder, position);
                break;
            default:
        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void updateContentUi(HomeDataHolder recyclerHolder, int position) {
        MotionSumResponse.MotionListBean motionListBean = mList.get(position - 1);
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
            MotionSumResponse.MotionListBean motion = mList.get(position - 1);
            DetailsHomeActivity.enterDetailsHome(mContext, motion.getId());
        });
    }

    private void updateTopUi(HomeTopHolder homeTopHolder) {
        homeTopHolder.hc.setData(mList);
    }

    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 2;
        } else {
            return mList.size() + 1;
        }
    }

    static class HomeDataHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView title;
        ImageView type;
        TextView distance;
        TextView distanceUnit;
        TextView during;
        TextView speed;
        ShadowLayout llCommon;

        HomeDataHolder(View itemView) {
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

    static class HomeTopHolder extends RecyclerView.ViewHolder {
        HomeChartView hc;

        HomeTopHolder(View itemView) {
            super(itemView);
            hc = itemView.findViewById(R.id.hc_home);
        }
    }

    static class HomeBottomHolder extends RecyclerView.ViewHolder {
        HomeBottomHolder(View itemView) {
            super(itemView);
        }
    }

    public void setProgress(int currentDistance, int currentTime, int currentCal, UserInfoEntity userInfoEntity) {
        mUserInfoEntity = userInfoEntity;
        mCurrentDistance = currentDistance;
        mCurrentTime = currentTime;
        mCurrentCal = currentCal;
    }

    public void setAdapterData(List<MotionSumResponse.MotionListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void updateTop(int position) {
        if (mHomeUpdateTop != null) {
            mHomeUpdateTop.homeTop(position);
        }
    }

    public interface HomeUpdateTop {
        /**
         * 当前位置
         *
         * @param position 位置
         */
        void homeTop(int position);
    }

    private HomeUpdateTop mHomeUpdateTop;

    public void setHomeUpdateTop(HomeUpdateTop homeUpdateTop) {
        mHomeUpdateTop = homeUpdateTop;
    }
}
