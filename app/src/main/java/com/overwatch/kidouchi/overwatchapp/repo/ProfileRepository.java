package com.overwatch.kidouchi.overwatchapp.repo;

import android.app.Application;

import android.content.Context;
import com.google.common.base.Strings;
import com.overwatch.kidouchi.overwatchapp.OverwatchApp;
import com.overwatch.kidouchi.overwatchapp.R;
import com.overwatch.kidouchi.overwatchapp.db.OverwatchDatabase;
import com.overwatch.kidouchi.overwatchapp.db.dao.ProfileDao;
import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;
import com.overwatch.kidouchi.overwatchapp.model.Profile;
import com.overwatch.kidouchi.overwatchapp.service.OverwatchService;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ProfileRepository {

    private OverwatchService overwatchService;
    private Single<List<ProfileEntity>> allProfiles;
    private ProfileDao profileDao;
    private Context context;

    public ProfileRepository(final Application application) {
        OverwatchDatabase database = OverwatchDatabase.getDatabase(application);
        profileDao = database.profileDao();
        overwatchService = ((OverwatchApp) application).getOverwatchService();
        context = application;

        allProfiles = profileDao.getAllProfiles();
    }

    public Observable<Integer> clearAllProfiles() {
        return Observable.fromCallable(() -> profileDao.deleteAllProfiles());
    }

    public Single<List<ProfileEntity>> getAllProfilesDb() {
        return allProfiles;
    }

    private Maybe<ProfileEntity> getProfileDb(final String username) {
        return profileDao.getProfile(username);
    }

    private Observable<ProfileEntity> getProfileThruNetwork(final String username,
                                                        final Profile.Region region,
                                                        final Profile.Platform platform) {
        final String usernameFormatted = username.replace('#', '-');
        return overwatchService.getProfile(usernameFormatted, platform.getValue(), region.getValue())
                .flatMap(profileResponse -> {
                    if (profileResponse.isSuccessful()) {
                        final Profile profile = profileResponse.body();
                        if (Strings.isNullOrEmpty(profile.getUsername())) {
                            // Poor api doesn't return error code or error response, so we have
                            // to detect for an empty property
                            return Observable.error(new Exception(context.getString(R.string.profile_request_err_msg)));
                        }
                        final ProfileEntity profileEntity = new ProfileEntity(profile.getUsername(), profile
                                .getPortraitImgUrl());
                        return Observable.just(profileEntity);
                    } else {
                        return Observable.error(new Exception(context.getString(R.string.internet_err_msg)));
                    }
                })
                .doOnNext(profileEntity -> {
                    if (!Strings.isNullOrEmpty(profileEntity.getUsername())) {
                        profileDao.insert(profileEntity);
                    }
                });
    }

    public Observable<ProfileEntity> getProfile(final String username, final Profile.Region region,
                                           final Profile.Platform platform) {
        return Observable.concat(getProfileDb(username).toObservable(),
                getProfileThruNetwork(username, region, platform));
    }
}
