package com.example.re_stats_android.fragments.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.re_stats_android.R;
import com.example.re_stats_android.fragments.leagues.LeaguesFragment;
import com.example.re_stats_android.fragments.ui.tasks.TaskFragment;
import com.example.re_stats_android.models.ClubsModel;
import com.example.re_stats_android.models.FormModel;
import com.example.re_stats_android.models.IClubsListManagment;
import com.example.re_stats_android.models.IPlayerDataManagment;
import com.example.re_stats_android.models.PlayerData;
import com.example.re_stats_android.models.PlayersModel;
import com.example.re_stats_android.provider.ClubsListDataImpl;
import com.example.re_stats_android.provider.PlayersInfoImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

import static android.content.ContentValues.TAG;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_CLUBS;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_LEAGUES;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_PLAYERS;
import static com.example.re_stats_android.communs.CommunValues.Bundesligua;
import static com.example.re_stats_android.communs.CommunValues.ButtonGriser;
import static com.example.re_stats_android.communs.CommunValues.ButtonSubmit;
import static com.example.re_stats_android.communs.CommunValues.Ligua;
import static com.example.re_stats_android.communs.CommunValues.Ligue1;
import static com.example.re_stats_android.communs.CommunValues.Premiere_ligue;
import static com.example.re_stats_android.communs.CommunValues.SerieA;

public class FormFragment extends Fragment {
    private static String PARAM_ID_CLUBS = "ID_CLUBS";
    SharedPreferences sharedPref;
    ArrayAdapter<String> clubsListAdapter;
    ArrayAdapter<String> playersListAdapter;
    TextView form_title;
    Spinner spinnerSeasons;
    Spinner spinnerLeagues;
    Spinner spinnerClubs;
    Spinner spinnerPlayers;
    Button submitTask;
    private String mParam;
    private static String LEAGUES_FORM_TITLE = "Chercher un championnat";
    private static String CLUBS_FORM_TITLE = "Chercher un clubs";
    private static String PLAYERS_FORM_TITLE = "Chercher un joueurs";

    IClubsListManagment listManagment;
    IPlayerDataManagment playersData;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("teams");

    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance(String param) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        switch (param) {
            case ARG_PARAM_LEAGUES:
                args.putString(ARG_PARAM_LEAGUES, param);
                break;
            case ARG_PARAM_CLUBS:
                args.putString(ARG_PARAM_CLUBS, param);
                break;
            case ARG_PARAM_PLAYERS:
                args.putString(ARG_PARAM_PLAYERS, param);
                break;
            default:
                args.putString("NONE", param);
                break;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listManagment = new ClubsListDataImpl();
        playersData = new PlayersInfoImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        spinnerSeasons = view.findViewById(R.id.season_spinner);
        spinnerLeagues = view.findViewById(R.id.leagues_spinner);
        spinnerClubs = view.findViewById(R.id.clubs_spinner);
        spinnerPlayers = view.findViewById(R.id.players_spinner);
        form_title = view.findViewById(R.id.framehome_title);
        submitTask = view.findViewById(R.id.submit_task);

        spinnerSeasons.setAdapter((SpinnerAdapter) getSeasonsValueAdaptater());
        spinnerLeagues.setAdapter((SpinnerAdapter) getLeaguesValueAdaptater());
//        spinnerClubs.setAdapter((SpinnerAdapter) getClubsValueAdaptater());

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (getArguments().get(ARG_PARAM_LEAGUES) != null) {
            mParam = ARG_PARAM_LEAGUES;
            initLeaguesForm();
        } else if (getArguments().get(ARG_PARAM_CLUBS) != null) {
            mParam = ARG_PARAM_CLUBS;
            initClubsForm();
        } else if (getArguments().get(ARG_PARAM_PLAYERS) != null) {
            mParam = ARG_PARAM_PLAYERS;
            initPlayersForm();
        } else {
            mParam = "";
            noForm();
        }
        spinnerLeagues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerClubs.isEnabled()) {
                    getClubsValues(SearchCaseClub.CASE_GET_LIST_NOM);
                }
                ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerClubs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerPlayers.isEnabled()) {
                    getListOfPlayer();
                }
                ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Boolean condition = submitRequirement();
        submitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormModel formModel = null;
                switch (mParam) {
                    case ARG_PARAM_LEAGUES:
                        formModel = FormModel.builder()
                                .seasons(spinnerSeasons.getSelectedItem().toString())
                                .leagues(spinnerLeagues.getSelectedItem().toString())
                                .build();
                        TaskFragment fragment = TaskFragment.newInstance(getParentFragmentManager(), formModel, mParam);
                        fragment.openFragment(LeaguesFragment.newInstance(formModel));
                        break;
                    case ARG_PARAM_CLUBS:
                        formModel = FormModel.builder()
                                .seasons(spinnerSeasons.getSelectedItem().toString())
                                .leagues(spinnerLeagues.getSelectedItem().toString())
                                .clubs(spinnerClubs.getSelectedItem().toString())
                                .build();
                        break;
                    case ARG_PARAM_PLAYERS:
                        formModel = FormModel.builder()
                                .seasons(spinnerSeasons.getSelectedItem().toString())
                                .leagues(spinnerLeagues.getSelectedItem().toString())
                                .clubs(spinnerClubs.getSelectedItem().toString())
                                .players(spinnerPlayers.getSelectedItem().toString())
                                .build();
                        break;
                    default:
                        break;
                }
