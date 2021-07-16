package com.meilancycling.mema.ui.device.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 距离选择
 *
 * @author sorelion qq 571135591
 */
public class DistanceDialog extends Dialog {
    private Context mContext;
    private int mValue;

    private List<String> item1List = new ArrayList<>();
    private List<String> item2List = new ArrayList<>();
    private List<String> max2List = new ArrayList<>();

    private WheelPicker mWpItem1;
    private WheelPicker mWpItem2;

    public DistanceDialog(Context context, int value) {
        super(context, R.style.dialog_style);
        mContext = context;
        mValue = value;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_distance);
        mWpItem1 = findViewById(R.id.wp_distance_item1);
        mWpItem2 = findViewById(R.id.wp_distance_item2);
        TextView confirm = findViewById(R.id.tv_distance_confirm);
        TextView unit = findViewById(R.id.tv_distance_unit);
        setCanceledOnTouchOutside(false);
        int item1Position = mValue / 1000;
        int item2Position = mValue - (item1Position * 1000);
        int maxItem;
        int maxItem1;
        int maxItem2;

        if (Config.unit == Unit.METRIC.value) {
            unit.setText("(" + mContext.getString(R.string.unit_km) + ")");
            maxItem = 999;
            maxItem1 = 999;
            maxItem2 = 999;
        } else {
            unit.setText("(" + mContext.getString(R.string.unit_mile) + ")");
            maxItem = 999;
            maxItem1 = 621;
            maxItem2 = 371;
        }

        for (int i = 0; i <= maxItem; i++) {
            if (i < 10) {
                item1List.add("00" + i);
                item2List.add("00" + i);
                max2List.add("00" + i);
            } else if (i < 100) {
                item1List.add("0" + i);
                item2List.add("0" + i);
                max2List.add("0" + i);
            } else {
                item2List.add(String.valueOf(i));
                if (i <= maxItem1) {
                    item1List.add(String.valueOf(i));
                }
                if (i <= maxItem2) {
                    max2List.add(String.valueOf(i));
                }
            }
        }
        mWpItem1.setData(item1List);
        mWpItem1.setIndicator(true);
        mWpItem1.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        mWpItem1.setIndicatorSize(AppUtils.dipToPx(mContext, 1));
        mWpItem1.setVisibleItemCount(3);
        mWpItem1.setAtmospheric(true);
        mWpItem1.post(() -> mWpItem1.setSelectedItemPosition(item1Position));
        mWpItem1.setOnWheelChangeListener(mOnWheelChangeListener);


        if (item1Position == item1List.size() - 1) {
            mWpItem2.setData(max2List);
        } else {
            mWpItem2.setData(item2List);
        }
        mWpItem2.setIndicator(true);
        mWpItem2.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        mWpItem2.setIndicatorSize(AppUtils.dipToPx(mContext, 1));
        mWpItem2.setVisibleItemCount(3);
        mWpItem2.setAtmospheric(true);
        mWpItem2.post(() -> mWpItem2.setSelectedItemPosition(item2Position));


        confirm.setOnClickListener(v -> {
            if (null != mDistanceDialogListener) {
                String data1 = (String) mWpItem1.getData().get(mWpItem1.getCurrentItemPosition());
                String data2 = (String) mWpItem2.getData().get(mWpItem2.getCurrentItemPosition());
                mDistanceDialogListener.distanceConfirm(Integer.parseInt(data1) * 1000 + Integer.parseInt(data2));
            }
            dismiss();
        });
    }

    private WheelPicker.OnWheelChangeListener mOnWheelChangeListener = new WheelPicker.OnWheelChangeListener() {
        @Override
        public void onWheelScrolled(int offset) {
        }

        @Override
        public void onWheelSelected(int position) {
            if (position == mWpItem1.getData().size() - 1) {
                mWpItem2.setData(max2List);
                if (mWpItem2.getCurrentItemPosition() >= max2List.size()) {
                    mWpItem2.post(() -> mWpItem2.setSelectedItemPosition(max2List.size() - 1));
                }
            } else {
                mWpItem2.setData(item2List);
            }
        }

        @Override
        public void onWheelScrollStateChanged(int state) {
        }
    };

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

    public interface DistanceDialogListener {
        /**
         * 距离选择
         *
         * @param distance 选择的目标
         */
        void distanceConfirm(int distance);
    }

    private DistanceDialogListener mDistanceDialogListener;

    public void setDistanceDialogListener(DistanceDialogListener distanceDialogListener) {
        mDistanceDialogListener = distanceDialogListener;
    }
}

