package com.rn.android.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.rn.android.MyApplication;
import com.rn.android.R;
import com.rn.android.base.BaseDialog;
import com.rn.android.listeners.LoaAddedListener;
import com.rn.android.model.AlloyHospital;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlloyHospitalDialog extends BaseDialog {
    private Context context;
    /*  @BindView(R.id.rl_items)
      RecyclerView rl_items;*/
    @BindView(R.id.tvspinner)
    TextView tvspinner;
    @BindView(R.id.spList)
    Spinner spList;
    private List<AlloyHospital.LOAHospital> loaHospitalList;
    private int PatientId = 0;
    @BindView(R.id.et_note)
    EditText et_note;
    @BindView(R.id.tv_patient_name)
    TextView tv_patient_name;
    String note = "";
    int loaHospitalID;
    String loaHospitalName;
    String patientName;
    private ProgressDialog progressDialog;
    private LoaAddedListener loaAddedListener;
    boolean isFlag = false;
    String drugTime = "";


    public AlloyHospitalDialog(Context context, List<AlloyHospital.LOAHospital> loaHospitalList, int PatientId, String patientName, LoaAddedListener loaAddedListener,String drugTime) {
        super(context);
        this.context = context;
        this.loaHospitalList = loaHospitalList;
        this.PatientId = PatientId;
        this.patientName = patientName;
        this.loaAddedListener = loaAddedListener;
        this.drugTime = drugTime;
    }

    @Override
    protected void onKeyBoardShowHidden(boolean isKeyboardVisible) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alloy_hospital_dialog);
        Objects.requireNonNull(getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this);

        Log.d("TAG", "loaHospitalList Dialog: " + loaHospitalList.size());

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait ...");

        List<String> names = new ArrayList<>();
        Log.e("TAG", "onCreatesize: "+loaHospitalList.size() );
        for (AlloyHospital.LOAHospital loaHospital : loaHospitalList) {
            names.add(loaHospital.getLOAHospitalType());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, names);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spList.setAdapter(spinnerArrayAdapter);
        spList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                TextView textView = (TextView) view;
                for (AlloyHospital.LOAHospital loaHospital : loaHospitalList) {
                    if (loaHospital.getLOAHospitalType().equalsIgnoreCase(textView.getText().toString())) {
                        tvspinner.setText(loaHospital.getLOAHospitalType());
                        loaHospitalName = loaHospital.getLOAHospitalType();
                        loaHospitalID = loaHospital.getLOAHospital_ID();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       /* linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rl_items.setLayoutManager(linearLayoutManager);
        rl_items.setAdapter(alloyHospitalAdapter);
        tv_patient_name.setText(patientName);*/
        tvspinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFlag = true;
                spList.performClick();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.txt_ok)
    void onClickDone() {
//        loaHospitalName = alloyHospitalAdapter.getLoaHospitalName();
//        loaHospitalID = alloyHospitalAdapter.getLoaHospitalID();
        String note = et_note.getText().toString().trim();
        if (validate(loaHospitalName, loaHospitalID, note)) {
//            showPopupDialog();
            if (loaHospitalName.equals("Other")) {
                if(note.equals("")){
                    Toast.makeText(context, "Note can't be left blank", Toast.LENGTH_SHORT).show();
                }else{
                    makeApiCall();
                }
            } else {
                makeApiCall();
            }

        }


    }

    private void showPopupDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setCancelable(false);


        if (loaHospitalID == 1) {
            builder.setMessage("Please confirm patient is on " + loaHospitalName + ". Would you like to skip the current med pass?").setPositiveButton("YES", dialogClickListener)
                    .setNegativeButton("NO", dialogClickListener).show();
        } else if (loaHospitalID == 2) {
            builder.setMessage("Please confirm patient is in the Hospital. Would you like to skip the current med pass?").setPositiveButton("YES", dialogClickListener)
                    .setNegativeButton("NO", dialogClickListener).show();
        } else {
            builder.setMessage("Would you like to skip the current med pass?").setPositiveButton("YES", dialogClickListener)
                    .setNegativeButton("NO", dialogClickListener).show();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void makeApiCall() {
//        String loaHospitalName = alloyHospitalAdapter.getLoaHospitalName();
//        int loaHospitalID = alloyHospitalAdapter.getLoaHospitalID();
        int facilityID = MyApplication.getPrefranceDataInt("ExFacilityID");
        int patientID = PatientId;
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String doseDate = formatter.format(date);
        String note = et_note.getText().toString().trim();

        if (validate(loaHospitalName, loaHospitalID, note)) {
            JSONObject params = new JSONObject();
            try {
                params.put("facility_id", facilityID);
                params.put("patient_id", patientID);
                params.put("DoseDate", doseDate);
                params.put("doseTime", drugTime);
                params.put("LOAHospitalNote", note);
                params.put("LOAHospitalType", loaHospitalID);
                params.put("UserID", MyApplication.getPrefranceData("UserID"));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            progressDialog.show();

            NetworkUtility.makeJSONObjectRequest(API.UpdateLoaHospital, params, API.UpdateLoaHospital, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        Log.e("response", result.toString());

                        int ResponseStatus = result.getInt("ResponseStatus");
                        Utils.showAlertToast(context, result.getString("Message"));
                        if (ResponseStatus == 1) {
                            dismiss();
                            loaAddedListener.isDrugShiftedToLoa(true);
                        } else {
                            loaAddedListener.isDrugShiftedToLoa(false);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(JSONObject result) {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    try {
                        Utils.showAlertToast(context, result.getString("Message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private boolean validate(String loaHospitalName, int loaHospitalID, String note) {
        if (loaHospitalName.isEmpty()) {
            Toast.makeText(context, "Please select option for LOA/Hospital", Toast.LENGTH_SHORT).show();
            return false;
        } else if (loaHospitalID == -1) {
            Toast.makeText(context, "Please select option for LOA/Hospital", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    @OnClick(R.id.txt_cancel)
    void onClickCancel() {
        dismiss();
    }


}