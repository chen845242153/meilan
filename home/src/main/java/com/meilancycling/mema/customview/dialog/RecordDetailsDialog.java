package com.meilancycling.mema.customview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.meilancycling.mema.R;
import com.meilancycling.mema.db.AuthorEntity;

import java.util.Objects;

/**
 * 详情弹窗
 *
 * @author lion qq 571135591
 */
public class RecordDetailsDialog extends Dialog implements View.OnClickListener {
    private String titleValue;
    private AuthorEntity authorEntity;
    private String fitUrl;

    public RecordDetailsDialog(Context context, String title, AuthorEntity strEntity, String fit) {
        super(context, R.style.dialog_style);
        titleValue = title;
        authorEntity = strEntity;
        fitUrl = fit;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_details);
        TextView title = findViewById(R.id.tv_details_title);
        TextView record = findViewById(R.id.tv_edit_record);
        TextView delete = findViewById(R.id.tv_delete);
        TextView cancel = findViewById(R.id.tv_cancel);
        TextView strava = findViewById(R.id.tv_strava);
        title.setText(titleValue);
        title.setOnClickListener(this);
        record.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        strava.setOnClickListener(this);
        if (authorEntity == null || TextUtils.isEmpty(fitUrl)) {
            strava.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.tv_details_title:
                if (mRecordDetailsDialogClickListener != null) {
                    mRecordDetailsDialogClickListener.clickTitle();
                }
                break;
            case R.id.tv_edit_record:
                if (mRecordDetailsDialogClickListener != null) {
                    mRecordDetailsDialogClickListener.clickEditRecord();
                }
                break;
            case R.id.tv_delete:
                if (mRecordDetailsDialogClickListener != null) {
                    mRecordDetailsDialogClickListener.clickDelete();
                }
                break;
            case R.id.tv_strava:
                if (mRecordDetailsDialogClickListener != null) {
                    mRecordDetailsDialogClickListener.clickStrava();
                }
                break;
            default:
        }
    }

    public interface RecordDetailsDialogClickListener {
        /**
         * 点击标题
         */
        void clickTitle();

        /**
         * 点击编辑记录
         */
        void clickEditRecord();

        /**
         * 点击删除
         */
        void clickDelete();

        /**
         * 分享Strava
         */
        void clickStrava();

    }

    private RecordDetailsDialogClickListener mRecordDetailsDialogClickListener;


    public void setRecordDetailsDialogClickListener(RecordDetailsDialogClickListener recordDetailsDialogClickListener) {
        mRecordDetailsDialogClickListener = recordDetailsDialogClickListener;
    }
}

