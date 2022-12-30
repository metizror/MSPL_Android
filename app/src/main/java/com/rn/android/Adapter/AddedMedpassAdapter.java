package com.rn.android.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rn.android.R;
import com.rn.android.databinding.RowDiscontinueMedPassListBinding;
import com.rn.android.model.ChangedPrescriptionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 10/3/2017.
 */

public class AddedMedpassAdapter extends RecyclerView.Adapter<AddedMedpassAdapter.MyViewHolder> {

    RowDiscontinueMedPassListBinding mBinding;
    private OnViewClick onViewClick;
    private LayoutInflater inflater;
    private List<ChangedPrescriptionModel.DataBean> dataBeanList;
    private Context context;
    private Activity activity;

    public AddedMedpassAdapter(Activity activity, Context context, List<ChangedPrescriptionModel.DataBean> dataBeanList, OnViewClick onViewClick) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
        this.onViewClick = onViewClick;
        this.dataBeanList = new ArrayList<>();
        this.dataBeanList.addAll(dataBeanList);
    }

    public interface OnViewClick {
        void onItemViewClick(String position, ChangedPrescriptionModel.DataBean holderData);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.row_discontinue_med_pass_list, parent, false);
        return new MyViewHolder(mBinding.getRoot(), mBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.setIsRecyclable(false);
        ChangedPrescriptionModel.DataBean holderData = dataBeanList.get(position);

        holder.mBinding.txtFirstName.setText(Html.fromHtml("<b>" + holderData.getDrug() + "</b>"));
        holder.mBinding.txtDrugColor.setText(holderData.getColor());
        holder.mBinding.txtDrugShap.setText(holderData.getShape());
        holder.mBinding.txtDrugType.setText(holderData.getImprint());
        Glide.with(context).load(holderData.getDrugImage()).into(holder.mBinding.imgMedSheet);
        holder.mBinding.imgMedSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewClick.onItemViewClick(String.valueOf(position), holderData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataBeanList ? dataBeanList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowDiscontinueMedPassListBinding mBinding;

        public MyViewHolder(View itemView, RowDiscontinueMedPassListBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}
