package com.overwatch.kidouchi.overwatchapp.model;

import com.google.gson.annotations.SerializedName;

public class Competitive {

    @SerializedName("wins")
    private int wins;

    @SerializedName("lost")
    private int lost;

    @SerializedName("played")
    private int played;
}
