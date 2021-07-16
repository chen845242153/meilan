package com.meilancycling.mema.ui.home.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.MoreItemViewBinding;

/**
 * @Description:
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class MoreItemView extends LinearLayout {
    private MoreItemViewBinding mMoreItemViewBinding;

    public MoreItemView(Context context) {
        super(context);
        init();
    }

    public MoreItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoreItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mMoreItemViewBinding = DataBindingUtil.inflate(inflater, R.layout.more_item_view, this, false);
            this.addView(mMoreItemViewBinding.getRoot());
        }
    }

    /**
     * 设置数据
     */
    public void setDataAndListener(Drawable drawable, int title) {
        mMoreItemViewBinding.ivIcon.setImageDrawable(drawable);
        mMoreItemViewBinding.tvTitle.setText(title);
    }

}
