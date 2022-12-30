package com.rn.android;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;

import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.ActivityCustomCameraBinding;
import com.rn.android.util.CameraPreview;
import com.rn.android.util.PreferenceHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Custom_CameraActivity extends BaseActivity {
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private ActivityCustomCameraBinding mBinding;
    private File mediaFileData;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_camera);
        mBinding = DataBindingUtil.setContentView(activity, R.layout.activity_custom_camera);


        onCreateMethod();
    }

    private void onCreateMethod() {
        onClickListener();
        mCamera = getCameraInstance();
        mCamera.startPreview();

        /*mBinding.cameraPreview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mCamera!=null) {
                    mCamera.cancelAutoFocus();
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//                    parameters.setFocusAreas(Lists.newArrayList(new Camera.Area(focusRect, 1000)));

                    mCamera.setParameters(parameters);
                }
            }
        });*/

        /*  mBinding.cameraPreview.setOnTouchListener((view, event) -> {
            final int MAX_DURATION = 200;
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setParameters(parameters);
            handleFocus(event, parameters);
            return true;
        });
*/
        mCameraPreview = new CameraPreview(activity, context, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mCameraPreview);
        mBinding.llAfterImage.setVisibility(View.GONE);
        mBinding.btnCapture.setVisibility(View.VISIBLE);
    }

    private void onClickListener() {

        mBinding.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.btnCapture.setEnabled(false);
                mCamera.takePicture(null, null, mPicture);
                new Handler().postDelayed(() -> {
                    mBinding.btnCapture.setEnabled(true);
                    mBinding.llAfterImage.setVisibility(View.VISIBLE);
                    mBinding.btnCapture.setVisibility(View.GONE);
                }, 1000);
            }
        });
        mBinding.btnTaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: >>>>>>" + mediaFileData.getPath(), null);
                Log.e("TAG", "onClick: >>>>>>" + mediaFileData.getAbsolutePath(), null);
                PreferenceHelper.putString("picture_uri", mediaFileData.getAbsolutePath());
                //mCamera.takePicture(null, null, mPicture);
                finish();
            }
        });
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceHelper.putString("picture_uri", "");
                //mCamera.takePicture(null, null, mPicture);
                finish();
            }
        });
    }

    public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        Rect touchRect = new Rect(
                (int) (x - 100),
                (int) (y - 100),
                (int) (x + 100),
                (int) (y + 100));
        final Rect targetFocusRect = new Rect(
                touchRect.left * 2000 / mBinding.cameraPreview.getWidth() - 1000,
                touchRect.top * 2000 / mBinding.cameraPreview.getHeight() - 1000,
                touchRect.right * 2000 / mBinding.cameraPreview.getWidth() - 1000,
                touchRect.bottom * 2000 / mBinding.cameraPreview.getHeight() - 1000);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            try {
                List<Camera.Area> focusList = new ArrayList<Camera.Area>();
                Camera.Area focusArea = new Camera.Area(targetFocusRect, 1000);
                focusList.add(focusArea);

                params.setFocusAreas(focusList);
                params.setMeteringAreas(focusList);
                mCamera.setParameters(params);
                /* mCamera.autoFocus(this);*/
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "Unable to autofocus?????");
            }

        }
    }
    /*private Rect calculateTapArea(float x, float y, float coefficient) {
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

        View view;
        int left = clamp((int) x - areaSize / 2, 0, getSurfaceView().getWidth() - areaSize);
        int top = clamp((int) y - areaSize / 2, 0, getSurfaceView().getHeight() - areaSize);

        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        matrix.mapRect(rectF);

        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }*/

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (Exception e) {

            }
        }
    };

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
//        File mediaFile;
//        mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                + "IMG_" + timeStamp + ".jpg");
        mediaFileData = null;
        mediaFileData = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        return mediaFileData;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        PreferenceHelper.putString("picture_uri", "");
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreateMethod();
    }
}
