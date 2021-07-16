package com.meilancycling.mema.customview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.meilancycling.mema.R;

import java.util.Objects;

/**
 * 详情弹窗
 *
 * @author lion qq 571135591
 */
public class SelectImageDialog extends Dialog implements View.OnClickListener {
    public SelectImageDialog(Context context) {
        super(context, R.style.dialog_style);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_image);
        TextView pictures = findViewById(R.id.tv_take_pictures);
        TextView album = findViewById(R.id.tv_photo_album);
        TextView cancel = findViewById(R.id.tv_cancel);
        pictures.setOnClickListener(this);
        album.setOnClickListener(this);
        cancel.setOnClickListener(this);
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


    @Override
    public void onClick(View v) {
        dismiss();
        int id = v.getId();
        if (id == R.id.tv_take_pictures) {
            if (mSelectImageDialogClickListener != null) {
                mSelectImageDialogClickListener.clickPictures();
            }

        } else if (id == R.id.tv_photo_album) {
            if (mSelectImageDialogClickListener != null) {
                mSelectImageDialogClickListener.clickAlbum();
            }
        }
    }

    public interface SelectImageDialogClickListener {
        /**
         * 点击拍照
         */
        void clickPictures();

        /**
         * 点击相册
         */
        void clickAlbum();


    }

    private SelectImageDialogClickListener mSelectImageDialogClickListener;

    public void setSelectImageDialogClickListener(SelectImageDialogClickListener selectImageDialogClickListener) {
        mSelectImageDialogClickListener = selectImageDialogClickListener;
    }
}

