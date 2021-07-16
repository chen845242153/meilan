package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.databinding.ActivityAddRecordBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.FileUploadEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.work.FileUploadWork;
import com.meilancycling.mema.work.KomootUploadWork;
import com.meilancycling.mema.work.SensorUploadWork;
import com.meilancycling.mema.work.StravaUploadWork;
import com.meilancycling.mema.work.TPUploadWork;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 添加历史记录
 *
 * @author lion
 */
public class AddRecordActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAddRecordBinding mActivityAddRecordBinding;
    private int recordType;
    private final int code_file = 1;
    private boolean isUpLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddRecordBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_record);
        mActivityAddRecordBinding.ivFinish.setOnClickListener(this);
        mActivityAddRecordBinding.ivItem1.setOnClickListener(this);
        mActivityAddRecordBinding.ivItem2.setOnClickListener(this);
        mActivityAddRecordBinding.ivItem3.setOnClickListener(this);
        mActivityAddRecordBinding.tvSave.setOnClickListener(this);
        mActivityAddRecordBinding.tvSelectFile.setOnClickListener(this);
        mActivityAddRecordBinding.viewAddFit.setOnClickListener(this);
        recordType = Config.OUTDOOR_CYCLING;
        isUpLoad = false;
        String filePath = getExternalFilesDir("") + File.separator + "fit";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.tv_save:
                if (isUpLoad) {
                    uploadFile();
                }
                break;
            case R.id.iv_item1:
                recordType = Config.OUTDOOR_CYCLING;
                mActivityAddRecordBinding.ivItem1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item1_select));
                mActivityAddRecordBinding.ivItem2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item2));
                mActivityAddRecordBinding.ivItem3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item3));
                break;
            case R.id.iv_item2:
                recordType = Config.INDOOR_CYCLING;
                mActivityAddRecordBinding.ivItem1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item1));
                mActivityAddRecordBinding.ivItem2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item2_selelct));
                mActivityAddRecordBinding.ivItem3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item3));
                break;
            case R.id.iv_item3:
                recordType = Config.COMPETITION;
                mActivityAddRecordBinding.ivItem1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item1));
                mActivityAddRecordBinding.ivItem2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item2));
                mActivityAddRecordBinding.ivItem3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.add_item3_selelct));
                break;
            case R.id.tv_select_file:
                startActivityForResult(new Intent(AddRecordActivity.this, SelectFileActivity.class), code_file);
                break;
            case R.id.view_add_fit:
                startActivity(new Intent(AddRecordActivity.this, FitManagerActivity.class));
                break;
            default:
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == code_file && resultCode == RESULT_OK) {
            assert data != null;
            String result = data.getStringExtra("result");
            isUpLoad = true;
            mActivityAddRecordBinding.tvFileName.setVisibility(View.VISIBLE);
            mActivityAddRecordBinding.tvFileName.setText(result);
            mActivityAddRecordBinding.tvSave.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_main_4));
            mActivityAddRecordBinding.tvSave.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    /**
     * 上传文件
     */
    private void uploadFile() {
        String filePath = mActivityAddRecordBinding.tvFileName.getText().toString().trim();
        if (TextUtils.isEmpty(filePath)) {
            ToastUtils.show(this, getString(R.string.select_correct_file));
            return;
        }
        byte[] bytesByFile = AppUtils.getBytesByFile(filePath);
        if (bytesByFile != null) {
            String[] split = filePath.split("/");

            uploadFitFile(bytesByFile, recordType, split[split.length - 1]);
        } else {
            ToastUtils.show(this, getString(R.string.select_correct_file));
        }
    }

    /**
     * 上传历史记录文件
     */
    private void uploadFitFile(byte[] file, int motionType, String name) {
        Map<String, RequestBody> mapParams = new HashMap<>(8);
        RequestBody sessionBody = RequestBody.create(MediaType.parse("multipart/form-data"), getUserInfoEntity().getSession());
        mapParams.put("session", sessionBody);

        RequestBody motionTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(motionType));
        mapParams.put("motionType", motionTypeBody);

        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part myBody = MultipartBody.Part.createFormData("uploadFile", name, fileBody);

        RequestBody mDataTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
        mapParams.put("dataType", mDataTypeBody);
        RequestBody dataSource = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(3));
        mapParams.put("dataSource", dataSource);
        progress = 0;
        mHandler.sendEmptyMessage(0);

        RetrofitUtils.getApiUrl().uploadFitFile(mapParams, myBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mHandler.removeMessages(0);
                        mHandler.sendEmptyMessage(1);
                        clearData();
                        ToastUtils.show(AddRecordActivity.this, getString(R.string.uploaded_successfully));
                        String path = getExternalFilesDir("") + File.separator + ".myfit/" + getUserId() + name;
                        FileUploadEntity fileUploadEntity = DbUtils.getInstance().queryFileUploadEntityByName(getUserId(), path);
                        if (fileUploadEntity == null) {
                            fileUploadEntity = new FileUploadEntity();
                            fileUploadEntity.setUserId(getUserId());
                            fileUploadEntity.setSportsType(motionType);
                            fileUploadEntity.setFileName(name);
                            fileUploadEntity.setProductNo(null);
                            fileUploadEntity.setTimeZone(AppUtils.getTimeZone());
                            fileUploadEntity.setFilePath(path);
                            DbUtils.getInstance().addFileUploadEntity(fileUploadEntity);
                        } else {
                            fileUploadEntity.setUserId(getUserId());
                            fileUploadEntity.setSportsType(motionType);
                            fileUploadEntity.setFileName(name);
                            fileUploadEntity.setProductNo(null);
                            fileUploadEntity.setTimeZone(AppUtils.getTimeZone());
                            fileUploadEntity.setFilePath(path);
                            DbUtils.getInstance().updateFileUploadEntity(fileUploadEntity);
                        }
                        AppUtils.saveBytesToFile(path, file);

                        StrWork(getUserInfoEntity().getSession(), getUserId(), path);
                        KomWork(getUserInfoEntity().getSession(), getUserId(), path);
                        TPWork(getUserInfoEntity().getSession(), getUserId(), path);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mHandler.removeMessages(0);
                        mHandler.sendEmptyMessage(1);
                        clearData();
                        ToastUtils.showError(AddRecordActivity.this, resultCode);
                    }
                });
    }

    private float progress;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                progress = progress + 0.05f;
                if (progress < 1) {
                    int height = mActivityAddRecordBinding.ivUpload.getLayoutParams().height;
                    ViewGroup.LayoutParams layoutParams = mActivityAddRecordBinding.viewProgress.getLayoutParams();
                    layoutParams.height = (int) (height * progress);
                    mActivityAddRecordBinding.viewProgress.setVisibility(View.VISIBLE);
                    mActivityAddRecordBinding.viewProgress.setLayoutParams(layoutParams);
                    mHandler.sendEmptyMessageDelayed(0, 500);
                }
            } else {
                ViewGroup.LayoutParams layoutParams = mActivityAddRecordBinding.viewProgress.getLayoutParams();
                layoutParams.height = 1;
                mActivityAddRecordBinding.viewProgress.setLayoutParams(layoutParams);
                mActivityAddRecordBinding.viewProgress.setVisibility(View.INVISIBLE);
            }
        }
    };

    /**
     * 清除缓存
     */
    private void clearData() {
        isUpLoad = false;
        mActivityAddRecordBinding.tvFileName.setVisibility(View.INVISIBLE);
        mActivityAddRecordBinding.tvSave.setBackground(ContextCompat.getDrawable(AddRecordActivity.this, R.drawable.shape_f2_4));
        mActivityAddRecordBinding.tvSave.setTextColor(Color.parseColor("#FFB5B5B5"));
    }

    @SuppressLint("RestrictedApi")
    private void StrWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(this)
                .enqueue(new OneTimeWorkRequest.Builder(StravaUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    @SuppressLint("RestrictedApi")
    private void KomWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(this)
                .enqueue(new OneTimeWorkRequest.Builder(KomootUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    @SuppressLint("RestrictedApi")
    private void TPWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(this)
                .enqueue(new OneTimeWorkRequest.Builder(TPUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }
}