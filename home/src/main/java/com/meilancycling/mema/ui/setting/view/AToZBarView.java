package com.meilancycling.mema.ui.setting.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ChartUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 记录顶部导航
 * @Author: sore_lion
 * @CreateDate: 2020/11/11 17:51 PM
 */
public class AToZBarView extends View {

    private Paint paint;
    private List<String> data;

    public AToZBarView(Context context) {
        super(context);
        init();
    }

    public AToZBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AToZBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lastPosition = -1;
        data = new ArrayList<>();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(AppUtils.spToPx(getContext(), 14));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.blue_color));
        padding = AppUtils.dipToPx(getContext(), 18);
    }

    public void addData(String item) {
        if (!data.contains(item)) {
            data.add(item);
        }
    }

    private int start;
    private int padding;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int top = (getHeight() - data.size() * padding) / 2;
        start = top;
        for (int i = 0; i < data.size(); i++) {
            canvas.drawText(data.get(i), getWidth() / 2, top + padding * i, paint);
        }
    }

    private int lastPosition;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int position = (int) Math.ceil((event.getY() - start) / padding);
        if (position < 0) {
            position = 0;
        }
        if (position > data.size() - 1) {
            position = data.size() - 1;
        }
        if (position != lastPosition) {
            mAToZBarViewCallback.resultCallback(data.get(position));
            lastPosition = position;
        }

        return true;

    }

    public interface AToZBarViewCallback {
        void resultCallback(String result);
    }

    private AToZBarViewCallback mAToZBarViewCallback;

    public void setAToZBarViewCallback(AToZBarViewCallback AToZBarViewCallback) {
        mAToZBarViewCallback = AToZBarViewCallback;
    }
}
