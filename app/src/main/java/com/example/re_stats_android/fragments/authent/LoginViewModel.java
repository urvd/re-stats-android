package com.example.re_stats_android.fragments.authent;

import androidx.lifecycle.ViewModel;

import com.example.re_stats_android.models.UserModel;

public class LoginViewModel extends ViewModel {
    UserModel userState;
    public boolean isEqualToMock(){
        return this.userState.getUsername() == "user"
                && this.userState.getPassword() == "user"
                && this.userState.isStatus();

    }

    public UserModel getUserState() {
        return userState;
    }

    public void setUserState(UserModel userState) {
        this.userState = userState;
    }
}