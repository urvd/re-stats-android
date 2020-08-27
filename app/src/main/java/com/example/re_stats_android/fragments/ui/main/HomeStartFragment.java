package com.example.re_stats_android.fragments.ui.main;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.re_stats_android.R;
import com.example.re_stats_android.fragments.ui.FragmentSliderImpl;
import com.example.re_stats_android.fragments.ui.tasks.TaskFragment;

import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM1;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM2;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM3;

public class HomeStartFragment extends Fragment implements FragmentSliderImpl {

    private MainViewModel mViewModel;
    Button ask_leagues;
    Button ask_clubs;
    Button ask_players;
    Button sliderRight;
    String form_asked;

    public static HomeStartFragment newInstance() {
        return new HomeStartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_start_fragment, container, false);
        ask_leagues = view.findViewById(R.id.btn_see_leagues);
        ask_clubs = view.findViewById(R.id.btn_see_clubs);
        ask_players = view.findViewById(R.id.btn_see_player);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.frame_home, FormFragment.newInstance(""))
                    .replace(R.id.frame_home, FormFragment.newInstance(""))
                    .commit();
        }
        sliderRight = view.findViewById(R.id.slideframe_right);

        sliderRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });

        ask_leagues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, FormFragment.newInstance(ARG_PARAM1))
                        .commitNow();
                form_asked = ARG_PARAM1;
            }
        });

        ask_clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, FormFragment.newInstance(ARG_PARAM2))
                        .commitNow();
                form_asked = ARG_PARAM2;
            }
        });
        ask_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, FormFragment.newInstance(ARG_PARAM3))
                        .commitNow();
                form_asked = ARG_PARAM3;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.formViewKey = form_asked;
    }

    //FragmentSliderImpl interface
    @Override
    public void openFragment() {
        Fragment fragment = fragment = TaskFragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.frame_from_right_slider, R.anim.frame_to_left_slider);
        transaction.replace(R.id.container,fragment).commit();
    }
}