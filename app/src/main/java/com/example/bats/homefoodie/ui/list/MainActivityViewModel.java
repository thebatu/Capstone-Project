package com.example.bats.homefoodie.ui.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bats.homefoodie.database.dishDatabase.DishEntry;

import java.util.List;


/**
 * this class holds the data of the UI. it survive configuration changes and it is a replacement
 * for AsyncTaskLoaders. It holder a reference to the Repository {@link HomeFoodieRepository}
 * and it holdes all the data of the Ui (@link mAllDishes). it is only logical that here the
 * data of the UI should exist.
 */
public class DishesViewModel extends AndroidViewModel {

    private HomeFoodieRespository mHomeFoodieRespository;
    private LiveData<List<DishEntry>> mAllDishes;


    public DishesViewModel(Application application) {
        super(application);
        //the repository instance.
        mHomeFoodieRespository = new HomeFoodieRepository(application);
        //all of my dishes.
        mAllDishes = mHomeFoodieRespository.getAllDishes();

        /**getAllDishes
         * Insert a DishEntry to the database, local or distant using the repository.class
         * as a proxy
         */
        public void insert (DishEntry dish){
            mHomeFoodieRespository.insert(dish)
        } ;

        //getter
        public LiveData<List<DishEntry>> getAllDishes () {
            return mAllDishes;
        }
    }


}
