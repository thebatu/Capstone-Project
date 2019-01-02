package com.example.bats.homefoodie;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;
import com.example.bats.homefoodie.database.dishDatabase.IngredientDao;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.network.localStorage.LocalDataSource;
import com.example.bats.homefoodie.network.networkDataSource.RemoteDataSource;

import java.util.HashMap;
import java.util.List;

public class HomefoodieRepository {
    private static final String LOG_TAG = HomefoodieRepository.class.getSimpleName();

    //for Singleton instantiation
    private static final Object LOCK = new Object();
    private static HomefoodieRepository sInstance;
    private DishDao mDishDao;
    private UserDao mUserDao;
    private IngredientDao mIngredientDao;
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;
    private AppExecutors mExecutors;
    private boolean mInitialized = false;


    public HomefoodieRepository(final DishDao mDishDao, UserDao mUserDao, IngredientDao ingredientDAO,
                                LocalDataSource mLocalDataSource, RemoteDataSource mRemoteDataSource,
                                final AppExecutors mExecutors) {
        this.mDishDao = mDishDao;
        this.mUserDao = mUserDao;
        this.mLocalDataSource = mLocalDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
        this.mExecutors = mExecutors;

//        MutableLiveData<UserEntry> firebaseNetworkUserEntry = mRemoteDataSource.getLatestUsers();
//        firebaseNetworkUserEntry.observeForever(userEntry -> {
//            mExecutors.networkIO().execute(() -> {
//
//            });
//
//        });


//        LiveData<DishEntry[]> local_data = mLocalDataSource.getLatestDishes();
//        local_data.observeForever(newDishesListFromNetwork -> {
//            mExecutors.diskIO().execute(() -> {
//                deleteOldData();
//                Log.d(LOG_TAG, "Deleted old dishes list");
//                mDishDao.bulkInset(newDishesListFromNetwork);
//                Log.d(LOG_TAG, "New dishes igetLatestUsersnserted");
//            });
//        });
    }

    public synchronized static HomefoodieRepository getsInstance(DishDao dishDao, UserDao userDao,
                                                                 IngredientDao ingredientDAO,
                                                                 LocalDataSource localDataSource,
                                                                 RemoteDataSource remoteDataSource,
                                                                 AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new HomefoodieRepository(dishDao, userDao, ingredientDAO,
                        localDataSource, remoteDataSource,executors);
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

    public LiveData<HashMap<String,DishEntry>> getUserEntryList(){
        return mRemoteDataSource.getLatestUsers();
    }





}
