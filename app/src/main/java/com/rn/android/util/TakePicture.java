package com.rn.android.util;

import static android.app.Activity.RESULT_OK;
import static android.hardware.Camera.getCameraInfo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Surface;
import android.view.WindowManager;

import androidx.core.content.FileProvider;

import com.rn.android.BuildConfig;
import com.rn.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Random;


public class TakePicture {

    private Uri outPutFileUri;
    private final Activity mContext;
    public static int CAMERA_CAPTURE = 100;
    public static int GALLERY = 101;
    public static int CROP_CODE = 203;
    public static String SDCARD = Environment.getExternalStorageDirectory() + "/Seq/";
    public static int PERMISSION_FOR_CAMERA = 11;
    Camera camera;

    public TakePicture(Activity mContext) {
        this.mContext = mContext;
//        setCameraDisplayOrientation(mContext, 0, new Camera());
//        setCameraDisplayOrientation(mContext, getCameraId());
    }

    private int getCameraId() {
        int curCameraId = 0;

        if (Camera.getNumberOfCameras() > 0) {
            curCameraId = (curCameraId + 1) % Camera.getNumberOfCameras();
        } else {
            curCameraId = 0;
        }
        return curCameraId;
    }

    public void setCameraDisplayOrientation(Activity activity, int curCameraId) {
        if (camera == null) {
            try {
                camera = Camera.open(curCameraId);
            } catch (Exception e) {
            }
        }
        Camera.CameraInfo info = new Camera.CameraInfo();

        Camera.getCameraInfo(curCameraId, info);
        WindowManager winManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int rotation = winManager.getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


   /* public void selectImage() throws IOException {
        final CharSequence[] items = {mContext.getResources().getString(R.string.take_new_photo),
                mContext.getResources().getString(R.string.select_from_photo), mContext.getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getResources().getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(mContext.getResources().getString(R.string.take_new_photo))) {

                    try {
                        captureImageUsingCamera();
                        dialog.dismiss();
                    } catch (IOException e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                } else if (items[item].equals(mContext.getResources().getString(R.string.select_from_photo))) {

                    try {
                        pickImageFromGallery();
                        dialog.dismiss();
                    } catch (Exception e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                } else if (items[item].equals(mContext.getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }*/

    // Capture Image From Camera
    public void captureImageUsingCamera() throws ActivityNotFoundException, IOException {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File file = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                OutputStream out = null;
                ContentResolver contentResolver = mContext.getContentResolver();
                ContentValues contentValues = new ContentValues();
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + n + ".jpeg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + mContext.getResources().getString(R.string.app_name));
                outPutFileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                out = contentResolver.openOutputStream(Objects.requireNonNull(outPutFileUri));
                //finalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                Objects.requireNonNull(out);
                String abc = Environment.getExternalStorageDirectory()
                        + "/" + Environment.DIRECTORY_PICTURES
                        + File.separator + mContext.getResources().getString(R.string.app_name)
                        //+ mContext.getResources().getString(R.string.app_name)
                        + "/" + "Image_" + n + ".jpeg";
                file = new File(abc);
                //Log.e(TAG, "saveImageAsPerQuality: >>>>>>>" + file, null);
                out.flush();
                out.close();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                outPutFileUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
            } else {
                outPutFileUri = Uri.fromFile(createImageFile());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outPutFileUri);
        captureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext.startActivityForResult(captureIntent, CAMERA_CAPTURE);
    }

    // Pick Image From Gallery
    public void pickImageFromGallery() throws ActivityNotFoundException {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mContext.startActivityForResult(pickIntent, GALLERY);
    }

