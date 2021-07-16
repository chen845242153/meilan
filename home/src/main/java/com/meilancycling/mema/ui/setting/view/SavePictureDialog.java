package com.meilancycling.mema.ui.setting.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.meilancycling.mema.R;

import java.util.Objects;


/**
 * 保存二维码
 *
 * @author sorelion qq 571135591
 */
public class SavePictureDialog extends Dialog {

    public SavePictureDialog(Context context) {
        super(context, R.style.dialog_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_save_picture);
        findViewById(R.id.view_close).setOnClickListener(v -> dismiss());
        findViewById(R.id.iv).setOnLongClickListener(v -> {
            if (null != mSavePictureListener) {
                dismiss();
                mSavePictureListener.savePicture();
            }
            return false;
        });
    }

    @Override
    public void show() {
        super.show();
        /*
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public interface SavePictureListener {
        void savePicture();
    }

    private SavePictureListener mSavePictureListener;

    public void setSavePictureListener(SavePictureListener savePictureListener) {
        mSavePictureListener = savePictureListener;
    }
}

