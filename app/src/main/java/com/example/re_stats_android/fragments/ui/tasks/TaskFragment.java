package com.example.re_stats_android.fragments.ui.tasks;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.re_stats_android.R;
import com.example.re_stats_android.fragments.ui.FragmentSliderImpl;
import com.example.re_stats_android.fragments.ui.main.HomeStartFragment;

import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_ACCOUNT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements FragmentSliderImpl {

    Button sliderLeft;
    public TaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public void setTargetFragment(@Nullable Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task, container, false);
        sliderLeft = view.findViewById(R.id.slideframe_left);
        //sliderRight
        sliderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });
        return  view;
    }

    //FragmentSliderImpl interface
    @Override
    public void openFragment() {
        Fragment fragment = fragment = HomeStartFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.frame_from_left_slider, R.anim.frame_to_right_slider);
        transaction.replace(R.id.container,fragment).commit();
    }
}