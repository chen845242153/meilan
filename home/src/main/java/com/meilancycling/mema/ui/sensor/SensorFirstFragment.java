package com.meilancycling.mema.ui.sensor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.lion.common.eventbus.MessageEvent;
import com.lion.common.eventbus.sensor.SensorMessageType;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseFragment;
import com.meilancycling.mema.ble.sensor.JniBleController;
import com.meilancycling.mema.constant.BroadcastConstant;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.SensorType;
import com.meilancycling.mema.databinding.FragmentSensorFirstBinding;
import com.meilancycling.mema.db.DbUtils;
import com.meilancycling.mema.db.FileUploadEntity;
import com.meilancycling.mema.db.HeartZoneEntity;
import com.meilancycling.mema.db.PowerZoneEntity;
import com.meilancycling.mema.db.UserInfoEntity;
import com.meilancycling.mema.db.WarningEntity;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.service.SensorControllerService;
import com.meilancycling.mema.ui.adapter.SensorPageAdapter;
import com.meilancycling.mema.ui.details.DetailsHomeActivity;
import com.meilancycling.mema.ui.sensor.view.DistanceFinishDialog;
import com.meilancycling.mema.ui.sensor.view.SensorBottomView;
import com.meilancycling.mema.ui.sensor.view.SensorLapDialog;
import com.meilancycling.mema.ui.sensor.view.SensorResultDialog;
import com.meilancycling.mema.ui.sensor.view.TimeFinishDialog;
import com.meilancycling.mema.ui.sensor.view.WarningDialog;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.SPUtils;
import com.meilancycling.mema.utils.StatusAppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.meilancycling.mema.work.FileUploadWork;
import com.meilancycling.mema.work.KomootUploadWork;
import com.meilancycling.mema.work.SensorUploadWork;
import com.meilancycling.mema.work.StravaUploadWork;
import com.meilancycling.mema.work.TPUploadWork;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Description: 作用描述
 * @Author: lion 571135591
 * @CreateDate: 2021/6/17 5:56 下午
 */
