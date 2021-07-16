package com.meilancycling.mema.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.db.FileUploadEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 添加传感器
 * @Author: sore_lion
 * @CreateDate: 2020-05-15 08:37
 */
public class FitManagerAdapter extends RecyclerView.Adapter<FitManagerAdapter.FitManagerHolder> {
    private Context mContext;
    private List<FileUploadEntity> dataList;
    private List<String> selectList = new ArrayList<>();
    private boolean selectFlag = false;

    public FitManagerAdapter(List<FileUploadEntity> dataList) {
        this.dataList = dataList;
    }

    public void updateList(List<FileUploadEntity> dataList) {
        this.dataList = dataList;
    }

    public void selectClick(boolean isSelect) {
        if (!selectFlag) {
            selectList.clear();
        }
        selectFlag = isSelect;
        notifyDataSetChanged();
    }

    public void deleteItem(FileUploadEntity fileUploadEntity) {
        for (String s : selectList) {
            if (s.equals(fileUploadEntity.getFileName())) {
                selectList.remove(s);
                break;
            }
        }
        dataList.remove(fileUploadEntity);
        notifyDataSetChanged();
    }

    public List<String> getSelectList() {
        return selectList;
    }

    private void addItem(String fileName) {
        boolean isSelect = false;
        for (String s : selectList) {
            if (fileName.equals(s)) {
                isSelect = true;
                break;
            }
        }
        if (isSelect) {
            selectList.remove(fileName);
        } else {
            selectList.add(fileName);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FitManagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fit_manager, parent, false);
        return new FitManagerHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FitManagerHolder holder, int position) {
        FileUploadEntity fileUploadEntity = dataList.get(position);
        if (selectFlag) {
            holder.ivSelect.setVisibility(View.VISIBLE);
            boolean isCheck = false;
            for (String s : selectList) {
                if (fileUploadEntity.getFileName().equals(s)) {
                    isCheck = true;
                    break;
                }
            }
            if (isCheck) {
                holder.ivSelect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.fit_manager_select));
            } else {
                holder.ivSelect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.fit_manager_normal));
            }
        } else {
            holder.ivSelect.setVisibility(View.GONE);
        }
        //   1骑车，2骑行台，3骑行竞赛，7其它
        switch (fileUploadEntity.getSportsType()) {
            case 1:
                holder.ivType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.add_item1_select));
                break;
            case 2:
                holder.ivType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.add_item2_selelct));
                break;
            case 3:
                holder.ivType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.add_item3_selelct));
                break;
        }

        holder.tvFileName.setText(fileUploadEntity.getFileName());
        holder.viewUpload.setOnClickListener(v -> mFitManagerClickListener.clickUpload(fileUploadEntity));
        holder.viewShare.setOnClickListener(v -> mFitManagerClickListener.clickShare(fileUploadEntity));
        holder.viewSelect.setOnClickListener(v -> addItem(fileUploadEntity.getFileName()));
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public static class FitManagerHolder extends RecyclerView.ViewHolder {
        ImageView ivType;
        TextView tvFileName;
        ImageView ivSelect;
        ImageView ivShare;
        ImageView ivUpload;
        View viewSelect;
        View viewShare;
        View viewUpload;

        public FitManagerHolder(@NonNull View itemView) {
            super(itemView);
            ivType = itemView.findViewById(R.id.iv_type);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            ivSelect = itemView.findViewById(R.id.iv_select);
            ivShare = itemView.findViewById(R.id.iv_share);
            ivUpload = itemView.findViewById(R.id.iv_upload);
            viewSelect = itemView.findViewById(R.id.view_select);
            viewShare = itemView.findViewById(R.id.view_share);
            viewUpload = itemView.findViewById(R.id.view_upload);
        }
    }

    public interface FitManagerClickListener {
        void clickUpload(FileUploadEntity fileUploadEntity);

        void clickShare(FileUploadEntity fileUploadEntity);
    }

    private FitManagerClickListener mFitManagerClickListener;

    public void setFitManagerClickListener(FitManagerClickListener fitManagerClickListener) {
        mFitManagerClickListener = fitManagerClickListener;
    }
}
