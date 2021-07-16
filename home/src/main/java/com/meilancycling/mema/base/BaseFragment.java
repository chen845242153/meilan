package com.meilancycling.mema.base;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.meilancycling.mema.utils.StatusAppUtils;

/**
 * 基类
 *
 * @author lion qq 571135591
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    public void setStatusBar(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(view.getLayoutParams());
        lp.setMargins(0, StatusAppUtils.getStatusBarHeight(mActivity), 0, 0);
        view.setLayoutParams(lp);
    }

}
