package com.example.bats.homefoodie;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.userDatabase.UserDao;

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
                sInstance = new HomefoodieRepository( dishDao, userDao, homeFoodieNetworkDataSource
                executors);
                Log.d(LOG_TAG, "Made new repository");

            }
        }
        return sInstance;
    }


}
