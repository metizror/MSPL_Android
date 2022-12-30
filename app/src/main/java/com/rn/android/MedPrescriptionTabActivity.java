package com.rn.android;

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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.ActivityMedPrescriptionTabBinding;
import com.metiz.pelconnect.databinding.DialogDiscontinueBinding;
import com.metiz.pelconnect.databinding.DialogDobimgShowAlertBinding;
import com.metiz.pelconnect.databinding.DialogShowAlertBinding;
import com.rn.android.Adapter.AddedMedpassAdapter;
import com.rn.android.Adapter.DiscontinueMedpassAdapter;
import com.rn.android.Adapter.MedPassPRNAdapter;
import com.rn.android.Adapter.MedPassPrescriptionAdapter;
import com.rn.android.Adapter.MedTakenPrescriptionAdapter;

import com.rn.android.dialog.AlloyHospitalDialog;
import com.rn.android.model.AlloyHospital;
import com.rn.android.model.ChangedPrescriptionModel;
import com.rn.android.model.GetGivenMedPassPrescriptionModel;
import com.rn.android.model.MedPassPrnDetailModel;
import com.rn.android.model.ModelMessPrescription;
import com.rn.android.model.ModelTakenPrescription;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.retrofit.ApiClient;
import com.rn.android.retrofit.ApiInterface;
import com.rn.android.util.MyReceiver;
import com.rn.android.util.PreferenceHelper;
import com.rn.android.util.TouchImageView;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedPrescriptionTabActivity extends BaseActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
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
    //Context context;
   /* @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_tab_taking_drug_list)
    TextView tvTabTakingDrugList;
    @BindView(R.id.no_data_tv)
    TextView no_data_tv;
    @BindView(R.id.tv_tab_prn_drug)
    TextView tvTabPrnDrug;
    @BindView(R.id.tv_tab_drug_history)
    TextView tvTabDrugHistory;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_birthdate)
    TextView txtBirthdate;
    @BindView(R.id.ll_scanner)
    LinearLayout llScanner;
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.rv_mess_prescription_list)
    RecyclerView rvMessPrescriptionList;
    @BindView(R.id.rv_mess_prescription_list_prn)
    RecyclerView rvMessPrnList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.swipe_refresh_prn)
    SwipeRefreshLayout swipe_refresh_prn;
    @BindView(R.id.ll_remaining)
    LinearLayout llRemaining;
    @BindView(R.id.search_history)
    SearchView searchHistory;
    @BindView(R.id.rv_taken_prescription_list)
    RecyclerView rvTakenPrescriptionList;
    @BindView(R.id.swipe_refresh_history)
    SwipeRefreshLayout swipeRefreshHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.patient_image)
    CircleImageView patientImage;
    @BindView(R.id.txt_alloy_hospital)
    TextView txt_alloy_hospital;
    @BindView(R.id.ll_prn)
    LinearLayout llPrn;
    @BindView(R.id.search_prn)
    SearchView searchPrn;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.ll_search_history)
    LinearLayout ll_search_history;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;*/
    String versionRelease = Build.VERSION.RELEASE;
    List<ModelMessPrescription> modelMessPrescriptionList = new ArrayList<>();
    List<ModelMessPrescription> modelMessPrescriptionList1 = new ArrayList<>();
    List<AlloyHospital.LOAHospital> loaHospitalList = new ArrayList<>();
    List<MedPassPrnDetailModel.DataBean.ObjpatientdetailsBean> modelMessPrescriptionListPrn = new ArrayList<>();
    List<MedPassPrnDetailModel.DataBean> modelMessPrescriptionListPrn1 = new ArrayList<>();
    List<String> elephantList = new ArrayList<>();
    List<GetGivenMedPassPrescriptionModel.DataBean.ModelTakenPrescription> modelTakenPrescriptionList = new ArrayList<>();
    List<ModelTakenPrescription> missTakenPrescriptionList = new ArrayList<>();
    MedPassPrescriptionAdapter medPassPrescriptionAdapter;
    MedPassPRNAdapter medPassPRNAdapter;
    MedTakenPrescriptionAdapter medTakenPrescriptionAdapter;
    int PatientId = 0;
    int MedpassID = 0;
    String url = "";
    String time = "";
    String dosetime = "";
    int missCount = 0;
    int listSize = 0;
    int missDoseCount = 0;
    boolean isWrongDialog = false;
    boolean isWrongPatient = false;
    boolean isDiscontinue = false;
    boolean isShowDialog = false;
    boolean isListEmpty = false;
    boolean Is_PrescriptionImg;
    String missDoseTime = "";
    String patientName = "";
    //AlertDialog alert;
    String patient_fName = "";
    String multiPack;
    String patient_lName = "";
    String MissDoseMsg = "";
    String Gender = "";
    String Gender_full = "";
    String BirthDate = "";
    String Msg = "";
    String QRcodeformat = "";
    String imageUrl = "";
    String ndc = "";
    String msg1 = "";
    String massageDialog = "";
    ApiInterface apiService;
    //AlertDialog alertDialog = null;
    private ActivityMedPrescriptionTabBinding mBinding;
    private BroadcastReceiver MyReceiver = null;

    private BroadcastReceiver checkNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getExtras().getString("status");
            if (status.equalsIgnoreCase("true")) {
                if (PreferenceHelper.getString("CHECK_NETWORK_STATUS", "true").equalsIgnoreCase("false")) {
                    if (dialogSHowAlert != null) {
                        if (dialogSHowAlert.isShowing()) {
                            dialogSHowAlert.dismiss();
                            dialogSHowAlert = null;
                        }
                    }
                    PreferenceHelper.putString("CHECK_NETWORK_STATUS", "true");
                    getLoaHospitalApi();
                }
            } else {

            }
            //Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }
    };

    private int SCANNED_BARCODE_ID = 0;
    private boolean isLOAHospital;
    private boolean isLOADialogShown = false;
    private boolean isDialogShown = false;
    private boolean isPRNDialogShown = false;
    private boolean isCurrentMedPassDialogShown = false;
    private boolean isHistoryDialogShown = false;
    private boolean isDialogShownMiss = false;
    private boolean fromDetail = false;
    private KProgressHUD hud;
    private DiscontinueMedpassAdapter discontinueMedpassAdapter;
    private AddedMedpassAdapter addedMedpassAdapter;
    private List<ChangedPrescriptionModel.DataBean> dataBeanDiscontinueList = new ArrayList<>();
    private List<ChangedPrescriptionModel.DataBean> dataBeanAddedList = new ArrayList<>();
    private DialogDiscontinueBinding mDialogDiscontinueBinding;
    private Dialog dialogDiscontinue;
    private DialogShowAlertBinding mDialogShowAlertBinding;
    private DialogDobimgShowAlertBinding mDialogShowAlertBinding1;
    private Dialog dialogSHowAlert;
    private Dialog dialogSHowAlert1;

    public static boolean checkAndRequestPermissions(final Activity context) {
        int ExtstorePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
        mBinding = DataBindingUtil.setContentView(activity, R.layout.activity_med_prescription_tab);
//        ButterKnife.bind(this);
        setTitle("Prescription");
        closeKeyboard();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBinding.llSearch.getWindowToken(), 0);

        MyReceiver = new MyReceiver();

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false);

        initRecyclerView();
        PatientId = getIntent().getIntExtra("PatientId", 0);
        Log.e("TAG", "onCreate: name" + PatientId);
        patientName = getIntent().getStringExtra("name");
        patient_fName = getIntent().getStringExtra("fname");
        patient_lName = getIntent().getStringExtra("lname");
        Gender = getIntent().getStringExtra("gender");
        Gender_full = getIntent().getStringExtra("gender_full");
        BirthDate = getIntent().getStringExtra("BirthDate");
        //isLOAHospital = getIntent().getBooleanExtra("isLOAHospital", false);
        fromDetail = getIntent().getBooleanExtra("fromDetail", false);
        Is_PrescriptionImg = getIntent().getBooleanExtra("Is_PrescriptionImg", false);
        ndc = getIntent().getStringExtra("ndc");
        Log.e("TAG", "onCreate: " + PatientId);
        /*if (isLOAHospital){
            txt_alloy_hospital.setVisibility(View.GONE);
            openAlertDialog3();
        } else {
            txt_alloy_hospital.setVisibility(View.VISIBLE);
            openAlertDialog1();
        }*/
        isDialogShown = false;
        isDialogShownMiss = false;
