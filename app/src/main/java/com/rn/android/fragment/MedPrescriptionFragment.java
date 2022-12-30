package com.rn.android.fragment;


import static com.rn.android.util.Utils.dismissProgressDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.reflect.TypeToken;
import com.metiz.pelconnect.R;
import com.rn.android.Adapter.MedPassPrescriptionAdapter;
import com.rn.android.MyApplication;
import com.rn.android.model.ModelMessPrescription;
import com.rn.android.network.API;
import com.rn.android.network.NetworkUtility;
import com.rn.android.network.VolleyCallBack;
import com.rn.android.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedPrescriptionFragment extends Fragment {

    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.rv_mess_prescription_list)
    RecyclerView rvMessPrescriptionList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    List<ModelMessPrescription> modelMessPrescriptionList = new ArrayList<>();
    MedPassPrescriptionAdapter adapter;
    int PatientId = 0;

    public MedPrescriptionFragment() {
        // Required empty public constructor

    }

    @SuppressLint("ValidFragment")
    public MedPrescriptionFragment(int patientId) {
        // Required empty public constructor
        this.PatientId = patientId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_med_prescription, container, false);
        unbinder = ButterKnife.bind(this, view);
        search.setQueryHint("Search Drug");
        search.setIconifiedByDefault(false);
        search.setIconified(false);
        search.setVisibility(View.GONE);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        initRecyclerView();
        getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPrescriptionList(MyApplication.getPrefranceDataInt("ExFacilityID"));
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (!TextUtils.isEmpty(newText.trim())) {
                        if (rvMessPrescriptionList.getAdapter() != null) {
                            newText.replace(",", "");
                            ((MedPassPrescriptionAdapter) rvMessPrescriptionList.getAdapter()).filter(newText);
                        }
                    } else {
                        ((MedPassPrescriptionAdapter) rvMessPrescriptionList.getAdapter()).filter("");

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return false;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initRecyclerView() {
        rvMessPrescriptionList.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void getPrescriptionList(int facilityID) {
        Log.e("Step", "==============4");
        if (!swipeRefresh.isRefreshing())
            Utils.showProgressDialog(getActivity());


        JSONObject param = new JSONObject();
        try {
            param.put("FacilityID", facilityID);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        NetworkUtility.makeJSONObjectRequest(API.GetMedPassDrugDetailsByPatientID + "?FacilityID=" + facilityID + "&PatientID=" + PatientId + "&currdate=" + new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()), new JSONObject(), API.GetMedPassDrugDetailsByPatientID, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject result) {

                try {

                    if (modelMessPrescriptionList.size() > 0) {
                        modelMessPrescriptionList.clear();

                    }

                    if (result != null) {
                        Type listType = new TypeToken<List<ModelMessPrescription>>() {
                        }.getType();
                        modelMessPrescriptionList = MyApplication.getGson().fromJson(result.getJSONArray("Data").toString(), listType);

                        adapter = new MedPassPrescriptionAdapter(getActivity(), getActivity(), modelMessPrescriptionList, true);
                        rvMessPrescriptionList.setAdapter(adapter);

                        search.setVisibility(View.VISIBLE);
                        search.clearFocus();
                        Utils.hide_keyboard(getActivity());

                        dismissProgressDialog();

                        swipeRefresh.setRefreshing(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dismissProgressDialog();


                }
            }

            @Override
            public void onError(JSONObject result) {
                dismissProgressDialog();

                swipeRefresh.setRefreshing(false);
            }
        });
    }

}
