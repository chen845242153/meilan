package com.meilancycling.mema.ui.club.view;

import android.content.Context;
import android.view.View;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseCustomView;
import com.meilancycling.mema.databinding.CommentItemViewBinding;
import com.meilancycling.mema.ui.club.model.CommentModel;

public class CommentItemView extends BaseCustomView<CommentItemViewBinding, CommentModel> {
    public CommentItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.comment_item_view;
    }

    @Override
    public void onRootClicked(View view) {

    }

    @Override
    protected void setDataToView(CommentModel commentModel) {
        binding.setModel(commentModel);
    }


}
