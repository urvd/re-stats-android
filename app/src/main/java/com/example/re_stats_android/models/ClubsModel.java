package com.example.re_stats_android.models;

import lombok.Data;

@Data
public class ClubsModel {
    private int team_id;
    private String name;
    private String code;
    private String logo;
    private String country;
    private boolean is_national;
    private int founded;
    private String venue_name;
    private String venue_surface;
    private String venue_address;
    private String venue_city;
    private int venue_capacity;
}
