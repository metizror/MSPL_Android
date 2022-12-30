package com.rn.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rn.android.R;
import com.rn.android.model.modelMedication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 13/3/18.
 */
public class MedicationAdapter extends
        RecyclerView.Adapter<MedicationAdapter.ViewHolder> {

    private static final String TAG = MedicationAdapter.class.getSimpleName();


    private final Context context;
    private final List<modelMedication> list;

    public MedicationAdapter(Context context, ArrayList<modelMedication> list
    ) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_medication, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        modelMedication item = list.get(position);
        holder.tv_patient_name.setText(item.getPatient());
        holder.tv_drug_name.setText(item.getDrug());
        holder.tv_rx_number.setText(item.getRx_No());
        holder.tv_qty.setText(item.getQty());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        return (null != list ? list.size() : 0);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.tv_patient_name)
        TextView tv_patient_name;
        @BindView(R.id.tv_drug_name)
        TextView tv_drug_name;
        @BindView(R.id.tv_rx_number)
        TextView tv_rx_number;
        @BindView(R.id.tv_qty)
        TextView tv_qty;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}