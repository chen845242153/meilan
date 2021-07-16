package com.meilancycling.mema.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.CamcorderProfile;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.Surface;

import androidx.annotation.Nullable;

import com.meilancycling.mema.R;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @Description: 录屏服务
 * @Author: sore_lion
 * @CreateDate: 2020/12/1 5:30 PM
 */
public class RecordService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RecordBinder();
    }

    private String outPath;
    private int dpi;
    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;
    private CamcorderProfile profile;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext())
                .setSmallIcon(R.mipmap.logo_main)
                .setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ONE_ID = "123";
            String CHANNEL_ONE_NAME = "sensor";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);
            notificationChannel.setShowBadge(false);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        // 获取构建好的Notification
        Notification notification = builder.build();
        //设置为默认的声音
        notification.defaults = Notification.DEFAULT_SOUND;
        startForeground(123, notification);
        if (intent != null) {
            outPath = intent.getStringExtra("path");
            dpi = intent.getIntExtra("dpi", 1);
            int code = intent.getIntExtra("code", -1);
            Intent data = intent.getParcelableExtra("data");
            mediaProjection = createMediaProjection(code, data);
            mediaRecorder = createMediaRecorder();
            // 必须在mediaRecorder.prepare() 之后调用，否则报错"fail to get surface"
            virtualDisplay = createVirtualDisplay();
        }
        return Service.START_NOT_STICKY;
    }

    public class RecordBinder extends Binder {
        public RecordService getService() {
            return RecordService.this;
        }
    }

    /**
     * 开始录制
     */
    public void startRecord() {
        mediaRecorder.start();
    }

    private MediaRecorder createMediaRecorder() {
        MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        //mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

        mediaRecorder.setVideoEncodingBitRate(8 * 1024 * 1024);

        mediaRecorder.setVideoSize(profile.videoFrameHeight, profile.videoFrameWidth);

        mediaRecorder.setVideoFrameRate(60);
        mediaRecorder.setOutputFile(outPath);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            String message = e.getMessage();
        }

        return mediaRecorder;
    }

    private MediaProjection createMediaProjection(int resultCode, Intent intent) {
        return ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE)).getMediaProjection(resultCode, intent);
    }

    private Surface surface;

    private VirtualDisplay createVirtualDisplay() {
        @SuppressLint("WrongConstant") ImageReader imageReader = ImageReader.newInstance(profile.videoFrameHeight, profile.videoFrameWidth, PixelFormat.RGBA_8888, 2);
        imageReader.setOnImageAvailableListener(imageAvailableListener, null);
        try {
            surface = mediaRecorder.getSurface();
        } catch (Exception e) {
            String message = e.getMessage();
        }
        return virtualDisplay = mediaProjection.createVirtualDisplay(RecordService.class.getSimpleName(),
                profile.videoFrameHeight,
                profile.videoFrameWidth,
                dpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                surface,
                null,
                null);

    }

    ImageReader.OnImageAvailableListener imageAvailableListener = reader -> {
        Image image = reader.acquireLatestImage();
        if (image != null) {
            int width = image.getWidth();
            int height = image.getHeight();
            final Image.Plane[] planes = image.getPlanes();
            final ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;
            Bitmap mBitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            mBitmap.copyPixelsFromBuffer(buffer);
            image.close();
        }
    };

    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            release();
        }
    }

    public void release() {
        if (virtualDisplay != null) {
            virtualDisplay.release();
            virtualDisplay = null;
        }
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);
            mediaProjection.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
        }
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
    }
}
