package com.overwatch.kidouchi.overwatchapp.model;

import com.google.gson.annotations.SerializedName;

public class Games {

    @SerializedName("quickplay")
    private Quickplay quickplay;

    @SerializedName("competitive")
    private Competitive competitive;
}
