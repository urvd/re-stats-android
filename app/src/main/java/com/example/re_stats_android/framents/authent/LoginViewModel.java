package com.example.re_stats_android.framents.authent;

import androidx.lifecycle.ViewModel;

import com.example.re_stats_android.models.UserModel;

public class LoginViewModel extends ViewModel {
    UserModel userState;
    public boolean isEqualToMock(){
        return this.userState.getUsername() == "user"
                && this.userState.getPassword() == "user"
                && this.userState.getStatus();

    }

    public UserModel getUserState() {
        return userState;
    }

    public void setUserState(UserModel userState) {
        this.userState = userState;
    }
}