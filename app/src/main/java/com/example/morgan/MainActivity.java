package com.example.morgan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.media.MediaPlayer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MyDB myDB;
    private ImageView[] cars = new ImageView[5];
    private ImageView[] explosion = new ImageView[5];
    private ImageView[] hearts = new ImageView[3];
    private int countHearts = 2;
    private int countCars = 2;
    private ImageButton left, right;
    private ImageView[][] rocks = new ImageView[10][5];
    private ImageView[][] coins = new ImageView[10][5];
    private static final int DELAY = 1000;
    private int clock = 0;
    private Timer timer;
    private int num, num1, num2, num3, num4, min = 0, max = 5;
    private int coinsNum, coinsNum1;
    private boolean onStart = true, onStart1 = true, onStart2 = true, onStart3 = true, onStart4 = true;
    private boolean startCoins = true, startCoins1 = true;
    private int oneJump = 0;
    private int i = -1, j = -1, k = -1, g = -1, h = -1;
    private int coinsIndex = -1, coinsIndex1 = -1;
    private MediaPlayer player, coin, backgrounds;
    private TextView odometer, coinsText;
    private int coinsCounter = 0;

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
                cars[countCars].setVisibility(View.GONE);
                cars[++countCars].setVisibility(View.VISIBLE);
                if (rocks[9][countCars].getVisibility() == View.VISIBLE && cars[countCars].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("Ouch!");
                    rocks[9][countCars].setVisibility(View.GONE);
                    explosion[countCars].setVisibility(View.VISIBLE);
                    cars[countCars].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                    player.start();
                }
                if (coins[9][countCars].getVisibility() == View.VISIBLE && cars[countCars].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("WOW!");
                    coins[9][countCars].setVisibility(View.GONE);
                    coin.start();
                    coinsCounter++;
                }
            }

        });
        left = findViewById(R.id.main_BTN_left);
        left.setOnClickListener(v -> {
            if (countCars > 0) {
                cars[countCars].setVisibility(View.GONE);
                cars[--countCars].setVisibility(View.VISIBLE);
                if (rocks[9][countCars].getVisibility() == View.VISIBLE && cars[countCars].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("Ouch!");
                    rocks[9][countCars].setVisibility(View.GONE);
                    explosion[countCars].setVisibility(View.VISIBLE);
                    cars[countCars].setVisibility(View.GONE);
                    hearts[countHearts--].setVisibility(View.INVISIBLE);
                    player.start();
                }
                if (coins[9][countCars].getVisibility() == View.VISIBLE && cars[countCars].getVisibility() == View.VISIBLE) {
                    vibrate();
                    toast("WOW!");
                    coins[9][countCars].setVisibility(View.GONE);
                    coin.start();
                    coinsCounter++;
                }
            }

        });

    }

    private void createGame() {
        String fromJSON = MSPv3.getInstance(this).getStringSP("MY_DB","");
        myDB = new Gson().fromJson(fromJSON,MyDB.class);
        if(myDB == null)
            myDB = new MyDB();
        createCars();
        createExplosion();
        createRocks();
        createHearts();
        createCoins();
        player = MediaPlayer.create(this, R.raw.explosion);
        coin = MediaPlayer.create(this, R.raw.coins);
        backgrounds = MediaPlayer.create(this, R.raw.background);
        backgrounds.start();
        odometer = findViewById(R.id.main_TXT_odometer);
        coinsText = findViewById(R.id.main_TXT_coinsCounter);

    }
    private void createCars() {
        cars[0] = findViewById(R.id.main_IMG_carLeft);
        cars[1] = findViewById(R.id.main_IMG_carMiddleLeft);
        cars[2] = findViewById(R.id.main_IMG_carMiddle);
        cars[3] = findViewById(R.id.main_IMG_carMiddleRight);
        cars[4] = findViewById(R.id.main_IMG_carRight);
    }
    private void createExplosion() {
        explosion[0] = findViewById(R.id.main_IMG_explosion1);
        explosion[1] = findViewById(R.id.main_IMG_explosion2);
        explosion[2] = findViewById(R.id.main_IMG_explosion3);
        explosion[3] = findViewById(R.id.main_IMG_explosion4);
        explosion[4] = findViewById(R.id.main_IMG_explosion5);
    }
    private void createRocks() {
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
    }
    private void createHearts() {
        hearts[0] = findViewById(R.id.main_IMG_heart1);
        hearts[1] = findViewById(R.id.main_IMG_heart2);
        hearts[2] = findViewById(R.id.main_IMG_heart3);
    }
    private void createCoins() {
        coins[0][0] = findViewById(R.id.main_IMG_coin1);
        coins[1][0] = findViewById(R.id.main_IMG_coin2);
        coins[2][0] = findViewById(R.id.main_IMG_coin3);
        coins[3][0] = findViewById(R.id.main_IMG_coin4);
        coins[4][0] = findViewById(R.id.main_IMG_coin5);
        coins[5][0] = findViewById(R.id.main_IMG_coin6);
        coins[6][0] = findViewById(R.id.main_IMG_coin7);
        coins[7][0] = findViewById(R.id.main_IMG_coin8);
        coins[8][0] = findViewById(R.id.main_IMG_coin9);
        coins[9][0] = findViewById(R.id.main_IMG_coin10);

        coins[0][1] = findViewById(R.id.main_IMG_coin11);
        coins[1][1] = findViewById(R.id.main_IMG_coin12);
        coins[2][1] = findViewById(R.id.main_IMG_coin13);
        coins[3][1] = findViewById(R.id.main_IMG_coin14);
        coins[4][1] = findViewById(R.id.main_IMG_coin15);
        coins[5][1] = findViewById(R.id.main_IMG_coin16);
        coins[6][1] = findViewById(R.id.main_IMG_coin17);
        coins[7][1] = findViewById(R.id.main_IMG_coin18);
        coins[8][1] = findViewById(R.id.main_IMG_coin19);
        coins[9][1] = findViewById(R.id.main_IMG_coin20);

        coins[0][2] = findViewById(R.id.main_IMG_coin21);
        coins[1][2] = findViewById(R.id.main_IMG_coin22);
        coins[2][2] = findViewById(R.id.main_IMG_coin23);
        coins[3][2] = findViewById(R.id.main_IMG_coin24);
        coins[4][2] = findViewById(R.id.main_IMG_coin25);
        coins[5][2] = findViewById(R.id.main_IMG_coin26);
        coins[6][2] = findViewById(R.id.main_IMG_coin27);
        coins[7][2] = findViewById(R.id.main_IMG_coin28);
        coins[8][2] = findViewById(R.id.main_IMG_coin29);
        coins[9][2] = findViewById(R.id.main_IMG_coin30);

        coins[0][3] = findViewById(R.id.main_IMG_coin31);
        coins[1][3] = findViewById(R.id.main_IMG_coin32);
        coins[2][3] = findViewById(R.id.main_IMG_coin33);
        coins[3][3] = findViewById(R.id.main_IMG_coin34);
        coins[4][3] = findViewById(R.id.main_IMG_coin35);
        coins[5][3] = findViewById(R.id.main_IMG_coin36);
        coins[6][3] = findViewById(R.id.main_IMG_coin37);
        coins[7][3] = findViewById(R.id.main_IMG_coin38);
        coins[8][3] = findViewById(R.id.main_IMG_coin39);
        coins[9][3] = findViewById(R.id.main_IMG_coin40);

        coins[0][4] = findViewById(R.id.main_IMG_coin41);
        coins[1][4] = findViewById(R.id.main_IMG_coin42);
        coins[2][4] = findViewById(R.id.main_IMG_coin43);
        coins[3][4] = findViewById(R.id.main_IMG_coin44);
        coins[4][4] = findViewById(R.id.main_IMG_coin45);
        coins[5][4] = findViewById(R.id.main_IMG_coin46);
        coins[6][4] = findViewById(R.id.main_IMG_coin47);
        coins[7][4] = findViewById(R.id.main_IMG_coin48);
        coins[8][4] = findViewById(R.id.main_IMG_coin49);
        coins[9][4] = findViewById(R.id.main_IMG_coin50);
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
                    if(countHearts < 0)
                        finishGame();
                    else
                        updateRocks();
                    if(clock >= 1)
                        updateCoins();
                    odometer.setText(String.valueOf(clock));
                    coinsText.setText(String.valueOf(coinsCounter));

                });
            }
        }, 0, DELAY);
    }

    private void finishGame() {
        timer.cancel();
        Record record = new Record();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        backgrounds.stop();
        Log.d("size", "" + myDB.getRecords().size());
        if (myDB.getRecords().size() == 0) {
            record.setDistance(clock).setCoins(coinsCounter).setLat(location.getLatitude()).setLon(location.getLongitude());
            myDB.getRecords().add(record);
        }
        else if (myDB.getRecords().size() <= 10) {
            record.setDistance(clock).setCoins(coinsCounter).setLat(location.getLatitude()).setLon(location.getLongitude());
            myDB.getRecords().add(record);
        } else if (myDB.getRecords().get(myDB.getRecords().size() - 1).getScore() < clock * coinsCounter) {
            record.setDistance(clock).setCoins(coinsCounter).setLat(location.getLatitude()).setLon(location.getLongitude());
            myDB.getRecords().set(myDB.getRecords().size() - 1, record);
        }
        sortMyDB(myDB);


        Intent intent = new Intent(this, RecordAndMapActivity.class);
        Bundle bundle = new Bundle();
        String json = new Gson().toJson(myDB);
        bundle.putString("myDB", json);
        intent.putExtra("myDB", bundle);
        MSPv3.getInstance(this).putStringSP("MY_DB", json);
        finish();
        startActivity(intent);
    }

    private void sortMyDB(MyDB myDB) {
        Record tempRecord;
        for(int i = myDB.getRecords().size() - 1; i >= 0; i--)
            if(i >= 1 && myDB.getRecords().get(i).getScore() > myDB.getRecords().get(i - 1).getScore()) {
                tempRecord = myDB.getRecords().get(i - 1);
                myDB.getRecords().set(i - 1, myDB.getRecords().get(i));
                myDB.getRecords().set(i, tempRecord);
            }
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
        if (onStart) {
            num = setRandom();
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
                        player.start();
                    } else {
                        rocks[i][num].setVisibility(View.GONE);
                        rocks[++i][num].setVisibility(View.VISIBLE);
                    }
                    oneJump++;
                }
            } else {

                rocks[i][num].setVisibility(View.GONE);
                explosion[num].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num = setRandom();
                i = 0;
                rocks[i][num].setVisibility(View.VISIBLE);


            }
        }

        if (onStart1 && oneJump == 2) {
            num1 = setRandom();
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
                        player.start();


                    } else {
                        rocks[j][num1].setVisibility(View.GONE);
                        rocks[++j][num1].setVisibility(View.VISIBLE);
                    }

                }
            } else {
                rocks[j][num1].setVisibility(View.GONE);
                explosion[num1].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num1 = setRandom();
                j = 0;
                rocks[j][num1].setVisibility(View.VISIBLE);

            }
        }

        if (onStart2 && oneJump == 4) {
            num2 = setRandom();
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
                        player.start();

                    } else {
                        rocks[k][num2].setVisibility(View.GONE);
                        rocks[++k][num2].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[k][num2].setVisibility(View.GONE);
                explosion[num2].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num2 = setRandom();
                k = 0;
                rocks[k][num2].setVisibility(View.VISIBLE);

            }
        }

        if (onStart3 && oneJump == 6) {
            num3 = setRandom();
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
                        player.start();

                    } else {
                        rocks[g][num3].setVisibility(View.GONE);
                        rocks[++g][num3].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[g][num3].setVisibility(View.GONE);
                explosion[num3].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num3 = setRandom();
                g = 0;
                rocks[g][num3].setVisibility(View.VISIBLE);

            }

        }
        if (onStart4 && oneJump == 8) {
            num4 = setRandom();
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
                        player.start();
                    } else {
                        rocks[h][num4].setVisibility(View.GONE);
                        rocks[++h][num4].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[h][num4].setVisibility(View.GONE);
                explosion[num4].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                num4 = setRandom();
                h = 0;
                rocks[h][num4].setVisibility(View.VISIBLE);

            }
        }

    }

    private void updateCoins()
    {
        if (startCoins && oneJump == 1) {
            coinsNum = setRandom();
            startCoins = false;
        }
        if (!startCoins) {
            if (coinsIndex < 9) {
                if (coinsIndex == -1) {
                    coins[++coinsIndex][coinsNum].setVisibility(View.VISIBLE);
                } else {

                    if (coinsIndex == 8 && coinsNum == countCars) {
                        coin.start();
                        toast("YAY :)");
                        coins[coinsIndex][coinsNum].setVisibility(View.GONE);
                        coinsIndex++;
                        coinsCounter++;
                    } else {
                        coins[coinsIndex][coinsNum].setVisibility(View.GONE);
                        coins[++coinsIndex][coinsNum].setVisibility(View.VISIBLE);
                    }
                }
            } else {

                coins[coinsIndex][coinsNum].setVisibility(View.GONE);
                coinsNum = setRandom();
                coinsIndex = 0;
                coins[coinsIndex][coinsNum].setVisibility(View.VISIBLE);

            }
        }
        if (startCoins1 && oneJump == 5) {
            coinsNum1 = setRandom();
            startCoins1 = false;
        }
        if (!startCoins1) {
            if (coinsIndex1 < 9) {
                if (coinsIndex1 == -1) {
                    coins[++coinsIndex1][coinsNum1].setVisibility(View.VISIBLE);
                } else {

                    if (coinsIndex1 == 8 && coinsNum1 == countCars) {
                        coin.start();
                        toast("YAY :)");
                        coins[coinsIndex1][coinsNum1].setVisibility(View.GONE);
                        coinsIndex1++;
                        coinsCounter++;
                    } else {
                        coins[coinsIndex1][coinsNum1].setVisibility(View.GONE);
                        coins[++coinsIndex1][coinsNum1].setVisibility(View.VISIBLE);
                    }
                }
            } else {

                coins[coinsIndex1][coinsNum1].setVisibility(View.GONE);
                coinsNum1 = setRandom();
                coinsIndex1 = 0;
                coins[coinsIndex1][coinsNum1].setVisibility(View.VISIBLE);

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


    private int setRandom() {
        int num = (int) (Math.random() * (max - min)) + min;
        return num;
    }


}
