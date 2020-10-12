package com.example.re_stats_android.models;

import android.app.Activity;

import com.example.re_stats_android.models.UserModel;

import java.util.List;

public interface IUser {
    void signInUser(final Activity activity, UserModel userModel);
    String onIdTokenRevocation();
    UserModel getCurrentUser();
    void signOutUser();
}
