package com.rn.android.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.GivenMedPassPatientAdapter;
import com.rn.android.MyApplication;
import com.rn.android.BaseActivity;

import com.rn.android.model.MedpassPatientListModel;
import com.rn.android.model.MedsheetUrlModel;
import com.rn.android.retrofit.ApiClient;
import com.rn.android.retrofit.ApiInterface;
import com.rn.android.util.MyReceiver;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GivenMedpassPatientListActivity extends BaseActivity {
    List<MedpassPatientListModel.DataBean.MedpassdetailsBean> modelMedPassPatientList = new ArrayList<>();
    @BindView(R.id.search)
    SearchView search;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    GivenMedPassPatientAdapter adapter;
    // ProgressDialog progressDialog;
    @BindView(R.id.rv_given_med_pass_patient_list)
    RecyclerView rvGivenMedPassPatientList;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    private KProgressHUD hud;
    ApiInterface apiService;
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_given_medpass_patient_list);
        ButterKnife.bind(this);
        setTitle("GIVEN MEDPASS PATIENT");

        initProgress();
        MyReceiver = new MyReceiver();
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        search.setQueryHint("Search Patient");
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        // Utils.hideKeybord(this);
        search.setVisibility(View.GONE);
        initRecyclerView();
        getGivenMedPassPatientList(MyApplication.getPrefranceDataInt("ExFacilityID"));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (!TextUtils.isEmpty(newText.trim())) {
                        if (rvGivenMedPassPatientList.getAdapter() != null) {
                            newText.replace(",", "");
                            ((GivenMedPassPatientAdapter) rvGivenMedPassPatientList.getAdapter()).filter(newText);
                        }
                    } else {
                        ((GivenMedPassPatientAdapter) rvGivenMedPassPatientList.getAdapter()).filter("");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGivenMedPassPatientList(MyApplication.getPrefranceDataInt("ExFacilityID"));
                // getMedPassPatientList(facility);
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initRecyclerView() {
        rvGivenMedPassPatientList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        broadcastIntent();
        super.onResume();
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    public void openMedsheetDialog(String facilityName, String PatientId) {
        final String[] months = new String[]{"January", "February",
                "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};

        final String[] years = new String[]{"2017", "2018",
                "2019", "2020", "2021", "2022", "2023", "2024", "2025",};
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        Log.e("Month Name", month_name);
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String todayDate = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

        Log.e("Today Date", todayDate);

        int count = days / 2;


        Log.e("Count", String.valueOf(count));

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        Log.e("Year", yearInString);

        Dialog dialog = new Dialog(this);
        //    dialog.setCancelable(false);
        //    dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_med_sheet);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        Spinner spinner_month = (Spinner) dialog.findViewById(R.id.spinner_month);
        Spinner spinner_year = (Spinner) dialog.findViewById(R.id.spinner_year);
        Button tv_save = (Button) dialog.findViewById(R.id.tv_save);
        Button tv_close_dialog = (Button) dialog.findViewById(R.id.tv_close_dialog);
        setTimeSpinner(spinner_month, months);
        setTimeSpinner(spinner_year, years);
        for (int i = 0; i < spinner_month.getCount(); i++) {
            if (Integer.parseInt(todayDate) >= count) {
                if (spinner_month.getItemAtPosition(i).toString().equalsIgnoreCase(month_name)) {
                    spinner_month.setSelection(i);

                }
            } else {
                if (spinner_month.getItemAtPosition(i).toString().equalsIgnoreCase(month_name)) {
                    spinner_month.setSelection(i);

                }

            }

        }

        for (int i = 0; i < spinner_year.getCount(); i++) {
            if (spinner_year.getItemAtPosition(i).toString().equalsIgnoreCase(yearInString)) {
                spinner_year.setSelection(i);

            }
        }

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMedpassList(facilityName, spinner_month.getSelectedItem().toString(), spinner_year.getSelectedItem().toString(), PatientId, dialog);

            }
        });
        tv_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void getMedpassList(String facilityName, String month, String year, String patientID, Dialog dialog) {

       /* progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        KProgressHUD hud1;
        hud1 = KProgressHUD.create(Objects.requireNonNull(GivenMedpassPatientListActivity.this))
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("it may take a while to complete. \n\t\t\t\tPlease be patient.")
                .setCancellable(false);
        hud1.show();
        Call<MedsheetUrlModel> call = apiService.getMedsheeturl(facilityName, month, year, patientID);
        call.enqueue(new Callback<MedsheetUrlModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MedsheetUrlModel> call, @NonNull Response<MedsheetUrlModel> response) {
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    if (response.body() != null) {

                        if (response.body().getResponseStatus() == 1) {
                            if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                                Intent intent = new Intent(GivenMedpassPatientListActivity.this, PDFViewActivity.class);
                                intent.putExtra("url", response.body().getData());
                                startActivity(intent);
                            } else {
                                Utils.showAlertToast(GivenMedpassPatientListActivity.this, "No Medsheet found");
                            }
                        } else {
                            Utils.showAlertToast(GivenMedpassPatientListActivity.this, "No Medsheet found");
                        }

                    } else {
                        Utils.showAlertToast(GivenMedpassPatientListActivity.this, "No Medsheet found");
                    }

                    hud1.dismiss();
                }

            }

            @Override
            public void onFailure(@NonNull Call<MedsheetUrlModel> call, @NonNull Throwable t) {
                Log.e("TAG", t.toString());
                hud1.dismiss();
            }
        });
   /*     NetworkUtility.makeJSONObjectRequest(API.GetPatientMedsheetReport + "?FacilityName=" + facilityName + "&ddlmonth=" + month + "&ddlYear=" + year + "&PatientID=" + patientID, new JSONObject(), API.GetPatientMedsheetReport, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {

                    //  progressDialog.dismiss();
                    if (!result.getString("Data").equalsIgnoreCase("")) {
                        Intent intent = new Intent(GivenMedpassPatientListActivity.this, PDFViewActivity.class);
                        intent.putExtra("url", result.getString("Data"));
                        startActivity(intent);
                        dialog.dismiss();
                    } else {
                        Utils.showAlertToast(GivenMedpassPatientListActivity.this, "No Medsheet found");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    // progressDialog.dismiss();


                }
            }

            @Override
            public void onError(JSONObject result) {
                // progressDialog.dismiss();
                hud.dismiss();

            }
        });*/
    }

    public void setTimeSpinner(final Spinner spinner, String[] list) {

        final ArrayAdapter<String> cycleStartDateArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.simple_spinner_item,
                        list);

        cycleStartDateArrayAdapter.setDropDownViewResource(R.layout
                .spinner_dropdown_item);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setAdapter(cycleStartDateArrayAdapter);//setting the adapter data into the AutoCompleteTextView
            }
        });
    }

    private void initProgress() {
     /*   progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);*/

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false);
    }

    private void getGivenMedPassPatientList(int facilityID) {
        Log.e("Step", "==============4");
        if (!swipeRefresh.isRefreshing())
            hud.show();
        // progressDialog.show();
        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        Call<MedpassPatientListModel> call = apiService.getPatientList(facilityID, new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));
        call.enqueue(new Callback<MedpassPatientListModel>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MedpassPatientListModel> call, @NonNull Response<MedpassPatientListModel> response) {
                hud.dismiss();
                if (Utils.checkInternetConnection(getApplicationContext())) {
                    try {
                        int response_code = response.body().getResponseStatus();
                        Log.e("onResponse111:", "tag111: " + response.body());
                        if (modelMedPassPatientList.size() > 0) {
                            modelMedPassPatientList.clear();
                        }
                        if (response_code == 1) {
                            modelMedPassPatientList = response.body().getData().getMedpassdetails();
                            adapter = new GivenMedPassPatientAdapter(GivenMedpassPatientListActivity.this, modelMedPassPatientList);
                            rvGivenMedPassPatientList.setAdapter(adapter);
                            search.setVisibility(View.VISIBLE);
                            search.clearFocus();
                            //     dismissProgressDialog();
                            swipeRefresh.setRefreshing(false);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //dismissProgressDialog();
                    }
                }
              /*  try {
                    if (modelMedPassPatientList.size() > 0) {
                        modelMedPassPatientList.clear();
                    }
                    if (response.body() != null) {
                        Type listType = new TypeToken<List<ModelMedPassPatient>>() {
                        }.getType();
                        modelMedPassPatientList = Application.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                        adapter = new GivenMedPassPatientAdapter(GivenMedpassPatientListActivity.this, modelMedPassPatientList);
                        rvGivenMedPassPatientList.setAdapter(adapter);
                        search.setVisibility(View.VISIBLE);
                        search.clearFocus();
                        //     progressDialog.dismiss();
                        swipeRefresh.setRefreshing(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //  progressDialog.dismiss();
                }*/
                //    Log.d("TAG", "reportResponse: " + response_code);

            }

            @Override
            public void onFailure(@NonNull Call<MedpassPatientListModel> call, Throwable t) {
                Log.e("TAG", t.toString());
                hud.dismiss();
            }
        });



    /*    NetworkUtility.makeJSONObjectRequest(API.GetMedGivenpatientList + "?FacilityID=" + facilityID + "&currdate=" + new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()), new JSONObject(), API.GetMedGivenpatientList, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                    if (modelMedPassPatientList.size() > 0) {
                        modelMedPassPatientList.clear();
                    }
                    if (result != null) {
                        Type listType = new TypeToken<List<ModelMedPassPatient>>() {
                        }.getType();
                        modelMedPassPatientList = Application.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                        adapter = new GivenMedPassPatientAdapter(GivenMedpassPatientListActivity.this, modelMedPassPatientList);
                        rvGivenMedPassPatientList.setAdapter(adapter);
                        search.setVisibility(View.VISIBLE);
                        search.clearFocus();
                   //     progressDialog.dismiss();
                        swipeRefresh.setRefreshing(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                  //  progressDialog.dismiss();
                }
            }

            @Override
            public void onError(JSONObject result) {
               // progressDialog.dismiss();
                hud.dismiss();
                swipeRefresh.setRefreshing(false);
            }
        });*/
    }

}
