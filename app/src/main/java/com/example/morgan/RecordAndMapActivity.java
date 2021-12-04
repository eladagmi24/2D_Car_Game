package com.example.morgan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecordAndMapActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;
    private String distance, coins;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_and_map);

        info = findViewById(R.id.info);

        listFragment = new ListFragment();
        listFragment.setCallBackList(callBack_List);
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, listFragment).commit();

        mapFragment = new MapFragment();
        mapFragment.setCallBackMap(callBack_map);
        getSupportFragmentManager().beginTransaction().add(R.id.frame2, mapFragment).commit();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        distance = bundle.getString("distance");
        coins = bundle.getString("coins");

        listFragment.setArguments(bundle);
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

        }
    };

    CallBack_Map callBack_map = new CallBack_Map() {
        @Override
        public void mapClicked(double lat, double lon) {
            listFragment.setTitle("City");
        }
    };
}