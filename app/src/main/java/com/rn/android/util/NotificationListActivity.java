package com.rn.android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.rn.android.MyApplication;
import com.rn.android.BaseActivity;
import com.rn.android.R;
import com.rn.android.model.Notification;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationListActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_activity_name)
    TextView tvActivityName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<Notification> notificationList = new ArrayList<>();
    public static int count = 0;
    NotificationAdapter notificationAdapter;
    private KProgressHUD hud;
    private BroadcastReceiver MyReceiver = null;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        ButterKnife.bind(this);
        MyReceiver = new MyReceiver();

        MyApplication.getPrefranceDataInt("facility");
        initRecyclerView();

        getNotificationList();

        setTitle("Notification");
        MyApplication.setPreferences("NotificationCount", 0);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void scrollList() {
        recyclerView.smoothScrollToPosition(0);
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    private void getNotificationList() {
        hud.show();
        //   showProgressDialog(this);
        int facilityID = MyApplication.getPrefranceDataInt("ExFacilityID");
        NetworkUtility.makeJSONObjectRequest(API.NotificationList + "?FacilityID=" + MyApplication.getPrefranceDataInt("ExFacilityID") + "&UserId=" + MyApplication.getPrefranceData("UserID"), new JSONObject(), API.NotificationList, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                hud.dismiss();
                try {
                   // dismissProgressDialog();

                    if (result != null) {
                        Type listType = new TypeToken<List<Notification>>() {
                        }.getType();

                        notificationList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);
                        count = notificationList.size();
                        //   recyclerView.setAdapter(new NotificationAdapter(NotificationListActivity.this, notificationList));
                        notificationAdapter = new NotificationAdapter(NotificationListActivity.this, notificationList);
                        notificationAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(notificationAdapter);

                   //     Utils.showAlertToast(NotificationListActivity.this, result.getString("Message"));


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Utils.showAlertToast(NotificationListActivity.this, getResources().getString(R.string.something_went_wrong));
                    recyclerView.setAdapter(new NotificationAdapter(NotificationListActivity.this, new ArrayList<Notification>()));


                }
            }

            @Override
            public void onError(JSONObject result) {
                hud.dismiss();
                Utils.showAlertToast(NotificationListActivity.this, "something went wrong!");
                // dismissProgressDialog();
                recyclerView.setAdapter(new NotificationAdapter(NotificationListActivity.this, new ArrayList<Notification>()));


            }
        });
    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        broadcastIntent();
        registerReceiver(broadcastReceiver, new IntentFilter("com.notification"));
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MyApplication.setPreferences("NotificationCount", 0);
            getNotificationList();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

}


