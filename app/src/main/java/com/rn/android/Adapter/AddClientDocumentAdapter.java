package com.rn.android.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.metiz.pelconnect.R;
import com.rn.android.AddClientPharmaActivity;
import com.rn.android.util.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClientDocumentAdapter extends
        RecyclerView.Adapter<AddClientDocumentAdapter.ViewHolder> {

    private static final String TAG = AddClientDocumentAdapter.class.getSimpleName();


    private Context context;
    private List<String> list;

    public AddClientDocumentAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    public void updateMe(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.row_client_document, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = list.get(position);

       /* Log.e("Image Path", "In adapter ==== > " + item);
        Log.e("Image Path", "Name ==== > " + new File(item).getName());
        Log.e("Image Path", "Path ==== > " + FileUtils.getPath(context, Uri.parse(item)));
*/

        String mime = FileUtils.getMimeType(context, item);
        if ((mime != null && mime.toLowerCase().contains("pdf") || new File(item).getName().toLowerCase().endsWith(".pdf"))) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.img.setImageDrawable(context.getDrawable(R.drawable.pdf_img));
            } else {
                holder.img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pdf_img));
            }

        } else {

            File file = new File(item);


            Glide.with(context)
                    .load(file.getAbsolutePath())
                    .placeholder(R.drawable.app_icon)
                    .error(R.drawable.app_icon)
                    .into(holder.img);


//            holder.img.setImageURI(Uri.parse(item));

        }


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure, you want to delete document?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, list.size());

                        ((AddClientPharmaActivity) context).listUpdated(list);
                    }
                })
                        .setNegativeButton("No", null).show();
            }
        });


        //Todo: Setup viewholder for item
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings
        @BindView(R.id.img)
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}