package com.rn.android.Adapter;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metiz.pelconnect.R;
import com.rn.android.BaseActivity;
import com.rn.android.model.NewModelWillBeRefill;
import com.rn.android.model.WillBeRefill;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 13/3/18.
 */
public class WillBeRifillAdapter extends
        RecyclerView.Adapter<WillBeRifillAdapter.ViewHolder> {

    private static final String TAG = WillBeRifillAdapter.class.getSimpleName();


    private Context context;
    private List<NewModelWillBeRefill> list;

    public WillBeRifillAdapter(Context context, ArrayList<NewModelWillBeRefill> list
    ) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.txt_patient_name)
        TextView txtPatientName;
        @BindView(R.id.img_info)
        ImageView imgInfo;
        @BindView(R.id.txt_doctor_name)
        TextView txtDoctorName;
        @BindView(R.id.tv_rx_number)
        TextView tvRxNumber;
        @BindView(R.id.tv_drug_name)
        TextView tvDrugName;
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.llMainLayout)
        LinearLayout llMainLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_will_be_rifill, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArrayList<WillBeRefill> item = list.get(position).getWillBeRefillArrayList();

        for ( int i = 0; i < item.size(); i++) {
            if (item.get(i).getKeyText().equals("Patient")) {
                holder.txtPatientName.setText(item.get(i).getValueText());

            }

            if (item.get(i).getKeyText().equals("Doctor")) {
                holder.txtDoctorName.setText(item.get(i).getValueText());

            }
            if (item.get(i).getKeyText().equals("Rx")) {
                holder.tvRxNumber.setText(item.get(i).getValueText());

            } if (item.get(i).getKeyText().equals("Drug")) {
                holder.tvDrugName.setText(item.get(i).getValueText());

            }
        }




        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(item);



            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private void showDialog(ArrayList<WillBeRefill> WillBeRefill) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context,R.style.DialogStyle);
        LayoutInflater inflater = ((BaseActivity) context).getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_rifill_information, null);
        dialogBuilder.setView(dialogView);

     //   dialogBuilder.setPositiveButton("Ok", null);
        AlertDialog alertDialog = dialogBuilder.create();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = alertDialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        LinearLayout ll_view = (LinearLayout) dialogView.findViewById(R.id.ll_view);
        TextView tv_ok = (TextView) dialogView.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



        for (int i = 0; i <WillBeRefill.size() ; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom_view,null);
            TextView tv_key_value =(TextView)view.findViewById(R.id.tv_key_value);
            TextView tv_key_text =(TextView)view.findViewById(R.id.tv_key_text);
            if (WillBeRefill.get(i).isIsDisplayMobile()){
                tv_key_value.setText(WillBeRefill.get(i).getKeyText() );
                tv_key_text.setText(WillBeRefill.get(i).getValueText());
                ll_view.addView(view);

            }


        }

        alertDialog.show();


    }

}