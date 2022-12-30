package com.rn.android.Adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rn.android.MyApplication;
import com.rn.android.BaseActivity;
import com.rn.android.R;
import com.rn.android.customview.ActionEditText;
import com.rn.android.fragment.CensusFragment;
import com.rn.android.model.ChangeActiveStatus;
import com.rn.android.model.ModelCensus;
import com.rn.android.model.NewModelCensus;
import com.rn.android.model.PatientStatus;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rn.android.util.Utils.dismissProgressDialog;

/**
 * Created by paras on 8/8/20.
 */

public class CensusAdapter extends
        RecyclerView.Adapter<CensusAdapter.ViewHolder> {

    private static final String TAG = CensusAdapter.class.getSimpleName();
    private Context context;
    private List<NewModelCensus> list;
    private List<PatientStatus> patientStatusList;
    private ArrayList<ChangeActiveStatus> changeActiveStatusList;
    private int HeaderId = 0;
    String mNote = "";
    CensusFragment censusFragment;
    String cycleStartDate;
    String facultyID , ExternalPatientId;
    String type;
    int days;
    String Patient = "";
    public CensusAdapter( Context context, List<NewModelCensus> list, ArrayList<ChangeActiveStatus> changeActiveStatusList, List<PatientStatus> patientStatusList, int HeaderId, String cycleStartDate, CensusFragment censusFragment) {
        this.context = context;
        this.list = list;
        this.changeActiveStatusList = changeActiveStatusList;
        this.patientStatusList = patientStatusList;
        this.HeaderId = HeaderId;
        this.cycleStartDate = cycleStartDate;
        this.censusFragment = censusFragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.tv_patient_name)
        TextView tvPatientName;
        @BindView(R.id.img_info)
        ImageView imgInfo;

        @BindView(R.id.spinner_status)
        Spinner spinner_status;

        @BindView(R.id.cb_yes)
        CheckBox cb_yes;
        @BindView(R.id.cb_no)
        CheckBox cb_no;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_census, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position1) {
        ArrayList<ModelCensus> item = list.get(position1).getModelCensusArrayList();

        for (int i = 0; i < item.size(); i++) {

            if(item.get(i).getKeyText().equals("CycleChangeStatusYes")){
                if(item.get(i).getValueText().equals("Yes")){
                    holder.cb_yes.setChecked(true);
                }else if (item.get(i).getValueText().equals("NO")){
                    holder.cb_no.setChecked(true);
                }else{
                    holder.cb_yes.setChecked(false);
                    holder.cb_no.setChecked(false);
                }
            }

            if(item.get(i).getKeyText().equals("PendingDay")) {
                days = Integer.parseInt(item.get(i).getValueText());
            }

           /* if(item.get(i).getKeyText().equals("CycleChangeStatusNo")){
                if(item.get(i).getValueText().equals("true")){
                    holder.cb_no.setChecked(true);
                }else{
                    holder.cb_no.setChecked(false);
                }
            }*/

            if (item.get(i).getKeyText().equals("Patient")) {
                holder.tvPatientName.setText(item.get(i).getValueText());
            }
            for (int j = 0; j < patientStatusList.size(); j++) {

                if (item.get(i).getKeyText().equals("Status")) {
                    if (item.get(i).getValueText().equals(String.valueOf(patientStatusList.get(j).getID()))) {
                        setCycleStartDateSpinner(holder.spinner_status, patientStatusList);
                        Log.e("Index", String.valueOf(+patientStatusList.indexOf(patientStatusList.get(j))));
                        holder.spinner_status.setSelection(patientStatusList.indexOf(patientStatusList.get(j)));
                    }
                }
            }

        }

       /* if(type.equals("ForAllPatient")){
            holder.cb_no.setChecked(true);
        }else{
            holder.cb_no.setChecked(false);
        }*/

        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(item);
            }
        });

        holder.spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               /* if(days < 10){
                    Utils.showAlertToast(context, "10 days prior of cycle start date you can't change any status.");
                }else{
                    changeActiveStatusList.get(position1).setFacilityPatientStatus(patientStatusList.get(position).getID());
                }*/
                changeActiveStatusList.get(position1).setFacilityPatientStatus(patientStatusList.get(position).getID());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        holder.cb_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b;
                if(days < 10) {
                    holder.cb_yes.setChecked(holder.cb_yes.isChecked());
                    Utils.showAlertToast(context, "10 days prior of cycle start date you can't change any status.");
                }else{
                    if (holder.cb_yes.isChecked()) {
                        b = true;
                        CycleMedChangesForSinglePatient(position1, "true", b);
                    } else {
                        b = false;
                        holder.cb_yes.setChecked(true);
                        CycleMedChangesForSinglePatient(position1, "true", b);
                    }
                }
            }
        });

        holder.cb_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b;
                if(days < 10) {
                    holder.cb_no.setChecked(holder.cb_no.isChecked());
                    Utils.showAlertToast(context, "10 days prior of cycle start date you can't change any status.");
                }else{
                    if (holder.cb_no.isChecked()) {
                        b = true;
                        CycleMedChangesForSinglePatient(position1,"false", b);
                    }else{
                        holder.cb_no.setChecked(true);
                        //b = false;
                        //CycleMedChangesForSinglePatient(position1,"false", b);
                    }
                }
            }
        });

    }

    public ArrayList<ChangeActiveStatus> getChangeActiveStatusList() {
        return changeActiveStatusList;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void showDialog(ArrayList<ModelCensus> modelCensuses) {

        Dialog dialogBuilder = new Dialog(context, R.style.DialogStyle);
        LayoutInflater inflater = ((BaseActivity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rifill_information, null);
        dialogBuilder.setContentView(dialogView);
        LinearLayout ll_notes = (LinearLayout) dialogView.findViewById(R.id.ll_notes);
        LinearLayout ll_bottom = (LinearLayout) dialogView.findViewById(R.id.ll_spinner);
        LinearLayout ll_view = (LinearLayout) dialogView.findViewById(R.id.ll_view);
        Spinner spinner_status = (Spinner) dialogView.findViewById(R.id.spinner_status);
        TextView tv_ok = (TextView) dialogView.findViewById(R.id.tv_ok);
        TextView tv_update = (TextView) dialogView.findViewById(R.id.tv_update);
        tv_update.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.GONE);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        for (int i = 0; i < modelCensuses.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_view, null);
            TextView tv_key_value = (TextView) view.findViewById(R.id.tv_key_value);
            TextView tv_key_text = (TextView) view.findViewById(R.id.tv_key_text);
            ActionEditText edtFacilityNote = (ActionEditText) view.findViewById(R.id.edt_facility_note);
            View view1 = (View) view.findViewById(R.id.view);
            for (int ii = 0; ii < modelCensuses.size(); ii++) {

                if (modelCensuses.get(ii).getKeyText().equalsIgnoreCase("PendingDay")) {
                    if (Integer.parseInt(modelCensuses.get(ii).getValueText()) < 10) {
                        tv_key_text.setEnabled(false);
                        tv_key_text.setEnabled(false);
                        edtFacilityNote.setEnabled(false);
                        spinner_status.setEnabled(false);
                        ll_notes.setVisibility(View.VISIBLE);
                        tv_update.setEnabled(false);
                        tv_update.setVisibility(View.GONE);

                    } else {
                        tv_key_text.setEnabled(true);
                        tv_key_text.setEnabled(true);
                        edtFacilityNote.setEnabled(true);
                        spinner_status.setEnabled(true);
                        ll_notes.setVisibility(View.GONE);
                        tv_update.setEnabled(true);
                        tv_update.setVisibility(View.VISIBLE);

                    }
                }

            }

            for (int j = 0; j < patientStatusList.size(); j++) {

                if (modelCensuses.get(i).getKeyText().equals("Status")) {
                    if (modelCensuses.get(i).getValueText().equals(String.valueOf(patientStatusList.get(j).getID()))) {
                        setCycleStartDateSpinner(spinner_status, patientStatusList);
                        Log.e("Index", String.valueOf(+patientStatusList.indexOf(patientStatusList.get(j))));
                        spinner_status.setSelection(patientStatusList.indexOf(patientStatusList.get(j)));
                    }
                }
            }
            if (modelCensuses.get(i).isIsDisplayMobile()) {

                if (modelCensuses.get(i).getKeyText().equalsIgnoreCase("Facility Note")) {
                    edtFacilityNote.setVisibility(View.VISIBLE);
                    edtFacilityNote.setText(modelCensuses.get(i).getValueText());
                    edtFacilityNote.setText(modelCensuses.get(i).getValueText());
                    mNote = modelCensuses.get(i).getValueText();
                    tv_key_text.setVisibility(View.GONE);
                    view1.setVisibility(View.GONE);
                }
                if(modelCensuses.get(i).getKeyText().equalsIgnoreCase("External Patient id")) {

                }else if (modelCensuses.get(i).getKeyText().equalsIgnoreCase("patient dob")){

                }else if (modelCensuses.get(i).getKeyText().equalsIgnoreCase("CycleChangeStatusYes")){

                }else{
                    tv_key_value.setText(modelCensuses.get(i).getKeyText());
                    tv_key_text.setText(modelCensuses.get(i).getValueText());
                    ll_view.addView(view);
                }

            }
            edtFacilityNote.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edtFacilityNote.setCursorVisible(true);
                    mNote = edtFacilityNote.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            tv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //if (Integer.parseInt(modelCensuses.get(15).getValueText()) < 10) {
                    if (days < 10) {
                        Utils.showAlertToast(context, "Pending days not completed");
//
                    } else if (patientStatusList.get(spinner_status.getSelectedItemPosition()).getID() == 0) {
                        Utils.showAlertToast(context, "Please Change Status");


                    } else {

                        Utils.showProgressDialog(context);
                        JSONObject param = new JSONObject();
                        try {
                            param.put("HeaderID", HeaderId);

                            if (modelCensuses.get(2).getKeyText().equalsIgnoreCase("Facility ID")) {
                                param.put("FacilityID", modelCensuses.get(2).getValueText());
                            }
                            if (modelCensuses.get(3).getKeyText().equalsIgnoreCase("PatientFirst")) {
                                param.put("PatientFirst", modelCensuses.get(3).getValueText());
                            }
                            if (modelCensuses.get(4).getKeyText().equalsIgnoreCase("PatientLast")) {
                                param.put("PatientLast", modelCensuses.get(4).getValueText());
                            }
                            param.put("Note", mNote);
                            param.put("UserId", MyApplication.getPrefranceData("UserID"));
                            param.put("FacilityPatientStatus", patientStatusList.get(spinner_status.getSelectedItemPosition()).getID());
                            System.out.println(param);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        NetworkUtility.makeJSONObjectRequest(API.SaveFacilityNoteByFacilityUser, param, API.SaveFacilityNoteByFacilityUser, new VolleyCallBack() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                try {
                                    dismissProgressDialog();
                                    dialogBuilder.dismiss();
                                    ((CensusFragment) censusFragment).getCensusuList();
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
            });
        }
        dialogBuilder.show();
    }

    private void setCycleStartDateSpinner(final Spinner spinner, List<PatientStatus> list) {

        final ArrayAdapter<PatientStatus> cycleStartDateArrayAdapter = new ArrayAdapter<PatientStatus>
                (context, R.layout.simple_spinner_item,
                        list);
        cycleStartDateArrayAdapter.setDropDownViewResource(R.layout
                .spinner_dropdown_item);
        spinner.setAdapter(cycleStartDateArrayAdapter);
    }


    public void CycleMedChangesForSinglePatient(int position, String isFlag,boolean b) {
        Utils.showProgressDialog(context);
        JSONObject param = new JSONObject();
        try {
            ArrayList<ModelCensus> item = list.get(position).getModelCensusArrayList();

            if (item.get(5).getKeyText().equals("Patient")) {
                Patient = item.get(5).getValueText();
            }

            String changeStatus = "";
            if (isFlag.equals("true")){
                if(b){
                    changeStatus = "Y";
                }else{
                    changeStatus = "";
                }
            }else if (isFlag.equals("false")){
                if(b){
                    changeStatus = "N";
                }else{
                    changeStatus = "";
                }
            }
            /*if(b){
                changeStatus = "Y";
            }else {
                changeStatus = "N";
            }*/
            param.put("ChangeStatus",changeStatus);
            param.put("ChangedBy", MyApplication.getPrefranceData("UserID"));
            param.put("ChangesOn",Utils.getCurrentDateTime());
            if (item.get(0).getKeyText().equalsIgnoreCase("Id")) {
                param.put("CyclePatientID", item.get(0).getValueText());
            }
            if (item.get(2).getKeyText().equalsIgnoreCase("Facility ID")) {
                param.put("Facility_Id", item.get(2).getValueText());
                facultyID = item.get(2).getValueText();
            }
            param.put("HeaderID", HeaderId);
            if (item.get(1).getKeyText().equalsIgnoreCase("External Patient id")) {
                param.put("external_patietn_id", item.get(1).getValueText());
                ExternalPatientId = item.get(1).getValueText();
            }

            //param.put("external_patietn_id", ExternalPatientId);
            //param.put("CyclePatientID","");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(isFlag.equals("true")){
            Utils.dismissProgressDialog();
            Intent i = new Intent(context, PatientPrescriptionDetailsActivity.class);
            i.putExtra("cycleStartDate", cycleStartDate);
            i.putExtra("Facility_Id", facultyID);
            i.putExtra("HeaderId", HeaderId);
            i.putExtra("ExternalPatientId", ExternalPatientId);
            i.putExtra("Patient", Patient);
            i.putExtra("param", param.toString());
            context.startActivity(i);
        }

        if(isFlag.equals("false")){
            NetworkUtility.makeJSONObjectRequest(API.CycleMedChangesForSinglePatient , param, API.CycleMedChangesForSinglePatient, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        Utils.dismissProgressDialog();
                       /* if(isFlag.equals("true")){
                            Intent i = new Intent(context, PatientPrescriptionDetailsActivity.class);
                            i.putExtra("cycleStartDate", cycleStartDate);
                            i.putExtra("Facility_Id", facultyID);
                            i.putExtra("HeaderId", HeaderId);
                            i.putExtra("ExternalPatientId", ExternalPatientId);
                            i.putExtra("Patient", Patient);
                            context.startActivity(i);
                        }*/
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



}