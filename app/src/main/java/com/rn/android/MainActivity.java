package com.rn.android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.metiz.pelconnect.R;
import com.rn.android.model.Facility;
import com.rn.android.model.PatientPOJO;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.MyReceiver;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_add_order)
    TextView txtAddOrder;
    @BindView(R.id.view_add_order)
    View viewAddOrder;
    @BindView(R.id.txt_order_history)
    TextView txtOrderHistory;
    @BindView(R.id.view_oder_history)
    View viewOderHistory;

    private BroadcastReceiver MyReceiver = null;

    private Toolbar toolbar;
    public TextView toolbar_facility_name;
    private ImageView imgChange, imgLogout;
    private Spinner spinner_facility;
    private List<Facility> facilityList;
    ProgressDialog progressDialog;
    private int SCANNED_BARCODE_ID = 0;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private void initProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//    MyReceiver = new MyReceiver();

        broadcastIntent();
        ButterKnife.bind(this);
        initProgress();
        initToolbar();
//        setUpTabs();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        MyReceiver = new MyReceiver();
        broadcastIntent();
        txtAddOrder.performClick();
    }

    private void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void checkPrefrenceandLoadData() {
        try {
            if (MyApplication.getPrefranceData("IsConfirm").equalsIgnoreCase("false")) {
                openDialog(true);
                Log.e("Step", "==============1");
            } else if (getIntent().getExtras().containsKey("isFromLogin") && getIntent().getExtras().getBoolean("isFromLogin")) {
                Log.e("Step", "==============2");
                changeFacilityDialog(true);
            } else if (getIntent().getExtras().getBoolean("isFromSpash")) {
                Log.e("Step", "==============3");
                List<Facility> list = MyApplication.getGson().fromJson(MyApplication.getPrefranceData("Facility"), new TypeToken<List<Facility>>() {
                }.getType());
                if (list.size() == 1) {
                    imgChange.setVisibility(View.GONE);
                    toolbar_facility_name.setText(list.get(0).getFacilityName());
                } else {
                    toolbar_facility_name.setText(list.get(MyApplication.getPrefranceDataInt("facility_selected_index")).getFacilityName());
                }
                FragmentAddOrder fragment = (FragmentAddOrder) getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (fragment != null)
                    fragment.getPatientList(MyApplication.getPrefranceDataInt("facility"));

            } else {
                FragmentAddOrder fragment = (FragmentAddOrder) getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (fragment != null)
                    fragment.getPatientList(MyApplication.getPrefranceDataInt("facility"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_facility_name = (TextView) toolbar.findViewById(R.id.toolbar_sub_title);
        imgChange = (ImageView) findViewById(R.id.img_change);


        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFacilityDialog(false);
            }
        });
    }

    private void openDialog(boolean b) {
        if (b) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.accept_dialog);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            TextView accept = (TextView) dialog.findViewById(R.id.dialog_txt_accept);
            TextView decline = (TextView) dialog.findViewById(R.id.dialog_txt_decline);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    AcceptTermsAPI();
                    changeFacilityDialog(true);
                }
            });
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    imgLogout.performClick();
                }
            });

            dialog.show();
        }
    }

    private void AcceptTermsAPI() {
        progressDialog.show();
        NetworkUtility.makeJSONObjectRequest(API.ConfirmHipa + "?UserID=" + MyApplication.getPrefranceData("UserID"), new JSONObject(), API.ConfirmHipa, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    MyApplication.setPreferences("IsConfirm", "true");
                    if (result != null) {
                        Type listType = new TypeToken<List<PatientPOJO>>() {
                        }.getType();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject result) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public boolean changeFacilityDialog(final boolean isFromLogin) {
        List<Facility> list = MyApplication.getGson().fromJson(MyApplication.getPrefranceData("Facility"), new TypeToken<List<Facility>>() {
        }.getType());


        if (list.size() == 1) {
            MyApplication.setPreferences("facility", list.get(0).getFacilityID());
            MyApplication.setPreferences("ExFacilityID", list.get(0).getExFacilityID());
            FragmentAddOrder fragment = (FragmentAddOrder) getSupportFragmentManager().findFragmentByTag("AddOrder");
            if (fragment != null)
                fragment.getPatientList(list.get(0).getFacilityID());
            imgChange.setVisibility(View.GONE);
            toolbar_facility_name.setText(list.get(0).getFacilityName());
        } else {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.dialog_change_facility);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);

            spinner_facility = (Spinner) dialog.findViewById(R.id.spinner_facility);

            TextView accept = (TextView) dialog.findViewById(R.id.btn_dialog_ok);
            TextView decline = (TextView) dialog.findViewById(R.id.btn_dialog_cancel);


            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (spinner_facility.getAdapter() != null) {

                        MyApplication.setPreferences("facility", ((Facility) spinner_facility.getAdapter().getItem(spinner_facility.getSelectedItemPosition())).getFacilityID());
                        MyApplication.setPreferences("ExFacilityID", ((Facility) spinner_facility.getAdapter().getItem(spinner_facility.getSelectedItemPosition())).getExFacilityID());
                        MyApplication.setPreferences("facility_selected_index", spinner_facility.getSelectedItemPosition());
                        FragmentAddOrder fragment = (FragmentAddOrder) getSupportFragmentManager().findFragmentByTag("AddOrder");
                        if (fragment != null)
                            fragment.getPatientList(((Facility) spinner_facility.getSelectedItem()).getFacilityID());

                        toolbar_facility_name.setText(((Facility) spinner_facility.getAdapter().getItem(spinner_facility.getSelectedItemPosition())).getFacilityName());
                    }
                }
            });
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (isFromLogin)
                        imgLogout.performClick();
                }
            });
            fillFacilitySpinner();
            dialog.show();
        }

        return true;
    }

    private void fillFacilitySpinner() {
        try {

            Type listType = new TypeToken<List<Facility>>() {
            }.getType();
            facilityList = MyApplication.getGson().fromJson(MyApplication.getPrefranceData("Facility"), listType);

            ArrayAdapter<Facility> dataAdapter = new ArrayAdapter<Facility>(this,
                    R.layout.simple_spinner_item, facilityList);
            dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);

            spinner_facility.setAdapter(dataAdapter);

            if (MyApplication.getPrefranceDataInt("facility") != 0) {
                spinner_facility.setSelection(MyApplication.getPrefranceDataInt("facility_selected_index"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setUpTabs() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("Add Order").setTag("AddOrder"));
//        tabLayout.addTab(tabLayout.newTab().setText("Order History").setTag("OrderHistory"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
        viewPager = (ViewPager) findViewById(R.id.pager);
//        adapter = new PagerAdapter
//                (getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager.setCurrentItem(0);


        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new FragmentAddOrder().newInstance(), "Add Order");
        adapter.addFragment(new FragmentOrderHistory().newInstance(), "Order History");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);
    }

    @OnClick({R.id.txt_add_order, R.id.txt_order_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_add_order:
                viewAddOrder.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                viewOderHistory.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                replaceFragment(new FragmentAddOrder(), R.id.content_frame);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkPrefrenceandLoadData();
                break;
            case R.id.txt_order_history:
                viewAddOrder.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                viewOderHistory.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                replaceFragment(new FragmentOrderHistory(), R.id.content_frame);
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (result != null) {

            if (result.getContents() == null) {

//cancel

            } else {
                Log.e("result", result.getContents() + "\n" + result.getBarcodeImagePath() + "\n" + result.getFormatName() + "\n" + result.getErrorCorrectionLevel());
                FragmentAddOrder fragment = (FragmentAddOrder) getSupportFragmentManager().findFragmentByTag("AddOrder");

                if (SCANNED_BARCODE_ID == 0) {

                    if (fragment != null) {
                        fragment.setRxText(result.getContents());
                    }

                } else {
                    fragment.setDrugText(result.getContents());
                }
//Scanned successfully

            }

        } else {

            super.onActivityResult(requestCode, resultCode, intent);

        }

    }

    public void replaceFragment(Fragment fr, int id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(id, fr);
        fragmentTransaction.commit();
    }
}
