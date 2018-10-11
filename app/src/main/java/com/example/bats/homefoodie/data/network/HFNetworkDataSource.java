package com.example.bats.homefoodie.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.data.HFRepository;
import com.example.bats.homefoodie.data.database.userDatabase.UserEntry;

public class HFNetworkDataSource {
    private static final String LOG_TAG = HFNetworkDataSource.class.getSimpleName();



    // For Singelton instantiation
    private static final Object LOCK = new Object();
    private static HFNetworkDataSource sInstance;
    private Context mContext;

    //LiveData storing the latest downloaded user dishes
    private static MutableLiveData<UserEntry[]> mDownloadedDishes;
    private final AppExecutors mExecutors;


    private HFNetworkDataSource(Context context, AppExecutors executors){
        this.mContext = context;
        this.mExecutors = executors;
        mDownloadedDishes = new MutableLiveData<UserEntry[]>();
    }


    public static HFNetworkDataSource getInstance(Context context, AppExecutors executors){
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new HFNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public static LiveData<UserEntry[]> getDownloadedDishes() {
        return mDownloadedDishes;
    }



    /**
     * Starts an intent service to fetch the weather.
     */
    //TODO
    public void startFetchHFService(){

    }

    /**
     * Schedules a repeating job service which fetches the dishes.
     */
    //TODO
    public void scheduleRecurringFetchWeatherSync() {

    }


    /**
     * Gets the newest weather
     */
    //TODO
    void fetchDishes(){

    }









}
