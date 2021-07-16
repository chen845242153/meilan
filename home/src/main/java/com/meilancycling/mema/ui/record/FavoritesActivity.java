package com.meilancycling.mema.ui.record;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityFavoritesBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.MotionInfoRequest;
import com.meilancycling.mema.network.bean.request.UpMotionInfoRequest;
import com.meilancycling.mema.network.bean.response.MotionInfoResponse;
import com.meilancycling.mema.ui.adapter.FavoritesAdapter;
import com.meilancycling.mema.utils.AppUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultLoadMoreView;
import java.util.ArrayList;
import java.util.List;
/**
 * 我的收藏
 *
 * @author lion
 */
public class FavoritesActivity extends BaseActivity {
    private ActivityFavoritesBinding mActivityFavoritesBinding;
    private int pageNum;
    private FavoritesAdapter mFavoritesAdapter;
    private List<MotionInfoResponse.RowsBean> adapterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFavoritesBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorites);
        mActivityFavoritesBinding.ctvFavorites.setData(getResources().getString(R.string.my_favorites), v -> finish());
        mFavoritesAdapter = new FavoritesAdapter();
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(this);
        mActivityFavoritesBinding.srvFavorites.setLoadMoreView(defaultLoadMoreView);
        mActivityFavoritesBinding.srvFavorites.setLayoutManager(new LinearLayoutManager(this));
        mActivityFavoritesBinding.srvFavorites.setSwipeMenuCreator(mSwipeMenuCreator);
        mActivityFavoritesBinding.srvFavorites.setOnItemMenuClickListener(mItemMenuClickListener);
        mActivityFavoritesBinding.srvFavorites.setLoadMoreListener(mLoadMoreListener);
        mActivityFavoritesBinding.srvFavorites.setAdapter(mFavoritesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNum = 1;
        adapterList.clear();
        queryFavoriteData();
    }

    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            ++pageNum;
            queryFavoriteData();
        }
    };
    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    private OnItemMenuClickListener mItemMenuClickListener = (menuBridge, position) -> {
        menuBridge.closeMenu();
        MotionInfoResponse.RowsBean rowsBean = adapterList.get(position);
        cancelCollection(rowsBean.getId(), position);
    };
    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator mSwipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        SwipeMenuItem competitionItem = new SwipeMenuItem(this)
                .setWidth(AppUtils.dipToPx(this, 91))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.delete_red))
                .setText(getResources().getString(R.string.un_favorite))
                .setTextColor(getResources().getColor(R.color.white))
                .setTextSize(15)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 添加菜单到右侧。
        swipeRightMenu.addMenuItem(competitionItem);
    };

    /**
     * 取消收藏
     */
    public void cancelCollection(int id, int position) {
        showLoadingDialog();
        UpMotionInfoRequest upMotionInfoRequest = new UpMotionInfoRequest();
        upMotionInfoRequest.setSession(getUserInfoEntity().getSession());
        upMotionInfoRequest.setUpStatus(0);
        upMotionInfoRequest.setId(id);
        RetrofitUtils.getApiUrl().upCompetition(upMotionInfoRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<Object>() {
                    @Override
                    public void onSuccess(Object obj) {
                        hideLoadingDialog();
                        adapterList.remove(position);
                        mFavoritesAdapter.setAdapterData(adapterList);
                        if (adapterList.size() == 0) {
                            mActivityFavoritesBinding.srvFavorites.setVisibility(View.GONE);
                            mActivityFavoritesBinding.groupEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mActivityFavoritesBinding.srvFavorites.setVisibility(View.VISIBLE);
                            mActivityFavoritesBinding.groupEmpty.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        ToastUtils.show(FavoritesActivity.this, getString(R.string.save_failed));
                    }
                });
    }
    private void queryFavoriteData() {
        showLoadingDialog();
        int pageSize = 10;
        MotionInfoRequest motionInfoRequest = new MotionInfoRequest();
        motionInfoRequest.setIsCompetition("1");
        motionInfoRequest.setSession(getUserInfoEntity().getSession());
        motionInfoRequest.setPageNum(pageNum);
        motionInfoRequest.setMotionType(0);
        motionInfoRequest.setPageSize(pageSize);
        motionInfoRequest.setTimeType(5);
        RetrofitUtils.getApiUrl().listMotionInfo(motionInfoRequest)
                .compose(RxHelper.observableToMain(this))
                .subscribe(new MyObserver<MotionInfoResponse>() {
                    @Override
                    public void onSuccess(MotionInfoResponse result) {
                        hideLoadingDialog();
                        if (result != null) {
                            if (result.getRows() != null && result.getRows().size() != 0) {
                                mActivityFavoritesBinding.srvFavorites.setVisibility(View.VISIBLE);
                                mActivityFavoritesBinding.groupEmpty.setVisibility(View.GONE);
                                adapterList.addAll(result.getRows());
                                mFavoritesAdapter.setAdapterData(adapterList);
                            } else {
                                mActivityFavoritesBinding.srvFavorites.setVisibility(View.GONE);
                                mActivityFavoritesBinding.groupEmpty.setVisibility(View.VISIBLE);
                            }
                            if (result.getPages() == pageNum) {
                                mActivityFavoritesBinding.srvFavorites.loadMoreFinish(false, false);
                            } else {
                                mActivityFavoritesBinding.srvFavorites.loadMoreFinish(false, true);
                            }
                        } else {
                            mActivityFavoritesBinding.srvFavorites.loadMoreFinish(true, true);
                        }
                    }
                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        hideLoadingDialog();
                        mActivityFavoritesBinding.srvFavorites.setVisibility(View.GONE);
                        mActivityFavoritesBinding.groupEmpty.setVisibility(View.VISIBLE);
                    }
                });
    }
}