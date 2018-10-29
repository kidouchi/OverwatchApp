package com.overwatch.kidouchi.overwatchapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ProfileDao extends BaseDao<ProfileEntity> {

    @Transaction
    @Query("SELECT * FROM profile")
    Single<List<ProfileEntity>> getAllProfiles();

    @Transaction
    @Query("SELECT * FROM profile WHERE username LIKE :username LIMIT 1")
    Maybe<ProfileEntity> getProfile(String username);

    @Transaction
    @Query("DELETE FROM profile")
    int deleteAllProfiles();
}
