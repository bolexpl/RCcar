package com.example.bolek.rcdroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bolek.rcdroid.controls.MoveControl;
import com.example.bolek.rcdroid.R;

public class CrossFragment extends Fragment {

    private MoveControl controls;

    public CrossFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cross, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt = getActivity().findViewById(R.id.button);

        controls = new MoveControl(getActivity().getApplicationContext());

        bt.setTag("cross");

        bt.setOnTouchListener(controls);
    }

    @Override
    public void onResume() {
        super.onResume();
        controls.updateUDP();
    }
}
