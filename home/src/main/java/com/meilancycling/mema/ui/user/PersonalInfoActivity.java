package com.meilancycling.mema.ui.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.command.BleCommandManager;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.customview.dialog.SelectGenderDialog;
import com.meilancycling.mema.customview.dialog.SelectImageDialog;
import com.meilancycling.mema.databinding.ActivityPersonalInfoBinding;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.network.bean.request.EditUserInformation;
import com.meilancycling.mema.network.bean.response.UserHeadResponse;
import com.meilancycling.mema.ui.details.MyTakePhoto;
import com.meilancycling.mema.ui.setting.ChooseCountryActivity;
import com.meilancycling.mema.ui.setting.view.HeightImperialDialog;
import com.meilancycling.mema.ui.setting.view.HeightMetricDialog;
import com.meilancycling.mema.ui.setting.view.WeightDialog;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.GlideUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * ????????????
 *
 * @author lion
 */
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private ActivityPersonalInfoBinding mActivityPersonalInfoBinding;
    private UserInfoEntity mUserInfoEntity;
    private String nationalFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPersonalInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info);
        mActivityPersonalInfoBinding.ctvPersonal.setData(getString(R.string.personal_information), this);
        initView();
        mActivityPersonalInfoBinding.llPersonalAvatar.setOnClickListener(this);
        mActivityPersonalInfoBinding.uivNickname.setOnClickListener(this);
        mActivityPersonalInfoBinding.uivGender.setOnClickListener(this);
        mActivityPersonalInfoBinding.uivHeight.setOnClickListener(this);
        mActivityPersonalInfoBinding.uivBodyWeight.setOnClickListener(this);
        mActivityPersonalInfoBinding.uivBirthday.setOnClickListener(this);
        mActivityPersonalInfoBinding.uivArea.setOnClickListener(this);
    }


    private void initView() {
        mUserInfoEntity = getUserInfoEntity();
        GlideUtils.loadCircleImage(mUserInfoEntity.getHeadUrl(), this, mActivityPersonalInfoBinding.ivAvatar);
        mActivityPersonalInfoBinding.uivNickname.setItemData(getString(R.string.nickname), mUserInfoEntity.getNickname());
        if (mUserInfoEntity.getGender() == 1) {
            mActivityPersonalInfoBinding.uivGender.setItemData(getString(R.string.gender), getString(R.string.man));
        } else {
            mActivityPersonalInfoBinding.uivGender.setItemData(getString(R.string.gender), getString(R.string.woman));
        }
        UnitBean heightBean = UnitConversionUtil.getUnitConversionUtil().heightSetting(this, mUserInfoEntity.getHeight());
        mActivityPersonalInfoBinding.uivHeight.setItemData(getString(R.string.height), heightBean.getValue() + heightBean.getUnit());
        UnitBean weightBean = UnitConversionUtil.getUnitConversionUtil().weightSetting(this, mUserInfoEntity.getWeight());
        mActivityPersonalInfoBinding.uivBodyWeight.setItemData(getString(R.string.weight), weightBean.getValue() + weightBean.getUnit());
        mActivityPersonalInfoBinding.uivBirthday.setItemData(getString(R.string.birthday), mUserInfoEntity.getBirthday());

        if (mUserInfoEntity.getCountry() != null) {
            String[] split = mUserInfoEntity.getCountry().split("&");
            if (split.length == 2) {
                if (AppUtils.isChinese()) {
                    mActivityPersonalInfoBinding.uivArea.setItemData(getString(R.string.area), split[0]);
                } else {
                    mActivityPersonalInfoBinding.uivArea.setItemData(getString(R.string.area), split[1]);
                }
            }
        } else {
            mActivityPersonalInfoBinding.uivArea.setItemData(getString(R.string.area), mUserInfoEntity.getCountry());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //??????????????????????????????EasyPermissions??????
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

    private int code = 102;
    private int codeNickname = 103;
    private int country = 104;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_common_back:
                finish();
                break;
            case R.id.ll_personal_avatar:
                String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                if (EasyPermissions.hasPermissions(PersonalInfoActivity.this, perms)) {
                    // ????????????
                    showSelectDialog();
                } else {
                    //????????????????????????????????????????????????????????????
                    //???????????????????????????
                    //????????????????????????????????????
                    EasyPermissions.requestPermissions(PersonalInfoActivity.this, getResources().getString(R.string.authorization), 0, perms);
                }
                break;
            case R.id.uiv_nickname:
                startActivityForResult(new Intent(this, ModifyNickActivity.class), codeNickname);
                break;
            case R.id.uiv_gender:
                SelectGenderDialog selectGenderDialog = new SelectGenderDialog(this, mUserInfoEntity.getGender());
                selectGenderDialog.show();
                selectGenderDialog.setSelectGenderClickListener((genderString, gender) -> {
                    mActivityPersonalInfoBinding.uivGender.setItemData(getString(R.string.gender), genderString);
                    mUserInfoEntity.setGender(gender);
                    updateData(mUserInfoEntity);
                });
                break;
            case R.id.uiv_height:
                if (Config.unit == Unit.METRIC.value) {
                    HeightMetricDialog heightMetricDialog = new HeightMetricDialog(this, mUserInfoEntity.getHeight());
                    heightMetricDialog.show();
                    heightMetricDialog.setHeightCallback((result, value) -> {
                        mActivityPersonalInfoBinding.uivHeight.setItemData(getString(R.string.height), value + getString(R.string.unit_cm));
                        mUserInfoEntity.setHeight(result);
                        updateData(mUserInfoEntity);
                    });
                } else {
                    HeightImperialDialog heightImperialDialog = new HeightImperialDialog(this, mUserInfoEntity.getHeight());
                    heightImperialDialog.show();
                    heightImperialDialog.setHeightImperialCallback((result, value) -> {
                        mActivityPersonalInfoBinding.uivHeight.setItemData(getString(R.string.height), value + getString(R.string.unit_feet));
                        mUserInfoEntity.setHeight(result);
                        updateData(mUserInfoEntity);
                    });
                }
                break;
            case R.id.uiv_birthday:
                long current = AppUtils.timeToLong(mUserInfoEntity.getBirthday(), Config.DEFAULT_PATTERN);
                long endTime = System.currentTimeMillis();
                long startTime = AppUtils.timeToLong("1900-01-01", Config.DEFAULT_PATTERN);
                dateDialog(this, current, startTime, endTime, listener);
                break;
            case R.id.uiv_body_weight:
                WeightDialog weightDialog = new WeightDialog(this, mUserInfoEntity.getWeight());
                weightDialog.show();
                weightDialog.setWeightCallback((result, value) -> {
                    if (Config.unit == Unit.METRIC.value) {
                        mActivityPersonalInfoBinding.uivBodyWeight.setItemData(getString(R.string.weight), value + getString(R.string.unit_kg));
                    } else {
                        mActivityPersonalInfoBinding.uivBodyWeight.setItemData(getString(R.string.weight), value + getString(R.string.unit_lb));
                    }
                    mUserInfoEntity.setWeight(result);
                    updateData(mUserInfoEntity);
                });
                break;
            case R.id.uiv_area:
                startActivityForResult(new Intent(this, ChooseCountryActivity.class), country);
                break;
            default:
        }
    }

    private TimePickerView mTimePickerView;

    /**
     * ???????????????
     */
    public void dateDialog(Context context, long time, long startTime, long endTime, OnTimeSelectListener listener) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTimeInMillis(time);
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(startTime);
        //??????????????????
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));

        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(endTime);
        //??????????????????
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));

        mTimePickerView = new TimePickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom, v -> {
                    TextView title = v.findViewById(R.id.tv_cancel);
                    TextView confirm = v.findViewById(R.id.tv_finish);
                    confirm.setOnClickListener(v1 -> {
                        mTimePickerView.dismiss();
                        mTimePickerView.returnData();
                    });
                    title.setOnClickListener(v12 -> mTimePickerView.dismiss());
                })
                //???????????????????????????????????????
                .setLabel("", "", "", "", "", "")
                .setType(new boolean[]{true, true, true, false, false, false})
                //??????????????????
                .setContentTextSize(18)
                //?????????????????? Night mode
                .setBgColor(ContextCompat.getColor(PersonalInfoActivity.this, R.color.white))
                //???????????????
                .setDividerColor(Color.parseColor("#FFDCDCDC"))
                .setTextColorCenter(ContextCompat.getColor(PersonalInfoActivity.this, R.color.black_3))
                .setOutSideCancelable(false)
                .setItemVisibleCount(5)
                .setLineSpacingMultiplier(2)
                // ?????????????????????????????????????????????*/
                .setDate(selectedDate)
                //???????????????????????????
                .setRangDate(startDate, endDate)
                //??????????????????????????????
                .isDialog(false)
                .setGravity(Gravity.CENTER)
                .build();
        mTimePickerView.show();
    }

    @SuppressLint("SimpleDateFormat")
    private OnTimeSelectListener listener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            SimpleDateFormat formatter = new SimpleDateFormat(Config.DEFAULT_PATTERN);
            mUserInfoEntity.setBirthday(formatter.format(date));
            updateUserInfoEntity(mUserInfoEntity);
            mActivityPersonalInfoBinding.uivBirthday.setItemData(getString(R.string.birthday), mUserInfoEntity.getBirthday());
            updateData(mUserInfoEntity);
        }
    };

    private void showSelectDialog() {
        SelectImageDialog selectImageDialog = new SelectImageDialog(this);
        selectImageDialog.setCanceledOnTouchOutside(false);
        selectImageDialog.show();
        selectImageDialog.setSelectImageDialogClickListener(new SelectImageDialog.SelectImageDialogClickListener() {
            @Override
            public void clickPictures() {
                Intent intent = new Intent(PersonalInfoActivity.this, MyTakePhoto.class);
                intent.putExtra(MyTakePhoto.TAKEPHOTO_TYPE, MyTakePhoto.ON_TAKEPHOTOS);
                startActivityForResult(intent, code);
            }

            @Override
            public void clickAlbum() {
                Intent intent = new Intent(PersonalInfoActivity.this, MyTakePhoto.class);
                intent.putExtra(MyTakePhoto.TAKEPHOTO_TYPE, MyTakePhoto.ON_SELECTPICTURES);
                startActivityForResult(intent, code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code) {
            if (data != null) {
                String extra = data.getStringExtra("TakePhotosActivity_image");
                if (extra != null) {
                    uploadPic(new File(extra));
                }
            } else {
                finish();
            }
        } else if (requestCode == codeNickname) {
            if (data != null) {
                String result = data.getStringExtra("result");
                mUserInfoEntity.setNickname(result);
                mActivityPersonalInfoBinding.uivNickname.setItemData(getString(R.string.nickname), mUserInfoEntity.getNickname());
                updateData(mUserInfoEntity);
            }
        } else if (requestCode == country) {
            if (data != null) {
                String result = data.getStringExtra("result");
                nationalFlag = data.getStringExtra("nationalFlag");
                mUserInfoEntity.setCountry(result);
                if (mUserInfoEntity.getCountry() != null) {
                    String[] split = mUserInfoEntity.getCountry().split("&");
                    if (split.length == 2) {
                        if (AppUtils.isChinese()) {
                            mActivityPersonalInfoBinding.uivArea.setItemData(getString(R.string.area), split[0]);
                        } else {
                            mActivityPersonalInfoBinding.uivArea.setItemData(getString(R.string.area), split[1]);
                        }
                    }
                } else {
                    mActivityPersonalInfoBinding.uivArea.setItemData(getString(R.string.area), mUserInfoEntity.getCountry());
                }
                updateData(mUserInfoEntity);
            }
        }
    }

    /**
     * ????????????
     */
    public void uploadPic(File file) {
        showLoadingDialog();
        Map<String, RequestBody> mapParams = new HashMap<>(8);
        RequestBody sessionBody = RequestBody.create(MediaType.parse("multipart/form-data"), getUserInfoEntity().getSession());
        mapParams.put("session", sessionBody);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part myBody = MultipartBody.Part.createFormData("uploadFile", file.getName(), fileBody);

        RetrofitUtils.getApiUrl().uploadPic(mapParams, myBody)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<UserHeadResponse>() {
                    @Override
                    public void onSuccess(UserHeadResponse userHeadResponse) {
                        hideLoadingDialog();
                        UserInfoEntity userInfoEntity = getUserInfoEntity();
                        userInfoEntity.setHeadUrl(userHeadResponse.getHeaderUrl());
                        GlideUtils.loadCircleImage(userHeadResponse.getHeaderUrl(), PersonalInfoActivity.this, mActivityPersonalInfoBinding.ivAvatar);
                        updateUserInfoEntity(userInfoEntity);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });
    }

    /**
     * ????????????
     */
    private void updateData(UserInfoEntity mUserInfoEntity) {
        //????????????
        updateUserInfoEntity(mUserInfoEntity);
        //????????????
        String birthday = mUserInfoEntity.getBirthday();
        String[] split = birthday.split("-");
        CommandEntity commandEntity = BleCommandManager.getInstance().userInfo(mUserInfoEntity.getGender(), mUserInfoEntity.getHeight(), mUserInfoEntity.getWeight(), Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        sendCommandData(commandEntity);
        EditUserInformation editUserInformation = new EditUserInformation();
        editUserInformation.setSession(mUserInfoEntity.getSession());
        editUserInformation.setCountry(mUserInfoEntity.getCountry());
        editUserInformation.setBirthday(mUserInfoEntity.getBirthday());
        editUserInformation.setHeight(String.valueOf(mUserInfoEntity.getHeight()));
        editUserInformation.setNickName(mUserInfoEntity.getNickname());
        editUserInformation.setSex(String.valueOf(mUserInfoEntity.getGender()));
        editUserInformation.setWeight(String.valueOf(mUserInfoEntity.getWeight()));
        if (nationalFlag != null) {
            editUserInformation.setNationalFlag(nationalFlag);
        }

        RetrofitUtils.getApiUrl()
                .editUserInformation(editUserInformation)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object object) {

                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {

                    }
                });
    }
}