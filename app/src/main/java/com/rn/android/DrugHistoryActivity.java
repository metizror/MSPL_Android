package com.rn.android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.DrugAdapter;
import com.rn.android.model.Delivery;
import com.rn.android.model.DrugResponse;
import com.rn.android.model.Facility;
import com.rn.android.model.Order;
import com.rn.android.model.PatientPOJO;
import com.rn.android.model.PrescriptionData;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class DrugHistoryActivity extends AppCompatActivity {

    @BindView(R.id.txt_refill)
    TextView txtRefill;
    private RecyclerView drugListView;
    List<DrugResponse> drugList = new ArrayList<>();
    private DrugAdapter drugAdapter;
    Toolbar toolbar;
    ImageView imgLogout, imgChange;
    ProgressDialog progressDialog;
    Spinner spinner_facility;
    private List<Delivery> deliverylist = new ArrayList<>();
    private List<Order> orderlist = new ArrayList<>();
    private Spinner spinner_delivery, spinner_order;
    private List<Facility> facilityList;
    private TextView toolbar_facility_name;
    private SearchView search;
    private TextView edt_rx_number;
    private TextView edt_drug;
    private int SCANNED_BARCODE_ID = 0;
    private List<PrescriptionData> prescriptionList;
    public PatientPOJO patient = new PatientPOJO();
    SwipeRefreshLayout swipeRefreshLayout;
    private ImageView imgBack;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);
        ButterKnife.bind(this);
        initView();
        initProgress();
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDrugList();
            }
        });

        patient = (PatientPOJO) getIntent().getSerializableExtra("obj");
        toolbar_title.setText(patient.getLastname() + ", " + patient.getFirstname());
        getDrugList();
    }

    private void getDrugList() {
        drugAdapter = new DrugAdapter(this, drugList, null);
        drugListView.setAdapter(drugAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_facility_name = (TextView) toolbar.findViewById(R.id.toolbar_sub_title);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        search = (SearchView) findViewById(R.id.search);
        imgChange = (ImageView) findViewById(R.id.img_change);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search.setVisibility(View.GONE);
        imgChange.setVisibility(View.GONE);
        imgLogout.setVisibility(View.GONE);
        drugListView = (RecyclerView) findViewById(R.id.patient_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        drugListView.setLayoutManager(new LinearLayoutManager(this));
        drugAdapter = new DrugAdapter(this, drugList, null);
        drugListView.setAdapter(drugAdapter);
        search.setQueryHint("Search Patient");
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        Utils.hideKeybord(this);
        search.setVisibility(View.GONE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (!TextUtils.isEmpty(newText.trim())) {
                        if (drugListView.getAdapter() != null) {
                            ((DrugAdapter) drugListView.getAdapter()).filter(newText);
                        }
                    } else {
                        ((DrugAdapter) drugListView.getAdapter()).filter("");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });

    }
    private void initProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }
    private void openAddOrderDialog(final PatientPOJO patientPojo) {

        final Dialog dialog = new Dialog(DrugHistoryActivity.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_add_drug_new);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        Button add = (Button) dialog.findViewById(R.id.btn_dialog_add);
        Button cancel = (Button) dialog.findViewById(R.id.btn_dialog_cancel);
        final TagContainerLayout tags = (TagContainerLayout) dialog.findViewById(R.id.tag_view);
        tags.setTagTextSize(18f);
        tags.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                tags.removeTag(position);

                if (tags.getChildCount() == 0) {
                    dialog.dismiss();
                }
            }
        });
        ImageView img_barcode_drug = (ImageView) dialog.findViewById(R.id.img_barcode_drug);
        ImageView img_barcode_rx = (ImageView) dialog.findViewById(R.id.img_barcode_rx);
        edt_rx_number = (TextView) dialog.findViewById(R.id.edt_rx_number);
        img_barcode_drug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SCANNED_BARCODE_ID = 1;
                setBarcode("scan drug");
            }
        });

        img_barcode_rx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SCANNED_BARCODE_ID = 0;
                setBarcode("scan Rx Number");
            }
        });
        edt_drug = (TextView) dialog.findViewById(R.id.edt_drug);


        final TextView txt_dialog_facility = (TextView) dialog.findViewById(R.id.txt_dialog_facility);


        txt_dialog_facility.setText(toolbar_facility_name.getText().toString());
        spinner_delivery = (Spinner) dialog.findViewById(R.id.spinner_delivery);
        spinner_order = (Spinner) dialog.findViewById(R.id.spinner_order);
        final EditText edt_notes = (EditText) dialog.findViewById(R.id.edt_notes);
        ArrayAdapter<Order> orderAdapter = new ArrayAdapter<Order>(this,
                R.layout.simple_spinner_item, orderlist);
        orderAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner_order.setAdapter(orderAdapter);

        ArrayAdapter<Delivery> dataAdapter = new ArrayAdapter<Delivery>(this,
                R.layout.simple_spinner_item, deliverylist);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner_delivery.setAdapter(dataAdapter);

        String drufStr = "";
