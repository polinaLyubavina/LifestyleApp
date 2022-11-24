package com.lifestyleapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WeatherUserViewModel extends AndroidViewModel {

    private LiveData<User> userLiveData;
    private UserRepository userRepository;

    public WeatherUserViewModel (Application application) {

        super(application);

        userRepository = UserRepository.getInstance();

        userLiveData = userRepository.getUserData();

    }

    // FORWARD ALL OF THE DATA TO THE REPOSITORY
    public void setProfileViewModelData(String fullName, int age, String city, String country, double height, double weight, int gender, String photoLocation, int photoSize, int steps, double bmi, double bmr, boolean sedentary, double pounds){
        userRepository.setUserData(fullName, age, city, country, height, weight, gender, photoLocation, photoSize, steps, sedentary, pounds);
    }

    // RETRIEVE DATA FROM THE REPOSITORY
    public LiveData<User> getProfileViewModelData() {
        return userRepository.getUserData();
    }

}