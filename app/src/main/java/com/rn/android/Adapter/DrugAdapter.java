package com.rn.android.Adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
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
import com.rn.android.BaseActivity;
import com.rn.android.DrugHistoryActivity;
import com.rn.android.R;
import com.rn.android.fragment.DrugHistoryFragment;
import com.rn.android.model.DrugResponse;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.TouchImageView;
import com.rn.android.util.Utils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    public List<DrugResponse> drugList, filterList;
    public DrugHistoryFragment drugHistoryFragment;


    Context context;
    int checkedCount = 0;

    private String getFormate(String date) throws ParseException {
        if (date != null && !date.isEmpty()) {
            Date d = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH).parse(date);
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

    public DrugAdapter(Context context, List<DrugResponse> drugList, DrugHistoryFragment fragment) {
        this.drugList = drugList;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.filterList = new ArrayList<>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.drugList);
        this.drugHistoryFragment = fragment;
        checkedCount = 0;
        fragment.updateRefillText(checkedCount);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_drug_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v) {
        }; // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // set the data in items

        final DrugResponse DrugResponse = filterList.get(position);

        if (DrugResponse.isChecked()) {
            holder.llMain.setBackgroundColor(holder.colorSelected);
        } else {
            holder.llMain.setBackgroundColor(holder.colorWhite);
        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filterList.get(position).isChecked) {
                    if (getRemainingQty(DrugResponse) <= 0) {
                        showNoRefillDialog(position, DrugResponse);
//                Api        Utils.showAlertToast(context, "No refill, Pharmacy will contact Doctor.");
//                    Utils.showAlertToast(context, "No enough refill, Please contact your doctor");
//                    return;
                    } else if (DrugResponse.getRx_expire_date() != null && !new Date().before(new Date(DrugResponse.rx_expire_date))) {
                        showExpiredRefillDialog();
                    } else {
                        filterList.get(position).setChecked(!DrugResponse.isChecked());
                        notifyItemChanged(position);

                        if (filterList.get(position).isChecked) {
                            checkedCount++;
                        } else {
                            checkedCount--;
                        }

                        drugHistoryFragment.updateRefillText(checkedCount);
                    }
                } else {
                    filterList.get(position).setChecked(!DrugResponse.isChecked());
                    notifyItemChanged(position);

                    if (filterList.get(position).isChecked) {
                        checkedCount++;
                    } else {
                        checkedCount--;
                    }

                    drugHistoryFragment.updateRefillText(checkedCount);
                }


            }

        });
        holder.imgPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrescription(DrugResponse, position);
            }
        });

        holder.img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(filterList.get(position));
            }
        });

        String drugStrengh = DrugResponse.getStrength();
        String drugValue = DrugResponse.getStrength_value();

        if (drugValue == null || drugValue.equalsIgnoreCase("null")) {
            drugValue = "";
        } else {
            drugValue = " " + drugValue;
        }

        if (drugStrengh == null || drugStrengh.equalsIgnoreCase("null")) {
            drugStrengh = "";
        } else {
            drugStrengh = " " + drugStrengh;
        }

        String prn = "";
        if (DrugResponse.getMed_type() != null && DrugResponse.getMed_type().startsWith("P")) {
            prn = " (PRN)";
        }

        if (DrugResponse.getQty().isEmpty()) {
            DrugResponse.setQty("0");
        }

        holder.txtName.setText(DrugResponse.getDrug() + " " + drugValue + drugStrengh + "" + prn);
        holder.txtLastDate.setText(Utils.formatDateFromOnetoAnother(DrugResponse.getLast_pickedup_date(), "MM/dd/yyyy hh:mm:ss aaa", "MM/dd/yy"));
        holder.txtNextDate.setText(Utils.addDay(DrugResponse.getLast_pickedup_date(), "MM/dd/yyyy hh:mm:ss aaa", Integer.parseInt(DrugResponse.getDays_supply())));
        holder.txtQty.setText(new DecimalFormat().format(Double.parseDouble(DrugResponse.getQty())));
        holder.txtRefillRemaining.setText(new DecimalFormat().format(getRemainingQty(DrugResponse)));
        holder.txtRefillRemaining.setText(DrugResponse.getRemain_Refill());
    }

    private void showNoRefillDialog(final int pos, final DrugResponse drugResponse) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
// ...Irrelevant code for customizing the buttons and title

        dialogBuilder.setMessage("This prescription has no refills.The pharmacy will contact the prescriber to get more refills. It will be very helpful, if you could contact the prescriber as well. Thank you");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filterList.get(pos).setChecked(!drugResponse.isChecked());
                notifyItemChanged(pos);

                if (filterList.get(pos).isChecked) {
                    checkedCount++;
                } else {
                    checkedCount--;
                }

                drugHistoryFragment.updateRefillText(checkedCount);
            }
        });
        dialogBuilder.setNegativeButton("No", null);
//        dialogBuilder.setTitle("Rx Information");
        AlertDialog alertDialog = dialogBuilder.create();


        alertDialog.show();

    }

    private void showExpiredRefillDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
// ...Irrelevant code for customizing the buttons and title

        dialogBuilder.setMessage("Expired Prescription, Pharmacy will contact Physician. To expediate the process, please contact Physician as well.");
        dialogBuilder.setPositiveButton("Ok", null);
