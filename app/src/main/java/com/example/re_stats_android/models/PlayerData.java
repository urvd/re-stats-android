package com.example.re_stats_android.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class PlayerData {
    private  int player_id;
    private String player_name;
    private String firstname;
    private String lastname;
    private String number;
    private String position;
    private int age;
    private String birth_date;
    private String birth_place;
    private String birth_country;
    private String nationality;
    private String height;
    private String weight;
}
