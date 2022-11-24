package com.lifestyleapp;

public class WeatherData
{
    Coord coord;
    Weather weather[];
    Main main;
    Wind wind;
    Clouds clouds;
}

class Coord
{
    double lon;
    double lat;
}

class Weather
{
    int id;
    String main;
    String description;
    String icon;
}

class Main
{
    double temp;
    double feels_like;
    double temp_min;
    double temp_max;
    double pressure;
    double humidity;
}

class Wind
{
    double speed;
    double deg;
}

class Clouds
{
    double all;
}