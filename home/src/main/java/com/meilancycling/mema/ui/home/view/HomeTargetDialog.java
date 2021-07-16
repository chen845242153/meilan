package com.meilancycling.mema.ui.home.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.greendao.UserInfoEntityDao;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.Objects;

/**
 * 首页目标弹窗
 *
 * @author sorelion qq 571135591
 */
public class HomeTargetDialog extends Dialog {
    private int mTotal;
    private int mType;
    private int mCurrent;
    private TextView ctv;
    private TextView title;
    private TextView unit;
    private UserInfoEntity userInfoEntity;

    public HomeTargetDialog(Context context, int type) {
        super(context, R.style.dialog_style);
        mType = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_home_target);
        setCanceledOnTouchOutside(false);
        long userId = SPUtils.getLong(getContext(), Config.CURRENT_USER);
        userInfoEntity = MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().queryBuilder()
                .where(UserInfoEntityDao.Properties.UserId.eq(userId))
                .unique();
        switch (mType) {
            case HomeScheduleView.DISTANCE:
                mTotal = Config.TOTAL_DISTANCE;
                mCurrent = userInfoEntity.getDistanceTarget();
                break;
            case HomeScheduleView.TIME:
                mTotal = Config.TOTAL_TIME;
                mCurrent = userInfoEntity.getTimeTarget();
                break;
            case HomeScheduleView.KAL:
                mTotal = Config.TOTAL_CAL;
                mCurrent = userInfoEntity.getCalTarget();
                break;
            default:
        }
        View close = findViewById(R.id.iv_close);
        HomeTargetProgress htp = findViewById(R.id.htp);
        ctv = findViewById(R.id.ctv);
        title = findViewById(R.id.tv_title);
        unit = findViewById(R.id.tv_unit);
        htp.setProgressData(mTotal, mCurrent, mType);
        showData();
        htp.setProgressChange(new HomeTargetProgress.ProgressChange() {
            @Override
            public void changeData(int result) {
                mCurrent = result;
                showData();
            }

            @Override
            public void saveData(int result) {
                switch (mType) {
                    case HomeScheduleView.DISTANCE:
                        userInfoEntity.setDistanceTarget(result);
                        break;
                    case HomeScheduleView.TIME:
                        userInfoEntity.setTimeTarget(result);
                        break;
                    case HomeScheduleView.KAL:
                        userInfoEntity.setCalTarget(result);
                        break;
                    default:
                }
                MyApplication.mInstance.getDaoSession().getUserInfoEntityDao().update(userInfoEntity);
            }
        });
        close.setOnClickListener(v -> dismiss());
    }

    /**
     * 显示数据
     */
    @SuppressLint("SetTextI18n")
    private void showData() {
        switch (mType) {
            case HomeScheduleView.DISTANCE:
                ctv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_main));
                int value = UnitConversionUtil.getUnitConversionUtil().distance2Int(mCurrent);
                ctv.setText(String.valueOf(value));
                title.setText(R.string.distance);
                if (Config.unit == Unit.METRIC.value) {
                    unit.setText("(" + getContext().getString(R.string.unit_km) + ")");
                } else {
                    unit.setText("(" + getContext().getString(R.string.unit_mile) + ")");
                }
                break;
            case HomeScheduleView.TIME:
                ctv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_blue));
                ctv.setText(String.valueOf(mCurrent));
                title.setText(R.string.time);
                unit.setText("(h)");
                break;
            case HomeScheduleView.KAL:
                ctv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_orange));
                ctv.setText(String.valueOf(mCurrent));
                title.setText(R.string.calories);
                unit.setText("(" + getContext().getString(R.string.unit_cal) + ")");
                break;
            default:
        }
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

}

