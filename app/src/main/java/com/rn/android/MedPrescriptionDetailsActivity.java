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
import android.media.ExifInterface;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.ActivityMessPrescriptionDetailsNewBinding;
import com.metiz.pelconnect.databinding.DialogShowAlertBinding;
import com.rn.android.Adapter.MedPassPrescriptionAdapter;
import com.rn.android.base.MediaSelectionDialog;

import com.rn.android.dialog.AlloyHospitalDialogNew;
import com.rn.android.listeners.PickerOptionListener;
import com.rn.android.model.AlloyHospital;
import com.rn.android.model.GetCompleteDoseModel;
import com.rn.android.model.InsertFillPackhistoryModel;
import com.rn.android.model.ModelMessPrescription;
import com.rn.android.model.UpdatePatientMedPassModel;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.retrofit.ApiClient;
import com.rn.android.retrofit.ApiInterface;
import com.rn.android.util.MyReceiver;
import com.rn.android.util.PreferenceHelper;
import com.rn.android.util.TakePicture;
import com.rn.android.util.TouchImageView;
import com.rn.android.util.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedPrescriptionDetailsActivity extends BaseActivity {

    private static int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            //Manifest.permission.ACCESS_MEDIA_LOCATION,
    };
    int PERMISSION_ALL = 7;
    ActivityMessPrescriptionDetailsNewBinding mBinding;
    List<AlloyHospital.LOAHospital> loaHospitalList = new ArrayList<>();
    List<ModelMessPrescription> modelMessPrescriptionList = new ArrayList<>();
    MedPassPrescriptionAdapter medPassPrescriptionAdapter;

    /*public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 124;
    @BindView(R.id.tv_drug_name)
    TextView tvDrugName;
    @BindView(R.id.tv_rx_number)
    TextView tvRxNumber;
    @BindView(R.id.tv_drug_details)
    TextView tvDrugDetails;
    @BindView(R.id.tv_drug_time)
    TextView tvDrugTime;
    @BindView(R.id.tv_drug_qty)
    TextView tvDrugQty;
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
    @BindView(R.id.et_drug_qty)
    EditText et_drug_qty;
    @BindView(R.id.edt_notes)
    ActionEditText edtNotes;
    @BindView(R.id.verified_med_order)
    CheckBox verified_med_order;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    // ProgressDialog progressDialog;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    @BindView(R.id.img_promotional_item)
    ImageView imgPromotionalItem;*/
    String imageUrl = "";
    double drugQty = 0;
    int drugQtyinInt = 0;
    int tvDrugQtyinInt = 0;
    int missDoseCount = 0;
    int tran_id = 0, facility_id = 0, listSize = 0, patientId;
    String ndc = "";
    String patientName = "";
    String Gender = "";
    String Gender_Full = "";
    String BirthDate = "";
    String sig_code = "";
    String lName = "";
    String fName = "";
    String QRcodeformat = "";
    String numberD;
    String beforeDot;
    String fileName;
    String multiPack;
    int multiPack1;
    int multiPack2;
    String patient_lName = "", patient_fName = "";
    String drugName = "", rxNumber = "", drugDetails = "", drugTime = "", drugDate = "", msg = "", sigCode = "";
    boolean Is_PrescriptionImg;
    boolean isListEmpty;
    boolean isMissDoseNew;
    ApiInterface apiService;
    DialogInterface.OnClickListener dialogClickListener;
    AlertDialog alertDialog = null;
    private TakePicture takePicture;
    private BroadcastReceiver MyReceiver = null;
    private String base64 = null;
    private boolean isLoa;
    private boolean isNote;
    private boolean isErly;
    private boolean isLate;
    boolean isWrongDialog = false;
    private boolean IsShown = false;
    private boolean isPRN;
    private boolean isMissDose;
    private KProgressHUD hud;
    private DialogShowAlertBinding mDialogShowAlertBinding;
    private Dialog dialogSHowAlert;
    String pillId;

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
//        setContentView(R.layout.activity_mess_prescription_details_new);
        mBinding = DataBindingUtil.setContentView(activity, R.layout.activity_mess_prescription_details_new);
