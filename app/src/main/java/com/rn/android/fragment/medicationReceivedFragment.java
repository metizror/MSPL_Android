package com.rn.android.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metiz.pelconnect.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class medicationReceivedFragment extends Fragment {


    public medicationReceivedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medication_received, container, false);
    }

}
