package com.example.morgan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class StartGameActivity extends AppCompatActivity {
    private TextView text, recordsAndMap;
    private CheckBox sensors;
    public static final String MODE = "MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        sensors = (CheckBox)findViewById(R.id.main_BOX_switch2);
        text = findViewById(R.id.main_TXT_play);
        recordsAndMap = findViewById(R.id.main_TXT_recordsAndMap);


            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent(StartGameActivity.this, MainActivity.class);
                    if(sensors.isChecked()) {
                        mainIntent.putExtra(MODE, "sensors");
                        startActivity(mainIntent);
                    } else {
                        mainIntent.putExtra(MODE, "arrows");
                        startActivity(mainIntent);
                    }
                }
            });

        recordsAndMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartGameActivity.this, RecordAndMapActivity.class));
            }
        });

    }
}