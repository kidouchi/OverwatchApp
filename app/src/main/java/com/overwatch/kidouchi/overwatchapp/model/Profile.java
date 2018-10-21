package com.overwatch.kidouchi.overwatchapp.model;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("username")
    private String username;

    @SerializedName("games")
    private Games games;

    @SerializedName("playtime")
    private Playtime playtime;

    @SerializedName("competitive")
    private CompetitiveRank competitiveRank;
}
