package com.rn.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rn.android.R;
import com.rn.android.model.AlloyHospital;

import java.util.ArrayList;
import java.util.List;

public class AlloyHospitalAdapter extends RecyclerView.Adapter<AlloyHospitalAdapter.ViewHolder > {

    private Context context;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    private List<AlloyHospital.LOAHospital> loaHospitalList;
    private int selectedPosition = -1;
    private String loaHospitalName = "";
    private int loaHospitalID = -1;

    public AlloyHospitalAdapter(Context context, List<AlloyHospital.LOAHospital> loaHospitalList) {
        this.context = context;
        this.loaHospitalList = loaHospitalList;

        /*
        //initilize selectCheck
        for (int i = 0; i < alloyList.size(); i++) {
            selectCheck.add(0);
        }*/

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alloy_hospital_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AlloyHospital.LOAHospital alloyHospital = loaHospitalList.get(position);
        holder.tv_items.setText(alloyHospital.getLOAHospitalType());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //do your toast programming here.
                Log.d("TAG", "onCheckedChanged: " + loaHospitalList.get(position));
            }
        });

        holder.checkbox.setChecked(position== selectedPosition);

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TAG", "onCheckedChanged: " + loaHospitalList.get(position));
                if(isChecked)
                {
                    selectedPosition =  position;
                    AlloyHospital.LOAHospital hospitalObj = loaHospitalList.get(position);
                    Log.e("TAG", "onCheckedChanged: "+ hospitalObj.getLOAHospitalType());
                    loaHospitalName = hospitalObj.getLOAHospitalType();
                    loaHospitalID = hospitalObj.getLOAHospital_ID();
                }
                else{
                    selectedPosition = -1;
                }
                notifyDataSetChanged();
            }
        });


        /*holder.tv_items.setText(alloyList.get(position));

        if (selectCheck.get(position) == 1) {
            holder.checkbox.setChecked(true);
        } else {
            holder.checkbox.setChecked(false);
        }

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int k = 0; k < selectCheck.size(); k++) {
                    if (k == position) {
                        selectCheck.set(k, 1);
                    } else {
                        selectCheck.set(k, 0);
                    }
                }
                notifyDataSetChanged();

            }
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {
                    //Do whatever you want to do with selected value
                    Log.d("TAG", "onCheckedChanged: " + alloyList.get(position).trim());
                }
            }
        });*/

    }

    public String getLoaHospitalName(){
        return loaHospitalName;
    }

    public int getLoaHospitalID(){
        return loaHospitalID;
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if (loaHospitalList != null && loaHospitalList.size() > 0){
            return loaHospitalList.size();
        } else {
            return 0;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkbox;
        private TextView tv_items;

        public ViewHolder(View v) {
            super(v);
            checkbox = v.findViewById(R.id.checkbox);
            tv_items = v.findViewById(R.id.tv_items);
        }

    }
}
