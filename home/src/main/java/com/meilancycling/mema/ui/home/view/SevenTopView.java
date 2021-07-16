package com.meilancycling.mema.ui.home.view;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.meilancycling.mema.R;
import com.meilancycling.mema.databinding.SevenTopViewBinding;
import com.meilancycling.mema.ui.record.RecordHomeActivity;


/**
 * @Description:
 * @Author: sore_lion
 * @CreateDate: 2020/11/9 5:51 PM
 */
public class SevenTopView extends LinearLayout {
    private SevenTopViewBinding mSevenTopViewBinding;

    public SevenTopView(Context context) {
        super(context);
        init();
    }

    public SevenTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SevenTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            mSevenTopViewBinding = DataBindingUtil.inflate(inflater, R.layout.seven_top_view, this, false);
            this.addView(mSevenTopViewBinding.getRoot());
        }
        mSevenTopViewBinding.viewMore.setOnClickListener(v -> getContext().startActivity(new Intent(getContext(), RecordHomeActivity.class)));
    }
}
