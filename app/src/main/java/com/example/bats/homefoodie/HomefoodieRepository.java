package com.example.bats.homefoodie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.network.HomeFoodieNetworkDataSource;

import java.util.Date;
import java.util.List;

public class HomefoodieRepository {
    private static final String LOG_TAG = HomefoodieRepository.class.getSimpleName();

    //for Singleton instantiation
    private static final Object LOCK = new Object();
    private static HomefoodieRepository sInstance;
    private DishDao mDishDao;
    private UserDao mUserDao;
    private HomeFoodieNetworkDataSource mHomeFoodieNetworkDataSource;
    private AppExecutors mExecutors;
    private boolean mInitialized = false;


    public HomefoodieRepository(final DishDao mDishDao, UserDao mUserDao,
                                HomeFoodieNetworkDataSource mHomeFoodieNetworkDataSource,
                                final AppExecutors mExecutors) {
        this.mDishDao = mDishDao;
        this.mUserDao = mUserDao;
        this.mHomeFoodieNetworkDataSource = mHomeFoodieNetworkDataSource;
        this.mExecutors = mExecutors;

        LiveData<DishEntry[]> networkData = mHomeFoodieNetworkDataSource.getLatestDishes();

        networkData.observeForever(newDishesListFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                deleteOldData();
                Log.d(LOG_TAG, "Deleted old dishes list");
                mDishDao.bulkInset(newDishesListFromNetwork);
                Log.d(LOG_TAG, "New dishes inserted");
            });
        });
    }

    public synchronized static HomefoodieRepository getsInstance(DishDao dishDao, UserDao userDao,
                                          HomeFoodieNetworkDataSource homeFoodieNetworkDataSource,
                                                                 AppExecutors executors ) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = new HomefoodieRepository( dishDao, userDao, homeFoodieNetworkDataSource,
                executors);
                Log.d(LOG_TAG, "Made new repository");

            }
        }
        return sInstance;
    }


     /**
     * Deletes old dishes data
     */
    private void deleteOldData() {
        mDishDao.deleteRepo();
    }


    public LiveData<List<DishEntry>> getAllDishes(){ return mDishDao.getAllDishes(); }

    public void insert(DishEntry dish){ mDishDao.insertDish(dish); }


}
