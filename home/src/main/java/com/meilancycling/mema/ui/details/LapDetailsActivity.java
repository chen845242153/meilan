package com.meilancycling.mema.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityLapDetailsBinding;
import com.meilancycling.mema.network.bean.LapBean;
import com.meilancycling.mema.ui.adapter.LapAdapter;
import com.meilancycling.mema.ui.adapter.LapDetailsAdapter;
import com.meilancycling.mema.utils.StatusAppUtils;

import java.util.List;

/**
 * 圈数详情
 *
 * @author lion
 */
public class LapDetailsActivity extends BaseActivity {
    private ActivityLapDetailsBinding mActivityLapDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivityLapDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_lap_details);
        String lap = getIntent().getStringExtra("lap");
        List<LapBean> lapList = new Gson().fromJson(lap, new TypeToken<List<LapBean>>() {
        }.getType());
        LapDetailsAdapter lapDetailsAdapter = new LapDetailsAdapter(lapList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        mActivityLapDetailsBinding.rv.setLayoutManager(manager);
        mActivityLapDetailsBinding.rv.setAdapter(lapDetailsAdapter);

        if (lapList != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            LapAdapter lapAdapter = new LapAdapter(lapList.size());
            mActivityLapDetailsBinding.rvLeft.setLayoutManager(linearLayoutManager);
            mActivityLapDetailsBinding.rvLeft.setAdapter(lapAdapter);
        }

        mActivityLapDetailsBinding.viewBack.setOnClickListener(v -> finish());
        mActivityLapDetailsBinding.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
                    mActivityLapDetailsBinding.rvLeft.scrollBy(dx, dy);
                }
            }
        });

        mActivityLapDetailsBinding.rvLeft.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
                    mActivityLapDetailsBinding.rv.scrollBy(dx, dy);
                }
            }
        });


    }
}