public class SensorFirstFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 0 模式一 两个
     * 1 模式二 四个
     * 2 模式三 六个
     * 3 模式三 十个
     */
    public int sensorType;
    /**
     * 是否正在运动
     */
    public boolean isRunning;
    private List<Fragment> mFragments = new ArrayList<>();
    private SensorHomeFragment mSensorHomeFragment;
    private CurrentFragment mCurrentFragment;
    private PreviousFragment mPreviousFragment;
    private HeartRateFragment mHeartRateFragment;
    private PowerFragment mPowerFragment;
    private SensorPageAdapter mSensorPageAdapter;
    public List<SensorType> showSensorTypeList = new ArrayList();
    private int motionType;
    /**
     * 当前显示的页面
     */
    private int currentSelect;
    public PowerZoneEntity mPowerZoneEntity;
    public HeartZoneEntity mHeartZoneEntity;
    public WarningEntity mWarningEntity;

    /**
     * 是否在显示弹窗
     */
    private boolean isShowDialog;
    /**
     * 弹窗类型
     * 1 时间
     * 2 距离
     * 3 速度
     * 4 踏频
     * 5 心率
     * 6 功率
     */
    private int showType;
    private boolean timeIsFinish;
    private boolean distanceIsFinish;
    private long speedTime;
    private long cadenceTime;
    private long hrTime;
    private long powerTime;

    private FragmentSensorFirstBinding mFragmentSensorFirstBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSensorFirstBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor_first, container, false);
        return mFragmentSensorFirstBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isShowDialog = false;
        timeIsFinish = false;
        distanceIsFinish = false;
        showType = 0;
        motionType = JniBleController.getBleController().E_CYCLING_MODE_INDOOR;
        currentSelect = 0;
        mHandler.sendEmptyMessage(0);
        mPowerZoneEntity = DbUtils.getInstance().queryPowerZoneEntity(mActivity.getUserId());
        mHeartZoneEntity = DbUtils.getInstance().queryHeartZoneEntity(mActivity.getUserId());
        mWarningEntity = DbUtils.getInstance().queryWarningEntity(mActivity.getUserId());

        SensorBottomModel sensorBottomModel = new SensorBottomModel();
        sensorBottomModel.setSportStatus(0);
        sensorBottomModel.setLapNumber(1);
        sensorBottomModel.setLock(false);
        sensorBottomModel.setSportType(0);

        mFragmentSensorFirstBinding.sensorBottomView.bindData(sensorBottomModel);
        init();
        try {
            mTextToSpeech = new TextToSpeech(getContext(), status -> {
            });
        } catch (Exception e) {
        }

        EventBus.getDefault().register(this);
        if (mWarningEntity.getBrightScreen() == 0) {
            mFragmentSensorFirstBinding.tbSensorScreen.setChecked(false);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            mFragmentSensorFirstBinding.tbSensorScreen.setChecked(true);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        mFragmentSensorFirstBinding.tbSensorScreen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mWarningEntity.setBrightScreen(1);
            } else {
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mWarningEntity.setBrightScreen(0);
            }
            DbUtils.getInstance().updateWarningEntity(mWarningEntity);
        });

        mFragmentSensorFirstBinding.tbSensorSpeech.setChecked(mWarningEntity.getVoiceSwitch() != 0);

        mFragmentSensorFirstBinding.tbSensorSpeech.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mWarningEntity.setVoiceSwitch(1);
            } else {
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mWarningEntity.setVoiceSwitch(0);
            }
            DbUtils.getInstance().updateWarningEntity(mWarningEntity);
        });

        mFragmentSensorFirstBinding.sensorBottomView.setSensorBottomViewClickListener(new SensorBottomView.SensorBottomViewClickListener() {
            @Override
            public void clickBack() {
                stopMotionModule();
                mActivity.finish();
            }

            @Override
            public void clickStart() {
                speedTime = System.currentTimeMillis() + 50 * 1000;
                cadenceTime = System.currentTimeMillis() + 60 * 1000;
                hrTime = System.currentTimeMillis() + 60 * 1000;
                powerTime = System.currentTimeMillis() + 60 * 1000;
                startExercise();
            }

            @Override
            public void clickCarryOn() {
                isRunning = true;
                EventBus.getDefault().post(new MessageEvent(SensorMessageType.ACTION_CARRY_ON_MOVEMENT, SensorMessageType.ACTION_CARRY_ON_MOVEMENT));
            }

            @Override
            public void clickChangeType(int type) {
                //室内
                if (type == 0) {
                    EventBus.getDefault().post(new MessageEvent(JniBleController.getBleController().E_CYCLING_MODE_INDOOR, SensorMessageType.ACTION_TYPE_ACTIVITY));
                    motionType = JniBleController.getBleController().E_CYCLING_MODE_INDOOR;
                    mFragmentSensorFirstBinding.viewItem1.setVisibility(View.INVISIBLE);
                } else {
                    EventBus.getDefault().post(new MessageEvent(JniBleController.getBleController().E_CYCLING_MODE_OUTDOOR, SensorMessageType.ACTION_TYPE_ACTIVITY));
                    motionType = JniBleController.getBleController().E_CYCLING_MODE_OUTDOOR;
                    mFragmentSensorFirstBinding.viewItem1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void clickLapNumber(int number) {
                SensorLapDialog sensorLapDialog = new SensorLapDialog(getActivity(), getString(R.string.road_section) + (number - 1), SensorControllerService.lapData.getElapsed_time(),
                        SensorControllerService.lapData.getDist_travelled(), (double) SensorControllerService.lapData.getMax_speed() / 10);
                sensorLapDialog.show();
                mHandler.postDelayed(sensorLapDialog::dismiss, 2000);
                if (number == 2) {
                    if (mFragmentSensorFirstBinding.viewItem1.getVisibility() == View.VISIBLE) {
                        mFragmentSensorFirstBinding.viewItem0.setVisibility(View.VISIBLE);
                    } else {
                        mFragmentSensorFirstBinding.viewItem1.setVisibility(View.VISIBLE);
                    }
                    mFragmentSensorFirstBinding.viewItem5.setVisibility(View.VISIBLE);
                    mCurrentFragment = new CurrentFragment();
                    mPreviousFragment = new PreviousFragment();
                    mFragments.add(1, mCurrentFragment);
                    mFragments.add(2, mPreviousFragment);
                    mSensorPageAdapter = new SensorPageAdapter(getActivity(), mFragments);
                    mFragmentSensorFirstBinding.vpSensor.setAdapter(mSensorPageAdapter);
                }
                EventBus.getDefault().post(new MessageEvent(SensorMessageType.ACTION_LAP_MOVEMENT, SensorMessageType.ACTION_LAP_MOVEMENT));
            }

            @Override
            public void clickPause() {
                isRunning = false;
                EventBus.getDefault().post(new MessageEvent(SensorMessageType.ACTION_SUSPEND_MOVEMENT, SensorMessageType.ACTION_SUSPEND_MOVEMENT));
            }

            @Override
            public void clickStop() {
                showResultDialog();
            }

            @Override
            public void clickLock(boolean status) {
                if (status) {
                    mFragmentSensorFirstBinding.viewLock.setVisibility(View.VISIBLE);
                } else {
                    mFragmentSensorFirstBinding.viewLock.setVisibility(View.GONE);
                }
            }
        });

        UserInfoEntity userInfoEntity = mActivity.getUserInfoEntity();
        if (((userInfoEntity.getGuideFlag() & 4) >> 2) == Config.NEED_GUIDE) {
            StatusAppUtils.setColor(mActivity, ContextCompat.getColor(mActivity, R.color.guide_bg));
            mFragmentSensorFirstBinding.groupSensorGuide.setVisibility(View.VISIBLE);
        }
        mFragmentSensorFirstBinding.viewGuide1.setOnClickListener(v -> {
            StatusAppUtils.setColor(mActivity, ContextCompat.getColor(mActivity, R.color.white));
            mFragmentSensorFirstBinding.groupSensorGuide.setVisibility(View.GONE);
            int guideFlag = userInfoEntity.getGuideFlag();
            userInfoEntity.setGuideFlag(guideFlag & 0xFB);
            mActivity.updateUserInfoEntity(userInfoEntity);
        });
    }

    private TimeFinishDialog timeFinishDialog;
    private DistanceFinishDialog distanceFinishDialog;
    private WarningDialog warningDialog;
    private TextToSpeech mTextToSpeech;

    private void voiceBroadcast(String content) {
        if (mTextToSpeech != null) {
            int result = mTextToSpeech.setLanguage(getResources().getConfiguration().locale);
            if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
//                Toast.makeText(mContext, "TTS暂时不支持这种语音的朗读！", Toast.LENGTH_SHORT).show();
            } else {
                mTextToSpeech.speak(content, TextToSpeech.QUEUE_ADD, null);
            }
        }
    }

    private boolean mIsScrolled;

    private void init() {
        showSensorTypeList = new ArrayList<>();
        String type = SPUtils.getString(getContext(), SPUtils.SENSOR_TYPE_GROUP);
        if (TextUtils.isEmpty(type)) {
            SPUtils.putString(getContext(), SPUtils.SENSOR_TYPE_GROUP, "3,6,4,7,9,12,2,0,1,17");
            type = "3,6,4,7,9,12,2,0,1,17";
        }
        String[] split = type.split(",");
        for (String s : split) {
            showSensorTypeList.add(getSensorType(Integer.parseInt(s)));
        }
        int anInt = SPUtils.getInt(getContext(), SPUtils.SENSOR_TYPE, -1);
        if (anInt == -1) {
            SPUtils.putInt(getContext(), SPUtils.SENSOR_TYPE, 3);
            sensorType = 3;
        } else {
            sensorType = anInt;
        }
        Log.i("sore", "sensorType=" + sensorType);
        mFragmentSensorFirstBinding.viewSensor.setOnClickListener(this);
        mFragmentSensorFirstBinding.ivSensorType.setOnClickListener(this);
        mFragmentSensorFirstBinding.viewSetting.setOnClickListener(this);
        mFragmentSensorFirstBinding.viewBg.setOnClickListener(this);
        mSensorHomeFragment = new SensorHomeFragment(showSensorTypeList);
        mHeartRateFragment = new HeartRateFragment();
        mPowerFragment = new PowerFragment();
        mSensorHomeFragment.changeType(0, showSensorTypeList);
        mFragments.add(mSensorHomeFragment);
        mFragments.add(mHeartRateFragment);
        mFragments.add(mPowerFragment);
        mSensorPageAdapter = new SensorPageAdapter(getActivity(), mFragments);
        mFragmentSensorFirstBinding.vpSensor.setAdapter(mSensorPageAdapter);
        mFragmentSensorFirstBinding.vpSensor.registerOnPageChangeCallback(mOnPageChangeCallback);
        showSensorType();
    }

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (position == 0) {
                mFragmentSensorFirstBinding.ivSensorType.setVisibility(View.VISIBLE);
            } else {
                mFragmentSensorFirstBinding.ivSensorType.setVisibility(View.GONE);
            }
            switch (position) {
                case 0:
                    currentSelect = 0;
                    mHandler.sendEmptyMessage(1);
                    mFragmentSensorFirstBinding.tvTitle.setText(getString(R.string.home_item2));
                    if (mFragmentSensorFirstBinding.sensorBottomView.getData().getLapNumber() > 1) {
                        showSelectView(0);
                    } else {
                        showSelectView(1);
                    }
                    break;
                case 1:
                    if (mFragmentSensorFirstBinding.sensorBottomView.getData().getLapNumber() > 1) {
                        currentSelect = 1;
                        mFragmentSensorFirstBinding.tvTitle.setText(getString(R.string.current_section));
                        showSelectView(1);
                    } else {
                        currentSelect = 3;
                        mFragmentSensorFirstBinding.tvTitle.setText(getString(R.string.heart_rate));
                        showSelectView(2);
                    }
                    mHandler.sendEmptyMessage(1);
                    break;
                case 2:
                    if (mFragmentSensorFirstBinding.sensorBottomView.getData().getLapNumber() > 1) {
                        currentSelect = 2;
                        mFragmentSensorFirstBinding.tvTitle.setText(getString(R.string.upper_road_section));
                        showSelectView(2);
                    } else {
                        currentSelect = 4;
                        mFragmentSensorFirstBinding.tvTitle.setText(getString(R.string.power));
                        showSelectView(3);
                    }
                    mHandler.sendEmptyMessage(1);
                    break;
                case 3:
                    currentSelect = 3;
                    mFragmentSensorFirstBinding.tvTitle.setText(getString(R.string.heart_rate));
                    showSelectView(3);
                    mHandler.sendEmptyMessage(1);
                    break;
                case 4:
                    currentSelect = 4;
                    mFragmentSensorFirstBinding.tvTitle.setText(getString(R.string.power));
                    showSelectView(4);
                    mHandler.sendEmptyMessage(1);
                    break;
                default:
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            switch (state) {
                case ViewPager2.SCROLL_STATE_DRAGGING:
                    mIsScrolled = false;
                    break;

                case ViewPager2.SCROLL_STATE_SETTLING:
                    mIsScrolled = true;
                    break;

                case ViewPager2.SCROLL_STATE_IDLE:
                    if (!mIsScrolled
                            && mFragmentSensorFirstBinding.vpSensor.getCurrentItem() == 0
                            && mFragmentSensorFirstBinding.sensorBottomView.getData().getSportType() == 1) {
                        try {
                            ((SensorHomeActivity) getActivity()).selectMapFragment();
                        } catch (Exception e) {

                        }
                        //Navigation.findNavController(mFragmentSensorFirstBinding.getRoot()).navigate(R.id.enter_map);
                    }
                    mIsScrolled = true;
                    break;
                default:
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mHandler.sendEmptyMessageDelayed(0, 500);
            }
            switch (currentSelect) {
                case 0:
                    mSensorHomeFragment.updateUi(showSensorTypeList);
                    break;
                case 1:
                    mCurrentFragment.updateUi();
                    break;
                case 2:
                    mPreviousFragment.updateUi();
                    break;
                case 3:
                    mHeartRateFragment.updateUi();
                    break;
                case 4:
                    mPowerFragment.updateUi();
                    break;
                default:
            }
        }
    };

    /**
     * 显示当前选择的ui
     */
    private void showSelectView(int position) {
        switch (position) {
            case 0:
                mFragmentSensorFirstBinding.viewItem1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_main));
                mFragmentSensorFirstBinding.viewItem2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                break;
            case 1:
                mFragmentSensorFirstBinding.viewItem1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_main));
                mFragmentSensorFirstBinding.viewItem3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                break;
            case 2:
                mFragmentSensorFirstBinding.viewItem1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_main));
                mFragmentSensorFirstBinding.viewItem4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                break;
            case 3:
                mFragmentSensorFirstBinding.viewItem1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_main));
                mFragmentSensorFirstBinding.viewItem5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                break;
            case 4:
                mFragmentSensorFirstBinding.viewItem1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_99));
                mFragmentSensorFirstBinding.viewItem5.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_circle_main));
                break;
            default:
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sensor_type:
                if (sensorType == 3) {
                    sensorType = 0;
                } else {
                    ++sensorType;
                }
                mSensorHomeFragment.placedTop();
                SPUtils.putInt(getContext(), SPUtils.SENSOR_TYPE, sensorType);
                showSensorType();
                mSensorHomeFragment.changeType(sensorType, showSensorTypeList);
                break;
            case R.id.view_sensor:
                startActivity(new Intent(getContext(), SensorListStatusActivity.class));
                break;
            case R.id.view_setting:
                if (isShowSetting) {
                    isShowSetting = false;
                    mFragmentSensorFirstBinding.groupSetting.setVisibility(View.GONE);
                    mFragmentSensorFirstBinding.viewBg.setVisibility(View.GONE);
                } else {
                    isShowSetting = true;
                    mFragmentSensorFirstBinding.groupSetting.setVisibility(View.VISIBLE);
                    mFragmentSensorFirstBinding.viewBg.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.view_bg:
                mFragmentSensorFirstBinding.viewBg.setVisibility(View.GONE);
                isShowSetting = false;
                mFragmentSensorFirstBinding.groupSetting.setVisibility(View.GONE);
                break;
            default:
        }
    }

    private boolean isShowSetting = false;

    private void showSensorType() {
        switch (sensorType) {
            case 0:
                mFragmentSensorFirstBinding.ivSensorType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_4));
                break;
            case 1:
                mFragmentSensorFirstBinding.ivSensorType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_6));
                break;
            case 2:
                mFragmentSensorFirstBinding.ivSensorType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_8));
                break;
            case 3:
                mFragmentSensorFirstBinding.ivSensorType.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sensor_2));
                break;
            default:
        }
    }

    /**
     * 开始锻炼
     */
    private void startExercise() {
        isRunning = true;
        EventBus.getDefault().post(new MessageEvent(mActivity.getUserId(), SensorMessageType.ACTION_START_MOTION));
    }

    /**
     * 显示结果弹窗
     */
    private void showResultDialog() {
        SensorResultDialog sensorResultDialog = new SensorResultDialog(getContext());
        sensorResultDialog.show();
        sensorResultDialog.setSensorResultDialogClickListener(new SensorResultDialog.SensorResultDialogClickListener() {
            @Override
            public void deleteClickListener() {
                EventBus.getDefault().post(new MessageEvent(mActivity.getUserId(), SensorMessageType.ACTION_END_MOVEMENT));
                stopMotionModule();
                mActivity.finish();
            }

            @Override
            public void saveClickListener() {
                EventBus.getDefault().post(new MessageEvent(mActivity.getUserId(), SensorMessageType.ACTION_END_MOVEMENT));
                byte[] bs = new byte[JniBleController.mList.size()];
                for (int i = 0; i < JniBleController.mList.size(); i++) {
                    bs[i] = JniBleController.mList.get(i);
                }
                uploadFitFile(bs);
            }

            @Override
            public void carryOnClickListener() {
                isRunning = true;
                EventBus.getDefault().post(new MessageEvent(mActivity.getUserId(), SensorMessageType.ACTION_CARRY_ON_MOVEMENT));
            }
        });
    }

    public void activityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            String typeString = SPUtils.getString(getContext(), SPUtils.SENSOR_TYPE_GROUP);
            String[] split = typeString.split(",");
            int position = data.getIntExtra("position", 0);
            int type = data.getIntExtra("type", 0);
            split[position] = String.valueOf(type);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    stringBuilder.append(split[i]);
                } else {
                    stringBuilder.append(",").append(split[i]);
                }
            }
            String result = stringBuilder.toString();
            SPUtils.putString(getContext(), SPUtils.SENSOR_TYPE_GROUP, result);
            SensorType sensorType = getSensorType(type);
            showSensorTypeList.set(position, sensorType);
            switch (currentSelect) {
                case 0:
                    mSensorHomeFragment.updateUi(showSensorTypeList);
                    break;
                case 1:
                    mCurrentFragment.updateUi();
                    break;
                case 2:
                    mPreviousFragment.updateUi();
                    break;
                case 3:
                    mHeartRateFragment.updateUi();
                    break;
                case 4:
                    mPowerFragment.updateUi();
                    break;
                default:
            }
        }
    }

    /**
     * 上传历史记录文件
     */
    private void uploadFitFile(byte[] file) {
        mActivity.showLoadingDialog();
        Map<String, RequestBody> mapParams = new HashMap<>(8);
        RequestBody sessionBody = RequestBody.create(MediaType.parse("multipart/form-data"), mActivity.getUserInfoEntity().getSession());
        mapParams.put("session", sessionBody);
        RequestBody motionTypeBody;
        if (motionType == JniBleController.getBleController().E_CYCLING_MODE_OUTDOOR) {
            motionTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
        } else {
            motionTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(2));
        }
        mapParams.put("motionType", motionTypeBody);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part myBody = MultipartBody.Part.createFormData("uploadFile", System.currentTimeMillis() + ".fit", fileBody);
        RequestBody mDataTypeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
        mapParams.put("dataType", mDataTypeBody);
        RetrofitUtils.getApiUrl().uploadFitFile(mapParams, myBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mActivity.hideLoadingDialog();
                        String name = AppUtils.timeToString(System.currentTimeMillis(), Config.DEFAULT_PATTERN_COMPLETE).replace(" ", "-").replace(":", "-") + ".fit";
                        String path = mActivity.getExternalFilesDir("") + File.separator + ".myfit/" + mActivity.getUserInfoEntity().getUserCode() + name + ".fit";
                        uploadData(file, path, name);
                        DetailsHomeActivity.enterDetailsHome(getContext(), Integer.parseInt(result));
                        stopMotionModule();
                        Intent intent = new Intent(BroadcastConstant.ACTION_DELETE_RECORD);
                        mActivity.sendBroadcast(intent);
                        mActivity.finish();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mActivity.hideLoadingDialog();
                        if ("-1".equals(resultCode)) {
                            String name = AppUtils.timeToString(System.currentTimeMillis(), Config.DEFAULT_PATTERN_COMPLETE);
                            String path = mActivity.getExternalFilesDir("") + File.separator + ".myfit/" + mActivity.getUserId() + name + ".fit";
                            uploadData(file, path, name);
                            fileWork(mActivity.getUserInfoEntity().getSession(), mActivity.getUserId(), path);
                        } else {
                            ToastUtils.showError(getContext(), resultCode);
                        }
                        isShowDialog = false;
                        timeIsFinish = false;
                        distanceIsFinish = false;
                        showType = 0;
                        isRunning = false;
                        stopMotionModule();
                        mActivity.finish();
                    }
                });
    }

    /**
     * 停止运动模块
     */
    private void stopMotionModule() {
        mHandler.removeMessages(0);
    }

    private SensorType getSensorType(int type) {
        switch (type) {
            case 0:
                return SensorType.sport_time;
            case 1:
                return SensorType.total_time;
            case 2:
                return SensorType.sport_distance;
            case 3:
                return SensorType.speed;
            case 4:
                return SensorType.avg_speed;
            case 5:
                return SensorType.max_speed;
            case 6:
                return SensorType.cadence;
            case 7:
                return SensorType.avg_cadence;
            case 8:
                return SensorType.max_cadence;
            case 9:
                return SensorType.hrm;
            case 10:
                return SensorType.avg_hrm;
            case 11:
                return SensorType.max_hrm;
            case 12:
                return SensorType.power;
            case 13:
                return SensorType.left_right_balance;
            case 14:
                return SensorType.avg_left_right_balance;
            case 15:
                return SensorType.avg_power;
            case 16:
                return SensorType.max_power;
            case 17:
                return SensorType.calories;
        }
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getType()) {
            case SensorMessageType.ACTION_TIME_WARMING:
                if (!timeIsFinish) {
                    if (!isShowDialog || showType == 1) {
                        int time = (int) event.getMessage();
                        if (!isShowDialog) {
                            isShowDialog = true;
                            showType = 1;
                            timeFinishDialog = new TimeFinishDialog(getContext(), time);
                            timeFinishDialog.show();
                            if (mWarningEntity.getVoiceSwitch() == 1) {
                                voiceBroadcast(getString(R.string.warn_time));
                            }
                            timeFinishDialog.setOnDismissListener(dialog -> {
                                timeIsFinish = true;
                                isShowDialog = false;
                                showType = 0;
                            });
                            timeFinishDialog.setTimeFinishClickListener(new TimeFinishDialog.TimeFinishClickListener() {
                                @Override
                                public void stopClick() {
                                    timeFinishDialog.dismiss();
                                    showResultDialog();
                                }

                                @Override
                                public void continueClick() {
                                    timeFinishDialog.dismiss();
                                }
                            });
                        } else {
                            if (timeFinishDialog != null) {
                                timeFinishDialog.updateTime(time);
                            }
                        }
                    }
                }
                break;
            case SensorMessageType.ACTION_DISTANCE_WARMING:
                if (!distanceIsFinish) {
                    if (!isShowDialog || showType == 2) {
                        int distance = (int) event.getMessage();
                        if (!isShowDialog) {
                            isShowDialog = true;
                            showType = 2;
                            distanceFinishDialog = new DistanceFinishDialog(getContext(), distance);
                            distanceFinishDialog.show();
                            if (mWarningEntity.getVoiceSwitch() == 1) {
                                voiceBroadcast(getString(R.string.warn_distance));
                            }
                            distanceFinishDialog.setOnDismissListener(dialog -> {
                                distanceIsFinish = true;
                                isShowDialog = false;
                                showType = 0;
                            });
                            distanceFinishDialog.setDistanceFinishClickListener(new DistanceFinishDialog.DistanceFinishClickListener() {
                                @Override
                                public void stopClick() {
                                    distanceFinishDialog.dismiss();
                                    showResultDialog();
                                }

                                @Override
                                public void continueClick() {
                                    distanceFinishDialog.dismiss();
                                }
                            });
                        } else {
                            if (distanceFinishDialog != null) {
                                distanceFinishDialog.updateDistance(distance);
                            }
                        }
                    }
                }
                break;
            case SensorMessageType.ACTION_SPEED_WARMING:
                if (!isShowDialog) {
                    double speed = (double) event.getMessage();
                    if (System.currentTimeMillis() >= speedTime) {
                        speedTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 3;
                        warningDialog = new WarningDialog(getContext(), 0, speed, (double) mWarningEntity.getMaxSpeedValue() / 1000, true);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_speed_height));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
            case SensorMessageType.ACTION_SPEED_LOW_WARMING:
                if (!isShowDialog) {
                    double speed = (double) event.getMessage();
                    if (System.currentTimeMillis() >= speedTime) {
                        speedTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 3;
                        warningDialog = new WarningDialog(getContext(), 0, speed, (double) mWarningEntity.getMinSpeedValue() / 1000, false);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_speed_low));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
            case SensorMessageType.ACTION_CADENCE_WARMING:
                if (!isShowDialog) {
                    int cadence = (int) event.getMessage();
                    if (System.currentTimeMillis() >= cadenceTime) {
                        cadenceTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 4;
                        warningDialog = new WarningDialog(getContext(), 2, cadence, mWarningEntity.getMaxCadenceValue(), true);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_cadence_height));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
            case SensorMessageType.ACTION_CADENCE_LOW_WARMING:
                if (!isShowDialog) {
                    int cadence = (int) event.getMessage();
                    if (System.currentTimeMillis() >= cadenceTime) {
                        cadenceTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 4;
                        warningDialog = new WarningDialog(getContext(), 2, cadence, mWarningEntity.getMinCadenceValue(), false);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_cadence_low));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
            case SensorMessageType.ACTION_HRM_WARMING:
                if (!isShowDialog) {
                    int hr = (int) event.getMessage();
                    if (System.currentTimeMillis() >= hrTime) {
                        hrTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 5;
                        warningDialog = new WarningDialog(getContext(), 1, hr, mWarningEntity.getMaxHeartValue(), true);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_hr_height));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
            case SensorMessageType.ACTION_HRM_LOW_WARMING:
                if (!isShowDialog) {
                    int hr = (int) event.getMessage();
                    if (System.currentTimeMillis() >= hrTime) {
                        hrTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 5;
                        warningDialog = new WarningDialog(getContext(), 1, hr, mWarningEntity.getMinHeartValue(), false);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_hr_low));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
            case SensorMessageType.ACTION_POWER_WARMING:
                if (!isShowDialog) {
                    int power = (int) event.getMessage();
                    if (System.currentTimeMillis() >= powerTime) {
                        powerTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 6;
                        warningDialog = new WarningDialog(getContext(), 3, power, mWarningEntity.getMaxPowerValue(), true);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_power_height));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
            case SensorMessageType.ACTION_POWER_LOW_WARMING:
                if (!isShowDialog) {
                    int power = (int) event.getMessage();
                    if (System.currentTimeMillis() >= powerTime) {
                        powerTime = System.currentTimeMillis() + mWarningEntity.getWarningInterval() * 60 * 1000;
                        isShowDialog = true;
                        showType = 6;
                        warningDialog = new WarningDialog(getContext(), 3, power, mWarningEntity.getMinPowerValue(), false);
                        warningDialog.show();
                        if (mWarningEntity.getVoiceSwitch() == 1) {
                            voiceBroadcast(getString(R.string.warn_power_low));
                        }
                        mHandler.postDelayed(() -> {
                            warningDialog.dismiss();
                            showType = 0;
                            isShowDialog = false;
                        }, 2000);
                    }
                }
                break;
        }
    }

    private void uploadData(byte[] data, String path, String name) {
        FileUploadEntity fileUploadEntity = DbUtils.getInstance().queryFileUploadEntityByName(mActivity.getUserId(), path);
        if (fileUploadEntity == null) {
            fileUploadEntity = new FileUploadEntity();
            fileUploadEntity.setUserId(mActivity.getUserId());
            if (motionType == JniBleController.getBleController().E_CYCLING_MODE_INDOOR) {
                fileUploadEntity.setSportsType(2);
            } else {
                fileUploadEntity.setSportsType(1);
            }
            fileUploadEntity.setFileName(name);
            fileUploadEntity.setProductNo(null);
            fileUploadEntity.setTimeZone(AppUtils.getTimeZone());
            fileUploadEntity.setFilePath(path);
            DbUtils.getInstance().addFileUploadEntity(fileUploadEntity);
        } else {
            fileUploadEntity.setUserId(mActivity.getUserId());
            fileUploadEntity.setSportsType(motionType);
            fileUploadEntity.setFileName(name);
            fileUploadEntity.setProductNo(null);
            fileUploadEntity.setTimeZone(AppUtils.getTimeZone());
            fileUploadEntity.setFilePath(path);
            DbUtils.getInstance().updateFileUploadEntity(fileUploadEntity);
        }
        AppUtils.saveBytesToFile(path, data);

        StrWork(mActivity.getUserInfoEntity().getSession(), mActivity.getUserId(), path);
        KomWork(mActivity.getUserInfoEntity().getSession(), mActivity.getUserId(), path);
        TPWork(mActivity.getUserInfoEntity().getSession(), mActivity.getUserId(), path);
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
        WorkManager.getInstance(mActivity)
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
        WorkManager.getInstance(mActivity)
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
        WorkManager.getInstance(mActivity)
                .enqueue(new OneTimeWorkRequest.Builder(TPUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }

    @SuppressLint("RestrictedApi")
    private void fileWork(String session, long userId, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data sendData = new Data.Builder().
                putString(SensorUploadWork.SESSION_FLAG, session).
                putLong(FileUploadWork.DATA_USER_ID, userId).
                putString(FileUploadWork.DATA_FILE_PATH, filePath).
                build();
        WorkManager.getInstance(mActivity)
                .enqueue(new OneTimeWorkRequest.Builder(FileUploadWork.class)
                        .setConstraints(constraints)
                        .setInputData(sendData)
                        .build());
    }
}
