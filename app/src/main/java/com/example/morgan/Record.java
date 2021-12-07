package com.example.morgan;

public class Record {
//    private String name ="";
    private int distance=0;
    private int coins=0;
    private double lat=0.0;
    private double lon=0.0;

    public Record() {
    }

//    public String getName() {
//        return name;
//    }
//
//    public Record setName(String name) {
//        this.name = name;
//        return this;
//    }

    public int getDistance() {
        return distance;
    }

    public Record setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public int getCoins() {
        return coins;
    }

    public Record setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    public long getScore(){
        return distance*coins;
    }

    public double getLat() {
        return lat;
    }

    public Record setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Record setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        return "Coins: " + coins + ", Distance: " + distance;
    }
}
