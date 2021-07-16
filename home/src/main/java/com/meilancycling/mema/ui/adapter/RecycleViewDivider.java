package com.meilancycling.mema.ui.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.utils.AppUtils;

/**
 * @Description: recycleView 分割线
 * @Author: sore_lion
 * @CreateDate: 2020/12/24 8:44 AM
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    private Paint textPaint;

    public RecycleViewDivider() {
        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#FFE8E8E8"));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int count = parent.getChildCount();
        int padding = AppUtils.dipToPx(parent.getContext(), 15);
        for (int i = 0; i < count; i++) {
            // 获取对应i的View
            View view = parent.getChildAt(i);
            // 获取View的布局位置
            int position = parent.getChildLayoutPosition(view);
            if (position != 0) {
                c.drawRect(padding, view.getTop() -AppUtils.dipToPx(parent.getContext(), 1), view.getWidth() - padding, view.getTop(), textPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        if (position != 0) {
            outRect.set(0, AppUtils.dipToPx(parent.getContext(), 1), 0, 0);
        }
    }
}