//      getPatientImage(ndc);
        mBinding.txtName.setText(patientName);
        mBinding.txtGender.setText(Gender);
        mBinding.txtBirthdate.setText(BirthDate);
        getUserImage(PatientId);
        mBinding.txtAlloyHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelMessPrescriptionList.size() > 0) {
                    dosetime = modelMessPrescriptionList.get(0).getDose_time();
                    Log.e("TAG", "onClick:dosetime " + dosetime);
                    new AlloyHospitalDialog(context, loaHospitalList, PatientId, patientName, this::isDrugShiftedToLoa, dosetime).show();
                } else {
//                    Toast.makeText(context, "blank", Toast.LENGTH_SHORT).show();
//                    showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");
//                    new AlloyHospitalDialog(context, loaHospitalList, PatientId, patientName, this::isDrugShiftedToLoa).show();
                    checkMissDoesGetTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"), msg1);
                }
            }

            public void isDrugShiftedToLoa(boolean isLoaShifted) {
                if (isLoaShifted) {
                    Log.e("TAG", "isDrugShiftedToLoa: 308");
                    getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                }

            }
        });
        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("TAG", "isDrugShiftedToLoa: 317");
                getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
            }
        });
        mBinding.swipeRefreshPrn.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPRNList(MyApplication.getPrefranceDataInt("ExFacilityID"));
            }
        });

        mBinding.swipeRefreshHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
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
                        if (mBinding.rvMessPrescriptionList.getAdapter() != null) {
                            newText.replace(",", "");
                            ((MedPassPrescriptionAdapter) mBinding.rvMessPrescriptionList.getAdapter()).filter(newText);
                        }
                    } else {
                        ((MedPassPrescriptionAdapter) mBinding.rvMessPrescriptionList.getAdapter()).filter("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });

        mBinding.tvTabTakingDrugList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_tab_left));
                mBinding.tvTabTakingDrugList.setTextColor(Color.WHITE);
                mBinding.tvTabPrnDrug.setTextColor(Color.BLACK);
                mBinding.tvTabPrnDrug.setBackground(getResources().getDrawable(R.drawable.middle_border_white));
                mBinding.tvTabDrugHistory.setTextColor(Color.BLACK);
                mBinding.tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.right_border_white));
                isWrongDialog = false;
                Log.e("TAG", "isDrugShiftedToLoa: 368");
                getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                mBinding.llRemaining.setVisibility(View.VISIBLE);
                mBinding.txtAlloyHospital.setVisibility(View.VISIBLE);
                mBinding.llPrn.setVisibility(View.GONE);
                mBinding.llHistory.setVisibility(View.GONE);
                mBinding.searchHistory.clearFocus();
                mBinding.search.clearFocus();
                mBinding.searchPrn.clearFocus();
                closeKeyboard();
            }
        });

        mBinding.tvTabPrnDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_with_radius));
                mBinding.tvTabTakingDrugList.setTextColor(Color.BLACK);
                mBinding.tvTabPrnDrug.setTextColor(Color.WHITE);
                mBinding.tvTabPrnDrug.setBackground(getResources().getDrawable(R.drawable.middle_tab_blue));
                mBinding.tvTabDrugHistory.setTextColor(Color.BLACK);
                mBinding.tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.right_border_white));
                mBinding.llRemaining.setVisibility(View.GONE);
                mBinding.llPrn.setVisibility(View.VISIBLE);
                mBinding.txtAlloyHospital.setVisibility(View.GONE);
                mBinding.llHistory.setVisibility(View.GONE);
                mBinding.searchPrn.setQueryHint("Search Drug Or Rx Number");
                mBinding.searchPrn.setIconifiedByDefault(false);
                mBinding.searchPrn.setIconified(false);
                mBinding.searchPrn.setVisibility(View.GONE);
                mBinding.llPrn.setVisibility(View.GONE);
                mBinding.searchHistory.clearFocus();
                mBinding.search.clearFocus();
                mBinding.searchPrn.clearFocus();
                closeKeyboard();
                mBinding.searchHistory.clearFocus();
                mBinding.search.clearFocus();
                mBinding.searchPrn.clearFocus();
                closeKeyboard();
                getPRNList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                mBinding.searchPrn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        try {
                            if (!TextUtils.isEmpty(newText.trim())) {
                                if (mBinding.rvMessPrescriptionListPrn.getAdapter() != null) {
                                    newText.replace(",", "");
                                    ((MedPassPRNAdapter) mBinding.rvMessPrescriptionListPrn.getAdapter()).filter(newText);
                                }
                            } else {
                                ((MedPassPRNAdapter) mBinding.rvMessPrescriptionListPrn.getAdapter()).filter("");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                });
            }
        });

        mBinding.tvTabDrugHistory.setOnClickListener(v -> {
            mBinding.tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.squre_tab_right));
            mBinding.tvTabDrugHistory.setTextColor(Color.WHITE);
            mBinding.tvTabPrnDrug.setBackground(getResources().getDrawable(R.drawable.middle_border_white));
            mBinding.tvTabPrnDrug.setTextColor(Color.BLACK);
            mBinding.tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_with_radius));
            mBinding.tvTabTakingDrugList.setTextColor(Color.BLACK);
