package com.example.bats.homefoodie;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;
import com.example.bats.homefoodie.database.dishDatabase.IngredientDao;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.network.HomeFoodieNetworkDataSource;

import java.util.List;

public class HomefoodieRepository {
    private static final String LOG_TAG = HomefoodieRepository.class.getSimpleName();

    //for Singleton instantiation
    private static final Object LOCK = new Object();
    private static HomefoodieRepository sInstance;
    private DishDao mDishDao;
    private UserDao mUserDao;
    private IngredientDao mIngredientDao;
    private HomeFoodieNetworkDataSource mHomeFoodieNetworkDataSource;
    private AppExecutors mExecutors;
    private boolean mInitialized = false;


    public HomefoodieRepository(final DishDao mDishDao, UserDao mUserDao, IngredientDao ingredientDAO,
                                HomeFoodieNetworkDataSource mHomeFoodieNetworkDataSource,
                                final AppExecutors mExecutors) {
        this.mDishDao = mDishDao;
        this.mUserDao = mUserDao;
        this.mIngredientDao = ingredientDAO;
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
                                                                 IngredientDao ingredientDAO,
                                                                 HomeFoodieNetworkDataSource homeFoodieNetworkDataSource,
                                                                 AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new HomefoodieRepository(dishDao, userDao, ingredientDAO,
                        homeFoodieNetworkDataSource, executors);
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

    public LiveData<List<DishWithIngredients>> getAllDishes() {
        return mDishDao.getAllDisheswithIngredients();
    }

    public DishEntry getDishForUser(int id){
        return mDishDao.getDishWithIngredient(id);
    }

    public void insert(DishEntry dish) {
        mDishDao.insertDish(dish);
    }

    public LiveData<List<Ingredient>> getIngredientForDish(DishEntry dish) {
       return mIngredientDao.getIngredientsForDish(dish.getId());
    }
}
