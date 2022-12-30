package com.rn.android.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.collection.ArrayMap;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.DrugAdapter;
import com.rn.android.MyApplication;
import com.rn.android.model.Delivery;
import com.rn.android.model.DrugResponse;
import com.rn.android.model.Order;
import com.rn.android.model.PatientPOJO;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.network.VolleyStringBack;
import com.rn.android.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by hp on 13/2/18.
 */
public class DrugHistoryFragment extends BaseFragment {


    private static final String ARG_SECTION_NUMBER = "section_number";
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.patient_list)
    RecyclerView drugListView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_refill)
    TextView txtRefill;
    @BindView(R.id.txt_refill_count)
    TextView txtRefillCount;
    Unbinder unbinder;
    @BindView(R.id.txt_total_drug)
    TextView txtTotalDrug;
    private DrugAdapter drugAdapter;
    private Spinner spinner_delivery, spinner_order;
    private TextView edt_rx_number;
    private TextView edt_drug;
    private int SCANNED_BARCODE_ID = 0;
    private List<Delivery> deliverylist = new ArrayList<>();
    private List<Order> orderlist = new ArrayList<>();
    static String patientID;
    private EditText edt_notes;
    TextView txtTime;
    private TagContainerLayout tags;
    public DrugHistoryFragment() {
    }

    public static DrugHistoryFragment newInstance(String patientID) {
        DrugHistoryFragment fragment = new DrugHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, patientID);
        fragment.setArguments(args);
        patientID = patientID;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drug_history, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDrugList();
//                getPatientList(Application.getPrefranceDataInt("facility"));
            }
        });
        drugListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDrugList();
        return rootView;
    }
    List<DrugResponse> drugList = new ArrayList<>();
    public void updateRefillText(int i) {
        if (drugList != null && drugList.size() > 0) {
            txtRefillCount.setText("(" + i + "/" + drugList.size() + ")");
        } else {
            txtRefillCount.setText("");
        }
    }
    private void getDrugList() {


        Log.e("Step", "==============4");
        if (!swipeRefreshLayout.isRefreshing())
            showProgressDialog(getActivity());

        NetworkUtility.makeArrayResponseReq(API.GetPrescriptionDataByPatient + getArguments().getString(ARG_SECTION_NUMBER), new ArrayMap<String, String>(), API.GetPrescriptionDataByPatient, new VolleyStringBack() {
            @Override
            public void onSuccess(String result) {
                try {

//                        if (result != null) {
//                            Type listType = new TypeToken<List<OrderHistory>>() {
//                            }.getType();
//
//                            orderList = Application.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
//
//                            orderHistoryAdapter = new OrderHistoryAdapter(getActivity(), orderList);
//                            recyclerView.setAdapter(orderHistoryAdapter);
//

                    drugList = MyApplication.getGson().fromJson(result, new TypeToken<List<DrugResponse>>() {
                    }.getType());

                    try {
                        Collections.sort(drugList);
                        Collections.reverse(drugList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    drugListView.setAdapter(new DrugAdapter(getActivity(), drugList, DrugHistoryFragment.this));
                    dismissProgressDialog();

                    txtTotalDrug.setText("Total: " + drugList.size());


                } catch (Exception ex) {
                    ex.printStackTrace();
                    dismissProgressDialog();
                    swipeRefreshLayout.setRefreshing(false);
                    txtTotalDrug.setText("Total: " + drugList.size());
                }
            }

            @Override
            public void onError(String result) {
                swipeRefreshLayout.setRefreshing(false);
                dismissProgressDialog();
                txtTotalDrug.setText("Total: " + drugList.size());

            }
        });


        drugAdapter = new DrugAdapter(getActivity(), drugList, DrugHistoryFragment.this);
        drugListView.setAdapter(drugAdapter);
        swipeRefreshLayout.setRefreshing(false);
        txtTotalDrug.setText("Total: " + drugList.size());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private int getRemainingQty(DrugResponse drugResponse) {

        return (int) ((Float.parseFloat(drugResponse.getLast_qty_approved()) - Float.parseFloat(drugResponse.getLast_qty_billed())) / Double.parseDouble(drugResponse.getQty()));
    }

    private void openAddOrderDialog(final PatientPOJO patientPojo) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_add_drug_new);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        Button add = (Button) dialog.findViewById(R.id.btn_dialog_add);
        Button cancel = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
        tags = (TagContainerLayout) dialog.findViewById(R.id.tag_view);
        txtTime = (TextView) dialog.findViewById(R.id.tv_time_picker);
        txtTime.setText(Utils.getFormatedDate(new Date(),"hh:mm aa"));

        final TagContainerLayout rx_tag = (TagContainerLayout) dialog.findViewById(R.id.rx_tag);
        tags.setTagTextSize(24f);
        rx_tag.setTagTextSize(24f);
        tags.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {
                
            }

            @Override
            public void onTagCrossClick(int position) {


                for (int i = 0; i < drugList.size(); i++) {
                    if (tags.getTags().get(position).equals(drugList.get(i).getDrug())) {
                        drugList.get(i).setChecked(false);
                        tags.removeTag(position);
                        rx_tag.removeTag(position);
                        break;
                    }
                }
                if (tags.getChildCount() == 0) {
                    dialog.dismiss();
                    updateRefillText(0);
                    getDrugList();
                }
            }
        });
        ImageView img_barcode_drug = (ImageView) dialog.findViewById(R.id.img_barcode_drug);
        ImageView img_barcode_rx = (ImageView) dialog.findViewById(R.id.img_barcode_rx);
        edt_rx_number = (TextView) dialog.findViewById(R.id.edt_rx_number);
        img_barcode_drug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SCANNED_BARCODE_ID = 1;
                setBarcode("scan drug");
            }
        });
        img_barcode_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SCANNED_BARCODE_ID = 0;
                setBarcode("scan Rx Number");
            }
        });
        edt_drug = (TextView) dialog.findViewById(R.id.edt_drug);
        final TextView txt_dialog_facility = (TextView) dialog.findViewById(R.id.txt_dialog_facility);
