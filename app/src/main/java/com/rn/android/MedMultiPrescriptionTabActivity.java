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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.ActivityMedMultiPrescriptionTabBinding;
import com.metiz.pelconnect.databinding.DialogShowAlertBinding;
import com.rn.android.Adapter.MedMultiPassPrescriptionAdapter;
import com.rn.android.base.MediaSelectionDialog;

import com.rn.android.dialog.AlloyHospitalDialogNew;
import com.rn.android.listeners.PickerOptionListener;
import com.rn.android.model.AlloyHospital;
import com.rn.android.model.MedMultiPrescriptionListModel;
import com.rn.android.model.ModelMessPrescription;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.MyReceiver;
import com.rn.android.util.PreferenceHelper;
import com.rn.android.util.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class MedMultiPrescriptionTabActivity extends BaseActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 124;
    private static int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public int barcodeOpen = 0;
    public int isOpen = 0;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            //Manifest.permission.ACCESS_MEDIA_LOCATION,
    };
    int PERMISSION_ALL = 7;
    String versionRelease = Build.VERSION.RELEASE;
    int PatientId = 0;
    List<MedMultiPrescriptionListModel.DataBean.ObjdetailsBean> modelMessPrescriptionList = new ArrayList<>();
    List<ModelMessPrescription> modelMessPrescriptionList1 = new ArrayList<>();
    MedMultiPassPrescriptionAdapter medPassPrescriptionAdapter;
    List<AlloyHospital.LOAHospital> loaHospitalList = new ArrayList<>();
    String externalPatientID;
    String date;
    String time;
    String id;
    String fileName;
    String multiPack;
    String patientName = "";
    String patient_fName = "";
    String patient_lName = "";
    String Gender = "";
    String Gender_full = "";
    String BirthDate = "";
    String QRcodeformat = "";
    String RxNumber = "";
    boolean isListEmpty;
    boolean Is_PrescriptionImg;
    boolean isNote = true;
    boolean IsMissedDose = false;
    private ActivityMedMultiPrescriptionTabBinding mBinding;
    private KProgressHUD hud;
    private BroadcastReceiver MyReceiver = null;
    private int SCANNED_BARCODE_ID = 0;
    private String base64 = null;
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
        mBinding = DataBindingUtil.setContentView(activity, R.layout.activity_med_multi_prescription_tab);
        ButterKnife.bind(this);
        context = MedMultiPrescriptionTabActivity.this;
        MyReceiver = new MyReceiver();
        initProgress();
        initRecyclerView();
        Log.e("isNote OnCreate:", "" + isNote);
        MyApplication.setPreferencesBoolean("isDesc", false);
        if (getIntent() != null) {
            externalPatientID = getIntent().getStringExtra("externalPatientID");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            PatientId = getIntent().getIntExtra("PatientId", 0);
            patientName = getIntent().getStringExtra("name");
            patient_fName = getIntent().getStringExtra("fname");
            patient_lName = getIntent().getStringExtra("lname");
            multiPack = getIntent().getStringExtra("multiPack");
            Log.e("TAG", "multiPack::::: ===> " + multiPack);
            QRcodeformat = getIntent().getStringExtra("QRcodeformat");
            Gender = getIntent().getStringExtra("gender");
            Gender_full = getIntent().getStringExtra("gender_full");
            BirthDate = getIntent().getStringExtra("BirthDate");
            id = getIntent().getStringExtra("id");
            //isLOAHospital = getIntent().getBooleanExtra("isLOAHospital", false);
            Is_PrescriptionImg = getIntent().getBooleanExtra("Is_PrescriptionImg", false);
            isListEmpty = getIntent().getBooleanExtra("isListEmpty", false);
        }
        Log.e("TAG", "externalPatientID123: " + externalPatientID);
        Log.e("TAG", "PatientId: " + PatientId);

        Log.e("TAG", "multiPrescription: ");

        GetCurrentMedpassDetailsByPatientID(id);

        mBinding.txtName.setText(patientName);
        mBinding.txtGender.setText(Gender);
        mBinding.txtBirthdate.setText(BirthDate);
        getUserImage(PatientId);
        mBinding.swipeRefresh.setOnRefreshListener(() -> GetCurrentMedpassDetailsByPatientID(id));

        mBinding.txtAlloyHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loaHospitalList.size() > 0) {
                    new AlloyHospitalDialogNew(activity, context, loaHospitalList, PatientId, patientName, IsMissedDose, time).show();
                } else {
                    Toast.makeText(context, "LOA list not available", Toast.LENGTH_SHORT).show();
                }
            }

            private void isDrugShiftedToLoa(boolean b) {
            }
        });

        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.verifiedOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        mBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (!TextUtils.isEmpty(newText.trim())) {
                        if (mBinding.rvMessMultiPrescriptionList.getAdapter() != null) {
                            newText.replace(",", "");
                            ((MedMultiPassPrescriptionAdapter) mBinding.rvMessMultiPrescriptionList.getAdapter()).filter(newText);
                        }
                    } else {
                        ((MedMultiPassPrescriptionAdapter) mBinding.rvMessMultiPrescriptionList.getAdapter()).filter("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });

        mBinding.llBack.setOnClickListener(v -> finish());

        mBinding.btnTaking.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_gry));
        mBinding.btnTaking.setEnabled(false);
        mBinding.btnTaking.setTextColor(getResources().getColor(R.color.white));
        mBinding.btnTaking.setOnClickListener((View view) -> {

            String note = mBinding.edtNotes.getText().toString().trim();
            Log.e("isNote:", "" + isNote);
            if (isNote) {
                if (!note.isEmpty() && !"".equals(note)) {
                    if (mBinding.edtConsultantname.getVisibility() == View.VISIBLE) {
                        String consultantName = mBinding.edtConsultantname.getText().toString().trim();
                        if (!consultantName.equals("")) {
                            hud.show();
                            UpdatePatientMedpassDetails_By_MedpassID(true);
                        } else {
                            Toast toast = Toast.makeText(context, "Consultant Name can't be left blank", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        hud.show();
                        UpdatePatientMedpassDetails_By_MedpassID(true);
                    }
                } else {
                    Toast toast = Toast.makeText(context, "Please note reason", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                UpdatePatientMedpassDetails_By_MedpassID(true);
            }

        });
        mBinding.btnRefused.setOnClickListener((View view) -> {
            String note = mBinding.edtNotes.getText().toString().trim();
            if (isNote) {
                if (!note.isEmpty() && !"".equals(note)) {
                    if (mBinding.edtConsultantname.getVisibility() == View.VISIBLE) {
                        String consultantName = mBinding.edtConsultantname.getText().toString().trim();
                        if (!consultantName.equals("")) {
                            UpdatePatientMedpassDetails_By_MedpassID(false);
                        } else {
                            Toast toast = Toast.makeText(context, "Consultant Name can't be left blank", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        UpdatePatientMedpassDetails_By_MedpassID(false);
                    }
                } else {
                    Toast toast = Toast.makeText(context, "Please note reason", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                UpdatePatientMedpassDetails_By_MedpassID(false);
            }


        });


        mBinding.llImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                } else {
                    dialogForImage();
                }
//                dialogForImage();
            }
        });
        getLoaHospitalApi();
    }

    private void getLoaHospitalApi() {
        hud.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API.GetLoaHospitalNames, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                hud.dismiss();
                try {
                    if (result != null) {
                        if (result.has("ResponseStatus") && result.getString("ResponseStatus").equalsIgnoreCase(String.valueOf(1))) {
                            loaHospitalList.clear();
                            if (result.has("Data")) {
                                JSONArray jsonArray = result.getJSONArray("Data");
                                if (jsonArray.length() > 0) {
                                    for (int k = 0; k < jsonArray.length(); k++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(k);
                                        AlloyHospital.LOAHospital loaHospital = new AlloyHospital.LOAHospital();
                                        loaHospital.setLOAHospital_ID(jsonObject.getInt("LOAHospital_ID"));
                                        loaHospital.setLOAHospitalType(jsonObject.getString("LOAHospitalType"));
                                        loaHospitalList.add(loaHospital);
                                    }
                                    Log.e("TAG", "loaHospitalList size: " + loaHospitalList);
                                } else {
                                    showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");
                                }
                            }
                        } else {
                        }
                    } else {
                    }
                } catch (Exception ex) {
                    showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hud.dismiss();
                Log.e("TAG", "onErrorResponse:>>>>>>>>>> " + error.getMessage(), null);
                showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");
                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong));
            }
        });

        jsonObjectRequest.setRetryPolicy(new

                DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().

                addToRequestQueue(jsonObjectRequest);
       /* NetworkUtility.getJSONObjectRequest(API.GetLoaHospitalNames, new JSONObject(), API.GetLoaHospitalNames, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onError(JSONObject result) {
                showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");
            }
        });*/
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

    public String parseDateToddMMyyyy(String time) {
        Log.e("TAG", "parseDateToddMMyyyy: " + time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(Date.parse(time));
    }

   /* @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean checkPermissions() {
        if (SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(MedMultiPrescriptionTabActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("if >>", "true");
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean cameraPermission() {
        if (SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(MedMultiPrescriptionTabActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.e("if >>", "true");
            return true;
        } else {
            requestPermissionForCamera();
            return false;
        }
    }*/

   /* // TODO ask for Storage permission
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(MedMultiPrescriptionTabActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    // TODO ask for Camera permission
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestPermissionForCamera() {
        ActivityCompat.requestPermissions(MedMultiPrescriptionTabActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
    }*/

    private void dialogForImage() {
        TextView textView = new TextView(context);
        textView.setText("Acknowledge Alert");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setCustomTitle(textView);
        builder.setMessage(context.getResources().getString(R.string.images_upload_message))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new MediaSelectionDialog(context, new PickerOptionListener() {
                            @Override
                            public void onTakeCameraSelected() {
                                fileName = System.currentTimeMillis() + ".jpg";

                                try {
                                    Intent intent = new Intent(context, Custom_CameraActivity.class);
                                    startActivityForResult(intent, 105);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                                if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                                }*/

                            }

                            @RequiresApi(api = Build.VERSION_CODES.Q)
                            @Override
                            public void onChooseGallerySelected() {
//                                if (checkPermissions()) {
                                galleryIntent();
//                                }
                            }
                        }).show();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }

   /* @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("permission >>", "" + requestCode + " >>> " + permissions.length + " >> " + grantResults.length);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                Log.e("123 >>", "External Storage");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    */
    /* Snackbar.make(constraintMain, R.string.txt_gallery_permission_denied, Snackbar.LENGTH_LONG).show();*//*
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
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
                    if (takePictureIntent.resolveActivity(MedMultiPrescriptionTabActivity.this.getPackageManager()) != null) {
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
    }*/

    private Uri getCacheImagePath(String fileName) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(context, activity.getPackageName() + ".provider", image);
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
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void sendLogToBackend(String message) {
//        hud.show();
        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("UserID", MyApplication.getPrefranceData("UserID"));
            mainObj.put("DateAndTime", getDateTime());
            mainObj.put("PatientID", PatientId);
            mainObj.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
            mainObj.put("DivceName", Build.MANUFACTURER + " " + Build.DEVICE);
            mainObj.put("ModelNo", Build.MODEL);
            mainObj.put("DeviceVersion", versionRelease);
            mainObj.put("Message", message);
            mainObj.put("ScaningValue", QRcodeformat);
            mainObj.put("Appversion", MyApplication.getPrefranceData("Version"));//
            mainObj.put("DoseDate", date);//
            mainObj.put("DoseTime", time);//


            Log.e("TAG", "sendLogToBackend: " + versionRelease + MyApplication.getPrefranceData("UserID") + Build.MANUFACTURER + " " + Build.DEVICE + android.os.Build.MODEL + getDateTime() + message);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        NetworkUtility.makeJSONObjectRequest(API.InsertMedpassLog, mainObj, API.InsertMedpassLog, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
//                hud.dismiss();
                try {
                    if (result != null) {
                        Log.e("TAG", "onSuccess: " + result.get("Data"));
                    }

                } catch (Exception ex) {
                    dismissProgressDialog();
                    ex.printStackTrace();
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something Went Wrong!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "1");
                    InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onError(JSONObject result) {
//                hud.dismiss();
               /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Something Went Wrong!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "1");
                InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

            }
        });
    }


    private void UpdatePatientMedpassDetails_By_MedpassID(boolean isAdministered) {
        hud.show();
        JSONObject mainObj = new JSONObject();


        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < modelMessPrescriptionList.size(); i++) {
            JSONObject param = new JSONObject();
            try {

                param.put("patient_id", modelMessPrescriptionList.get(i).getPatient_id());
                param.put("facility_id", modelMessPrescriptionList.get(i).getFacility_id());
                param.put("note", isNote ? mBinding.edtNotes.getText().toString() : "");
                if (isAdministered) {
                    param.put("dose_status", "D");
                } else {
                    param.put("dose_status", "R");
                }
                param.put("Updated_by", MyApplication.getPrefranceData("UserID"));
                param.put("VerifyDrug", mBinding.verifiedOrder.isChecked());
                param.put("MedpassID", modelMessPrescriptionList.get(i).getMedpassID());
                param.put("ConsultantName", mBinding.edtConsultantname.getText().toString());
                jsonArray.put(param);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        try {
            mainObj.put("details", jsonArray);
            mainObj.put("MedImage", base64);
              Log.e("TAG", "UpdatePatientMedpassDetails_By_MedpassID: " + jsonArray);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        NetworkUtility.makeJSONObjectRequest(API.UpdatePatientMedpassDetails_By_MedpassID, mainObj, API.UpdatePatientMedpassDetails_By_MedpassID, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    if (result != null) {
                        Log.e("TAG", "onSuccess: " + result.get("Data"));
                        boolean isData = (boolean) result.get("Data");
                        if (isData) {
                            /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Successful")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            Application.setPreferencesBoolean("isShowDcMedDialog", false);
                                            dialog.dismiss();
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                            Intent intent = new Intent(context, PatientListActivityNew.class);
                                            intent.putExtra("medPass", true);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();*/
                            showAlertDialog(context.getResources().getString(R.string.successful), "", "", "OK", "6");

                        }
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something Went Wrong!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "1");
                    InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
             /*   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Something Went Wrong!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "1");
                InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

            }
        });
    }

    private void GetCurrentMedpassDetailsByPatientIDOnResume(String externalPatientId) {
        Log.e("TAG", "GetCurrentMedpassDetailsByPatientID: " + externalPatientId);
        hud.show();
        date = parseDateToddMMyyyy(date);
        NetworkUtility.getJSONObjectRequest(API.GetCurrentMedpassDetailsByPatientID + "?FacilityID=" + MyApplication.getPrefranceDataInt("ExFacilityID") + "&PatientID=" + externalPatientId + "&Dosedate=" + date + "&DoseTime=" + time, new JSONObject(), API.GetCurrentMedpassDetailsByPatientID, new VolleyCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {

                    if (result != null) {
                        Type listType = new TypeToken<List<MedMultiPrescriptionListModel.DataBean.ObjdetailsBean>>() {
                        }.getType();

                        modelMessPrescriptionList = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("objdetails").toString(), listType);

                        Log.e("MissdoseMsg:", "onSuccess: " + result.getJSONObject("Data").getString("MissdoseMsg") + "  Size--->" + modelMessPrescriptionList.size() + "----->" + result.getJSONObject("Data").getString("Msg"));

                        if (modelMessPrescriptionList.size() > 0) {
                            medPassPrescriptionAdapter = new MedMultiPassPrescriptionAdapter(activity, context, modelMessPrescriptionList, Is_PrescriptionImg, isNote, multiPack);
                            mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            mBinding.rvMessMultiPrescriptionList.setAdapter(medPassPrescriptionAdapter);
                            mBinding.search.setVisibility(View.VISIBLE);
                            mBinding.llSearch.setVisibility(View.VISIBLE);
                            mBinding.search.clearFocus();
                            if (mBinding.swipeRefresh.isRefreshing()) {
                                mBinding.swipeRefresh.setRefreshing(false);
                            }
                            Log.d("TAG", "modelMessPrescriptionList: " + modelMessPrescriptionList.size());
                        }
                    }
                } catch (Exception ex) {
                    sendLogToBackend(ex.getMessage());
                    Log.e("Exception:", "onSuccess: " + ex.getMessage());
                    ex.printStackTrace();
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Data not available!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "1");

                }
            }

            @Override
            public void onError(JSONObject result) {
                sendLogToBackend(result.toString());
                hud.dismiss();
              /*  AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Data not available!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
*/
                showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "1");

            }
        });

    }

    private void GetCurrentMedpassDetailsByPatientID(String externalPatientId) {
        Log.e("TAG", "GetCurrentMedpassDetailsByPatientID: " + externalPatientId);
        hud.show();
        date = parseDateToddMMyyyy(date);
        NetworkUtility.getJSONObjectRequest(API.GetCurrentMedpassDetailsByPatientID + "?FacilityID=" + MyApplication.getPrefranceDataInt("ExFacilityID") + "&PatientID=" + externalPatientId + "&Dosedate=" + date + "&DoseTime=" + time, new JSONObject(), API.GetCurrentMedpassDetailsByPatientID, new VolleyCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    hud.dismiss();
                   /* TextView textView = new TextView(context);
                    textView.setText(context.getResources().getString(R.string.medpass_alert));
                    textView.setPadding(20, 30, 20, 30);
                    textView.setTextSize(20F);
                    textView.setBackgroundResource(R.color.colorPrimary);
                    textView.setTextColor(Color.WHITE);*/
                    if (result != null) {
                        Type listType = new TypeToken<List<MedMultiPrescriptionListModel.DataBean.ObjdetailsBean>>() {
                        }.getType();
                        modelMessPrescriptionList.clear();
                        if (medPassPrescriptionAdapter != null) {
                            medPassPrescriptionAdapter.removeAllData();
                            medPassPrescriptionAdapter.notifyDataSetChanged();
                            medPassPrescriptionAdapter=null;

                        }
                        modelMessPrescriptionList.addAll(MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("objdetails").toString(), listType));

                        Log.e("MissdoseMsg:", "onSuccess: " + result.getJSONObject("Data").getString("MissdoseMsg") + "  Size--->" + modelMessPrescriptionList.size() + "----->" + result.getJSONObject("Data").getString("Msg"));

                        String missDoseMsg = result.getJSONObject("Data").getString("MissdoseMsg");
                        String msg = result.getJSONObject("Data").getString("Msg");
                        String futureMedpassMsg = result.getJSONObject("Data").getString("FetureMedpassMsg");
                        String isLOAType = result.getJSONObject("Data").getString("IsLOAType");
                        boolean isTaken = result.getJSONObject("Data").getBoolean("IsTaken");
                        boolean IsLOA = result.getJSONObject("Data").getBoolean("IsLOA");
                        boolean islate = result.getJSONObject("Data").getBoolean("IsLate");
                        boolean isErly = result.getJSONObject("Data").getBoolean("IsErly");
                        if (islate | isErly) {
                            mBinding.edtConsultantname.setVisibility(View.VISIBLE);
                        }
                        if (modelMessPrescriptionList.size() > 0 && !missDoseMsg.isEmpty() && !missDoseMsg.equals("null")) {
                            sendLogToBackend(result.getJSONObject("Data").getString("MissdoseMsg"));

                            /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(result.getJSONObject("Data").getString("MissdoseMsg"))
                                    .setCancelable(false)
                                    .setCustomTitle(textView)
                                    .setPositiveButton("continue", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                            if (multiPack != null && !multiPack.isEmpty()) {
                                                openAlertDialogMultiPack();
                                            }
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();*/
                            showAlertDialog(result.getJSONObject("Data").getString("MissdoseMsg"), context.getResources().getString(R.string.medpass_alert), "cancel", "continue", "4");

                            if (modelMessPrescriptionList.size() > 0) {
                                IsMissedDose =  modelMessPrescriptionList.get(0).isIsMissedDose();
                                medPassPrescriptionAdapter = new MedMultiPassPrescriptionAdapter(activity, context, modelMessPrescriptionList, Is_PrescriptionImg, isNote, multiPack);
                                mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mBinding.rvMessMultiPrescriptionList.setAdapter(medPassPrescriptionAdapter);

                                mBinding.search.setVisibility(View.VISIBLE);
                                mBinding.llSearch.setVisibility(View.VISIBLE);
                                mBinding.search.clearFocus();
                                /*if (multiPack != null && !multiPack.isEmpty() && !multiPack.equalsIgnoreCase("1/1")) {
                                    openAlertDialogMultiPack();
                                }*/
                                if (mBinding.swipeRefresh.isRefreshing()) {
                                    mBinding.swipeRefresh.setRefreshing(false);
                                }
                                Log.d("TAG", "modelMessPrescriptionList: " + modelMessPrescriptionList.size());

                            }

                        } else {
                            if (modelMessPrescriptionList.size() <= 0 && isTaken) {
                                sendLogToBackend(result.getJSONObject("Data").getString("Msg"));
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(result.getJSONObject("Data").getString("Msg"))
                                        .setCancelable(false)
                                        .setCustomTitle(textView)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                                dialog.dismiss();
                                                if (isListEmpty) {
                                                    Intent intent = new Intent(context, PatientListActivityNew.class);
                                                    intent.putExtra("medPass", true);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    finish();
                                                }
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();*/
                                showAlertDialog(result.getJSONObject("Data").getString("Msg"), context.getResources().getString(R.string.medpass_alert), "", "OK", "2");
                                Log.e("TAG", "onSuccess:Msg >>>>>>>>>>>>>>>> "+ result.getJSONObject("Data").getString("Msg"));

                            } else if (modelMessPrescriptionList.size() <= 0 && IsLOA) {
                                sendLogToBackend(isLOAType);
                              /*  AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(isLOAType)
                                        .setCustomTitle(textView)
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                                dialog.dismiss();
                                                if (isListEmpty) {
                                                    Intent intent = new Intent(context, PatientListActivityNew.class);
                                                    intent.putExtra("medPass", true);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    finish();
                                                }
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();*/
                                showAlertDialog(isLOAType, context.getResources().getString(R.string.medpass_alert), "", "OK", "2");

                            } else if (modelMessPrescriptionList.size() <= 0 && !missDoseMsg.isEmpty() && !missDoseMsg.equals("null")) {
                                Log.e("print", "3333333: ");

                                sendLogToBackend(result.getJSONObject("Data").getString("MissdoseMsg"));
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(result.getJSONObject("Data").getString("MissdoseMsg"))
                                        .setCancelable(false)
                                        .setCustomTitle(textView)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                                dialog.dismiss();
                                                if (isListEmpty) {
                                                    Intent intent = new Intent(context, PatientListActivityNew.class);
                                                    intent.putExtra("medPass", true);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    finish();
                                                }
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();*/
                                showAlertDialog(result.getJSONObject("Data").getString("MissdoseMsg"), context.getResources().getString(R.string.medpass_alert), "", "OK", "2");
                                Log.e("TAG", "onSuccess:MissdoseMsg >>>>>>>>>>>>>>>> "+ result.getJSONObject("Data").getString("MissdoseMsg"));
                            } else if (modelMessPrescriptionList.size() <= 0 && !futureMedpassMsg.isEmpty() && !futureMedpassMsg.equals("null")) {
                                Log.e("print", "3333333: ");
                                sendLogToBackend(futureMedpassMsg);
                              /*  AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(futureMedpassMsg)
                                        .setCustomTitle(textView)
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do things
                                                dialog.dismiss();
                                                if (isListEmpty) {
                                                    Intent intent = new Intent(context, PatientListActivityNew.class);
                                                    intent.putExtra("medPass", true);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    finish();
                                                }
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();*/
                                showAlertDialog(futureMedpassMsg, context.getResources().getString(R.string.medpass_alert), "", "OK", "2");
                                Log.e("TAG", "onSuccess:futureMedpassMsg >>>>>>>>>>>>>>>> "+ futureMedpassMsg);

                            } else if (modelMessPrescriptionList.size() > 0) {
                                isNote = false;
                                medPassPrescriptionAdapter = new MedMultiPassPrescriptionAdapter(activity, context, modelMessPrescriptionList, Is_PrescriptionImg, isNote, multiPack);
                                mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mBinding.rvMessMultiPrescriptionList.setAdapter(medPassPrescriptionAdapter);
                                mBinding.search.setVisibility(View.VISIBLE);
                                mBinding.llSearch.setVisibility(View.VISIBLE);
                                mBinding.search.clearFocus();
                                if (multiPack != null && !multiPack.isEmpty() && !multiPack.equalsIgnoreCase("1/1")) {
                                    openAlertDialogMultiPack();
                                }
                                if (mBinding.swipeRefresh.isRefreshing()) {
                                    mBinding.swipeRefresh.setRefreshing(false);
                                }
                                Log.d("TAG", "modelMessPrescriptionList: " + modelMessPrescriptionList.size());

                            } else {
                                if (!msg.isEmpty() && !msg.equals("null")) {
                                    sendLogToBackend(result.getJSONObject("Data").getString("Msg"));
                                /*    builder.setMessage(result.getJSONObject("Data").getString("Msg"))
                                            .setCustomTitle(textView)
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                    if (isListEmpty) {
                                                        Intent intent = new Intent(context, PatientListActivityNew.class);
                                                        intent.putExtra("medPass", true);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    } else {
                                                        finish();
                                                    }
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
*/
                                    showAlertDialog(result.getJSONObject("Data").getString("Msg"),
                                            context.getResources().getString(R.string.medpass_alert), "", "OK", "2");

                                } else {
                                    sendLogToBackend(context.getResources().getString(R.string.no_medication_available_at_this_time));
                                   /* builder.setMessage("No Medication available at this time")
                                            .setCustomTitle(textView)
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //do things
                                                    dialog.dismiss();
                                                    if (isListEmpty) {
                                                        Intent intent = new Intent(MedMultiPrescriptionTabActivity.this, PatientListActivityNew.class);
                                                        intent.putExtra("medPass", true);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                    } else {
                                                        finish();
                                                    }
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();*/
                                    showAlertDialog(context.getResources().getString(R.string.no_medication_available_at_this_time),
                                            context.getResources().getString(R.string.medpass_alert), "", "OK", "2");
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    sendLogToBackend(ex.getMessage());
                    Log.e("Exception:", "onSuccess: " + ex.getMessage());
                    ex.printStackTrace();
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(MedMultiPrescriptionTabActivity.this);
                    builder.setMessage("Data not available!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/

                    showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "1");


                }
            }

            @Override
            public void onError(JSONObject result) {
                sendLogToBackend(result.toString());
                hud.dismiss();
              /*  AlertDialog.Builder builder = new AlertDialog.Builder(MedMultiPrescriptionTabActivity.this);
                builder.setMessage("Data not available!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/

                showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "1");


            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void openAlertDialogMultiPack() {
        /*DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_multipack, viewGroup, false);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialogView);
        builder.setPositiveButton("Ok", dialogClickListener);
        builder.show();*/


        showAlertDialog(context.getResources().getString(R.string.multipacks_message), "", "", "OK", "5");
    }

    private void initRecyclerView() {
        mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setBarcode(String msg) {
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        integrator.setPrompt(msg);
        integrator.setBeepEnabled(true);

        integrator.setOrientationLocked(false);

        integrator.initiateScan();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop", "stop");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        broadcastIntent();
        /*if (Utils.checkInternetConnection(MedMultiPrescriptionTabActivity.this)) {
            if (Application.getPrefranceDataBoolean("isDesc")) {
                GetCurrentMedpassDetailsByPatientIDOnResume(id);
                Application.setPreferencesBoolean("isDesc", false);
            } else {
                GetCurrentMedpassDetailsByPatientID(id);
            }
        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

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
        } else if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Log.e("Android 11 >>", "" + getCacheImagePath(fileName) + " >> " + fileName);
                try {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), getCacheImagePath(fileName));
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

        } else if (requestCode == SELECT_FILE) {
            onSelectFromGalleryResult(intent);
        } else if (requestCode == 103) {
            GetCurrentMedpassDetailsByPatientID(id);
        } else {
            try {
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                if (result != null) {
                    if (result.getContents() == null) {
                        barcodeOpen = 0;
                    } else {
                        Log.e("result", result.getContents() + "\n" + result.getBarcodeImagePath() + "\n" + result.getFormatName() + "\n" + result.getErrorCorrectionLevel());
                        isOpen = 1;
                        Log.e("TAG", "result.getContents(): " + result.getContents());
                        Log.e("TAG", "contains #: " + result.getContents().contains("#"));
                        List<String> elephantList = Arrays.asList(result.getContents().split(","));
                        Log.e("TAG", "item1 " + elephantList.get(0));
                        Log.e("TAG", "item2: " + elephantList.get(1));
                        Log.e("TAG", "item3: " + elephantList.get(2));
                        Log.e("TAG", "final item: " + elephantList.get(0).replace("#", ""));
                        if (SCANNED_BARCODE_ID == 0) {
                            RxNumber = result.getContents();
                            Log.e("Rx Number", result.getContents());
                            getPrescriptionDetails(RxNumber);
                        } else {
                            RxNumber = result.getContents();
                            getPrescriptionDetails(RxNumber);
                            Log.e("Rx Number1", result.getContents());

                        }

                    }

                } else {

                    super.onActivityResult(requestCode, resultCode, intent);


                }

            } catch (Exception ex) {
                ex.printStackTrace();
                super.onActivityResult(requestCode, resultCode, intent);
            }

        }

    }

    private String getFormat(String date) throws ParseException {
        if (date != null && !date.isEmpty()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(date);
            Log.d("Date", String.valueOf(d));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String monthName = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
            return monthName;
        } else {
            return "";
        }
    }

    private void getPrescriptionDetails(String rx) {
        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.GetMedPassDrugDetailsByRxnumber + "?Rxnumber=" + rx + "&patient_id=" + PatientId, new JSONObject(), API.GetMedPassDrugDetailsByRxnumber, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelMessPrescription>>() {
                        }.getType();
                        if (result.getJSONArray("Data").length() <= 0) {
                            /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Data not available!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();*/
                            Log.e("Here", "Here");
                            showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "1");
                        } else {
                            barcodeOpen = 1;
                            isOpen = 0;
                            modelMessPrescriptionList1 = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                            Intent intent = new Intent(context, MedPrescriptionDetailsActivity.class);
                            intent.putExtra("drug", modelMessPrescriptionList1.get(0).getDrug());
                            intent.putExtra("rxNumber", modelMessPrescriptionList1.get(0).getRx_number());
                            intent.putExtra("sig_detail", modelMessPrescriptionList1.get(0).getSig_code());
                            intent.putExtra("dose_time", modelMessPrescriptionList1.get(0).getDose_time());
                            intent.putExtra("dose_Qty", modelMessPrescriptionList1.get(0).getDose_Qty());
                            intent.putExtra("tran_id", modelMessPrescriptionList1.get(0).getTran_id());
                            intent.putExtra("facility_id", modelMessPrescriptionList1.get(0).getFacility_id());
                            intent.putExtra("patientId", modelMessPrescriptionList1.get(0).getPatient_id());
                            intent.putExtra("ndc", modelMessPrescriptionList1.get(0).getNdc());
                            intent.putExtra("IsLate", modelMessPrescriptionList1.get(0).isLate());
                            intent.putExtra("IsErly", modelMessPrescriptionList1.get(0).isErly());
                            intent.putExtra("fName", patient_fName);
                            intent.putExtra("lname", patient_lName);
                            intent.putExtra("name", modelMessPrescriptionList1.get(0).getPatient_last() + " " + modelMessPrescriptionList.get(0).getPatient_first());
                            Log.e("There", "There");

                            try {
                                intent.putExtra("BirthDate", getFormat(modelMessPrescriptionList1.get(0).getDOB()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra("gender", Gender);
                            startActivityForResult(intent, 103);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Data not available!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "1");
                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Data not available!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "1");
            }
        });
    }

    public void openBarcode() {
        if (barcodeOpen == 1) {
            Log.e("barcodeOpen", String.valueOf(barcodeOpen));
            if (modelMessPrescriptionList.size() > 0) {
                Log.e("Scab", "Savngn");
                setBarcode("Scan QR Code");
            }
        }
    }

    private void getUserImage(int patientId) {
        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.PatientImagePath + "?patient_id=" + patientId, new JSONObject(), API.PatientImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    Picasso.get().load(result.getString("Data"))
                            .placeholder(R.drawable.ic_user_placeholder)
                            .error(R.drawable.ic_user_placeholder)
                            .into(mBinding.patientImage);


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
        if (check.equalsIgnoreCase("5")) {
            mDialogShowAlertBinding.imgalert.setVisibility(View.VISIBLE);
        }
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
                if (isListEmpty) {
                    Intent intent = new Intent(context, PatientListActivityNew.class);
                    intent.putExtra("medPass", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            } else if (check.equalsIgnoreCase("3")) {
                MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
                Intent intent = new Intent(context, PatientListActivityNew.class);
                intent.putExtra("medPass", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else if (check.equalsIgnoreCase("4")) {
                if (multiPack != null && !multiPack.isEmpty() && !multiPack.equalsIgnoreCase("1/1")) {
                    openAlertDialogMultiPack();
                }
            } else if (check.equalsIgnoreCase("5")) {
                //do not use check - 5, only use for multi packs
            }else if (check.equalsIgnoreCase("6")) {
               /* MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
                Intent intent = new Intent(context, MedPrescriptionTabActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
        mDialogShowAlertBinding.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            if (check.equalsIgnoreCase("1")) {
                finish();
            } else if (check.equalsIgnoreCase("2")) {

            } else if (check.equalsIgnoreCase("3")) {

            } else if (check.equalsIgnoreCase("4")) {
                finish();
            } else if (check.equalsIgnoreCase("5")) {

            }
        });
        dialogSHowAlert.show();

       /* TextView textView = new TextView(context);
        textView.setText(title);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);

        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        Log.e("TAG", "showAlertDialog: >>>>>" + check, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setMessage(message);
        if (!TextUtils.isEmpty(title)) {
            builder.setCustomTitle(textView);
        }
        builder.setCancelable(false);
        if (!TextUtils.isEmpty(negativeBtn)) {
            builder.setNegativeButton(negativeBtn, (dialog, id) -> {
                dialog.dismiss();
                if (check.equalsIgnoreCase("1")) {
                    finish();
                } else if (check.equalsIgnoreCase("2")) {

                } else if (check.equalsIgnoreCase("3")) {

                } else if (check.equalsIgnoreCase("4")) {
                    finish();
                }
            });
        }
        builder.setPositiveButton(positiveBtn, (dialog, id) -> {
            dialog.dismiss();
            if (check.equalsIgnoreCase("1")) {
                dialog.dismiss();
            } else if (check.equalsIgnoreCase("2")) {
                dialog.dismiss();
                if (isListEmpty) {
                    Intent intent = new Intent(context, PatientListActivityNew.class);
                    intent.putExtra("medPass", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            } else if (check.equalsIgnoreCase("3")) {
                dialog.dismiss();
                if (isListEmpty) {
                    Intent intent = new Intent(context, PatientListActivityNew.class);
                    intent.putExtra("medPass", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            } else if (check.equalsIgnoreCase("4")) {
                dialog.dismiss();
                if (multiPack != null && !multiPack.isEmpty()) {
                    openAlertDialogMultiPack();
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();*/
    }

    private void InsertSometingwantErrorLog(String message) {
//        hud.show();
        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("UserID", MyApplication.getPrefranceData("UserID"));
            mainObj.put("DateAndTime", getDateTime());
            mainObj.put("PatientID", PatientId);
            mainObj.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
            mainObj.put("DivceName", Build.MANUFACTURER + " " + Build.DEVICE);
            mainObj.put("ModelNo", Build.MODEL);
            mainObj.put("DeviceVersion", versionRelease);
            mainObj.put("Message", message);
            mainObj.put("ScaningValue", QRcodeformat);
            mainObj.put("Appversion", MyApplication.getPrefranceData("Version"));//
            mainObj.put("DoseDate", date);
            mainObj.put("DoseTime", time);


            Log.e("TAG", "sendLogToBackend: " + versionRelease + MyApplication.getPrefranceData("UserID") + Build.MANUFACTURER + " " + Build.DEVICE + android.os.Build.MODEL + getDateTime() + message);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        NetworkUtility.makeJSONObjectRequest(API.InsertSometingwantErrorLog, mainObj, API.InsertSometingwantErrorLog, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
//                hud.dismiss();
                try {
                    if (result != null) {
                        Log.e("TAG", "InsertSometingwant: " + result.get("Data"));
                    }

                } catch (Exception ex) {
                    dismissProgressDialog();
                    ex.printStackTrace();
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something Went Wrong!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "1");

                }
            }

            @Override
            public void onError(JSONObject result) {
//                hud.dismiss();
               /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Something Went Wrong!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "1");

            }
        });
    }

}
