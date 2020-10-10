package com.example.re_stats_android.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class PlayersModel {
    private  int result;
    private List<PlayerData> players;
}
