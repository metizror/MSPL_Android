package com.rn.android.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rn.android.MedPrescriptionDetailsActivity;
import com.rn.android.R;
import com.rn.android.model.GetGivenMedPassPrescriptionModel;
import com.rn.android.util.TouchImageView;

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

public class MedTakenPrescriptionAdapter extends RecyclerView.Adapter<MedTakenPrescriptionAdapter.MyViewHolder> {
    public List<GetGivenMedPassPrescriptionModel.DataBean.ModelTakenPrescription> patientList, filterList;
    Context context;
    private LayoutInflater inflater;
    private Activity activity;

    public MedTakenPrescriptionAdapter(Activity activity, Context context, List<GetGivenMedPassPrescriptionModel.DataBean.ModelTakenPrescription> patientList) {
        this.patientList = patientList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
        this.filterList = new ArrayList<>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.patientList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_med_pass_prescription_taken_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v) {
        }; // pass the view to View Holder
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // set the data in items
        holder.setIsRecyclable(false);
        if (filterList.get(position).isIsPRN()) {
            holder.tv_isPRN.setVisibility(View.VISIBLE);
        } else {
            holder.tv_isPRN.setVisibility(View.GONE);
        }
        holder.txt_dose_qty.setText(String.valueOf(filterList.get(position).getDose_Qty()));
        holder.drugName.setText(filterList.get(position).getDrug());
        holder.rxNumber.setText("Rx Number: " + filterList.get(position).getRx_number());

        if (filterList.get(position).isIsLOAHospital()) {
            holder.ll_normal.setVisibility(View.GONE);
            holder.ll_loa.setVisibility(View.VISIBLE);
            holder.ll_img_picture.setVisibility(View.GONE);
            holder.tv_loa_hospital.setVisibility(View.VISIBLE);
            if (!filterList.get(position).getLOAHospitalNote().trim().equalsIgnoreCase("")) {
                holder.tv_loa_note_title.setVisibility(View.VISIBLE);
                holder.tv_loa_notes.setText(filterList.get(position).getLOAHospitalNote());
            } else {
                holder.tv_loa_note_title.setVisibility(View.GONE);
                holder.tv_loa_notes.setVisibility(View.GONE);
            }

            holder.txt_username.setText(filterList.get(position).getUserName());
            //String startDateString = Utils.DateFromOnetoAnother(filterList.get(position).getLOAHospitalDate(), "yyyy-MM-dd'T'HH:mm:ss", "MM/dd/yy");
            String startDateString = filterList.get(position).getLOAHospitalDate();
            //String loaTime = Utils.DateFromOnetoAnother(filterList.get(position).getLOAHospitalDate(), "yyyy-MM-dd'T'HH:mm:ss", "hh:mm a");
            String loaTime = filterList.get(position).getLOAHospitalTime();
            holder.txt_loa_dose_time.setText(filterList.get(position).getDose_date() + "\n" + filterList.get(position).getDose_time());
            holder.txt_dose_time_loa.setText(startDateString + "\n" + loaTime);
            holder.txt_loa_type.setText(filterList.get(position).getLOAType());
            holder.tv_loa_date_label.setText(filterList.get(position).getLOAType() + " Date");

        } else {
            holder.ll_normal.setVisibility(View.VISIBLE);
            holder.ll_loa.setVisibility(View.GONE);
            holder.ll_img_picture.setVisibility(View.VISIBLE);

            if (!filterList.get(position).getUpdated_by().trim().equalsIgnoreCase("")) {
                holder.txtTakenBy.setText(filterList.get(position).getUpdated_by());
            }

            if (!filterList.get(position).getGiventime().isEmpty() && filterList.get(position).getGiventime() != null) {
                holder.txtGivenTime.setText(filterList.get(position).getGiventime());
            } else {
                holder.txtGivenTime.setText(" - ");
            }

            try {
                //String startDateString = Utils.DateFromOnetoAnother(filterList.get(position).getDose_date(), "yyyy-MM-dd'T'HH:mm:ss", "MM/dd/yy");
                String startDateString = filterList.get(position).getDose_date();
                holder.txt_dose_date.setText(startDateString);
                holder.txtDoseTime.setText(startDateString + "\n" + filterList.get(position).getDose_time());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!filterList.get(position).getNote().trim().equalsIgnoreCase("")) {
                holder.tv_note_title.setVisibility(View.VISIBLE);
                holder.txtNotes.setText(filterList.get(position).getNote());
            } else {
                holder.tv_note_title.setVisibility(View.GONE);
                holder.txtNotes.setVisibility(View.GONE);
            }
        }

