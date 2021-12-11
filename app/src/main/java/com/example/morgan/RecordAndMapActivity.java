package com.example.morgan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;

public class RecordAndMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapFragment mapFragment;
    private TextView info;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_and_map);

        info = findViewById(R.id.info);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("myDB");
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);
        listFragment.setCallBackList(callBack_List);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, listFragment).commit();

        mapFragment = new MapFragment();
        mapFragment.setCallBackMap(callBack_map);
        getSupportFragmentManager().beginTransaction().add(R.id.frame2, mapFragment).commit();


    }

    CallBack_List callBack_List = new CallBack_List() {
        @Override
        public void setMainTitle(String str) {
            info.setText(str);
        }

        @Override
        public void setMainTitleColor(int color) {
            info.setTextColor(color);
        }

        @Override
        public void rowSelected(int i) {
            String fromJSON = MSPv3.getInstance(getApplicationContext()).getStringSP("MY_DB","");
            MyDB myDB = new Gson().fromJson(fromJSON,MyDB.class);
            if(i < myDB.getRecords().size())
            {
                Record record = myDB.getRecords().get(i);
                callBack_map.locationSelected(record);
            }
        }
    };

    CallBack_Map callBack_map = new CallBack_Map() {
        @Override
        public void mapClicked(double lat, double lon) {
        }

        @Override
        public void locationSelected(Record record) {
            mapFragment.onClicked(record);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng mark = new LatLng(32.104236455127015, 34.87987851707526);
        map.addMarker(new MarkerOptions().position(mark).title("I am here"));
        map.moveCamera(CameraUpdateFactory.newLatLng(mark));
    }
}