package com.rn.android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.CycleDeliveryAdapter;
import com.rn.android.Adapter.NextCycleDeliveryDateAdapter;
import com.rn.android.fragment.BaseFragment;
import com.rn.android.model.CycleStartDate;
import com.rn.android.model.ModelCycleDeliveryDate;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CycleActivity extends BaseFragment {
    List<CycleStartDate> cycleStartDateList = new ArrayList<>();
    @BindView(R.id.spinner_cycle_start_date)
    Spinner spinnerCycleStartDate;
    @BindView(R.id.cycle_date)
    TextView cycleDate;
    @BindView(R.id.cycle_time)
    TextView cycleTime;
    @BindView(R.id.changed_by)
    TextView changedBy;
    @BindView(R.id.changed_on)
    TextView changedOn;
    @BindView(R.id.btn_next)
    TextView btnNext;
    String cycleStartDate = "";
    @BindView(R.id.btn_update)
    TextView btnUpdate;
    CycleDeliveryAdapter adapter;
    int HeaderId = 0;
    String deliveryDate = "";
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.tv_next_cycle_start_date)
    TextView tv_next_cycle_start_date;
    @BindView(R.id.ll_cycle_main)
    LinearLayout llCycleMain;
    int facility = 0;
    List<ModelCycleDeliveryDate> modelCycleDeliveryDates = new ArrayList<>();
    List<ModelCycleDeliveryDate> modelNextCycleDeliveryDateList = new ArrayList<>();
    NextCycleDeliveryDateAdapter nextCycleDeliveryDateAdapter;
    int pendingDays = 0;

    public CycleActivity() {
        // Required empty public constructor

    }

    @SuppressLint("ValidFragment")
    public CycleActivity(int facility) {
        this.facility = facility;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cycle, container, false);
        ButterKnife.bind(this, rootView);
        getCycleStartList();
        tv_next_cycle_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextCycleDateDialog();
            }
        });
        spinnerCycleStartDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cycleStartDateList.size() > 0) {

                    getCycleDetails(cycleStartDateList.get(position).getId(), null);
                    HeaderId = cycleStartDateList.get(position).getId();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CycleTabActivity.class);
                intent.putExtra("cycleStartDate", cycleStartDate);
                intent.putExtra("HeaderId", HeaderId);
                intent.putExtra("pendingDays", pendingDays);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();


            }
        });
        return rootView;
    }
    private void getCycleStartList() {
        showProgressDialog(getActivity());

        NetworkUtility.makeJSONObjectRequest(API.GetFacilityCycleDetails + "?FacilityID=" + MyApplication.getPrefranceDataInt("ExFacilityID"), new JSONObject(), API.GetFacilityCycleDetails, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    dismissProgressDialog();

                    if (result != null) {
                        Type listType = new TypeToken<List<CycleStartDate>>() {
                        }.getType();

                        cycleStartDateList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);

                        setCycleStartDateSpinner(spinnerCycleStartDate, cycleStartDateList);
                        //   getCycleDetails(cycleStartDateList.get(0).getId());
                        if (cycleStartDateList.size() > 0) {
                            llCycleMain.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);

                        } else {
                            llCycleMain.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);

                        }


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                    llCycleMain.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();

                llCycleMain.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);

            }
        });
    }

    private void getDeliveryList(RecyclerView recyclerView, LinearLayout linearLayout, TextView textView, TextView SelectDate, TextView close, TextView save) {
        showProgressDialog(getActivity());

        NetworkUtility.makeJSONObjectRequest(API.GetCycleDeliveryDate + "?FacilityID=" + MyApplication.getPrefranceDataInt("ExFacilityID") + "&CycleStartDate=" + cycleStartDate, new JSONObject(), API.GetFacilityCycleDetails, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    dismissProgressDialog();

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelCycleDeliveryDate>>() {
                        }.getType();
                        if (modelCycleDeliveryDates.size() > 0) {
                            modelCycleDeliveryDates.clear();
                        }
                        List<ModelCycleDeliveryDate> modelCycleDeliveryList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);

                        for (int i = 0; i < modelCycleDeliveryList.size(); i++) {
                            ModelCycleDeliveryDate modelCycleDeliveryDate = new ModelCycleDeliveryDate();
                            modelCycleDeliveryDate.setPreviousDay(modelCycleDeliveryList.get(i).getPreviousDay());
                            modelCycleDeliveryDate.setPreviousDaystr(modelCycleDeliveryList.get(i).getPreviousDaystr());
                            modelCycleDeliveryDate.setPreviousDaywithdate(modelCycleDeliveryList.get(i).getPreviousDaywithdate());
                            modelCycleDeliveryDate.setPreviousDaywithdateName(modelCycleDeliveryList.get(i).getPreviousDaywithdateName());
                            if (modelCycleDeliveryList.get(i).getPreviousDaywithdate().equals(cycleDate.getText().toString())) {
                                modelCycleDeliveryDate.setSelected(true);
                            } else {
                                modelCycleDeliveryDate.setSelected(false);
                            }

                            modelCycleDeliveryDates.add(modelCycleDeliveryDate);
                        }
                        Collections.reverse(modelCycleDeliveryDates);
                        adapter = new CycleDeliveryAdapter(CycleActivity.this, modelCycleDeliveryDates, cycleDate.getText().toString());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        linearLayout.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        SelectDate.setVisibility(View.VISIBLE);
                        close.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);

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


    //    public void getSelctedDate(ModelCycleDeliveryDate item) {
