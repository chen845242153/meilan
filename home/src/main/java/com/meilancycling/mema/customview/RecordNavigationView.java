package com.meilancycling.mema.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.RecordNavigationBinding;


/**
 * @Description: 记录顶部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class RecordNavigationView extends LinearLayout {

  private RecordNavigationBinding mRecordNavigationBinding;

    public RecordNavigationView(Context context) {
        super(context);
        init();
    }

    public RecordNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecordNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mRecordNavigationBinding = DataBindingUtil.inflate(inflater, R.layout.record_navigation, this, false);
            this.addView(mRecordNavigationBinding.getRoot());
        }
    }

    /**
     * 设置状态
     */
    public void setData(boolean check) {

    }

}
