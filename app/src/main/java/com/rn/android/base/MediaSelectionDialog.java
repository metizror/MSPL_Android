package com.rn.android.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;


import com.metiz.pelconnect.R;
import com.rn.android.listeners.PickerOptionListener;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaSelectionDialog extends BaseDialog {

    private PickerOptionListener pickerOptionListener;



    public MediaSelectionDialog(@NonNull Context context, PickerOptionListener pickerOptionListener) {
        super(context);
        this.pickerOptionListener = pickerOptionListener;
    }

    @Override
    protected void onKeyBoardShowHidden(boolean isKeyboardVisible) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_chooser_dialog);
        ButterKnife.bind(this);
        Objects.requireNonNull(getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @OnClick({R.id.tv_camera, R.id.tv_gallery, R.id.btn_cancel_dialog, R.id.cl_dialog_holder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_camera:
                pickerOptionListener.onTakeCameraSelected();
                break;
            case R.id.tv_gallery:
                pickerOptionListener.onChooseGallerySelected();
                break;
        }
        dismiss();
    }
}
