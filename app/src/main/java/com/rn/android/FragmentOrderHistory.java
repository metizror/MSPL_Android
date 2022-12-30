package com.rn.android;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metiz.pelconnect.R;

public class FragmentOrderHistory extends Fragment {

    public static FragmentOrderHistory newInstance() {
        FragmentOrderHistory fragment = new FragmentOrderHistory();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_order_history, container, false);
        return view;
    }

}