    public String onActivityResult(int requestCode, int resultCode, Intent data) throws IOException {
        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_CAPTURE) {
                if (outPutFileUri != null) {
//                    cropImage(outPutFileUri);
                    Uri resultUri = outPutFileUri;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), resultUri);
                    return reduceImageSize(1000, 80, bitmap);
                }
            } else if (requestCode == GALLERY && data != null) {
                Uri fileUri = data == null ? null : data.getData();
                if (fileUri != null) {
//                    cropImage(fileUri);
                    Uri resultUri = fileUri;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), resultUri);
                    return reduceImageSize(1000, 80, bitmap);
                }
            } /*else if (requestCode == CROP_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), resultUri);
                return reduceImageSize(1000, 80, bitmap);
            }*/
        } else {
            return "";
        }
        return "";
    }

    public String reduceImageSize(int maxSize, int quality, Bitmap image) throws IOException {
        int mWidth, mHeight;
        if (image.getWidth() > image.getHeight()) {
            mWidth = maxSize;
            mHeight = Math.round((maxSize * image.getHeight()) / image.getWidth());
            return saveImageAsPerQuality(resizeBitmap(image, mWidth, mHeight), quality);
        } else if (image.getHeight() > image.getWidth()) {
            mHeight = maxSize;
            mWidth = Math.round((maxSize * image.getWidth()) / image.getHeight());
            return saveImageAsPerQuality(resizeBitmap(image, mWidth, mHeight), quality);
        } else if (image.getHeight() == image.getWidth() && image.getHeight() > maxSize) {
            mHeight = maxSize;
            mWidth = maxSize;
            return saveImageAsPerQuality(resizeBitmap(image, mWidth, mHeight), quality);
        } else {
            return saveImageAsPerQuality(image, quality);
        }
    }

    // For File Store
    private File createImageFile() throws IOException {

        File file = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                OutputStream out = null;
                ContentResolver contentResolver = mContext.getContentResolver();
                ContentValues contentValues = new ContentValues();
                Random generator = new Random();
                int n = 1000000;
                n = generator.nextInt(n);
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + n + ".jpeg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + mContext.getResources().getString(R.string.app_name));
                Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                out = contentResolver.openOutputStream(Objects.requireNonNull(imageUri));
                // finalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                Objects.requireNonNull(out);
                String abc = Environment.getExternalStorageDirectory()
                        + "/" + Environment.DIRECTORY_PICTURES
                        + File.separator + mContext.getResources().getString(R.string.app_name)
                        //+ mContext.getResources().getString(R.string.app_name)
                        + "/" + "Image_" + n + ".jpeg";
                file = new File(abc);
                //Log.e(TAG, "saveImageAsPerQuality: >>>>>>>" + file, null);
                out.flush();
                out.close();
            } else {
                File myDir;
                String FolderName = mContext.getResources().getString(R.string.app_name);
                if (FolderName.equalsIgnoreCase("")) {
                    myDir = new File(SDCARD + "/saved_images");
                } else {
                    myDir = new File(SDCARD + "/." + FolderName);
                }
                myDir.mkdirs();
                Random generator = new Random();
                int n = 1000000;
                n = generator.nextInt(n);
                String fname = "Image_" + n + ".jpeg";
                file = new File(myDir, fname);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*File myDir;
        String FolderName = mContext.getResources().getString(R.string.app_name);
        if (FolderName.equalsIgnoreCase("")) {
            myDir = new File(SDCARD + "/saved_images");
        } else {
            myDir = new File(SDCARD + "/." + FolderName);
        }

        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image_" + n + ".jpeg";
        File file = new File(myDir, fname);*/
        return file;
    }

    private Bitmap resizeBitmap(Bitmap mBitmap, int mWidth, int mHeight) {
        return Bitmap.createScaledBitmap(mBitmap, mWidth, mHeight, false);
    }

    private String saveImageAsPerQuality(Bitmap finalBitmap, int quality) throws IOException {
        File file = createImageFile();
        if (file.exists()) file.delete();
        /*try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                OutputStream out = null;
                ContentResolver contentResolver = mContext.getContentResolver();
                ContentValues contentValues = new ContentValues();
                Random generator = new Random();
                int n = 1000000;
                n = generator.nextInt(n);
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + n + ".jpeg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + mContext.getResources().getString(R.string.app_name));
                Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                out = contentResolver.openOutputStream(Objects.requireNonNull(imageUri));
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                Objects.requireNonNull(out);
                String abc = Environment.getExternalStorageDirectory()
                        + "/" + Environment.DIRECTORY_PICTURES
                        + File.separator + mContext.getResources().getString(R.string.app_name)
                        //+ mContext.getResources().getString(R.string.app_name)
                        + "/" + "Image_" + n + ".jpeg";
                file = new File(abc);
                //Log.e(TAG, "saveImageAsPerQuality: >>>>>>>" + file, null);
                out.flush();
                out.close();
            } else {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

   /* private void orientationData(){
        ExifInterface ei = new ExifInterface(photoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private int getExifOrientation() {
        ExifInterface exif;
        int orientation = 0;
        try {
            exif = new ExifInterface(mImagePath);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "got orientation " + orientation);
        return orientation;
    }*/
}