//                if(!mParam.isEmpty() && formModel != null){
//                    openFragment(formModel);
//                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //if(mViewModel==null) mViewModel = ViewModelProviders.of(this).get(FormModelView.class);

    }

    private Boolean submitRequirement() {
        if (mParam.isEmpty()) {
            submitTask.setBackgroundColor(ButtonGriser);
            submitTask.setEnabled(false);
            return false;
        }
        submitTask.setBackgroundColor(ButtonSubmit);
        submitTask.setEnabled(true);
        return true;
    }

    Adapter getMockAdaptater() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    Adapter getSeasonsValueAdaptater() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.seasons_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    Adapter getLeaguesValueAdaptater() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.leagues_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public String countryMatches() {

        switch (spinnerLeagues.getSelectedItem().toString()) {
            case Ligue1:
                return "France";
            case Premiere_ligue:
                return "England";
            case Bundesligua:
                return "Germany";
            case SerieA:
                return "Italy";
            case Ligua:
                return "Spain";
            default:
                return null;
        }
    }


    void initLeaguesForm() {
        form_title.setText(LEAGUES_FORM_TITLE);
        spinnerClubs.setEnabled(false);
        spinnerPlayers.setEnabled(false);
    }

    void initClubsForm() {
        form_title.setText(CLUBS_FORM_TITLE);
        //getClubsValues(SearchCaseClub.CASE_GET_LIST_NOM);
        spinnerPlayers.setEnabled(false);
    }

    void initPlayersForm() {
        form_title.setText(PLAYERS_FORM_TITLE);
//        spinnerClubs.setAdapter((SpinnerAdapter) clubsListAdapter);
        //getClubsValues(SearchCaseClub.CASE_GET_LIST_NOM);
        //getListOfPlayer();
//        spinnerPlayers.setAdapter((SpinnerAdapter) playersListAdapter);
    }

    void noForm() {
        form_title.setText("Faite un choix en haut pour chercher des stats !!");
        spinnerLeagues.setEnabled(false);
        spinnerSeasons.setEnabled(false);
        spinnerClubs.setEnabled(false);
        spinnerPlayers.setEnabled(false);
    }

    public static enum SearchCaseClub {
        CASE_GET_LIST_NOM, CASE_GET_TEAMID;
    }

    public void getClubsValues(final SearchCaseClub searchCaseClub) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotes) {
                List<ClubsModel> clubs = new ArrayList<>();
                for (DataSnapshot dataSnapshot : dataSnapshotes.getChildren()) {
                    clubs.add(dataSnapshot.getValue(ClubsModel.class));
                }
                ;
                if (searchCaseClub == SearchCaseClub.CASE_GET_LIST_NOM) {
                    clubsListAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,
                            listManagment.clubsListFilterByName(countryMatches(), clubs));
                    clubsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClubs.setAdapter((SpinnerAdapter) clubsListAdapter);
                    //clubsNameIdentityAdapter = new ClubsNameIdentityAdapter(getContext(),clubs);
                }

                if (searchCaseClub == SearchCaseClub.CASE_GET_TEAMID) {
                    for (ClubsModel clb : clubs) {
                        if (clb.getName().equals(spinnerClubs.getSelectedItem().toString())) {
                            //Bundle arg = getArguments();
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(PARAM_ID_CLUBS, String.valueOf(clb.getTeam_id()));
                            editor.apply();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());

            }
        });
    }

    public void getListOfPlayer() {
        String url = "https://api-football-v1.p.rapidapi.com/v2/players/squad/{teamId}/{seasons}";
        try {
            AndroidNetworking.get(url)
                    .addPathParameter("teamId", sharedPref.getString(PARAM_ID_CLUBS, null))
                    .addPathParameter("seasons", spinnerSeasons.getSelectedItem().toString())
                    .addHeaders("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                    .addHeaders("x-rapidapi-key", "35203f6d9dmshbd9b33f83d7343bp122468jsn3246bca289a7")
                    .addHeaders("Content-Type", "application/json")
                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @SneakyThrows
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONObject object1 = response.getJSONObject("api");
                            List<PlayerData> players = new ArrayList<>();
                            JSONArray array2 = object1.getJSONArray("players");
                            for (int i = 0; i < array2.length(); i++) {
                                PlayerData playerData = playersData.buildPlayers(array2.getJSONObject(i));
                                players.add(playerData);
                            }
                            PlayersModel model = PlayersModel.builder()
                                    .result(object1.getInt("results"))
                                    .players(players)
                                    .build();
                            if (model != null || model.getResult() > 0 &&
                                    !model.getPlayers().isEmpty() || model.getPlayers() != null) {
                                playersListAdapter = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_item,
                                        playersData.playersListFilterByName(model.getPlayers()));
                                playersListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerPlayers.setAdapter((SpinnerAdapter) playersListAdapter);
                            } else {
                                spinnerPlayers.setEnabled(false);
                            }

                        }

                        @Override
                        public void onError(ANError error) {
                            Log.w(TAG, "Failed to read value.", error.fillInStackTrace());
                            spinnerPlayers.setEnabled(false);
                        }
                    });
        } catch (Exception e) {
            String err = e.getMessage();
            e.printStackTrace();
        }
    }

//    public void openFragment(FormModel model) {
//        Fragment fragment = fragment = TaskFragment.newInstance(model,mParam);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.frame_from_right_slider, R.anim.frame_to_left_slider);
//        transaction.add(R.id.container,fragment).replace(R.id.container,fragment).commitNowAllowingStateLoss();
//    }
}