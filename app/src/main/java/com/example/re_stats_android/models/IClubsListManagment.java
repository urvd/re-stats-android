package com.example.re_stats_android.models;

import java.util.List;
import java.util.Map;

public interface IClubsListManagment {
    List<String> clubsListFilterByName(String countryFilter,List<ClubsModel> clubsList);
    Map<String,String> clubsListFilterByUrl(List<ClubsModel> clubsList);
    Map<String,String> clubListFilterByName(List<ClassementData> clubsStatsList,
                                       List<ClubsModel> clubsList);
}
