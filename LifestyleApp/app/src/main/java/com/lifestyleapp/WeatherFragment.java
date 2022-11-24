package com.lifestyleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class WeatherFragment extends Fragment implements View.OnClickListener
{
    private EditText editLocation;
    private Button buttonLocation, buttonLifestyle;
    private String localLocation;
    private TextView weatherInfo;

    private static final String ARG_CITY = "";
    private static final String ARG_COUNTRY = "";

    private WeatherViewModel mWeatherViewModel;
    private WeatherUserViewModel weatherUserViewModel;

    //private User user;

    ProfilePageFragment.OnLifePressListener lifePressListener;

    public interface OnLifePressListener {
        public void onLifeBtnPress();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lifePressListener = (ProfilePageFragment.OnLifePressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLifePressListener");
        }
    }

    public WeatherFragment()
    {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(String city, String country)
    {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        args.putString(ARG_COUNTRY, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        editLocation = view.findViewById(R.id.location);
        buttonLocation = (Button)view.findViewById(R.id.resetLocation);
        buttonLifestyle = (Button)view.findViewById(R.id.backToLifestyle);
        weatherInfo = view.findViewById(R.id.weatherInfo);

        buttonLocation.setOnClickListener(this);
        buttonLifestyle.setOnClickListener(this);

        //Create the view model
        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        //Set the observer
        mWeatherViewModel.getData().observe(getViewLifecycleOwner(), weatherObserver);

        return view;
    }

    final Observer<WeatherData> weatherObserver  = new Observer<WeatherData>() {
        @Override
        public void onChanged(@Nullable final WeatherData weatherData) {
            // Update the UI if this data variable changes
            if(weatherData!=null)
            {
                if(weatherData.weather[0].main.equals("ERROR"))
                {
                    weatherInfo.setText("Please enter a location in '[City], [Country Abbreviation] format.");
                }
                else
                {
                    String weatherString = "";

                    weatherString = weatherString + "Current Conditions: " + weatherData.weather[0].main + "\n";
                    weatherString = weatherString + "Current Temperature: " + weatherData.main.temp + "°\n";
                    weatherString = weatherString + "Feels-Like Temperature: " + weatherData.main.feels_like + "°\n";
                    weatherString = weatherString + "Current Humidity: " + weatherData.main.humidity + "%\n";
                    weatherString = weatherString + "Current Pressure: " + weatherData.main.pressure + "mbar\n";
                    weatherString = weatherString + "Current Wind Speed: " + weatherData.wind.speed + "mph\n";
                    weatherString = weatherString + "Current Wind Direction: " + weatherData.wind.deg + "°\n";
                    weatherString = weatherString + "Current Cloud Cover: " + weatherData.clouds.all + "%\n";

                    weatherInfo.setText(weatherString);
                }
            }
        }
    };

    @Override
    //public void onViewCreated(View view, Bundle savedInstanceState)
    public void onStart()
    {
        super.onStart();


        weatherUserViewModel = ViewModelProviders.of(this.getActivity()).get(WeatherUserViewModel.class);

        weatherUserViewModel.getProfileViewModelData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null && !user.getCity().isEmpty() && !user.getCountry().isEmpty())
                {
                    editLocation.setText(user.getCity() + ", " + user.getCountry());
                    localLocation = editLocation.getText().toString();
                    String locationForURL = localLocation.replaceAll(",\\s+", ",").trim();
                    locationForURL = locationForURL.replaceAll("\\s+", "%20").trim();

                    loadWeatherData(locationForURL);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetLocation:
            {

                localLocation = editLocation.getText().toString();
                String locationForURL = localLocation.replaceAll(",\\s+", ",").trim();
                locationForURL = locationForURL.replaceAll("\\s+", "%20").trim();

                loadWeatherData(locationForURL);
                break;
            }
            case R.id.backToLifestyle:
            {
                lifePressListener.onLifeBtnPress();
                break;
            }
        }
    }

    void loadWeatherData(String location){
        //pass the location in to the view model
        mWeatherViewModel.setLocation(location);
    }

}