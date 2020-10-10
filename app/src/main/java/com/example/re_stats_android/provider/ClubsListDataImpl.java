package com.example.re_stats_android.provider;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.re_stats_android.models.ClubsModel;
import com.example.re_stats_android.models.IClubsListManagment;
import java.util.ArrayList;
import java.util.List;

public class ClubsListDataImpl implements IClubsListManagment {

    @Override
    public List<String> clubsListFilterByName(@Nullable String countryFilter,
                                              List<ClubsModel> clubsList) {
        List<String> clubsListName = new ArrayList<>();

        if(countryFilter!=null){
            for (ClubsModel clubs: clubsList) {
                if(clubs.getCountry().equals(countryFilter)) {
                    clubsListName.add(clubs.getName());
                }
            }
            return clubsListName;
        }
        for (ClubsModel model : clubsList) {
            clubsListName.add(model.getName());
        }
        return clubsListName;
    }
}
