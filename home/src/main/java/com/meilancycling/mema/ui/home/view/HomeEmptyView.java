package com.meilancycling.mema.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.CalendarTopBinding;


/**
 * @Description: 记录顶部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class HomeEmptyView extends LinearLayout {

    private CalendarTopBinding mCalendarTopBinding;

    public HomeEmptyView(Context context) {
        super(context);
        init();
    }

    public HomeEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mCalendarTopBinding = DataBindingUtil.inflate(inflater, R.layout.home_empty, this, false);
            this.addView(mCalendarTopBinding.getRoot());
        }
    }

    /**
     * 设置状态
     */
    @SuppressLint("SetTextI18n")
    public void setData(String value, String type, String unit) {
        mCalendarTopBinding.tvCalendarValue.setText(value);
        if (TextUtils.isEmpty(unit)) {
            mCalendarTopBinding.tvCalendarUnit.setText(type);
        } else {
            mCalendarTopBinding.tvCalendarUnit.setText(type + "(" + unit + ")");
        }
    }

}
