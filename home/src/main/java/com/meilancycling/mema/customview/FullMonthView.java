package com.meilancycling.mema.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.meilancycling.mema.R;
import com.meilancycling.mema.utils.AppUtils;

import java.util.List;

/**
 * @author lion
 * 月视图
 */
public class FullMonthView extends MonthView {
    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final int CYCLING = 1;
    public static final int INDOOR_CYCLING = 2;
    public static final int GAME = 3;

    public FullMonthView(Context context) {
        super(context);
        mRectPaint.setStyle(Paint.Style.FILL);
        //4.0以上硬件加速会导致无效
        mSelectedPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
    }

    /**
     * 绘制选中的日子
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return true 则绘制onDrawScheme，因为这里背景色不是是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        return true;
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        List<Calendar.Scheme> schemes = calendar.getSchemes();
        if (schemes == null || schemes.size() == 0 || !calendar.isCurrentMonth()) {
            return;
        }
        int space = mItemHeight * 15 / 170;
        int sh = mItemHeight * 10 / 170;
        int indexY = y + mItemHeight * 90 / 170;

        for (int i = 0; i < schemes.size(); i++) {
            Calendar.Scheme scheme = schemes.get(i);
            String type = scheme.getScheme();
            switch (Integer.parseInt(type)) {
                case CYCLING:
                    mSchemePaint.setColor(getContext().getResources().getColor(R.color.main_color));
                    break;
                case INDOOR_CYCLING:
                    mSchemePaint.setColor(getContext().getResources().getColor(R.color.blue_color));
                    break;
                case GAME:
                    mSchemePaint.setColor(getContext().getResources().getColor(R.color.yellow_color));
                    break;
                default:
            }
            if (i < 3) {
                // 设置个新的长方形
                RectF oval = new RectF(x + AppUtils.dipToPx(getContext(), 8), indexY, mItemWidth + x - AppUtils.dipToPx(getContext(), 8), indexY + sh);
                //第二个参数是x半径，第三个参数是y半径
                canvas.drawRoundRect(oval, AppUtils.dipToPx(getContext(), 10), AppUtils.dipToPx(getContext(), 10), mSchemePaint);
                indexY = indexY + space + sh;
            } else {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_arrow);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int newWidth = AppUtils.dipToPx(getContext(), 8);
                int newHeight = AppUtils.dipToPx(getContext(), 8);
                // 计算缩放比例
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                canvas.drawBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true), x + AppUtils.dipToPx(getContext(), 20), indexY, mSchemePaint);
            }
        }
    }

    /**
     * 绘制文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        mRectPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(x + 1, y + 1, x + mItemWidth - 1, y + mItemHeight - 1, mRectPaint);
        int cx = x + mItemWidth / 2;
        int top = y + AppUtils.dipToPx(getContext(), 20);
        boolean isInRange = isInRange(calendar);
        canvas.drawText(String.valueOf(calendar.getDay()), cx, top, calendar.isCurrentDay() ? mCurDayTextPaint : calendar.isCurrentMonth() && isInRange ? mCurMonthTextPaint : mOtherMonthTextPaint);
    }
}
