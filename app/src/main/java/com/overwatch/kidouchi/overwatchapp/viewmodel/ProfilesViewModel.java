package com.overwatch.kidouchi.overwatchapp.viewmodel;

import android.net.Uri;

import com.overwatch.kidouchi.overwatchapp.bus.SearchEvent;
import com.overwatch.kidouchi.overwatchapp.bus.RxEventBus;
import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;
import com.overwatch.kidouchi.overwatchapp.model.Profile;
import com.overwatch.kidouchi.overwatchapp.repo.ProfileRepository;

import java.util.List;

import io.reactivex.Observable;

public class ProfilesViewModel {

    private final ProfileRepository profileRepository;

    public ProfilesViewModel(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Observable<List<ProfileEntity>> getAllProfiles() {
        return profileRepository.getAllProfilesDb().toObservable();
    }

    public Observable<ProfileEntity> getProfile(final String username,
                                          final Profile.Platform platform,
                                          final Profile.Region region) {
        return profileRepository.getProfile(username, region, platform);
    }

    public Observable<String> getProfileUsername(final String username,
                                         final Profile.Platform platform,
                                         final Profile.Region region) {
        return profileRepository.getProfile(username, region, platform)
                .map(profile -> profile.getUsername());
    }

    public Observable<Uri> getProfileAvatarUri(final String username,
                                               final Profile.Platform platform,
                                               final Profile.Region region) {
        return profileRepository.getProfile(username, region, platform)
                .map(profile -> Uri.parse(profile.getPortraitUrl()));
    }

    public void sendAddProfileEvent(final String username,
                                    final Profile.Platform platform,
                                    final Profile.Region region) {

        RxEventBus.getInstance().setEvent(new SearchEvent(username, platform, region));
    }

    public Observable<Integer> clearProfileList() {
        return profileRepository.clearAllProfiles();
    }
}