//
        for (int i = 0; i < drugAdapter.getDrugList().size(); i++) {
            if (drugAdapter.getDrugList().get(i).isChecked()) {
                tags.addTag("Drug Name " + i);
//                if (!drufStr.isEmpty())
//                    drufStr = drufStr + i + ", ";
//                else
//                    drufStr = i + ", ";


            }
            if (!drufStr.isEmpty())
                edt_drug.setText(drufStr.substring(0, drufStr.length() - 1));
        }




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setBarcode(String msg) {
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        integrator.setPrompt(msg);
        integrator.setBeepEnabled(true);

        integrator.setOrientationLocked(false);

        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (result != null) {

            if (result.getContents() == null) {

//cancel

            } else {
                Log.e("result", result.getContents() + "\n" + result.getBarcodeImagePath() + "\n" + result.getFormatName() + "\n" + result.getErrorCorrectionLevel());

                if (SCANNED_BARCODE_ID == 0) {
                    edt_rx_number.setText(result.getContents());
                } else {
                    edt_drug.setText(result.getContents());
                }
            }

        } else {

            super.onActivityResult(requestCode, resultCode, intent);

        }

    }




    private boolean isPatientValid(String value) {
        if (!value.contains(",")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void getOrderndDelivery(final PatientPOJO patient) {
        progressDialog.show();

        NetworkUtility.makeJSONObjectRequest(API.OrderDelivertype, new JSONObject(), API.OrderDelivertype, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (result != null) {

                        List<Delivery> tmpDeliveryList = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Delivery").toString(), new TypeToken<List<Delivery>>() {
                        }.getType());
                        orderlist = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("Ordertype").toString(), new TypeToken<List<Order>>() {
                        }.getType());

                        deliverylist.clear();
                        for (int i = 0; i < tmpDeliveryList.size(); i++) {
                            if (tmpDeliveryList.get(i).getDeliverytypeName().equalsIgnoreCase("Today") || tmpDeliveryList.get(i).getDeliverytypeName().equalsIgnoreCase("Tomorrow")) {

                                deliverylist.add(tmpDeliveryList.get(i));
                            }
                        }

                        openAddOrderDialog(patient);

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

    @OnClick(R.id.txt_refill)
    public void onViewClicked() {

        String drufStr = "";
        for (int i = 0; i < drugAdapter.getDrugList().size(); i++) {
            if (drugAdapter.getDrugList().get(i).isChecked()) {
                if (!drufStr.isEmpty())
                    drufStr = drufStr + i + ", ";
                else
                    drufStr = i + ", ";
            }


        }

        if (drufStr.isEmpty()) {
            Utils.showAlertToast(DrugHistoryActivity.this, "Please select drug from above to refill");
            return;
        }


        getOrderndDelivery(patient);
    }
}