//        deliveryDate = item.getPreviousDaywithdate();
//
//    }
    public void getSelctedDateNew(String item) {
        deliveryDate = item;

    }

    private void getCycleDetails(int id, Dialog dialog) {
        showProgressDialog(getActivity());

        NetworkUtility.makeJSONObjectRequest(API.GetCycleHeaderDetails + "?HeaderID=" + id, new JSONObject(), API.GetFacilityCycleDetails, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    dismissProgressDialog();

                    if (result != null) {
                        Log.e("Call", "Call");

                        cycleDate.setText(result.getJSONObject("Data").getString("DeliveryDate"));
                        cycleTime.setText(result.getJSONObject("Data").getString("DeliveryTime"));
                        changedBy.setText(result.getJSONObject("Data").getString("LastdeliverydateUpdatedBy"));
                        changedOn.setText(result.getJSONObject("Data").getString("LastdeliverydateUpdatedOn"));
                        pendingDays = result.getJSONObject("Data").getInt("PendingDays");
                        cycleStartDate = result.getJSONObject("Data").getString("CycleStartDate");
                        llCycleMain.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                        Type listType = new TypeToken<List<ModelCycleDeliveryDate>>() {
                        }.getType();

                        modelNextCycleDeliveryDateList = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("cyclenextdates").toString(), listType);

                            if (pendingDays >= 21) {
                            btnUpdate.setVisibility(View.VISIBLE);
                            btnUpdate.setText("Set Delivery");
                        } else {
                            btnUpdate.setVisibility(View.VISIBLE);
                            btnUpdate.setText("Update Delivery");

                        }


                        Log.e("Pending Days,", String.valueOf(pendingDays));


                        dialog.dismiss();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();


                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();

            }
        });
    }

    public void setCycleStartDateSpinner(final Spinner spinner, List<CycleStartDate> list) {

        final ArrayAdapter<CycleStartDate> cycleStartDateArrayAdapter = new ArrayAdapter<CycleStartDate>
                (getActivity(), R.layout.simple_spinner_item,
                        list);

        cycleStartDateArrayAdapter.setDropDownViewResource(R.layout
                .spinner_dropdown_item);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setAdapter(cycleStartDateArrayAdapter);//setting the adapter data into the AutoCompleteTextView
            }
        });
    }


    private void showDialog() {
        Dialog dialogBuilder = new Dialog(getActivity(), R.style.DialogStyle);
        LayoutInflater inflater = ((BaseActivity) getActivity()).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_cycle_delivery_date, null);
        dialogBuilder.setContentView(dialogView);
        List<String> time = new ArrayList<>();

        RecyclerView rv_cycle_delivery_date = (RecyclerView) dialogView.findViewById(R.id.rv_cycle_delivery_date);
        Spinner spinner_cycle_start_date = (Spinner) dialogView.findViewById(R.id.spinner_cycle_start_date);
        Button tv_save = (Button) dialogView.findViewById(R.id.tv_save);
        TextView tv_close = (TextView) dialogView.findViewById(R.id.tv_close);
        TextView lable_time = (TextView) dialogView.findViewById(R.id.lable_time);
        TextView lable_select_date = (TextView) dialogView.findViewById(R.id.lable_select_date);
        TextView tv_notes = (TextView) dialogView.findViewById(R.id.tv_notes);
        LinearLayout ll_spinner_time = (LinearLayout) dialogView.findViewById(R.id.ll_spinner_time);
        time.add("10:00 AM-03:00 PM");
        time.add("03:00 PM-08:00 PM");
        initRecyclerView(rv_cycle_delivery_date);
        getDeliveryList(rv_cycle_delivery_date, ll_spinner_time, lable_time, lable_select_date, tv_close, tv_save);
        setTimeSpinner(spinner_cycle_start_date, time);

        if (pendingDays < 10) {
            tv_save.setVisibility(View.GONE);
            tv_notes.setVisibility(View.VISIBLE);
            tv_save.setEnabled(false);
            tv_save.setBackground(getResources().getDrawable(R.drawable.button_bg_solid));

        } else {
            tv_notes.setVisibility(View.GONE);
            tv_save.setVisibility(View.VISIBLE);
            tv_save.setEnabled(true);
            tv_save.setBackground(getResources().getDrawable(R.drawable.shape_rounbded_squre_top_cycle));

        }

        for (int i = 0; i < time.size(); i++) {

            if (time.get(i).equals(cycleTime.getText().toString())) {
                spinner_cycle_start_date.setSelection(i);
            }
        }
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdateDelivery(deliveryDate, spinner_cycle_start_date.getSelectedItem().toString());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCycleDetails(HeaderId, dialogBuilder);


                    }
                }, 1000);
            }
        });
        dialogBuilder.show();
    }

    private void showNextCycleDateDialog() {
        Dialog dialogBuilder = new Dialog(getActivity(), R.style.DialogStyle);
        LayoutInflater inflater = ((BaseActivity) getActivity()).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_next_cycle_delivery_date, null);
        dialogBuilder.setContentView(dialogView);
        RecyclerView rv_cycle_delivery_date = (RecyclerView) dialogView.findViewById(R.id.rv_cycle_delivery_date);
        nextCycleDeliveryDateAdapter = new NextCycleDeliveryDateAdapter(CycleActivity.this, modelNextCycleDeliveryDateList);
        initRecyclerViewForNextCycle(rv_cycle_delivery_date);
        rv_cycle_delivery_date.setAdapter(nextCycleDeliveryDateAdapter);
        nextCycleDeliveryDateAdapter.notifyDataSetChanged();
        dialogBuilder.show();


    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

    private void initRecyclerViewForNextCycle(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setTimeSpinner(final Spinner spinner, List<String> list) {

        final ArrayAdapter<String> cycleStartDateArrayAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.simple_spinner_item,
                        list);

        cycleStartDateArrayAdapter.setDropDownViewResource(R.layout
                .spinner_dropdown_item);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setAdapter(cycleStartDateArrayAdapter);//setting the adapter data into the AutoCompleteTextView
            }
        });
    }

    private void saveUpdateDelivery(String DeliveryDate, String DeliveryTime) {
        Log.e("Step", "==============4");
        showProgressDialog(getActivity());
        JSONObject param = new JSONObject();
        try {
            param.put("HeaderID", HeaderId);
            param.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
            if (DeliveryDate.equalsIgnoreCase("")) {
                for (int i = 0; i < modelCycleDeliveryDates.size(); i++) {
                    if (modelCycleDeliveryDates.get(i).isSelected()) {
                        DeliveryDate = modelCycleDeliveryDates.get(i).getPreviousDaywithdate();
                    }
                }
            }
            param.put("DeliveryDate", DeliveryDate);
            param.put("DeliveryTime", DeliveryTime);
            param.put("CycleStartDate", cycleStartDate);
            param.put("UserID", MyApplication.getPrefranceData("UserID"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        NetworkUtility.makeJSONObjectRequest(API.UpdateCycleDeliveryDate, param, API.UpdateCycleDeliveryDate, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    dismissProgressDialog();

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();

            }
        });
    }


}
