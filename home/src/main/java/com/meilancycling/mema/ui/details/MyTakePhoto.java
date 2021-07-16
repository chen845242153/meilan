package com.meilancycling.mema.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;
import java.util.ArrayList;

/**
 * 照片选择
 *
 * @author sorelion qq 571135591
 */
public class MyTakePhoto extends TakePhotoActivity {
    public static final String TAKEPHOTO_TYPE = "TakePhoto_type";
    public static final int ON_TAKEPHOTOS = 1;
    public static final int ON_SELECTPICTURES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTakePhoto(getTakePhoto());
        if (getIntent().getIntExtra(TAKEPHOTO_TYPE, ON_SELECTPICTURES) == ON_SELECTPICTURES) {
            onSelectPictures(2, 1, true, 588, 589);
        } else {
            onTakePhotos(true, 588, 589);
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        finish();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showOneImg(result.getImages().get(0));
    }

    /**
     * 只需要一张图片
     */
    private void showOneImg(TImage tImage) {
        // 原图路径：getOriginalPath() 压缩图路径：getCompressPath()
        Intent intent = new Intent();
        intent.putExtra("TakePhotosActivity_image", tImage.getCompressPath());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 多张图片
     */
    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent();
        intent.putExtra("TakePhotosActivity_images", images);
        setResult(RESULT_OK, intent);
        finish();
    }

    // -----------------------------------------------------------------
    // -----------------------------------------------------------------

    private TakePhoto takePhoto;
    private Uri imageUri;

    private void initTakePhoto(TakePhoto takePhoto) {
        this.takePhoto = takePhoto;
        initImageUri();
        // 默认配置，需要特定设置可以重新配置
        // sumsung手机存在图片旋转角度问题，暂时没有解决
        configCompress(true, 102400, 588, 619, false, false, false);
        configTakePhotoOption(false);
    }

    private void initImageUri() {
        File file = new File(Environment.getExternalStorageDirectory(),
                "/takephototemp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
    }

    /**
     * 压缩配置
     *
     * @param compress        是否压缩
     * @param maxSize         大小不超过（B）（1M=1024KB 1Kb=1024B）
     * @param width           宽
     * @param height          高
     * @param showProgressBar 是否显示压缩进度条
     * @param saveRawFile     拍照压缩后是否保存原图
     * @param useLuban        选择压缩工具（自带或者Luban）
     */
    private void configCompress(boolean compress, int maxSize, int width, int height,
                                boolean showProgressBar, boolean saveRawFile, boolean useLuban) {
        if (!compress) {
            takePhoto.onEnableCompress(null, false);
            return;
        }

        CompressConfig config;
        if (!useLuban) {
            config = new CompressConfig
                    .Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(saveRawFile)
                    .create();
        } else {
            LubanOptions option = new LubanOptions
                    .Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(saveRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    /**
     * 相册配置
     *
     * @param useOwnGallery 是否使用TakePhoto自带相册
     */
    private void configTakePhotoOption(boolean useOwnGallery) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(useOwnGallery);
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * 裁切配置
     *
     * @param crop       是否裁切
     * @param useOwnCrop 选择裁切工具（第三方或者TakePhoto自带）
     * @param aspect     设置尺寸/比例（宽x高或者宽/高）
     * @param width      宽
     * @param height     高
     */
    private CropOptions getCropOptions(boolean crop, boolean useOwnCrop, boolean aspect, int width, int height) {
        if (!crop) {
            return null;
        }

        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
//        if (aspect) {
//            builder.setAspectX(width).setAspectY(height);
//        } else {
//            builder.setOutputX(width).setOutputY(height);
//        }
        builder.setWithOwnCrop(useOwnCrop);
        return builder.create();
    }

    /**
     * 拍照
     *
     * @param crop   是否裁切
     * @param width  宽
     * @param height 高
     */
    private void onTakePhotos(boolean crop, int width, int height) {
        if (crop) {
            takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions(true, false, false, width, height));
        } else {
            takePhoto.onPickFromCapture(imageUri);
        }
    }

    /**
     * 选择图片
     *
     * @param imageType 图片选择途径（1：文件 2：相册）
     * @param etLimit   最多选择图片数量
     * @param crop      是否裁切
     * @param width     宽
     * @param height    高
     */
    private void onSelectPictures(int imageType, int etLimit, boolean crop, int width, int height) {
        if (etLimit > 1) {
            if (crop) {
                takePhoto.onPickMultipleWithCrop(etLimit, getCropOptions(true, false, false, width, height));
            } else {
                takePhoto.onPickMultiple(etLimit);
            }
            return;
        }
        if (imageType == 1) {
            if (crop) {
                takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions(true, false, false, width, height));
            } else {
                takePhoto.onPickFromDocuments();
            }
        } else {
            if (crop) {
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions(true, false, false, width, height));
            } else {
                takePhoto.onPickFromGallery();
            }
        }
    }
}
