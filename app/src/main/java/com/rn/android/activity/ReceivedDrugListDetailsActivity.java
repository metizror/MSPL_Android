package com.rn.android.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;
import com.rn.android.MyApplication;
import com.rn.android.BaseActivity;
import com.rn.android.model.ModelReceivedListDetails;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceivedDrugListDetailsActivity extends BaseActivity {



    @BindView(R.id.rv_received_drug_details)
    RecyclerView rv_received_drug_details;

    ArrayList<ModelReceivedListDetails> modelReceivedListDetailsArrayList = new ArrayList<>();
    ReceivedDrugListDetailsAdapter receivedDrugListDetailsAdapter;
    String transId="";

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search)
    SearchView search;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_drug_list_details);
        ButterKnife.bind(this);
        setTitle("DETAIL");
        initProgress();
        search.setQueryHint(Html.fromHtml("<font color = #AEAEAE>" + "Search Drug" + "</font>"));
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        search.setVisibility(View.GONE);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (!TextUtils.isEmpty(newText.trim())) {
                        if (rv_received_drug_details.getAdapter() != null) {
                            newText.replace(",", "");
                            ((ReceivedDrugListDetailsAdapter) rv_received_drug_details.getAdapter()).filter(newText);
                        }
                    } else {
                        ((ReceivedDrugListDetailsAdapter) rv_received_drug_details.getAdapter()).filter("");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });
        transId = getIntent().getStringExtra("transID");
        Log.e("tranSID",transId);
        initRecyclerView();
        getInventoryDetails(transId);

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    private void initRecyclerView() {
        rv_received_drug_details.setLayoutManager(new LinearLayoutManager(this));

    }


    private void getInventoryDetails(String transId) {
        Log.e("Step", "==============4");
        progressDialog.show();
        NetworkUtility.makeJSONObjectRequest(API.GetInventoryDetailsByTranId + "?TranId=" + transId, new JSONObject(), API.GetInventoryDetailsByTranId, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {

                    if (modelReceivedListDetailsArrayList.size() > 0) {
                        modelReceivedListDetailsArrayList.clear();

                    }

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelReceivedListDetails>>() {
                        }.getType();
                        modelReceivedListDetailsArrayList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                        receivedDrugListDetailsAdapter = new ReceivedDrugListDetailsAdapter(ReceivedDrugListDetailsActivity.this, modelReceivedListDetailsArrayList);
                        rv_received_drug_details.setAdapter(receivedDrugListDetailsAdapter);
                        search.setVisibility(View.VISIBLE);
                        search.clearFocus();
                        progressDialog.dismiss();


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onError(JSONObject result) {
                progressDialog.dismiss();
            }
        });
    }



}
