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
 * 询问弹窗
 *
 * @author sorelion qq 571135591
 */
public class AskDialog extends Dialog implements View.OnClickListener {
    private String mTitle;
    private String mContent;

    public AskDialog(Context context, String title, String content) {
        super(context, R.style.dialog_style);
        mTitle = title;
        mContent = content;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_device);
        setCanceledOnTouchOutside(false);
        TextView title = findViewById(R.id.tv_title);
        title.setText(mTitle);
        TextView content = findViewById(R.id.tv_content);
        content.setText(mContent);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(getWindow()).getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.tv_cancel:
                if (mAskDialogListener != null) {
                    mAskDialogListener.clickCancel();
                }
                break;
            case R.id.tv_confirm:
                if (mAskDialogListener != null) {
                    mAskDialogListener.clickConfirm();
                }
                break;
            default:
        }
    }

    public interface AskDialogListener {
        /**
         * 点击取消
         */
        void clickCancel();

        /**
         * 点击确认
         */
        void clickConfirm();
    }

    private AskDialogListener mAskDialogListener;

    public void setAskDialogListener(AskDialogListener askDialogListener) {
        mAskDialogListener = askDialogListener;
    }
}

