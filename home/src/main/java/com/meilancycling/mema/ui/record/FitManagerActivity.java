package com.meilancycling.mema.ui.record;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityFitManagerBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.FileUploadEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.ui.adapter.FitManagerAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.work.FileUploadWork;
import com.meilancycling.mema.work.KomootUploadWork;
import com.meilancycling.mema.work.SensorUploadWork;
import com.meilancycling.mema.work.StravaUploadWork;
import com.meilancycling.mema.work.TPUploadWork;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FitManagerActivity extends BaseActivity implements View.OnClickListener {
    private ActivityFitManagerBinding mActivityFitManagerBinding;
    private boolean isSelect;
    private FitManagerAdapter mFitManagerAdapter;
    private List<FileUploadEntity> fileUploadEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSelect = false;
        mActivityFitManagerBinding = DataBindingUtil.setContentView(this, R.layout.activity_fit_manager);
        mActivityFitManagerBinding.viewBack.setOnClickListener(this);
        mActivityFitManagerBinding.viewSelect.setOnClickListener(this);
        mActivityFitManagerBinding.delete.setOnClickListener(this);
        showRightUi();

        fileUploadEntities = DbUtils.getInstance().queryFileUploadEntity(getUserId());
        if (fileUploadEntities == null || fileUploadEntities.size() == 0) {
            mActivityFitManagerBinding.viewBottom.setVisibility(View.GONE);
            mActivityFitManagerBinding.delete.setVisibility(View.GONE);
            mActivityFitManagerBinding.viewSelect.setVisibility(View.GONE);
            mActivityFitManagerBinding.tvRight.setVisibility(View.GONE);
        }
        mFitManagerAdapter = new FitManagerAdapter(fileUploadEntities);
        mActivityFitManagerBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mActivityFitManagerBinding.rv.setSwipeMenuCreator(mSwipeMenuCreator);
        mActivityFitManagerBinding.rv.setOnItemMenuClickListener(mItemMenuClickListener);
        mActivityFitManagerBinding.rv.setAdapter(mFitManagerAdapter);
        mFitManagerAdapter.setFitManagerClickListener(new FitManagerAdapter.FitManagerClickListener() {
            @Override
            public void clickUpload(FileUploadEntity fileUploadEntity) {
                uploadFile(fileUploadEntity);
            }

            @Override
            public void clickShare(FileUploadEntity fileUploadEntity) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                if (Build.VERSION.SDK_INT >= 24) {//7.0 Android N
                    Uri uri = FileProvider.getUriForFile(FitManagerActivity.this, "com.meilancycling.mema.fileProvider", new File(fileUploadEntity.getFilePath()));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                } else {//7.0以下
                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(fileUploadEntity.getFilePath())));
                }
                shareIntent.setType("*/*");//此处可发送多种文件
                startActivity(Intent.createChooser(shareIntent, "share"));
            }
        });

    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        SwipeMenuItem competitionItem = new SwipeMenuItem(this)
                .setWidth(AppUtils.dipToPx(this, 84))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.delete_red))
                .setText(getResources().getString(R.string.delete))
                .setTextColor(getResources().getColor(R.color.white))
                .setTextSize(15)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 添加菜单到右侧。
        swipeRightMenu.addMenuItem(competitionItem);
    };
    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    private OnItemMenuClickListener mItemMenuClickListener = (menuBridge, position) -> {
        menuBridge.closeMenu();
        FileUploadEntity fileUploadEntity = fileUploadEntities.get(position);
        DbUtils.getInstance().deleteFileUploadEntity(fileUploadEntity);
        try {
            File file = new File(fileUploadEntity.getFilePath());
            file.delete();
        } catch (Exception e) {

        }
        fileUploadEntities.remove(position);
        mFitManagerAdapter.deleteItem(fileUploadEntity);
        if (fileUploadEntities == null || fileUploadEntities.size() == 0) {
            mActivityFitManagerBinding.viewSelect.setVisibility(View.GONE);
            mActivityFitManagerBinding.tvRight.setVisibility(View.GONE);
        }
    };

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_back:
                finish();
                break;
            case R.id.view_select:
                isSelect = !isSelect;
                mFitManagerAdapter.selectClick(isSelect);
                showRightUi();
                break;
            case R.id.delete:
                List<String> selectList = mFitManagerAdapter.getSelectList();
                if (selectList.size() > 0) {
                    for (String s : selectList) {
                        for (FileUploadEntity fileUploadEntity : fileUploadEntities) {
                            if (fileUploadEntity.getFileName().equals(s)) {
                                DbUtils.getInstance().deleteFileUploadEntity(fileUploadEntity);
                                try {
                                    File file = new File(fileUploadEntity.getFilePath());
                                    file.delete();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                    fileUploadEntities = DbUtils.getInstance().queryFileUploadEntity(getUserId());
                    mFitManagerAdapter.updateList(fileUploadEntities);
                    isSelect = false;
                    mFitManagerAdapter.selectClick(false);
                    showRightUi();

                    if (fileUploadEntities == null || fileUploadEntities.size() == 0) {
                        mActivityFitManagerBinding.viewSelect.setVisibility(View.GONE);
                        mActivityFitManagerBinding.tvRight.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.show(FitManagerActivity.this, getString(R.string.select_delete_file));
                }
                break;
        }
    }

    private void showRightUi() {
        if (isSelect) {
            mActivityFitManagerBinding.viewBottom.setVisibility(View.VISIBLE);
            mActivityFitManagerBinding.delete.setVisibility(View.VISIBLE);
            mActivityFitManagerBinding.tvRight.setText(R.string.cancel);
        } else {
            mActivityFitManagerBinding.viewBottom.setVisibility(View.GONE);
            mActivityFitManagerBinding.delete.setVisibility(View.GONE);
            mActivityFitManagerBinding.tvRight.setText(R.string.select);
        }
    }

    /**
     * 上传文件
     */
    private void uploadFile(FileUploadEntity fileUploadEntity) {

        byte[] bytesByFile = AppUtils.getBytesByFile(fileUploadEntity.getFilePath());
        if (bytesByFile != null) {
            uploadFitFile(bytesByFile, fileUploadEntity.getSportsType(), fileUploadEntity.getFileName(), fileUploadEntity.getFilePath());
        }
    }

    /**
     * 上传历史记录文件
     */
    private void uploadFitFile(byte[] file, int motionType, String name, String path) {
        showLoadingDialog();
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

        RetrofitUtils.getApiUrl().uploadFitFile(mapParams, myBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String result) {
                        hideLoadingDialog();
                        ToastUtils.show(FitManagerActivity.this, getString(R.string.uploaded_successfully));

                        StrWork(getUserInfoEntity().getSession(), getUserId(), path);
                        KomWork(getUserInfoEntity().getSession(), getUserId(), path);
                        TPWork(getUserInfoEntity().getSession(), getUserId(), path);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.showError(FitManagerActivity.this, resultCode);
                    }
                });
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