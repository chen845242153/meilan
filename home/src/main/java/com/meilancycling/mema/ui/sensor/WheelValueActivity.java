package com.meilancycling.mema.ui.sensor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.constant.Config;
import com.meilancycling.mema.constant.Unit;
import com.meilancycling.mema.databinding.ActivityWheelValueBinding;
import com.meilancycling.mema.network.bean.UnitBean;
import com.meilancycling.mema.ui.adapter.WheelAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.UnitConversionUtil;

/**
 * 设置轮圈周长
 *
 * @author lion
 */
public class WheelValueActivity extends BaseActivity implements View.OnClickListener {
    private ActivityWheelValueBinding mActivityWheelValueBinding;
    private String value;
    private String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int wheel = getIntent().getIntExtra("wheel", 0);
        mActivityWheelValueBinding = DataBindingUtil.setContentView(this, R.layout.activity_wheel_value);

        UnitBean unitBean = UnitConversionUtil.getUnitConversionUtil().wheelSetting(this, (double) wheel / 100);
        unit = unitBean.getUnit();
        value = unitBean.getValue();
        initStatus();
        mActivityWheelValueBinding.llClose.setOnClickListener(this);
        mActivityWheelValueBinding.llSelect.setOnClickListener(this);
        WheelAdapter wheelAdapter = new WheelAdapter(this);
        mActivityWheelValueBinding.rvWheel.setLayoutManager(new LinearLayoutManager(this));
        mActivityWheelValueBinding.rvWheel.setAdapter(wheelAdapter);
        wheelAdapter.setWheelClickListener((data, norm) -> {
            UnitBean unitBean1 = UnitConversionUtil.getUnitConversionUtil().wheelSetting(WheelValueActivity.this, data);
            value = unitBean1.getValue();
            clickStatus(norm);
        });
        mActivityWheelValueBinding.ctvWheel.setData(getString(R.string.set_wheel), v -> {
            try {
                String trim = mActivityWheelValueBinding.etWheelValue.getText().toString().trim().replace(",", ".");
                int result = UnitConversionUtil.getUnitConversionUtil().saveWheel( AppUtils.stringToFloat(trim));
                Intent intent = new Intent();
                intent.putExtra("wheel", result);
                setResult(RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                finish();
            }
        });
        mActivityWheelValueBinding.etWheelValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    Double data = Double.valueOf(mActivityWheelValueBinding.etWheelValue.getText().toString());
                    if (Config.unit == Unit.METRIC.value) {
                        if (data > 9999) {
                            mActivityWheelValueBinding.etWheelValue.setText(String.valueOf(9999));
                            mActivityWheelValueBinding.etWheelValue.setSelection(4);
                        }
                    } else {
                        if (data > 999.99) {
                            mActivityWheelValueBinding.etWheelValue.setText(String.valueOf(999.99));
                            mActivityWheelValueBinding.etWheelValue.setSelection(6);
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 初始状态
     */
    private void initStatus() {
        mActivityWheelValueBinding.etWheelValue.setText(value);
        mActivityWheelValueBinding.tvWheelUnit.setText(unit);
        mActivityWheelValueBinding.llClose.setVisibility(View.VISIBLE);
        mActivityWheelValueBinding.rlOpen.setVisibility(View.GONE);
        mActivityWheelValueBinding.llSelect.setVisibility(View.GONE);
        mActivityWheelValueBinding.rvWheel.setVisibility(View.GONE);
    }

    /**
     * 选择状态
     */
    private void selectStatus() {
        mActivityWheelValueBinding.llClose.setVisibility(View.GONE);
        mActivityWheelValueBinding.rlOpen.setVisibility(View.VISIBLE);
        mActivityWheelValueBinding.llSelect.setVisibility(View.GONE);
        mActivityWheelValueBinding.rvWheel.setVisibility(View.VISIBLE);
    }

    /**
     * 选中状态
     */
    private void clickStatus(String norm) {
        mActivityWheelValueBinding.etWheelValue.setText(value);
        mActivityWheelValueBinding.llClose.setVisibility(View.GONE);
        mActivityWheelValueBinding.rlOpen.setVisibility(View.GONE);
        mActivityWheelValueBinding.llSelect.setVisibility(View.VISIBLE);
        mActivityWheelValueBinding.tvValue1.setText(value);
        mActivityWheelValueBinding.tvValue2.setText(norm);
        mActivityWheelValueBinding.rvWheel.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        selectStatus();
    }
}