package com.meilancycling.mema.ui.club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivityCommentListBinding;
import com.meilancycling.mema.network.MyObserver;
import com.meilancycling.mema.network.RetrofitUtils;
import com.meilancycling.mema.network.RxHelper;
import com.meilancycling.mema.network.bean.request.AddCommentRequest;
import com.meilancycling.mema.network.bean.request.CommentRequest;
import com.meilancycling.mema.network.bean.response.CommentResponse;
import com.meilancycling.mema.ui.adapter.CommentAdapter;
import com.meilancycling.mema.ui.club.model.CommentModel;
import com.meilancycling.mema.utils.GlideUtils;
import com.meilancycling.mema.utils.ToastUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultLoadMoreView;

import java.util.ArrayList;
import java.util.List;

public class CommentListActivity extends BaseActivity {
    private ActivityCommentListBinding mActivityCommentListBinding;
    private int detailsId;
    private String mSession;
    private int pageNum = 1;
    private List<CommentModel> adapterData;
    private CommentAdapter mAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCommentListBinding = DataBindingUtil.setContentView(this, R.layout.activity_comment_list);
        Intent intent = getIntent();
        detailsId = intent.getIntExtra("detailsId", 0);
        mSession = getUserInfoEntity().getSession();
        adapterData = new ArrayList<>();
        mAdapter = new CommentAdapter();
        mActivityCommentListBinding.rvComment.setLayoutManager(new LinearLayoutManager(this));
        mActivityCommentListBinding.rvComment.setAdapter(mAdapter);
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(this);
        mActivityCommentListBinding.rvComment.setLoadMoreView(defaultLoadMoreView);
        mActivityCommentListBinding.rvComment.setLoadMoreListener(mLoadMoreListener);

        GlideUtils.loadCircleImage(getUserInfoEntity().getHeadUrl(), this, mActivityCommentListBinding.ivHeader);
        requestCommentList();

        mActivityCommentListBinding.refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.main_color));
        mActivityCommentListBinding.refresh.setOnRefreshListener(refreshListener);

        mActivityCommentListBinding.etContact.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == 0 || actionId == 3) && event != null) {
                String trim = mActivityCommentListBinding.etContact.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    mActivityCommentListBinding.etContact.setText("");
                    addComment(trim);

                }
            }
            return false;
        });
        mActivityCommentListBinding.viewBack.setOnClickListener(view -> finish());

    }

    private final SwipeRecyclerView.LoadMoreListener mLoadMoreListener = () -> {

        ++pageNum;
        requestCommentList();
    };
    private final SwipeRefreshLayout.OnRefreshListener refreshListener = () -> {
        adapterData.clear();
        pageNum = 1;
        requestCommentList();
    };

    public static void enterCommentList(Context context, int detailsId) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra("detailsId", detailsId);
        context.startActivity(intent);
    }

    /**
     * 获取评论列表
     */
    public void requestCommentList() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setPageNum(pageNum);
        commentRequest.setPageSize(20);
        commentRequest.setSession(mSession);
        commentRequest.setExternalId(detailsId);
        RetrofitUtils.getApiUrl()
                .commentList(commentRequest)
                .compose(RxHelper.observableToMain(CommentListActivity.this))
                .subscribe(new MyObserver<CommentResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(CommentResponse result) {
                        List<CommentResponse.RowsBean> rows = result.getRows();
                        if (rows != null && rows.size() > 0) {
                            for (CommentResponse.RowsBean row : rows) {
                                CommentModel commentModel = new CommentModel();
                                commentModel.content = row.getContent();
                                commentModel.createDate = row.getCreateDate();
                                commentModel.headerUrl = row.getHeaderUrl();
                                commentModel.nickName = row.getNickName();
                                adapterData.add(commentModel);
                            }
                            if (pageNum >= result.getPages()) {
                                mActivityCommentListBinding.rvComment.loadMoreFinish(false, false);
                            } else {
                                mActivityCommentListBinding.rvComment.loadMoreFinish(false, true);
                            }
                        } else {
                            mActivityCommentListBinding.rvComment.loadMoreFinish(true, true);
                        }
                        mActivityCommentListBinding.tvTitle.setText(getString(R.string.comment) + "(" + result.getTotal() + ")");
                        mAdapter.setData(adapterData);
                        mActivityCommentListBinding.refresh.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        mActivityCommentListBinding.tvTitle.setText(getString(R.string.comment));
                        mActivityCommentListBinding.refresh.setRefreshing(false);
                    }
                });
    }

    /**
     * 添加评论
     */
    public void addComment(String commentContent) {
        AddCommentRequest addCommentRequest = new AddCommentRequest();
        addCommentRequest.setSession(mSession);
        addCommentRequest.setComment(commentContent);
        addCommentRequest.setExternalId(detailsId);
        RetrofitUtils.getApiUrl()
                .addComment(addCommentRequest)
                .compose(RxHelper.observableToMain(CommentListActivity.this))
                .subscribe(new MyObserver<Object>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Object result) {
                        adapterData.clear();
                        pageNum = 1;
                        requestCommentList();
                    }

                    @Override
                    public void onFailure(Throwable e, String resultCode) {
                        ToastUtils.showError(CommentListActivity.this, resultCode);
                    }
                });
    }
}