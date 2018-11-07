package com.example.bats.homefoodie.database.dishDatabase;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;


public class DishWithIngredients {

    @Embedded
    public DishEntry dishEntry;


    @Relation(parentColumn = "id", entityColumn = "dishId", entity = Ingredient.class)
    public List<Ingredient> ingredients;
}
