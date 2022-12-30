package com.rn.android.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.metiz.pelconnect.R;
import com.rn.android.MyApplication;
import com.rn.android.BaseActivity;

import com.rn.android.model.Delivery;
import com.rn.android.model.Order;
import com.rn.android.model.PatientPOJO;
import com.rn.android.model.PrescriptionData;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.RangeTimePickerDialog;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddOrderActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.delivery_time)
    TextView delivery_time;
    @BindView(R.id.tv_player_team_portal)
    TextView tvPlayerTeamPortal;
    @BindView(R.id.spinner_order)
    Spinner spinnerOrder;
    @BindView(R.id.spinner_delivery)
    Spinner spinnerDelivery;
    @BindView(R.id.auto_patient)
    AutoCompleteTextView autoPatient;
    @BindView(R.id.edt_drug)
    AutoCompleteTextView edtDrug;
    @BindView(R.id.img_barcode_drug)
    ImageView imgBarcodeDrug;
    @BindView(R.id.img_barcode_rx)
    ImageView imgBarcodeRx;
    @BindView(R.id.edt_notes)
    EditText edtNotes;
    @BindView(R.id.img_promotional_item)
    ImageView imgPromotionalItem;
    @BindView(R.id.btn_dialog_add)
    TextView btnDialogAdd;
    @BindView(R.id.first)
    CardView first;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    @BindView(R.id.edt_rx_number)
    EditText edtRxNumber;
    @BindView(R.id.edt_qty)
    EditText edtQty;
    private String base64 = null;
    private int SCANNED_BARCODE_ID = 0;
    private List<Order> orderlist = new ArrayList<>();
    ProgressDialog progressDialog;
    private List<Delivery> deliverylist = new ArrayList<>();
    List<PatientPOJO> patientList = new ArrayList<>();
    private PatientPOJO patient = new PatientPOJO();
    private List<PrescriptionData> prescriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        initProgress();
        getPatientList(MyApplication.getPrefranceDataInt("facility"));
        getOrderndDelivery();
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogForImage();

