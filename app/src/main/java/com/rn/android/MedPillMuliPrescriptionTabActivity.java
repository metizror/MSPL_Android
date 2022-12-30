package com.rn.android;

import static androidx.core.content.FileProvider.getUriForFile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.text.Html;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.ActivityMedPillMuliPrescriptionTabBinding;
import com.metiz.pelconnect.databinding.DialogDcWithCheckboxBinding;
import com.metiz.pelconnect.databinding.DialogShowAlertBinding;
import com.rn.android.Adapter.MedPillMultiPassPrescriptionAdapter;
import com.rn.android.base.MediaSelectionDialog;

import com.rn.android.dialog.AlloyHospitalDialogNew;
import com.rn.android.listeners.PickerOptionListener;
import com.rn.android.model.AlloyHospital;
import com.rn.android.model.InsertFillPackhistoryModel;
import com.rn.android.model.MedPillMultiMedpassModel;
import com.rn.android.model.ModelMessPrescription;
import com.rn.android.model.MultiPackModel;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.retrofit.ApiClient;
import com.rn.android.retrofit.ApiInterface;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedPillMuliPrescriptionTabActivity extends BaseActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private static int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public int barcodeOpen = 0;
    public int isOpen = 0;
    String[] PERMISSIONS = {//
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            //  Manifest.permission.ACCESS_MEDIA_LOCATION,
    };
    int PERMISSION_ALL = 7;
    /*@BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_birthdate)
    TextView txtBirthdate;
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.rv_mess_multi_prescription_list)
    RecyclerView rvMessPrescriptionList;
    @BindView(R.id.verified_order)
    CheckBox verified_order;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.edt_notes)
    TextView edt_notes;
    @BindView(R.id.patient_image)
    CircleImageView patientImage;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.btn_taking)
    AppCompatButton btnTaking;
    @BindView(R.id.btn_refused)
    TextView btn_refused;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    //  ProgressDialog progressDialog;
    @BindView(R.id.ll_dcimg)
    LinearLayout ll_dcimg;
    @BindView(R.id.img_promotional_item)
    ImageView imgPromotionalItem;
    @BindView(R.id.dcimg_promotional_item)
    ImageView dcImgPromotionalItem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;*/
    String versionRelease = Build.VERSION.RELEASE;
    List<MedPillMultiMedpassModel.DataBean.ObjdetailsBean> modelMessPrescriptionList = new ArrayList<>();
    List<ModelMessPrescription> modelMessPrescriptionList1 = new ArrayList<>();
    MedPillMultiPassPrescriptionAdapter medPassPrescriptionAdapter;
    List<AlloyHospital.LOAHospital> loaHospitalList = new ArrayList<>();
    int PatientId = 0;
    int objectListSize = 0;
    String pillId;
    String date;
    String MedpassCountMsg = "";
    String time;
    String RxNumber = "";
    //AlertDialog alert;
    String fileName;
    String disContinueFileName;
    String patientName = "";
    String patient_fName = "";
    String patient_lName = "";
    String Gender = "";
    String dcString;
    String multiPack;
    String multiPack1;
    String multiPack2;
    String Gender_full = "";
    String QRcodeformat = "";
    String BirthDate = "";
    boolean isListEmpty;
    boolean isNote = true;
    boolean onCreateFill = false;
    boolean isDiscontinue = false;
    boolean normalImageClick = false;
    boolean dcImageClick = false;
    boolean discardedDialogChecked = false;
    boolean isShowDialog = false;
    boolean isChecked = false;
    boolean Is_PrescriptionImg;
    boolean isMissDose;
    Bitmap GalleryBm;
    ApiInterface apiService;
    //AlertDialog alertDialog = null;
    private BroadcastReceiver MyReceiver = null;
    private int SCANNED_BARCODE_ID = 0;
    private int missDoseCount = 0;
    private String base64 = null;
    private String DisContinuedBase64 = null;
    private Uri DisContinuedBaseURI, MedImageURI;
    private KProgressHUD hud;
    private ActivityMedPillMuliPrescriptionTabBinding mBinding;

    private DialogShowAlertBinding mDialogShowAlertBinding;
    private DialogDcWithCheckboxBinding mDialogDcWithCheckboxBinding;
    private Dialog dialogSHowAlert;
    private Dialog dialogDCWithCheckBox;

    public static void clearCache(Context context) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (path.exists() && path.isDirectory()) {
            for (File child : path.listFiles()) {
                child.delete();
            }
        }
    }

    public static boolean checkAndRequestPermissions(final Activity context) {
        int ExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(activity, R.layout.activity_med_pill_muli_prescription_tab);
        ButterKnife.bind(this);
        MyReceiver = new MyReceiver();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //setTitle("Prescription");
        initProgress();
        initRecyclerView();
        Log.e("isNote OnCreate:", "" + isNote);
        mBinding.btnTaking.setEnabled(false);
        if (getIntent() != null) {
            pillId = getIntent().getStringExtra("pillId");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            Log.e("TAG", "onCreate:time " + time);
            PatientId = getIntent().getIntExtra("PatientId", 0);
            patientName = getIntent().getStringExtra("name");
            patient_fName = getIntent().getStringExtra("fname");
            patient_lName = getIntent().getStringExtra("lname");
            QRcodeformat = getIntent().getStringExtra("QRcodeformat");
            Gender = getIntent().getStringExtra("gender");
            multiPack = getIntent().getStringExtra("multiPack");
            Log.e("TAG", "multiPack::::: ===> " + multiPack);
            Gender_full = getIntent().getStringExtra("gender_full");
            BirthDate = getIntent().getStringExtra("BirthDate");
            isMissDose = getIntent().getBooleanExtra("isMissDose", false);
            missDoseCount = getIntent().getIntExtra("missDoseCount", 0);
            Is_PrescriptionImg = getIntent().getBooleanExtra("Is_PrescriptionImg", false);
            isListEmpty = getIntent().getBooleanExtra("isListEmpty", false);
        }
        Log.e("TAG", "PatientId: " + PatientId);

        if (multiPack != null) {
            String[] separated = multiPack.split("/");
            multiPack1 = separated[0];
            multiPack2 = separated[1];
        }

        Log.e("TAG", "multiPrescription: ");
        onCreateFill = true;
        GetCurrentMedpassDetailsByFillid(pillId);

        mBinding.txtName.setText(patientName);
        mBinding.txtGender.setText(Gender);
        mBinding.txtBirthdate.setText(BirthDate);
        getUserImage(PatientId);
        mBinding.swipeRefresh.setOnRefreshListener(() -> GetCurrentMedpassDetailsByFillid(pillId));

//        String currentDateTimeString = java.text.DateFormat.getDateInstance().format("YYYY-MM-DD");
//        Log.e("TAG", "onCreate:Date " + currentDateTimeString);
        mBinding.verifiedOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mBinding.btnTaking.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_bg));
                    mBinding.btnTaking.setTextColor(getResources().getColor(R.color.white));
                    mBinding.btnTaking.setEnabled(true);

                    mBinding.btnRefused.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_gry));
                    mBinding.btnRefused.setTextColor(getResources().getColor(R.color.white));
                    mBinding.btnRefused.setEnabled(false);
                } else {
                    mBinding.btnTaking.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_gry));
                    mBinding.btnTaking.setTextColor(getResources().getColor(R.color.white));
                    mBinding.btnTaking.setEnabled(false);

                    mBinding.btnRefused.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_bg));
                    mBinding.btnRefused.setTextColor(getResources().getColor(R.color.white));
                    mBinding.btnRefused.setEnabled(true);
                }
            }
        });

        mBinding.txtAlloyHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loaHospitalList.size() > 0) {
                    new AlloyHospitalDialogNew(activity, context, loaHospitalList, PatientId, patientName, isMissDose, time).show();
//                    new AlloyHospitalDialog(context, loaHospitalList, PatientId, patientName, this::isDrugShiftedToLoa, time).show();
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
                            ((MedPillMultiPassPrescriptionAdapter) mBinding.rvMessMultiPrescriptionList.getAdapter()).filter(newText);
                        }
                    } else {
                        ((MedPillMultiPassPrescriptionAdapter) mBinding.rvMessMultiPrescriptionList.getAdapter()).filter("");
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
            Log.e("isNote:", "---------------------------------------------------->" + isNote);
            if (isNote) {
                if (!note.isEmpty() && !"".equals(note)) {
                    if (isDiscontinue) {
                        if (DisContinuedBase64 != null && !DisContinuedBase64.isEmpty()) {
                            if (discardedDialogChecked) {
                                hud.show();
                                medpassFormDataImageSave(true);
                            }else{
                                Toast toast = Toast.makeText(context, "Please accept you discarded Discontinued medication.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(context, "Please Take Discontinued med image.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        hud.show();
                        medpassFormDataImageSave(true);
                    }
                } else {
                    Toast toast = Toast.makeText(context, "Please note reason", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                if (isDiscontinue) {
                    if (DisContinuedBase64 != null && !DisContinuedBase64.isEmpty()) {
                        if (discardedDialogChecked) {
                            hud.show();
                            medpassFormDataImageSave(true);
                        }else{
                            Toast toast = Toast.makeText(context, "Please accept you discarded Discontinued medication.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(context, "Please Take Discontinued med image.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    hud.show();
                    medpassFormDataImageSave(true);
                }
            }
        });
        mBinding.btnRefused.setOnClickListener((View view) -> {
            String note = mBinding.edtNotes.getText().toString().trim();
            if (isNote) {
                if (!note.isEmpty() && !"".equals(note)) {
                    if (isDiscontinue) {
                        if (DisContinuedBase64 != null && !DisContinuedBase64.isEmpty()) {
                            if (discardedDialogChecked) {
                                hud.show();
                                medpassFormDataImageSave(false);
                            }else{
                                Toast toast = Toast.makeText(context, "Please accept you discarded Discontinued medication.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(context, "Please Take Discontinued med image.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        medpassFormDataImageSave(false);
                    }
                } else {
                    Toast toast = Toast.makeText(context, "Please note reason", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                if (isDiscontinue) {
                    if (DisContinuedBase64 != null && !DisContinuedBase64.isEmpty()) {
                        if (discardedDialogChecked) {
                            hud.show();
                            medpassFormDataImageSave(false);
                        }else{
                            Toast toast = Toast.makeText(context, "Please accept you discarded Discontinued medication.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(context, "Please Take Discontinued med image.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    medpassFormDataImageSave(false);
                }
            }
        });

        mBinding.llImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalImageClick = true;
                if (hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                } else {
                    dialogForImage();
                }
            }
        });
        mBinding.llDcimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDcDialog();
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

    private void GetCurrentMedpassDetailsByFillidOnResume(String pillId) {
        hud.show();
        date = parseDateToddMMyyyy(date);
        Log.e("GetCurrentMedpass:", "111: " + MyApplication.getPrefranceDataInt("ExFacilityID") + "--pillId" + pillId + "---date->" + date + "---time" + time);
        Call<MedPillMultiMedpassModel> call = apiService.GetCurrentMedpassDetailsByFillid(MyApplication.getPrefranceDataInt("ExFacilityID"), PatientId, pillId, date, time);
        call.enqueue(new Callback<MedPillMultiMedpassModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MedPillMultiMedpassModel> call, @NonNull Response<MedPillMultiMedpassModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    Log.e("TAG", "onResponse: " + response.body().toString());
                    try {
                        int response_code = response.body().getResponseStatus();
                        Log.e("onResponse111:", "tag111: " + response.body());

                        if (response_code == 1) {

                            assert response.body() != null;
                            modelMessPrescriptionList = response.body().getData().getObjdetails();
                            if (modelMessPrescriptionList.size() > 0) {
                                medPassPrescriptionAdapter = new MedPillMultiPassPrescriptionAdapter(activity, context, modelMessPrescriptionList, Is_PrescriptionImg, isNote, missDoseCount, isMissDose, isListEmpty, multiPack, pillId, QRcodeformat);
                                mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mBinding.rvMessMultiPrescriptionList.setAdapter(medPassPrescriptionAdapter);
                                mBinding.search.setVisibility(View.VISIBLE);
                                mBinding.search.clearFocus();
                                mBinding.swipeRefresh.setRefreshing(false);
                            }

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MedPillMultiMedpassModel> call, Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void openAlertDialogMultiPack() {
       /* DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        TextView textView = new TextView(context);
        textView.setText("Medpass Alert");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_multipack, viewGroup, false);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(dialogView);
        builder.setPositiveButton("Ok", dialogClickListener);
//        builder.show();*/
//        private void getUserImage ( int patientId){
//            //showProgressDialog(MedPrescriptionTabActivity.this);
//            hud.show();

        Call<MultiPackModel> call = apiService.GetMultiPackCountData(MyApplication.getPrefranceDataInt("ExFacilityID"), PatientId, pillId);
        Log.e("TAG", "openAlertDialogMultiPack: Call"+call);
        call.enqueue(new Callback<MultiPackModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MultiPackModel> call, @NonNull Response<MultiPackModel> response) {
                hud.dismiss();
                if (response.body() != null) {
                    if(response.body().getResponseStatus()==1) {
                        MedpassCountMsg = response.body().getData().getPendingPackMsg();
                        Log.e("TAG", "onResponse: >>>>>>>>>" + response.body().getData().getPendingPackMsg());

                        if (!MedpassCountMsg.equalsIgnoreCase("")) {
                            showAlertDialog(MedpassCountMsg, "Multipacks alert", "", "OK", "5");
                        } else {
                            showAlertDialog(context.getResources().getString(R.string.multipacks_message), "Multipacks alert", "", "OK", "5");
                        }
                    }else {
                        InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MultiPackModel> call, Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
                if (isInternetAvailable(context)) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something Went Wrong...!!!")
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
                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "3");
                    InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));
                }
            }
        });

    }

    private void GetCurrentMedpassDetailsByFillid(String pillId) {
        hud.show();
        date = parseDateToddMMyyyy(date);
        Log.e("GetCurrentMedpass:", "111: " + MyApplication.getPrefranceDataInt("ExFacilityID") + "--pillId" + pillId + "---date->" + date + "---time" + time);
        Call<MedPillMultiMedpassModel> call = apiService.GetCurrentMedpassDetailsByFillid(MyApplication.getPrefranceDataInt("ExFacilityID"), PatientId, pillId, date, time);
        call.enqueue(new Callback<MedPillMultiMedpassModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MedPillMultiMedpassModel> call, @NonNull Response<MedPillMultiMedpassModel> response) {
                hud.dismiss();

//                Log.e("TAG", "onResponse: " + response.body().toString());
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    try {
                        int response_code = response.body().getResponseStatus();
                        Log.e("onResponse111:", "tag111: " + response.body());

                        /*if (alert != null) {
                            if (alert.isShowing()) {
                                alert.dismiss();
                            }
                        }*/
//                        alert = null;
                        if (response_code == 1) {
                            if (response.body().getData().getObjdetailsInactive().size() > 0) {
                                StringBuilder msg = new StringBuilder();
                                isDiscontinue = true;
                                mBinding.llDcimg.setVisibility(View.VISIBLE);
                                for (int i = 0; i < response.body().getData().getObjdetailsInactive().size(); i++) {
                                    if (i == 0) {
                                        msg.append(response.body().getData().getObjdetailsInactive().get(i).getDrug());
                                    } else {
                                        msg.append(", ").append(response.body().getData().getObjdetailsInactive().get(i).getDrug());
                                    }
                                }


                                dcString = "<b>" + msg + "</b>" + " has been Discontinued";
                                objectListSize = response.body().getData().getObjdetails().size();
                                if (response.body().getData().getObjdetails().size() <= 0) {
                                    isShowDialog = true;
                                    showDcDialog();
                                }


                            }
                            assert response.body() != null;
                            modelMessPrescriptionList = response.body().getData().getObjdetails();
                            String missDoseMsg = response.body().getData().getMissdoseMsg();
                            String msg = (String) response.body().getData().getMsg();
                            String futureMedpassMsg = (String) response.body().getData().getFetureMedpassMsg();
                            String isLOAType = (String) response.body().getData().getIsLOAType();
                            boolean isTaken = response.body().getData().isIsTaken();
                            boolean IsLOA = response.body().getData().isIsLOA();

                            if (response.body().getData().getObjdetails().size() > 0 && missDoseMsg != null && !missDoseMsg.isEmpty()) {
                                sendLogToBackend(missDoseMsg);
                                if (modelMessPrescriptionList.size() <= 0) {
                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage(missDoseMsg)
                                            .setCustomTitle(textView)
                                            .setCancelable(false)
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    finish();
                                                }
                                            })
                                            .setPositiveButton("Ok", (dialog, id) -> {
                                                //do things
                                                dialog.dismiss();
                                                if (modelMessPrescriptionList.size() <= 0) {
                                                    if (alert.isShowing())
                                                        alert.dismiss();
                                                    Intent intent = new Intent(context, PatientListActivityNew.class);
                                                    intent.putExtra("medPass", true);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    if (isDiscontinue) {
                                                        dialog.dismiss();
                                                        isShowDialog = true;
                                                        showDcDialog();
                                                    } else {
                                                        dialog.dismiss();
                                                    }
                                                }
                                                // finish();
                                            });
                                    alert = builder.create();*/
                                    showAlertDialog(missDoseMsg, context.getResources().getString(R.string.medpass_alert), "Cancel", "OK", "1");
                                } else {
                                    isMissDose = false;
                                    missDoseCount = 0;
                                    showAlertDialog(missDoseMsg, context.getResources().getString(R.string.medpass_alert), "Cancel", "Continue", "2");
                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage(missDoseMsg)
                                            .setCustomTitle(textView)
                                            .setCancelable(false)
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    finish();
                                                }
                                            })
                                            .setPositiveButton("continue", (dialog, id) -> {
                                                //do things
                                                dialog.dismiss();
                                                if (modelMessPrescriptionList.size() <= 0) {
                                                    if (alert.isShowing())
                                                        alert.dismiss();
                                                    Intent intent = new Intent(context, PatientListActivityNew.class);
                                                    intent.putExtra("medPass", true);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                } else {
                                                    if (isDiscontinue) {
                                                        dialog.dismiss();
                                                        isShowDialog = true;
                                                        showDcDialog();
                                                    } else {
                                                        dialog.dismiss();
                                                    }

                                                }
                                                // finish();
                                            });
                                    alert = builder.create();*/
                                }
                                /*if (alert != null) {
                                    if (alert.isShowing()) {
                                        alert.dismiss();
                                    }
                                }
                                alert.show();*/
                                boolean islate = response.body().getData().isLate();
                                boolean isErly = response.body().getData().isErly();
                                if (islate | isErly) {
                                    mBinding.edtConsultantname.setVisibility(View.VISIBLE);
                                }
                                medPassPrescriptionAdapter = new MedPillMultiPassPrescriptionAdapter(activity, context, modelMessPrescriptionList, Is_PrescriptionImg, isNote, missDoseCount, isMissDose, isListEmpty, multiPack, pillId, QRcodeformat);
                                mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mBinding.rvMessMultiPrescriptionList.setAdapter(medPassPrescriptionAdapter);
                                mBinding.search.setVisibility(View.VISIBLE);
                                mBinding.search.clearFocus();
                                mBinding.swipeRefresh.setRefreshing(false);
                            } else {
                                if (modelMessPrescriptionList.size() <= 0 && isTaken) {
                                    if (missDoseMsg != null && !missDoseMsg.isEmpty()) {
                                        sendLogToBackend(missDoseMsg);
//                                        showAlertDialog(missDoseMsg, textView, "", "Ok", "3");
                                        showAlertDialog(missDoseMsg, context.getResources().getString(R.string.medpass_alert), "", "ok", "3");
                                        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage(missDoseMsg)
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
                                        alert = builder.create();*/
                                    } else {
                                        sendLogToBackend(msg);
//                                        showAlertDialog(msg, textView, "", "Ok", "4");
                                        showAlertDialog(msg, context.getResources().getString(R.string.medpass_alert), "", "Ok", "3");
                                        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage(msg)
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
                                        alert = builder.create();*/
                                    }
                                    /*if (alert != null) {
                                        if (alert.isShowing()) {
                                            alert.dismiss();
                                        }
                                    }
                                    alert.show();*/

                                } else if (modelMessPrescriptionList.size() <= 0 && IsLOA) {
                                    sendLogToBackend(isLOAType);
//                                    showAlertDialog(isLOAType, textView, "", "Ok", "5");
                                    showAlertDialog(isLOAType, context.getResources().getString(R.string.medpass_alert), "", "Ok", "3");
                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                                    alert = builder.create();
                                    if (alert != null) {
                                        if (alert.isShowing()) {
                                            alert.dismiss();
                                        }
                                    }
                                    alert.show();*/
                                } else if (modelMessPrescriptionList.size() <= 0 && missDoseMsg != null && !missDoseMsg.isEmpty()) {
                                    sendLogToBackend(missDoseMsg);
//                                    showAlertDialog(missDoseMsg, textView, "", "Ok", "6");
                                    showAlertDialog(missDoseMsg, context.getResources().getString(R.string.medpass_alert), "", "Ok", "3");
                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage(missDoseMsg)
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
                                    alert = builder.create();
                                    if (alert != null) {
                                        if (alert.isShowing()) {
                                            alert.dismiss();
                                        }
                                    }
                                    alert.show();*/
                                } else if (modelMessPrescriptionList.size() <= 0 && futureMedpassMsg != null && !futureMedpassMsg.isEmpty()) {
                                    sendLogToBackend(futureMedpassMsg);
//                                    showAlertDialog(futureMedpassMsg, textView, "", "Ok", "7");
                                    showAlertDialog(futureMedpassMsg, context.getResources().getString(R.string.medpass_alert), "", "Ok", "3");
                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                                    alert = builder.create();
                                    if (alert != null) {
                                        if (alert.isShowing()) {
                                            alert.dismiss();
                                        }
                                    }
                                    alert.show();*/
                                } else if (modelMessPrescriptionList.size() > 0) {
                                    isNote = false;
                                    if (isDiscontinue && !isShowDialog) {
                                        showDcDialog();
                                    }
                                    if (multiPack != null && !multiPack.isEmpty() && !multiPack.equalsIgnoreCase("1/1")) {
                                        openAlertDialogMultiPack();
                                    }
                                    boolean islate = response.body().getData().isLate();
                                    boolean isErly = response.body().getData().isErly();
                                    if (islate | isErly) {
                                        mBinding.edtConsultantname.setVisibility(View.VISIBLE);
                                    }

                                    Log.e("TAG", "onResponse:QR " + QRcodeformat);
                                    medPassPrescriptionAdapter = new MedPillMultiPassPrescriptionAdapter(activity, context, modelMessPrescriptionList, Is_PrescriptionImg, isNote, missDoseCount, isMissDose, isListEmpty, multiPack, pillId, QRcodeformat);
                                    mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    mBinding.rvMessMultiPrescriptionList.setAdapter(medPassPrescriptionAdapter);
                                    mBinding.search.setVisibility(View.VISIBLE);
                                    mBinding.search.clearFocus();
                                    mBinding.swipeRefresh.setRefreshing(false);
                                    Log.e("TAG", "modelMessPrescriptionList: " + modelMessPrescriptionList.size());

                                } else {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    if (msg != null && !msg.isEmpty()) {
                                        sendLogToBackend(msg);
                                        if (!isDiscontinue) {
//                                            showAlertDialog(msg, textView, "", "Ok", "8");
                                            showAlertDialog(msg, context.getResources().getString(R.string.medpass_alert), "", "Ok", "3");
                                        }
                                        /*builder.setMessage(msg)
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
                                                });*/
                                    } else {
                                        sendLogToBackend(context.getResources().getString(R.string.no_medication_available_at_this_time));
                                        if (!isDiscontinue) {
//                                            showAlertDialog(context.getResources().getString(R.string.no_medication_available_at_this_time), textView, "", "Ok", "9");
                                            showAlertDialog(context.getResources().getString(R.string.no_medication_available_at_this_time),
                                                    context.getResources().getString(R.string.medpass_alert), "", "Ok", "3");
                                        }
                                       /* builder.setMessage("No Medication available at this time")
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
                                                });*/
                                    }
                                    /*if (!isDiscontinue) {
                                        alert = builder.create();
                                        if (alert != null) {
                                            if (alert.isShowing()) {
                                                alert.dismiss();
                                            }
                                        }
                                        alert.show();
                                    }*/
                                }
                            }

                        }else{
                            InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong));
                        }
                    } catch (Exception ex) {
                        Log.e("TAG", "onResponse:da >>>>>>>>" + ex.getMessage(), null);
                        ex.printStackTrace();
                        hud.dismiss();
                        if (isInternetAvailable(context)) {
                            /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Something Went Wrong...!!!")
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
                            showAlertDialog(context.getResources().getString(R.string.QR_code_scanning_error), "", "", "OK", "3");
//                            showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "3");
                            Log.e("TAG", "onFailure1111111:>>>>>> " );

                            InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MedPillMultiMedpassModel> call, Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
                if (isInternetAvailable(context)) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something Went Wrong...!!!")
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
//                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "3");
                    showAlertDialog(context.getResources().getString(R.string.QR_code_scanning_error), "", "", "OK", "3");
                    Log.e("TAG", "onFailure:>>>>>> " );
                    InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));
                }
            }
        });
    }

    /*private void showAlertDialog(String message, TextView textView, String negativeBtn, String positiveBtn, String check) {
     *//*   TextView textView = new TextView(context);
        textView.setText("Medpass Alert");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);*//*

        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        Log.e("TAG", "showAlertDialog: >>>>>" + check, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setMessage(message)
                .setCustomTitle(textView)
                .setCancelable(false)
                .setNegativeButton(negativeBtn, (dialog, id) -> {
                    dialog.dismiss();
                    if (check.equalsIgnoreCase("1")) {
                        finish();
                    } else if (check.equalsIgnoreCase("2")) {
                        finish();
                    } else if (check.equalsIgnoreCase("3")) {

                    } else if (check.equalsIgnoreCase("4")) {

                    } else if (check.equalsIgnoreCase("5")) {

                    } else if (check.equalsIgnoreCase("6")) {

                    } else if (check.equalsIgnoreCase("7")) {

                    } else if (check.equalsIgnoreCase("8")) {

                    } else if (check.equalsIgnoreCase("9")) {

                    }
                })
                .setPositiveButton(positiveBtn, (dialog, id) -> {
                    dialog.dismiss();
                    if (check.equalsIgnoreCase("1")) {
                        if (modelMessPrescriptionList.size() <= 0) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            if (isDiscontinue) {
                                dialog.dismiss();
                                isShowDialog = true;
                                showDcDialog();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    } else if (check.equalsIgnoreCase("2")) {
                        if (modelMessPrescriptionList.size() <= 0) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            if (isDiscontinue) {
                                dialog.dismiss();
                                isShowDialog = true;
                                showDcDialog();
                            } else {
                                if (multiPack != null && !multiPack.isEmpty()) {
                                    openAlertDialogMultiPack();
                                }
                                dialog.dismiss();
                            }
                        }
                    } else if (check.equalsIgnoreCase("3")) {
                        if (isListEmpty) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    } else if (check.equalsIgnoreCase("4")) {
                        if (isListEmpty) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    } else if (check.equalsIgnoreCase("5")) {
                        if (isListEmpty) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    } else if (check.equalsIgnoreCase("6")) {
                        if (isListEmpty) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    } else if (check.equalsIgnoreCase("7")) {
                        if (isListEmpty) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    } else if (check.equalsIgnoreCase("8")) {
                        if (isListEmpty) {
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            finish();
                        }
                    } else if (check.equalsIgnoreCase("9")) {
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
        alertDialog = builder.create();
        alertDialog.show();
    }*/

    private void showDcDialog() {

        dialogDCWithCheckBox = new Dialog(context);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mDialogDcWithCheckboxBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_dc_with_checkbox, null, false);
        dialogDCWithCheckBox.setContentView(mDialogDcWithCheckboxBinding.getRoot());
//                            dialogDiscontinue.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialogDCWithCheckBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogDCWithCheckBox.getWindow().setStatusBarColor(context.getResources().getColor(R.color.white));
        dialogDCWithCheckBox.setCancelable(true);
        dialogDCWithCheckBox.setCanceledOnTouchOutside(false);
        dialogDCWithCheckBox.show();
        if (GalleryBm != null) {
            mDialogDcWithCheckboxBinding.dcimgPromotionalItemDialog.setImageBitmap(GalleryBm);
            mBinding.dcimgPromotionalItem.setImageBitmap(GalleryBm);
        }
        mDialogDcWithCheckboxBinding.tvMessage.setText(Html.fromHtml(dcString));
        mDialogDcWithCheckboxBinding.llDcimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dcImageClick = true;
                if (hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                } else {
                    dialogForImage();
                }
            }
        });
        if (discardedDialogChecked) {
            mDialogDcWithCheckboxBinding.dcCheck.setChecked(true);
        }
        mDialogDcWithCheckboxBinding.dcCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                discardedDialogChecked = isChecked;
            }
        });
        mDialogDcWithCheckboxBinding.okBtn.setOnClickListener(v -> {
            if (objectListSize > 0) {
                if (DisContinuedBase64 != null && !DisContinuedBase64.isEmpty()) {
                    if (discardedDialogChecked) {
                        Log.e("checkBox.isChecked():", "onClick: " + mDialogDcWithCheckboxBinding.dcCheck.isChecked());
                        dialogDCWithCheckBox.dismiss();
                        if (multiPack != null && !multiPack.isEmpty() && !multiPack.equalsIgnoreCase("1/1")) {
                            openAlertDialogMultiPack();
                        }
                    } else {
                        Toast.makeText(context, "Please accept you discarded Discontinued medication.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please Take Discontinued med image.", Toast.LENGTH_SHORT).show();
                }
            } else {
                dialogDCWithCheckBox.dismiss();
                Intent intent = new Intent(context, PatientListActivityNew.class);
                intent.putExtra("medPass", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (alert != null && alert.isShowing())
//            alert.dismiss();
//        alert = null;
    }

    public String parseDateToddMMyyyy(String time) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return formatter.format(Date.parse(time));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

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
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    new MediaSelectionDialog(context, new PickerOptionListener() {
                        @Override
                        public void onTakeCameraSelected() {
                            if (normalImageClick) {
                                fileName = System.currentTimeMillis() + ".jpg";
                            } else {
                                disContinueFileName = System.currentTimeMillis() + ".jpg";
                            }

                            if (checkAndRequestPermissions(activity)) {
                                try {
                                    Intent intent = new Intent(context, Custom_CameraActivity.class);
                                    startActivityForResult(intent, 105);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onChooseGallerySelected() {
                            galleryIntent();
                        }
                    }).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                }).show();

    }

    /*@RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean checkPermissions() {
        if (SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
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
        if (SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Log.e("if >>", "true");

            return true;
        } else {
            requestPermissionForCamera();
            return false;
        }
    }

    // TODO ask for Storage permission
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    // TODO ask for Camera permission
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestPermissionForCamera() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }*/

    private Uri getCacheImagePath(String fileName) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(context, context.getPackageName() + ".provider", image);
    }

    /*@RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("permission >>", "" + requestCode + " >>> " + permissions.length + " >> " + grantResults.length);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                Log.e("123 >>", "External Storage");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
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
                    Intent takePictureIntent;
                    if (dcImageClick) {
                        disContinueFileName = System.currentTimeMillis() + ".jpg";
                        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(disContinueFileName));
                    } else {
                        fileName = System.currentTimeMillis() + ".jpg";
                        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                    }


                    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
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

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                if (dcImageClick) {
                    GalleryBm = bm;
                    DisContinuedBaseURI = data.getData();
                    if (bm != null) {
                        mDialogDcWithCheckboxBinding.dcimgPromotionalItemDialog.setImageBitmap(bm);
                        mBinding.dcimgPromotionalItem.setImageBitmap(bm);
                        try {
                            hud.show();
                            DisContinuedBase64 = Utils.convertImageToBase64(bm);
                            hud.dismiss();
                        } catch (Exception ex) {
                            hud.dismiss();
                        }
                    }
                    dcImageClick = false;
                } else {
                    MedImageURI = data.getData();
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
                    normalImageClick = false;
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
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void sendLogToBackend(String message) {
        hud.show();
        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("UserID", MyApplication.getPrefranceData("UserID"));
            mainObj.put("PatientID", PatientId);
            mainObj.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
            mainObj.put("DateAndTime", getDateTime());
            mainObj.put("DivceName", Build.MANUFACTURER + " " + Build.DEVICE);
            mainObj.put("ModelNo", Build.MODEL);
            mainObj.put("DeviceVersion", versionRelease);
            mainObj.put("ScaningValue", QRcodeformat);
            mainObj.put("Appversion", MyApplication.getPrefranceData("Version"));//
            mainObj.put("DoseDate", date);//
            mainObj.put("DoseTime", time);//

            Log.e("TAG", "sendLogToBackend: " + versionRelease + MyApplication.getPrefranceData("UserID") + Build.MANUFACTURER + " " + Build.DEVICE + android.os.Build.MODEL + getDateTime() + message);


            if (message != null) {
                mainObj.put("Message", message);
            } else {
                mainObj.put("Message", "");
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        NetworkUtility.makeJSONObjectRequest(API.InsertMedpassLog, mainObj, API.InsertMedpassLog, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    if (result != null) {
                        if(result.has("ResponseStatus")&&result.getString("ResponseStatus").equalsIgnoreCase(String.valueOf(1))){
                            Log.e("TAG", "onSuccess: " + result.get("Data"));
                        }else{
                            InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong));
                        }
                    }

                } catch (Exception ex) {
                    dismissProgressDialog();
                    ex.printStackTrace();
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Something Went Wrong...!!!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();*/
                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "4");
                    InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Something Went Wrong...!!!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "4");
                InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

            }
        });
    }


    private void medpassFormDataImageSave(boolean isAdministered) {

        if (DisContinuedBase64 != null) {
            Log.e("DisContinuedBase64URI:", "medpassFormDataImageSave: " + DisContinuedBaseURI);
        } else {
            RequestBody file = RequestBody.create(MultipartBody.FORM, "");

        }
        if (base64 != null) {
            Log.e("TAG", "medpassFormDataImageSave: MedImageURI" + MedImageURI);
            Log.e("TAG", "medpassFormDataImageSave: " + base64);
        } else {

        }
        if (mBinding.edtConsultantname.getVisibility() == View.VISIBLE) {
            String consultantName = mBinding.edtConsultantname.getText().toString().trim();
            if (!consultantName.equals("")) {
                UpdatePatientMedpassDetails_By_MedpassID(isAdministered);
            } else {
                hud.dismiss();
                Toast toast = Toast.makeText(context, "Consultant Name can't be left blank", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else {
            UpdatePatientMedpassDetails_By_MedpassID(isAdministered);
        }
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
                //  param.put("note", isNote ? edt_notes.getText().toString() : "");
                param.put("note", mBinding.edtNotes.getText().toString());

                if (isAdministered) {
                    param.put("dose_status", "D");
                } else {
                    param.put("dose_status", "R");
                }
                param.put("Updated_by", MyApplication.getPrefranceData("UserID"));
                param.put("VerifyDrug", mBinding.verifiedOrder.isChecked());
                param.put("MedpassID", modelMessPrescriptionList.get(i).getMedpassID());
                mainObj.put("MedImage", base64);
                mainObj.put("DisconMedImage", DisContinuedBase64);
                mainObj.put("ConsultantName", mBinding.edtConsultantname.getText().toString());

                jsonArray.put(param);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        try {
            mainObj.put("details", jsonArray);
            Log.e("TAG", "UpdatePatientMedpassDetails_By_MedpassID: "+jsonArray );
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        NetworkUtility.makeJSONObjectRequest(API.UpdatePatientMedpassDetails_By_MedpassID, mainObj, API.UpdatePatientMedpassDetails_By_MedpassID, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    try {
                        if (result != null) {
                            if(result.has("ResponseStatus")&&result.getString("ResponseStatus").equalsIgnoreCase(String.valueOf(1))) {

                                Log.e("TAG", "onSuccess: " + result.get("Data"));
                                boolean isData = (boolean) result.get("Data");
                                if (isData) {
                                    if (multiPack == null) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("Successful")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.dismiss();
                                                        if (isListEmpty) {
                                                            if (isMissDose && missDoseCount > modelMessPrescriptionList.size()) {
                                                                finish();
                                                            } else if (isMissDose && missDoseCount == modelMessPrescriptionList.size()) {
                                                               /* MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
                                                                Intent intent = new Intent(context, PatientListActivityNew.class);
                                                                intent.putExtra("medPass", true);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intent);*/
                                                                finish();
                                                            } else {
                                                              /*  MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
                                                                Intent intent = new Intent(context, PatientListActivityNew.class);
                                                                intent.putExtra("medPass", true);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intent);*/
                                                                finish();
                                                            }
                                                        } else {
                                                            finish();
                                                        }
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();

                                    } else {
                                        InsertFillPackhistory();
                                    }
                                }
                            }else{
                                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong));
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Something Went Wrong...!!!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();*/
                        showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "4");
                        InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

                    }
                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Something Went Wrong...!!!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
                showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "4");
                InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong));

            }
        });
    }


    private void InsertFillPackhistory() {
        hud.show();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String currentDate = sdf.format(new Date());
        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", modelMessPrescriptionList.get(0).getFacility_id());
            param.put("PatientID", modelMessPrescriptionList.get(0).getPatient_id());
            param.put("DoseDate", date);
            param.put("DoseTime", time);
            param.put("GivenBy", MyApplication.getPrefranceData("UserID"));
            param.put("GivenDate", currentDate);
            param.put("FillID", pillId);
            param.put("ScaningValue", QRcodeformat);
            param.put("GivenPack", Integer.parseInt(multiPack1));
            param.put("TotalPack", Integer.parseInt(multiPack2));
        } catch (Exception r) {
            r.printStackTrace();
        }

        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), param.toString());
        Log.e("TAG", "InsertFillPackhistory: " + bodyRequest);
        Call<InsertFillPackhistoryModel> call = apiService.InsertFillPackhistory(bodyRequest);
        call.enqueue(new Callback<InsertFillPackhistoryModel>() {
            @Override
            public void onResponse(@NonNull Call<InsertFillPackhistoryModel> call, @NonNull Response<InsertFillPackhistoryModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    if (response.body() != null) {
                        int response_code = response.body().getResponseStatus();
                        if (response_code == 1) {
                            try {
                                if (response.body().isData()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("Successful")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.dismiss();
                                                    if (isListEmpty) {
                                                        if (isMissDose && missDoseCount > modelMessPrescriptionList.size()) {
                                                            finish();
                                                        } else if (isMissDose && missDoseCount == modelMessPrescriptionList.size()) {
                                                            MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
                                                            Intent intent = new Intent(context, PatientListActivityNew.class);
                                                            intent.putExtra("medPass", true);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                        } else {
                                                            MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
                                                            Intent intent = new Intent(context, PatientListActivityNew.class);
                                                            intent.putExtra("medPass", true);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                        }
                                                    } else {
                                                        finish();
                                                    }
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertFillPackhistoryModel> call, @NonNull Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
                Utils.showAlertToast(context, t.toString());
                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong));
            }
        });
//        base64 = null;

    }


    private void initRecyclerView() {
        mBinding.rvMessMultiPrescriptionList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //   barcodeOpen = 0;
        Log.e("stop", "stop");

    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastIntent();
        //
        /*broadcastIntent();
        if (Utils.checkInternetConnection(context)) {
            if (Application.getPrefranceDataBoolean("isDesc")) {
                GetCurrentMedpassDetailsByFillidOnResume(pillId);
                Application.setPreferencesBoolean("isDesc", false);
            } else {
                if (!onCreateFill) {
                    GetCurrentMedpassDetailsByFillid(pillId);
                }
            }
        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hud.isShowing()) {
            hud.dismiss();
        }
        if (MyReceiver != null) {
            unregisterReceiver(MyReceiver);
        }
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
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 105) {
            if (dcImageClick) {
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
                        DisContinuedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                        GalleryBm = bmOriginal;
                        mDialogDcWithCheckboxBinding.dcimgPromotionalItemDialog.setImageBitmap(bmOriginal);
                        mBinding.dcimgPromotionalItem.setImageBitmap(bmOriginal);
                        dcImageClick = false;
//                    mBinding.imgPromotionalItem.setImageBitmap(bmOriginal);
                        //Glide.with(context).load(fileName).into(mBinding.imgPromotionalItem);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
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
                        normalImageClick = false;
                        //Glide.with(context).load(fileName).into(mBinding.imgPromotionalItem);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                try {
                    if (dcImageClick) {
                        Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), getCacheImagePath(disContinueFileName));
                        GalleryBm = bm;
                        if (bm != null) {
                            DisContinuedBaseURI = getImageUri(context, bm, disContinueFileName);
//                            dcimg_promotional_item_dialog.setImageBitmap(bm);
                            try {
                                hud.show();
                                DisContinuedBase64 = Utils.convertImageToBase64(bm);
                                hud.dismiss();
                            } catch (Exception ex) {
                                hud.dismiss();
                            }
                        }
                        dcImageClick = false;
                    } else {
                        Bitmap bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), getCacheImagePath(fileName));

                        if (bm != null) {
                            MedImageURI = getImageUri(context, bm, fileName);
                            mBinding.imgPromotionalItem.setImageBitmap(bm);
                            try {
                                hud.show();
                                base64 = Utils.convertImageToBase64(bm);
                                hud.dismiss();
                            } catch (Exception ex) {
                                hud.dismiss();

                            }
                        }
                        normalImageClick = false;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == SELECT_FILE) {
            onSelectFromGalleryResult(intent);
        } else if (requestCode == 103) {
            GetCurrentMedpassDetailsByFillid(pillId);
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String name) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, name, null);
        return Uri.parse(path);
    }

    private String getFormate(String date) throws ParseException {
        if (date != null && !date.isEmpty()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(date);
            Log.d("Date", String.valueOf(d));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            @SuppressLint("SimpleDateFormat") String monthName = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
            return monthName;
        } else {
            return "";
        }
    }


    public void openBarcode() {
        if (barcodeOpen == 1) {
            Log.e("barcodeOpen", String.valueOf(barcodeOpen));
            if (modelMessPrescriptionList.size() > 0) {
                Log.e("Scab", "Savngn");
                setBarcode();
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
                if (modelMessPrescriptionList.size() <= 0) {
                    Intent intent = new Intent(context, PatientListActivityNew.class);
                    intent.putExtra("medPass", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    if (isDiscontinue) {
                        isShowDialog = true;
                        showDcDialog();
                    } else {

                    }
                }
            } else if (check.equalsIgnoreCase("2")) {
                if (modelMessPrescriptionList.size() <= 0) {
                    Intent intent = new Intent(context, PatientListActivityNew.class);
                    intent.putExtra("medPass", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    if (isDiscontinue) {
                        isShowDialog = true;
                        showDcDialog();
                    } else {
                        if (multiPack != null && !multiPack.isEmpty() && !multiPack.equalsIgnoreCase("1/1")) {
                            openAlertDialogMultiPack();
                        }
                    }
                }
            } else if (check.equalsIgnoreCase("3")) {
                if (isListEmpty) {
                    Intent intent = new Intent(context, PatientListActivityNew.class);
                    intent.putExtra("medPass", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            } else if (check.equalsIgnoreCase("4")) {

            } else if (check.equalsIgnoreCase("5")) {

            }
        });
        mDialogShowAlertBinding.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            if (check.equalsIgnoreCase("1")) {
                finish();
            } else if (check.equalsIgnoreCase("2")) {
                finish();
            } else if (check.equalsIgnoreCase("3")) {

            } else if (check.equalsIgnoreCase("4")) {

            } else if (check.equalsIgnoreCase("5")) {

            }
        });
        dialogSHowAlert.show();
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