package com.example.bats.homefoodie.database.dishDatabase;

import android.support.v7.app.AppCompatActivity;

import com.example.bats.homefoodie.database.HomeFoodieDatabase;

public class DatabaseHelper extends AppCompatActivity {

    private DishDao dishDao = HomeFoodieDatabase.getInstance(getApplicationContext()).dishDao();
    private IngredientDao ingredientDao = HomeFoodieDatabase.getInstance(getApplicationContext()).ingredientDao();




}
