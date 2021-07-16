package com.meilancycling.mema.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.AggregateDataBinding;
import com.meilancycling.mema.databinding.CommonBottomViewBinding;
import com.meilancycling.mema.databinding.CommonTitleBinding;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class CommonBottomView extends LinearLayout {

    private CommonBottomViewBinding mCommonBottomViewBinding;

    public CommonBottomView(Context context) {
        super(context);
        init();
    }

    public CommonBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mCommonBottomViewBinding = DataBindingUtil.inflate(inflater, R.layout.common_bottom_view, this, false);
            this.addView(mCommonBottomViewBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setBottomView(int value, int color, OnClickListener onClickListener) {
        mCommonBottomViewBinding.tvTitle.setText(value);
        mCommonBottomViewBinding.tvTitle.setTextColor(ContextCompat.getColor(getContext(), color));
        mCommonBottomViewBinding.tvTitle.setOnClickListener(onClickListener);
    }
    public void setBottomView(String value, int color, OnClickListener onClickListener) {
        mCommonBottomViewBinding.tvTitle.setText(value);
        mCommonBottomViewBinding.tvTitle.setTextColor(color);
        mCommonBottomViewBinding.tvTitle.setOnClickListener(onClickListener);
    }
}
