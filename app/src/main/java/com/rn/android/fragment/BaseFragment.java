package com.rn.android.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;

import com.metiz.pelconnect.R;
import com.rn.android.util.NotificationListActivity;

/**
 * Created by hp on 13/2/18.
 */

public class BaseFragment extends Fragment {

    private ProgressDialog pDialog;

    public void initProgressDialog(Context context) {
        if (pDialog == null)
            pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
    }

    public void showProgressDialog(Context context) {

        try {
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.show();
            } else {
                initProgressDialog(context);
                pDialog.show();
            }
        } catch (Exception ex) {

        }
    }

    public void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    public void sendNotification(String title, String messageBody) {

        Intent intent = new Intent(getActivity(), NotificationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0 /* Request code */, intent,
                0);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(getNotificationIcon())
//                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    public int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }
}
