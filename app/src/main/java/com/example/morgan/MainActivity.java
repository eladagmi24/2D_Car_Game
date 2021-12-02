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

    private ImageView[] cars = new ImageView[5];
    private ImageView[] explosion = new ImageView[5];
    private ImageView[] hearts = new ImageView[3];
    private int countHearts = 2;
    private int countCars = 2;
    private ImageButton left, right;
    private ImageView[][] rocks = new ImageView[10][5];
    private static final int DELAY = 1000;
    private int clock = 0;
    private Timer timer;
    private int num, num1, num2, num3, num4, min = 0, max = 5;
    private boolean onStart = true, onStart1 = true, onStart2 = true, onStart3 = true, onStart4 = true;
    private int oneJump = 0;
    private int i = -1, j = -1, k = -1, g = -1, h = -1;

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

            if (countCars < 4) {
                cars[countCars].setVisibility(View.INVISIBLE);
                cars[++countCars].setVisibility(View.VISIBLE);
                if (rocks[9][countCars].getVisibility() == View.VISIBLE && cars[countCars].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("Ouch!");
                    rocks[9][countCars].setVisibility(View.GONE);
                    explosion[countCars].setVisibility(View.VISIBLE);
                    cars[countCars].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }
            }
//                } else if (rocks[9][1].getVisibility() == View.VISIBLE && cars[1].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][1].setVisibility(View.GONE);
//                    explosion[1].setVisibility(View.VISIBLE);
//                    cars[1].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                } else if (rocks[9][2].getVisibility() == View.VISIBLE && cars[2].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][2].setVisibility(View.GONE);
//                    explosion[2].setVisibility(View.VISIBLE);
//                    cars[2].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                } else if (rocks[9][3].getVisibility() == View.VISIBLE && cars[3].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][3].setVisibility(View.GONE);
//                    explosion[3].setVisibility(View.VISIBLE);
//                    cars[3].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                } else if (rocks[9][4].getVisibility() == View.VISIBLE && cars[4].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][4].setVisibility(View.GONE);
//                    explosion[4].setVisibility(View.VISIBLE);
//                    cars[4].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                }
//
//            }


        });
        left = findViewById(R.id.main_BTN_left);
        left.setOnClickListener(v -> {
            if (countCars > 0) {
                cars[countCars].setVisibility(View.INVISIBLE);
                cars[--countCars].setVisibility(View.VISIBLE);
                if (rocks[9][countCars].getVisibility() == View.VISIBLE && cars[countCars].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("Ouch!");
                    rocks[9][countCars].setVisibility(View.GONE);
                    explosion[countCars].setVisibility(View.VISIBLE);
                    cars[countCars].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                }
//                } else if (rocks[9][1].getVisibility() == View.VISIBLE && cars[1].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][1].setVisibility(View.GONE);
//                    explosion[1].setVisibility(View.VISIBLE);
//                    cars[1].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                } else if (rocks[9][2].getVisibility() == View.VISIBLE && cars[2].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][2].setVisibility(View.GONE);
//                    explosion[2].setVisibility(View.VISIBLE);
//                    cars[2].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                } else if (rocks[9][3].getVisibility() == View.VISIBLE && cars[3].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][3].setVisibility(View.GONE);
//                    explosion[3].setVisibility(View.VISIBLE);
//                    cars[3].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                } else if (rocks[9][4].getVisibility() == View.VISIBLE && cars[4].getVisibility() == View.VISIBLE) {
//                    vibrate();
//                    toast("Ouch!");
//                    rocks[9][4].setVisibility(View.GONE);
//                    explosion[4].setVisibility(View.VISIBLE);
//                    cars[4].setVisibility(View.GONE);
//                    hearts[countHearts--].setVisibility(View.INVISIBLE);
//                }


            }

        });
    }

    private void createGame() {
        cars[0] = findViewById(R.id.main_IMG_carLeft);
        cars[1] = findViewById(R.id.main_IMG_carMiddleLeft);
        cars[2] = findViewById(R.id.main_IMG_carMiddle);
        cars[3] = findViewById(R.id.main_IMG_carMiddleRight);
        cars[4] = findViewById(R.id.main_IMG_carRight);

        explosion[0] = findViewById(R.id.main_IMG_explosion1);
        explosion[1] = findViewById(R.id.main_IMG_explosion2);
        explosion[2] = findViewById(R.id.main_IMG_explosion3);
        explosion[3] = findViewById(R.id.main_IMG_explosion4);
        explosion[4] = findViewById(R.id.main_IMG_explosion5);


        rocks[0][0] = findViewById(R.id.main_IMG_rock1);
        rocks[1][0] = findViewById(R.id.main_IMG_rock2);
        rocks[2][0] = findViewById(R.id.main_IMG_rock3);
        rocks[3][0] = findViewById(R.id.main_IMG_rock4);
        rocks[4][0] = findViewById(R.id.main_IMG_rock5);
        rocks[5][0] = findViewById(R.id.main_IMG_rock6);
        rocks[6][0] = findViewById(R.id.main_IMG_rock7);
        rocks[7][0] = findViewById(R.id.main_IMG_rock8);
        rocks[8][0] = findViewById(R.id.main_IMG_rock9);
        rocks[9][0] = findViewById(R.id.main_IMG_rock10);

        rocks[0][1] = findViewById(R.id.main_IMG_rock11);
        rocks[1][1] = findViewById(R.id.main_IMG_rock12);
        rocks[2][1] = findViewById(R.id.main_IMG_rock13);
        rocks[3][1] = findViewById(R.id.main_IMG_rock14);
        rocks[4][1] = findViewById(R.id.main_IMG_rock15);
        rocks[5][1] = findViewById(R.id.main_IMG_rock16);
        rocks[6][1] = findViewById(R.id.main_IMG_rock17);
        rocks[7][1] = findViewById(R.id.main_IMG_rock18);
        rocks[8][1] = findViewById(R.id.main_IMG_rock19);
        rocks[9][1] = findViewById(R.id.main_IMG_rock20);

        rocks[0][2] = findViewById(R.id.main_IMG_rock21);
        rocks[1][2] = findViewById(R.id.main_IMG_rock22);
        rocks[2][2] = findViewById(R.id.main_IMG_rock23);
        rocks[3][2] = findViewById(R.id.main_IMG_rock24);
        rocks[4][2] = findViewById(R.id.main_IMG_rock25);
        rocks[5][2] = findViewById(R.id.main_IMG_rock26);
        rocks[6][2] = findViewById(R.id.main_IMG_rock27);
        rocks[7][2] = findViewById(R.id.main_IMG_rock28);
        rocks[8][2] = findViewById(R.id.main_IMG_rock29);
        rocks[9][2] = findViewById(R.id.main_IMG_rock30);

        rocks[0][3] = findViewById(R.id.main_IMG_rock31);
        rocks[1][3] = findViewById(R.id.main_IMG_rock32);
        rocks[2][3] = findViewById(R.id.main_IMG_rock33);
        rocks[3][3] = findViewById(R.id.main_IMG_rock34);
        rocks[4][3] = findViewById(R.id.main_IMG_rock35);
        rocks[5][3] = findViewById(R.id.main_IMG_rock36);
        rocks[6][3] = findViewById(R.id.main_IMG_rock37);
        rocks[7][3] = findViewById(R.id.main_IMG_rock38);
        rocks[8][3] = findViewById(R.id.main_IMG_rock39);
        rocks[9][3] = findViewById(R.id.main_IMG_rock40);

        rocks[0][4] = findViewById(R.id.main_IMG_rock41);
        rocks[1][4] = findViewById(R.id.main_IMG_rock42);
        rocks[2][4] = findViewById(R.id.main_IMG_rock43);
        rocks[3][4] = findViewById(R.id.main_IMG_rock44);
        rocks[4][4] = findViewById(R.id.main_IMG_rock45);
        rocks[5][4] = findViewById(R.id.main_IMG_rock46);
        rocks[6][4] = findViewById(R.id.main_IMG_rock47);
        rocks[7][4] = findViewById(R.id.main_IMG_rock48);
        rocks[8][4] = findViewById(R.id.main_IMG_rock49);
        rocks[9][4] = findViewById(R.id.main_IMG_rock50);

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


    private void updateRocks() { //handle later
        ++clock;
        if (countHearts < 0) {
            countHearts = 2;
        }
        if (onStart) {
            num = setRandomRock();
            onStart = false;
            oneJump = 0;
        }
        if (!onStart) {
            if (i < 9) {
                if (i == -1) {
                    rocks[++i][num].setVisibility(View.VISIBLE);
                } else {
                    if (i == 8 && num == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[i][num].setVisibility(View.GONE);
                        explosion[num].setVisibility(View.VISIBLE);
                        cars[num].setVisibility(View.GONE);
                        i++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);
                    } else {
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


        if (onStart1 && oneJump == 2) {
            num1 = setRandomRock();
            onStart1 = false;
        }
        if (!onStart1) {
            if (j < 9) {

                if (j == -1) {
                    rocks[++j][num1].setVisibility(View.VISIBLE);

                } else {
                    if (j == 8 && num1 == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[j][num1].setVisibility(View.GONE);
                        explosion[num1].setVisibility(View.VISIBLE);
                        cars[num1].setVisibility(View.GONE);
                        j++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);

                    } else {
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

        if (onStart2 && oneJump == 4) {
            num2 = setRandomRock();
            onStart2 = false;
        }
        if (!onStart2) {
            if (k < 9) {
                if (k == -1) {
                    rocks[++k][num2].setVisibility(View.VISIBLE);

                } else {
                    if (k == 8 && num2 == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[k][num2].setVisibility(View.GONE);
                        explosion[num2].setVisibility(View.VISIBLE);
                        cars[num2].setVisibility(View.GONE);
                        k++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);

                    } else {
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

        if (onStart3 && oneJump == 6) {
            num3 = setRandomRock();
            onStart3 = false;
        }
        if (!onStart3) {
            if (g < 9) {
                if (g == -1) {
                    rocks[++g][num3].setVisibility(View.VISIBLE);

                } else {
                    if (g == 8 && num3 == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[g][num3].setVisibility(View.GONE);
                        explosion[num3].setVisibility(View.VISIBLE);
                        cars[num3].setVisibility(View.GONE);
                        g++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);

                    } else {
                        rocks[g][num3].setVisibility(View.INVISIBLE);
                        rocks[++g][num3].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[g][num3].setVisibility(View.GONE);
                explosion[num3].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num3 = setRandomRock();
                g = 0;
                rocks[g][num3].setVisibility(View.VISIBLE);

            }

        }
        if (onStart4 && oneJump == 8) {
            num4 = setRandomRock();
            onStart4 = false;
        }
        if (!onStart4) {
            if (h < 9) {
                if (h == -1) {
                    rocks[++h][num4].setVisibility(View.VISIBLE);

                } else {
                    if (h == 8 && num4 == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[h][num4].setVisibility(View.GONE);
                        explosion[num4].setVisibility(View.VISIBLE);
                        cars[num4].setVisibility(View.GONE);
                        h++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);

                    } else {
                        rocks[h][num4].setVisibility(View.INVISIBLE);
                        rocks[++h][num4].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[h][num4].setVisibility(View.GONE);
                explosion[num4].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num4 = setRandomRock();
                h = 0;
                rocks[h][num4].setVisibility(View.VISIBLE);

            }
        }

    }

    private void toast(String text) {
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


    private int setRandomRock() {
        int num = (int) (Math.random() * (max - min)) + min;
        return num;
    }
}

