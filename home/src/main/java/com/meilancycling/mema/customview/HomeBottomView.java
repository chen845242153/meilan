package com.meilancycling.mema.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.HomeBottomBinding;

/**
 * @Description: 底部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class HomeBottomView extends LinearLayout {

    private HomeBottomBinding mHomeBottomBinding;

    public HomeBottomView(Context context) {
        super(context);
        init();
    }

    public HomeBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mHomeBottomBinding = DataBindingUtil.inflate(inflater, R.layout.home_bottom, this, false);
            this.addView(mHomeBottomBinding.getRoot());
        }
    }

    /**
     * 设置状态
     */
    public void setCheckData(boolean check, int position) {
        switch (position) {
            case 0:
                if (check) {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_item1));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.main_color));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item1_select));
                } else {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_item1));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.black_9));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item1));
                }
                break;
            case 1:
                if (check) {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_item2));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.main_color));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item2_select));
                } else {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_item2));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.black_9));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item2));
                }
                break;
            case 2:
                if (check) {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_club));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.main_color));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item3_select));
                } else {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_club));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.black_9));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item3));
                }
                break;
            case 3:
                if (check) {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_item4));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.main_color));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item4_select));
                } else {
                    mHomeBottomBinding.tvHomeBottom.setText(getContext().getString(R.string.home_item4));
                    mHomeBottomBinding.tvHomeBottom.setTextColor(ContextCompat.getColor(getContext(), R.color.black_9));
                    mHomeBottomBinding.ivHomeBottom.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.home_item4));
                }
                break;
            default:
        }
    }
}
