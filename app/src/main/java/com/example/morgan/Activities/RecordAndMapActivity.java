package com.example.morgan.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.morgan.CallBacks.CallBack_List;
import com.example.morgan.CallBacks.CallBack_Map;
import com.example.morgan.Fragments.ListFragment;
import com.example.morgan.DataBase.MSPv3;
import com.example.morgan.Fragments.MapFragment;
import com.example.morgan.DataBase.MyDB;
import com.example.morgan.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class RecordAndMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapFragment mapFragment;
    private TextView homeScreen;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_and_map);

        homeScreen = findViewById(R.id.list_TXT_homeScreen);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("myDB");
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);
        listFragment.setCallBackList(callBack_List);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, listFragment).commit();

        mapFragment = new MapFragment();
        mapFragment.setCallBackMap(callBack_map);
        getSupportFragmentManager().beginTransaction().add(R.id.frame2, mapFragment).commit();
        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordAndMapActivity.this, StartGameActivity.class));
            }
        });


    }

    CallBack_List callBack_List = new CallBack_List() {
        @Override
        public void setMainTitle(String str) {
            homeScreen.setText(str);
        }

        @Override
        public void setMainTitleColor(int color) {
            homeScreen.setTextColor(color);
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