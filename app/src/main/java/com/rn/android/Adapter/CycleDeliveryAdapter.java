package com.rn.android.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rn.android.CycleActivity;
import com.rn.android.R;
import com.rn.android.model.ModelCycleDeliveryDate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 13/3/18.
 */
public class CycleDeliveryAdapter extends
        RecyclerView.Adapter<CycleDeliveryAdapter.ViewHolder> {

    private static final String TAG = CycleDeliveryAdapter.class.getSimpleName();


    private CycleActivity context;
    private List<ModelCycleDeliveryDate> list;
    String cycleDate = "";
    public int selectedCenterPos = 0;


    public CycleDeliveryAdapter(CycleActivity context, List<ModelCycleDeliveryDate> list, String cycleDate) {
        this.context = context;
        this.list = list;
        this.cycleDate = cycleDate;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_day)
        TextView tvDay;

        @BindView(R.id.ll_date_time)
        LinearLayout llDateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_cycle_delivery_date, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelCycleDeliveryDate item = list.get(position);
     //   context.getSelctedDate(item);


        if (item.isSelected())
        {
            holder.tvDate.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvDay.setTextColor(context.getResources().getColor(R.color.white));
            holder.llDateTime.setBackground(context.getResources().getDrawable(R.drawable.cycle_delivery_selected));

        }else {
            holder.tvDate.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvDay.setTextColor(context.getResources().getColor(R.color.black));
            holder.llDateTime.setBackground(context.getResources().getDrawable(R.drawable.edittext_round_bg));

        }

        holder.tvDate.setText(item.getPreviousDaywithdate());
        holder.tvDay.setText(item.getPreviousDaywithdateName());
        holder.llDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.isSelected()) {
//                    selectedCenterPos = -1;
//                    item.setSelected(false);
                } else {
//                    if (selectedCenterPos != -1)
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).isSelected())
                        {
                            list.get(i).setSelected(false);
                        }
                    }
                        list.get(selectedCenterPos).setSelected(false);
                    item.setSelected(true);
                    selectedCenterPos = position;
                    context.getSelctedDateNew(list.get(selectedCenterPos).getPreviousDaywithdate());

                }
                notifyDataSetChanged();
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
}