package com.rn.android;

import static androidx.core.content.FileProvider.getUriForFile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.ActivityMessPrescriptionDetailsPrnBinding;
import com.metiz.pelconnect.databinding.DialogShowAlertBinding;
import com.rn.android.base.MediaSelectionDialog;
import com.rn.android.listeners.PickerOptionListener;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.MyReceiver;
import com.rn.android.util.PreferenceHelper;
import com.rn.android.util.TakePicture;
import com.rn.android.util.TouchImageView;
import com.rn.android.util.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedPrescriptionDetailsActivityForPRN extends BaseActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 124;
    private static int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            //Manifest.permission.ACCESS_MEDIA_LOCATION,
    };

    /*@BindView(R.id.tv_drug_name)
    TextView tvDrugName;
    @BindView(R.id.tv_rx_number)
    TextView tvRxNumber;
    @BindView(R.id.tv_drug_details)
    TextView tvDrugDetails;
    @BindView(R.id.tv_drug_time)
    TextView tvDrugTime;
    @BindView(R.id.tv_drug_qty)
    EditText tvDrugQty;
    @BindView(R.id.btn_taking)
    AppCompatButton btnTaking;
    @BindView(R.id.btn_refused)
    AppCompatButton btnRefused;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rxImage)
    ImageView rxImage;
    @BindView(R.id.patient_image)
    CircleImageView patientImage;
    @BindView(R.id.img_drug)
    ImageView imgDrug;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_birthdate)
    TextView txtBirthdate;
    @BindView(R.id.edt_notes)
    ActionEditText edtNotes;
    @BindView(R.id.verified_med_order)
    CheckBox verified_med_order;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    //    private KProgressHUD hud;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    @BindView(R.id.img_promotional_item)
    ImageView imgPromotionalItem;*/
    int PERMISSION_ALL = 1;
    ActivityMessPrescriptionDetailsPrnBinding mBinding;
    KProgressHUD hud;
    float drugQty = 0;
    int drugQtyinInt = 0;
    int tvDrugQtyinInt = 0;
    int tran_id = 0, facility_id = 0, patientId;
    String ndc = "";
    String patientName = "";
    String patient_fName = "";
    String patient_lName = "";
    String Gender = "";
    String Gender_Full = "";
    String BirthDate = "";
    String imageUrl = "";
    String fileName;
    String numberD;
    String Msg = "";
    String drugName = "", rxNumber = "", drugDetails = "", drugTime = "", sigCode = "";
    boolean Is_PrescriptionImg;
    AlertDialog alertDialog = null;
    private TakePicture takePicture;
    private BroadcastReceiver MyReceiver = null;
    private String base64 = null;
    private boolean isLoa;

    private DialogShowAlertBinding mDialogShowAlertBinding;
    private Dialog dialogSHowAlert;

    public static void clearCache(Context context) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (path.exists() && path.isDirectory()) {
            for (File child : path.listFiles()) {
                child.delete();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_mess_prescription_details_prn);
        mBinding = DataBindingUtil.setContentView(activity, R.layout.activity_mess_prescription_details_prn);
//      ButterKnife.bind(this);
        MyReceiver = new MyReceiver();
        initProgress();

        takePicture = new TakePicture(activity);

        drugName = getIntent().getStringExtra("drug");
        rxNumber = getIntent().getStringExtra("rxNumber");
        drugDetails = getIntent().getStringExtra("sig_detail");
        sigCode = getIntent().getStringExtra("sig_code");
        drugTime = getIntent().getStringExtra("dose_time");
        drugQty = getIntent().getFloatExtra("dose_Qty", 0);
        tran_id = getIntent().getIntExtra("tran_id", 0);
        facility_id = getIntent().getIntExtra("facility_id", 0);
        patientId = getIntent().getIntExtra("patientId", 0);
        ndc = getIntent().getStringExtra("ndc");
        patientName = getIntent().getStringExtra("name");
        patient_fName = getIntent().getStringExtra("fname");
        patient_lName = getIntent().getStringExtra("lname");
        Msg = getIntent().getStringExtra("msg");
        Gender = getIntent().getStringExtra("gender");
        Gender_Full = getIntent().getStringExtra("gender_full");
        BirthDate = getIntent().getStringExtra("BirthDate");
        isLoa = getIntent().getBooleanExtra("isMissedDose", false);
        Is_PrescriptionImg = getIntent().getBooleanExtra("Is_PrescriptionImg", false);
        Log.e("TAG", "drugQty: " + drugQty);
        Log.e("Fraction Point:", "--->:" + Utils.convertDecimalToFraction(drugQty));
        numberD = String.valueOf(drugQty);
        numberD = numberD.substring(numberD.indexOf("."));
        if (Is_PrescriptionImg) {
            mBinding.rxImage.setVisibility(View.VISIBLE);
        } else {
            mBinding.rxImage.setVisibility(View.GONE);
        }

        if (Msg != null && !Msg.isEmpty() && !Msg.equals("")) {
            hud.dismiss();
            showAlertDialog(Msg, context.getResources().getString(R.string.medpass_alert), "cancel", "Ok", "3");
        }

        Log.e("TAG", "isMissedDose 1: " + isLoa);
        Log.e("TAG", "patientId 2: " + patientId);

        getUserImage(patientId);
        getDrugImage(ndc);
        mBinding.btnTaking.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_gry));
        mBinding.btnTaking.setEnabled(false);
        mBinding.btnTaking.setTextColor(getResources().getColor(R.color.white));
        mBinding.btnTaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoa) {
                    String note = mBinding.edtNotes.getText().toString().trim();
                    String GivenDrugQty = mBinding.tvDrugQty.getText().toString().trim();
                    if (!note.isEmpty()) {
                        if (!GivenDrugQty.isEmpty()) {
                            showDialogForAdministered();
                        } else {
                            Toast toast = Toast.makeText(context, "Drug Quantity can't be left blank", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(context, "Note can't be left blank", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                } else {
                    showDialogForAdministered();
                }
            }
        });
        mBinding.btnRefused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hud.show();
                if (isLoa) {
                    String note = mBinding.edtNotes.getText().toString().trim();
                    if (!note.isEmpty() && !"".equals(note)) {
                        hud.dismiss();
                        showDialogForRefused();
                    } else {
                        hud.dismiss();
                        Toast toast = Toast.makeText(context, "Note can't be left blank", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                } else {
                    hud.dismiss();
                    showDialogForRefused();
                }
            }
        });

        mBinding.verifiedMedOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mBinding.btnTaking.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_bg));
                    mBinding.btnTaking.setTextColor(getResources().getColor(R.color.white));
                    mBinding.btnTaking.setEnabled(true);

                    mBinding.btnRefused.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_gry));
                    mBinding.btnRefused.setEnabled(false);
                    mBinding.btnRefused.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBinding.btnTaking.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_gry));
                    mBinding.btnTaking.setEnabled(false);
                    mBinding.btnTaking.setTextColor(getResources().getColor(R.color.white));

                    mBinding.btnRefused.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_bg));
                    mBinding.btnRefused.setTextColor(getResources().getColor(R.color.white));
                    mBinding.btnRefused.setEnabled(true);
                }
            }
        });

        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.rxImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrescription(tran_id);

            }
        });
        mBinding.imgDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageDrugDialog(imageUrl);

            }
        });
        mBinding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        mBinding.llImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialogForImage();
                if (hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                } else {
                    dialogForImage();
                }
            }
        });
    }

    public boolean hasPermissions(Context context, String... permissions) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try {
            if (hasPermissions(context, PERMISSIONS)) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
            } else {
                dialogForImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlertDialog(String message, String title, String negativeBtn, String positiveBtn, String check) {

        dialogSHowAlert = new Dialog(context);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mDialogShowAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_show_alert, null, false);
        dialogSHowAlert.setContentView(mDialogShowAlertBinding.getRoot());
//                            dialogDiscontinue.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialogSHowAlert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogSHowAlert.getWindow().setStatusBarColor(context.getResources().getColor(R.color.white));
        dialogSHowAlert.setCancelable(false);
        dialogSHowAlert.setCanceledOnTouchOutside(false);

        mDialogShowAlertBinding.tvTitle.setText(title);
        mDialogShowAlertBinding.tvMessage.setText(message);
        mDialogShowAlertBinding.okBtn.setText(positiveBtn);
        mDialogShowAlertBinding.canleBtn.setText(negativeBtn);

        mDialogShowAlertBinding.imgalert.setVisibility(View.GONE);
        mDialogShowAlertBinding.tvTitle.setVisibility(View.GONE);
        mDialogShowAlertBinding.canleBtn.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(title)) {
            mDialogShowAlertBinding.tvTitle.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(negativeBtn)) {
            mDialogShowAlertBinding.canleBtn.setVisibility(View.VISIBLE);
        }
        mDialogShowAlertBinding.okBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            if (check.equalsIgnoreCase("1")) {

            } else if (check.equalsIgnoreCase("2")) {
                finish();
            } else if (check.equalsIgnoreCase("3")) {
            }
        });
        mDialogShowAlertBinding.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            if (check.equalsIgnoreCase("1")) {

            } else if (check.equalsIgnoreCase("2")) {

            } else if (check.equalsIgnoreCase("3")) {
                finish();
            }
        });
        dialogSHowAlert.show();

       /* if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);

        Log.e("TAG", "showAlertDialog: >>>>>" + check, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setMessage(message);
        if (!TextUtils.isEmpty(title)) {
            builder.setCustomTitle(textView);
        }
        builder.setCancelable(false);
        if (!TextUtils.isEmpty(negativeBtn)) {
            builder.setNegativeButton(negativeBtn, (dialog, id) -> {
                dialog.dismiss();
                if (check.equalsIgnoreCase("1")) {

                } else if (check.equalsIgnoreCase("2")) {

                } else if (check.equalsIgnoreCase("3")) {

                }
            });
        }
        builder.setPositiveButton(positiveBtn, (dialog, id) -> {
            dialog.dismiss();
            if (check.equalsIgnoreCase("1")) {

            } else if (check.equalsIgnoreCase("2")) {

            } else if (check.equalsIgnoreCase("3")) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    @Override
    protected void onResume() {
        broadcastIntent();
        super.onResume();
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void showDialogForAdministered() {
        UpdatePatientMedPassDrugDetails("D");
    }

    private void showDialogForRefused() {
        UpdatePatientMedPassDrugDetails("R");
    }

    /*private boolean checkPermissions() {
        if (SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(MedPrescriptionDetailsActivityForPRN.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("if >>", "true");
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private boolean cameraPermission() {
        if (SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(MedPrescriptionDetailsActivityForPRN.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("if >>", "true");

            return true;
        } else {
            requestPermissionForCamera();
            return false;
        }
    }*/

    private void dialogForImage() {

        TextView textView = new TextView(context);
        textView.setText("Acknowledge Alert");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setCustomTitle(textView);
        builder.setMessage(context.getResources().getString(R.string.images_upload_message))
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    new MediaSelectionDialog(context, new PickerOptionListener() {
                        @Override
                        public void onTakeCameraSelected() {
                            try {
                                Intent intent = new Intent(context, Custom_CameraActivity.class);
                                startActivityForResult(intent, 105);
//                                    takePicture.captureImageUsingCamera();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            /*try {
                                takePicture.captureImageUsingCamera();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/

//                                fileName = System.currentTimeMillis() + ".jpg";
//                        if (cameraPermission()) {
//                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
//                                if (takePictureIntent.resolveActivity(`MedPrescriptionDetailsActivityForPRN.this`.getPackageManager()) != null) {
//                                    startActivityForResult(takePictureIntent, REQUEST_CAMERA);
//                                }
//                        }
                        }

                        @Override
                        public void onChooseGallerySelected() {
//                        if (checkPermissions()) {
//                                galleryIntent();
//                        }
                            try {
                                takePicture.pickImageFromGallery();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }

    // TODO ask for Storage permission
    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    // TODO ask for Camera permission
    private void requestPermissionForCamera() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(context, context.getPackageName() + ".provider", image);
    }

    /*  @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          Log.e("permission >>", "" + requestCode + " >>> " + permissions.length + " >> " + grantResults.length);
          switch (requestCode) {
              case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                  Log.e("123 >>", "External Storage");
                  if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      galleryIntent();
                  } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                      *//* Snackbar.make(constraintMain, R.string.txt_gallery_permission_denied, Snackbar.LENGTH_LONG).show();*//*
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                            return;
                        }
                    }
                }
                break;
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("124 >>", "Camera");
                    fileName = System.currentTimeMillis() + ".jpg";
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                    if (takePictureIntent.resolveActivity(MedPrescriptionDetailsActivityForPRN.this.getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                    }
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                }
                break;
        }
    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        Log.e("onActivityResult >>", "" + requestCode + " >> " + resultCode + " >> " + resultData);
//        if (requestCode == REQUEST_CAMERA) {
        if (requestCode == 105) {
            try {
                fileName = "";
                fileName = PreferenceHelper.getString("picture_uri", "");
                if (!TextUtils.isEmpty(fileName)) {

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 4;
                    Bitmap bmOriginal = BitmapFactory.decodeFile(fileName, bitmapOptions);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bmOriginal = Bitmap.createBitmap(bmOriginal, 0, 0, bmOriginal.getWidth(), bmOriginal.getHeight(), matrix, true);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmOriginal.compress(Bitmap.CompressFormat.JPEG, 80, baos); //bm is the bitmap object
                    byte[] bytes = baos.toByteArray();
                    base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                    mBinding.imgPromotionalItem.setImageBitmap(bmOriginal);
                    //Glide.with(context).load(fileName).into(mBinding.imgPromotionalItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK) {
            try {
                fileName = "";
                fileName = takePicture.onActivityResult(requestCode, resultCode, resultData);
//                    Log.e("Android 11 >>", "" + getCacheImagePath(fileName) + " >> " + fileName);
                Bitmap bm = BitmapFactory.decodeFile(fileName);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 80, baos); //bm is the bitmap object
                byte[] bytes = baos.toByteArray();
                base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                mBinding.imgPromotionalItem.setImageBitmap(bm);
                Glide.with(context).load(fileName).into(mBinding.imgPromotionalItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                if (bm != null) {
                    mBinding.imgPromotionalItem.setImageBitmap(bm);
                    try {
                        hud.show();
                        base64 = Utils.convertImageToBase64(bm);
                        hud.dismiss();
                    } catch (Exception ex) {
                        hud.dismiss();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void initProgress() {
        /*progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);*/
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false);

    }

    private void setData() {
        mBinding.tvDrugName.setText(drugName);
        mBinding.tvRxNumber.setText("Rx Number: " + rxNumber);
        mBinding.tvDrugDetails.setText(drugDetails);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
        Date date = new Date();
        System.out.println(formatter.format(date));
        mBinding.tvDrugTime.setText(formatter.format(date));
        if (drugQty == 1.0) {
            tvDrugQtyinInt = 1;
        } else if (drugQty == 2.0) {
            tvDrugQtyinInt = 2;
        } else if (drugQty == 3.0) {
            tvDrugQtyinInt = 3;
        } else if (drugQty == 4.0) {
            tvDrugQtyinInt = 4;
        } else if (drugQty == 5.0) {
            tvDrugQtyinInt = 5;
        } else if (drugQty == 6.0) {
            tvDrugQtyinInt = 6;
        } else if (drugQty == 7.0) {
            tvDrugQtyinInt = 7;
        }
//        /*if (tvDrugQtyinInt != 0) {
//            mBinding.tvDrugQty.setText(tvDrugQtyinInt + "");
//        } else {
//            mBinding.tvDrugQty.setText(drugQty + "");
//        }*/
        mBinding.txtName.setText(patientName);
        mBinding.txtGender.setText(Gender);
        mBinding.txtBirthdate.setText(BirthDate);

    }

    private void UpdatePatientMedPassDrugDetails(String doseStatus) {
        if (mBinding.tvDrugQty.getText().toString().trim().isEmpty() || mBinding.tvDrugQty.getText().toString().trim().equals("0")|| mBinding.tvDrugQty.getText().toString().trim().equals(".")
                || mBinding.tvDrugQty.getText().toString().trim().equals("0.0")
                || mBinding.tvDrugQty.getText().toString().trim().equals(".0")) {
            Toast.makeText(context, "Need to provide drug Quantity.", Toast.LENGTH_SHORT).show();
        } else {
            hud.show();
            JSONObject param = new JSONObject();
            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                System.out.println(formatter.format(date));
                param.put("dose_date", formatter.format(date));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SSS");
                param.put("dose_time", time.format(new Date()));
                param.put("patient_id", patientId);
                param.put("facility_id", facility_id);
                param.put("dose_Qty", Double.parseDouble(mBinding.tvDrugQty.getText().toString().trim()));
                param.put("dose_status", doseStatus);
                param.put("note", mBinding.edtNotes.getText().toString());
                param.put("med_type", "P");
                param.put("sig_code", sigCode);
                param.put("sig_detail", drugDetails);
                param.put("internal_rx_id", tran_id);
                param.put("rx_number", rxNumber);
                param.put("drug", drugName);
                param.put("patient_last", patient_lName);
                param.put("patient_first", patient_fName);
                String startDateString = Utils.DateFromOnetoAnother(BirthDate, "MM/dd/yyyy", "MM/dd/yyyy");
                param.put("dob", startDateString);
                param.put("gender", Gender);
                param.put("ndc", ndc);
                param.put("VerifyDrug", mBinding.verifiedMedOrder.isChecked());
                param.put("CreatedBY", Integer.parseInt(MyApplication.getPrefranceData("UserID")));
                if (base64 != null) {
                    param.put("MedImagePath", base64);    //Image in base64
                }

            } catch (Exception r) {
                r.printStackTrace();

            }

            Log.e("TAG", "UpdatePatientMedPassDrugDetails: " + param.toString());
            NetworkUtility.makeJSONObjectRequest(API.ManagePatientPRNMedPassDrugDetails, param, API.ManagePatientPRNMedPassDrugDetails, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        hud.dismiss();
                        if (result != null) {
                            showAlertDialog(context.getResources().getString(R.string.successful), "", "", "OK", "2");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(JSONObject result) {
                    hud.dismiss();
                }
            });

//            base64 = null;
        }


    }

    private void getPrescription(int tran_id) {
        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.PriscriptionImagePath + "?tran_id=" + tran_id, new JSONObject(), API.PriscriptionImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    hud.dismiss();
                    if (result.getString("Data").isEmpty()) {
                        noPrescriptionDialog();
                    } else {
                        openImageDialog(result.getString("Data"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
            }
        });
    }

    private void noPrescriptionDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    dialog.dismiss();
                }
            }
        };

        /*androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("No Prescription Image available.").setPositiveButton("OK", dialogClickListener).show();*/
        showAlertDialog(context.getResources().getString(R.string.no_prescription_image_available), "", "", "OK", "1");
    }

    private void getUserImage(int patientId) {
        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.PatientImagePath + "?patient_id=" + patientId, new JSONObject(), API.PatientImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    hud.dismiss();
                    setData();
                    mBinding.llMain.setVisibility(View.VISIBLE);

                    if (result.getString("Data").equalsIgnoreCase("")) {
                        Picasso.get().load(R.drawable.ic_user_placeholder)
                                .placeholder(R.drawable.ic_user_placeholder)
                                .error(R.drawable.ic_user_placeholder)
                                .into(mBinding.patientImage);
                    } else {
                        Picasso.get().load(result.getString("Data"))
                                .placeholder(R.drawable.ic_user_placeholder)
                                .error(R.drawable.ic_user_placeholder)
                                .into(mBinding.patientImage);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    setData();

                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.show();
                setData();

            }
        });
    }


    private void getDrugImage(String ndc) {

        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.DrugImagePath + "?ndc=" + ndc, new JSONObject(), API.DrugImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    hud.dismiss();
                    imageUrl = result.getString("Data");
                    Glide.with(activity).load(result.getString("Data")).placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder).into(mBinding.imgDrug);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
            }
        });
    }

    private void openImageDialog(String imageURL) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TouchImageView dialog_image = (TouchImageView) dialog.findViewById(R.id.dialog_image);
        SimpleDraweeView drawee_image = (SimpleDraweeView) dialog.findViewById(R.id.drawee_image);
        final ImageView dialog_cancel = (ImageView) dialog.findViewById(R.id.dialog_cancel);
        //http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(this).load(imageURL).placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder).into(dialog_image);
        drawee_image.setVisibility(View.GONE);
        Uri uri = Uri.parse(imageURL);
//        Uri uri = Uri.parse("http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg");
        drawee_image.setImageURI(uri);
        dialog.show();
    }

    private void openImageDrugDialog(String imageURL) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TouchImageView dialog_image = (TouchImageView) dialog.findViewById(R.id.dialog_image);
        SimpleDraweeView drawee_image = (SimpleDraweeView) dialog.findViewById(R.id.drawee_image);
        final ImageView dialog_cancel = (ImageView) dialog.findViewById(R.id.dialog_cancel);
        //http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(this).load(imageURL).placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder).into(dialog_image);
        drawee_image.setVisibility(View.GONE);
        Uri uri = Uri.parse(imageURL);
//        Uri uri = Uri.parse("http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg");
        drawee_image.setImageURI(uri);
        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