        holder.ll_img_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageDrugDialog(context, filterList.get(position).getMedImagePath());
            }
        });


        if (filterList.get(position).getDose_status().trim().equals("D")) {
            holder.tv_administered.setVisibility(View.VISIBLE);
            holder.tv_refused.setVisibility(View.GONE);
        } else if (filterList.get(position).getDose_status().trim().equals("R")) {
            holder.tv_refused.setVisibility(View.VISIBLE);
            holder.tv_administered.setVisibility(View.GONE);
        } else {
            holder.tv_refused.setVisibility(View.GONE);
            holder.tv_administered.setVisibility(View.GONE);
        }

        if (filterList.get(position).isIsMissedDose()) {
            holder.tv_administered.setVisibility(View.GONE);
            holder.tv_refused.setVisibility(View.GONE);
            holder.ll_img_picture.setVisibility(View.GONE);
            holder.tv_missed_dose.setVisibility(View.VISIBLE);
            if (filterList.get(position).isIsLOAHospital()) {
                holder.ll_note.setVisibility(View.GONE);
                holder.ll_loa_note.setVisibility(View.VISIBLE);
                holder.tv_loa_hospital.setVisibility(View.VISIBLE);
            } else {
                holder.ll_note.setVisibility(View.VISIBLE);
                holder.ll_loa_note.setVisibility(View.GONE);
                holder.tv_loa_hospital.setVisibility(View.GONE);

            }
        } else {
            holder.tv_missed_dose.setVisibility(View.GONE);
            if (filterList.get(position).isIsLOAHospital()) {
                holder.ll_note.setVisibility(View.GONE);
                holder.ll_loa_note.setVisibility(View.VISIBLE);
                holder.tv_loa_hospital.setVisibility(View.VISIBLE);
            } else {
                holder.ll_note.setVisibility(View.VISIBLE);
                holder.ll_loa_note.setVisibility(View.GONE);
                holder.tv_loa_hospital.setVisibility(View.GONE);
            }

        }

        holder.tv_missed_dose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterList.get(position).isIsMissedDose()) {
                    showAlert(position);
                }
            }
        });
    }

    private void showAlert(int position) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent intent = new Intent(context, MedPrescriptionDetailsActivity.class);
                    intent.putExtra("drug", filterList.get(position).getDrug());
                    intent.putExtra("rxNumber", filterList.get(position).getRx_number());
                    intent.putExtra("sig_detail", filterList.get(position).getSig_detail());
                    intent.putExtra("dose_time", filterList.get(position).getDose_time());
                    intent.putExtra("dose_Qty", filterList.get(position).getDose_Qty());
                    intent.putExtra("tran_id", filterList.get(position).getTran_id());
                    intent.putExtra("facility_id", filterList.get(position).getFacility_id());
                    intent.putExtra("patientId", filterList.get(position).getPatient_id());
                    intent.putExtra("ndc", filterList.get(position).getNdc());
                    intent.putExtra("name", filterList.get(position).getPatient_last() + " " + filterList.get(position).getPatient_first());
                    intent.putExtra("fname", filterList.get(position).getPatient_first());
                    intent.putExtra("lname", filterList.get(position).getPatient_last());
