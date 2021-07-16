package com.meilancycling.mema.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.CommonTitleBinding;

/**
 * @Description: 汇总view
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class CommonTitleView extends LinearLayout {

    private CommonTitleBinding mCommonTitleBinding;

    public CommonTitleView(Context context) {
        super(context);
        init();
    }

    public CommonTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mCommonTitleBinding = DataBindingUtil.inflate(inflater, R.layout.common_title, this, false);
            this.addView(mCommonTitleBinding.getRoot());
        }
    }

    public void setData(String title, OnClickListener mOnClickListener) {
        mCommonTitleBinding.tvCommonTitle.setText(title);
        mCommonTitleBinding.llCommonBack.setOnClickListener(mOnClickListener);
    }

    public void changeTitle(String title) {
        mCommonTitleBinding.tvCommonTitle.setText(title);
    }

    public void setRightDraw(Drawable drawable, OnClickListener mOnClickListener) {
        mCommonTitleBinding.ivCommonRight.setImageDrawable(drawable);
        mCommonTitleBinding.llCommonRight.setOnClickListener(mOnClickListener);
    }

    public void setItemDraw(Drawable drawable, OnClickListener mOnClickListener) {
        mCommonTitleBinding.ivCommonItem.setImageDrawable(drawable);
        mCommonTitleBinding.llCommonItem.setClickable(true);
        mCommonTitleBinding.llCommonItem.setOnClickListener(mOnClickListener);
    }
    public void cancelItem(){
        mCommonTitleBinding.ivCommonItem.setImageDrawable(null);
        mCommonTitleBinding.llCommonItem.setClickable(false);
    }

}
