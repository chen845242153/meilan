package com.meilancycling.mema.ui.device.view;

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
import com.meilancycling.mema.utils.AppUtils;

import java.util.List;
import java.util.Objects;

/**
 * 语言选择
 *
 * @author sorelion qq 571135591
 */
public class SelectLanguageDialog extends Dialog implements View.OnClickListener {
    private List<String> mList;
    private String mCurrent;
    private int mPosition;

    public SelectLanguageDialog(Context context, List<String> dataList, String current) {
        super(context, R.style.dialog_style);
        mList = dataList;
        mCurrent = current;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_language);
        TextView cancel = findViewById(R.id.tv_language_cancel);
        TextView finish = findViewById(R.id.tv_language_finish);
        cancel.setOnClickListener(this);
        finish.setOnClickListener(this);
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).equals(mCurrent)) {
                mPosition = i;
                break;
            }
        }

        setCanceledOnTouchOutside(false);
        WheelPicker wheelPicker = findViewById(R.id.wp_select_language);
        wheelPicker.setData(mList);
        wheelPicker.setSelectedItemPosition(mPosition);
        wheelPicker.setVisibleItemCount(5);

        wheelPicker.setIndicator(true);
        wheelPicker.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        wheelPicker.setIndicatorSize(AppUtils.dipToPx(getContext(), 1));
        // 设置是否有空气感，设置后上下边缘会渐变为透明，默认false
        wheelPicker.setAtmospheric(true);
        // 设置是否有卷曲感，不能微调卷曲幅度，默认false
        wheelPicker.setCurved(true);
        wheelPicker.post(() -> wheelPicker.setSelectedItemPosition(mPosition));
        wheelPicker.setOnItemSelectedListener((picker, data, position) -> {
                    mCurrent = mList.get(position);
                    mPosition = position;
                }
        );
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
            case R.id.tv_language_cancel:
                dismiss();
                break;
            case R.id.tv_language_finish:
                if (mSelectLanguageClickListener != null) {
                    mSelectLanguageClickListener.confirm(mCurrent, mPosition);
                }
                dismiss();
                break;
            default:
        }
    }

    public interface SelectLanguageClickListener {
        /**
         * 选择语言
         *
         * @param value    语言
         * @param position 序列号
         */
        void confirm(String value, int position);
    }

    private SelectLanguageClickListener mSelectLanguageClickListener;


    public void setSelectLanguageClickListener(SelectLanguageClickListener selectLanguageClickListener) {
        mSelectLanguageClickListener = selectLanguageClickListener;
    }
}

