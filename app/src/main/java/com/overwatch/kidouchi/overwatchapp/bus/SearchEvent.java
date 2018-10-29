package com.overwatch.kidouchi.overwatchapp.bus;

import com.overwatch.kidouchi.overwatchapp.model.Profile;

public class SearchEvent {

    private String battleTag;
    private Profile.Platform platform;
    private Profile.Region region;

    public SearchEvent(final String battleTag,
                       final Profile.Platform platform,
                       final Profile.Region region) {
        this.battleTag = battleTag;
        this.platform = platform;
        this.region = region;
    }

    public String getBattleTag() {
        return battleTag;
    }

    public Profile.Platform getPlatform() {
        return platform;
    }

    public Profile.Region getRegion() {
        return region;
    }
}
