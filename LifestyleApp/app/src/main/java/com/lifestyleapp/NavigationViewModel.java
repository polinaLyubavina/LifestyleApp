package com.lifestyleapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NavigationViewModel extends AndroidViewModel {

    private LiveData<User> userMutableLiveData;
    private UserRepository profilePageRepository;

    public NavigationViewModel(Application application) {

        super(application);
        profilePageRepository = UserRepository.getInstance();
        profilePageRepository.createDb(application.getApplicationContext());
        userMutableLiveData = profilePageRepository.getUserData();

    }

    // RETRIEVE DATA FROM THE REPOSITORY
    private LiveData<User> getProfileViewModelData() {
        return profilePageRepository.getUserData();
    }

    public String getCity() {

        String city = "Salt Lake City";  // default if there is no user

        if (getProfileViewModelData().getValue() != null) {

            return getProfileViewModelData().getValue().getCity();

        }

        return city;
    }

}
