package com.rn.android.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.metiz.pelconnect.databinding.DialogDobimgShowAlertBinding;
import com.rn.android.Adapter.MedTakenPrescriptionAdapter;
import com.rn.android.MyApplication;
import com.rn.android.BaseActivity;

import com.rn.android.model.GetGivenMedPassPrescriptionModel;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.retrofit.ApiClient;
import com.rn.android.retrofit.ApiInterface;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetGivenMedpassDrungHistoryActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_history)
    SearchView searchHistory;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @BindView(R.id.rv_taken_prescription_list)
    RecyclerView rvTakenPrescriptionList;
    @BindView(R.id.swipe_refresh_history)
    SwipeRefreshLayout swipeRefreshHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.txt_birthdate)
    TextView txtBirthdate;
    MedTakenPrescriptionAdapter medTakenPrescriptionAdapter;
    List<GetGivenMedPassPrescriptionModel.DataBean.ModelTakenPrescription> modelTakenPrescriptionList = new ArrayList<>();
    int PatientId = 0;
    String patientName = "";
    String BirthDate = "";
    String Gender = "";
    String url = "";
    ApiInterface apiService;
    //  ProgressDialog progressDialog;
    @BindView(R.id.patient_image)
    CircleImageView patientImage;
    private KProgressHUD hud;
    private DialogDobimgShowAlertBinding mDialogShowAlertBinding1;
    private Dialog dialogSHowAlert1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_given_medpass_drung_history);
        ButterKnife.bind(this);
        initProgress();
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        PatientId = getIntent().getIntExtra("PatientId", 0);
        patientName = getIntent().getStringExtra("name");
        BirthDate = getIntent().getStringExtra("BirthDate");
        Gender = getIntent().getStringExtra("gender");
        initRecyclerView();
        getUserImage(PatientId);


        swipeRefreshHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
                // getMedPassPatientList(facility);
            }
        });
        txtName.setText(patientName);
        txtBirthdate.setText(BirthDate);
        txtGender.setText(Gender);
        searchHistory.setQueryHint("Search Drug Or Rx Number");
        searchHistory.setIconifiedByDefault(false);
        searchHistory.setIconified(false);
        searchHistory.setVisibility(View.GONE);
        llSearchHistory.setVisibility(View.GONE);
        llHistory.setVisibility(View.VISIBLE);
        searchHistory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (!TextUtils.isEmpty(newText.trim())) {
                        if (rvTakenPrescriptionList.getAdapter() != null) {
                            newText.replace(",", "");
                            ((MedTakenPrescriptionAdapter) rvTakenPrescriptionList.getAdapter()).filter(newText);
                        }
                    } else {
                        ((MedTakenPrescriptionAdapter) rvTakenPrescriptionList.getAdapter()).filter("");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
        super.onResume();
    }

    private void initRecyclerView() {
        rvTakenPrescriptionList.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initProgress() {
      /*  progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);*/
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false);
    }


    private void getTakenPrescription(int facilityID) {
        Log.e("Step", "==============4");
        if (!swipeRefreshHistory.isRefreshing())
            hud.show();
        //   progressDialog.show();


        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }


//        Call<GetGivenMedPassPrescriptionModel> call = apiService.GetGivenMedPassDrugDetailsByPatientID(PatientId, facilityID, new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));
        Call<GetGivenMedPassPrescriptionModel> call = apiService.GetGivenMedPassDrugHistoryByPatientID(PatientId, facilityID, new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));
        call.enqueue(new Callback<GetGivenMedPassPrescriptionModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<GetGivenMedPassPrescriptionModel> call, @NonNull Response<GetGivenMedPassPrescriptionModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    try {
                        int response_code = response.body().getResponseStatus();
                        Log.e("onResponse111:", "tag111: " + response.body());
                        if (modelTakenPrescriptionList.size() > 0) {
                            modelTakenPrescriptionList.clear();
                        }
                        if (response_code == 1) {

                            modelTakenPrescriptionList = response.body().getData().getObjdetails();
                            medTakenPrescriptionAdapter = new MedTakenPrescriptionAdapter(GetGivenMedpassDrungHistoryActivity.this, GetGivenMedpassDrungHistoryActivity.this, modelTakenPrescriptionList);
                            rvTakenPrescriptionList.setAdapter(medTakenPrescriptionAdapter);
                            searchHistory.setVisibility(View.VISIBLE);
                            llSearchHistory.setVisibility(View.VISIBLE);
                            searchHistory.clearFocus();
                            //     dismissProgressDialog();
                            swipeRefreshHistory.setRefreshing(false);
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



      /*  NetworkUtility.makeJSONObjectRequest(API.GetGivenMedPassDrugDetailsByPatientID + "?FacilityID=" + facilityID + "&PatientID=" + PatientId + "&currdate=" + new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()), new JSONObject(), API.GetGivenMedPassDrugDetailsByPatientID, new VolleyCallBack() {
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

                        medTakenPrescriptionAdapter = new MedTakenPrescriptionAdapter(GetGivenMedpassDrungHistoryActivity.this, modelTakenPrescriptionList);
                        rvTakenPrescriptionList.setAdapter(medTakenPrescriptionAdapter);

                        searchHistory.setVisibility(View.VISIBLE);
                        llSearchHistory.setVisibility(View.VISIBLE);

                        searchHistory.clearFocus();

                      //  progressDialog.dismiss();

                        swipeRefreshHistory.setRefreshing(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                 //   hud.dismiss();
                   // progressDialog.dismiss();


                }
            }


            @Override
            public void onError(JSONObject result) {
               // progressDialog.dismiss();
                hud.dismiss();
                swipeRefreshHistory.setRefreshing(false);
            }
        });*/
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
            }
        });
        mDialogShowAlertBinding1.okBtn.setOnClickListener(v -> {
            dialogSHowAlert1.dismiss();
            dialogSHowAlert1 = null;
            if (check.equalsIgnoreCase("1")) {
                getTakenPrescription(MyApplication.getPrefranceDataInt("ExFacilityID"));
            }
        });
        dialogSHowAlert1.show();

    }

    private void getUserImage(int patientId) {
        hud.show();
        //  showProgressDialog(GetGivenMedpassDrungHistoryActivity.this);
        NetworkUtility.makeJSONObjectRequest(API.PatientImagePath + "?patient_id=" + patientId, new JSONObject(), API.PatientImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    //      dismissProgressDialog();
                    //  Pic.with(MedPrescriptionTabActivity.this).load("https://tm.mapyourmedsapi.com:5002/SignatureData/2019/10/6370688610105977770.jpg").placeholder(R.drawable.ic_user_placeholder).error(R.drawable.ic_user_placeholder).into(patientImage);
                    //  Picasso.get().load(().into(patientImage));

//                    Picasso.get().load(result.getString("Data"))
//                            .placeholder(R.drawable.ic_user_placeholder)
//                            .error(R.drawable.ic_user_placeholder)
//                            .into(patientImage);
                    url = result.getString("Data");

                    Glide.with(context).load(result.getString("Data")).placeholder(R.drawable.ic_user_placeholder)
                            .error(R.drawable.ic_user_placeholder)
                            .into(patientImage);

                    showAlertDialogimage("Did you verify patient's picture?\n\n" + "Patient - " + Html.fromHtml("<b>" + patientName + "</b>") + "\n", "Verify Person", "No", "Yes", "1");


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


}
