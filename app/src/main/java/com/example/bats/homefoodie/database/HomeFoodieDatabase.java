package com.example.bats.homefoodie.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;


/**
 * {@link HomeFoodieDatabase} database for the application including a table for{@link UserEntry}
 * with the DAO {@link UserDao}.
 */
@Database(entities = {UserEntry.class}, version = 1)
public abstract class HomeFoodieDatabase extends RoomDatabase {

    private static final String LOG_TAG = HomeFoodieDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "user";

    //for Singelton instantiation
    private static final Object LOCK = new Object();
    private static HomeFoodieDatabase sInstance;

    public static HomeFoodieDatabase getsInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        HomeFoodieDatabase.class, HomeFoodieDatabase.DATABASE_NAME).build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }


    //the associated DAOs for the database
    public abstract UserDao userDao();


}
