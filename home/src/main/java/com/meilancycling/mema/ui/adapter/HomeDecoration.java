package com.meilancycling.mema.ui.adapter;

import android.graphics.Canvas;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description: recycleView 分割线
 * @Author: sore_lion
 * @CreateDate: 2020/12/24 8:44 AM
 */
public class HomeDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof HomeDataAdapter) {
            HomeDataAdapter adapter = (HomeDataAdapter) parent.getAdapter();
            // 返回可见区域内的第一个item的position
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            if (layoutManager != null) {
                int position = layoutManager.findFirstVisibleItemPosition();
                adapter.updateTop(position);
            }
        }
    }
}