//        if (imgChange.getVisibility() == View.VISIBLE) {
//            txt_dialog_facility.setText(facilityList.get(Application.getPrefranceDataInt("facility_selected_index")).getFacilityName());
//        }
        spinner_delivery = (Spinner) dialog.findViewById(R.id.spinner_delivery);
        spinner_order = (Spinner) dialog.findViewById(R.id.spinner_order);
        edt_notes = (EditText) dialog.findViewById(R.id.edt_notes);
        ArrayAdapter<Order> orderAdapter = new ArrayAdapter<Order>(getActivity(),
                R.layout.simple_spinner_item, orderlist);
        orderAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner_order.setAdapter(orderAdapter);
        ArrayAdapter<Delivery> dataAdapter = new ArrayAdapter<Delivery>(getActivity(),
                R.layout.simple_spinner_item, deliverylist);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner_delivery.setAdapter(dataAdapter);
        String drufStr = "";
        for (int i = 0; i < drugList.size(); i++) {
            if (drugList.get(i).isChecked()) {
                tags.addTag(drugList.get(i).getDrug());
                rx_tag.addTag(drugList.get(i).getExternal_prescription_id());

            }
        }
            if (!drufStr.isEmpty()){
                edt_drug.setText(drufStr.substring(0, drufStr.length() - 1));

            }




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTime.getText().toString().isEmpty()) {
                    Utils.showAlertToast(getActivity(), "Please select delivery time");
                    return;
                }
                dialog.dismiss();
                sumbitRefillOrder();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getDrugList();
            }
        });
        dialog.show();
    }
    private void sumbitRefillOrder() {
        showProgressDialog(getActivity());
        JSONArray jsonArray = new JSONArray();

        try {

            for (int i = 0; i < drugList.size(); i++) {
                if (drugList.get(i).isChecked) {

                    JSONObject param = new JSONObject();

                    param.put("Deliverytype", ((Delivery) spinner_delivery.getAdapter().getItem(spinner_delivery.getSelectedItemPosition())).getDeliveryID());
                    param.put("Ordertype", getOrderType(orderlist));
                    param.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
                    param.put("Rxnumber", drugList.get(i).getPharmacy_order_id());
                    param.put("RxID", drugList.get(i).getExternal_prescription_id());
                    param.put("PatientName", ((TabActivity) getActivity()).patient.getLastname() + "," + ((TabActivity) getActivity()).patient.getFirstname());
                    param.put("drug", drugList.get(i).getDrug());
                    param.put("CreatedBy", MyApplication.getPrefranceData("UserID"));
                    param.put("UpdatedBy", MyApplication.getPrefranceData("UserID"));
                    param.put("Note", edt_notes.getText().toString());
                    param.put("PatientID", TabActivity.patient.getExternal_patient_id());
                    param.put("deliverytime", txtTime.getText().toString());
                    param.put("RefillQTY", drugList.get(i).getQty());
                    param.put("qty", drugList.get(i).getQty());
                    param.put("StoreName","Refill");

                    if(new DecimalFormat().format(getRemainingQty(drugList.get(i))).equalsIgnoreCase("0"))
                    {
                        param.put("RefillRequest","Facility is requesting this rx: " + drugList.get(i).getPharmacy_order_id()  + " to contact the doctor for a refill.");
                    }
                    if (drugList.get(i).getLast_pickedup_date() != null)
                        param.put("LastDeliveredDate", drugList.get(i).getLast_pickedup_date());
                    else
                        param.put("LastDeliveredDate", "null");
                    param.put("DaySupply", drugList.get(i).getDays_supply());
                    jsonArray.put(param);
                }
            }
        } catch (Exception r) {

        }
        NetworkUtility.makeJSONArrayRequest(API.AddRefillorder, jsonArray, API.AddRefillorder, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    dismissProgressDialog();
                    if (result != null) {

                        Utils.showAlertToast(getActivity(), result.getString("Message"));

                        getDrugList();
                      //  sendNotification("Refill", "Refill order received.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
            }

        });

    }
    private String getOrderType(List<Order> orderlist) {

        String order_type = null;
        try {
            order_type = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < orderlist.size(); i++) {
            if (orderlist.get(i).getOrderType().equalsIgnoreCase("refill")) {
                order_type = orderlist.get(i).getOrdertypeID() + "";
                break;
            }
        }
        return order_type;
    }
    private void setBarcode(String msg) {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(msg);
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }
    public void getOrderndDelivery(final PatientPOJO patient) {
        showProgressDialog(getActivity());
        NetworkUtility.makeJSONObjectRequest(API.OrderDelivertype, new JSONObject(), API.OrderDelivertype, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    dismissProgressDialog();
                    if (result != null) {
                        List<Delivery> tmpDeliveryList = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Delivery").toString(), new TypeToken<List<Delivery>>() {
                        }.getType());
                        orderlist = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Ordertype").toString(), new TypeToken<List<Order>>() {
                        }.getType());
                        deliverylist.clear();
                        for (int i = 0; i < tmpDeliveryList.size(); i++) {
                            if (tmpDeliveryList.get(i).getDeliverytypeName().equalsIgnoreCase("Today") || tmpDeliveryList.get(i).getDeliverytypeName().equalsIgnoreCase("Tomorrow")) {

                                deliverylist.add(tmpDeliveryList.get(i));
                            }
                        }
                      openAddOrderDialog(patient);
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
    @OnClick(R.id.ll_refill_button)
    public void onViewClicked() {
        String drufStr = "";
        for (int i = 0; i < drugList.size(); i++) {
            if (drugList.get(i).isChecked()) {
                if (!drufStr.isEmpty())
                    drufStr = drufStr + i + ", ";
                else
                    drufStr = i + ", ";
            }
        }
        if (drufStr.isEmpty()) {
            Utils.showAlertToast(getActivity(), "Please select drug from above to refill");
            return;
        }
        getOrderndDelivery(TabActivity.patient);
    }
}
