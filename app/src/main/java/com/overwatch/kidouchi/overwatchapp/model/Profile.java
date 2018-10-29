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

    @SerializedName("portrait")
    private String portraitImgUrl;

    @SerializedName("star")
    private String starImgUrl;

    @SerializedName("endorsement")
    private Endorsement endorsement;

    public String getUsername() {
        return username;
    }

    public Games getGames() {
        return games;
    }

    public Playtime getPlaytime() {
        return playtime;
    }

    public CompetitiveRank getCompetitiveRank() {
        return competitiveRank;
    }

    public String getPortraitImgUrl() {
        return portraitImgUrl;
    }

    public String getStarImgUrl() {
        return starImgUrl;
    }

    public Endorsement getEndorsement() {
        return endorsement;
    }

    public enum Region {
        US("us"),

        EU("eu"),

        KR("kr"),

        CN("cn"),

        GLOBAL("global");

        private String value;

        public String getValue() {
            return value;
        }

        private Region(final String value) {
            this.value = value;
        }
    }

    public enum Platform {
        PC("pc"),

        PSN("psn"),

        XBL("xbl");

        private String value;

        public String getValue() {
            return value;
        }

        private Platform(final String value) {
            this.value = value;
        }
    }
}
