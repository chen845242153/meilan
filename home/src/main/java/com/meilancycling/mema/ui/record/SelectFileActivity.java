package com.meilancycling.mema.ui.record;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meilancycling.mema.R;
import com.meilancycling.mema.base.BaseActivity;
import com.meilancycling.mema.databinding.ActivitySelectFileBinding;
import com.meilancycling.mema.ui.adapter.SelectFileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件选择
 *
 * @author lion
 */
public class SelectFileActivity extends BaseActivity {
    private ActivitySelectFileBinding mActivitySelectFileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySelectFileBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_file);
        mActivitySelectFileBinding.ctvSelectFile.setData(getResources().getString(R.string.select_file), v -> finish());
        String filePath = getExternalFilesDir("") + File.separator + "fit";
        File[] files = new File(filePath).listFiles();
        List<String> pathList = new ArrayList<>();
        for (File file : files) {
            // 判断是否为文件夹
            if (!file.isDirectory()) {
                String filename = file.getName();
                if (filename.trim().toLowerCase().endsWith(".fit")) {
                    pathList.add(filename);
                }
            }
        }
        SelectFileAdapter selectFileAdapter = new SelectFileAdapter(this, pathList);
        mActivitySelectFileBinding.rvFile.setLayoutManager(new LinearLayoutManager(this));
        mActivitySelectFileBinding.rvFile.setAdapter(selectFileAdapter);
    }


}