//            if (isHistoryDialogShown) {
                mBinding.searchHistory.clearFocus();
                mBinding.search.clearFocus();
                mBinding.searchPrn.clearFocus();
                closeKeyboard();
                getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
           /* } else {
                historyVerificationDialog("4");
            }*/
            mBinding.searchHistory.setQueryHint("Search Drug Or Rx Number");
            mBinding.searchHistory.setIconifiedByDefault(false);
            mBinding.searchHistory.setIconified(false);
            mBinding.searchHistory.setVisibility(View.GONE);
            mBinding.llSearchHistory.setVisibility(View.GONE);
            mBinding.llRemaining.setVisibility(View.GONE);
            mBinding.llPrn.setVisibility(View.GONE);
            mBinding.txtAlloyHospital.setVisibility(View.GONE);
            mBinding.llHistory.setVisibility(View.VISIBLE);
            mBinding.searchHistory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    try {
                        if (!TextUtils.isEmpty(newText.trim())) {
                            if (mBinding.rvTakenPrescriptionList.getAdapter() != null) {
                                newText.replace(",", "");
                                ((MedTakenPrescriptionAdapter) mBinding.rvTakenPrescriptionList.getAdapter()).filter(newText);
                            }
                        } else {
                            ((MedTakenPrescriptionAdapter) mBinding.rvTakenPrescriptionList.getAdapter()).filter("");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            });
        });

        mBinding.llScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogSHowAlert == null) {
                    if (hud != null) {
                        if (!hud.isShowing()) {
                            if (hasPermissions(context, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                setBarcode("Scan QR Code");
                            }
                        }
                    } else {
                        if (hasPermissions(context, PERMISSIONS)) {
                            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
                        } else {
                            setBarcode("Scan QR Code");
                        }
                    }
                }
            }
        });
        mBinding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromDetail) {
                    Intent intent = new Intent(context, PatientListActivityNew.class);
                    intent.putExtra("medPass", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        });

        broadcastIntent();

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
                setBarcode("Scan QR Code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLoaHospitalApi() {
        // showProgressDialog(this);
        hud.show();
        NetworkUtility.getJSONObjectRequest(API.GetLoaHospitalNames, new JSONObject(), API.GetLoaHospitalNames, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    if (result != null) {
                        if (result.has("ResponseStatus") && result.getString("ResponseStatus").equalsIgnoreCase(String.valueOf(1))) {
                            Type listType = new TypeToken<List<AlloyHospital.LOAHospital>>() {
                            }.getType();

                            Log.d("TAG", "onSuccess: " + result.getJSONArray("Data").length());
//
                            if (result.getJSONArray("Data").length() <= 0) {

                            /*alertDialog.setMessage("Data not available!");
                            alertDialog.setCancelable(false);
                            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            if (alertDialog.isShowing()) {
                                alertDialog.dismiss();
                            }
                            alertDialog.show();*/
                                hud.dismiss();
                                showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");
                            } else {
                                loaHospitalList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                                Log.d("TAG", "loaHospitalList size: " + loaHospitalList);
                                Log.e("TAG", "isDrugShiftedToLoa: 565");

                                if (Utils.checkInternetConnection(context)) {
                                    if (mBinding.llRemaining.getVisibility() == View.VISIBLE) {
                                        Log.e("getPrescriptionList:", "onResume: ");
                                        getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                                    } else if (mBinding.llPrn.getVisibility() == View.VISIBLE) {
                                        getPRNList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                                    } else if (mBinding.llHistory.getVisibility() == View.VISIBLE) {
                                        Log.e("getTakenPrescription:", "onResume: ");
                                        getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
                                    }
                                }
                            }
                        }
                    } else {
                        InsertSometingwantErrorLog("Something Went Wrong!", elephantList.get(1), convertMilitaryTime(elephantList.get(2).substring(0, 4)));
                    }
                } catch (Exception ex) {
                    hud.dismiss();
                    ex.printStackTrace();
                    /*alertDialog.setMessage("Data not available!");
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    alertDialog.show();*/
                    showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");

                }
            }

            @Override
            public void onError(JSONObject result) {
                //          dismissProgressDialog();
                hud.dismiss();
                /*alertDialog.setMessage("Data not available!");
                alertDialog.setCancelable(false);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                alertDialog.show();*/
                showAlertDialog(context.getResources().getString(R.string.data_not_available), "", "", "OK", "3");
                InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong), elephantList.get(1), convertMilitaryTime(elephantList.get(2).substring(0, 4)));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void openAlertDialog1(String check) {
      /*  DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        openAlertDialog2();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        finish();
                        break;

                }
            }
        };
       *//* TextView textView = new TextView(context);
        textView.setText("Verify information");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);*//*
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context,R.style.AlertDialogTheme);
        builder.setCancelable(false);
//        builder.setCustomTitle(textView);
        builder.setMessage("Did you verify patient's name and DOB?\n\n" + "Patient - " + Html.fromHtml("<b>" + patientName + "</b>") + "\n" + "DOB -       " + Html.fromHtml("<b>" + BirthDate + "</b>")).
                setPositiveButton("YES", dialogClickListener)
                .setNegativeButton("NO", dialogClickListener);
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        builder.show();*/
        //showProgressDialog(MedPrescriptionTabActivity.this);

            /*NetworkUtility.makeJSONObjectRequest(API.PatientImagePath + "?patient_id=" + PatientId, new JSONObject(), API.PatientImagePath, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {

                       *//* Picasso.get().load(result.getString("Data"))
                                .placeholder(R.drawable.ic_user_placeholder)
                                .error(R.drawable.ic_user_placeholder)
                                .into(mBinding.patientImage);*//*
                        url = result.getString("Data");
                        Log.e("TAG", "onCreate: name >>>" + url );


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onError(JSONObject result) {
                    //dismissProgressDialog();
                }
            });
*/
        showAlertDialogimage("Did you verify patient's picture?\n\n" + "Patient - " + Html.fromHtml("<b>" + patientName + "</b>") + "\n", "Verify Person", "No", "Yes", check);
        isCurrentMedPassDialogShown = true;
    }

    @SuppressLint("SetTextI18n")
    private void prnVerificationDialog(String check) {
        showAlertDialogimage("Did you verify patient's picture?\n\n" + "Patient - " + Html.fromHtml("<b>" + patientName + "</b>") + "\n", "Verify Person", "No", "Yes", check);
        isPRNDialogShown = true;
    }

    private void historyVerificationDialog(String check) {
        showAlertDialogimage("Did you verify patient's picture?\n\n" + "Patient - " + Html.fromHtml("<b>" + patientName + "</b>") + "\n", "Verify Person", "No", "Yes", check);
        isHistoryDialogShown = true;
    }

    private void openAlertDialog2() {
       /* DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    getChangePrescriptionByPatientId();
                    isDialogShown = true;
//                    isDialogShownMiss = true;
                    if (missCount > 0) {
                        openAlertDialog3();
                    }

                }
            }
        };
        TextView textView = new TextView(context);
        textView.setText("Note");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context,R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setCustomTitle(textView);
        builder.setMessage("Please remember to take vitals, if necessary.").setPositiveButton("OK", dialogClickListener);
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        builder.show();*/
        showAlertDialog(context.getResources().getString(R.string.please_remember_to_take_vitals_if_necessary), context.getResources().getString(R.string.note), "", "Ok", "2");
    }

    private void openAlertDialog3() {
      /*  DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    dialog.dismiss();
                }
            }
        };
        TextView textView = new TextView(context);
        textView.setText("Missdose Alert");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setBackgroundResource(R.color.colorPrimary);
        textView.setTextColor(Color.WHITE);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context,R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setCustomTitle(textView);
        builder.setMessage("You missed prior medpass for " + time + ". Would you like to administer these meds now ?")
                .setPositiveButton("OK", dialogClickListener);
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        builder.show();*/
        showAlertDialog("You missed prior medpass for " + time + ".", "Note", "", "OK", "3");
    }

    private void getChangePrescriptionByPatientId() {

        hud.show();
        Call<ChangedPrescriptionModel> call = apiService.GetChangedPrescriptionByPatient(PatientId);
        call.enqueue(new Callback<ChangedPrescriptionModel>() {
            @SuppressLint({"DefaultLocale", "LongLogTag"})
            @Override
            public void onResponse(@NonNull Call<ChangedPrescriptionModel> call, @NonNull Response<ChangedPrescriptionModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    Log.e("getChangePrescriptionByPatientId:", "onResponse:" + response.body().getMessage());
                    if (!MyApplication.getPrefranceDataBoolean("isShowDcMedDialog")) {
                        if (response.body().getData().size() > 0) {
                            StringBuilder disContinueMsg = new StringBuilder();
                            StringBuilder medChangeMsg = new StringBuilder();

                            List<ChangedPrescriptionModel.DataBean> dataBeanList = response.body().getData();
                            dataBeanDiscontinueList.clear();
                            dataBeanAddedList.clear();
                            for (ChangedPrescriptionModel.DataBean dataBean : dataBeanList) {
                                if (dataBean.getIs_active().replace(" ", "").equalsIgnoreCase("N")) {
                                    dataBeanDiscontinueList.add(dataBean);
                                } else if (dataBean.getIs_active().replace(" ", "").equalsIgnoreCase("Y")) {
                                    dataBeanAddedList.add(dataBean);
                                }
                            }
                            if (dataBeanList.size() > 0) {
                                openDiscontinueDialog();
                            }

                        } else {
//                            mBinding.txtAlloyHospital.setEnabled(false);
                        }
                    } else {
                        Log.e("Dialog:", "onResponse: " + "Dialog Already showed");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChangedPrescriptionModel> call, Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
            }
        });
    }

    private void openDiscontinueDialog() {
       /* ChangedPrescriptionModel.DataBean dataBean = new ChangedPrescriptionModel.DataBean();
        dataBean.setDrug("ASPIRIN 81 mg");
        dataBean.setColor("orange");
        dataBean.setShape("round");
        dataBean.setImprint("PH 034");
        dataBean.setDrugImage("http://apitaskmanagement.metizcloud.in/SignatureData/2022/07/637934074506842345_Drug0.jpg");
        dataBean.setIs_active("N");

        ChangedPrescriptionModel.DataBean dataBean2 = new ChangedPrescriptionModel.DataBean();
        dataBean2.setDrug("ASPIRIN 81 mg");
        dataBean2.setColor("orange");
        dataBean2.setShape("round");
        dataBean2.setImprint("PH 034");
        dataBean2.setDrugImage("http://apitaskmanagement.metizcloud.in/SignatureData/2022/07/637934074506842345_Drug0.jpg");
        dataBean2.setIs_active("N");
        dataBeanDiscontinueList.add(dataBean);
        dataBeanDiscontinueList.add(dataBean2);

        ChangedPrescriptionModel.DataBean dataBean1 = new ChangedPrescriptionModel.DataBean();
        dataBean1.setDrug("ASPIRIN 81 mg");
        dataBean1.setColor("orange");
        dataBean1.setShape("round");
        dataBean1.setImprint("PH 034");
        dataBean1.setDrugImage("http://apitaskmanagement.metizcloud.in/SignatureData/2022/07/637934074506842345_Drug0.jpg");
        dataBean1.setIs_active("Y");

        ChangedPrescriptionModel.DataBean dataBean3 = new ChangedPrescriptionModel.DataBean();
        dataBean3.setDrug("ASPIRIN 81 mg");
        dataBean3.setColor("orange");
        dataBean3.setShape("round");
        dataBean3.setImprint("PH 034");
        dataBean3.setDrugImage("http://apitaskmanagement.metizcloud.in/SignatureData/2022/07/637934074506842345_Drug0.jpg");
        dataBean3.setIs_active("Y");

        dataBeanAddedList.add(dataBean1);
        dataBeanAddedList.add(dataBean3);*/

//        dialogDiscontinue = new Dialog(context,R.style.AppTheme);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialogDiscontinue = new Dialog(context);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mDialogDiscontinueBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_discontinue, null, false);
        dialogDiscontinue.setContentView(mDialogDiscontinueBinding.getRoot());
//                            dialogDiscontinue.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialogDiscontinue.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogDiscontinue.getWindow().setStatusBarColor(context.getResources().getColor(R.color.white));
        dialogDiscontinue.setCancelable(true);
        dialogDiscontinue.setCanceledOnTouchOutside(false);

        mDialogDiscontinueBinding.tvAddedDrug.setVisibility(View.GONE);
        mDialogDiscontinueBinding.rvAddedMedPass.setVisibility(View.GONE);
        mDialogDiscontinueBinding.tvDcDrug.setVisibility(View.GONE);
        mDialogDiscontinueBinding.rvDiscontinueMedPass.setVisibility(View.GONE);

        if (dataBeanDiscontinueList.size() > 0) {
            mDialogDiscontinueBinding.tvDcDrug.setVisibility(View.VISIBLE);
            mDialogDiscontinueBinding.rvDiscontinueMedPass.setVisibility(View.VISIBLE);
        }
        if (dataBeanAddedList.size() > 0) {
            mDialogDiscontinueBinding.tvAddedDrug.setVisibility(View.VISIBLE);
            mDialogDiscontinueBinding.rvAddedMedPass.setVisibility(View.VISIBLE);
        }
        //RecyclerView.LayoutManager lm = new GridLayoutManager(context, 2);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mDialogDiscontinueBinding.rvDiscontinueMedPass.setHasFixedSize(true);
        mDialogDiscontinueBinding.rvDiscontinueMedPass.setLayoutManager(lm);
        mDialogDiscontinueBinding.rvDiscontinueMedPass.setItemAnimator(new DefaultItemAnimator());
        discontinueMedpassAdapter = new DiscontinueMedpassAdapter(activity, context, dataBeanDiscontinueList, new DiscontinueMedpassAdapter.OnViewClick() {
            @Override
            public void onItemViewClick(String position, ChangedPrescriptionModel.DataBean data) {
//                                    dialogDiscontinue.dismiss();
                openImageDrugDialog(data.getDrugImage());
            }
        });
        mDialogDiscontinueBinding.rvDiscontinueMedPass.setAdapter(discontinueMedpassAdapter);

        mDialogDiscontinueBinding.rvAddedMedPass.setHasFixedSize(true);
        RecyclerView.LayoutManager lmt = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mDialogDiscontinueBinding.rvAddedMedPass.setLayoutManager(lmt);
        mDialogDiscontinueBinding.rvAddedMedPass.setItemAnimator(new DefaultItemAnimator());
        addedMedpassAdapter = new AddedMedpassAdapter(activity, context, dataBeanAddedList, new AddedMedpassAdapter.OnViewClick() {
            @Override
            public void onItemViewClick(String position, ChangedPrescriptionModel.DataBean data) {
//                                    dialogDiscontinue.dismiss();
                openImageDrugDialog(data.getDrugImage());
            }
        });
        mDialogDiscontinueBinding.rvAddedMedPass.setAdapter(addedMedpassAdapter);
        mDialogDiscontinueBinding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDiscontinue.dismiss();
            }
        });
        dialogDiscontinue.show();
    }

    private void openImageDrugDialog(String imageURL) {
        final Dialog dialog = new Dialog(context);
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

        Glide.with(activity).load(imageURL).placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder).into(dialog_image);
        drawee_image.setVisibility(View.GONE);
        Uri uri = Uri.parse(imageURL);
