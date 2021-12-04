package com.example.morgan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class MapFragment extends Fragment {

    private MaterialButton frame2_BTN_map;
    private CallBack_Map callBack_map;

    public void setCallBackMap(CallBack_Map callBack_map) {
        this.callBack_map = callBack_map;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);
        initViews();


        return view;
    }

    private void initViews() {
        frame2_BTN_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack_map.mapClicked(2.0, 6.8);
            }
        });
    }

    private void findViews(View view) {
        frame2_BTN_map = view.findViewById(R.id.map_BTN_map);
    }
}