package com.rn.android;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.metiz.pelconnect.R;
import com.rn.android.model.Delivery;
import com.rn.android.model.Order;
import com.rn.android.model.PatientPOJO;
import com.rn.android.model.PrescriptionData;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.ItemClickSupport;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FragmentAddOrder extends Fragment {
    private Activity mActivity;

    public static FragmentAddOrder newInstance() {
        FragmentAddOrder fragment = new FragmentAddOrder();

        return fragment;
    }

    private ProgressDialog progressDialog;

    private RecyclerView patientListView;

    private SearchView search;
    List<PatientPOJO> patientList = new ArrayList<>();
    private PatientAdapter patientAdapter;
    FloatingActionButton fab;
    private EditText edt_rx_number;
    private AutoCompleteTextView edt_drug;
    private List<Delivery> deliverylist = new ArrayList<>();
    private List<Order> orderlist = new ArrayList<>();
    private Spinner spinner_delivery, spinner_order;
    private int SCANNED_BARCODE_ID = 0;
    private List<PrescriptionData> prescriptionList;
    private PatientPOJO patient = new PatientPOJO();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_order, container, false);
        initView(view);
        initProgress();
        return view;
    }

    private void initProgress() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    private void initView(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        search = (SearchView) view.findViewById(R.id.search);
        patientListView = (RecyclerView) view.findViewById(R.id.patient_list);
        patientListView.setLayoutManager(new LinearLayoutManager(mActivity));

        search.setQueryHint("Search Patient");
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        Utils.hideKeybord(mActivity);
        search.setVisibility(View.GONE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())) {
                    if (patientListView.getAdapter() != null) {
                        ((PatientAdapter) patientListView.getAdapter()).filter(newText);
                    }
                } else {
                    ((PatientAdapter) patientListView.getAdapter()).filter("");

                }
                return false;
            }
        });

        patientListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                getOrderndDelivery(null);
            }
        });


        ItemClickSupport.addTo(patientListView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                PatientPOJO patientPOJO = ((PatientAdapter) recyclerView.getAdapter()).filterList.get(position);
                getOrderndDelivery(patientPOJO);
            }
        });


    }

    public void setRxText(String st) {
        edt_rx_number.setText(st);
    }

    public void setDrugText(String st) {
        edt_drug.setText(st);
    }

    private void getOrderndDelivery(final PatientPOJO patient) {
        progressDialog.show();


        NetworkUtility.makeJSONObjectRequest(API.OrderDelivertype, new JSONObject(), API.OrderDelivertype, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (result != null) {

                        List<Delivery> tmpDeliveryList = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Delivery").toString(), new TypeToken<List<Delivery>>() {
                        }.getType());
                        orderlist = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Ordertype").toString(), new TypeToken<List<Order>>() {
                        }.getType());

                        deliverylist.clear();
                        for (int i = 0; i < tmpDeliveryList.size(); i++) {
                            if (tmpDeliveryList.get(i).getDeliverytypeName().equalsIgnoreCase("Today") || tmpDeliveryList.get(i).getDeliverytypeName().equalsIgnoreCase("Tomorrow")){

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
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        });
    }

    public void getPatientList(int facilityID) {
        Log.e("Step", "==============4");
        progressDialog.show();
        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        NetworkUtility.makeJSONObjectRequest(API.Patient_List_URL + "?FacilityID=" + facilityID, new JSONObject(), API.Patient_List_URL, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    patientList.clear();

                    if (result != null) {
                        Type listType = new TypeToken<List<PatientPOJO>>() {
                        }.getType();

                        patientList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);

                        patientAdapter = new PatientAdapter(mActivity, patientList);
                        patientListView.setAdapter(patientAdapter);

                        //for soting list alphabetically by last name
                        Collections.sort(patientList, new Comparator<PatientPOJO>() {
                            @Override
                            public int compare(PatientPOJO lhs, PatientPOJO rhs) {
                                return lhs.getLastname().compareTo(rhs.getLastname());
                            }
                        });
                        search.setVisibility(View.VISIBLE);
                        search.clearFocus();
                        Utils.hideKeybord(mActivity);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onError(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void openAddOrderDialog(final PatientPOJO patientPojo) {

        final Dialog dialog = new Dialog(mActivity);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_add_order);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        Button add = (Button) dialog.findViewById(R.id.btn_dialog_add);
        Button cancel = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
        ImageView img_barcode_drug = (ImageView) dialog.findViewById(R.id.img_barcode_drug);
        ImageView img_barcode_rx = (ImageView) dialog.findViewById(R.id.img_barcode_rx);
        edt_rx_number = (EditText) dialog.findViewById(R.id.edt_rx_number);
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
        edt_drug = (AutoCompleteTextView) dialog.findViewById(R.id.edt_drug);


        final TextView txt_dialog_facility = (TextView) dialog.findViewById(R.id.txt_dialog_facility);

        txt_dialog_facility.setText(((MainActivity) mActivity).toolbar_facility_name.getText().toString());

        spinner_delivery = (Spinner) dialog.findViewById(R.id.spinner_delivery);
        spinner_order = (Spinner) dialog.findViewById(R.id.spinner_order);
        final EditText edt_notes = (EditText) dialog.findViewById(R.id.edt_notes);

        ArrayAdapter<Order> orderAdapter = new ArrayAdapter<Order>(mActivity,
                R.layout.simple_spinner_item, orderlist);
        orderAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner_order.setAdapter(orderAdapter);

        ArrayAdapter<Delivery> dataAdapter = new ArrayAdapter<Delivery>(mActivity,
                R.layout.simple_spinner_item, deliverylist);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner_delivery.setAdapter(dataAdapter);

        final AutoCompleteTextView auto_patient = (AutoCompleteTextView) dialog.findViewById(R.id.auto_patient);

        SearchablePatientAdapter adapter = new SearchablePatientAdapter(mActivity, R.layout.simple_spinner_item, patientList);
        auto_patient.setAdapter(adapter);
        auto_patient.setThreshold(1);

        auto_patient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                auto_patient.setEnabled(false);
                auto_patient.setText(patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getLastname() + "," + patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getFirstname()
//                        + " " + patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getDob()
                );
                auto_patient.setEnabled(true);
                edt_rx_number.requestFocus();
                getDrugandRx(patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getExternal_patient_id());
                patient = patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i)));
            }
        });


        if (patientPojo != null) {
            auto_patient.setText(patientPojo.getLastname() + "," + patientPojo.getFirstname());
            getDrugandRx(patientPojo.getExternal_patient_id());
            patient = patientPojo;
        } else {
            auto_patient.setText("Patient Name");
            patient = null;
        }
        auto_patient.setEnabled(false);
        auto_patient.setEnabled(true);
        auto_patient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0) {
                    edt_drug.setText("");
                    edt_rx_number.setText("");
                }

                patient = null;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auto_patient.getText().toString().trim().length() > 0 && edt_drug.getText().toString().trim().length() > 0 && spinner_delivery.getAdapter() != null && spinner_order.getAdapter() != null) {
                    if (isPatientValid(auto_patient.getText().toString())) {
                        JSONObject json = new JSONObject();
                        try {
                            json.put("Deliverytype", ((Delivery) spinner_delivery.getAdapter().getItem(spinner_delivery.getSelectedItemPosition())).getDeliveryID());
                            json.put("Ordertype", ((Order) spinner_order.getAdapter().getItem(spinner_order.getSelectedItemPosition())).getOrdertypeID());
                            json.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
                            json.put("Rxnumber", edt_rx_number.getText().toString());
                            json.put("PatientName", auto_patient.getText().toString());
                            json.put("drug", edt_drug.getText().toString());
                            json.put("CreatedBy", MyApplication.getPrefranceData("UserID"));
                            json.put("UpdatedBy", MyApplication.getPrefranceData("UserID"));
                            json.put("Note", edt_notes.getText().toString());
                            if (patient != null) {
                                json.put("PatientID", patient.getExternal_patient_id());
                            } else {
                                json.put("PatientID", "0");
                            }
                        } catch (Exception ex) {

                        }
                        addOrderApicall(json);
                        dialog.dismiss();
                    } else {
                        Utils.showAlertToast(mActivity, "Invalid Patient format [Ex: Lastname,Firstname]");
                    }

                } else {
                    Utils.showAlertToast(mActivity, "Some value are missing");
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void setBarcode(String msg) {
        IntentIntegrator integrator = new IntentIntegrator(mActivity);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        integrator.setPrompt(msg);
        integrator.setBeepEnabled(true);

        integrator.setOrientationLocked(false);

        integrator.initiateScan();
    }

    private boolean isPatientValid(String value) {
        if (!value.contains(",")) {
            return false;
        } else {
            return true;
        }
    }

    private void getDrugandRx(String externalId) {
        progressDialog.show();
        NetworkUtility.makeJSONObjectRequest(API.PrescriptionData + "?ExternalPatientID=" + externalId, new JSONObject(), API.PrescriptionData, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();

                    try {
                        Type listType = new TypeToken<List<PrescriptionData>>() {
                        }.getType();
                        prescriptionList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                        SearchableDrugAdapter adapter = new SearchableDrugAdapter(mActivity, R.layout.simple_spinner_item, prescriptionList);
                        edt_drug.setAdapter(adapter);
                        edt_drug.setThreshold(1);
                        edt_drug.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                edt_drug.showDropDown();

                            }
                        });


                        edt_drug.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                edt_drug.setEnabled(false);
                                edt_drug.setText(prescriptionList.get(prescriptionList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getDrug());
                                edt_drug.setEnabled(true);
                                edt_rx_number.setText(prescriptionList.get(prescriptionList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getExternal_prescription_id());
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void addOrderApicall(JSONObject json) {
        progressDialog.show();
        NetworkUtility.makeJSONObjectRequest(API.AddOrder, json, API.AddOrder, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();

                    try {
                        Utils.showAlertToast(mActivity, result.getString("Message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

}
