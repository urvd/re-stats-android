package com.example.re_stats_android.fragments.ui.tasks;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.re_stats_android.fragments.leagues.LeaguesFragment;
import com.example.re_stats_android.R;
import com.example.re_stats_android.fragments.ui.main.HomeStartFragment;
import com.example.re_stats_android.models.FormModel;

import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_CLUBS;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_LEAGUES;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_PLAYERS;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_TASK;
import static com.example.re_stats_android.communs.CommunValues.FORM_CLUBS;
import static com.example.re_stats_android.communs.CommunValues.FORM_LEAGUES;
import static com.example.re_stats_android.communs.CommunValues.FORM_PLAYERS;
import static com.example.re_stats_android.communs.CommunValues.FORM_SEASON;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {
    FormModel formModel;
    String mParam;
    Button sliderLeft;
    public TaskFragment() {
    }
    FragmentManager fragmentManager;
    public TaskFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(FragmentManager manager, FormModel model, String formCase) {
        TaskFragment fragment = new TaskFragment(manager);
        Bundle args = new Bundle();
        switch (formCase){
            case ARG_PARAM_LEAGUES:
                args.putString(ARG_PARAM_TASK, formCase);
                args.putString(FORM_SEASON, model.getSeasons());
                args.putString(FORM_LEAGUES, model.getLeagues());
                break;
            case ARG_PARAM_CLUBS:
                args.putString(ARG_PARAM_TASK, formCase);
                args.putString(FORM_SEASON, model.getSeasons());
                args.putString(FORM_LEAGUES, model.getLeagues());
                args.putString(FORM_CLUBS, model.getClubs());
                break;
            case ARG_PARAM_PLAYERS:
                args.putString(ARG_PARAM_TASK, formCase);
                args.putString(FORM_SEASON, model.getSeasons());
                args.putString(FORM_LEAGUES, model.getLeagues());
                args.putString(FORM_PLAYERS, model.getPlayers());
                break;
            default:
                args.putString(ARG_PARAM_TASK, "NONE");
                break;

        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTargetFragment(@Nullable Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || mParam.equals("NONE") ){
            formModel = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task, container, false);

//        sliderLeft = view.findViewById(R.id.slideframe_left);
//        //sliderRight
//        sliderLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFragment();
//            }
//        });
        return  view;
    }

    public void openFragment(Fragment fragment) {
        mParam = getArguments().getString(ARG_PARAM_TASK);
        switch (mParam){
            case ARG_PARAM_LEAGUES:
                formModel = FormModel.builder()
                        .seasons( getArguments().getString(FORM_SEASON))
                        .leagues( getArguments().getString(FORM_LEAGUES))
                        .build();
                break;
            case ARG_PARAM_CLUBS:
                formModel = FormModel.builder()
                        .seasons( getArguments().getString(FORM_SEASON))
                        .leagues( getArguments().getString(FORM_LEAGUES))
                        .clubs( getArguments().getString(FORM_CLUBS))
                        .build();
                break;
            case ARG_PARAM_PLAYERS:
                formModel = FormModel.builder()
                        .seasons( getArguments().getString(FORM_SEASON))
                        .leagues( getArguments().getString(FORM_LEAGUES))
                        .clubs( getArguments().getString(FORM_CLUBS))
                        .players(getArguments().getString(FORM_PLAYERS))
                        .build();
                break;
            default:
                break;
        }
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.frame_from_left_slider, R.anim.frame_to_right_slider);
        transaction.replace(R.id.container,fragment).commit();
    }
}