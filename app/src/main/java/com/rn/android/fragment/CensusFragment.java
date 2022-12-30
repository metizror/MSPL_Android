package com.rn.android.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.CensusAdapter;
import com.rn.android.MyApplication;


import com.rn.android.model.ChangeActiveStatus;
import com.rn.android.model.ModelCensus;
import com.rn.android.model.NewModelCensus;
import com.rn.android.model.PatientStatus;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CensusFragment extends BaseFragment {
    List<ModelCensus> modelCensusList = new ArrayList<>();
    ArrayList<NewModelCensus> tempnewModelCensusArrayList = new ArrayList<>();
    CensusAdapter censusAdapter;
    @BindView(R.id.rv_census)
    RecyclerView rvCensus;
    String cycleStartDate = "";
    List<PatientStatus> patientStatusList = new ArrayList<>();
    int HeaderId = 0;
    int pendingDays = 0;
    ArrayList<ChangeActiveStatus> changeActiveStatusesList = new ArrayList<>();
    ArrayList<ModelCensus> item = new ArrayList<>();

    @BindView(R.id.cb_client_all_changes)
    CheckBox cb_client_all_changes;

    @BindView(R.id.btn_all_active)
    TextView btn_all_active;

    @BindView(R.id.btn_save)
    TextView btn_save;
    String data;
    private int pos = 0;

    public CensusFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CensusFragment(String cycleStartDate, int HeaderId,int pendingDays) {
        this.cycleStartDate = cycleStartDate;
        this.HeaderId = HeaderId;
        this.pendingDays = pendingDays;
    }
    private ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_census, container, false);
        ButterKnife.bind(this, view);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        if (pendingDays < 10) {
            btn_all_active.setVisibility(View.VISIBLE);
            btn_all_active.setEnabled(false);
            btn_all_active.setBackground(getResources().getDrawable(R.drawable.button_bg_solid));

            btn_save.setVisibility(View.VISIBLE);
            btn_save.setEnabled(false);
            btn_save.setBackground(getResources().getDrawable(R.drawable.button_bg_solid));

            cb_client_all_changes.setVisibility(View.VISIBLE);
            cb_client_all_changes.setEnabled(false);

        } else {
            btn_all_active.setVisibility(View.VISIBLE);
            btn_all_active.setEnabled(true);
            btn_all_active.setBackground(getResources().getDrawable(R.drawable.shape_rounbded_squre_top_cycle));

            btn_save.setVisibility(View.VISIBLE);
            btn_save.setEnabled(true);
            btn_save.setBackground(getResources().getDrawable(R.drawable.shape_rounbded_squre_top_cycle));

            cb_client_all_changes.setVisibility(View.VISIBLE);
            cb_client_all_changes.setEnabled(true);

        }
        cb_client_all_changes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cycleMedChagesAllPatient(b);
                    cb_client_all_changes.setChecked(false);
                }
            }
        });

        btn_all_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <censusAdapter.getChangeActiveStatusList().size() ; i++) {
                    censusAdapter.getChangeActiveStatusList().get(i).setFacilityPatientStatus(2);
                    censusAdapter.getChangeActiveStatusList().get(i).setHeaderID(HeaderId);
                    censusAdapter.getChangeActiveStatusList().get(i).setUserId(Integer.parseInt(MyApplication.getPrefranceData("UserID")));
                }
                ActiveStatusApiForAll(censusAdapter.getChangeActiveStatusList());
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  submitStatus();
                ActiveStatusApi();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        getCensusuList();
    }


    private void ActiveStatusApi() {

            int  status = 0;

        for (int i = 0; i <censusAdapter.getChangeActiveStatusList().size() ; i++) {

            if (censusAdapter.getChangeActiveStatusList().get(i).getFacilityPatientStatus() ==0){
                status = 0;

                Utils.showAlertToast(getActivity(),"Please change all status in a list");
                return;
            }else {
                status=1;
            }

        }
        if (status==1) {
            showProgressDialog(getActivity());
            JSONObject param = new JSONObject();

            Gson gson = new GsonBuilder().create();
            for (int i = 0; i <censusAdapter.getChangeActiveStatusList().size() ; i++) {
                censusAdapter.getChangeActiveStatusList().get(i).setHeaderID(HeaderId);
                censusAdapter.getChangeActiveStatusList().get(i).setUserId(Integer.parseInt(MyApplication.getPrefranceData("UserID")));

            }
            JsonArray myCustomArray = gson.toJsonTree(censusAdapter.getChangeActiveStatusList()).getAsJsonArray();
            Log.e("JsonArry", myCustomArray.toString());

            try {

                param.put("objcensus", myCustomArray);
              //  param.put("HeaderID", HeaderId);

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            NetworkUtility.makeJSONObjectRequest(API.UpdateCyclewisePatientStatus, param, API.UpdateCyclewisePatientStatus, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject result) {

                    try {
                        Log.e("result", result.toString());
                        dismissProgressDialog();
                    } catch (Exception e) {
                        dismissProgressDialog();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(JSONObject result) {
                    dismissProgressDialog();
                }
            });
        }

    }
    private void ActiveStatusApiForAll(List<ChangeActiveStatus> list) {
        showProgressDialog(getActivity());
        JSONObject param = new JSONObject();

        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(list).getAsJsonArray();
        Log.e("JsonArry", myCustomArray.toString());

        try {

            param.put("objcensus", myCustomArray);
          //  param.put("HeaderID",HeaderId);
            System.out.println(param);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        NetworkUtility.makeJSONObjectRequest(API.UpdateCyclewisePatientStatus, param, API.UpdateCyclewisePatientStatus, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    Log.e("result", result.toString());
                    censusAdapter.getChangeActiveStatusList().clear();
                    getCensusuList();
                    dismissProgressDialog();
                } catch (Exception e) {
                    dismissProgressDialog();
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();
            }
        });
    }

    public void cycleMedChagesAllPatient(boolean b) {
        showProgressDialog(getActivity());
        JSONObject param = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            for (int j = 0; j < tempnewModelCensusArrayList.size(); j++) {
                boolean isEnter = false;
                item = tempnewModelCensusArrayList.get(j).getModelCensusArrayList();
                JSONObject obj = new JSONObject();
                for (int k = 0; k < item.size(); k++) {
                    try {
                        /*if(item.get(15).getValueText().equals("NO") || item.get(15).getValueText().equals("")){*/
                            //isEnter = true;
                            if(b){
                                obj.put("ChangeStatus","N");
                            }else{
                                obj.put("ChangeStatus","");
                            }
                            obj.put("ChangedBy", MyApplication.getPrefranceData("UserID"));
                            String date = Utils.getCurrentDateTime();
                            obj.put("ChangesOn",date);
                            if (item.get(0).getKeyText().equalsIgnoreCase("Id")) {
                                obj.put("CyclePatientID", item.get(0).getValueText());
                            }
                            if (item.get(2).getKeyText().equalsIgnoreCase("Facility ID")) {
                                obj.put("Facility_Id", item.get(2).getValueText());
                            }
                            obj.put("HeaderID", HeaderId);
                            if (item.get(1).getKeyText().equalsIgnoreCase("External Patient id")) {
                                obj.put("external_patietn_id", item.get(1).getValueText());
                            }
                        /*}else {
                            isEnter = false;
                        }*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                array.put(obj);
                /*if (isEnter){
                    array.put(obj);
                }else{
                    Log.d("", "yes value not insert");
                }*/
            }
            param.put("lstCycleChanges", array.toString());
            System.out.println(param);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkUtility.makeJSONObjectRequest(API.CycleMedChangesForAllPatient , param, API.CycleMedChangesForAllPatient, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    getCensusuList();
                    dismissProgressDialog();
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



    public void getCensusuList() {
       // showProgressDialog(getActivity());
        pDialog.show();
//        Application.getPrefranceDataInt("ExFacilityID")    FacilityID=883&CycleStartDate=08-22-20
        int fId = MyApplication.getPrefranceDataInt("ExFacilityID");
        NetworkUtility.makeJSONObjectRequest(API.GetCycleListofDetails + "?FacilityID=" + MyApplication.getPrefranceDataInt("ExFacilityID") + "&CycleStartDate=" + cycleStartDate, new JSONObject(), API.GetCycleListofDetails, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                //dismissProgressDialog();
                pDialog.dismiss();
                boolean isEnter = false;
                try {
                    if (changeActiveStatusesList.size() > 0) {
                        changeActiveStatusesList.clear();
                    }
                    if (result != null) {


                        Type listType = new TypeToken<List<PatientStatus>>() {
                        }.getType();
                        patientStatusList = MyApplication.getGson().fromJson(result.getJSONObject("Data").getJSONArray("lstpatientstatus").toString(), listType);
                    }
                    result.getJSONObject("Data").getJSONArray("lstcensus").toString();
                    Log.e("MyResponce", result.getJSONObject("Data").getJSONArray("lstcensus").toString());
                    ArrayList<NewModelCensus> newModelCensusArrayList = new ArrayList<>();
                    tempnewModelCensusArrayList = new ArrayList<>();
                    for (int i = 0; i < result.getJSONObject("Data").getJSONArray("lstcensus").length(); i++) {
                        JSONObject jsonObject = result.getJSONObject("Data").getJSONArray("lstcensus").getJSONObject(i);
                        ArrayList<ModelCensus> myList = MyApplication.getGson().fromJson(jsonObject.getJSONArray("objlist").toString(), new TypeToken<ArrayList<ModelCensus>>() {
                        }.getType());
                        NewModelCensus newModelCensus = new NewModelCensus();
                        newModelCensus.setModelCensusArrayList(myList);
                        newModelCensusArrayList.add(newModelCensus);
                        tempnewModelCensusArrayList.add(newModelCensus);
                        ChangeActiveStatus changeActiveStatus = new ChangeActiveStatus();
                        for (int j = 0; j < newModelCensusArrayList.size(); j++) {
                            item = newModelCensusArrayList.get(j).getModelCensusArrayList();
                           /* if (item.get(15).getKeyText().equals("CycleChangeStatusYes")){
                                if(item.get(15).getValueText().equals("")){
                                    isEnter = false;
                                }else{
                                    isEnter = true;
                                }
                            }*/
                        }
                        for (int ii = 0; ii < item.size(); ii++) {
                            if (item.get(ii).getKeyText().equals("PatientFirst")) {
                                changeActiveStatus.setPatientFirst(item.get(ii).getValueText());
                            }
                            if (item.get(ii).getKeyText().equals("Facility Id")) {
                                changeActiveStatus.setFacilityID(Integer.parseInt(item.get(ii).getValueText()));
                            }
                            if (item.get(ii).getKeyText().equals("PatientLast")) {

                                changeActiveStatus.setPatientLast(item.get(ii).getValueText());
                            }
                            if (item.get(ii).getKeyText().equals("Id")) {
                                changeActiveStatus.setUserId(Integer.parseInt(item.get(ii).getValueText()));
                            }
                            if (item.get(ii).getKeyText().equals("Status")) {
                                changeActiveStatus.setFacilityPatientStatus(Integer.parseInt(item.get(ii).getValueText()));
                            }

                        }
                        changeActiveStatusesList.add(changeActiveStatus);
                        Log.e("List size", String.valueOf(changeActiveStatusesList.size()));
                    }
                   /* for (int j = 0; j < newModelCensusArrayList.size(); j++) {
                        item = newModelCensusArrayList.get(j).getModelCensusArrayList();
                        if (item.get(15).getKeyText().equals("CycleChangeStatusYes")){
                            if(item.get(15).getValueText().equals("")){
                                isEnter = false;
                            }else{
                                isEnter = true;
                            }
                        }
                    }
                    if(isEnter){
                        cb_client_all_changes.setChecked(true);
                    }else{
                        cb_client_all_changes.setChecked(false);
                    }*/
                    //dismissProgressDialog();
                    censusAdapter = new CensusAdapter(getActivity(), newModelCensusArrayList, changeActiveStatusesList, patientStatusList, HeaderId, cycleStartDate,  CensusFragment.this);
                    censusAdapter.notifyDataSetChanged();
                    rvCensus.setAdapter(censusAdapter);
                    pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //dismissProgressDialog();
                    pDialog.dismiss();
                }
            }

            @Override
            public void onError(JSONObject result) {
                //dismissProgressDialog();
                pDialog.dismiss();
            }
        });
    }

    private void initRecyclerView() {
        rvCensus.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
