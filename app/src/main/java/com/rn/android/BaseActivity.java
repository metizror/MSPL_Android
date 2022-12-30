package com.rn.android;

import static com.rn.android.PatientListActivityNew.clickPosition;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.metiz.pelconnect.R;
import com.rn.android.model.EmergencyMessage;
import com.rn.android.model.HolidayMaster;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.DialogCallbacks;
import com.rn.android.util.NotificationListActivity;
import com.rn.android.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaseActivity extends AppCompatActivity {
    public Context context;
    public Activity activity;

    public static ProgressDialog pDialog;
    public static int notificationCountCart = 0;
    public static List<HolidayMaster> holidayMasterList = new ArrayList<>();
    public static List<EmergencyMessage> emergencyMessageList = new ArrayList<>();

    public static void initProgressDialog1(Context context) {
        if (pDialog == null)
            pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("OnCreate", "OnCreate");
        context = BaseActivity.this;
        activity = BaseActivity.this;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!MyApplication.getPrefranceDataBoolean("isLogin")) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
        Log.e("onResume", "onResume");

            if (MyApplication.getPrefranceDataLong("time") != 0) {
                long diff = System.currentTimeMillis() - MyApplication.getPrefranceDataLong("time");

                if (TimeUnit.MILLISECONDS.toMinutes(diff) >= MyApplication.getPrefranceDataLong("sessiontime")) {
//                if (TimeUnit.MILLISECONDS.toMinutes(diff) >= 1) {
                    LogoutAPICall();
                } else {
                    Log.e("TAG", "logouttime and inactive time: "+TimeUnit.MILLISECONDS.toMinutes(diff) +MyApplication.getPrefranceDataBoolean("isSessionLogoutMin") +" "+MyApplication.getPrefranceDataLong("sessiontime")+" ");
                    long time = System.currentTimeMillis();
                    MyApplication.setPreferencesLong("time", time);
                }
            } else {
                long time = System.currentTimeMillis();
                MyApplication.setPreferencesLong("time", time);
            }

        Log.e("TAG", "logouttime and inactive time: "+MyApplication.getPrefranceDataBoolean("isSessionLogoutMin") +" "+MyApplication.getPrefranceDataLong("sessiontime")+" ");

    }

    @SuppressLint("MissingPermission")
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        long time = System.currentTimeMillis();
        MyApplication.setPreferencesLong("time", time);
        Log.e("CurentTime", MyApplication.getPrefranceDataLong("time") + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "onDestroy");
        long time = System.currentTimeMillis();
        MyApplication.setPreferencesLong("time", time);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");
        //   stopDisconnectTimer();
    }

    public void initProgressDialog(Context context) {
        if (pDialog == null)
            pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please wait...");
    }

    public void showProgressDialog(Context context) {

        try {
            if (pDialog == null || pDialog.isShowing()) {
                initProgressDialog(context);
            }
            pDialog.show();
        } catch (Exception ex) {

        }
    }

    public void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void getPasswordFlag() {
        NetworkUtility.makeJSONObjectRequest(API.GetPassWordflag + "?UserID=" + MyApplication.getPrefranceData("UserID"), new JSONObject(), API.GetPassWordflag + "?UserID=" + MyApplication.getPrefranceData("UserID"), new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {

                    if (result != null) {
                        Log.e("result", result.toString());

                        if (result.has("Data")) {
                            if (result.getInt("Data") == 2) {

                                Utils.showDialogwithCallbacks(BaseActivity.this, null, "Seems like your password has been changed, Please login again.", new DialogCallbacks() {
                                    @Override
                                    public void positiveClicked() {

                                        LogoutAPICall();
                                    }

                                    @Override
                                    public void nagetiveClicked() {
                                    }

                                    @Override
                                    public void neutralClicked() {

                                    }
                                });

                            } else if (result.getInt("Data") == 3) {


                                Utils.showDialogwithCallbacks(BaseActivity.this, null, "Your password is too old, You must need to change it.", new DialogCallbacks() {
                                    @Override
                                    public void positiveClicked() {

                                        MyApplication.setPreferencesBoolean("changePassword", true);
                                        Intent i = new Intent(BaseActivity.this, ChangePasswordActivity.class);
                                        startActivity(i);

                                    }

                                    @Override
                                    public void nagetiveClicked() {

                                    }

                                    @Override
                                    public void neutralClicked() {

                                    }
                                });
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {

            }


        });
    }

    public void LogoutAPICall() {

        showProgressDialog(this);

        NetworkUtility.makeJSONObjectRequest(API.GetLogout + "?UserID=" + MyApplication.getPrefranceData("UserID") + "&TokenID=" + MyApplication.getPrefranceData("device_token"), new JSONObject(), API.GetLogout + "?UserID=" + MyApplication.getPrefranceData("UserID"), new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    dismissProgressDialog();
                    //Yes button clicked

                    boolean isSessionLogoutMin = MyApplication.getPrefranceDataBoolean("isSessionLogoutMin");
                    MyApplication.setPreferencesBoolean("isLogin", false);
                    MyApplication.clearPrefrences();
                    MyApplication.setPreferencesBoolean("isSessionLogoutMin", isSessionLogoutMin);
                    Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                    clickPosition = 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();

            }
        });


    }


    public int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    public void sendNotification(String title, String messageBody) {

        int notificationId = new Random().nextInt(1000000);
        Intent intent = new Intent(this, NotificationListActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getNotificationIcon())
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))

                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }

        notificationManager.notify(notificationId  /* ID of notification */, notificationBuilder.build());
    }


}
