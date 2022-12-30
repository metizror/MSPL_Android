package com.rn.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metiz.pelconnect.R;
import com.rn.android.model.ModelCensusListDetails;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationListTypeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.rvNotificationList)
    RecyclerView rvNotificationList;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    ArrayList<ModelCensusListDetails> modelCensusListDetails = new ArrayList<>();
    NotificationListAdapter listAdapter;
    ProgressDialog progressDialog;
    String OrderID, HeaderID, DailyHoldRxID, EmailType, PatientID, PharmacyClientID, time, facilityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_type);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EmailType = getIntent().getExtras().get("EmailType").toString();
        time = getIntent().getExtras().get("time").toString();
        facilityId = getIntent().getExtras().get("FacilityID").toString();
        //time = "2020-08-05 14:16:43.000";
        //EmailType = "11";
        //facilityId = "188";

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        rvNotificationList.setLayoutManager(new LinearLayoutManager(this));

        getNotificationList();

    }


    private void getNotificationList() {
        progressDialog.show();
        JSONObject json = new JSONObject();

        try {
            json.put("OrderID", 0);
            json.put("HeaderID", 0);
            json.put("DailyHoldRxID", 0);
            json.put("EmailType", EmailType);
            json.put("PatientID", 0);
            json.put("PharmacyClientID", 0);
            json.put("time", time);
            json.put("FacilityID", facilityId);
        } catch (Exception ex) {

        }
        Log.e("TAG", "getNotificationList: >>>" + json.toString(), null);
        NetworkUtility.makeJSONObjectRequest(API.NewNotification, json, API.NewNotification, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (result != null) {
                        JSONArray arrObjValue = new JSONArray();
                        JSONArray arrData = result.getJSONArray("Data");
                        for (int i = 0; i < arrData.length(); i++) {
                            JSONArray jsonArray = arrData.getJSONObject(i).getJSONArray("objlist");

                            JSONObject obj = new JSONObject();
                            obj.put("DeliveryNo", jsonArray.getJSONObject(0).getString("ValueText"));
                            obj.put("drug", jsonArray.getJSONObject(1).getString("ValueText"));
                            obj.put("qty", jsonArray.getJSONObject(2).getString("ValueText"));
                            obj.put("Rx", jsonArray.getJSONObject(3).getString("ValueText"));
                            obj.put("Patient", jsonArray.getJSONObject(4).getString("ValueText"));
                            obj.put("Driver", jsonArray.getJSONObject(5).getString("ValueText"));

                            arrObjValue.put(obj);
                        }

                        listAdapter = new NotificationListAdapter(NotificationListTypeActivity.this, arrObjValue);
                        rvNotificationList.setAdapter(listAdapter);
                        llData.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    } else {
                        llNoData.setVisibility(View.VISIBLE);
                        llData.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    llNoData.setVisibility(View.VISIBLE);
                    llData.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
                llNoData.setVisibility(View.VISIBLE);
                llData.setVisibility(View.GONE);

            }
        });

    }


}
