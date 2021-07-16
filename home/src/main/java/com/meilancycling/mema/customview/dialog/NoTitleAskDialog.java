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
 * 无标题询问弹窗
 *
 * @author sorelion qq 571135591
 */
public class NoTitleAskDialog extends Dialog implements View.OnClickListener {
    private String mContent;
    private String mLeft;
    private String mRight;

    public NoTitleAskDialog(Context context, String content, String left, String right) {
        super(context, R.style.dialog_style);
        mContent = content;
        mLeft = left;
        mRight = right;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ask_no_title);
        setCanceledOnTouchOutside(false);
        TextView content = findViewById(R.id.tv_content);
        content.setText(mContent);
        TextView left = findViewById(R.id.tv_left);
        left.setText(mLeft);
        TextView right = findViewById(R.id.tv_right);
        right.setText(mRight);
        findViewById(R.id.view_left).setOnClickListener(this);
        findViewById(R.id.view_right).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.view_left:
                if (mAskNoTitleDialogListener != null) {
                    mAskNoTitleDialogListener.clickLeft();
                }
                break;
            case R.id.view_right:
                if (mAskNoTitleDialogListener != null) {
                    mAskNoTitleDialogListener.clickRight();
                }
                break;
            default:
        }
    }

    public interface AskNoTitleDialogListener {
        /**
         * 点击取消
         */
        void clickLeft();

        /**
         * 点击确认
         */
        void clickRight();
    }

    private AskNoTitleDialogListener mAskNoTitleDialogListener;


    public void setAskNoTitleDialogListener(AskNoTitleDialogListener askNoTitleDialogListener) {
        mAskNoTitleDialogListener = askNoTitleDialogListener;
    }
}

