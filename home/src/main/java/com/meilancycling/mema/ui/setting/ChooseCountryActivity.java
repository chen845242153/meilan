package com.meilancycling.mema.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityChooseCountryBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.CountryInfoRequest;
import com.meilancycling.mema.network.bean.response.CountryInfoResponse;
import com.meilancycling.mema.ui.adapter.CountryAdapter;
import com.meilancycling.mema.utils.AppUtils;

import java.util.List;

/**
 * 选择国家
 *
 * @author lion
 */
public class ChooseCountryActivity extends BaseActivity {
    private ActivityChooseCountryBinding mActivityChooseCountryBinding;
    private List<CountryInfoResponse.RowsBean> adapterList;
    private CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChooseCountryBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_country);
        getCountryData();
        mActivityChooseCountryBinding.viewBack.setOnClickListener(v -> finish());
        mActivityChooseCountryBinding.aBarView.setAToZBarViewCallback(result -> {
            if (adapterList != null) {
                int selectPosition = -1;
                if (AppUtils.isChinese()) {
                    for (int i = 0; i < adapterList.size(); i++) {
                        if (adapterList.get(i).getCnFirst().equals(result)) {
                            selectPosition = i;
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < adapterList.size(); i++) {
                        if (adapterList.get(i).getEnName().substring(0, 1).equals(result)) {
                            selectPosition = i;
                            break;
                        }
                    }
                }

                scrollToPosition(selectPosition);
            }
        });
        countryAdapter = new CountryAdapter();
        countryAdapter.setCountryAdapterCallback(position -> {
            CountryInfoResponse.RowsBean rowsBean = adapterList.get(position);
            Intent intent = new Intent();
            intent.putExtra("result", rowsBean.getCnName() + "&" + rowsBean.getEnName());
            intent.putExtra("phone", rowsBean.getPhone());
            intent.putExtra("nationalFlag", rowsBean.getNationalFlag());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void scrollToPosition(int position) {
        if (position != -1) {
            mActivityChooseCountryBinding.rvCountry.scrollToPosition(position);
            LinearLayoutManager mLayoutManager =
                    (LinearLayoutManager) mActivityChooseCountryBinding.rvCountry.getLayoutManager();
            assert mLayoutManager != null;
            mLayoutManager.scrollToPositionWithOffset(position, 0);
        }
    }


    private void getCountryData() {
        showLoadingDialog();
        CountryInfoRequest countryInfoRequest = new CountryInfoRequest();
        if (AppUtils.isChinese()) {
            countryInfoRequest.setDataType(1);
        } else {
            countryInfoRequest.setDataType(2);
        }
        countryInfoRequest.setPageNum(1);
        countryInfoRequest.setPageSize(300);
        RetrofitUtils.getApiUrl()
                .countryInfo(countryInfoRequest)
                .compose(RxHelper.observableToMain(ChooseCountryActivity.this))
                .subscribe(new MyObserver<CountryInfoResponse>() {
                    @Override
                    public void onSuccess(CountryInfoResponse countryInfoResponse) {
                        hideLoadingDialog();
                        if (AppUtils.isChinese()) {
                            for (CountryInfoResponse.RowsBean row : countryInfoResponse.getRows()) {
                                mActivityChooseCountryBinding.aBarView.addData(row.getCnFirst());
                            }
                        } else {
                            for (CountryInfoResponse.RowsBean row : countryInfoResponse.getRows()) {
                                mActivityChooseCountryBinding.aBarView.addData(row.getEnName().substring(0, 1));
                            }
                        }
                        mActivityChooseCountryBinding.aBarView.postInvalidate();
                        adapterList = countryInfoResponse.getRows();
                        countryAdapter.setData(adapterList);
                        mActivityChooseCountryBinding.rvCountry.setLayoutManager(new LinearLayoutManager(ChooseCountryActivity.this));
                        mActivityChooseCountryBinding.rvCountry.setAdapter(countryAdapter);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });
    }

}