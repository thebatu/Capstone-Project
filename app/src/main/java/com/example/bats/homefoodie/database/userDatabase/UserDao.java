package com.example.bats.homefoodie.database.userDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.bats.homefoodie.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.database.userDatabase.UserEntry;

import java.util.List;

 /**
  * {@link Dao} which provides an api for all data operations with the {@link HomeFoodieDatabase}
  */
 @Dao
public interface UserDao {


    /**
     * Selects all users. the LiveData will be kept in sync with the database.
     * so that it will automatically notify observers when the
     * * values in the table change.
     * @return users
     */
    @Query("SELECT * FROM user")
    LiveData<List<UserEntry>> getAllUsers();


    /**
     * Selects a user form the database
     * @param id id of the user to select
     * @return a user
     */
    @Query("SELECT * FROM user WHERE user.id == :id")
    LiveData<List<UserEntry>> getUser(int id);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(UserEntry user);


    @Delete
    void deleteUser(UserEntry user);


    @Insert
    void insertUser(UserEntry user);


}
