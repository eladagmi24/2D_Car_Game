package com.example.morgan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView[] cars = new ImageView[3];
    private ImageView[] explosion = new ImageView[3];
    private ImageView[] hearts = new ImageView[3];
    private int countHearts = 2;
    private int countCars = 1;

    private ImageButton left, right;

    private ImageView[][] rocks = new ImageView[6][3];


    private static  final int DELAY = 1000;
    private int clock = 0;
    private Timer timer;
    private int num, num1, num2, min = 0, max = 3;
    private boolean onStart = true, onStart1 = true, onStart2 = true;
    private int oneJump = 0;

    private int i = -1, j = -1, k = -1;


    public void hideSystemUI() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideSystemUI();
        setContentView(R.layout.activity_main);

        createGame();
        cars[countCars].setVisibility(View.VISIBLE);
        right = findViewById(R.id.main_BTN_right);
        right.setOnClickListener(v -> {

            if(countCars < 2)
            {
                cars[countCars].setVisibility(View.INVISIBLE);
                cars[++countCars].setVisibility(View.VISIBLE);
                if(rocks[5][0].getVisibility() == View.VISIBLE && cars[0].getVisibility() == View.VISIBLE)
                {
                    vibrate();
                    toast("Ouch!");
                    rocks[5][0].setVisibility(View.GONE);
                    explosion[0].setVisibility(View.VISIBLE);
                    cars[0].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }
                else if (rocks[5][1].getVisibility() == View.VISIBLE && cars[1].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("Ouch!");
                    rocks[5][1].setVisibility(View.GONE);
                    explosion[1].setVisibility(View.VISIBLE);
                    cars[1].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }
                else if(rocks[5][2].getVisibility() == View.VISIBLE && cars[2].getVisibility() == View.VISIBLE)
                {
                    vibrate();
                    toast("Ouch!");
                    rocks[5][2].setVisibility(View.GONE);
                    explosion[2].setVisibility(View.VISIBLE);
                    cars[2].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }


            }


        });
        left = findViewById(R.id.main_BTN_left);
        left.setOnClickListener(v -> {
            if(countCars > 0)
            {
                cars[countCars].setVisibility(View.INVISIBLE);
                cars[--countCars].setVisibility(View.VISIBLE);
                if(rocks[5][0].getVisibility() == View.VISIBLE && cars[0].getVisibility() == View.VISIBLE)
                {
                    vibrate();
                    toast("Ouch!");
                    rocks[5][0].setVisibility(View.GONE);
                    explosion[0].setVisibility(View.VISIBLE);
                    cars[0].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }
                else if (rocks[5][1].getVisibility() == View.VISIBLE && cars[1].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("Ouch!");
                    rocks[5][1].setVisibility(View.GONE);
                    explosion[1].setVisibility(View.VISIBLE);
                    cars[1].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }
                else if(rocks[5][2].getVisibility() == View.VISIBLE && cars[2].getVisibility() == View.VISIBLE)
                {
                    vibrate();
                    toast("Ouch!");
                    rocks[5][2].setVisibility(View.GONE);
                    explosion[2].setVisibility(View.VISIBLE);
                    cars[2].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }


            }

        });
    }

    private void createGame() {
        cars[0] = findViewById(R.id.main_IMG_carLeft);
        cars[1] = findViewById(R.id.main_IMG_carMiddle);
        cars[2] = findViewById(R.id.main_IMG_carRight);

        explosion[0] = findViewById(R.id.main_IMG_explosion1);
        explosion[1] = findViewById(R.id.main_IMG_explosion2);
        explosion[2] = findViewById(R.id.main_IMG_explosion3);


        rocks[0][0] = findViewById(R.id.main_IMG_rock1);
        rocks[1][0] = findViewById(R.id.main_IMG_rock2);
        rocks[2][0] = findViewById(R.id.main_IMG_rock3);
        rocks[3][0] = findViewById(R.id.main_IMG_rock4);
        rocks[4][0] = findViewById(R.id.main_IMG_rock5);
        rocks[5][0] = findViewById(R.id.main_IMG_rock6);

        rocks[0][1] = findViewById(R.id.main_IMG_rock7);
        rocks[1][1] = findViewById(R.id.main_IMG_rock8);
        rocks[2][1] = findViewById(R.id.main_IMG_rock9);
        rocks[3][1] = findViewById(R.id.main_IMG_rock10);
        rocks[4][1] = findViewById(R.id.main_IMG_rock11);
        rocks[5][1] = findViewById(R.id.main_IMG_rock12);

        rocks[0][2] = findViewById(R.id.main_IMG_rock13);
        rocks[1][2] = findViewById(R.id.main_IMG_rock14);
        rocks[2][2] = findViewById(R.id.main_IMG_rock15);
        rocks[3][2] = findViewById(R.id.main_IMG_rock16);
        rocks[4][2] = findViewById(R.id.main_IMG_rock17);
        rocks[5][2] = findViewById(R.id.main_IMG_rock18);

        hearts[0] = findViewById(R.id.main_IMG_heart1);
        hearts[1] = findViewById(R.id.main_IMG_heart2);
        hearts[2] = findViewById(R.id.main_IMG_heart3);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    private void startTicker() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Log.d("Timetick", "Tick: " + clock + "On Thread: " + Thread.currentThread().getName());
                runOnUiThread(() -> {
                    Log.d("Timetick", "Tick: " + clock + "On Thread: " + Thread.currentThread().getName());
                    updateRocks();
                });
            }
        }, 0, DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();

    }

    private void stopTicker() {
        timer.cancel();
    }


    private void updateRocks() {
        ++clock;
        if(countHearts < 0)
        {
            countHearts = 2;
        }
        if(onStart)
        {
            num = setRandomRock();
            onStart = false;
            oneJump = 0;
        }
        if(!onStart)
        {
            if (i < 5) {

                if(i == -1)
                {
                    rocks[++i][num].setVisibility(View.VISIBLE);
                }
                else
                {
                    if(i == 4 && num == countCars)
                    {
                        vibrate();
                        toast("Ouch!");
                        rocks[i][num].setVisibility(View.GONE);
                        explosion[num].setVisibility(View.VISIBLE);
                        cars[num].setVisibility(View.GONE);
                        i++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        rocks[i][num].setVisibility(View.INVISIBLE);
                        rocks[++i][num].setVisibility(View.VISIBLE);
                    }
                    oneJump++;
                }
            } else {

                rocks[i][num].setVisibility(View.GONE);
                explosion[num].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num = setRandomRock();
                i = 0;
                rocks[i][num].setVisibility(View.VISIBLE);


            }
        }


        if(onStart1 && oneJump == 2)
        {
            num1 = setRandomRock();
            onStart1 = false;
        }
        if(!onStart1)
        {
            if (j < 5) {

                if(j == -1)
                {
                    rocks[++j][num1].setVisibility(View.VISIBLE);

                }
                else {
                    if(j == 4 && num1 == countCars)
                    {
                        vibrate();
                        toast("Ouch!");
                        rocks[j][num1].setVisibility(View.GONE);
                        explosion[num1].setVisibility(View.VISIBLE);
                        cars[num1].setVisibility(View.GONE);
                        j++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);

                    }
                    else
                    {
                        rocks[j][num1].setVisibility(View.INVISIBLE);
                        rocks[++j][num1].setVisibility(View.VISIBLE);
                    }

                }
            } else {
                rocks[j][num1].setVisibility(View.GONE);
                explosion[num1].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num1 = setRandomRock();
                j = 0;
                rocks[j][num1].setVisibility(View.VISIBLE);

            }
        }

        if(onStart2 && oneJump == 4)
        {
            num2 = setRandomRock();
            onStart2 = false;
        }
        if(!onStart2)
        {
            if (k < 5) {

                if(k == -1)
                {
                    rocks[++k][num2].setVisibility(View.VISIBLE);

                }
                else {
                    if(k == 4 && num2 == countCars)
                    {
                        vibrate();
                        toast("Ouch!");
                        rocks[k][num2].setVisibility(View.GONE);
                        explosion[num2].setVisibility(View.VISIBLE);
                        cars[num2].setVisibility(View.GONE);
                        k++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);

                    }
                    else
                    {
                        rocks[k][num2].setVisibility(View.INVISIBLE);
                        rocks[++k][num2].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[k][num2].setVisibility(View.GONE);
                explosion[num2].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num2 = setRandomRock();
                k = 0;
                rocks[k][num2].setVisibility(View.VISIBLE);

            }
        }
    }

    private void toast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }



   /* private void updateRocksLocation() {
        ++clock;
        if(onStart)
        {
            num = setRandomRock();
            onStart = false;
            oneJump = 0;
        }
        if(num == 1)
        {
            if (countRockLeft < 5) {

                if(countRockLeft == -1)
                {
                    rocksLeft[++countRockLeft].setVisibility(View.VISIBLE);
                }
                else
                {

                    rocksLeft[countRockLeft].setVisibility(View.INVISIBLE);
                    rocksLeft[++countRockLeft].setVisibility(View.VISIBLE);
                    oneJump++;
                }
            } else {
                rocksLeft[countRockLeft].setVisibility(View.INVISIBLE);
                countRockLeft = -1;
                onStart = true;
            }
        } else if(num == 2)
        {
            if (countRockMiddle < 5) {
                if(countRockMiddle == -1)
                {
                    rocksMiddle[++countRockMiddle].setVisibility(View.VISIBLE);
                }
                else
                {
                    rocksMiddle[countRockMiddle].setVisibility(View.INVISIBLE);
                    rocksMiddle[++countRockMiddle].setVisibility(View.VISIBLE);
                    oneJump++;
                }
            } else {
                rocksMiddle[countRockMiddle].setVisibility(View.INVISIBLE);
                countRockMiddle = -1;
                onStart = true;

            }
        } else if(num == 3){
            if (countRockRight < 5) {
                if(countRockRight == -1)
                {
                    rocksRight[++countRockRight].setVisibility(View.VISIBLE);

                }
                else
                {
                    rocksRight[countRockRight].setVisibility(View.INVISIBLE);
                    rocksRight[++countRockRight].setVisibility(View.VISIBLE);
                    oneJump++;
                }

            } else {
                rocksRight[countRockRight].setVisibility(View.INVISIBLE);
                countRockRight = -1;
                onStart = true;

            }

        }
        if(onStart1 && oneJump == 2)
        {
            num1 = setRandomRock();
            onStart1 = false;
        }
        if(num1 == 1)
        {
            if (countRockLeft1 < 5) {

                if(countRockLeft1 == -1)
                {
                    rocksLeft[++countRockLeft1].setVisibility(View.VISIBLE);

                }
                else
                {
                    rocksLeft[countRockLeft1].setVisibility(View.INVISIBLE);
                    rocksLeft[++countRockLeft1].setVisibility(View.VISIBLE);
                }
            } else {
                rocksLeft[countRockLeft1].setVisibility(View.INVISIBLE);
                countRockLeft1 = -1;
                onStart1 = true;
            }
        } else if(num1 == 2)
        {
            if (countRockMiddle1 < 5) {

                if(countRockMiddle1 == -1)
                {
                    rocksMiddle[++countRockMiddle1].setVisibility(View.VISIBLE);

                }
                else
                {
                    rocksMiddle[countRockMiddle1].setVisibility(View.INVISIBLE);
                    rocksMiddle[++countRockMiddle1].setVisibility(View.VISIBLE);
                }

            } else {
                rocksMiddle[countRockMiddle1].setVisibility(View.INVISIBLE);
                countRockMiddle1 = -1;
                onStart1 = true;

            }
        } else if(num1 == 3) {
            if (countRockRight1 < 5) {
                if(countRockRight1 == -1)
                {
                    rocksRight[++countRockRight1].setVisibility(View.VISIBLE);

                }
                else
                {
                    rocksRight[countRockRight1].setVisibility(View.INVISIBLE);
                    rocksRight[++countRockRight1].setVisibility(View.VISIBLE);

                }
        } else {
                rocksRight[countRockRight1].setVisibility(View.INVISIBLE);
                countRockRight1 = -1;
                onStart1 = true;

            }

        }
      if(onStart2 && oneJump == 4)
        {
            num2 = setRandomRock();
            onStart2 = false;
        }
        if(num2 == 1)
        {
            if (countRockLeft2 < 5) {

                if(countRockLeft2 == -1)
                {
                    rocksLeft[++countRockLeft2].setVisibility(View.VISIBLE);

                }
                else
                {
                    rocksLeft[countRockLeft2].setVisibility(View.INVISIBLE);
                    rocksLeft[++countRockLeft2].setVisibility(View.VISIBLE);

                }

            } else {
                rocksLeft[countRockLeft2].setVisibility(View.INVISIBLE);
                countRockLeft2 = -1;
                onStart2 = true;
            }
        } else if(num2 == 2)
        {
            if (countRockMiddle2 < 5) {

                if(countRockMiddle2 == -1)
                {
                    rocksMiddle[++countRockMiddle2].setVisibility(View.VISIBLE);

                }
                else
                {
                    rocksMiddle[countRockMiddle2].setVisibility(View.INVISIBLE);
                    rocksMiddle[++countRockMiddle2].setVisibility(View.VISIBLE);

                }

            } else {
                rocksMiddle[countRockMiddle2].setVisibility(View.INVISIBLE);
                countRockMiddle2 = -1;
                onStart2 = true;

            }
        } else if(num2 == 3){
            if (countRockRight2 < 5) {
                if(countRockRight2 == -1)
                {
                    rocksRight[++countRockRight2].setVisibility(View.VISIBLE);

                }
                else
                {

                    rocksRight[countRockRight2].setVisibility(View.INVISIBLE);
                    rocksRight[++countRockRight2].setVisibility(View.VISIBLE);
                }

            } else {
                rocksRight[countRockRight2].setVisibility(View.INVISIBLE);
                countRockRight2 = -1;
                onStart1 = true;

            }

        }


    } */



    private int setRandomRock()
    {
        int num = (int) (Math.random()*(max-min)) + min;
        return num;
    }
}