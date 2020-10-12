package com.example.re_stats_android.fragments.leagues;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.re_stats_android.R;
import com.example.re_stats_android.adapters.league.ClassementAdapter;
import com.example.re_stats_android.models.ClassementModel;
import com.example.re_stats_android.models.FormModel;
import com.example.re_stats_android.models.ClassementData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.re_stats_android.communs.CommunValues.FORM_LEAGUES;
import static com.example.re_stats_android.communs.CommunValues.FORM_SEASON;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaguesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaguesFragment extends Fragment {
    FormModel formModel;
    TextView titleLeague;
    private RecyclerView recyclerView;
    private ClassementAdapter classementAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("stats/championnats_stats");

    public LeaguesFragment() {
        // Required empty public constructor
    }

    public static LeaguesFragment newInstance(FormModel formModel) {
        LeaguesFragment fragment = new LeaguesFragment();
        Bundle args = new Bundle();
        args.putString(FORM_SEASON, formModel.getSeasons());
        args.putString(FORM_LEAGUES, formModel.getLeagues());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getActivity().s;
//        actionBar.setCustomView(R.layout.toolbar_simple);
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leagues, container, false);
        titleLeague = view.findViewById(R.id.title_league);
        if (getArguments() != null) {
            formModel = FormModel.builder()
                    .seasons(getArguments().getString(FORM_SEASON))
                    .leagues(getArguments().getString(FORM_LEAGUES))
                    .build();
            titleLeague.setText(titleLeague.getText() +" :" +formModel.getLeagues()+" et seasons 2019-2020");
        }
        recyclerView = view.findViewById(R.id.cls_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getClassementLeague();
        return  view;
    }

    private void getClassementLeague(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ClassementModel model = new ClassementModel();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String leagueName = snapshot.child("championnat").getValue(String.class);
                    if(leagueName.equals(formModel.getLeagues())) {
                        model = ClassementModel.builder()
                                .championnat(leagueName)
                                .seasons(snapshot.child("seasons").getValue(String.class))
                                .build();

                        List<ClassementData> stats = new ArrayList<>();
                        for (DataSnapshot childSnapshot : snapshot.child("classement").getChildren()) {
                            ClassementData statsData = ClassementData.builder()
                                    .but_pour(childSnapshot.child("but_pour").getValue(Integer.class))
                                    .but_contre(childSnapshot.child("but_contre").getValue(Integer.class))
                                    .nul(childSnapshot.child("nul").getValue(Integer.class))
                                    .gagne(childSnapshot.child("gagne").getValue(Integer.class))
                                    .perdu(childSnapshot.child("perdu").getValue(Integer.class))
                                    .joue(childSnapshot.child("joue").getValue(Integer.class))
                                    .pts(childSnapshot.child("pts").getValue(Integer.class))
                                    .clubs(childSnapshot.child("clubs").getValue(String.class))
                                    .build();
                            stats.add(statsData);
                        }
                        model.setClassement(stats);
                        break;
                    }
                }
                classementAdapter = new ClassementAdapter(getContext(),
                                model.getClassement(),
                                model.getChampionnat());

                recyclerView.setAdapter(classementAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value ClassementModel.", error.toException());
            }
        });
    }
}