package com.meilancycling.mema.ui.record;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
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
import com.meilancycling.mema.databinding.ActivityShareImageBinding;
import com.meilancycling.mema.customview.dialog.SelectImageDialog;
import com.meilancycling.mema.ui.details.MyTakePhoto;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.ui.details.MapLineActivity;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
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
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author lion
 * 图片分享
 */
public class ShareImageActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    ActivityShareImageBinding mActivityShareImageBinding;
    private Bitmap mBitmap;
    private Tencent mTencent;
    private int motionType;

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityShareImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_share_image);
        StatusAppUtils.setColor(this, getResources().getColor(R.color.white));
        mActivityShareImageBinding.ctvShare.setData(getResources().getString(R.string.share), this);
        motionType = getIntent().getIntExtra("motionType", 0);
        boolean haveMap = getIntent().getBooleanExtra("haveMap", false);
        if (AppUtils.isChinese()) {
            mActivityShareImageBinding.llQq.setVisibility(View.VISIBLE);
            mActivityShareImageBinding.llFacebook.setVisibility(View.GONE);
        } else {
            mActivityShareImageBinding.llQq.setVisibility(View.GONE);
            mActivityShareImageBinding.llFacebook.setVisibility(View.VISIBLE);
        }
        switch (motionType) {
            case Config.SPORT_OUTDOOR:
                mActivityShareImageBinding.ivShareType.setImageDrawable(getResources().getDrawable(R.drawable.share_1));
                break;
            case Config.SPORT_INDOOR:
                mActivityShareImageBinding.ivShareType.setImageDrawable(getResources().getDrawable(R.drawable.share_2));
                break;
            case Config.SPORT_COMPETITION:
                mActivityShareImageBinding.ivShareType.setImageDrawable(getResources().getDrawable(R.drawable.share_3));
                break;
            default:
        }
        if (DetailsHomeActivity.mMotionDetailsResponse != null && DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo() != null) {
            MotionDetailsResponse.MotionCyclingResponseVoBean motionCyclingResponseVo = DetailsHomeActivity.mMotionDetailsResponse.getMotionCyclingResponseVo();
            if ("0".equals(motionCyclingResponseVo.getTimeType())) {
                mActivityShareImageBinding.tvShareDate.setText(AppUtils.timeToString(motionCyclingResponseVo.getActivityDate(), Config.TIME_PATTERN));
            } else {
                mActivityShareImageBinding.tvShareDate.setText(AppUtils.zeroTimeToString(motionCyclingResponseVo.getActivityDate(), Config.TIME_PATTERN));
            }
            UnitBean distanceBean = UnitConversionUtil.getUnitConversionUtil().distanceSetting(ShareImageActivity.this, motionCyclingResponseVo.getDistance());
            mActivityShareImageBinding.ctvShareDistance.setText(distanceBean.getValue());
            mActivityShareImageBinding.ctvShareDistanceUnit.setText(distanceBean.getUnit());
            mActivityShareImageBinding.tvShareTime.setText(UnitConversionUtil.getUnitConversionUtil().timeToHMS(motionCyclingResponseVo.getActivityTime()));
            UnitBean speedSetting = UnitConversionUtil.getUnitConversionUtil().speedSetting(ShareImageActivity.this, (double) motionCyclingResponseVo.getAvgSpeed() / 10);
            mActivityShareImageBinding.tvShareSpeed.setText(speedSetting.getValue());
            mActivityShareImageBinding.tvShareSpeedUnit.setText(getString(R.string.average_speed) + "(" + speedSetting.getUnit() + ")");
            mActivityShareImageBinding.tvShareCal.setText(String.valueOf(motionCyclingResponseVo.getTotalCalories()));
        }
        if (!haveMap) {
            mActivityShareImageBinding.llMotionTrack.setVisibility(View.GONE);
        }
        mActivityShareImageBinding.ivDownImage.setOnClickListener(this);
        mActivityShareImageBinding.ivWx.setOnClickListener(this);
        mActivityShareImageBinding.ivFriends.setOnClickListener(this);
        mActivityShareImageBinding.ivQq.setOnClickListener(this);
        mActivityShareImageBinding.ivDown.setOnClickListener(this);
        mActivityShareImageBinding.ivFacebook.setOnClickListener(this);
        mActivityShareImageBinding.llMotionTrack.setOnClickListener(this);
        mActivityShareImageBinding.llPersonalitySharing.setOnClickListener(this);
        initFacebook();
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
                ToastUtils.show(ShareImageActivity.this, getString(R.string.share_success));
            }

            @Override
            public void onCancel() {
                ToastUtils.show(ShareImageActivity.this, getString(R.string.share_cancel));
            }

            @Override
            public void onError(FacebookException error) {
                ToastUtils.show(ShareImageActivity.this, getString(R.string.share_fail));
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.iv_down:
            case R.id.iv_down_image:
                showLoadingDialog();
                mBitmap = getBitmapFromView(mActivityShareImageBinding.rlShareBitmap);
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
                mBitmap = getBitmapFromView(mActivityShareImageBinding.rlShareBitmap);
                shareWeChat(mBitmap, 2);
                break;
            case R.id.iv_friends:
                mBitmap = getBitmapFromView(mActivityShareImageBinding.rlShareBitmap);
                shareWeChat(mBitmap, 1);
                break;
            case R.id.iv_qq:
                mBitmap = getBitmapFromView(mActivityShareImageBinding.rlShareBitmap);
                shareQQ(mBitmap);
                break;
            case R.id.iv_facebook:
                mBitmap = getBitmapFromView(mActivityShareImageBinding.rlShareBitmap);
                shareFaceBook(mBitmap);
                break;
            case R.id.ll_motion_track:
                MapLineActivity.enterMapLineActivity(this, motionType);
                break;
            case R.id.ll_personality_sharing:
                String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                if (EasyPermissions.hasPermissions(ShareImageActivity.this, perms)) {
                    // 图片选择
                    showSelectDialog();
                } else {
                    //第二个参数是被拒绝后再次申请该权限的解释
                    //第三个参数是请求码
                    //第四个参数是要申请的权限
                    EasyPermissions.requestPermissions(ShareImageActivity.this, getResources().getString(R.string.authorization), 0, perms);
                }
                break;
            default:
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        showSelectDialog();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        ToastUtils.show(this, getString(R.string.failed_obtain_permission));
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

    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {

            //分享成功
        }

        @Override
        public void onError(UiError uiError) {
            //分享失败

        }

        @Override
        public void onCancel() {
            //分享取消

        }
    }

    private int CODE_HEAD = 101;

    private void showSelectDialog() {
        SelectImageDialog selectImageDialog = new SelectImageDialog(this);
        selectImageDialog.setCanceledOnTouchOutside(false);
        selectImageDialog.show();
        selectImageDialog.setSelectImageDialogClickListener(new SelectImageDialog.SelectImageDialogClickListener() {
            @Override
            public void clickPictures() {
                Intent intent = new Intent(ShareImageActivity.this, MyTakePhoto.class);
                intent.putExtra(MyTakePhoto.TAKEPHOTO_TYPE, MyTakePhoto.ON_TAKEPHOTOS);
                startActivityForResult(intent, CODE_HEAD);
            }

            @Override
            public void clickAlbum() {
                Intent intent = new Intent(ShareImageActivity.this, MyTakePhoto.class);
                intent.putExtra(MyTakePhoto.TAKEPHOTO_TYPE, MyTakePhoto.ON_SELECTPICTURES);
                startActivityForResult(intent, CODE_HEAD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_HEAD) {
            if (data != null && null != data.getStringExtra("TakePhotosActivity_image")) {
                Intent intent = new Intent(this, PersonalShareActivity.class);
                intent.putExtra("motionType", motionType);
                intent.putExtra("url", data.getStringExtra("TakePhotosActivity_image"));
                startActivity(intent);
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}