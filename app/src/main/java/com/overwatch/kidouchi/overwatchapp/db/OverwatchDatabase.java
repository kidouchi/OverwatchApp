package com.overwatch.kidouchi.overwatchapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.overwatch.kidouchi.overwatchapp.db.dao.BaseDao;
import com.overwatch.kidouchi.overwatchapp.db.dao.ProfileDao;
import com.overwatch.kidouchi.overwatchapp.db.entities.ProfileEntity;

@Database(entities = {ProfileEntity.class}, version = 1)
public abstract class OverwatchDatabase extends RoomDatabase {

    public abstract ProfileDao profileDao();

    private static volatile OverwatchDatabase INSTANCE;

    public static OverwatchDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OverwatchDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), OverwatchDatabase.class, "overwatch_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