//        Uri uri = Uri.parse("http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg");
        drawee_image.setImageURI(uri);
        dialog.show();
    }

    private void openAlertDialog4() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        isLOADialogShown = true;
                        //finish();
                        break;
                }
            }
        };

        /*AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setMessage("Current Medpass is given.").setPositiveButton("OK", dialogClickListener).show();*/
        showAlertDialog(context.getResources().getString(R.string.current_medpass_is_given), "", "", "OK", "3");

    }

    /*@OnClick(R.id.txt_alloy_hospital)
    void onAlloyHospitalClicked() {
        new AlloyHospitalDialog(context, loaHospitalList, PatientId, patientName, this::isDrugShiftedToLoa).show();
    }*/

    private void showAlertDialog(String message, String title, String negativeBtn, String positiveBtn, String check) {
        mBinding.searchHistory.clearFocus();
        mBinding.search.clearFocus();
        mBinding.searchPrn.clearFocus();
        closeKeyboard();
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
        Log.e("TAG", "showAlertDialog>>>>: " + title);
        Log.e("TAG", "showAlertDialog????: " + message);
        Log.e("TAG", "showAlertDialog: " + check);

        mDialogShowAlertBinding.imgalert.setVisibility(View.GONE);
        mDialogShowAlertBinding.tvTitle.setVisibility(View.GONE);
        mDialogShowAlertBinding.canleBtn.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(title)) {
            mDialogShowAlertBinding.tvTitle.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(negativeBtn)) {
            mDialogShowAlertBinding.canleBtn.setVisibility(View.VISIBLE);
        }
        mDialogShowAlertBinding.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            dialogSHowAlert = null;
            if (check.equalsIgnoreCase("1")) {
                finish();
            } else if (check.equalsIgnoreCase("2")) {

            } else if (check.equalsIgnoreCase("3")) {

            } else if (check.equalsIgnoreCase("4")) {

            } else if (check.equalsIgnoreCase("5")) {

            } else if (check.equalsIgnoreCase("6")) {

            }
        });
        mDialogShowAlertBinding.okBtn.setOnClickListener(v -> {
            dialogSHowAlert.dismiss();
            dialogSHowAlert = null;
            if (check.equalsIgnoreCase("1")) {
                openAlertDialog2();
            } else if (check.equalsIgnoreCase("2")) {
                getChangePrescriptionByPatientId();
                isDialogShown = true;
                if (missCount > 0) {
                    openAlertDialog3();
                }
            } else if (check.equalsIgnoreCase("3")) {

            } else if (check.equalsIgnoreCase("4")) {
                getChangePrescriptionByPatientId();
            } else if (check.equalsIgnoreCase("5")) {

            } else if (check.equalsIgnoreCase("6")) {
                if (Utils.checkInternetConnection(context)) {
                    if (mBinding.llRemaining.getVisibility() == View.VISIBLE) {
                        Log.e("getPrescriptionList:", "onResume: ");
                        Log.e("TAG", "isDrugShiftedToLoa: 1115");
                        getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                    } else if (mBinding.llPrn.getVisibility() == View.VISIBLE) {
                        mBinding.searchPrn.clearFocus();
                        getPRNList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                    } else if (mBinding.llHistory.getVisibility() == View.VISIBLE) {
                        Log.e("getTakenPrescription:", "onResume: ");
                        getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
                    }
                }

                Log.e("isOpen:", "onResume: " + isOpen);

                mBinding.search.setQueryHint("Search Drug Or Rx Number");
                mBinding.search.setIconifiedByDefault(false);
                mBinding.search.setIconified(false);
                mBinding.search.setVisibility(View.GONE);
                mBinding.llSearch.setVisibility(View.GONE);
                Log.e("onResume", "onResume");
                mBinding.searchHistory.clearFocus();
                mBinding.search.clearFocus();
                mBinding.searchPrn.clearFocus();
                mBinding.llSearch.clearFocus();
                mBinding.llSearchHistory.clearFocus();
                mBinding.llSearchPrn.clearFocus();
                closeKeyboard();
            }
        });
        dialogSHowAlert.show();

        /*TextView textView = new TextView(context);
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

                }
            });
        }
        builder.setPositiveButton(positiveBtn, (dialog, id) -> {
            dialog.dismiss();
            if (check.equalsIgnoreCase("1")) {
                openAlertDialog2();
            } else if (check.equalsIgnoreCase("2")) {
                getChangePrescriptionByPatientId();
                isDialogShown = true;
//                    isDialogShownMiss = true;
                if (missCount > 0) {
                    openAlertDialog3();
                }
            } else if (check.equalsIgnoreCase("3")) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();*/
    }

    private void showAlertDialogimage(String message, String title, String negativeBtn, String positiveBtn, String check) {

        dialogSHowAlert1 = new Dialog(context);//, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mDialogShowAlertBinding1 = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_dobimg_show_alert, null, false);
        dialogSHowAlert1.setContentView(mDialogShowAlertBinding1.getRoot());
//      dialogDiscontinue.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialogSHowAlert1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogSHowAlert1.getWindow().setStatusBarColor(context.getResources().getColor(R.color.white));
        dialogSHowAlert1.setCancelable(false);
        dialogSHowAlert1.setCanceledOnTouchOutside(false);

        mDialogShowAlertBinding1.tvTitle.setText(title);
        mDialogShowAlertBinding1.tvMessage.setText(message);
       /* Picasso.get().load(url)
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(mDialogShowAlertBinding1.Patientimg);*/

        Glide.with(context).load(url).placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(mDialogShowAlertBinding1.Patientimg);

        mDialogShowAlertBinding1.okBtn.setText(positiveBtn);
        mDialogShowAlertBinding1.canleBtn.setText(negativeBtn);
        Log.e("TAG", "showAlertDialog>>>>: " + title);
        Log.e("TAG", "showAlertDialog????: " + message);
        Log.e("TAG", "showAlertDialog: " + check);

        mDialogShowAlertBinding1.imgalert.setVisibility(View.GONE);
        mDialogShowAlertBinding1.tvTitle.setVisibility(View.GONE);
        mDialogShowAlertBinding1.canleBtn.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(title)) {
            mDialogShowAlertBinding1.tvTitle.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(negativeBtn)) {
            mDialogShowAlertBinding1.canleBtn.setVisibility(View.VISIBLE);
        }
        mDialogShowAlertBinding1.canleBtn.setOnClickListener(v -> {
            dialogSHowAlert1.dismiss();
            dialogSHowAlert1 = null;
            if (check.equalsIgnoreCase("1")) {
                finish();
            } else if (check.equalsIgnoreCase("2")) {
                finish();
            } else if (check.equalsIgnoreCase("3")) {
                finish();
            } else if (check.equalsIgnoreCase("4")) {
                finish();
            }
        });
        mDialogShowAlertBinding1.okBtn.setOnClickListener(v -> {
            dialogSHowAlert1.dismiss();
            dialogSHowAlert1 = null;
            if (check.equalsIgnoreCase("1")) {
                openAlertDialog2();
            } else if (check.equalsIgnoreCase("2")) {
                showAlertDialog(massageDialog, "", "", "OK", "4");
            } else if (check.equalsIgnoreCase("3")) {
                getPRNList(MyApplication.getPrefranceDataInt("ExFacilityID"));
            } else if (check.equalsIgnoreCase("4")) {
                getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
            }
        });
        dialogSHowAlert1.show();

        /*TextView textView = new TextView(context);
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

                }
            });
        }
        builder.setPositiveButton(positiveBtn, (dialog, id) -> {
            dialog.dismiss();
            if (check.equalsIgnoreCase("1")) {
                openAlertDialog2();
            } else if (check.equalsIgnoreCase("2")) {
                getChangePrescriptionByPatientId();
                isDialogShown = true;
//                    isDialogShownMiss = true;
                if (missCount > 0) {
                    openAlertDialog3();
                }
            } else if (check.equalsIgnoreCase("3")) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();*/
    }

    private void initRecyclerView() {//
        mBinding.rvTakenPrescriptionList.setLayoutManager(new LinearLayoutManager(context));
        mBinding.rvMessPrescriptionListPrn.setLayoutManager(new LinearLayoutManager(context));
        mBinding.rvMessPrescriptionList.setLayoutManager(new LinearLayoutManager(context));
//        alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme).create();
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
    }

    private void setBarcode(String msg) {
        unRegistedBroadcast();
        SCANNED_BARCODE_ID = 1;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_128, IntentIntegrator.QR_CODE);
        integrator.setPrompt(msg);
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //   barcodeOpen = 0;
        unRegistedBroadcast();
        Log.e("stop", "stop");

    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegistedBroadcast();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.searchHistory.clearFocus();
        mBinding.search.clearFocus();
        mBinding.searchPrn.clearFocus();
        closeKeyboard();
        broadcastIntent();
        Log.e("TAG=---=>", "onResume: Keyboard close");
        /*if (Utils.checkInternetConnection(MedPrescriptionTabActivity.this)) {
            if (llRemaining.getVisibility() == View.VISIBLE) {
                Log.e("getPrescriptionList:", "onResume: ");
                getPrescriptionList(Application.getPrefranceDataInt("ExFacilityID"));
            } else if (llPrn.getVisibility() == View.VISIBLE) {
                getPRNList(Application.getPrefranceDataInt("ExFacilityID"));

            } else if (llHistory.getVisibility() == View.VISIBLE) {
                Log.e("getTakenPrescription:", "onResume: ");
                getTakenPrescription(Application.getPrefranceDataInt("ExFacilityID"));
            }
        }

        Log.e("isOpen:", "onResume: " + isOpen);

        search.setQueryHint("Search Drug Or Rx Number");
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        search.setVisibility(View.GONE);
        ll_search.setVisibility(View.GONE);
        Log.e("onResume", "onResume");*/
    }

    private void broadcastIntent() {
        LocalBroadcastManager.getInstance(context).registerReceiver(checkNetworkReceiver, new IntentFilter("CHECK_NETWORK_STATUS"));
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
        try {
            mBinding.searchHistory.clearFocus();
            mBinding.search.clearFocus();
            mBinding.searchPrn.clearFocus();
            mBinding.llSearch.clearFocus();
            mBinding.llSearchHistory.clearFocus();
            mBinding.llSearchPrn.clearFocus();
            closeKeyboard();
            if (requestCode == 102) {
                broadcastIntent();
                if (Utils.checkInternetConnection(context)) {
                    if (mBinding.llRemaining.getVisibility() == View.VISIBLE) {
                        Log.e("getPrescriptionList:", "onResume: ");
                        Log.e("TAG", "isDrugShiftedToLoa: 1115");
                        getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                    } else if (mBinding.llPrn.getVisibility() == View.VISIBLE) {
                        mBinding.searchPrn.clearFocus();
                        getPRNList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                    } else if (mBinding.llHistory.getVisibility() == View.VISIBLE) {
                        Log.e("getTakenPrescription:", "onResume: ");
                        getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
                    }
                }

                Log.e("isOpen:", "onResume: " + isOpen);

                mBinding.search.setQueryHint("Search Drug Or Rx Number");
                mBinding.search.setIconifiedByDefault(false);
                mBinding.search.setIconified(false);
                mBinding.search.setVisibility(View.GONE);
                mBinding.llSearch.setVisibility(View.GONE);
                Log.e("onResume", "onResume");
            } else {
                SCANNED_BARCODE_ID = 2;
                broadcastIntent();
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                QRcodeformat = result.toString();
                Log.e("Bar code Result", String.valueOf(result));
                if (result != null) {
                    if (result.getContents() == null) {
                        Log.e("TAG", "onActivityResult: " + result);
//                        isWrongDialog = true;
                        /*final Dialog dialog = new Dialog(this);
                        dialog.setContentView(R.layout.med_expired_dialog);
                        TextView dialogButton = dialog.findViewById(R.id.ok);
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }*/
                        showAlertDialog(context.getResources().getString(R.string.invalid_scan), "Alert", "", "OK", "6");

                    } else {
                        Log.e("result", result.getContents() + "\n" + result.getBarcodeImagePath() + "\n" + result.getFormatName() + "\n" + result.getErrorCorrectionLevel());
                        isOpen = 1;

                        if (result.getContents().startsWith("#")) {
                            if (Utils.checkInternetConnection(context)) {
                                elephantList = Arrays.asList(result.getContents().split(","));
                                Log.e("TAG", "PatientId000: " + PatientId);
                                Log.e("Rx Number0", result.getContents());
                                getInternalIPSPatientID(elephantList.get(0).replace("#", ""), elephantList, result.toString());
                            }

                        } else if (result.getContents().startsWith("-")) {
                            if (Utils.checkInternetConnection(context)) {

                                elephantList = Arrays.asList(result.getContents().split(","));
                                Log.e("TAG", "multiPack " + elephantList.size());
                                if (elephantList.size() > 3) {
                                    Log.e("TAG6", "multiPack " + elephantList);
                                    multiPack = elephantList.get(3);
                                    Log.e("TAG1", "multiPack " + multiPack);
                                } else {
                                    Log.e("TAG5", "multiPack " + elephantList);
                                    Log.e("TAG2", "multiPack " + multiPack);
                                    multiPack = null;
                                }


                                Log.e("TAG", "PatientId111: " + PatientId);
                                Log.e("Rx Number0", result.getContents());
                                //  getPrescriptionFromPillId(elephantList.get(0).replace("-", ""), elephantList);
                                Intent i = new Intent(context, MedPillMuliPrescriptionTabActivity.class);
                                i.putExtra("pillId", elephantList.get(0).replace("-", ""));
                                i.putExtra("date", elephantList.get(1));
                                i.putExtra("time", convertMilitaryTime(elephantList.get(2).substring(0, 4)));
                                Log.e("TAG", "onActivityResultconvertMilitaryTime: "+convertMilitaryTime(elephantList.get(2).substring(0, 4)) );
                                Log.e("TAG", "onActivityResultconvertMilitaryTime: "+elephantList.get(2).substring(0, 4) );
                                i.putExtra("PatientId", PatientId);
                                i.putExtra("name", patientName);
                                i.putExtra("fname", patient_fName);
                                i.putExtra("multiPack", multiPack);
                                i.putExtra("QRcodeformat", elephantList.get(0) + "," + elephantList.get(1) + "," + elephantList.get(2));
            /*            i.putExtra("patient_fName", modelMessPrescriptionList1.get(0).getPatient_first());
                        i.putExtra("patient_lName", modelMessPrescriptionList1.get(0).getPatient_last());*/
                                i.putExtra("lname", patient_lName);
                                i.putExtra("gender", Gender);
                                i.putExtra("gender_full", Gender_full);
                                i.putExtra("isMissDose", missDoseCount >= 1);
//                        i.putExtra("isMissDose", missDoseTime.trim().equalsIgnoreCase(convertMilitaryTime(elephantList.get(2).substring(0, 4)).trim()));
                                i.putExtra("BirthDate", BirthDate);
                                i.putExtra("missDoseCount", missDoseCount);
                                i.putExtra("Is_PrescriptionImg", Is_PrescriptionImg);
                                i.putExtra("isListEmpty", isListEmpty);
                                i.putExtra("MedpassId", MedpassID);
                                startActivityForResult(i, 102);
                            }
                        } else {
                            if (Utils.checkInternetConnection(this)) {
                               /* if (SCANNED_BARCODE_ID == 0) {
                                    RxNumber = result.getContents();
                                    Log.e("Rx Number", result.getContents());
                                    getPrescriptionDetails(RxNumber);
                                } else {*/
                                if (result.getContents() != null) {
                                    if (!result.getContents().isEmpty()) {
                                        getPrescriptionDetails(result.getContents());
                                        Log.e("RxNumber123>>>>>", result.getContents());
                                    } else {
                                        Utils.showToast(activity, "Please Rescan BarCode...", 0);
                                    }
                                } else {
                                    Utils.showToast(activity, "Please ReScan BarCode...", 0);
                                }
//                                }
                            }
                        }
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, intent);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    private void getInternalIPSPatientID(String externalPatientID, List<String> elephantList, String QRcodeformat) {

        Log.e("getInternalIPSPatientID", elephantList.get(1));
        if (elephantList.size() > 3) {
            Log.e("TAG6", "multiPack " + elephantList);
            multiPack = elephantList.get(3);
            Log.e("TAG1", "multiPack " + multiPack);
        } else {
            Log.e("TAG5", "multiPack " + elephantList);
            Log.e("TAG2", "multiPack " + multiPack);
            multiPack = null;
        }
        SimpleDateFormat formatterCurrent = new SimpleDateFormat("yyyy/MM/dd");
        Date d1 = new Date();
        Log.d("TAG 5", formatterCurrent.format(d1));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        hud.show();
        //    showProgressDialog(this);
        NetworkUtility.makeJSONObjectRequest(API.GetInternalIPSPatientID + "?ExternalPatientID=" + externalPatientID, new JSONObject(), API.GetInternalIPSPatientID, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                Log.e("TAG", "onSuccess: " + result);
                try {

                    String id = String.valueOf(result.get("Data"));
                    if (id != null && !id.isEmpty() && !id.equals("null")) {
                        Log.e("abc", "" + result.get("Data"));
//                        Log.e("abcTimeeeeee", "" + convertMilitaryTime("130011111".substring(0, 4)));

                        if (String.valueOf(PatientId).equals(id)) {
                            Intent intent1 = new Intent(getApplicationContext(), MedMultiPrescriptionTabActivity.class);
//                            intent1.putExtra("pillId", elephantList.get(0).replace("-", ""));
                            intent1.putExtra("externalPatientID", elephantList.get(0).replace("#", ""));
                            intent1.putExtra("date", elephantList.get(1));
                            intent1.putExtra("time", convertMilitaryTime(elephantList.get(2).substring(0, 4)));
                            intent1.putExtra("PatientId", PatientId);
                            intent1.putExtra("name", patientName);
                            intent1.putExtra("fname", patient_fName);
                            intent1.putExtra("lname", patient_lName);
                            intent1.putExtra("multiPack", multiPack);
                            intent1.putExtra("gender", Gender);
                            intent1.putExtra("gender_full", Gender_full);
                            intent1.putExtra("BirthDate", BirthDate);
                            intent1.putExtra("QRcodeformat", elephantList.get(0) + "," + elephantList.get(1) + "," + elephantList.get(2));
                            intent1.putExtra("Is_PrescriptionImg", Is_PrescriptionImg);
                            intent1.putExtra("id", id);
                            intent1.putExtra("isListEmpty", isListEmpty);
                            startActivityForResult(intent1, 102);
                        } else {
                            sendLogToBackend(context.getResources().getString(R.string.wrong_patient), elephantList);
                            isWrongDialog = true;
                            /*TextView textView = new TextView(context);
                            textView.setText(context.getResources().getString(R.string.medpass_alert));
                            textView.setPadding(20, 30, 20, 30);
                            textView.setTextSize(20F);
                            textView.setBackgroundResource(R.color.colorPrimary);
                            textView.setTextColor(Color.WHITE);
                            alertDialog.setMessage("Wrong patient!");
                            alertDialog.setCancelable(false);
                            alertDialog.setCustomTitle(textView);
                            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            if (alertDialog.isShowing()) {
                                alertDialog.dismiss();
                            }
                            alertDialog.show();*/
                            showAlertDialog(context.getResources().getString(R.string.wrong_patient), "Medpass Alert", "", "OK", "5");
                        }
                        // GetCurrentMedpassDetailsByPatientID(id);
                    } else {
                        sendLogToBackend("There is no medpass available for this prescription", elephantList);
                        /*alertDialog.setMessage("There is no medpass available for this prescription");
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        alertDialog.show();*/
                        showAlertDialog(context.getResources().getString(R.string.there_is_no_medpass_available_for_this_prescription), "", "", "OK", "3");
                    }
                } catch (Exception ex) {
                    hud.dismiss();
                    Log.e("onException:", Objects.requireNonNull(ex.getMessage()));
                    sendLogToBackend(ex.getMessage(), elephantList);
                    ex.printStackTrace();
                    /*alertDialog.setMessage(context.getResources().getString(R.string.something_went_wrong));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    alertDialog.show();*/
                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "3");
                    InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong), elephantList.get(1), convertMilitaryTime(elephantList.get(2).substring(0, 4)));
                }
            }

            @Override
            public void onError(JSONObject result) {
                sendLogToBackend(result.toString(), elephantList);
                Log.e("deviceName:", "onCreate: " + android.os.Build.MODEL);
                //   dismissProgressDialog();
                hud.dismiss();

                /*alertDialog.setMessage(context.getResources().getString(R.string.something_went_wrong));
                alertDialog.setCancelable(false);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                alertDialog.show();*/
                showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "3");
                InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong), elephantList.get(1), convertMilitaryTime(elephantList.get(2).substring(0, 4)));

            }
        });
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void sendLogToBackend(String message, List<String> elephantList) {
        hud.show();
        // showProgressDialog(this);
        JSONObject mainObj = new JSONObject();

        try {
            mainObj.put("UserID", MyApplication.getPrefranceData("UserID"));
            mainObj.put("PatientID", PatientId);
            mainObj.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
            mainObj.put("DateAndTime", getDateTime());
            mainObj.put("DivceName", Build.MANUFACTURER + " " + Build.DEVICE);
            mainObj.put("ModelNo", Build.MODEL);
            mainObj.put("DeviceVersion", versionRelease);
            mainObj.put("Message", message);//
            mainObj.put("Appversion", MyApplication.getPrefranceData("Version"));//
            mainObj.put("DoseDate", elephantList.get(1));//
            mainObj.put("DoseTime", convertMilitaryTime(elephantList.get(2).substring(0, 4)));//

            mainObj.put("ScaningValue", elephantList.get(0) + "," + elephantList.get(1) + "," + elephantList.get(2));//
            Log.e("TAG", "sendLogToBackend: " + versionRelease + MyApplication.getPrefranceData("UserID") + Build.MANUFACTURER + " " + Build.DEVICE + android.os.Build.MODEL + getDateTime() + message);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        NetworkUtility.makeJSONObjectRequest(API.InsertMedpassLog, mainObj, API.InsertMedpassLog, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                // dismissProgressDialog();
                hud.dismiss();
                try {
                    if (result != null) {
                        Log.e("TAG", "onSuccess: " + result.get("Data"));
                    }

                } catch (Exception ex) {
                    dismissProgressDialog();
                    ex.printStackTrace();
                    /*alertDialog.setMessage(context.getResources().getString(R.string.something_went_wrong));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    alertDialog.show();*/
                    showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "3");
                }
            }

            @Override
            public void onError(JSONObject result) {
                //       dismissProgressDialog();
                hud.dismiss();
                /*alertDialog.setMessage(context.getResources().getString(R.string.something_went_wrong));
                alertDialog.setCancelable(false);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                alertDialog.show();*/
                showAlertDialog(context.getResources().getString(R.string.something_went_wrong), "", "", "OK", "3");
            }
        });
    }

    public String convertMilitaryTime(String time) {

        String time1 = time.substring(0, 2);
        String time2 = time.substring(2, 4);
        int t1;
        Log.e("Time -->", "" + time1);
        if (Integer.parseInt(time1) > 12) {
            t1 = Integer.parseInt(time1) - 12;
            Log.e("T11111", "" + t1);
        } else {
            t1 = Integer.parseInt(time1);
        }
        Log.e("Time t1 -->", "" + t1);
        String timeStr;
        if (Integer.parseInt(time1) >= 12) {
            if (t1 >= 10) {
                timeStr = t1 + ":" + time2 + "PM";
            } else {
                timeStr = "0" + t1 + ":" + time2 + "PM";
            }
            Log.e("Time timeStr if -->", "" + timeStr);


        } else {
            if (t1 >= 10) {
                timeStr = t1 + ":" + time2 + "AM";
            } else {
                timeStr = "0" + t1 + ":" + time2 + "AM";
            }
            Log.e("Time timeStr else -->", "" + timeStr);

        }
        Log.e("timeStr:", "" + timeStr);

        return timeStr;
    }

    private String getFormate(String date) throws ParseException {
        if (date != null && !date.isEmpty()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(date);
            Log.d("Date", String.valueOf(d));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            return new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
        } else {
            return "";
        }
    }

    private void getPrescriptionDetails(String rx) {
        //showProgressDialog(this);
        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.GetMedPassDrugDetailsByRxnumber + "?Rxnumber=" + rx + "&patient_id=" + PatientId, new JSONObject(), API.GetMedPassDrugDetailsByRxnumber, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    // dismissProgressDialog();

                    if (result != null) {
                        Log.e("Api Res", result.toString());

                        isOpen = 0;
                        Type listType = new TypeToken<List<ModelMessPrescription>>() {
                        }.getType();

                        modelMessPrescriptionList1 = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Data").toString(), listType);
                        if (result.getString("ResponseStatus").equalsIgnoreCase("1")) {
                            if (modelMessPrescriptionList1.size() > 0) {
//                                dosetime = modelMessPrescriptionList1.get(0).getDose_time();
                                Intent intent = new Intent(context, MedPrescriptionDetailsActivity.class);
                                intent.putExtra("drug", modelMessPrescriptionList1.get(0).getDrug());
                                if (modelMessPrescriptionList1.get(0).getRx_number() != null) {
                                    intent.putExtra("rxNumber", modelMessPrescriptionList1.get(0).getRx_number());
                                    intent.putExtra("dose_Qty", modelMessPrescriptionList1.get(0).getDose_Qty());

                                } else {
                                    intent.putExtra("rxNumber", modelMessPrescriptionList1.get(0).getPharmacy_order_id());

                                    intent.putExtra("dose_Qty", Double.parseDouble(modelMessPrescriptionList1.get(0).getQty()));
                                }

                                if (modelMessPrescriptionList1.get(0).getSig_detail() != null) {
                                    intent.putExtra("sig_detail", modelMessPrescriptionList1.get(0).getSig_detail());
                                } else {
                                    intent.putExtra("sig_detail", modelMessPrescriptionList1.get(0).getSig_english());
                                }
                                intent.putExtra("dose_time", modelMessPrescriptionList1.get(0).getDose_time());
                                intent.putExtra("isMissDose", missDoseCount >= 1);
                                intent.putExtra("isMissedDose", modelMessPrescriptionList1.get(0).isIsMissedDose());

                          /* if(missDoseTime != null && modelMessPrescriptionList1.get(0).getDose_time() != null){
                               intent.putExtra("isMissDose", missDoseTime.trim().equalsIgnoreCase(modelMessPrescriptionList1.get(0).getDose_time().trim()));
                           }
                           else{
                               intent.putExtra("isMissDose", "");
                           }*/
                                intent.putExtra("missDoseCount", missDoseCount);
                                intent.putExtra("tran_id", modelMessPrescriptionList1.get(0).getTran_id());
                                intent.putExtra("facility_id", modelMessPrescriptionList1.get(0).getFacility_id());
                                intent.putExtra("patientId", PatientId);
                                intent.putExtra("ndc", modelMessPrescriptionList1.get(0).getNdc());
                                intent.putExtra("listSize", listSize);
                                intent.putExtra("sig_code", modelMessPrescriptionList1.get(0).getSig_code());
                                intent.putExtra("patient_lName", modelMessPrescriptionList1.get(0).getPatient_last());
                                intent.putExtra("patient_fName", modelMessPrescriptionList1.get(0).getPatient_first());
                                intent.putExtra("lname", patient_lName);
                                intent.putExtra("fname", patient_fName);
                                intent.putExtra("gender", Gender);
                                intent.putExtra("gender_full", Gender_full);
                                intent.putExtra("IsErly", modelMessPrescriptionList1.get(0).isErly());
                                intent.putExtra("IsLate", modelMessPrescriptionList1.get(0).isLate());
                                intent.putExtra("gender_full", Gender_full);


                                intent.putExtra("BirthDate", BirthDate);

                                intent.putExtra("isPRN", modelMessPrescriptionList1.get(0).getMed_type().replace(" ", "").equalsIgnoreCase("P"));

                                if (modelMessPrescriptionList1.get(0).getPatient_first() != null && modelMessPrescriptionList1.get(0).getPatient_last() != null) {
                                    intent.putExtra("name", modelMessPrescriptionList1.get(0).getPatient_last() + " " + modelMessPrescriptionList1.get(0).getPatient_first());
                                } else {
                                    intent.putExtra("name", patient_lName + " " + patient_fName);
                                }


                                Log.e("There", "There");

                                try {
                                    intent.putExtra("birthDate", getFormate(modelMessPrescriptionList1.get(0).getDOB()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (result.getJSONObject("Data").getString("Msg") != null && !result.getJSONObject("Data").getString("Msg").isEmpty()) {
                                    intent.putExtra("msg", result.getJSONObject("Data").getString("Msg"));
                                }
                                if (modelMessPrescriptionList1.get(0).getGender() != null) {
                                    intent.putExtra("gender", Gender);
                                } else {
                                    intent.putExtra("genderFull", Gender_full);
                                }
                                startActivityForResult(intent, 102);
                            } else {
                                String msg = result.getJSONObject("Data").getString("Msg");
                                if (TextUtils.isEmpty(msg) || msg.equalsIgnoreCase("null")) {
                                    msg = "There is no medpass available for this prescription";
                                }
                                isWrongPatient = true;
                                /*TextView textView = new TextView(context);
                                textView.setText(context.getResources().getString(R.string.medpass_alert));
                                textView.setPadding(20, 30, 20, 30);
                                textView.setTextSize(20F);
                                textView.setBackgroundResource(R.color.colorPrimary);
                                textView.setTextColor(Color.WHITE);
                                alertDialog.setCustomTitle(textView);
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(msg);
                                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        });
                                if (alertDialog.isShowing()) {
                                    alertDialog.dismiss();
                                }
                                alertDialog.show();*/
                                showAlertDialog(msg, context.getResources().getString(R.string.medpass_alert), "", "OK", "3");
                            }
                            Log.e("modelMess:", "onSuccess: " + modelMessPrescriptionList1.size());
                        } else {
                            String msg = result.getJSONObject("Data").getString("Msg");
                            if (TextUtils.isEmpty(msg) || msg.equalsIgnoreCase("null")) {
                                msg = "There is no medpass available for this prescription";
                            }
                            /*TextView textView = new TextView(context);
                            textView.setText(context.getResources().getString(R.string.medpass_alert));
                            textView.setPadding(20, 30, 20, 30);
                            textView.setTextSize(20F);
                            textView.setBackgroundResource(R.color.colorPrimary);
                            textView.setTextColor(Color.WHITE);
                            alertDialog.setCustomTitle(textView);
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(msg);
                            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });
                            if (alertDialog.isShowing()) {
                                alertDialog.dismiss();
                            }
                            alertDialog.show();*/
                            showAlertDialog(msg, context.getResources().getString(R.string.medpass_alert), "", "OK", "3");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("ex:", "onSuccess: " + ex.getMessage());
                    /*alertDialog.setMessage(context.getResources().getString(R.string.something_went_wrong));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    alertDialog.show();*/
                    showAlertDialog(context.getResources().getString(R.string.barcode_scanning_error), "", "", "OK", "3");
                    InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong), elephantList.get(1), convertMilitaryTime(elephantList.get(2).substring(0, 4)));
                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
                //     dismissProgressDialog();
                /*alertDialog.setMessage(context.getResources().getString(R.string.something_went_wrong));
                alertDialog.setCancelable(false);
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                alertDialog.show();*/
                showAlertDialog(context.getResources().getString(R.string.barcode_scanning_error), "", "", "OK", "3");
                InsertSometingwantErrorLog(context.getResources().getString(R.string.something_went_wrong), elephantList.get(1), convertMilitaryTime(elephantList.get(2).substring(0, 4)));

            }
        });
    }

    private void getPrescriptionList(int facilityID) {
        Log.e("Step", "==============3");
//        if (!mBinding.swipeRefresh.isRefreshing())
//            hud.show();
        if (!hud.isShowing()) {
            hud.show();
        }

        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        NetworkUtility.makeJSONObjectRequest(API.GetMedPassDrugDetailsByPatientID + "?FacilityID=" + facilityID + "&PatientID=" + PatientId + "&currdate=" + new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()), new JSONObject(), API.GetMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            //        NetworkUtility.makeJSONObjectRequest(API.GetMedPassDrugDetailsByPatientID + "?FacilityID=" + "348" + "&PatientID=" + "1961317" + "&currdate=07/04/2022" , new JSONObject(), API.GetMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(JSONObject result) {

                hud.dismiss();
               /* if (!isDialogShown) {
                    Log.e("TAG", "onSuccess: Dialog" + String.valueOf(modelMessPrescriptionList.size()));
//                    openAlertDialog1();
                }*/

                try {
                    if (modelMessPrescriptionList.size() > 0) {
                        modelMessPrescriptionList.clear();
                    }
                    if (result != null) {
                        Type listType = new TypeToken<List<ModelMessPrescription>>() {
                        }.getType();

                        modelMessPrescriptionList = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("objdetails").toString(), listType);
                        listSize = modelMessPrescriptionList.size();
                        msg1 = result.getJSONObject("Data").getString("Msg");

                        Log.e("modelMessPrescriptionList:", "onSuccess: " + modelMessPrescriptionList.size());
                        Log.d("TAG", "isLOAHospital  fsdffd v: " + isLOAHospital);


                        if (modelMessPrescriptionList.size() == 0) {
                            isListEmpty = true;
                            if (!isWrongDialog) {
                                Log.e("Size of dialog", String.valueOf(modelMessPrescriptionList.size()));
                                massageDialog = result.getJSONObject("Data").getString("Msg");
                                checkMissDoesGetTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"), result.getJSONObject("Data").getString("Msg"));
                            }
                        } else {
                            if (!isDialogShown) {
                                Log.e("TAG", "onSuccess: Dialog" + isDialogShown);
                                if (!isCurrentMedPassDialogShown) {
                                    openAlertDialog1("1");
                                } else {
                                    openAlertDialog2();
                                }
                                isDialogShown = true;
                            }
                        }
                        /*if (Application.getGson().fromJson(result.getJSONObject("Msg").get("Current medpass is given").toString(),listType)){
                            if (!isDialogShown) {
                                Log.e("TAG", "onSuccess: Dialog" + String.valueOf(modelMessPrescriptionList.size()));
                                openAlertDialog1();
                                isDialogShown = true;
                            }
                        }*/

                        medPassPrescriptionAdapter = new MedPassPrescriptionAdapter(activity, context, modelMessPrescriptionList, Is_PrescriptionImg);
                        mBinding.rvMessPrescriptionList.setAdapter(medPassPrescriptionAdapter);
                        mBinding.search.setVisibility(View.VISIBLE);
                        mBinding.llSearch.setVisibility(View.VISIBLE);
                        mBinding.search.clearFocus();

                        if (mBinding.swipeRefresh.isRefreshing()) {
                            mBinding.swipeRefresh.setRefreshing(false);
                        }
                        if (mBinding.swipeRefreshPrn.isRefreshing()) {
                            mBinding.swipeRefreshPrn.setRefreshing(false);
                        }
                        if (mBinding.swipeRefreshHistory.isRefreshing()) {
                            mBinding.swipeRefreshHistory.setRefreshing(false);
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }

            @Override
            public void onError(JSONObject result) {

                hud.dismiss();
                mBinding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void getPRNList(int facilityID) {
        Log.e("Step", "==============4");
//        if (!mBinding.swipeRefreshPrn.isRefreshing())
//            hud.show();
        if (!hud.isShowing()) {
            hud.show();
        }


        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        Call<MedPassPrnDetailModel> call = apiService.GetMedPassPRNDrugDetailsByPatientID(PatientId);
        call.enqueue(new Callback<MedPassPrnDetailModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MedPassPrnDetailModel> call, @NonNull Response<MedPassPrnDetailModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    try {
                        int response_code = response.body().getResponseStatus();
                        Log.e("onResponse111:", "tag111: " + response.body());
                        if (modelTakenPrescriptionList.size() > 0) {
                            modelTakenPrescriptionList.clear();
                        }
                        if (response_code == 1) {
                            modelMessPrescriptionListPrn = response.body().getData().getObjpatientdetails();

                            medPassPRNAdapter = new MedPassPRNAdapter(activity, context, modelMessPrescriptionListPrn, patient_fName, patient_lName, BirthDate, Gender_full, Is_PrescriptionImg);
                            mBinding.rvMessPrescriptionListPrn.setAdapter(medPassPRNAdapter);
                            mBinding.searchPrn.setVisibility(View.VISIBLE);
                            mBinding.llPrn.setVisibility(View.VISIBLE);

                            mBinding.searchPrn.clearFocus();
                            if (mBinding.swipeRefresh.isRefreshing()) {
                                mBinding.swipeRefresh.setRefreshing(false);
                            }
                            if (mBinding.swipeRefreshPrn.isRefreshing()) {
                                mBinding.swipeRefreshPrn.setRefreshing(false);
                            }
                            if (mBinding.swipeRefreshHistory.isRefreshing()) {
                                mBinding.swipeRefreshHistory.setRefreshing(false);
                            }

                            Log.e("Size", String.valueOf(modelMessPrescriptionListPrn.size()));

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<MedPassPrnDetailModel> call, Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
            }
        });




        /*NetworkUtility.makeJSONObjectRequest(API.GetMedPassPRNDrugDetailsByPatientID*//* + "?FacilityID=" + facilityID*//* + "?PatientID=" + PatientId*//* + "&currdate=" + new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date())*//*, new JSONObject(), API.GetMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    if (modelMessPrescriptionListPrn.size() > 0) {
                        modelMessPrescriptionListPrn.clear();
                    }

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelPrnDrugs>>() {
                        }.getType();

                        modelMessPrescriptionListPrn = Application.getGson().fromJson(result.getJSONArray("Data").toString(), listType);

                        medPassPRNAdapter = new MedPassPRNAdapter(MedPrescriptionTabActivity.this, modelMessPrescriptionListPrn, patient_fName, patient_lName, BirthDate, Gender_full, Is_PrescriptionImg);
                        rvMessPrnList.setAdapter(medPassPRNAdapter);
                        searchPrn.setVisibility(View.VISIBLE);
                        llPrn.setVisibility(View.VISIBLE);

                        searchPrn.clearFocus();
                        //    dismissProgressDialog();
                        if (swipeRefresh.isRefreshing()) {
                            swipeRefresh.setRefreshing(false);
                        }
                        if (swipe_refresh_prn.isRefreshing()) {
                            swipe_refresh_prn.setRefreshing(false);
                        }
                        if (swipeRefreshHistory.isRefreshing()) {
                            swipeRefreshHistory.setRefreshing(false);
                        }

                        Log.e("Size", String.valueOf(modelMessPrescriptionListPrn.size()));
                        if (modelMessPrescriptionListPrn.size() > 0) {
                            openBarcode();
                        } else {
                            //finish();
                        }


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // dismissProgressDialog();
                    hud.dismiss();
                }
            }

            @Override
            public void onError(JSONObject result) {
                //    dismissProgressDialog();
                hud.dismiss();
                swipe_refresh_prn.setRefreshing(false);
            }
        });*/
    }

    /* private void getPatientImage(String ndc) {
         hud.show();
         NetworkUtility.makeJSONObjectRequest(API.PatientImagePath + "?ndc=" + ndc, new JSONObject(), API.PatientImagePath, new VolleyCallBack() {
             @Override
             public void onSuccess(JSONObject result) {
                 hud.dismiss();
                 try {
                     imageUrl = result.getString("Data");
 //                    Log.e("imageUrl:", "onSuccess: " + imageUrl);
                     if (result.getString("Data").equalsIgnoreCase("")) {
                         Picasso.get().load(R.drawable.ic_place_holder)
                                 .placeholder(R.drawable.ic_place_holder)
                                 .error(R.drawable.ic_place_holder)
                                 .into(patientImage);
                         Log.e("TAG", "onSuccess: Patient image" + patientImage);
                     } else {
                         Picasso.get().load(result.getString("Data"))
                                 .placeholder(R.drawable.ic_place_holder)
                                 .error(R.drawable.ic_place_holder)
                                 .into(patientImage);
 //                        Log.e("TAG", "onSuccess: Patient image is:" + imageUrl);

                     }
                 } catch (Exception e) {
                     Log.e("imageUrlEx:", "onSuccess: " + e.getMessage());
                     e.printStackTrace();
                 }
             }

             @Override
             public void onError(JSONObject result) {
                 hud.dismiss();
             }
         });
     }

     *//*image populating*//*
    private void openImagePatientDialog(String imageURL) {
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
//        Log.e("TAG", "openImagePatientDialog: Patient image" + imageURL );

    }
*/

    private void checkMissDoesGetTakenPrescription(int facilityID, String msg) {
        Log.e("Step", "==============6");

        if (!mBinding.swipeRefreshHistory.isRefreshing())
            hud.show();
        //      showProgressDialog(this);
        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        missTakenPrescriptionList.clear();
        NetworkUtility.makeJSONObjectRequest(API.GetGivenMedPassDrugDetailsByPatientID + "?FacilityID=" + facilityID + "&PatientID=" + PatientId + "&currdate=" + new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()), new JSONObject(), API.GetGivenMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            //        NetworkUtility.makeJSONObjectRequest(API.GetGivenMedPassDrugDetailsByPatientID + "?FacilityID=" + facilityID + "&PatientID=" + PatientId + "&currdate=07/04/2022", new JSONObject(), API.GetGivenMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelTakenPrescription>>() {
                        }.getType();
                        List<ModelTakenPrescription> missTakenPrescriptionListNew = new ArrayList<>();
                        missTakenPrescriptionListNew.addAll(MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("objdetails").toString(), listType));
                        isWrongPatient = false;
                        missCount = 0;
                        time = "";
                        missTakenPrescriptionList.clear();
                        /*for (int i = 0; i < missTakenPrescriptionList.size(); i++) {
                            if (missTakenPrescriptionList.get(i).isPRN()) {
                                missTakenPrescriptionList.remove(i);
                            }
                        }*/
                        for (ModelTakenPrescription prescription : missTakenPrescriptionListNew) {
                            if (!prescription.isPRN()) {
                                missTakenPrescriptionList.add(prescription);
                            }
                        }
                        missTakenPrescriptionList.sort((o1, o2) -> {

                            try {
                                return new SimpleDateFormat("hh:mm a").parse(o2.getDose_time()).compareTo(new SimpleDateFormat("hh:mm a").parse(o1.getDose_time()));
                            } catch (ParseException e) {
                                return 0;
                            }
                        });
                        for (int i = 0; i < missTakenPrescriptionList.size(); i++) {
                            Log.e("time:", "onSuccess: " + missTakenPrescriptionList.get(i).getDose_time());
                            if (missTakenPrescriptionList.get(i).isMissedDose() && missTakenPrescriptionList.get(0).getDose_time().equals(missTakenPrescriptionList.get(i).getDose_time())) {
                                time = missTakenPrescriptionList.get(i).getDose_time();
                                missCount += 1;
                                missDoseTime = time;
                                Log.e("TAG", "onSuccess: " + missTakenPrescriptionList.get(i).isMissedDose());
                                Log.e("TAG", "onSuccess: ----------------------------------------->" + missDoseTime + " " + missCount);
                            }
                        }
                        missDoseCount = missCount;
                        missDoseTime = time;
                        boolean isMissDose = result.getJSONObject("Data").getBoolean("IsMissedDose");
                        boolean isTaken = result.getJSONObject("Data").getBoolean("IsTaken");

                        if (isTaken) {
                            if (!isWrongPatient) {
                                /*alertDialog.setMessage(msg);
                                alertDialog.setCancelable(false);
                                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                getChangePrescriptionByPatientId();
                                            }
                                        });
                                if (alertDialog.isShowing()) {
                                    alertDialog.dismiss();
                                }
                                alertDialog.show();*/
                                if (!isCurrentMedPassDialogShown) {
                                    openAlertDialog1("2");
                                } else {
                                    showAlertDialog(massageDialog, "", "", "OK", "4");
                                }
                                isDialogShown = true;
//                                showAlertDialog(msg, "", "", "OK", "4");
                            }
                        } else {
                            if (isMissDose && missCount > 0) {
                                if (!isWrongPatient) {
                                    if (!isDialogShown) {
                                        Log.e("TAG", "onSuccess: Dialog" + isDialogShown);
                                        if (!isCurrentMedPassDialogShown) {
                                            openAlertDialog1("1");
                                        } else {
                                            openAlertDialog2();
                                        }
                                        isDialogShown = true;
                                    } else {
                                        if (missCount > 0) {
                                            openAlertDialog3();
                                        }
                                    }
                                } else {
                                    if (missCount > 0) {
                                        openAlertDialog3();
                                    }
                                }
                            } else {
                                if (!isWrongPatient) {
                                    /*alertDialog.setMessage(msg);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    getChangePrescriptionByPatientId();
                                                }
                                            });
                                    if (alertDialog.isShowing()) {
                                        alertDialog.dismiss();
                                    }
                                    alertDialog.show();*/
                                    if (!isCurrentMedPassDialogShown) {
                                        openAlertDialog1("2");
                                    } else {
                                        showAlertDialog(massageDialog, "", "", "OK", "4");
                                    }
                                    isDialogShown = true;
//                                    showAlertDialog(msg, "", "", "OK", "4");
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                //dismissProgressDialog();
                hud.dismiss();
                mBinding.swipeRefreshHistory.setRefreshing(false);
            }
        });
    }

    private void getTakenPrescription(int facilityID) {
        Log.e("Step", "==============5");
//        if (!mBinding.swipeRefreshHistory.isRefreshing())
//            hud.show();
        if (!hud.isShowing()) {
            hud.show();
        }

        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        Call<GetGivenMedPassPrescriptionModel> call = apiService.GetGivenMedPassDrugHistoryByPatientID(PatientId, facilityID, new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));
        call.enqueue(new Callback<GetGivenMedPassPrescriptionModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<GetGivenMedPassPrescriptionModel> call, @NonNull Response<GetGivenMedPassPrescriptionModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    try {
//                        Log.e("TAG", "onResponse: "+response.body().getData().getObjdetails().get(0).getDOB() );
                        assert response.body() != null;
                        int response_code = response.body().getResponseStatus();
                        Log.e("onResponse111:", "tag111: " + response.body());
                        if (modelTakenPrescriptionList.size() > 0) {
                            modelTakenPrescriptionList.clear();
                        }
                        if (response_code == 1) {
                            modelTakenPrescriptionList = response.body().getData().getObjdetails();
                            if (modelTakenPrescriptionList.size() <= 0) {
                                mBinding.noDataTv.setVisibility(View.VISIBLE);
                                mBinding.swipeRefreshHistory.setVisibility(View.GONE);
                            } else {
                                mBinding.noDataTv.setVisibility(View.GONE);
                                mBinding.swipeRefreshHistory.setVisibility(View.VISIBLE);
                            }

                            medTakenPrescriptionAdapter = new MedTakenPrescriptionAdapter(activity, context, modelTakenPrescriptionList);
                            mBinding.rvTakenPrescriptionList.setAdapter(medTakenPrescriptionAdapter);

                            mBinding.searchHistory.setVisibility(View.VISIBLE);
                            mBinding.llSearchHistory.setVisibility(View.VISIBLE);

                            mBinding.searchHistory.clearFocus();
                            //  dismissProgressDialog();

                            //swipeRefreshHistory.setRefreshing(false);
                            if (mBinding.swipeRefresh.isRefreshing()) {
                                mBinding.swipeRefresh.setRefreshing(false);
                            }
                            if (mBinding.swipeRefreshPrn.isRefreshing()) {
                                mBinding.swipeRefreshPrn.setRefreshing(false);
                            }
                            if (mBinding.swipeRefreshHistory.isRefreshing()) {
                                mBinding.swipeRefreshHistory.setRefreshing(false);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //dismissProgressDialog();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetGivenMedPassPrescriptionModel> call, Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
            }
        });
        /* NetworkUtility.makeJSONObjectRequest(API.GetGivenMedPassDrugDetailsByPatientID + "?FacilityID=" + facilityID + "&PatientID=" + PatientId + "&currdate=" + new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()), new JSONObject(), API.GetGivenMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    if (modelTakenPrescriptionList.size() > 0) {
                        modelTakenPrescriptionList.clear();
                    }

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelTakenPrescription>>() {
                        }.getType();
                        modelTakenPrescriptionList = Application.getGson().fromJson(result.getJSONObject("Data").getJSONArray("objdetails").toString(), listType);
                        if (modelTakenPrescriptionList.size() <= 0) {
                            no_data_tv.setVisibility(View.VISIBLE);
                            swipeRefreshHistory.setVisibility(View.GONE);
                        }
                        else{
                            no_data_tv.setVisibility(View.GONE);
                            swipeRefreshHistory.setVisibility(View.VISIBLE);
                        }

                                medTakenPrescriptionAdapter = new MedTakenPrescriptionAdapter(MedPrescriptionTabActivity.this, modelTakenPrescriptionList);
                        rvTakenPrescriptionList.setAdapter(medTakenPrescriptionAdapter);

                        searchHistory.setVisibility(View.VISIBLE);
                        ll_search_history.setVisibility(View.VISIBLE);

                        searchHistory.clearFocus();
                        //  dismissProgressDialog();

                        //swipeRefreshHistory.setRefreshing(false);
                        if (swipeRefresh.isRefreshing()) {
                            swipeRefresh.setRefreshing(false);
                        }
                        if (swipe_refresh_prn.isRefreshing()) {
                            swipe_refresh_prn.setRefreshing(false);
                        }
                        if (swipeRefreshHistory.isRefreshing()) {
                            swipeRefreshHistory.setRefreshing(false);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // dismissProgressDialog();
                }
            }


            @Override
            public void onError(JSONObject result) {
                //  dismissProgressDialog();
                hud.dismiss();
                swipeRefreshHistory.setRefreshing(false);
            }
        });*/
    }

    private void getUserImage(int patientId) {
        //showProgressDialog(MedPrescriptionTabActivity.this);
        hud.show();
        NetworkUtility.makeJSONObjectRequest(API.PatientImagePath + "?patient_id=" + patientId, new JSONObject(), API.PatientImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {

                    url = result.getString("Data");
                  /*  Picasso.get().load(result.getString("Data"))
                            .placeholder(R.drawable.ic_user_placeholder)
                            .error(R.drawable.ic_user_placeholder)
                            .into(mBinding.patientImage);*/
                    getLoaHospitalApi();

                    Glide.with(context).load(result.getString("Data")).placeholder(R.drawable.ic_user_placeholder)
                            .error(R.drawable.ic_user_placeholder)
                            .into(mBinding.patientImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
                //dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        MyApplication.setPreferencesBoolean("isShowDcMedDialog", false);
        /*if (alert != null && alert.isShowing()) {
            alert.dismiss();

        }
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        alert = null;
        alertDialog = null;*/
        Log.e("TAG", "onDestroy:Tab ");
        unRegistedBroadcast();
        super.onDestroy();
    }

    private void unRegistedBroadcast() {
        try {
            if (MyReceiver != null) {
                unregisterReceiver(MyReceiver);
            }
            if (checkNetworkReceiver != null) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(checkNetworkReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        Activity act = this;
        if (act.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /* @Override
    public void isDrugShiftedToLoa(boolean isLoaShifted) {
        if (isLoaShifted) {
            getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
        }

    }*/

    private void InsertSometingwantErrorLog(String message, String date, String time) {
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
