package com.meilancycling.mema.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.MapBottomBinding;

/**
 * @Description: 地图切换模式
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class MapBottomView extends LinearLayout {

    private MapBottomBinding mMapBottomBinding;

    public MapBottomView(Context context) {
        super(context);
        init();
    }

    public MapBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mMapBottomBinding = DataBindingUtil.inflate(inflater, R.layout.map_bottom, this, false);
            this.addView(mMapBottomBinding.getRoot());
        }
    }

    public void initView(OnClickListener onClickListener) {
        mMapBottomBinding.llMapItem1.setOnClickListener(onClickListener);
        mMapBottomBinding.llMapItem2.setOnClickListener(onClickListener);
        mMapBottomBinding.llMapItem3.setOnClickListener(onClickListener);
        mMapBottomBinding.llMapItem4.setOnClickListener(onClickListener);
    }

    /**
     * 设置数据
     */
    public void setSelect(int position) {
        switch (position) {
            case 0:
                mMapBottomBinding.tvMapItem1.setTextColor(getContext().getResources().getColor(R.color.main_color));
                mMapBottomBinding.tvMapItem2.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem3.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem4.setTextColor(getContext().getResources().getColor(R.color.black_3));
                break;
            case 1:
                mMapBottomBinding.tvMapItem1.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem2.setTextColor(getContext().getResources().getColor(R.color.main_color));
                mMapBottomBinding.tvMapItem3.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem4.setTextColor(getContext().getResources().getColor(R.color.black_3));
                break;
            case 2:
                mMapBottomBinding.tvMapItem1.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem2.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem3.setTextColor(getContext().getResources().getColor(R.color.main_color));
                mMapBottomBinding.tvMapItem4.setTextColor(getContext().getResources().getColor(R.color.black_3));
                break;
            case 3:
                mMapBottomBinding.tvMapItem1.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem2.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem3.setTextColor(getContext().getResources().getColor(R.color.black_3));
                mMapBottomBinding.tvMapItem4.setTextColor(getContext().getResources().getColor(R.color.main_color));
                break;
            default:
        }
    }

}