//        ButterKnife.bind(this);

        onCreatedMethod();
    }

    private void onCreatedMethod() {
        takePicture = new TakePicture(activity);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        MyReceiver = new MyReceiver();
        IsShown = false;
        initProgress();
        if (getIntent() != null) {
            Log.e("Call1", "Call1");
            drugName = getIntent().getStringExtra("drug");
            rxNumber = getIntent().getStringExtra("rxNumber");
            msg = getIntent().getStringExtra("msg");
            drugDetails = getIntent().getStringExtra("sig_detail");
            sig_code = getIntent().getStringExtra("sig_code");
            patientName = getIntent().getStringExtra("name");
            isErly = getIntent().getBooleanExtra("IsErly", false);
            isLate = getIntent().getBooleanExtra("IsLate", false);
            fName = getIntent().getStringExtra("fname");
            lName = getIntent().getStringExtra("lname");
            drugTime = getIntent().getStringExtra("dose_time");
            drugDate = getIntent().getStringExtra("dose_date");
            Log.e("TAG", "onCreatedMethod:time " + drugTime);
            drugQty = getIntent().getDoubleExtra("dose_Qty", 0);
            Log.e("TAG", "drugQty: " + drugQty);
            tran_id = getIntent().getIntExtra("tran_id", 0);
            listSize = getIntent().getIntExtra("listSize", 0);
            Log.e("TAG", "onCreatedMethod:time:size " + listSize);
            facility_id = getIntent().getIntExtra("facility_id", 0);
            patientId = getIntent().getIntExtra("patientId", 0);
            missDoseCount = getIntent().getIntExtra("missDoseCount", 0);
            ndc = getIntent().getStringExtra("ndc");
            Gender = getIntent().getStringExtra("gender");
            Gender_Full = getIntent().getStringExtra("gender_full");
            sigCode = getIntent().getStringExtra("sig_code");
            QRcodeformat = getIntent().getStringExtra("QRcodeformat");
            Log.e("TAG", "onCreatedMethod: " + QRcodeformat);
            pillId = getIntent().getStringExtra("pillId");
            Log.e("TAG", "onCreatedMethod:pillId " + pillId);
            BirthDate = getIntent().getStringExtra("BirthDate");
            multiPack = getIntent().getStringExtra("multiPack");
            Log.e("TAG", "multiPack: ===> " + multiPack);
            isLoa = getIntent().getBooleanExtra("isMissedDose", false);
            isMissDoseNew = getIntent().getBooleanExtra("isMissedDose", false);
            isPRN = getIntent().getBooleanExtra("isPRN", false);
            isNote = getIntent().getBooleanExtra("isNote", false);
            isListEmpty = getIntent().getBooleanExtra("isListEmpty", false);
            isMissDose = getIntent().getBooleanExtra("isMissDose", false);
            Is_PrescriptionImg = getIntent().getBooleanExtra("Is_PrescriptionImg", false);
            Log.e("Fraction Point:", "--->:" + Utils.convertDecimalToFraction(drugQty));
            numberD = String.valueOf(drugQty);
            beforeDot = numberD.substring(0, numberD.indexOf("."));
            beforeDot = beforeDot.replace(".", "");
            Log.e("kept:", "onCreate: " + beforeDot);
            numberD = numberD.substring(numberD.indexOf("."));
            Log.e("listSize:", "--->:" + listSize);
            Log.e("Name:", "--->:" + fName + lName);

            if (multiPack != null) {
                String[] separated = multiPack.split("/");
                multiPack1 = Integer.parseInt(separated[0]);
                multiPack2 = Integer.parseInt(separated[1]);
            }

            if (isMissDoseNew | isLate | isErly) {
                mBinding.edtConsultantname.setVisibility(View.VISIBLE);
            }
        }

        if (isPRN) {
            Log.e("Call2", "Call2");

            mBinding.etDrugQty.setVisibility(View.VISIBLE);
            mBinding.tvDrugQty.setVisibility(View.GONE);
        } else {
            Log.e("Call1", "Call1");

            mBinding.etDrugQty.setVisibility(View.GONE);
            mBinding.tvDrugQty.setVisibility(View.VISIBLE);
        }

        if (isPRN) {
            Log.e("Call2", "Call2");
            mBinding.txtAlloyHospital.setVisibility(View.GONE);
        } else {
            Log.e("Call1", "Call1");
            mBinding.txtAlloyHospital.setVisibility(View.VISIBLE);
        }

        MyApplication.setPreferencesBoolean("isDesc", true);

        if (Is_PrescriptionImg) {
            mBinding.rxImage.setVisibility(View.VISIBLE);
        } else {
            mBinding.rxImage.setVisibility(View.GONE);
        }

        Log.e("TAG", "isMissedDose 1: " + isLoa);
        Log.e("TAG", "patientId 1: " + patientId);

        getUserImage(patientId);
        getDrugImage(ndc);

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

        if (msg != null && !msg.isEmpty()) {
            if (!IsShown) {
                showAlertDialog(msg, context.getResources().getString(R.string.medpass_alert), "Cancel", "Ok", "1");
                IsShown = true;
            }
        } else {

        }

        mBinding.txtAlloyHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loaHospitalList.size() > 0) {
                    new AlloyHospitalDialogNew(activity, context, loaHospitalList, patientId, patientName, isMissDoseNew, drugTime).show();
                } else {
                    Toast.makeText(context, "LOA list not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBinding.btnTaking.setBackgroundDrawable(getResources().getDrawable(R.drawable.mess_button_gry));
        mBinding.btnTaking.setEnabled(false);
        mBinding.btnTaking.setTextColor(getResources().getColor(R.color.white));
        mBinding.btnTaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoa || isNote || msg != null && !msg.isEmpty()) {
                    if (isPRN) {
                        String note = mBinding.edtNotes.getText().toString().trim();
                        String edtnotes = mBinding.edtConsultantname.getText().toString().trim();
                        String GivendrugQty = mBinding.etDrugQty.getText().toString().trim();
                        if (!GivendrugQty.isEmpty() && !"".equals(GivendrugQty)) {
                            if (!note.isEmpty() && !"".equals(note)) {
                                showDialogForAdministered();
                            } else {
                                Toast toast = Toast.makeText(context, "Note can't be left blank", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        } else {
                            Toast toast = Toast.makeText(context, "Drug Quantity can't be left blank", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        String note = mBinding.edtNotes.getText().toString().trim();
                        if (!note.isEmpty() && !"".equals(note)) {
                            if (mBinding.edtConsultantname.getVisibility() == View.VISIBLE) {
                                String consultantName = mBinding.edtConsultantname.getText().toString().trim();
                                if (!consultantName.equals("")) {
                                    showDialogForAdministered();
                                } else {
                                    Toast toast = Toast.makeText(context, "Consultant Name can't be left blank", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            } else {
                                showDialogForAdministered();
                            }
                        } else {
                            Toast toast = Toast.makeText(context, "Note can't be left blank", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                } else {
                    showDialogForAdministered();
                }
            }
        });
        mBinding.btnRefused.setOnClickListener(v -> {
            hud.show();
            if (isLoa || isNote || msg != null && !msg.isEmpty()) {
                String note = mBinding.edtNotes.getText().toString().trim();
                if (!note.isEmpty() && !"".equals(note)) {
                    if (mBinding.edtConsultantname.getVisibility() == View.VISIBLE) {
                        String consultantName = mBinding.edtConsultantname.getText().toString().trim();
                        if (!consultantName.equals("")) {
                            hud.dismiss();
                            showDialogForRefused();
                        } else {
                            hud.dismiss();
                            Toast toast = Toast.makeText(context, "Consultant Name can't be left blank", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } else {
                        hud.dismiss();
                        showDialogForRefused();
                    }
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
                            InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);
                        }
                    } else {
                        InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);
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
                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
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

    private void showAlertDialog(String message, String title, String negativeBtn, String
            positiveBtn, String check) {

        dialogSHowAlert = new Dialog(context);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mDialogShowAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_show_alert, null, false);
        dialogSHowAlert.setContentView(mDialogShowAlertBinding.getRoot());
//      dialogDiscontinue.getWindow().getAttributesre().windowAnimations = R.style.DialogAnimation_2;
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

            } else if (check.equalsIgnoreCase("3")) {
                finish();
            } else if (check.equalsIgnoreCase("4")) {
                MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
                Log.e("DATA:", "onClick: " + listSize + "----->" + isMissDose + "----->" + missDoseCount);
                if (listSize == 1) {
                    if (isMissDose && missDoseCount > 1) {
                        Log.e("if1", "onClick: ");
                        Intent intent = new Intent(context, MedPrescriptionTabActivity.class);
                        intent.putExtra("PatientId", patientId);
                        intent.putExtra("name", patientName);
                        intent.putExtra("fname", fName);
                        intent.putExtra("lname", lName);
                        intent.putExtra("Is_PrescriptionImg", Is_PrescriptionImg);
                        //intent.putExtra("isLOAHospital", filterList.get(position).isLOAHospital());
                        intent.putExtra("BirthDate", BirthDate);
                        intent.putExtra("gender", Gender);
                        intent.putExtra("gender_full", Gender_Full);
                        intent.putExtra("fromDetail", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (isMissDose && missDoseCount == 1) {
                        Log.e("elseif1", "onClick: ");
                        if (isListEmpty) {
                            MyApplication.setPreferencesBoolean("isDesc", false);
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, MedPrescriptionTabActivity.class);
                            intent.putExtra("PatientId", patientId);
                            intent.putExtra("name", patientName);
                            intent.putExtra("fname", fName);
                            intent.putExtra("lname", lName);
                            intent.putExtra("Is_PrescriptionImg", Is_PrescriptionImg);
                            //intent.putExtra("isLOAHospital", filterList.get(position).isLOAHospital());
                            intent.putExtra("BirthDate", BirthDate);
                            intent.putExtra("gender", Gender);
                            intent.putExtra("gender_full", Gender_Full);
                            intent.putExtra("fromDetail", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    } else {
                        Log.e("else1", "onClick: ");
                        if (isListEmpty) {
                            MyApplication.setPreferencesBoolean("isDesc", false);
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(context, MedPrescriptionTabActivity.class);
                            intent.putExtra("PatientId", patientId);
                            intent.putExtra("name", patientName);
                            intent.putExtra("fname", fName);
                            intent.putExtra("lname", lName);
                            intent.putExtra("Is_PrescriptionImg", Is_PrescriptionImg);
                            //intent.putExtra("isLOAHospital", filterList.get(position).isLOAHospital());
                            intent.putExtra("BirthDate", BirthDate);
                            intent.putExtra("gender", Gender);
                            intent.putExtra("gender_full", Gender_Full);
                            intent.putExtra("fromDetail", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                } else {
                    if (isMissDose && missDoseCount > 1) {
                        Log.e("if2", "onClick: ");
                        finish();
                    } else if (isMissDose && missDoseCount == 1) {
                        Log.e("elseif3", "onClick: ");
                        if (isListEmpty) {
                            MyApplication.setPreferencesBoolean("isDesc", false);
                            Intent intent = new Intent(context, PatientListActivityNew.class);
                            intent.putExtra("medPass", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            finish();
                        }
                    } else {
                        Log.e("else4", "onClick: ");
                        finish();
                    }
                }
            }
        });
        mDialogShowAlertBinding.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            if (check.equalsIgnoreCase("1")) {
                finish();
            } else if (check.equalsIgnoreCase("2")) {

            } else if (check.equalsIgnoreCase("3")) {

            } else if (check.equalsIgnoreCase("4")) {

            }
        });
        dialogSHowAlert.show();

        /*if (alertDialog != null) {
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
                    finish();
                } else if (check.equalsIgnoreCase("2")) {

                }
            });
        }
        builder.setPositiveButton(positiveBtn, (dialog, id) -> {
            dialog.dismiss();
            if (check.equalsIgnoreCase("1")) {

            } else if (check.equalsIgnoreCase("2")) {

            }
        });
        alertDialog = builder.create();
        alertDialog.show();*/
    }

    private void showDialogForAdministered() {
        if (isPRN) {
            UpdatePatientMedPassDrugDetailsPRN("D");
        } else {
            UpdatePatientMedPassDrugDetails("D");
        }
    }

    private void UpdatePatientMedPassDrugDetailsPRN(String doseStatus) {
        if (mBinding.etDrugQty.getText().toString().trim().isEmpty() || mBinding.etDrugQty.getText().toString().trim().equals("0") || mBinding.etDrugQty.getText().toString().trim().equals(".") || mBinding.etDrugQty.getText().toString().trim().equals("0.0") || mBinding.etDrugQty.getText().toString().trim().equals(".0")) {
            Toast.makeText(context, "Need to provide drug Quantity.", Toast.LENGTH_SHORT).show();
        } else {
            hud.show();
            JSONObject param = new JSONObject();
            try {
                /*@SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                System.out.println(formatter.format(date));
                param.put("dose_date", formatter.format(date));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                param.put("dose_time", time.format(new Date()));
                param.put("patient_id", patientId);
                param.put("facility_id", Application.getPrefranceDataInt("ExFacilityID"));
                param.put("dose_Qty", Double.parseDouble(et_drug_qty.getText().toString().trim()));
                param.put("dose_status", doseStatus);
                param.put("note", edtNotes.getText().toString());
                param.put("med_type", "P");
                param.put("sig_code", sig_code);
                param.put("sig_detail", drugDetails);
                param.put("internal_rx_id", tran_id);
                param.put("rx_number", rxNumber);
                param.put("drug", drugName);
                param.put("patient_last", lName);
                param.put("patient_first", fName);
                String startDateString = Utils.DateFromOnetoAnother(BirthDate, "MM/dd/yyyy", "yyyy-dd-MM");
                param.put("dob", startDateString);
                param.put("gender", Gender_Full);
                param.put("ndc", ndc);
                param.put("VerifyDrug", verified_med_order.isChecked());
                param.put("CreatedBY", Integer.parseInt(Application.getPrefranceData("UserID")));
                Log.e("TAG", "UpdatePatientMedPassDrugDetailsPRN: "+param.getString("gender"));
                if (base64 != null) {
                    param.put("MedImagePath", base64);    //Image in base64
                }
            } catch (Exception r) {
                r.printStackTrace();
            }*/
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                System.out.println(formatter.format(date));
                param.put("dose_date", formatter.format(date));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat time = new SimpleDateFormat("HH:mm:00.000");
                param.put("dose_time", time.format(new Date()));
                param.put("patient_id", patientId);
                param.put("facility_id", MyApplication.getPrefranceDataInt("ExFacilityID"));
                param.put("dose_Qty", Double.parseDouble(mBinding.etDrugQty.getText().toString().trim()));
                param.put("dose_status", doseStatus);
                param.put("note", mBinding.edtNotes.getText().toString());
                param.put("med_type", "P");
                param.put("sig_code", sigCode);
                param.put("sig_detail", drugDetails);
                param.put("internal_rx_id", tran_id);
                param.put("rx_number", rxNumber);
                param.put("drug", drugName);
                param.put("patient_last", lName);
                Log.e("TAG", "UpdatePatientMedPassDrugDetailsPRN: patient_last" + lName);
                param.put("patient_first", fName);
                String startDateString = Utils.DateFromOnetoAnother(BirthDate, "MM/dd/yyyy", "MM/dd/yyyy");
                param.put("dob", startDateString);
                param.put("gender", Gender_Full);
                param.put("ndc", ndc);
                param.put("VerifyDrug", mBinding.verifiedMedOrder.isChecked());
                param.put("CreatedBY", Integer.parseInt(MyApplication.getPrefranceData("UserID")));


                if (base64 != null) {
                    param.put("MedImagePath", base64);    //Image in base64
                }

            } catch (Exception r) {
                r.printStackTrace();
            }

            Log.d("TAG", "UpdatePatientMedPassDrugDetails: " + param);
            NetworkUtility.makeJSONObjectRequest(API.ManagePatientPRNMedPassDrugDetails, param, API.ManagePatientPRNMedPassDrugDetails, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        hud.dismiss();
                        if (result != null) {
                            if (result.has("ResponseStatus") && result.getString("ResponseStatus").equalsIgnoreCase(String.valueOf(1))) {
                            /*android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                            builder.setMessage("Successful")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", (dialog, id) -> {
                                        finish();
                                    });
                            android.app.AlertDialog alert = builder.create();
                            alert.show();*/
                                showAlertDialog(context.getResources().getString(R.string.successful), "", "", "OK", "3");
                            }else{
                                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(JSONObject result) {
                    hud.dismiss();
                    InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);

                }
            });

//            base64 = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastIntent();
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void showDialogForRefused() {
        if (isPRN) {
            UpdatePatientMedPassDrugDetailsPRN("R");
        } else {
            UpdatePatientMedPassDrugDetails("R");
        }
    }

    private void dialogForImage() {
        TextView textView = new TextView(context);
        textView.setText("Acknowledge Alert");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
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
                                try {
                                    Intent intent = new Intent(context, Custom_CameraActivity.class);
                                    startActivityForResult(intent, 105);
//                                    takePicture.captureImageUsingCamera();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                fileName = System.currentTimeMillis() + ".jpg";
//                        if (cameraPermission()) {
//                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
//                                if (takePictureIntent.resolveActivity(MedPrescriptionDetailsActivity.this.getPackageManager()) != null) {
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
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

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
                    if (takePictureIntent.resolveActivity(MedPrescriptionDetailsActivity.this.getPackageManager()) != null) {
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
        } else {
            if (resultCode == RESULT_OK) {
                try {
                    fileName = "";
                    fileName = takePicture.onActivityResult(requestCode, resultCode, resultData);
//                Log.e("Android 11 >>", "" + getCacheImagePath(fileName) + " >> " + fileName);
                    Bitmap bm = BitmapFactory.decodeFile(fileName);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, baos); //bm is the bitmap object
                    byte[] bytes = baos.toByteArray();

                    ExifInterface exif = new ExifInterface(fileName);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    Log.e("TAG", "onActivityResult: >>>" + orientation, null);
                    base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                    mBinding.imgPromotionalItem.setImageBitmap(bm);
                    Glide.with(context).load(fileName).into(mBinding.imgPromotionalItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getCameraPhotoOrientation(Context context, String imagePath) {

        int rotate = 0;
        int orientation = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("RotateImage", "Exif orientation: " + orientation);
        Log.i("RotateImage", "Rotate value: " + rotate);
        return rotate;

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

    /*@RequiresApi(api = Build.VERSION_CODES.Q)
    private boolean checkPermissions() {
        if (SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(MedPrescriptionDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
                && ContextCompat.checkSelfPermission(MedPrescriptionDetailsActivity.this, Manifest.permission.CAMERA)
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
        ActivityCompat.requestPermissions(MedPrescriptionDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
    }

    // TODO ask for Camera permission
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestPermissionForCamera() {
        ActivityCompat.requestPermissions(MedPrescriptionDetailsActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
    }*/

    private void setData() {
        mBinding.tvDrugName.setText(drugName);
        mBinding.tvRxNumber.setText("Rx Number: " + rxNumber);
        mBinding.tvDrugDetails.setText(drugDetails);
        mBinding.tvDrugTime.setText(drugTime);
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
        if (tvDrugQtyinInt != 0) {
            if (isPRN) {
                mBinding.etDrugQty.setText(tvDrugQtyinInt + "");
            } else {
//                mBinding.tvDrugQty.setText(tvDrugQtyinInt + "");
            }
        } else {
            if (isPRN) {
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
                Date date = new Date();
                System.out.println(formatter.format(date));
                mBinding.tvDrugTime.setText(formatter.format(date));
//                mBinding.etDrugQty.setText(drugQty + "");
            } else {
                mBinding.tvDrugQty.setText(drugQty + "");
            }
        }
        if (!isPRN) {
            if (Integer.parseInt(beforeDot.replace(".", "")) > 0) {
                if (Integer.parseInt(numberD.replace(".", "")) > 0) {
                    mBinding.tvDrugQty.setText(beforeDot + " and 1/2");
                } else {
                    mBinding.tvDrugQty.setText(beforeDot);
                }
            } else {
                mBinding.tvDrugQty.setText("1/2");
            }
        }

        mBinding.txtName.setText(patientName);
        mBinding.txtGender.setText(Gender);
        mBinding.txtBirthdate.setText(BirthDate);
    }

    private void UpdatePatientMedPassDrugDetails(String doseStatus) {
        hud.show();
        JSONObject param = new JSONObject();

        try {
            param.put("tran_id", tran_id);
            param.put("patient_id", patientId);
            param.put("facility_id", facility_id);
            param.put("rx_number", rxNumber);
            param.put("note", mBinding.edtNotes.getText().toString());
            param.put("dose_status", doseStatus);
            param.put("Updated_by", MyApplication.getPrefranceData("UserID"));
            param.put("VerifyDrug", mBinding.verifiedMedOrder.isChecked());
            param.put("ConsultantName", mBinding.edtConsultantname.getText());

            if (multiPack != null) {
                param.put("doseTime", drugTime);
                param.put("DoseDate", drugDate);
                param.put("FillID", pillId);
                param.put("ScaningValue", QRcodeformat);
                param.put("GivenPack", multiPack1);
                param.put("TotalPack", multiPack2);
                param.put("TotalCount", listSize);
            } else {
                param.put("doseTime", "");
                param.put("DoseDate", "");
                param.put("FillID", "");
                param.put("ScaningValue", QRcodeformat);
                param.put("GivenPack", "");
                param.put("TotalPack", "");
                param.put("TotalCount", listSize);
            }

            if (base64 != null) {
                param.put("MedImagePath", base64);    //Image in base64
            }


            Log.d("TAG", "MedImagePath: " + base64);
            Log.e("TAG", "MedImagePath:>>>>> " + param);

        } catch (Exception r) {
            r.printStackTrace();
        }


        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), param.toString());
        Call<UpdatePatientMedPassModel> call = apiService.UpdatePatientMedPassDrugDetails(bodyRequest);
        call.enqueue(new Callback<UpdatePatientMedPassModel>() {
            @Override
            public void onResponse(@NonNull Call<UpdatePatientMedPassModel> call, @NonNull Response<UpdatePatientMedPassModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    if (response.body() != null) {
                        int response_code = response.body().getResponseStatus();
                        if (response_code == 1) {
                            try {
                                showAlertDialog(context.getResources().getString(R.string.successful), "", "", "OK", "4");
                            } catch (Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);

                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<UpdatePatientMedPassModel> call, @NonNull Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
                Utils.showAlertToast(context, t.toString());
                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);

            }
        });
//        base64 = null;

    }

    private void GetCompleteDose(String doseStatus) {
        hud.show();

        Call<GetCompleteDoseModel> call = apiService.GetCompleteDose(facility_id, patientId, drugTime);
        call.enqueue(new Callback<GetCompleteDoseModel>() {
            @Override
            public void onResponse(@NonNull Call<GetCompleteDoseModel> call, @NonNull Response<GetCompleteDoseModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    if (response.body() != null) {
                        int response_code = response.body().getResponseStatus();
                        if (response_code == 1) {
                            try {
                                if (response.body().getData().size() > 0) {
                                    InsertFillPackhistory(doseStatus);
                                } else {
                                    showAlertDialog(context.getResources().getString(R.string.successful), "", "", "OK", "4");
                                }
                            } catch (Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<GetCompleteDoseModel> call, @NonNull Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
                Utils.showAlertToast(context, t.toString());
            }
        });
//        base64 = null;

    }

    private void InsertFillPackhistory(String doseStatus) {
        hud.show();

        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facility_id);
            param.put("PatientID", patientId);
            param.put("DoseDate", "2022-08-23 00:00:00.000");
            param.put("DoseTime", "08:00:00.000");
            param.put("GivenBy", 77);
            param.put("GivenDate", "2022-08-23 10:08:55.640");
            param.put("FillID", "-2650897");
            param.put("ScaningValue", "-2650897,2022/08/23,0800");

        } catch (Exception r) {
            r.printStackTrace();
        }

        RequestBody bodyRequest = RequestBody.create(MediaType.parse("application/json"), param.toString());
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

                            } catch (Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<InsertFillPackhistoryModel> call, @NonNull Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
                Utils.showAlertToast(context, t.toString());
                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);
            }
        });
//        base64 = null;

    }

    private void getPrescription(int tran_id) {
        hud.show();

        NetworkUtility.makeJSONObjectRequest(API.PriscriptionImagePath + "?tran_id=" + tran_id, new JSONObject(), API.PriscriptionImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    if(result.has("ResponseStatus")&&result.getString("ResponseStatus").equalsIgnoreCase(String.valueOf(1))) {

                        if (result.getString("Data").isEmpty()) {

                            noPrescriptionDialog();
                        } else {
                            openImageDialog(result.getString("Data"));
                        }
                    }else{
                        InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
                InsertSometingwantErrorLog(getResources().getString(R.string.something_went_wrong), drugDate, drugTime);

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
        showAlertDialog(context.getResources().getString(R.string.no_prescription_image_available),
                "", "", "OK", "2");
    }

    private void getUserImage(int patientId) {
//        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.PatientImagePath + "?patient_id=" + patientId, new JSONObject(), API.PatientImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
//                hud.dismiss();
                try {

                    mBinding.llMain.setVisibility(View.VISIBLE);

                    if (result.getString("Data").equalsIgnoreCase("")) {

                        Glide.with(activity).
                                load(R.drawable.ic_user_placeholder).
                                placeholder(R.drawable.ic_user_placeholder).
                                error(R.drawable.ic_user_placeholder).
                                into(mBinding.patientImage);
                    } else {

                        Glide.with(activity).
                                load(result.getString("Data")).
                                placeholder(R.drawable.ic_user_placeholder).
                                error(R.drawable.ic_user_placeholder).
                                into(mBinding.patientImage);
                    }
                    setData();


                } catch (Exception e) {
                    e.printStackTrace();
                    setData();

                }
            }

            @Override
            public void onError(JSONObject result) {
//                hud.dismiss();
                setData();

            }
        });
    }


    private void getDrugImage(String ndc) {
//        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.DrugImagePath + "?ndc=" + ndc, new JSONObject(), API.DrugImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
//                hud.dismiss();
                try {
                    imageUrl = result.getString("Data");
                    Log.e("imageUrl:", "onSuccess: " + imageUrl);
                    if (result.getString("Data").equalsIgnoreCase("")) {
                        Picasso.get().load(R.drawable.ic_place_holder)
                                .placeholder(R.drawable.ic_place_holder)
                                .error(R.drawable.ic_place_holder)
                                .into(mBinding.imgDrug);
                    } else {
                        Picasso.get().load(result.getString("Data"))
                                .placeholder(R.drawable.ic_place_holder)
                                .error(R.drawable.ic_place_holder)
                                .into(mBinding.imgDrug);
                    }
                } catch (Exception e) {
                    Log.e("imageUrlEx:", "onSuccess: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
//                hud.dismiss();
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

    /*image populating*/
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

    private String getDateTime() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void InsertSometingwantErrorLog(String message, String date, String time) {
//        hud.show();
        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("UserID", MyApplication.getPrefranceData("UserID"));
            mainObj.put("DateAndTime", getDateTime());
            mainObj.put("PatientID", patientId);
            mainObj.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
            mainObj.put("DivceName", Build.MANUFACTURER + " " + Build.DEVICE);
            mainObj.put("ModelNo", Build.MODEL);
            mainObj.put("DeviceVersion", Build.VERSION.RELEASE);
            mainObj.put("Message", message);
            mainObj.put("ScaningValue", QRcodeformat);
            mainObj.put("Appversion", MyApplication.getPrefranceData("Version"));//
            mainObj.put("DoseDate", date);
            mainObj.put("DoseTime", time);


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
