package com.example.re_stats_android.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface IPlayerDataManagment {
    PlayerData buildPlayers(JSONObject json) throws JSONException;
    List<String> playersListFilterByName(List<PlayerData> players);
}
