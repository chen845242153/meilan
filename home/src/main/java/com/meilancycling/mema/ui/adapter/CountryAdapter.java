package com.meilancycling.mema.ui.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.network.bean.response.CountryInfoResponse;
import com.meilancycling.mema.utils.AppUtils;

import java.util.List;

/**
 * @Description: 添加传感器
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {

    private Context mContext;
    private List<CountryInfoResponse.RowsBean> mList;
    private boolean isCN;


    public void setData(List<CountryInfoResponse.RowsBean> rows) {
        mList = rows;
        isCN = AppUtils.isChinese();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_country, parent, false);
        return new CountryHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
        CountryInfoResponse.RowsBean rowsBean = mList.get(position);
        if (isCN) {
            holder.content.setText(rowsBean.getCnName());
            if (position == 0) {
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(rowsBean.getCnFirst());
            } else {
                if (rowsBean.getCnFirst().equals(mList.get(position - 1).getCnFirst())) {
                    holder.title.setVisibility(View.GONE);
                } else {
                    holder.title.setVisibility(View.VISIBLE);
                    holder.title.setText(rowsBean.getCnFirst());
                }
            }
        } else {
            holder.content.setText(rowsBean.getEnName());
            if (position == 0) {
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(rowsBean.getEnName().substring(0, 1));
            } else {
                if (rowsBean.getEnName().substring(0, 1).equals(mList.get(position - 1).getEnName().substring(0, 1))) {
                    holder.title.setVisibility(View.GONE);
                } else {
                    holder.title.setVisibility(View.VISIBLE);
                    holder.title.setText(rowsBean.getEnName().substring(0, 1));
                }
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (mCountryAdapterCallback != null) {
                mCountryAdapterCallback.clickPosition(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class CountryHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        public CountryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
        }
    }

    public interface CountryAdapterCallback {
        void clickPosition(int position);
    }

    private CountryAdapterCallback mCountryAdapterCallback;

    public void setCountryAdapterCallback(CountryAdapterCallback countryAdapterCallback) {
        mCountryAdapterCallback = countryAdapterCallback;
    }
}
