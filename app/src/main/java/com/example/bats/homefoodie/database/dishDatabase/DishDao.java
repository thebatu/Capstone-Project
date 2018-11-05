package com.example.bats.homefoodie.database.dishDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.bats.homefoodie.database.HomeFoodieDatabase;

import java.util.List;

/**
 * {@link Dao} which provides an api for all data operations with the {@link HomeFoodieDatabase}
 */
@Dao
public interface DishDao {

    /**
     * Selects all dishes. the LiveData will be kept in sync with the database.
     * so that it will automatically notify observers when the
     * * values in the table change.
     * @return users
     */
    @Query("SELECT * FROM dish ")
    LiveData<List<DishModelWithIngredients>> getAllDisheswithIngredients();

    /**
     * Selects a dish form the database
     * @param userId id of the user to get a dish for
     * @return a dish
     */
    @Query("SELECT * FROM dish WHERE userId == :userId")
    LiveData<List<DishEntry>> getDishForUser(int userId);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDish(DishEntry dish);


    @Query("DELETE FROM dish")
    void deleteRepo();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDish(DishEntry dish);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInset(DishEntry... dish);

    @Query("INSERT into ingredient where ingredient.dishID = dishEntry")
    void insertIngredient(DishEntry dishEntry, DishModelWithIngredients dishIngredients);




}
