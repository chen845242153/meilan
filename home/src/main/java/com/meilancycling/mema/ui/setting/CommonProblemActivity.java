package com.meilancycling.mema.ui.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityCommonProblemBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.response.CommonProblemResponse;
import com.meilancycling.mema.ui.adapter.CommonProblemAdapter;

import java.util.List;

/**
 * 常见问题
 *
 * @author lion
 */
public class CommonProblemActivity extends BaseActivity implements View.OnClickListener {
    private ActivityCommonProblemBinding mActivityCommonProblemBinding;
    private CommonProblemAdapter mCommonProblemAdapter;
    /**
     * 0 英文
     * 1 中文
     * 2 西班牙
     */
    private int showType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCommonProblemBinding = DataBindingUtil.setContentView(this, R.layout.activity_common_problem);
        showType = 0;
        queryContentList();
        mActivityCommonProblemBinding.tvTitle.setText(R.string.problem_title_en);
        mActivityCommonProblemBinding.ivBack.setOnClickListener(this);
        mActivityCommonProblemBinding.ivType.setOnClickListener(this);
    }

    private void queryContentList() {
        showLoadingDialog();
        RetrofitUtils.getApiUrl().queryContentList("password/contentInfo/v201/queryContentList")
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<List<CommonProblemResponse>>() {
                    @Override
                    public void onSuccess(List<CommonProblemResponse> commonData) {
                        hideLoadingDialog();
                        mCommonProblemAdapter = new CommonProblemAdapter(commonData);
                        mActivityCommonProblemBinding.rvProblem.setLayoutManager(new LinearLayoutManager(CommonProblemActivity.this));
                        mActivityCommonProblemBinding.rvProblem.setAdapter(mCommonProblemAdapter);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_type:
                if (mCommonProblemAdapter != null) {
                    switch (showType) {
                        case 0:
                            showType = 1;
                            mActivityCommonProblemBinding.tvTitle.setText(R.string.problem_title_cn);
                            mActivityCommonProblemBinding.ivType.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.problem_cn));
                            break;
                        case 1:
                            showType = 2;
                            mActivityCommonProblemBinding.tvTitle.setText(R.string.problem_title_es);
                            mActivityCommonProblemBinding.ivType.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.problem_es));
                            break;
                        case 2:
                            showType = 0;
                            mActivityCommonProblemBinding.tvTitle.setText(R.string.problem_title_en);
                            mActivityCommonProblemBinding.ivType.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.problem_en));
                            break;
                        default:
                    }
                    mCommonProblemAdapter.changeType(showType);
                }
                break;
            default:
        }
    }
}