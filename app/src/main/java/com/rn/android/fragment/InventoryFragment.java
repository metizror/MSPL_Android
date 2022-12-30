package com.rn.android.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.MedicationAdapter;
import com.rn.android.MyApplication;
import com.rn.android.activity.ReceivedDrugListActivity;
import com.rn.android.model.ModelDLNumber;
import com.rn.android.model.modelMedication;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends BaseFragment {
    @BindView(R.id.scan_barcode)
    SearchView scan_barcode;
    @BindView(R.id.tv_tab_taking_drug_list)
    TextView tvTabTakingDrugList;
    @BindView(R.id.tv_tab_drug_history)
    TextView tvTabDrugHistory;
    @BindView(R.id.edt_notes)
    TextView edt_notes;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_received)
    TextView tv_received;

    @BindView(R.id.rv_medication)
    RecyclerView rv_medication;
    @BindView(R.id.rv_received_drug)
    RecyclerView rv_received_drug;
    @BindView(R.id.ll_scanner)
    LinearLayout ll_scanner;

    @BindView(R.id.ll_medication_received)
    LinearLayout ll_medication_received;

    @BindView(R.id.ll_medication)
    LinearLayout ll_medication;

    @BindView(R.id.ll_bottom_note)
    LinearLayout ll_bottom_note;

    ArrayList<modelMedication> modelMedicationList = new ArrayList<>();
    MedicationAdapter medicationAdapter;

    private int SCANNED_BARCODE_ID = 0;
    ArrayList<modelMedication> receivedList = new ArrayList<>();
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    @BindView(R.id.ll_search_barcode)
    LinearLayout ll_search_barcode;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    List<ModelDLNumber> modelDLNumberList = new ArrayList<>();
    @BindView(R.id.spinner_delivery_number)
    Spinner spinnerDeliveryNumber;
    private int pos = 0;
    private int facilityId = 0;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    public InventoryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InventoryFragment(int facility) {
        // Required empty public constructor
        this.facilityId = facility;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        ButterKnife.bind(this, view);
        //  getDLNumber( Application.getPrefranceDataInt("ExFacilityID"));
        getDLNumber(MyApplication.getPrefranceDataInt("ExFacilityID"));
        scan_barcode.setQueryHint(Html.fromHtml("<font color = #AEAEAE>" + "Scan barcode" + "</font>"));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReceivedDrugListActivity.class));

            }
        });
        tvTabTakingDrugList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_tab_left));
                tvTabTakingDrugList.setTextColor(Color.WHITE);
                tvTabDrugHistory.setTextColor(Color.BLACK);
                tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.right_border_white));
                ll_medication.setVisibility(View.VISIBLE);
                ll_medication_received.setVisibility(View.GONE);
            }
        });
        tvTabDrugHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.squre_tab_right));
                tvTabDrugHistory.setTextColor(Color.WHITE);
                tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_with_radius));
                tvTabTakingDrugList.setTextColor(Color.BLACK);
                ll_medication.setVisibility(View.GONE);
                Utils.hide_keyboard(getActivity());
                getReceivedDrugList();
            }
        });
        ll_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SCANNED_BARCODE_ID = 1;
                setBarcode("Scan Rx Number");
            }
        });
        tv_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDrugReceiver1();

            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (receivedList.size() > 0) {
                    receivedList.clear();
                    edt_notes.setText("");
                    tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_tab_left));
                    tvTabTakingDrugList.setTextColor(Color.WHITE);
                    tvTabDrugHistory.setTextColor(Color.BLACK);
                    tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.right_border_white));
                    ll_medication.setVisibility(View.VISIBLE);
                    ll_medication_received.setVisibility(View.GONE);
                }
            }
        });

        ll_search_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (scan_barcode.getQuery().toString().trim().equalsIgnoreCase("")) {
                    Utils.showAlertToast(getActivity(), "Please enter Rx number");

                } else {
                    Utils.hideKeybord(getActivity());
                    int pos = 0;
                    boolean isRemove = false;
                    for (int i = 0; i < modelMedicationList.size(); i++) {
                        if (scan_barcode.getQuery().toString().trim().equalsIgnoreCase(modelMedicationList.get(i).getRx_No())) {
                            if (receivedList.size() > 0) {
                                boolean isExists = false;

                                for (int j = 0; j < receivedList.size(); j++) {
                                    if (receivedList.get(j).getRx_No().equalsIgnoreCase(scan_barcode.getQuery().toString().trim())) {
                                        isExists = true;

                                    }
                                }

                                if (!isExists) {
                                    receivedList.add(modelMedicationList.get(i));
                                    isRemove = true;
                                    pos = i;
                                }
                            } else
                                receivedList.add(modelMedicationList.get(i));
                            isRemove = true;
                            pos = i;


                        }
                    }

                    if (isRemove) {
                        modelMedicationList.remove(pos);

                    }
                }

            }
        });

        scan_barcode.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equalsIgnoreCase("")) {
                    Utils.showAlertToast(getActivity(), "Please enter Rx number");
                } else {
                    Utils.hideKeybord(getActivity());
                    int pos = 0;
                    boolean isRemove = false;
                    for (int i = 0; i < modelMedicationList.size(); i++) {
                        if (scan_barcode.getQuery().toString().trim().equalsIgnoreCase(modelMedicationList.get(i).getRx_No())) {
                            if (receivedList.size() > 0) {
                                boolean isExists = false;

                                for (int j = 0; j < receivedList.size(); j++) {
                                    if (receivedList.get(j).getRx_No().equalsIgnoreCase(scan_barcode.getQuery().toString().trim())) {
                                        isExists = true;

                                    }
                                }

                                if (!isExists) {
                                    receivedList.add(modelMedicationList.get(i));
                                    isRemove = true;
                                    pos = i;
                                }
                            } else
                                receivedList.add(modelMedicationList.get(i));
                            isRemove = true;
                            pos = i;


                        }
                    }

                    if (isRemove) {
                        modelMedicationList.remove(pos);

                    }

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });

        spinnerDeliveryNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                if (modelDLNumberList.size() > 0) {
                    if (position != 0) {
                        getMedicationList(modelDLNumberList.get(position).getDelivery_no().trim(), MyApplication.getPrefranceDataInt("ExFacilityID"));

                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos != 0) {
                    getMedicationList(modelDLNumberList.get(pos).getDelivery_no().toString().trim(), MyApplication.getPrefranceDataInt("ExFacilityID"));
                }


            }
        });

        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    public void setDLNumberList(final Spinner spinner, List<ModelDLNumber> list) {


        final ArrayAdapter<ModelDLNumber> stateArrayAdapter = new ArrayAdapter<ModelDLNumber>
                (getActivity(), R.layout.simple_spinner_item_inventory,
                        list); //selected item will look like a spinner set from XML
        stateArrayAdapter.setDropDownViewResource(R.layout
                .spinner_dropdown_item_inventory);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // autoCompleteTextView.setThreshold(1);//will start working from first character
                spinner.setAdapter(stateArrayAdapter);//setting the adapter data into the AutoCompleteTextView
            }
        });
    }

    private void AddDrugReceiver1() {
        showProgressDialog(getActivity());
        JSONObject param = new JSONObject();

        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(receivedList).getAsJsonArray();
        Log.e("JsonArry", myCustomArray.toString());

        try {
            param.put("delivery_no", MyApplication.getPrefranceData("deliveryNo"));
            param.put("CreatedBy", MyApplication.getPrefranceData("UserID"));//
            param.put("CreatedBy", MyApplication.getPrefranceData("UserID"));
            param.put("Group_id", "");
            param.put("Facility_id", MyApplication.getPrefranceDataInt("ExFacilityID"));
            param.put("Note", edt_notes.getText().toString());
            param.put("external_facilityid", MyApplication.getPrefranceDataInt("ExFacilityID"));
            param.put("lstdetails", myCustomArray);


        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        NetworkUtility.makeJSONObjectRequest(API.AddDrugReceiver, param, API.AddDrugReceiver, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    Log.e("result", result.toString());
                    if (receivedList.size() > 0) {
                        receivedList.clear();
                    }
                    edt_notes.setText("");
                    //    search_delivery_no.setQueryHint(Html.fromHtml("<font color = #AEAEAE>" + "Delivery No" + "</font>"));
                    //   scan_barcode.setQueryHint(Html.fromHtml("<font color = #AEAEAE>" + "Scan barcode" + "</font>"));
                    //  search_delivery_no.clearFocus();

                    tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_tab_left));
                    tvTabTakingDrugList.setTextColor(Color.WHITE);
                    tvTabDrugHistory.setTextColor(Color.BLACK);
                    tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.right_border_white));
                    ll_medication.setVisibility(View.VISIBLE);
                    ll_medication_received.setVisibility(View.GONE);
                    Utils.hide_keyboard(getActivity());

                    dismissProgressDialog();
                } catch (Exception e) {
                    dismissProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
            }
        });

    }


    private void setBarcode(String msg) {

//        IntentIntegrator integrator = new IntentIntegrator(getActivity());
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        integrator.setPrompt(msg);
//        integrator.setBeepEnabled(true);
//
//        integrator.setOrientationLocked(false);
//
//        integrator.initiateScan();

        IntentIntegrator.forSupportFragment(this).initiateScan();

    }


    private void getMedicationList(String deliveryNum, int facilityID) {
        Log.e("Step", "==============4");
        showProgressDialog(getActivity());


        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        NetworkUtility.makeJSONObjectRequest(API.DrugReceiver + "?deliveryno=" + deliveryNum + "&external_facilityid=" + facilityID, new JSONObject(), API.DrugReceiver, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    if (modelMedicationList.size() > 0) {
                        modelMedicationList.clear();
                    }
                    if (result != null) {


                        Type listType = new TypeToken<List<modelMedication>>() {
                        }.getType();

                        if (result.getJSONArray("Data").length() > 0) {

                            rv_medication.setLayoutManager(new LinearLayoutManager(getActivity()));
                            modelMedicationList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                            medicationAdapter = new MedicationAdapter(getActivity(), modelMedicationList);
                            rv_medication.setAdapter(medicationAdapter);


                            MyApplication.setPreferences("deliveryNo", modelDLNumberList.get(pos).getDelivery_no());
                            dismissProgressDialog();
                            Utils.hide_keyboard(getActivity());
                        } else {
                            dismissProgressDialog();
                            Utils.hide_keyboard(getActivity());

                            Utils.showAlertToast(getActivity(), "No record found");
                        }

                    } else {
                        dismissProgressDialog();
                        Utils.hide_keyboard(getActivity());

                        Utils.showAlertToast(getActivity(), "No record found");
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

    private void getReceivedDrugList() {

        if (receivedList.size() > 0) {
            rv_received_drug.setLayoutManager(new LinearLayoutManager(getActivity()));
            medicationAdapter = new MedicationAdapter(getActivity(), receivedList);
            rv_received_drug.setAdapter(medicationAdapter);
            ll_medication_received.setVisibility(View.VISIBLE);
            ll_bottom_note.setVisibility(View.VISIBLE);
        } else {
            ll_medication_received.setVisibility(View.VISIBLE);
            ll_bottom_note.setVisibility(View.GONE);

        }
    }

    private void getDLNumber(int facilityID) {
        Log.e("Step", "==============4");
        showProgressDialog(getActivity());


        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        NetworkUtility.makeJSONObjectRequest(API.Getdeliverynobyfacility + "?facilityid=" + facilityID, new JSONObject(), API.Getdeliverynobyfacility, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();

                try {

                    if (modelDLNumberList.size() > 0) {
                        modelDLNumberList.clear();
                    }
                    if (result != null) {


                        Type listType = new TypeToken<List<ModelDLNumber>>() {
                        }.getType();

                        if (result.getJSONArray("Data").length() > 0) {

                            modelDLNumberList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);

                            ModelDLNumber modelDLNumber = new ModelDLNumber();
                            modelDLNumber.setDelivery_no("Select Delivery Number");
                            modelDLNumberList.add(0, modelDLNumber);


                            setDLNumberList(spinnerDeliveryNumber, modelDLNumberList);
                        }
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        try {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (result != null) {
                Utils.hideKeybord(getActivity());
                //  search_delivery_no.clearFocus();
                scan_barcode.clearFocus();

                if (result.getContents() == null) {
                    Utils.showAlertToast(getActivity(), "No result found");
                } else {
                    Log.e("Rx Number0", result.getContents());
                    Utils.hide_keyboard(getActivity());
                    if (SCANNED_BARCODE_ID == 0) {

                        Log.e("Rx Number", result.getContents());

                        Utils.hideKeybord(getActivity());
                        int pos = 0;
                        boolean isRemove = false;
                        for (int i = 0; i < modelMedicationList.size(); i++) {
                            if (result.getContents().equalsIgnoreCase(modelMedicationList.get(i).getRx_No())) {
                                if (receivedList.size() > 0) {
                                    boolean isExists = false;

                                    for (int j = 0; j < receivedList.size(); j++) {
                                        if (receivedList.get(j).getRx_No().equalsIgnoreCase(result.getContents())) {
                                            isExists = true;
                                        }
                                    }

                                    if (!isExists) {
                                        receivedList.add(modelMedicationList.get(i));
                                        isRemove = true;
                                        pos = i;

                                    }
                                } else
                                    receivedList.add(modelMedicationList.get(i));
                                isRemove = true;
                                pos = i;

                            }

                            //   getReceivedDrugList();
                        }
                        if (isRemove) {
                            modelMedicationList.remove(pos);
                        }
                        Log.e("List", MyApplication.getGson().toJson(receivedList));

                    } else {
                        int pos = 0;
                        boolean isRemove = false;
                        Log.e("Rx Number1", result.getContents());
//                        tvTabDrugHistory.setBackground(getResources().getDrawable(R.drawable.squre_tab_right));
//                        tvTabDrugHistory.setTextColor(Color.WHITE);
//                        tvTabTakingDrugList.setBackground(getResources().getDrawable(R.drawable.squre_with_radius));
//                        tvTabTakingDrugList.setTextColor(Color.BLACK);
//                        ll_medication.setVisibility(View.GONE);
                        Utils.hide_keyboard(getActivity());

                        for (int i = 0; i < modelMedicationList.size(); i++) {
                            if (result.getContents().equalsIgnoreCase(modelMedicationList.get(i).getRx_No())) {
                                if (receivedList.size() > 0) {
                                    boolean isExists = false;

                                    for (int j = 0; j < receivedList.size(); j++) {
                                        if (receivedList.get(j).getRx_No().equalsIgnoreCase(result.getContents())) {
                                            isExists = true;
                                        }
                                    }
                                    if (!isExists) {
                                        receivedList.add(modelMedicationList.get(i));
                                        isRemove = true;
                                    }

                                } else
                                    receivedList.add(modelMedicationList.get(i));
                                isRemove = true;

                            }

                        }
                        if (isRemove) {
                            modelMedicationList.remove(pos);
                        }
                        Log.e("List1234", MyApplication.getGson().toJson(receivedList));
                    }
                }

            } else {
                super.onActivityResult(requestCode, resultCode, intent);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            super.onActivityResult(requestCode, resultCode, intent);
        }

    }


}
