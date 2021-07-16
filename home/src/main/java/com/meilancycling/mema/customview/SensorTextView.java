package com.meilancycling.mema.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * @author lion
 * 设置单独字体
 */

@SuppressLint("AppCompatCustomView")
public class SensorTextView extends TextView {
    public SensorTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SensorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SensorTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/arialbd.ttf");
            setTypeface(tf);
        }
    }
}
