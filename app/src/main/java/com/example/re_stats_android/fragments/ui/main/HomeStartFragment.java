package com.example.re_stats_android.fragments.ui.main;

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

import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_LEAGUES;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_CLUBS;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_PLAYERS;

public class HomeStartFragment extends Fragment {

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
//        sliderRight = view.findViewById(R.id.slideframe_right);
//
//        sliderRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFragment();
//            }
//        });

        ask_leagues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.formViewKey = ARG_PARAM_LEAGUES;
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, FormFragment.newInstance(ARG_PARAM_LEAGUES))
                        .commitNow();
                //form_asked = ARG_PARAM_LEAGUES;

            }
        });

        ask_clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.formViewKey = ARG_PARAM_CLUBS;
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, FormFragment.newInstance(ARG_PARAM_CLUBS))
                        .commitNow();
                //form_asked = ARG_PARAM_CLUBS;
            }
        });
        ask_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.formViewKey = ARG_PARAM_PLAYERS;
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, FormFragment.newInstance(ARG_PARAM_PLAYERS))
                        .commitNow();
                //form_asked = ARG_PARAM_PLAYERS;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if(mViewModel.formViewKey == null) mViewModel.formViewKey = "Default";
    }

    //FragmentSliderImpl interface
//    @Override
//    public void openFragment() {
//        Fragment fragment = fragment = TaskFragment.newInstance(null,null);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.frame_from_right_slider, R.anim.frame_to_left_slider);
//        transaction.replace(R.id.container,fragment).commit();
//    }
}