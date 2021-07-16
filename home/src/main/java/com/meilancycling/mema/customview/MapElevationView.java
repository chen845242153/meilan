package com.meilancycling.mema.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meilancycling.mema.utils.AppUtils;

import java.util.List;

/**
 * @author lion
 * 坡度进度
 */
public class MapElevationView extends View {
    private List<List<String>> valueList;
    private int minValue;
    private int maxValue;
    private int progress;
    private Path bgPath;
    private float lastX;
    /**
     * 画笔
     */
    private Paint mBackgroundPaint, mProgressPaint, valuePaint;

    public MapElevationView(Context context) {
        this(context, null);
    }

    public MapElevationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapElevationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        // 设置抗锯齿
        mBackgroundPaint.setAntiAlias(true);
        // 设置抖动
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setStrokeWidth(AppUtils.dipToPx(context, 1));
        mBackgroundPaint.setColor(Color.parseColor("#FFF2F2F2"));

        // 初始化进度圆环画笔
        mProgressPaint = new Paint();
        // 只描边，不填充
        mProgressPaint.setStyle(Paint.Style.STROKE);
        // 设置抗锯齿
        mProgressPaint.setAntiAlias(true);
        // 设置抖动
        mProgressPaint.setDither(true);
        mProgressPaint.setStrokeWidth(AppUtils.dipToPx(context, 2));
        mProgressPaint.setColor(Color.parseColor("#FF6FB92C"));

        valuePaint = new Paint();
        valuePaint.setStyle(Paint.Style.FILL);
        // 设置抗锯齿
        valuePaint.setAntiAlias(true);
        // 设置抖动
        valuePaint.setDither(true);
        valuePaint.setColor(Color.parseColor("#666FB92C"));
        bgPath = new Path();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (valueList != null) {
            float widthItem = (float) getWidth() / valueList.size();
            float heightItem = (float) getHeight() / (maxValue - minValue);
            bgPath.reset();
            bgPath.moveTo(0, getHeight());
            for (int i = 1; i < valueList.size(); i++) {
                List<String> last = valueList.get(i - 1);
                List<String> current = valueList.get(i);
                if (last.size() > 3) {
                    if (i <= ((float) progress / flag) * valueList.size()) {
                        canvas.drawLine(widthItem * (i - 1), (Integer.parseInt(last.get(3)) - minValue) * heightItem, widthItem * i, (Integer.parseInt(current.get(3)) - minValue) * heightItem, mProgressPaint);
                        bgPath.lineTo(widthItem * i, (Integer.parseInt(current.get(3)) - minValue) * heightItem);
                        lastX = widthItem * i;
                    } else {
                        float startY = (Integer.parseInt(last.get(3)) - minValue) * heightItem;
                        float stopY = (Integer.parseInt(current.get(3)) - minValue) * heightItem;
                        float startX = widthItem * (i - 1);
                        canvas.drawLine(startX, startY, widthItem * i, stopY, mBackgroundPaint);
                    }
                }
            }
            bgPath.lineTo(lastX, getHeight());
            canvas.drawPath(bgPath, valuePaint);
        }
    }

    private int flag;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            progress = msg.arg1;
            if (progress <= flag) {
                invalidate();
                mHandler.postDelayed(() -> {
                    Message message = mHandler.obtainMessage();
                    message.arg1 = progress + 1;
                    message.what = 0;
                    mHandler.sendMessage(message);
                }, 100);
            }
        }
    };

    public void startView(int time) {
        flag = time;
        Message message = mHandler.obtainMessage();
        message.arg1 = 0;
        message.what = 0;
        mHandler.sendMessage(message);
    }

    public void endView() {
        progress = 0;
        postInvalidate();
    }

    public void updateView(List<List<String>> coordinateList, int min, int max) {
        progress = 0;
        valueList = coordinateList;
        minValue = min;
        maxValue = max;
        postInvalidate();
    }
}
