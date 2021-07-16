package com.meilancycling.mema.customview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 单位设置
 *
 * @author sorelion qq 571135591
 */
public class SelectUnitDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private int mPosition;
    private String result;

    public SelectUnitDialog(Context context, int unit) {
        super(context, R.style.dialog_style);
        mContext = context;
        if (unit == Unit.METRIC.value) {
            mPosition = 0;
        } else {
            mPosition = 1;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_unit);
        TextView cancel = findViewById(R.id.tv_unit_cancel);
        TextView finish = findViewById(R.id.tv_unit_finish);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        WheelPicker wheelPicker = findViewById(R.id.wp_unit_gender);

        List<String> mData = new ArrayList<>();
        mData.add(mContext.getString(R.string.metric));
        mData.add(mContext.getString(R.string.imperial));

        wheelPicker.setData(mData);
        wheelPicker.setSelectedItemPosition(mPosition);
        wheelPicker.setVisibleItemCount(3);
        // 设置是否有幕布，设置后选中项会被指定的颜色覆盖，默认false
        wheelPicker.setCurtain(true);
        wheelPicker.setCurtainColor(Color.parseColor("#80D6D6D6"));
        // 设置是否有空气感，设置后上下边缘会渐变为透明，默认false
        wheelPicker.setAtmospheric(true);
        // 设置是否有卷曲感，不能微调卷曲幅度，默认false
        wheelPicker.setCurved(true);
        wheelPicker.post(() -> wheelPicker.setSelectedItemPosition(mPosition));
        result = mData.get(mPosition);
        wheelPicker.setOnItemSelectedListener((picker, data, position) -> {
            result = mData.get(position);
            mPosition = position;
        });
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unit_cancel:
                dismiss();
                break;
            case R.id.tv_unit_finish:
                if (mSelectUnitClickListener != null) {
                    mSelectUnitClickListener.confirm(result, mPosition);
                }
                dismiss();
                break;
            default:
        }
    }

    public interface SelectUnitClickListener {
        /**
         * 单位选择
         *
         * @param unitString 单位
         * @param unit       单位
         */
        void confirm(String unitString, int unit);
    }

    private SelectUnitClickListener mSelectUnitClickListener;

    public void setSelectUnitClickListener(SelectUnitClickListener selectUnitClickListener) {
        mSelectUnitClickListener = selectUnitClickListener;
    }
}

