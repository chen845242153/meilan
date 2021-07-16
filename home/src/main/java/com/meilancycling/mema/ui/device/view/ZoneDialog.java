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
import com.meilancycling.mema.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 区间弹框
 *
 * @author sorelion qq 571135591
 */
public class ZoneDialog extends Dialog {
    private Context mContext;
    private int mPosition;
    private int mValue;
    private int mMax;
    private int mMin;
    private String mTitle;

    public ZoneDialog(Context context, int value, int max, int min, String title) {
        super(context, R.style.dialog_style);
        mContext = context;
        mValue = value;
        mMax = max;
        mMin = min;
        mTitle = title;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zone);
        TextView title = findViewById(R.id.tv_title);
        WheelPicker wheelPicker = findViewById(R.id.wp_value);
        TextView confirm = findViewById(R.id.tv_finish);
        List<String> mList = new ArrayList<>();
        mPosition = 0;
        for (int i = mMin; i <= mMax; i++) {
            mList.add(String.valueOf(i));
            if (i == mValue) {
                mPosition = i - mMin;
            }
        }
        wheelPicker.setData(mList);
        wheelPicker.setIndicator(true);
        wheelPicker.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        wheelPicker.setIndicatorSize(AppUtils.dipToPx(mContext, 1f));
        wheelPicker.setVisibleItemCount(3);

        wheelPicker.setAtmospheric(true);

        wheelPicker.post(() -> wheelPicker.setSelectedItemPosition(mPosition));

        title.setText(mTitle);

        confirm.setOnClickListener(v -> {
            if (null != mZoneDialogListener) {
                mZoneDialogListener.confirmListener(Integer.parseInt(mList.get(wheelPicker.getCurrentItemPosition())));
            }
            dismiss();
        });
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

    public interface ZoneDialogListener {
        /**
         * 确定按钮
         *
         * @param value 选择的值
         */
        void confirmListener(int value);
    }

    private ZoneDialogListener mZoneDialogListener;

    public void setZoneDialogListener(ZoneDialogListener zoneDialogListener) {
        mZoneDialogListener = zoneDialogListener;
    }
}

