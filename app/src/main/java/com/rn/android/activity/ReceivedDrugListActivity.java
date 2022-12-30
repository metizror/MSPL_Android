package com.rn.android.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;
import com.rn.android.MyApplication;
import com.rn.android.BaseActivity;
import com.rn.android.model.ModelReceivedDrug;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceivedDrugListActivity extends BaseActivity {
    List<ModelReceivedDrug> modelReceivedDrugList = new ArrayList<>();
    @BindView(R.id.rv_received_drug)
    RecyclerView rv_received_drug;

    @BindView(R.id.edt_from_date)
    EditText edt_from_date;
    @BindView(R.id.edt_to_date)
    EditText edt_to_date;

    @BindView(R.id.search)
    SearchView search;
    ReceivedDrugAdapter receivedDrugAdapter;

    @BindView(R.id.ll_back)
    LinearLayout llBack;
  @BindView(R.id.ll_search)
    LinearLayout ll_search;

    @BindView(R.id.ll_search_from_to)
    LinearLayout ll_search_from_to;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_drug_list);
        ButterKnife.bind(this);

        setTitle("RECEIVED");

        search.setQueryHint(Html.fromHtml("<font color = #AEAEAE>" + "Search Delivery No" + "</font>"));
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        search.setVisibility(View.GONE);
        ll_search.setVisibility(View.GONE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String startDateStr = df.format(monthFirstDay);
       // String endDateStr = df.format(monthLastDay);
        String endDateStr = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());;

        Log.e("Start and End Date",startDateStr);
        Log.e("Start and End Date",endDateStr);
        edt_from_date.setText(startDateStr);
        edt_to_date.setText(endDateStr);
        getReceivedDrugList(edt_from_date.getText().toString(),edt_to_date.getText().toString(), MyApplication.getPrefranceData("UserID"));


        ll_search_from_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isValid()){
                        getReceivedDrugList(edt_from_date.getText().toString(),edt_to_date.getText().toString(), MyApplication.getPrefranceData("UserID"));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
                        if (rv_received_drug.getAdapter() != null) {
                            newText.replace(",", "");
                            ((ReceivedDrugAdapter) rv_received_drug.getAdapter()).filter(newText);
                        }
                    } else {
                        ((ReceivedDrugAdapter) rv_received_drug.getAdapter()).filter("");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });
        initRecyclerView();
        edt_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openDatePicker(ReceivedDrugListActivity.this, edt_from_date, new Utils.DatePickerSelected() {
                    @Override
                    public void selectedDate(String date) {
                        edt_from_date.setText(date);
                        edt_to_date.requestFocus();
                    }
                });
            }
        });
        edt_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openDatePicker(ReceivedDrugListActivity.this, edt_to_date, new Utils.DatePickerSelected() {
                    @Override
                    public void selectedDate(String date) {
                        edt_to_date.setText(date);

                    }
                });
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       // getReceivedDrugList("01/01/2020", "01/22/2020", "77");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    private boolean isValid() throws ParseException {

            SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy");

            if (edt_from_date.getText().toString().equalsIgnoreCase("From Date")) {
                Utils.showAlertToast(this, "Please select from date");
                return false;
            } else if (edt_to_date.getText().toString().equalsIgnoreCase("To Date")) {
                Utils.showAlertToast(this, "Please select to date");
                return false;
            } else if (dfDate.parse(edt_from_date.getText().toString()).after(dfDate.parse(edt_to_date.getText().toString()))) {
                Utils.showAlertToast(this, "From Date Always before To Date");
                return false;

            }

            return true;

    }


    private void initRecyclerView() {
        rv_received_drug.setLayoutManager(new LinearLayoutManager(this));

    }


    private void getReceivedDrugList(String StartDate, String EndDate, String UserID) {
        Log.e("Step", "==============4");
        showProgressDialog(this);


        NetworkUtility.makeJSONObjectRequest(API.ReceivedDrugsList + "?StartDate=" + StartDate + "&EndDate=" + EndDate + "&UserID=" + UserID, new JSONObject(), API.ReceivedDrugsList, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {

                    if (modelReceivedDrugList.size() > 0) {
                        modelReceivedDrugList.clear();

                    }

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelReceivedDrug>>() {
                        }.getType();
                        modelReceivedDrugList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                        receivedDrugAdapter = new ReceivedDrugAdapter(ReceivedDrugListActivity.this, modelReceivedDrugList);
                        rv_received_drug.setAdapter(receivedDrugAdapter);
                        if (modelReceivedDrugList.size()>0){
                            search.setVisibility(View.VISIBLE);
                            ll_search.setVisibility(View.VISIBLE);
                            search.clearFocus();
                        }else {
                            search.setVisibility(View.GONE);
                            ll_search.setVisibility(View.GONE);
                            search.clearFocus();
                        }


                        dismissProgressDialog();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dismissProgressDialog();
                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
            }
        });
    }
}
