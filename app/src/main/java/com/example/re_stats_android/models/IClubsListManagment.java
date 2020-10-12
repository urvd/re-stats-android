package com.example.re_stats_android.models;

import java.util.List;

public interface IClubsListManagment {
    List<String> clubsListFilterByName(String countryFilter,List<ClubsModel> clubsList);
}
