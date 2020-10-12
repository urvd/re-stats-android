package com.example.re_stats_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.re_stats_android.fragments.authent.AuthentFragment;
import com.example.re_stats_android.fragments.authent.LoginFragment;
import com.example.re_stats_android.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
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