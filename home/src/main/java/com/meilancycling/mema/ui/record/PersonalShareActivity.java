package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.meilancycling.mema.MyApplication;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.response.MotionDetailsResponse;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityPersonalShareBinding;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.GlideUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/12/3 11:26 AM
 */
public class PersonalShareActivity extends BaseActivity implements View.OnClickListener {
    ActivityPersonalShareBinding mActivityPersonalShareBinding;
    private int motionType;
    private String url;
    private Bitmap mBitmap;
    private Tencent mTencent;

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPersonalShareBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_share);
        mActivityPersonalShareBinding.ctvShare.setData(getResources().getString(R.string.personality_sharing), this);
        motionType = getIntent().getIntExtra("motionType", 0);
        url = getIntent().getStringExtra("url");
        if (AppUtils.isChinese()) {
            mActivityPersonalShareBinding.llQq.setVisibility(View.VISIBLE);
            mActivityPersonalShareBinding.llFacebook.setVisibility(View.GONE);
        } else {
            mActivityPersonalShareBinding.llQq.setVisibility(View.GONE);
            mActivityPersonalShareBinding.llFacebook.setVisibility(View.VISIBLE);
        }
        switch (motionType) {
            case Config.SPORT_OUTDOOR:
                mActivityPersonalShareBinding.ivShareType.setImageDrawable(getResources().getDrawable(R.drawable.details_item1_white));
                break;
            case Config.SPORT_INDOOR:
                mActivityPersonalShareBinding.ivShareType.setImageDrawable(getResources().getDrawable(R.drawable.details_item2_white));
                break;
            case Config.SPORT_COMPETITION:
                mActivityPersonalShareBinding.ivShareType.setImageDrawable(getResources().getDrawable(R.drawable.details_item3_white));
                break;
            default:
        }
        if (DetailsHomeActivity.mMotionDetailsResponse != null && DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo() != null) {
            MotionDetailsResponse.MotionCyclingResponseVoBean motionCyclingResponseVo = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo();
            mActivityPersonalShareBinding.tvShareDate.setText(AppUtils.timeToString(motionCyclingResponseVo.getActivityDate(), Config.TIME_PATTERN));
            UnitBean distanceBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(PersonalShareActivity.this, motionCyclingResponseVo.getDistance());
            mActivityPersonalShareBinding.ctvShareDistance.setText(distanceBean.getValue());
            mActivityPersonalShareBinding.ctvShareDistanceUnit.setText(distanceBean.getUnit());
            mActivityPersonalShareBinding.tvShareTime.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(motionCyclingResponseVo.getActivityTime()));
            UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(PersonalShareActivity.this, (double) motionCyclingResponseVo.getAvgSpeed() / 10);
            mActivityPersonalShareBinding.tvShareSpeed.setText(speedSetting.getValue());
            mActivityPersonalShareBinding.tvShareSpeedUnit.setText(getString(R.string.average_speed) + "(" + speedSetting.getUnit() + ")");
            mActivityPersonalShareBinding.tvShareCal.setText(String.valueOf(motionCyclingResponseVo.getTotalCalories()));
        }
        GlideUtils.loadImageWelcome(url, this, mActivityPersonalShareBinding.ivBg);

        mActivityPersonalShareBinding.ivDownImage.setOnClickListener(this);
        mActivityPersonalShareBinding.ivDown.setOnClickListener(this);
        mActivityPersonalShareBinding.ivWx.setOnClickListener(this);
        mActivityPersonalShareBinding.ivFriends.setOnClickListener(this);
        mActivityPersonalShareBinding.ivQq.setOnClickListener(this);
        mActivityPersonalShareBinding.ivFacebook.setOnClickListener(this);
        initFacebook();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.iv_down_image:
            case R.id.iv_down:
                showLoadingDialog();
                mBitmap = getBitmapFromView(mActivityPersonalShareBinding.rlShareBitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (AppUtils.saveImageToGallery(this, mBitmap)) {
                        ToastUtils.show(this, getString(R.string.save_success));
                    } else {
                        ToastUtils.show(this, getString(R.string.save_failed));
                    }
                } else {
                    if (AppUtils.saveImage(this, mBitmap)) {
                        ToastUtils.show(this, getString(R.string.save_success));
                    } else {
                        ToastUtils.show(this, getString(R.string.save_failed));
                    }
                }
                hideLoadingDialog();
                break;
            case R.id.iv_wx:
                mBitmap = getBitmapFromView(mActivityPersonalShareBinding.rlShareBitmap);
                shareWeChat(mBitmap, 2);
                break;
            case R.id.iv_friends:
                mBitmap = getBitmapFromView(mActivityPersonalShareBinding.rlShareBitmap);
                shareWeChat(mBitmap, 1);
                break;
            case R.id.iv_qq:
                mBitmap = getBitmapFromView(mActivityPersonalShareBinding.rlShareBitmap);
                shareQQ(mBitmap);
                break;
            case R.id.iv_facebook:
                mBitmap = getBitmapFromView(mActivityPersonalShareBinding.rlShareBitmap);
                shareFaceBook(mBitmap);
                break;
        }
    }

    private Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    /**
     * 1 朋友圈
     * 2 好友
     */
    public void shareWeChat(Bitmap bitmap, int type) {
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "sore";
        req.message = msg;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        if (type == 1) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        //调用api接口发送数据到微信
        MyApplication.api.sendReq(req);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    private void shareQQ(Bitmap bitmap) {
        if (null == mTencent) {
            mTencent = Tencent.createInstance(MyApplication.QQ_APPID, getApplication());
        }
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, saveBitMap(bitmap));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(this, params, new ShareUiListener());
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
//            ToastUtils.show(PersonalShareActivity.this, getString(R.string.share_success));
            //分享成功
        }

        @Override
        public void onError(UiError uiError) {
            //分享失败
//            ToastUtils.show(PersonalShareActivity.this, getString(R.string.share_fail));
        }

        @Override
        public void onCancel() {
            //分享取消
//            ToastUtils.show(PersonalShareActivity.this, getString(R.string.share_cancel));
        }
    }

    private String saveBitMap(Bitmap bitmap) {
        String filePath = getExternalFilesDir("") + File.separator + "qq.png";
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return filePath;
        } catch (IOException e) {
            return null;
        }
    }

    public void shareFaceBook(Bitmap bitmap) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            SharePhoto mSharePhoto = new SharePhoto.Builder()
                    .setCaption("测试一下")
                    .setBitmap(bitmap)
                    .build();
            SharePhotoContent mContent = new SharePhotoContent.Builder()
                    .addPhoto(mSharePhoto)
                    .build();
            shareDialog.show(mContent);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    /**
     * facebook配置, 在oncreate()方法中调用
     */
    private void initFacebook() {
        //抽取成员变量
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                //分享成功的回调，在这里做一些自己的逻辑处理
                ToastUtils.show(PersonalShareActivity.this, getString(R.string.share_success));
            }

            @Override
            public void onCancel() {
                ToastUtils.show(PersonalShareActivity.this, getString(R.string.share_cancel));
            }

            @Override
            public void onError(FacebookException error) {
                ToastUtils.show(PersonalShareActivity.this, getString(R.string.share_fail));
            }
        });
    }
}
