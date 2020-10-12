package com.example.re_stats_android.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassementData {
    private int but_contre;
    private int but_pour;
    private String clubs;
    private int gagne;
    private int joue;
    private int nul;
    private int perdu;
    private int pts;
}
