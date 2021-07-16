package com.meilancycling.mema.ui.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.DeviceSettingItemBinding;
import com.meilancycling.mema.databinding.UserItemBinding;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class UserItemView extends LinearLayout {
    private UserItemBinding mUserItemBinding;

    public UserItemView(Context context) {
        super(context);
        init();
    }

    public UserItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UserItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mUserItemBinding = DataBindingUtil.inflate(inflater, R.layout.user_item, this, false);
            this.addView(mUserItemBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setItemData(String title, String value) {
        mUserItemBinding.tvTitle.setText(title);
        mUserItemBinding.tvValue.setText(value);
    }
}
