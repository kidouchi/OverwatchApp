package com.overwatch.kidouchi.overwatchapp.service;

import com.overwatch.kidouchi.overwatchapp.model.Profile;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OverwatchService {

    @GET("profile/{platform}/{region}/{tag}")
    Observable<ApiResponse<Profile>> getProfile(@Path("platform") String platform,
                                                @Path("region") String region,
                                                @Path("tag") String tag);

}