//                PickImageDialog.build(new PickSetup())
//                        .setOnPickCancel(new IPickCancel() {
//                            @Override
//                            public void onCancelClick() {
//
//                            }
//                        })
//                        .setOnPickResult(new IPickResult() {
//                            @Override
//                            public void onPickResult(PickResult r) {
//                                //TODO: do what you have to...
//
//                                if (r.getBitmap() != null) {
//                                    imgPromotionalItem.setImageBitmap(r.getBitmap());
//                                    try {
//                                        progressDialog.show();
//                                        base64 = Utils.convertImageToBase64(r.getBitmap());
//                                        progressDialog.dismiss();
//                                    } catch (Exception ex) {
//                                        progressDialog.dismiss();
//                                    }
//                                }
//                            }
//                        }).show(AddOrderActivity.this);


            }
        });
        imgBarcodeDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SCANNED_BARCODE_ID = 1;
                setBarcode("scan drug");
            }
        });
        imgBarcodeRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SCANNED_BARCODE_ID = 0;
                setBarcode("scan Rx Number");
            }
        });

        autoPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                autoPatient.setEnabled(false);
                autoPatient.setText(patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getLastname() + "," + patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getFirstname()
//                        + " " + patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getDob()
                );
                autoPatient.setEnabled(true);
                edtRxNumber.requestFocus();
                getDrugandRx(patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getExternal_patient_id());
                patient = patientList.get(patientList.indexOf(((ListView) adapterView).getAdapter().getItem(i)));
            }
        });


        //   autoPatient.setHint("Patient Name");
        patient = null;

        autoPatient.setEnabled(false);
        autoPatient.setEnabled(true);
        autoPatient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0) {
                    edtDrug.setText("");
                    edtRxNumber.setText("");
                }

                patient = null;
            }
        });
        delivery_time.setText(Utils.getFormatedDate(new Date(), "hh:mm aa"));

        delivery_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCalendar = Calendar.getInstance();

                int hour = mCalendar.get(Calendar.HOUR_OF_DAY) + 4;
                int minute = mCalendar.get(Calendar.MINUTE);
                RangeTimePickerDialog mTimePicker;
                mTimePicker = new RangeTimePickerDialog(AddOrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        int hour = selectedHour;
                        int minutes = selectedMinute;
                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12) {
                            timeSet = "PM";
                        } else {
                            timeSet = "AM";
                        }

                        String min = "";
                        if (minutes < 10)
                            min = "0" + minutes;
                        else
                            min = String.valueOf(minutes);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hour).append(':')
                                .append(min).append(" ").append(timeSet).toString();
                        delivery_time.setText(aTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");

//                mTimePicker.setMin(hour, minute);

                mTimePicker.show();
            }
        });

        btnDialogAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoPatient.getText().toString().trim().length() > 0 && edtDrug.getText().toString().trim().length() > 0 && spinnerDelivery.getAdapter() != null && spinnerOrder.getAdapter() != null && edtQty.getText().toString().trim().length()>0) {
                    if (isPatientValid(autoPatient.getText().toString())) {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        JSONObject json = new JSONObject();
                                        try {
                                            json.put("Deliverytype", ((Delivery) spinnerDelivery.getAdapter().getItem(spinnerDelivery.getSelectedItemPosition())).getDeliveryID());
                                            json.put("Deliverydate", ((Delivery) spinnerDelivery.getAdapter().getItem(spinnerDelivery.getSelectedItemPosition())).getDeliveryDate());
                                            json.put("Ordertype", ((Order) spinnerOrder.getAdapter().getItem(spinnerOrder.getSelectedItemPosition())).getOrdertypeID());
                                            json.put("FacilityID", MyApplication.getPrefranceDataInt("ExFacilityID"));
                                            json.put("Rxnumber", edtRxNumber.getText().toString());
                                            json.put("PatientName", autoPatient.getText().toString());
                                            json.put("drug", edtDrug.getText().toString());
                                            json.put("CreatedBy", MyApplication.getPrefranceData("UserID"));
                                            json.put("UpdatedBy", MyApplication.getPrefranceData("UserID"));
                                            json.put("Note", edtNotes.getText().toString());
                                            json.put("qty", edtQty.getText().toString());
                                            // json.put("deliverytime", delivery_time.getText().toString());

                                            if (patient != null) {
                                                json.put("PatientID", patient.getExternal_patient_id());
                                            } else {
                                                json.put("PatientID", "0");
                                            }
                                            if (base64 != null) {
                                                json.put("DocumentPath", base64);
                                            }
                                        } catch (Exception ex) {

                                        }
                                        addOrderApicall(json);

                                        base64 = null;
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddOrderActivity.this);
                        builder.setMessage("Are you sure you want to order " + edtQty.getText().toString() +" Qty?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("Cancel", dialogClickListener).show();
                    }
                    else {
                        Utils.showAlertToast(AddOrderActivity.this, "Invalid Patient format [Ex: Lastname,Firstname]");
                    }
                } else {
                    Utils.showAlertToast(AddOrderActivity.this, "Some value are missing");
                }

            }
        });


    }


    private void dialogForImage() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked

                   /* PickImageDialog.build(new PickSetup())
                            .setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {
                                }
                            })
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    //TODO: do what you have to...

                                    if (r.getBitmap() != null) {
                                        imgPromotionalItem.setImageBitmap(r.getBitmap());
                                        try {
                                            progressDialog.show();
                                            base64 = Utils.convertImageToBase64(r.getBitmap());
                                            progressDialog.dismiss();
                                        } catch (Exception ex) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                }
                            }).show(AddOrderActivity.this);*/
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("I acknowledged that I fully HIPAA compliant to upload picture here.").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void setBarcode(String msg) {
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        integrator.setPrompt(msg);
        integrator.setBeepEnabled(true);

        integrator.setOrientationLocked(false);

        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        try {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

            if (result != null) {

                if (result.getContents() == null) {

//cancel

                } else {
                    Log.e("result", result.getContents() + "\n" + result.getBarcodeImagePath() + "\n" + result.getFormatName() + "\n" + result.getErrorCorrectionLevel());

                    if (SCANNED_BARCODE_ID == 0) {
                        edtRxNumber.setText(result.getContents());
                    } else {
                        edtDrug.setText(result.getContents());
                    }
//Scanned successfully

                }

            } else {

                super.onActivityResult(requestCode, resultCode, intent);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            super.onActivityResult(requestCode, resultCode, intent);
        }

    }

    private void initProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    public void getOrderndDelivery() {

        progressDialog.show();
        NetworkUtility.makeJSONObjectRequest(API.OrderDelivertype, new JSONObject(), API.OrderDelivertype, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (result != null) {


                        deliverylist = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Delivery_WorkingDay").toString(), new TypeToken<List<Delivery>>() {
                        }.getType());
                        orderlist = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Ordertype").toString(), new TypeToken<List<Order>>() {
                        }.getType());


                        ArrayAdapter<Order> orderAdapter = new ArrayAdapter<Order>(AddOrderActivity.this,
                                R.layout.simple_spinner_item, orderlist);
                        orderAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
                        spinnerOrder.setAdapter(orderAdapter);

                        ArrayAdapter<Delivery> dataAdapter = new ArrayAdapter<Delivery>(AddOrderActivity.this,
                                R.layout.simple_spinner_item, deliverylist);
                        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
                        spinnerDelivery.setAdapter(dataAdapter);


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

    private void getPatientList(int facilityID) {
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
                        //for soting list alphabetically by last name
                        Collections.sort(patientList, new Comparator<PatientPOJO>() {
                            @Override
                            public int compare(PatientPOJO lhs, PatientPOJO rhs) {
                                return lhs.getLastname().compareTo(rhs.getLastname());
                            }
                        });
                        SearchablePatientAdapter adapter = new SearchablePatientAdapter(AddOrderActivity.this, R.layout.simple_spinner_item, patientList);
                        autoPatient.setAdapter(adapter);
                        autoPatient.setThreshold(1);
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
                        SearchableDrugAdapter adapter = new SearchableDrugAdapter(AddOrderActivity.this, R.layout.simple_spinner_item, prescriptionList);
                        edtDrug.setAdapter(adapter);
                        edtDrug.setThreshold(1);
                        edtDrug.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                edtDrug.showDropDown();

                            }
                        });
                        edtDrug.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                edtDrug.setEnabled(false);
                                edtDrug.setText(prescriptionList.get(prescriptionList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getDrug());
                                edtDrug.setEnabled(true);
                                edtRxNumber.setText(prescriptionList.get(prescriptionList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getExternal_prescription_id());
                                edtQty.setText(prescriptionList.get(prescriptionList.indexOf(((ListView) adapterView).getAdapter().getItem(i))).getQty().toString());

//                                String substr = ".00";
//                                String[] parts = qty.split(substr);
//                                String before = parts[0];
//                                edtQty.setText(before);

//                                String str = "123dance456";
//                                String substr = "dance";
//                                String[] parts = str.split(substr);
//                                String before = parts[0];
//                                String after = parts[1];
//
//                                Log.e("before",before);
//                                Log.e("after",after);
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
                        Utils.showAlertToast(AddOrderActivity.this, result.getString("Message"));
                     //   sendNotification("Order", "Order received");
                        finish();

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

    private boolean isPatientValid(String value) {
        if (!value.contains(",")) {
            return false;
        } else {
            return true;
        }
    }


}
