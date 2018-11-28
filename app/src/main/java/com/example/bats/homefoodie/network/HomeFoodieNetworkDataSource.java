package com.example.bats.homefoodie.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;
import com.example.bats.homefoodie.database.dishDatabase.IngredientDao;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.database.userDatabase.UserEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides an API for doing all operations with the server data
 */
public class HomeFoodieNetworkDataSource {

    private static final String LOG_TAG = HomeFoodieNetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static HomeFoodieNetworkDataSource sInstance;
    private Context mContext;

    // LiveData storing the latest downloaded dishes
    private MutableLiveData<DishEntry[]> mDownloadedDishesList;
    private AppExecutors mExecutors;

    public HomeFoodieNetworkDataSource(Context mContext, AppExecutors appExecutors) {
        this.mContext = mContext;
        mExecutors = appExecutors;
        mDownloadedDishesList = new MutableLiveData<DishEntry[]>();
    }


    /**
     * Get the singleton for this class
     */
    public static HomeFoodieNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = new HomeFoodieNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "made new network data source");
            }
        }
        return sInstance;
    }

    public void fetchData(){
        //need to create from zero. this is temporary code
        UserDao userDao = HomeFoodieDatabase.getInstance(mContext).userDao();

        //create user
        userDao.insertUser(
                new UserEntry( "batu", "thebatu@gmail.com", "road tofame", true, "Dest Inc"));



        DishEntry dishEntry = new DishEntry(1, "fish&chips", 6, "best fish and chips in the world made with super care", "Mama's kitchen");
        DishEntry dishEntry2 = new DishEntry(1, "pizza", 10, "the pizza that rocks the city of Gotham", "uncles ben's kitchen" );
        DishEntry dishEntry3 = new DishEntry(1, "hamburger", 11, "hamburger made out of love sauced with dead fingers", "hamburger factory");
        DishEntry dishEntry4 = new DishEntry(1, "chicken wrap", 2212, "this chicken is definitely dead and it is tasty ", "chicken micken");


        DishDao dishDao = HomeFoodieDatabase.getInstance(mContext).dishDao();
        //insert dish
        dishDao.insertDish( dishEntry);
        dishDao.insertDish( dishEntry2);
        dishDao.insertDish( dishEntry3);
        dishDao.insertDish( dishEntry4);


        //dishDao.insertIngredientsList(dishIngredients);

//        IngredientDao ingredientDao = HomeFoodieDatabase.getInstance(mContext).ingredientDao();
//        ingredientDao.bulkInset( new Ingredient(1, "brown rice", "7 cups")
//        , new Ingredient(1, "red rice", "100 cups"));

        List<DishEntry> dd = dishDao.getDish(1);

        ArrayList tt = new ArrayList();
        tt.add(new Ingredient(dd.get(0).getId(), "brown rice", "7 cups"));
        tt.add(new Ingredient(dd.get(1).getId(), "red rice", "300 cups"));
        tt.add(new Ingredient(dd.get(2).getId(), "meat", "spoons"));
        tt.add(new Ingredient(dd.get(3).getId(), "chicken", "5 kilos"));
        Log.d("TAG2", "THE ID for dd 0  " + dd.get(0).getId());
        Log.d("TAG2", "THE ID for dd 1  " + dd.get(1).getId());

        dishDao.insertIngredientsForDish(dd.get(0),tt);
        dishDao.insertIngredientsForDish(dd.get(1),tt);
        dishDao.insertIngredientsForDish(dd.get(2),tt);
        dishDao.insertIngredientsForDish(dd.get(3),tt);

//        IngredientDao ingredientDao =  HomeFoodieDatabase.getInstance(mContext).ingredientDao();
//        ingredientDao.insertIngredientsForDish(dishEntry2,tt);

        //LiveData<List<DishWithIngredients>> list2 = dishDao.getAllDisheswithIngredients();

        Toast.makeText(mContext, "inserted a dish" , Toast.LENGTH_LONG).show();

        //LiveData<List<UserEntry> > bbb= userDao.getAllUsers();

    }

    public LiveData<DishEntry[]> getLatestDishes() {
        fetchData();
        return mDownloadedDishesList;

    }





}
