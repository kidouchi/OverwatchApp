package com.overwatch.kidouchi.overwatchapp.viewmodel;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.overwatch.kidouchi.overwatchapp.OverwatchApp;
import com.overwatch.kidouchi.overwatchapp.bus.SearchEvent;
import com.overwatch.kidouchi.overwatchapp.bus.RxEventBus;
import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;
import com.overwatch.kidouchi.overwatchapp.model.Profile;
import com.overwatch.kidouchi.overwatchapp.repo.ProfileRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilesViewModel {

    private final ProfileRepository profileRepository;
    private final Context context;

    public ProfilesViewModel(final Context context) {
        profileRepository = new ProfileRepository((OverwatchApp) context.getApplicationContext());
        this.context = context;
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

    public void clearProfileList() {
        profileRepository.clearAllProfiles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(numRows -> Toast.makeText(context, "Success! " + numRows + "deleted!", Toast.LENGTH_LONG).show(),
                        throwable -> Toast.makeText(context, "Wasn't able to clear all the profiles", Toast.LENGTH_LONG).show());
    }
}
