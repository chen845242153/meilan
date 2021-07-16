package com.meilancycling.mema.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Device;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/6/7 2:20 下午
 */
public class BindingAdapters {
    @BindingAdapter("loadImageUrl")
    public static void loadImageUrl(ImageView imageView, String pitureUrl) {
        Glide.with(imageView.getContext())
                .load(pitureUrl)
                .transition(withCrossFade())
                .into(imageView);
    }

    @BindingAdapter("loadCircleUrl")
    public static void loadCircleUrl(ImageView imageView, String pitureUrl) {
        if (!TextUtils.isEmpty(pitureUrl)) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .circleCrop();
            Glide.with(imageView.getContext())
                    .load(pitureUrl)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    @BindingAdapter("emptyToGone")
    public static void emptyToGone(View view, String content) {
        if (TextUtils.isEmpty(content)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("emptyToVisible")
    public static void emptyToVisible(View view, String content) {
        if (TextUtils.isEmpty(content)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("loadDevice")
    public static void loadDevice(ImageView imageView, String productNo) {
        if (!TextUtils.isEmpty(productNo)) {
            switch (productNo) {
                case Device.PRODUCT_NO_M1:
                    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.home_m1));
                    break;
                case Device.PRODUCT_NO_M2:
                    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.home_m2));
                    break;
                case Device.PRODUCT_NO_M4:
                    imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.home_m4));
                    break;
            }
        }
    }

    @BindingAdapter("loadDeviceName")
    public static void loadDeviceName(TextView textView, String productNo) {
        if (!TextUtils.isEmpty(productNo)) {
            switch (productNo) {
                case Device.PRODUCT_NO_M1:
                    textView.setText(Device.NAME_M1);
                    break;
                case Device.PRODUCT_NO_M2:
                    textView.setText(Device.NAME_M2);
                    break;
                case Device.PRODUCT_NO_M4:
                    textView.setText(Device.NAME_M4);
                    break;
            }
        }
    }
}
