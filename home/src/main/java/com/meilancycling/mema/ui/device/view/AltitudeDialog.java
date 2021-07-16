package com.meilancycling.mema.ui.device.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.meilancycling.mema.R;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 海拔弹窗
 *
 * @author sorelion qq 571135591
 */
public class AltitudeDialog extends Dialog {
    private Context mContext;
    private int mValue;

    private List<String> mItemList = new ArrayList<>();
    /**
     * 正值
     */
    private List<String> positiveItem1List = new ArrayList<>();
    private List<String> positiveItem2List = new ArrayList<>();
    private List<String> positiveMaxItem2List = new ArrayList<>();
    /**
     * 负值
     */
    private List<String> item1List = new ArrayList<>();
    private List<String> item2List = new ArrayList<>();
    private List<String> maxItem2List = new ArrayList<>();

    private TextView tvUnit;
    private TextView tvConfirm;

    private WheelPicker mWpItem1;
    private WheelPicker mWpItem2;
    private WheelPicker mWpItem3;

    /**
     * @param value 根据公英制传值
     */
    public AltitudeDialog(Context context, int value) {
        super(context, R.style.dialog_style);
        mContext = context;
        mValue = value;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_altitude);
        mWpItem1 = findViewById(R.id.wp_altitude_item1);
        mWpItem2 = findViewById(R.id.wp_altitude_item2);
        mWpItem3 = findViewById(R.id.wp_altitude_item3);
        tvUnit = findViewById(R.id.tv_altitude_unit);
        setCanceledOnTouchOutside(false);
        tvConfirm = findViewById(R.id.tv_altitude_confirm);
        initData();

