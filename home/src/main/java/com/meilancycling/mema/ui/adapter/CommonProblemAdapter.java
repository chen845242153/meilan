package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.response.CommonProblemResponse;
import com.meilancycling.mema.ui.setting.WebViewActivity;

import java.util.List;

/**
 * @Description: 添加传感器
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class CommonProblemAdapter extends RecyclerView.Adapter<CommonProblemAdapter.CommonProblemHolder> {
    private Context mContext;
    private List<CommonProblemResponse> mList;
    private int showType;

    public CommonProblemAdapter(List<CommonProblemResponse> commonData) {
        showType = 0;
        mList = commonData;
    }

    public void changeType(int type) {
        showType = type;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommonProblemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_common_problem, parent, false);
        return new CommonProblemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommonProblemHolder holder, int position) {
        CommonProblemResponse commonProblemResponse = mList.get(position);
        switch (showType) {
            case 0:
                holder.tvContent.setText(commonProblemResponse.getEnName());
                break;
            case 1:
                holder.tvContent.setText(commonProblemResponse.getName());
                break;
            case 2:
                holder.tvContent.setText(commonProblemResponse.getEsName());
                break;
            default:
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            switch (showType) {
                case 0:
                    intent.putExtra(WebViewActivity.title, commonProblemResponse.getEnName());
                    intent.putExtra(WebViewActivity.loadUrl, mList.get(position).getEnUrl());
                    break;
                case 1:
                    intent.putExtra(WebViewActivity.title, commonProblemResponse.getName());
                    intent.putExtra(WebViewActivity.loadUrl, mList.get(position).getUrl());
                    break;
                case 2:
                    intent.putExtra(WebViewActivity.title, commonProblemResponse.getEsName());
                    intent.putExtra(WebViewActivity.loadUrl, mList.get(position).getEsUrl());
                    break;
                default:
            }
            mContext.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class CommonProblemHolder extends RecyclerView.ViewHolder {
        TextView tvContent;

        public CommonProblemHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
