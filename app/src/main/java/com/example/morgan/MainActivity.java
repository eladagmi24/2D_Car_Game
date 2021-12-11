package com.example.morgan;

import static com.example.morgan.StartGameActivity.MODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Data base
    private MyDB myDB;

    //Game items
    private ImageView[] cars = new ImageView[5];
    private ImageView[] explosion = new ImageView[5];
    private ImageView[] hearts = new ImageView[3];
    private ImageView[][] rocks = new ImageView[10][5];
    private ImageView[][] coins = new ImageView[10][5];

    //Indexes & Actions objects
    private int[] arrNumbers = new int[5];
    private int[] arrIndexes = new int[5];
    private boolean[] arrOnStart = new boolean[5];
    int min = 0, max = 5;
    private int countHearts = 2;
    private int countCars = 2;
    private int coinsIndex = -1, coinsIndex1 = -1, coinsCounter = 0;
    private int coinsNum, coinsNum1;
    private boolean startCoins = true, startCoins1 = true;
    private int oneJump = 0;

    //Time
    private static  int DELAY = 1000;
    private Timer timer;
    private int clock = 0;

    //Buttons, Texts and sensors
    private ImageButton left, right;
    private MediaPlayer player, coin;
    private TextView odometer, coinsText;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener accSensorEventListener;
    private String mode = "";
    public enum DirectionAction {
        LEFT,RIGHT
    }

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
        setContentView(R.layout.activity_main);
        initArrays();
        hideSystemUI();
        createGame();
        if(getIntent() != null){
            Intent intent = getIntent();
            mode = intent.getStringExtra(MODE);
            if(mode.equals("sensors")){
                initSensor();
                accSensorEventListener = new SensorEventListener() {
                    public void onSensorChanged(SensorEvent event) {
                        float x = event.values[0];
                        float z = event.values[2];
                        if (x <= -0.7) {
                            DirectionAction action = DirectionAction.RIGHT;
                            move(action);
                        } else if (x >= 0.7) {
                            DirectionAction action = DirectionAction.LEFT;
                            move(action);
                        }
                    }
                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    }
                };
                right = findViewById(R.id.main_BTN_right);
                right.setVisibility(View.GONE);
                left = findViewById(R.id.main_BTN_left);
                left.setVisibility(View.GONE);
            } else {
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
        }


    }

    private void initArrays() {
        for(int i = 0; i < arrIndexes.length; i++)
            arrIndexes[i] = -1;
        for(int i = 0; i < arrOnStart.length; i++)
            arrOnStart[i] = true;
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
        myDB.sortRecords();


        Intent intent = new Intent(this, RecordAndMapActivity.class);
        Bundle bundle = new Bundle();
        String json = new Gson().toJson(myDB);
        bundle.putString("myDB", json);
        intent.putExtra("myDB", bundle);
        MSPv3.getInstance(this).putStringSP("MY_DB", json);
        finish();
        startActivity(intent);
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
        if (arrOnStart[0]) {
            arrNumbers[0] = setRandom();
            arrOnStart[0] = false;
            oneJump = 0;
        }
        if (!arrOnStart[0]) {
            if (arrIndexes[0] < 9) {
                if (arrIndexes[0] == -1) {
                    rocks[++arrIndexes[0]][ arrNumbers[0]].setVisibility(View.VISIBLE);
                } else {
                    if (arrIndexes[0] == 8 &&  arrNumbers[0] == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[arrIndexes[0]][arrNumbers[0]].setVisibility(View.GONE);
                        explosion[arrNumbers[0]].setVisibility(View.VISIBLE);
                        cars[ arrNumbers[0]].setVisibility(View.GONE);
                        arrIndexes[0]++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);
                        player.start();
                    } else {
                        rocks[arrIndexes[0]][arrNumbers[0]].setVisibility(View.GONE);
                        rocks[++arrIndexes[0]][arrNumbers[0]].setVisibility(View.VISIBLE);
                    }
                    oneJump++;
                }
            } else {

                rocks[arrIndexes[0]][arrNumbers[0]].setVisibility(View.GONE);
                explosion[arrNumbers[0]].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                arrNumbers[0] = setRandom();
                arrIndexes[0] = 0;
                rocks[arrIndexes[0]][arrNumbers[0]].setVisibility(View.VISIBLE);


            }
        }

        if (arrOnStart[1] && oneJump == 2) {
            arrNumbers[1] = setRandom();
            arrOnStart[1] = false;
        }
        if (!arrOnStart[1]) {
            if (arrIndexes[1] < 9) {

                if (arrIndexes[1] == -1) {
                    rocks[++arrIndexes[1]][arrNumbers[1]].setVisibility(View.VISIBLE);

                } else {
                    if (arrIndexes[1] == 8 && arrNumbers[1] == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[arrIndexes[1]][arrNumbers[1]].setVisibility(View.GONE);
                        explosion[arrNumbers[1]].setVisibility(View.VISIBLE);
                        cars[arrNumbers[1]].setVisibility(View.GONE);
                        arrIndexes[1]++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);
                        player.start();


                    } else {
                        rocks[arrIndexes[1]][arrNumbers[1]].setVisibility(View.GONE);
                        rocks[++arrIndexes[1]][arrNumbers[1]].setVisibility(View.VISIBLE);
                    }

                }
            } else {
                rocks[arrIndexes[1]][arrNumbers[1]].setVisibility(View.GONE);
                explosion[arrNumbers[1]].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                arrNumbers[1] = setRandom();
                arrIndexes[1] = 0;
                rocks[arrIndexes[1]][arrNumbers[1]].setVisibility(View.VISIBLE);

            }
        }

        if (arrOnStart[2] && oneJump == 4) {
            arrNumbers[2] = setRandom();
            arrOnStart[2] = false;
        }
        if (!arrOnStart[2]) {
            if (arrIndexes[2] < 9) {
                if (arrIndexes[2] == -1) {
                    rocks[++arrIndexes[2]][arrNumbers[2]].setVisibility(View.VISIBLE);

                } else {
                    if (arrIndexes[2] == 8 && arrNumbers[2] == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[arrIndexes[2]][arrNumbers[2]].setVisibility(View.GONE);
                        explosion[arrNumbers[2]].setVisibility(View.VISIBLE);
                        cars[arrNumbers[2]].setVisibility(View.GONE);
                        arrIndexes[2]++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);
                        player.start();

                    } else {
                        rocks[arrIndexes[2]][arrNumbers[2]].setVisibility(View.GONE);
                        rocks[++arrIndexes[2]][arrNumbers[2]].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[arrIndexes[2]][arrNumbers[2]].setVisibility(View.GONE);
                explosion[arrNumbers[2]].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                arrNumbers[2] = setRandom();
                arrIndexes[2] = 0;
                rocks[arrIndexes[2]][arrNumbers[2]].setVisibility(View.VISIBLE);

            }
        }

        if (arrOnStart[3] && oneJump == 6) {
            arrNumbers[3] = setRandom();
            arrOnStart[3] = false;
        }
        if (!arrOnStart[3]) {
            if (arrIndexes[3] < 9) {
                if (arrIndexes[3] == -1) {
                    rocks[++arrIndexes[3]][arrNumbers[3]].setVisibility(View.VISIBLE);

                } else {
                    if (arrIndexes[3] == 8 && arrNumbers[3] == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[arrIndexes[3]][arrNumbers[3]].setVisibility(View.GONE);
                        explosion[arrNumbers[3]].setVisibility(View.VISIBLE);
                        cars[arrNumbers[3]].setVisibility(View.GONE);
                        arrIndexes[3]++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);
                        player.start();

                    } else {
                        rocks[arrIndexes[3]][arrNumbers[3]].setVisibility(View.GONE);
                        rocks[++arrIndexes[3]][arrNumbers[3]].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[arrIndexes[3]][arrNumbers[3]].setVisibility(View.GONE);
                explosion[arrNumbers[3]].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                arrNumbers[3] = setRandom();
                arrIndexes[3] = 0;
                rocks[arrIndexes[3]][arrNumbers[3]].setVisibility(View.VISIBLE);

            }

        }
        if (arrOnStart[4] && oneJump == 8) {
            arrNumbers[4] = setRandom();
            arrOnStart[4] = false;
        }
        if (!arrOnStart[4]) {
            if (arrIndexes[4] < 9) {
                if (arrIndexes[4] == -1) {
                    rocks[++arrIndexes[4]][arrNumbers[4]].setVisibility(View.VISIBLE);

                } else {
                    if (arrIndexes[4] == 8 && arrNumbers[4] == countCars) {
                        vibrate();
                        toast("Ouch!");
                        rocks[arrIndexes[4]][arrNumbers[4]].setVisibility(View.GONE);
                        explosion[arrNumbers[4]].setVisibility(View.VISIBLE);
                        cars[arrNumbers[4]].setVisibility(View.GONE);
                        arrIndexes[4]++;
                        hearts[countHearts--].setVisibility(View.INVISIBLE);
                        player.start();
                    } else {
                        rocks[arrIndexes[4]][arrNumbers[4]].setVisibility(View.GONE);
                        rocks[++arrIndexes[4]][arrNumbers[4]].setVisibility(View.VISIBLE);
                    }


                }

            } else {
                rocks[arrIndexes[4]][arrNumbers[4]].setVisibility(View.GONE);
                explosion[arrNumbers[4]].setVisibility(View.GONE);
                cars[countCars].setVisibility(View.VISIBLE);
                arrNumbers[4] = setRandom();
                arrIndexes[4] = 0;
                rocks[arrIndexes[4]][arrNumbers[4]].setVisibility(View.VISIBLE);

            }
        }

    }

    private void updateCoins() {
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




    //Sensors
    private int setRandom() {
        int num = (int) (Math.random() * (max - min)) + min;
        return num;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mode.equals("sensors"))
            sensorManager.registerListener(accSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mode.equals("sensors"))
            sensorManager.unregisterListener(accSensorEventListener);
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public boolean isSensorExists(int sensorType) {
        return (sensorManager.getDefaultSensor(sensorType) != null);
    }

    private void move(DirectionAction action) {
        if (action == DirectionAction.RIGHT) {
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
        } else if (countCars > 0) {
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
    }

}
