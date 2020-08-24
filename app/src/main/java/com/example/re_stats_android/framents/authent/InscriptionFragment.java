package com.example.re_stats_android.framents.authent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.re_stats_android.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InscriptionFragment extends Fragment {

    public InscriptionFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static InscriptionFragment newInstance(String param1, String param2) {
        return new InscriptionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inscription, container, false);
    }
}