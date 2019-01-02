package com.example.bats.homefoodie.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.bats.homefoodie.HomefoodieRepository;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class holds the data of the UI. it survive configuration changes and it is a replacement
 * for AsyncTaskLoaders. It holder a reference to the Repository {@link HomefoodieRepository}
 * and it holdes all the data of the Ui (@link mAllDishes). it is only logical that here the
 * data of the UI should exist.
 */
public class DishesViewModel extends ViewModel {

    private LiveData<List<Ingredient>> mIngredientList;


    //reference to the repository {@link HomefoodieRepository }
    private HomefoodieRepository mHomeFoodieRepository;
    // List of dishes which will be populated from the repository in the constructor of this call
    //private LiveData<List<DishWithIngredients>> mAllDishes;
    private LiveData<HashMap<String, DishEntry>> mAllDishes;

    //Constructor
    public DishesViewModel(HomefoodieRepository repository) {
        //the repository instance.
        mHomeFoodieRepository = repository;
        //all of my dishes.
        //mAllDishes = mHomeFoodieRepository.getAllDishes();
        mAllDishes = mHomeFoodieRepository.getUserEntryList();
    }

    /**
     * Insert a DishEntry to the database, local or distant using the repository.class
     * as a proxy
     */
    public void insert (DishEntry dish){
        mHomeFoodieRepository.insert(dish);
    }

    //getter for all the dishes from the repository
//    public LiveData<List<DishWithIngredients>> getAllDishes() {
//        return mAllDishes;
//    }


     LiveData<List<Ingredient>> ingredientsListforSingleDish = new LiveData<List<Ingredient>>() {};

    public List<Ingredient> getIngredientsListFor1dish(String remoteDishId) {


        ingredientsListforSingleDish = Transformations.map(mAllDishes, input -> {
            List<Ingredient> fff = new ArrayList<Ingredient>(){};

            for (Map.Entry<String, DishEntry> entry : input.entrySet()){
                fff = entry.getValue().getIngredientList();
            }
            return fff;

        });
        return (List<Ingredient>) ingredientsListforSingleDish;
    }



        @NonNull
    public LiveData<HashMap<String, DishEntry>> getAllDishesLiveData() {
        return mAllDishes;
    }

}
