package com.example.re_stats_android.provider;

import androidx.annotation.Nullable;

import com.example.re_stats_android.models.ClubsModel;
import com.example.re_stats_android.models.IClubsListManagment;
import com.example.re_stats_android.models.ClassementData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubsListDataImpl implements IClubsListManagment {

    @Override
    public List<String> clubsListFilterByName(@Nullable String countryFilter,
                                              List<ClubsModel> clubsList) {
        List<String> clubsListName = new ArrayList<>();

        if(countryFilter!=null || !countryFilter.isEmpty()){
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

    @Override
    public Map<String,String> clubsListFilterByUrl(List<ClubsModel> clubsList) {
        Map<String,String> map = new HashMap<>();
        for (ClubsModel clubs: clubsList) {
            map.put(clubs.getName(),clubs.getLogo().replace("\\",""));
        }
        return map;
    }

    @Override
    public  Map<String,String>  clubListFilterByName(List<ClassementData> clubsStatsList, List<ClubsModel> clubsList) {
        Map<String,String> finalclubsMap= new HashMap<>();
        Map<String,String> map = clubsListFilterByUrl(clubsList);
        for (final ClassementData clubs: clubsStatsList) {
            if(map.keySet().contains(clubs.getClubs().compareToIgnoreCase(clubs.getClubs()))) {
                finalclubsMap.put(clubs.getClubs(), map.get(clubs.getClubs()));
            }
        }
        return finalclubsMap;
    }
}
