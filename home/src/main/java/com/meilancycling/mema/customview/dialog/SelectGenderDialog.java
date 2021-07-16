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

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

/**
 * 性别选择
 *
 * @author sorelion qq 571135591
 */
public class SelectGenderDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    /**
     * 性别 1：男；2：女；
     */
    private int mPosition;
    private String result;

    public SelectGenderDialog(Context context, int gender) {
        super(context, R.style.dialog_style);
        mContext = context;
        if (gender == 1) {
            mPosition = 0;
        } else {
            mPosition = 1;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_gender);
        TextView cancel = findViewById(R.id.tv_gender_cancel);
        TextView finish = findViewById(R.id.tv_gender_finish);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        WheelPicker wheelPicker = findViewById(R.id.wp_select_gender);

        List<String> mData = new ArrayList<>();
        mData.add(mContext.getString(R.string.man));
        mData.add(mContext.getString(R.string.woman));

        wheelPicker.setData(mData);
        wheelPicker.setSelectedItemPosition(mPosition);
        wheelPicker.setVisibleItemCount(3);
        // 设置是否有幕布，设置后选中项会被指定的颜色覆盖，默认false
        wheelPicker.setCurtain(false);
        wheelPicker.setCurtainColor(Color.parseColor("#FFDCDCDC"));
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


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gender_cancel:
                dismiss();
                break;
            case R.id.tv_gender_finish:
                if (mSelectGenderClickListener != null) {
                    mSelectGenderClickListener.confirm(result, mPosition + 1);
                }
                dismiss();
                break;
            default:
        }
    }

    public interface SelectGenderClickListener {
        /**
         * 性别选择
         *
         * @param genderString 性别
         * @param gender       性别
         */
        void confirm(String genderString, int gender);
    }

    private SelectGenderClickListener mSelectGenderClickListener;

    public void setSelectGenderClickListener(SelectGenderClickListener selectGenderClickListener) {
        mSelectGenderClickListener = selectGenderClickListener;
    }
}