        int position1;
        int position2;
        int position3;
        mWpItem1.setData(mItemList);
        initView(mWpItem1);
        if (mValue < 0) {
            position1 = 0;
            int positiveValue = mValue * (-1);
            position2 = positiveValue / 1000;
            position3 = positiveValue - position2 * 1000;
            mWpItem2.setData(item1List);
            if (position2 == item1List.size() - 1) {
                mWpItem3.setData(maxItem2List);
            } else {
                mWpItem3.setData(item2List);
            }
        } else {
            position1 = 1;
            position2 = mValue / 1000;
            position3 = mValue - position2 * 1000;
            mWpItem2.setData(positiveItem1List);
            if (position2 == positiveItem1List.size() - 1) {
                mWpItem3.setData(positiveMaxItem2List);
            } else {
                mWpItem3.setData(positiveItem2List);
            }
        }
        initView(mWpItem2);
        initView(mWpItem3);
        mWpItem1.post(() -> mWpItem1.setSelectedItemPosition(position1));
        mWpItem2.post(() -> mWpItem2.setSelectedItemPosition(position2));
        mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(position3));

        initListener();
    }

    private void initListener() {
        tvConfirm.setOnClickListener(v -> {
            if (null != mAltitudeDialogListener) {
                String data1 = (String) mWpItem2.getData().get(mWpItem2.getCurrentItemPosition());
                String data2 = (String) mWpItem3.getData().get(mWpItem3.getCurrentItemPosition());
                int selectData = Integer.parseInt(data1) * 1000 + Integer.parseInt(data2);
                if (mWpItem1.getCurrentItemPosition() == 0) {
                    mAltitudeDialogListener.confirmListener(selectData * (-1));
                } else {
                    mAltitudeDialogListener.confirmListener(selectData);
                }
            }
            dismiss();
        });

        mWpItem1.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {
            }

            @Override
            public void onWheelSelected(int position) {
                if (position == 0) {
                    mWpItem2.setData(item1List);
                    if (mWpItem2.getCurrentItemPosition() >= item1List.size() - 1) {
                        mWpItem2.post(() -> mWpItem2.setSelectedItemPosition(item1List.size() - 1));
                        mWpItem3.setData(maxItem2List);
                        if (mWpItem3.getCurrentItemPosition() >= maxItem2List.size()) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(maxItem2List.size() - 1));
                        }
                    } else {
                        mWpItem3.setData(item2List);
                        if (mWpItem3.getCurrentItemPosition() >= item2List.size()) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(item2List.size() - 1));
                        }
                    }
                } else {
                    mWpItem2.setData(positiveItem1List);
                    if (mWpItem2.getCurrentItemPosition() >= positiveItem1List.size() - 1) {
                        mWpItem2.post(() -> mWpItem2.setSelectedItemPosition(positiveItem1List.size() - 1));
                        mWpItem3.setData(positiveMaxItem2List);
                        if (mWpItem3.getCurrentItemPosition() >= positiveMaxItem2List.size()) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(positiveMaxItem2List.size() - 1));
                        }
                    } else {
                        mWpItem3.setData(positiveItem2List);
                        if (mWpItem3.getCurrentItemPosition() >= positiveItem2List.size()) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(positiveItem2List.size() - 1));
                        }
                    }
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });

        mWpItem2.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {
            }

            @Override
            public void onWheelSelected(int position) {
                if (mWpItem1.getCurrentItemPosition() == 0) {
                    if (position >= item1List.size() - 1) {
                        mWpItem2.post(() -> mWpItem2.setSelectedItemPosition(item1List.size() - 1));
                        mWpItem3.setData(maxItem2List);
                        if (mWpItem3.getCurrentItemPosition() >= maxItem2List.size() - 1) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(maxItem2List.size() - 1));
                        }
                    } else {
                        mWpItem3.setData(item2List);
                        if (mWpItem3.getCurrentItemPosition() >= item2List.size() - 1) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(item2List.size() - 1));
                        }
                    }
                } else {
                    if (position >= positiveItem1List.size() - 1) {
                        mWpItem2.post(() -> mWpItem2.setSelectedItemPosition(positiveItem1List.size() - 1));
                        mWpItem3.setData(positiveMaxItem2List);
                        if (mWpItem3.getCurrentItemPosition() >= positiveMaxItem2List.size() - 1) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(positiveMaxItem2List.size() - 1));
                        }
                    } else {
                        mWpItem3.setData(positiveItem2List);
                        if (mWpItem3.getCurrentItemPosition() >= positiveItem2List.size() - 1) {
                            mWpItem3.post(() -> mWpItem3.setSelectedItemPosition(positiveItem2List.size() - 1));
                        }
                    }
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });
    }

    private void initView(WheelPicker wheelPicker) {
        wheelPicker.setIndicator(true);
        wheelPicker.setIndicatorColor(Color.parseColor("#FFD2D2D2"));
        wheelPicker.setIndicatorSize(AppUtils.dipToPx(mContext, 1));
        wheelPicker.setVisibleItemCount(3);
        wheelPicker.setAtmospheric(true);
    }

    /**
     * 初始化data
     */
    @SuppressLint("SetTextI18n")
    private void initData() {
        mItemList.add("-");
        mItemList.add("+");
        if (Config.unit == Unit.METRIC.value) {
            tvUnit.setText("(" + mContext.getString(R.string.unit_m) + ")");
            item1List.add("00");
            for (int i = 0; i <= 8; i++) {
                positiveItem1List.add("0" + i);
            }
            for (int i = 0; i <= 400; i++) {
                if (i < 10) {
                    item2List.add("00" + i);
                    maxItem2List.add("00" + i);
                } else if (i < 100) {
                    item2List.add("0" + i);
                    maxItem2List.add("0" + i);
                } else {
                    item2List.add(String.valueOf(i));
                    maxItem2List.add(String.valueOf(i));
                }
            }
            for (int i = 0; i <= 999; i++) {
                if (i < 10) {
                    positiveItem2List.add("00" + i);
                    positiveMaxItem2List.add("00" + i);
                } else if (i < 100) {
                    positiveItem2List.add("0" + i);
                    positiveMaxItem2List.add("0" + i);
                } else {
                    positiveItem2List.add(String.valueOf(i));
                    if (i <= 848) {
                        positiveMaxItem2List.add(String.valueOf(i));
                    }
                }
            }
        } else {
            tvUnit.setText("(" + mContext.getString(R.string.unit_feet) + ")");
            item1List.add("00");
            item1List.add("01");
            for (int i = 0; i <= 29; i++) {
                if (i < 10) {
                    positiveItem1List.add("0" + i);
                } else {
                    positiveItem1List.add(String.valueOf(i));
                }
            }
            for (int i = 0; i <= 999; i++) {
                if (i < 10) {
                    item2List.add("00" + i);
                    maxItem2List.add("00" + i);
                } else if (i < 100) {
                    item2List.add("0" + i);
                    maxItem2List.add("0" + i);
                } else {
                    item2List.add(String.valueOf(i));
                    if (i <= 313) {
                        maxItem2List.add(String.valueOf(i));
                    }
                }
            }
            for (int i = 0; i < 999; i++) {
                if (i < 10) {
                    positiveItem2List.add("00" + i);
                    positiveMaxItem2List.add("00" + i);
                } else if (i < 100) {
                    if (i <= 28) {
                        positiveMaxItem2List.add("0" + i);
                    }
                    positiveItem2List.add("0" + i);
                } else {
                    positiveItem2List.add(String.valueOf(i));
                }
            }
        }
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public interface AltitudeDialogListener {
        /**
         * 海拔
         *
         * @param altitude 选择数值
         */
        void confirmListener(int altitude);
    }

    private AltitudeDialogListener mAltitudeDialogListener;

    public void setAltitudeDialogListener(AltitudeDialogListener altitudeDialogListener) {
        mAltitudeDialogListener = altitudeDialogListener;
    }
}

