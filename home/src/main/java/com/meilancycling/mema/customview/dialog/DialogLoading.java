package com.meilancycling.mema.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.meilancycling.mema.R;

/**
 * @author lion
 * 加载
 */
public class DialogLoading extends Dialog {
    private ImageView ivLoading;

    public DialogLoading(Context context) {
        super(context, R.style.loading_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        ivLoading = findViewById(R.id.iv_loading);
        rotationAnimation();
    }

    /**
     * 隐藏弹窗
     */
    public void hideDialog() {
        ivLoading.clearAnimation();
        dismiss();
    }

    /**
     * 旋转动画
     */
    private void rotationAnimation() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        ivLoading.startAnimation(rotateAnimation);
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