//        dialogBuilder.setTitle("Rx Information");
        AlertDialog alertDialog = dialogBuilder.create();


        alertDialog.show();

    }

    private void showDialog(DrugResponse drugResponse) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = ((BaseActivity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rx_info, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Ok", null);
//        dialogBuilder.setTitle("Rx Information");
        AlertDialog alertDialog = dialogBuilder.create();


//        final Dialog dialog = new Dialog(context);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setContentView(R.layout.dialog_rx_info);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        TextView txtRxNo = (TextView) dialogView.findViewById(R.id.txt_rx_no);
        TextView txtPrescribeDate = (TextView) dialogView.findViewById(R.id.txt_prescribe_date);
        TextView txtSIG = (TextView) dialogView.findViewById(R.id.txt_sig);
        TextView txtOriginalRefill = (TextView) dialogView.findViewById(R.id.txt_original_refill);
        TextView txtStopDate = (TextView) dialogView.findViewById(R.id.txt_stop_date);
        TextView txt_doctor = (TextView) dialogView.findViewById(R.id.txt_doctor);

        txtRxNo.setText(drugResponse.getPharmacy_order_id());
        txtSIG.setText(drugResponse.getSig_english());
        if (drugResponse.getTran_date() != null){
            txtPrescribeDate.setText(Utils.formatDateFromOnetoAnother(drugResponse.getTran_date(), "MM/dd/yyyy hh:mm:ss aaa", "MM/dd/yy"));
        }
           // txtPrescribeDate.setText(Utils.formatDateFromOnetoAnother(drugResponse.getTran_date(), "dd/MM/yyyy hh:mm:ss a", "MM/dd/yy"));
        if (drugResponse.getNo_of_refill() != null)
            txtOriginalRefill.setText(drugResponse.getNo_of_refill());

        if (drugResponse.getDocter_name() != null)
            txt_doctor.setText(drugResponse.getDocter_name());

        if (drugResponse.getStop_date() != null)
            txtStopDate.setText(Utils.formatDateFromOnetoAnother(drugResponse.getStop_date().toString(), "dd/MM/yyyy hh:mm:ss a", "MM/dd/yy"));

        alertDialog.show();
    }

    private void getPrescription(DrugResponse item, final int pos) {

        ((BaseActivity) context).showProgressDialog(context);
        NetworkUtility.makeJSONObjectRequest(API.PriscriptionImagePath + "?tran_id=" + item.getExternal_prescription_id() , new JSONObject(), API.PriscriptionImagePath, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {
                    ((BaseActivity) context).dismissProgressDialog();
                    if (result.getString("Data").isEmpty()) {
                        Utils.showAlertToast(context, "No Priscription Image available");
                    } else {
                        openImageDialog(result.getString("Data"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(JSONObject result) {
                ((BaseActivity) context).dismissProgressDialog();
            }
        });

    }

    private void openImageDialog(String imageURL) {
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

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TouchImageView dialog_image = (TouchImageView) dialog.findViewById(R.id.dialog_image);
        SimpleDraweeView drawee_image = (SimpleDraweeView) dialog.findViewById(R.id.drawee_image);
        final ImageView dialog_cancel = (ImageView) dialog.findViewById(R.id.dialog_cancel);

        //http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(context).load(imageURL).placeholder(R.drawable.app_icon).error(R.drawable.app_icon).into(dialog_image);
        drawee_image.setVisibility(View.GONE);
        Uri uri = Uri.parse(imageURL);
//        Uri uri = Uri.parse("http://www.sachinmittal.com/wp-content/uploads/2017/04/47559184-image.jpg");
        drawee_image.setImageURI(uri);

        dialog.show();

    }


    private int getRemainingQty(DrugResponse drugResponse) {

        return (int) ((Float.parseFloat(drugResponse.getLast_qty_approved()) - Float.parseFloat(drugResponse.getLast_qty_billed())) / Double.parseDouble(drugResponse.getQty()));
    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }


    public List<DrugResponse> getDrugList() {
        return filterList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_qty)
        TextView txtQty;
        @BindView(R.id.txt_last_date)
        TextView txtLastDate;
        @BindView(R.id.txt_next_date)
        TextView txtNextDate;
        @BindView(R.id.txt_refill_remaining)
        TextView txtRefillRemaining;
        @BindView(R.id.img_info)
        ImageView img_info;
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.img_prescription)
        ImageView imgPrescription;

        @BindColor(R.color.Tomato)
        int colorSelected;
        @BindColor(R.color.white)
        int colorWhite;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            ButterKnife.bind(this, itemView);
        }
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

                    filterList.addAll(drugList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (DrugResponse item : drugList) {
                        if (item.getDrug().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDrug().toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((DrugHistoryActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    public void getDoctorName(final DrugResponse drugResponse) {
        JSONObject jsonObject = new JSONObject();
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ((BaseActivity) context).showProgressDialog(context);
        NetworkUtility.makeJSONObjectRequest(API.GetUpdatedDoctorData, new JSONObject(), API.GetUpdatedDoctorData, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    ((BaseActivity) context).dismissProgressDialog();
                    if (result != null) {
                        showDialog(drugResponse);


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                ((BaseActivity) context).dismissProgressDialog();

            }
        });
    }
}
