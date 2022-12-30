package com.rn.android;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.MedPassPrescriptionAdapter;
import com.rn.android.model.ModelMessPrescription;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedPrescriptionListActivity extends BaseActivity {

    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.rv_mess_prescription_list)
    RecyclerView rvMessPrescriptionList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    int PatientId = 0;

    List<ModelMessPrescription> modelMessPrescriptionList = new ArrayList<>();
    MedPassPrescriptionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_prescription_list);
        ButterKnife.bind(this);
        setTitle("Prescription");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search.setQueryHint("Search Drug");
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        search.setVisibility(View.GONE);
        PatientId = getIntent().getIntExtra("PatientId", 0);
         swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (!TextUtils.isEmpty(newText.trim())) {
                        if (rvMessPrescriptionList.getAdapter() != null) {
                            newText.replace(",", "");
                            ((MedPassPrescriptionAdapter) rvMessPrescriptionList.getAdapter()).filter(newText);
                        }
                    } else {
                        ((MedPassPrescriptionAdapter) rvMessPrescriptionList.getAdapter()).filter("");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
        getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));

    }
    private void initRecyclerView() {
        rvMessPrescriptionList.setLayoutManager(new LinearLayoutManager(context));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPrescriptionList(int facilityID) {
        Log.e("Step", "==============4");
        if (!swipeRefresh.isRefreshing())
            showProgressDialog(this);

        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        NetworkUtility.makeJSONObjectRequest(API.GetMedPassDrugDetailsByPatientID + "?FacilityID=" + facilityID + "&PatientID=" + PatientId + "&currdate="+ new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()), new JSONObject(), API.GetMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {

                    if (modelMessPrescriptionList.size() > 0) {
                        modelMessPrescriptionList.clear();
                    }

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelMessPrescription>>() {
                        }.getType();
                        modelMessPrescriptionList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);

                        adapter = new MedPassPrescriptionAdapter(MedPrescriptionListActivity.this, MedPrescriptionListActivity.this, modelMessPrescriptionList, true);
                        rvMessPrescriptionList.setAdapter(adapter);

                        search.setVisibility(View.VISIBLE);
                        search.clearFocus();
                        dismissProgressDialog();

                        swipeRefresh.setRefreshing(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dismissProgressDialog();

                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();

                swipeRefresh.setRefreshing(false);
            }
        });
    }

}
