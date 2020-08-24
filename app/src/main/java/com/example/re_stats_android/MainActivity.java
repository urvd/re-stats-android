package com.example.re_stats_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.re_stats_android.fragments.authent.AuthentFragment;
import com.example.re_stats_android.fragments.authent.LoginFragment;

public class MainActivity extends AppCompatActivity {
    AuthentFragment authentFragment;
    LoginFragment loginFragment;
    //InscriptionFragment inscriptionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar_simple);
        actionBar.setDisplayHomeAsUpEnabled(true);

        loginFragment = new LoginFragment();
        authentFragment = new AuthentFragment(getSupportFragmentManager());
        authentFragment.startFragment(loginFragment);
    }
}