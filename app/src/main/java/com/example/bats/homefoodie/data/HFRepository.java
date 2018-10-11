package com.example.bats.homefoodie.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.data.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.data.database.userDatabase.UserDao;
import com.example.bats.homefoodie.data.database.userDatabase.UserEntry;
import com.example.bats.homefoodie.data.network.HFNetworkDataSource;

/**
 * Handles data operations in HomeFoodie. Acts as a mediator between {@link com.example.bats.homefoodie.data.network.HFNetworkDataSource}
 * and {@link @UserDao}
 */
public class HFRepository {
    private static final String LOG_TAG = HFRepository.class.getSimpleName();

    // For Singelton instantiation
    private static final Object LOCK = new Object();
    private static HFRepository sInstance;
    private Context mContext;
    UserDao mUserDao;
    DishDao mDishDao;
    private final HFNetworkDataSource mHFNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;



    private HFRepository(UserDao userDao, DishDao dishDao, HFNetworkDataSource hfNetworkDataSource,
                         AppExecutors appExecutors){

        mUserDao = userDao;
        mDishDao = dishDao;
        mHFNetworkDataSource = hfNetworkDataSource;
        mExecutors = appExecutors;

        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        LiveData<UserEntry[]> networkData = HFNetworkDataSource.getDownloadedDishes();
        networkData.observeForever(newDishesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mUserDao.bulkInsert(newDishesFromNetwork);
                Log.d(LOG_TAG, "New Dishes inserted from network");
            });
        });
    }

    /**
     * Get the Singelton for this class
     */



    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    private synchronized void initializeData(){

    }






}
