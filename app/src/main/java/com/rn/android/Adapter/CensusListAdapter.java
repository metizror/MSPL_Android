package com.rn.android.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rn.android.R;
import com.rn.android.model.ModelCensusListDetails;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rn.android.util.Utils.dismissProgressDialog;

/**
 * Created by paras on 8/8/20.
 */
public class CensusListAdapter extends RecyclerView.Adapter<CensusListAdapter.ViewHolder> {

    private static final String TAG = CensusListAdapter.class.getSimpleName();
    private Context context;
    private List<ModelCensusListDetails> list;
    Activity mContext;
    PatientPrescriptionDetailsActivity detailsActivity;
    int HeaderId;
    String ExternalPatientId;
    boolean isOnTextChanged = false;
    public CensusListAdapter(Context context, List<ModelCensusListDetails> list, int HeaderId, String ExternalPatientId) {
        this.context = context;
        this.list = list;
        this.HeaderId = HeaderId;
        this.ExternalPatientId = ExternalPatientId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_census_list, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_rx_number.setText(list.get(position).getRxnumber());
        String brand_drug = list.get(position).getBrand_drug();
        if(brand_drug != null) {
            brand_drug = " (" + brand_drug + ")";
        }else{
            brand_drug = "";
        }
        holder.tv_rx_name.setText(list.get(position).getDrug() + brand_drug);
        holder.tv_date.setText(list.get(position).getLast_pickedup_date());
        holder.tv_next_refil.setText(list.get(position).getNextRefill());
        holder.ed_facility.setText(list.get(position).getFacility_QTY());
        holder.ed_program.setText(list.get(position).getProgram_QTY());

        if (list.get(position).getDCStatus().equals("true")){

            if (!list.get(position).getDCFile().equals("")){
                holder.iv_upload.setBackgroundResource(R.drawable.ic_upload_red);
                holder.iv_upload.setEnabled(false);
            }else{
                holder.iv_upload.setBackgroundResource(R.drawable.ic_file_upload3x);
                holder.iv_upload.setEnabled(true);
            }

            if (!list.get(position).getDCNote().equals("")){
                holder.iv_dc_list.setBackgroundResource(R.drawable.ic_note_red);
            }else{
                holder.iv_dc_list.setBackgroundResource(R.drawable.ic_list_black3x);
            }

            //holder.iv_label_upload.setBackgroundResource(R.drawable.ic_file_upload3x);
            //holder.iv_hold_list.setBackgroundResource(R.drawable.ic_list_black3x);
            holder.cb_dc.setChecked(true);
            holder.iv_label_upload.setEnabled(false);
            //holder.cb_label_change.setChecked(false);
            //holder.cb_hold.setChecked(false);
            list.get(position).setLCFile("");
            list.get(position).setLCStatus("false");
            list.get(position).setNote("");
            list.get(position).setHoldStatus("false");
            disableEnableControls(false,holder.ll_lc);
            disableEnableControls(false,holder.ll_hold);


            holder.ed_facility.setEnabled(false);
            holder.ed_program.setEnabled(false);
            list.get(position).setFacility_QTY("");
            list.get(position).setProgram_QTY("");
        }else{
            holder.iv_upload.setBackgroundResource(R.drawable.ic_file_upload3x);
            holder.iv_dc_list.setBackgroundResource(R.drawable.ic_list_black3x);
            holder.cb_dc.setChecked(false);
            holder.iv_upload.setEnabled(true);
            holder.ed_facility.setEnabled(true);
            holder.ed_program.setEnabled(true);
            EnableControls(holder);
        }

        /*if (!list.get(position).getDCNote().equals("")){
            holder.iv_dc_list.setBackgroundResource(R.drawable.ic_note_red);
            holder.iv_label_upload.setBackgroundResource(R.drawable.ic_file_upload3x);
            holder.iv_hold_list.setBackgroundResource(R.drawable.ic_list_black3x);
            holder.cb_hold.setChecked(false);
            holder.cb_label_change.setChecked(false);
           *//* disableEnableControls(false,holder.ll_dc);
            disableEnableControls(false,holder.ll_lc);*//*
        }else{
            holder.iv_dc_list.setBackgroundResource(R.drawable.ic_list_black3x);
            holder.cb_hold.setChecked(false);
            //EnableControls(holder);
        }*/

        if (list.get(position).getLCStatus().equals("true")){
            holder.cb_label_change.setChecked(true);
            holder.iv_label_upload.setEnabled(false);
            holder.iv_label_upload.setBackgroundResource(R.drawable.ic_upload_red);
          /*  disableEnableControls(false,holder.ll_dc);
            disableEnableControls(false,holder.ll_hold);*/
        }else{
            holder.cb_label_change.setChecked(false);
            holder.iv_label_upload.setBackgroundResource(R.drawable.ic_file_upload3x);
            //holder.iv_label_upload.setEnabled(true);
            //EnableControls(holder);
        }

        if (list.get(position).getHoldStatus().equals("true")){
            holder.cb_hold.setChecked(true);
            holder.iv_hold_list.setBackgroundResource(R.drawable.ic_note_red);
           /* disableEnableControls(false,holder.ll_dc);
            disableEnableControls(false,holder.ll_lc);*/
        }else{
            holder.cb_hold.setChecked(false);
            holder.iv_hold_list.setBackgroundResource(R.drawable.ic_list_black3x);
            //EnableControls(holder);
        }

        /*holder.cb_dc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ((PatientPrescriptionDetailsActivity) context).pickImage(position, "DC");
                }else{
                    removeImage(position, "DC");
                }
            }
        });

         holder.cb_label_change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ((PatientPrescriptionDetailsActivity) context).pickImage(position, "LC");
                }else{
                    removeImage(position, "LC");
                }
            }
        });

        holder.cb_hold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ((PatientPrescriptionDetailsActivity) context).openNoteDialog(position, "HOLD");
                }else{
                    removeImage(position, "HOLD");
                }
            }
        });*/

        holder.cb_dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cb_dc.isChecked()){
                    ((PatientPrescriptionDetailsActivity) context).pickImage(position, "DC");
                }else{
                    if(list.get(position).getDCStatus().equals("true")){
                        removeImageNote(position, "D");
                    }
                }
            }
        });

        holder.cb_label_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cb_label_change.isChecked()){
                    ((PatientPrescriptionDetailsActivity) context).pickImage(position, "LC");
                }else{
                    if(list.get(position).getLCStatus().equals("true")) {
                        removeImageNote(position, "L");
                    }
                }
            }
        });

        holder.cb_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cb_hold.isChecked()){
                    openNoteDialog(position, "HOLD");
                }else{
                    if(list.get(position).getHoldStatus().equals("true")) {
                        removeImageNote(position, "H");
                    }
                }
            }
        });


        holder.iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PatientPrescriptionDetailsActivity) context).pickImage(position, "DC");
            }
        });

        holder.iv_label_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PatientPrescriptionDetailsActivity) context).pickImage(position, "LC");
            }
        });

        holder.iv_dc_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoteDialog(position, "DC");
            }
        });

        holder.iv_hold_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openNoteDialog(position, "HOLD");
            }
        });

        /*holder.ed_facility.addTextChangedListener(new TextChangedListener(position, "facility"));
        holder.ed_program.addTextChangedListener(new TextChangedListener(position, "program"));*/

       /* holder.ed_facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ed_facility.setCursorVisible(true);
            }
        });*/

       /*cursor false thai tyare
        holder.ed_facility.setSelection(0);*/
        holder.ed_facility.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(holder.ed_facility.hasFocus()){
                   /* if(!list.get(position).getLCFile().equals("")) {
                        list.get(position).setLCFile("");
                        list.get(position).setLCStatus("false");
                        list.get(position).setFacility_QTY(s.toString());
                        list.get(position).setIsChange("true");
                    }else{
                        list.get(position).setFacility_QTY(s.toString());
                        list.get(position).setIsChange("true");
                    }*/
                    list.get(position).setFacility_QTY(s.toString());
                    list.get(position).setIsChange("true");
                    list.get(position).setIsQtyChange("true");
                }
            }
        });

        /*holder.ed_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ed_program.setCursorVisible(true);
            }
        });*/

        /*cursor false thai tyare
        holder.ed_program.setSelection(0);*/
        holder.ed_program.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(holder.ed_program.hasFocus()) {
                    /*if(!list.get(position).getLCFile().equals("")) {
                        list.get(position).setLCFile("");
                        list.get(position).setLCStatus("false");
                        list.get(position).setProgram_QTY(s.toString());
                        list.get(position).setIsChange("true");
                    }else {
                        list.get(position).setProgram_QTY(s.toString());
                        list.get(position).setIsChange("true");
                    }*/
                    list.get(position).setProgram_QTY(s.toString());
                    list.get(position).setIsChange("true");
                    list.get(position).setIsQtyChange("true");
                }
            }
        });
    }

    private void disableEnableControls(boolean enable, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
        }
    }
    private void EnableControls(ViewHolder holder){
        disableEnableControls(true,holder.ll_dc);
        disableEnableControls(true,holder.ll_lc);
        disableEnableControls(true,holder.ll_hold);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.tv_rx_number)
        TextView tv_rx_number;
        @BindView(R.id.tv_rx_name)
        TextView tv_rx_name;

        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_next_refil)
        TextView tv_next_refil;

        @BindView(R.id.cb_dc)
        CheckBox cb_dc;
        @BindView(R.id.iv_upload)
        ImageView iv_upload;
        @BindView(R.id.iv_dc_list)
        ImageView iv_dc_list;

        @BindView(R.id.cb_label_change)
        CheckBox cb_label_change;
        @BindView(R.id.iv_label_upload)
        ImageView iv_label_upload;

        @BindView(R.id.cb_hold)
        CheckBox cb_hold;
        @BindView(R.id.iv_hold_list)
        ImageView iv_hold_list;

        @BindView(R.id.ed_facility)
        EditText ed_facility;
        @BindView(R.id.ed_program)
        EditText ed_program;

        @BindView(R.id.ll_dc)
        LinearLayout ll_dc;
        @BindView(R.id.ll_lc)
        LinearLayout ll_lc;
        @BindView(R.id.ll_hold)
        LinearLayout ll_hold;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void removeImageNote(int position, String type) {

        Dialog dialogBuilder = new Dialog(context, R.style.DialogStyle);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_remove_image, null);
        dialogBuilder.setContentView(dialogView);
        dialogBuilder.setCancelable(false);
        Button dialogBtnSave = (Button) dialogView.findViewById(R.id.btn_dialog_save);
        Button dialogBtnCalcel = (Button) dialogView.findViewById(R.id.btn_dialog_cancel);

        dialogBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ExternalPatientID = list.get(position).getExternal_Patient_ID();
                String PrescriptionId = list.get(position).getRxID();

                getDeleteCycleChangesEntry(ExternalPatientID, PrescriptionId,type);
                dialogBuilder.dismiss();
               /* if(type.equals("DC")){
                    list.get(position).setDCFile("");
                    list.get(position).setDCNote("");
                }else if (type.equals("LC")){
                    list.get(position).setLCFile("");
                }else if (type.equals("HOLD")){
                    list.get(position).setNote("");
                }else{

                }
                list.get(position).setIsChange("true");
                notifyDataSetChanged();
                dialogBuilder.dismiss();*/
            }
        });
        dialogBtnCalcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.show();
    }

    private void getDeleteCycleChangesEntry( String ExPatientId, String PrescriptonId, String type) {
        Utils.showProgressDialog(context);
        NetworkUtility.makeJSONObjectRequest(API.DeleteCycleChangesEntry + "?HeaderID=" + HeaderId + "&External_Patient_ID=" + ExternalPatientId + "&PrescriptionID=" + PrescriptonId + "&ChangeType=" + type, new JSONObject(), API.PatientPrescription, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                String Url = API.DeleteCycleChangesEntry + "?HeaderID=" + HeaderId + "&External_Patient_ID=" + ExternalPatientId + "&PrescriptionID=" + PrescriptonId + "&ChangeType=" + type;
                System.out.println(Url);
                try {
                    if (result != null) {
                        notifyDataSetChanged();
                        ((PatientPrescriptionDetailsActivity) context).getPatientPrescriptionDetails();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dismissProgressDialog();
                }
                dismissProgressDialog();
            }
            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
            }
        });
    }

    public void openNoteDialog(int position, String type) {
        System.out.println(position);
        Dialog dialogBuilder = new Dialog(context, R.style.DialogStyle);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.note_dialog, null);
        dialogBuilder.setContentView(dialogView);
        dialogBuilder.setCancelable(true);

        EditText text = dialogView.findViewById(R.id.dialog_note);
        Button dialogBtnSave = (Button) dialogView.findViewById(R.id.dialog_btn_save);
        Button dialogBtnCalcel = (Button) dialogView.findViewById(R.id.dialog_btn_cancel);

        if(type.equals("DC")){
            text.setText(list.get(position).getDCNote());
        }
        if(type.equals("HOLD")){
            text.setText(list.get(position).getNote());
        }

        dialogBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Note = text.getText().toString();
                if(type.equals("DC")){
                    list.get(position).setDCNote(Note);
                    list.get(position).setDCStatus("true");
                    list.get(position).setIsChange("true");
                }else if(type.equals("HOLD")){
                   /* if(!list.get(position).getLCFile().equals("")){
                        list.get(position).setLCFile("");
                        list.get(position).setLCStatus("false");
                        list.get(position).setNote(Note);
                        list.get(position).setHoldStatus("true");
                        list.get(position).setIsChange("true");
                    }else{
                        list.get(position).setNote(Note);
                        list.get(position).setHoldStatus("true");
                        list.get(position).setIsChange("true");
                    }*/
                    list.get(position).setNote(Note);
                    list.get(position).setHoldStatus("true");
                    list.get(position).setIsChange("true");
                    /*list.get(position).setNote(Note);
                    list.get(position).setHoldStatus("true");
                    list.get(position).setIsChange("true");*/
                }else{
                    list.get(position).setDCStatus("false");
                    list.get(position).setHoldStatus("false");
                }
                dialogBuilder.dismiss();
                notifyDataSetChanged();
            }
        });
        dialogBtnCalcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.show();

    }



}