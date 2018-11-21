package com.example.bats.homefoodie.ui.detail;

import android.arch.lifecycle.ViewModel;

import com.example.bats.homefoodie.HomefoodieRepository;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;

public class DishDetailFragmentViewModel extends ViewModel {

    //reference to the repository {@link HomefoodieRepository }
    private HomefoodieRepository mHomeFoodieRepository;

    // a dish for a user
    private DishEntry singleDishForUser;


    public DishDetailFragmentViewModel(HomefoodieRepository repository, int userID) {
        //the repository instance.
        mHomeFoodieRepository = repository;
        //all of my dishes.
        singleDishForUser = mHomeFoodieRepository.getDishForUser(userID);
    }


    //getter
    public DishEntry getSingleDishForUser() {
        return singleDishForUser;
    }


}
