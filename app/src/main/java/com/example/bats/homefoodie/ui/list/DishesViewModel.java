package com.example.bats.homefoodie.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bats.homefoodie.HomefoodieRepository;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;

import java.util.List;


/**
 * this class holds the data of the UI. it survive configuration changes and it is a replacement
 * for AsyncTaskLoaders. It holder a reference to the Repository {@link HomefoodieRepository}
 * and it holdes all the data of the Ui (@link mAllDishes). it is only logical that here the
 * data of the UI should exist.
 */
public class DishesViewModel extends ViewModel {

    //reference to the repository {@link HomefoodieRepository }
    private HomefoodieRepository mHomeFoodieRepository;

    // List of dishes which will be populated from the repository in the constructor of this call
    private LiveData<List<DishEntry>> mAllDishes;

    //Constructor
    public DishesViewModel(HomefoodieRepository repository) {
        //the repository instance.
        mHomeFoodieRepository = repository;
        //all of my dishes.
        mAllDishes = mHomeFoodieRepository.getAllDishes();
    }

    /**
     * Insert a DishEntry to the database, local or distant using the repository.class
     * as a proxy
     */
    public void insert (DishEntry dish){
        mHomeFoodieRepository.insert(dish);
    }

    //getter for all the dishes from the repository
    public LiveData<List<DishEntry>> getAllDishes() {
        return mAllDishes;
    }
}
