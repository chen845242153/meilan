package com.meilancycling.mema.customview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meilancycling.mema.R;

import java.util.Objects;

/**
 * @author lion
 * APP 升级弹窗
 */
public class AppUpdateDialog extends Dialog implements View.OnClickListener {
    /**
     * 是否强制升级：1普通，2强制
     */
    private int updateFlag;
    private String mContent;
    private TextView tvProgress;
    private TextView tvUpdate;
    private ProgressBar pbUpgrade;
    private TextView confirm;
    private TextView content;
    private TextView later;

    public AppUpdateDialog(Context context, int isUpgrade, String content) {
        super(context, R.style.dialog_style);
        updateFlag = isUpgrade;
        mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_app_update);
        confirm = findViewById(R.id.tv_confirm);
        content = findViewById(R.id.tv_content);
        later = findViewById(R.id.tv_later);
        content.setText(mContent);
        if (updateFlag == 1) {
            later.setVisibility(View.VISIBLE);
        } else {
            later.setVisibility(View.GONE);
        }
        later.setOnClickListener(this);
        confirm.setOnClickListener(this);
        tvProgress = findViewById(R.id.tv_progress);
        tvUpdate = findViewById(R.id.tv_update);
        pbUpgrade = findViewById(R.id.pb_upgrade);
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_later) {
            dismiss();
        } else {
            confirm.setVisibility(View.INVISIBLE);
            later.setVisibility(View.GONE);
            pbUpgrade.setVisibility(View.VISIBLE);
            tvProgress.setVisibility(View.VISIBLE);
            tvUpdate.setVisibility(View.VISIBLE);
            updateProgress(0);
            mAppUpdateConfirmListener.confirmListener();
        }
    }

    /**
     * 更新进度条
     */
    @SuppressLint("SetTextI18n")
    public void updateProgress(int progress) {
        tvProgress.setText(progress + "%");
        pbUpgrade.setProgress(progress);
    }

    public void updateFail() {
        confirm.setVisibility(View.VISIBLE);
        later.setVisibility(View.VISIBLE);
        pbUpgrade.setVisibility(View.GONE);
        tvProgress.setVisibility(View.GONE);
        tvUpdate.setVisibility(View.GONE);
    }

    public interface AppUpdateConfirmListener {
        /**
         * 确定点击
         */
        void confirmListener();
    }

    private AppUpdateConfirmListener mAppUpdateConfirmListener;

    public void setAppUpdateConfirmListener(AppUpdateConfirmListener appUpdateConfirmListener) {
        mAppUpdateConfirmListener = appUpdateConfirmListener;
    }
}
