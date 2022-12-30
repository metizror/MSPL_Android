package com.rn.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;

import com.rn.android.model.ModelNotificationDetails;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationDetilsActivity extends BaseActivity {

    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;

    NotificationDetailsAdapter adapter;
    RecyclerView rv_details;
    String OrderID, HeaderID, DailyHoldRxID, EmailType, PatientID, PharmacyClientID,time,facilityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detils);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        OrderID = getIntent().getExtras().get("OrderID").toString();
        HeaderID = getIntent().getExtras().get("HeaderID").toString();
        DailyHoldRxID = getIntent().getExtras().get("DailyHoldRxID").toString();
        EmailType = getIntent().getExtras().get("EmailType").toString();
        PatientID = getIntent().getExtras().get("PatientID").toString();
        PharmacyClientID = getIntent().getExtras().get("PharmacyClientID").toString();
        time = getIntent().getExtras().get("time").toString();
        facilityId = getIntent().getExtras().get("FacilityID").toString();
        //time = "2020-08-05 14:16:43.000";
        //EmailType = "11";
        //facilityId = "188";

        newNotification();
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void newNotification() {
        JSONObject json = new JSONObject();

        /*if(EmailType.equals("11")){
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
        }else{*/
            try {
                json.put("OrderID", OrderID);
                json.put("HeaderID", HeaderID);
                json.put("DailyHoldRxID", DailyHoldRxID);
                json.put("EmailType", EmailType);
                json.put("PatientID", PatientID);
                json.put("PharmacyClientID", PharmacyClientID);
                json.put("time", time);
                json.put("FacilityID", facilityId);
            } catch (Exception ex) {

            }
        /*}*/

        showProgressDialog(this);
        NetworkUtility.makeJSONObjectRequest(API.NewNotification, json, API.NewNotification, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
//                    ArrayList myArrayList = Application.getGson().fromJson(result.getJSONArray("Data").toString(), new TypeToken<ArrayList>() {
//                    }.getType());
////                    Log.e("Lenth", String.valueOf(myArrayList.get(0)));


                    if (result.getJSONArray("Data").length()>0) {

                        LayoutInflater layoutInflater = getLayoutInflater();
                        View view;

                            for (int i = 0; i < result.getJSONArray("Data").length(); i++) {
                                // Add the text layout to the parent layout

                                JSONObject jsonObject = result.getJSONArray("Data").getJSONObject(i);
                                JSONArray jsonArray = jsonObject.getJSONArray("objlist");
                                ArrayList<ModelNotificationDetails> myList = MyApplication.getGson().fromJson(jsonObject.getJSONArray("objlist").toString(), new TypeToken<ArrayList<ModelNotificationDetails>>() {
                                }.getType());

                                Log.e("Lenth", String.valueOf(myList.size()));
                                view = layoutInflater.inflate(R.layout.row_notification_details, layout, false);
                                rv_details = view.findViewById(R.id.rv_details);
                                rv_details.setLayoutManager(new LinearLayoutManager(NotificationDetilsActivity.this));
                                // Add the text view to the parent layout
                                adapter = new NotificationDetailsAdapter(NotificationDetilsActivity.this, myList);
                                rv_details.setNestedScrollingEnabled(false);
                                rv_details.setAdapter(adapter);

                                layout.addView(view);
                                llData.setVisibility(View.VISIBLE);
                                llNoData.setVisibility(View.GONE);

                            }

                    } else {
                        llNoData.setVisibility(View.VISIBLE);
                        llData.setVisibility(View.GONE);
                    }
                    dismissProgressDialog();

                } catch (JSONException e) {
                    e.printStackTrace();
                    llNoData.setVisibility(View.VISIBLE);
                    llData.setVisibility(View.GONE);

                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
                llNoData.setVisibility(View.VISIBLE);
                llData.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