//                    intent.putExtra("name", filterList.get(position).getPatient_last() + " " + filterList.get(position).getPatient_first());
                    intent.putExtra("isMissedDose", filterList.get(position).isIsMissedDose());
                    Log.e("TAG", "isMissedDose: " + filterList.get(position).isIsMissedDose());
                    try {
                        intent.putExtra("BirthDate", getFormate(filterList.get(position).getDOB()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("gender", "(" + filterList.get(position).getGender().substring(0, 1) + ")");
                    intent.putExtra("gender_full", filterList.get(position).getGender());
//                    context.startActivity(intent);
                    activity.startActivityForResult(intent, 102);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.dismiss();
                    break;
            }
        };

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("Do you want to administer this medication?").setPositiveButton("YES", dialogClickListener)
                .setNegativeButton("NO", dialogClickListener).show();
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

    private void openImageDrugDialog(Context context, String imageURL) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TouchImageView dialog_image = (TouchImageView) dialog.findViewById(R.id.dialog_image);
        SimpleDraweeView drawee_image = (SimpleDraweeView) dialog.findViewById(R.id.drawee_image);
        final ImageView dialog_cancel = (ImageView) dialog.findViewById(R.id.dialog_cancel);

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(context).load(imageURL).placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder).into(dialog_image);
        drawee_image.setVisibility(View.GONE);
        Uri uri = Uri.parse(imageURL);
//        Uri uri = Uri.parse("http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg");
        drawee_image.setImageURI(uri);
        dialog.show();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
                    for (GetGivenMedPassPrescriptionModel.DataBean.ModelTakenPrescription item : patientList) {

                        if ((item.getDrug().toLowerCase().trim().contains(text.toLowerCase().trim())) || item.getRx_number().toLowerCase().trim().contains(text.toLowerCase().trim())) {
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
        private TextView drugName, rxNumber;
        private LinearLayout ll_main;
        private TextView txtTakenBy;
        private TextView txtDoseTime;
        private TextView txt_dose_date;
        private TextView txtGivenTime;
        private TextView txtNotes;
        private TextView txt_dose_qty;
        private LinearLayout ll_note;
        private View view;
        private LinearLayout ll_img_picture;
        private LinearLayout ll_loa_note;
        private TextView tv_loa_notes;
        private TextView tv_missed_dose;

        private LinearLayout ll_normal;
        private LinearLayout ll_loa;
        private TextView txt_username;
        private TextView txt_dose_time_loa;
        private TextView txt_loa_type;
        private TextView tv_loa_note_title;
        private TextView tv_note_title;
        private TextView txt_loa_dose_time;
        private TextView tv_loa_date_label;
        private TextView tv_refused;
        private TextView tv_administered;
        private TextView tv_loa_hospital;

        private TextView tv_isPRN;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv_refused = itemView.findViewById(R.id.tv_refused);
            tv_administered = itemView.findViewById(R.id.tv_administered);
            tv_loa_hospital = itemView.findViewById(R.id.tv_loa_hospital);
            drugName = itemView.findViewById(R.id.txt_drug_name);
            rxNumber = itemView.findViewById(R.id.txt_rx_number);
            txtTakenBy = itemView.findViewById(R.id.txt_taken_by);
            txtDoseTime = itemView.findViewById(R.id.txt_dose_time);
            txt_dose_date = itemView.findViewById(R.id.txt_dose_date);
            txt_dose_qty = itemView.findViewById(R.id.txt_dose_qty);
            txtGivenTime = itemView.findViewById(R.id.txt_given_time);
            ll_main = itemView.findViewById(R.id.ll_main);
            txtNotes = itemView.findViewById(R.id.tv_notes);
            view = itemView.findViewById(R.id.view);
            ll_note = itemView.findViewById(R.id.ll_note);
            ll_img_picture = itemView.findViewById(R.id.ll_img_picture);
            ll_loa_note = itemView.findViewById(R.id.ll_loa_note);
            tv_loa_notes = itemView.findViewById(R.id.tv_loa_notes);
            tv_missed_dose = itemView.findViewById(R.id.tv_missed_dose);

            ll_normal = itemView.findViewById(R.id.ll_normal);
            ll_loa = itemView.findViewById(R.id.ll_loa);
            txt_username = itemView.findViewById(R.id.txt_username);
            txt_dose_time_loa = itemView.findViewById(R.id.txt_dose_time_loa);
            txt_loa_type = itemView.findViewById(R.id.txt_loa_type);
            tv_loa_note_title = itemView.findViewById(R.id.tv_loa_note_title);
            tv_note_title = itemView.findViewById(R.id.tv_note_title);
            txt_loa_dose_time = itemView.findViewById(R.id.txt_loa_dose_time);
            tv_isPRN = itemView.findViewById(R.id.tv_isPRN);

            tv_loa_date_label = itemView.findViewById(R.id.tv_loa_date_label);


        }
    }
}
