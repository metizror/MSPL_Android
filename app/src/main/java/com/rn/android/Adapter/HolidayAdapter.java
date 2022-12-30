package com.rn.android.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rn.android.R;
import com.rn.android.model.HolidayMaster;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 13/3/18.
 */
public class HolidayAdapter extends
        RecyclerView.Adapter<HolidayAdapter.ViewHolder> {

    private static final String TAG = HolidayAdapter.class.getSimpleName();


    private Context context;
    private List<HolidayMaster> list;

    public HolidayAdapter(Context context, List<HolidayMaster> list
    ) {
        this.context = context;
        this.list = list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        @BindView(R.id.txt_description)
        TextView txtDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_holiday, parent, false);
        ButterKnife.bind(this, view);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HolidayMaster item = list.get(position);
        holder.txtDescription.setText(item.getDescription());

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