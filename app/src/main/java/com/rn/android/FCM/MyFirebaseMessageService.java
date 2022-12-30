package com.rn.android.FCM;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.metiz.pelconnect.R;
import com.rn.android.MyApplication;
import com.rn.android.MainActivity;
import com.rn.android.util.NotificationListActivity;

import java.util.Date;
import java.util.Map;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    String TAG = "MyFCM";
    public static final String NOTIFICATION_CHANNEL_ID = "101";
    public static final String NOTIFICATION_NAME = "Pelmeds Notification";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notifManager;

    public MyFirebaseMessageService() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        try {
            if (remoteMessage.getData().size() > 0) {
                Log.e(TAG, "onMessageReceived: >>>>>>>" + remoteMessage.getData(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From:>>>>" + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Log.e("data>>>>>", "" + data.toString());

            int count = MyApplication.getPrefranceDataInt("NotificationCount");
            count++;
            MyApplication.setPreferences("NotificationCount", count);

            if (!data.get("body").equalsIgnoreCase("Pelmeds")) {
                sendNotification(data.get("title"), data.get("body"), this);
            }

//            Intent i = new Intent("com.notification");
//            sendBroadcast(i);
            KeyguardManager myKM = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);

        }
    }

    // Also if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated. See sendNotification method below.


    private int getNotificationIcon() {
//        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
//        return useWhiteIcon ? R.drawable.button_bg_solid : R.mipmap.ic_launcher;
        return R.mipmap.ic_launcher;
    }


    //----------------
    private void sendNotification(String title, String messageBody, Context context) {
        Log.e("rsp", messageBody);


        Intent intent = null;


        NotificationCompat.Builder builder = null;
        final int NOTIFY_ID = Integer.valueOf(NOTIFICATION_CHANNEL_ID); // ID of notification
        String id = NOTIFICATION_NAME; // default_channel_id
        intent = new Intent(this, NotificationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("if--", "0000");
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setContentIntent(pendingIntent)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(getNotificationIcon())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        } else {
            Log.e("else--", "0000");

            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            builder.setContentTitle(title)
                    .setContentText(messageBody)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("Accept your request")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        int random = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        //   notificationManager.notify(random, notificationBuilder.build());
        Notification notification = builder.build();
        Log.e("notification", "" + notification);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Log.e("notificationManager", "" + notificationManager);
        notificationManager.notify(random, notification);
        Log.e("notify", "" + NOTIFY_ID);

    }
}