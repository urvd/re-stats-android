package com.example.re_stats_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.re_stats_android.fragments.ui.main.HomeBaseFragment;

public class HomeBaseActivity extends AppCompatActivity {
    Object ins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_base_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeBaseFragment.newInstance())
                    .commitNow();
        }
    }
}