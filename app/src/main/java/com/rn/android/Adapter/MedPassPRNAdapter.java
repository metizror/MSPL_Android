package com.rn.android.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.rn.android.MedPrescriptionDetailsActivityForPRN;
import com.rn.android.R;
import com.rn.android.model.MedPassPrnDetailModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by abc on 10/3/2017.
 */

public class MedPassPRNAdapter extends RecyclerView.Adapter<MedPassPRNAdapter.MyViewHolder> {

    public List<MedPassPrnDetailModel.DataBean.ObjpatientdetailsBean> patientList, filterList;

    private LayoutInflater inflater;
    private Context context;
    private Activity activity;
    private String patient_fName;
    private String patient_lName;
    private String BirthDate;
    private String Gender;
    private String Msg;
    private boolean Is_PrescriptionImg;

    public MedPassPRNAdapter(Activity activity, Context context, List<MedPassPrnDetailModel.DataBean.ObjpatientdetailsBean> patientList, String patient_fName,
                             String patient_lName, String BirthDate, String Gender, boolean Is_PrescriptionImg) {
        this.patientList = patientList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
        this.filterList = new ArrayList<>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.patientList);
        this.patient_fName = patient_fName;
        this.patient_lName = patient_lName;
        this.BirthDate = BirthDate;
        this.Gender = Gender;
//        this.Msg = Msg;
        this.Is_PrescriptionImg = Is_PrescriptionImg;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_med_pass_prescription_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v) {
        }; // pass the view to View Holder
        return vh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // set the data in items

        holder.drugName.setText(filterList.get(position).getDrug());
        holder.rxNumber.setText("Rx Number: " + filterList.get(position).getPharmacy_order_id());

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        Date date = new Date();
        System.out.println(formatter.format(date));

        holder.txt_drug_date.setText("Date/Time : " + formatter.format(date));
        Msg=filterList.get(position).getMsg();
        /*String startDateString = Utils.DateFromOnetoAnother(filterList.get(position).getPrescribe_date(), "yyyy-MM-dd'T'HH:mm:ss", "MM/dd/yy");
        holder.txt_drug_date.setText("Dose : " + startDateString + " " + filterList.get(position).getDose_time());
        //holder.txt_drug_date.setText("Dose Date : " + filterList.get(position).getDose_date());*/

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MedPrescriptionDetailsActivityForPRN.class);
                intent.putExtra("drug", filterList.get(position).getDrug());
                intent.putExtra("rxNumber", filterList.get(position).getPharmacy_order_id());
                intent.putExtra("sig_code", filterList.get(position).getSig_code());
                intent.putExtra("sig_detail", filterList.get(position).getSig_english());
                intent.putExtra("dose_time", "");
                intent.putExtra("dose_Qty", Float.parseFloat(filterList.get(position).getQty()));
                intent.putExtra("tran_id", filterList.get(position).getExternal_prescription_id());
                intent.putExtra("facility_id", Integer.parseInt(filterList.get(position).getExternal_facility_id()));
                intent.putExtra("patientId", Integer.parseInt(filterList.get(position).getExternal_patient_id()));
                intent.putExtra("ndc", filterList.get(position).getNdc());
                intent.putExtra("name", patient_lName + ", " + patient_fName);
                intent.putExtra("fname", patient_fName);
                intent.putExtra("lname", patient_lName);
                intent.putExtra("msg", patientList.get(position).getMsg());

                Log.e("TAG", "patientList1 msg"+filterList.get(position).getMsg());
                intent.putExtra("isMissedDose", false);
                intent.putExtra("BirthDate", BirthDate);
                intent.putExtra("gender","(" +  Gender.substring(0, 1) + ")");
                intent.putExtra("gender_full", Gender);
                intent.putExtra("Is_PrescriptionImg", Is_PrescriptionImg);

//                context.startActivity(intent);
                activity.startActivityForResult(intent,102);
            }
        });

    }

    private String getFormate(String date) throws ParseException {
        if (date != null && !date.isEmpty()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(date);
//        5/21/1955 12:00:00 AM
            Log.d("Date", String.valueOf(d));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            String monthName = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
            return monthName;
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }

    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Clear the filter list
                filterList.clear();
                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
                    filterList.addAll(patientList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (MedPassPrnDetailModel.DataBean.ObjpatientdetailsBean item : patientList) {
                        if ((item.getDrug().toLowerCase().trim().contains(text.toLowerCase().trim())) || item.getPharmacy_order_id().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }
                ((FragmentActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView drugName, rxNumber, txt_drug_time, txt_drug_date;
        LinearLayout ll_main;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            drugName = (TextView) itemView.findViewById(R.id.txt_drug_name);
            rxNumber = (TextView) itemView.findViewById(R.id.txt_rx_number);
            ll_main = (LinearLayout) itemView.findViewById(R.id.ll_main);
            txt_drug_time = (TextView) itemView.findViewById(R.id.txt_drug_time);
            txt_drug_date = itemView.findViewById(R.id.txt_drug_date);
        }
    }
}
