package com.rn.android.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rn.android.R;
import com.rn.android.model.ReminderNotificationModel;
import com.rn.android.util.NotificationListActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MissDoseListAdapter extends RecyclerView.Adapter<MissDoseListAdapter.ViewHolder> {
    private List<ReminderNotificationModel.DataBean> list;
    NotificationListActivity context;
    public MissDoseListAdapter(NotificationListActivity notificationListActivity, List<ReminderNotificationModel.DataBean> reminderNotificationModels) {
        this.context = notificationListActivity;
        this.list = reminderNotificationModels;
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.missdose_medication_item, parent, false);
        ButterKnife.bind(this, view);

        MissDoseListAdapter.ViewHolder viewHolder = new MissDoseListAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ReminderNotificationModel.DataBean item = list.get(position);
        holder.tv_drug_name.setText(position+1+". "+item.getDrug());
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
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_drug_name)
        TextView tv_drug_name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
