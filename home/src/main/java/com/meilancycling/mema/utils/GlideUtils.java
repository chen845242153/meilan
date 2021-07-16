package com.meilancycling.mema.utils;

/**
 * Created by yc on 2017/4/13.
 */

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.meilancycling.mema.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 开源图片框架Glide工具类
 *
 * @author lion
 */
public class GlideUtils {

    /**
     * 圆形图片
     *
     * @param url
     * @param context
     * @param imageView
     */
    public static void loadCircleImage(String url, Context context, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .circleCrop();
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载图片（未设置缓存）
     * 启动页
     */
    public static void loadImageWelcome(String url, Context context, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar);
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载图片（圆角）
     */
    public static void loadImageRounder(String url, Context context, ImageView imageView, int radiusPx) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().transform(new RoundedCorners(radiusPx)))
                .into(imageView);
    }

    @BindingAdapter("loadImageUrl")
    public static void loadImageUrl(ImageView imageView, String pitureUrl) {
        if (!TextUtils.isEmpty(pitureUrl)) {
            Glide.with(imageView.getContext())
                    .load(pitureUrl)
                    .transition(withCrossFade())
                    .into(imageView);
        }
    }

    @BindingAdapter("loadImageUrl1")
    public static void loadImageUrl1(ImageView imageView, String pitureUrl) {
        Glide.with(imageView.getContext())
                .load(pitureUrl)
                .transition(withCrossFade())
                .into(imageView);

//        else {
//            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.empty_national));
//        }
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
}
