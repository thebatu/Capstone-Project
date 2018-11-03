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
public interface IngredientDao {
    
    /**
     * Selects a dish form the database
     * @param dishId id of the dish
     * @return a dish
     */
    @Query("SELECT * FROM ingredient WHERE dishId == :dishId")
    LiveData<List<Ingredient>> getIngredientsForDish(int dishId);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDish(Ingredient ingredient);


    @Query("DELETE FROM ingredient")
    void deleteRepo();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredient(Ingredient ingredient);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInset(Ingredient... ingredient);

}
