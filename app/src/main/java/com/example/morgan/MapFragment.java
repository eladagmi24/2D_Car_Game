package com.example.morgan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MaterialButton frame2_BTN_map;
    private CallBack_Map callBack_map;
    private GoogleMap map;
    private MyDB myDB;

    public void setCallBackMap(CallBack_Map callBack_map) {
        this.callBack_map = callBack_map;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findViews(view);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.google_map, mapFragment).commit();
        mapFragment.getMapAsync(this);



        return view;
    }
    private void findViews(View view) {
//        frame2_BTN_map = view.findViewById(R.id.map_BTN_map);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng mark = new LatLng(32.104236455127015, 34.87987851707526);
        map.addMarker(new MarkerOptions().position(mark).title("I am here"));
        map.moveCamera(CameraUpdateFactory.newLatLng(mark));
    }
//    private void moveCamera(Record record) {
//        LatLng mark = new LatLng(record.getLat(), record.getLon());
//        map.addMarker(new MarkerOptions().position(mark).title("I am here"));
//        map.moveCamera(CameraUpdateFactory.
//                newLatLngZoom(mark,1));
//    }
//    public void onClicked(Record record) {
//        moveCamera(record);
//    }

}
