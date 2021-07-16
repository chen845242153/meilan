package com.meilancycling.mema.ui.device.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.meilancycling.mema.R;

import java.util.Objects;

/**
 * 修改sensor名称
 *
 * @author lion qq 571135591
 */
public class ModifySensorNameDialog extends Dialog {
    private String mCurrentName;
    private boolean isDevice;

    public ModifySensorNameDialog(Context context, String currentName, boolean flag) {
        super(context, R.style.dialog_style);
        mCurrentName = currentName;
        isDevice = flag;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_modify_name);
        setCanceledOnTouchOutside(true);
        EditText etName = findViewById(R.id.et_modify_name);
        TextView finish = findViewById(R.id.tv_finish);
        etName.setText(mCurrentName);
        if (mCurrentName != null) {
            etName.setSelection(mCurrentName.length());
        }
        if (isDevice) {
            etName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    String trim = charSequence.toString().trim();
                    int maxNameLength = 12;
                    if (trim.getBytes().length > maxNameLength) {
                        String name = spiltName(trim, maxNameLength);
                        etName.setText(name);
                        etName.setSelection(name.length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        finish.setOnClickListener(v -> {
            String sensorName = etName.getText().toString().trim();
            if (!TextUtils.isEmpty(sensorName)) {
                if (mModifySensorNameCallback != null) {
                    mModifySensorNameCallback.selectSensorName(sensorName);
                }
            }
            dismiss();
        });
    }

    private String spiltName(String name, int maxNameLength) {
        if (name.getBytes().length <= maxNameLength) {
            return name;
        } else {
            return spiltName(name.substring(0, name.length() - 1), maxNameLength);
        }
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

    public interface ModifySensorNameCallback {
        /**
         * 选择名称
         *
         * @param sensorName 名称
         */
        void selectSensorName(String sensorName);
    }

    private ModifySensorNameCallback mModifySensorNameCallback;

    public void setModifySensorNameCallback(ModifySensorNameCallback modifySensorNameCallback) {
        mModifySensorNameCallback = modifySensorNameCallback;
    }
}

