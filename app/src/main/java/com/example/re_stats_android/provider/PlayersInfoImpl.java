package com.example.re_stats_android.provider;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.re_stats_android.models.ClubsModel;
import com.example.re_stats_android.models.IPlayerDataManagment;
import com.example.re_stats_android.models.PlayerData;
import com.example.re_stats_android.models.PlayersModel;
import com.google.android.gms.common.api.internal.TaskApiCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import static android.content.ContentValues.TAG;

public class PlayersInfoImpl implements IPlayerDataManagment {

    @Override
    public PlayerData buildPlayers(JSONObject json) throws JSONException {
        return  PlayerData.builder()
                .player_id(json.getInt("player_id"))
                .player_name(json.getString("player_name"))
                .firstname(json.getString("firstname"))
                .lastname(json.getString("lastname"))
                .number(null)
                .position(json.getString("position"))
                .nationality(json.getString("nationality"))
                .height(json.getString("height"))
                .weight(json.getString("weight"))
                .build();
    }

    @Override
    public List<String> playersListFilterByName(List<PlayerData> players) {
        List<String> playersListName = new ArrayList<>();
        for (PlayerData player: players) {
            playersListName.add(player.getFirstname() + " " + player.getLastname());
        }
        return playersListName;
    }
}
