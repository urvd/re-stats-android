package com.example.re_stats_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.re_stats_android.fragments.ui.main.HomeStartFragment;

public class HomeBaseActivity extends AppCompatActivity {

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_base_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar_simple);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        sliderLeft = findViewById(R.id.slideframe_left);
//        sliderRight = findViewById(R.id.slideframe_right);
        if (savedInstanceState == null) {
            //sliderLeft.setVisibility(Color.TRANSPARENT);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeStartFragment.newInstance())
                    .commitNow();
        }
    }
}