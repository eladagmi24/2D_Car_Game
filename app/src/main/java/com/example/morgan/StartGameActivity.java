package com.example.morgan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StartGameActivity extends AppCompatActivity {
    private TextView text, recordAndMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        text = findViewById(R.id.main_TXT_play);
        recordAndMap = findViewById(R.id.main_TXT_recordsAndMap);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartGameActivity.this, MainActivity.class));
            }
        });
        recordAndMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartGameActivity.this, RecordAndMapActivity.class));
            }
        });
    }
}