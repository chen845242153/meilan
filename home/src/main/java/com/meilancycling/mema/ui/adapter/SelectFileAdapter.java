package com.meilancycling.mema.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.ui.record.SelectFileActivity;

import java.io.File;
import java.util.List;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2/3/21 9:45 AM
 */
public class SelectFileAdapter extends RecyclerView.Adapter<SelectFileAdapter.SelectFileHolder> {
    private SelectFileActivity mSelectFileActivity;
    private List<String> adapterList;

    public SelectFileAdapter(SelectFileActivity selectFileActivity, List<String> adapterList) {
        mSelectFileActivity = selectFileActivity;
        this.adapterList = adapterList;
    }

    @NonNull
    @Override
    public SelectFileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mSelectFileActivity).inflate(R.layout.itme_select_file, parent, false);
        return new SelectFileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectFileHolder holder, int position) {
        holder.name.setText(adapterList.get(position));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent();
            String result = mSelectFileActivity.getExternalFilesDir("") + File.separator + "fit/" + adapterList.get(position);
            intent.putExtra("result", result);
            mSelectFileActivity.setResult(Activity.RESULT_OK, intent);
            mSelectFileActivity.finish();
        });
    }

    @Override
    public int getItemCount() {
        return adapterList == null ? 0 : adapterList.size();
    }

    static class SelectFileHolder extends RecyclerView.ViewHolder {
        TextView name;


        SelectFileHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);

        }
    }
}
