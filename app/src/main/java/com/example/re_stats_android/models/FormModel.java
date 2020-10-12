package com.example.re_stats_android.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormModel {
    private String accountUsername;
    private String seasons;
    private String leagues;
    private String clubs;
    private String players;
}
