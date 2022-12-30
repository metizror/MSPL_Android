package com.rn.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.rn.android.MyApplication;
import com.rn.android.MedPrescriptionTabActivity;
import com.rn.android.R;
import com.rn.android.model.MedpassPatientListModel;

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

public class MedPassPatientAdapter extends RecyclerView.Adapter<MedPassPatientAdapter.MyViewHolder> {


    public List<MedpassPatientListModel.DataBean.MedpassdetailsBean> patientList, filterList;
    Context context;
//    public List<MedpassPatientListModel.DataBean.MedpassdetailsBean.DosetimedetailsBean> dosetimelist;
    private LayoutInflater inflater;

    public MedPassPatientAdapter(Context context, List<MedpassPatientListModel.DataBean.MedpassdetailsBean> patientList) {
        this.patientList = patientList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.filterList = new ArrayList<>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.patientList);

    }

    private String getFormate(String date) throws ParseException {
        if (date != null && !date.isEmpty()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(date);
//        5/21/1955 12:00:00 AM
            Log.d("Date", String.valueOf(d));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            @SuppressLint("SimpleDateFormat")
            String monthName = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
            return monthName;
        } else {
            return "";
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_med_pass_patient_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v) {
        }; // pass the view to View Holder
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // set the data in items
        String dose_time = null;
//        String myFormate = null;
        String subdose_time;


        if (patientList.get(position).getDose_time() != null) {
            if (!TextUtils.isEmpty(patientList.get(position).getDose_time())) {
                dose_time = patientList.get(position).getDose_time();
//            subdose_time = TextUtils.substring(dose_time, 0, dose_time.length() - 4);
                holder.txtDob.setText("Next Dose: " + dose_time);
            } else {
                dose_time = "Done For Day";
                holder.txtDob.setText(dose_time);
                Log.e("TAG", "===========================>i am here 2: " + dose_time);

            }
        } else {
            dose_time = "Done For Day";
            holder.txtDob.setText(dose_time);
//            Log.e("TAG", "===========================>i am here 1: " + dose_time);
        }
        Log.d("date", dose_time);
        holder.img_med_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PatientListActivityNew) context).openMedsheetDialog(MyApplication.getPrefranceData("facilityName"), String.valueOf(filterList.get(position).getPatientId()));
                //Toast.makeText(context,"click Image",Toast.LENGTH_SHORT).show();
            }
        });
        holder.txtFirstName.setText(filterList.get(position).getPatient_last() + ", " + filterList.get(position).getPatient_first() + " (" + filterList.get(position).getGender().substring(0, 1) + ")");

        holder.txtgender.setText(filterList.get(position).getGender());
        holder.llNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MedPrescriptionTabActivity.class);
                intent.putExtra("PatientId", filterList.get(position).getPatientId());
                intent.putExtra("name", filterList.get(position).getPatient_last() + ", " + filterList.get(position).getPatient_first());
                intent.putExtra("fname", filterList.get(position).getPatient_first());
                intent.putExtra("lname", filterList.get(position).getPatient_last());
                intent.putExtra("Is_PrescriptionImg", filterList.get(position).isIs_PrescriptionImg());
                //intent.putExtra("isLOAHospital", filterList.get(position).isLOAHospital());
                try {
                    intent.putExtra("BirthDate", getFormate(filterList.get(position).getDOB()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                intent.putExtra("gender", "(" + filterList.get(position).getGender().substring(0, 1) + ")");
                intent.putExtra("gender_full", filterList.get(position).getGender());
                context.startActivity(intent);
            }
        });


    }


//
//    private String getDose_time() {
////        return String.valueOf((null != dosetimelist ? dosetimelist.size() :0 ));
//    }

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
                    for (MedpassPatientListModel.DataBean.MedpassdetailsBean item : patientList) {

                        if ((item.getPatient_last().toLowerCase().trim() + " " + item.getPatient_first().toLowerCase().trim()).contains(text.toLowerCase().trim()) ||
                                (item.getPatient_first().toLowerCase().trim() + " " + item.getPatient_last().toLowerCase().trim()).contains(text.toLowerCase().trim())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }
                // Set on UI Thread
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
        TextView txtFirstName;
        TextView txtgender;
        TextView txtDob;
        TextView txtPhone;
        ImageView img_med_sheet;
        LinearLayout ll_main;
        LinearLayout llNext;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtFirstName = (TextView) itemView.findViewById(R.id.txt_first_name);
            txtgender = (TextView) itemView.findViewById(R.id.txt_gender);
            img_med_sheet = (ImageView) itemView.findViewById(R.id.img_med_sheet);
            txtDob = (TextView) itemView.findViewById(R.id.txt_dob);
            txtPhone = (TextView) itemView.findViewById(R.id.txt_phone);
            llNext = (LinearLayout) itemView.findViewById(R.id.ll_next);
        }
    }
}
