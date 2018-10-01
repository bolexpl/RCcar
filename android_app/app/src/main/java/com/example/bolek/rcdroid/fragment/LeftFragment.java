package com.example.bolek.rcdroid.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bolek.rcdroid.controls.ClickControl;
import com.example.bolek.rcdroid.R;

public class LeftFragment extends Fragment {

    private ClickControl controls;

    public LeftFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button w = getActivity().findViewById(R.id.forward);
        Button s = getActivity().findViewById(R.id.backward);
        Button a = getActivity().findViewById(R.id.left);
        Button d = getActivity().findViewById(R.id.right);

        controls = new ClickControl(getActivity().getApplicationContext());

        w.setTag("forward");
        s.setTag("backward");
        a.setTag("left");
        d.setTag("right");

        w.setOnTouchListener(controls);
        s.setOnTouchListener(controls);
        a.setOnTouchListener(controls);
        d.setOnTouchListener(controls);
    }


    @Override
    public void onResume() {
        super.onResume();
        controls.updateUDP();
    }
}
