package com.example.bats.homefoodie.network.networkDataSource;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.util.Log;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteDataSource {
    private static final String LOG_TAG = RemoteDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static RemoteDataSource sInstance;
    private Context mContext;

    // LiveData storing the latest downloaded dishes
    private LiveData<List<DishEntry>> mDownloadedUserList2;
    private AppExecutors mExecutors;
    private final DatabaseReference HOT_STOCK_REF;


    // constructor, gets the instance of A REPO from FireBase converts livedata to userEntry objects
    public RemoteDataSource(Context mContext, AppExecutors appExecutors) {
        this.mContext = mContext;
        mExecutors = appExecutors;
        //firebase listener to /users

        HOT_STOCK_REF =  FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.Dishes));
        FirebaseQueryLiveDataService liveData = new FirebaseQueryLiveDataService(HOT_STOCK_REF);
        mDownloadedUserList2 =
                Transformations.map(liveData, new Deserializer());
    }

    /**
     * return the singleton for this class
     */
    public static RemoteDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = new RemoteDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "made new network data source");
            }
        }
        return sInstance;
    }

    /**
     * Deserialize using Livedata to UserEntry objects.
     */
    private class Deserializer implements Function<DataSnapshot, List<DishEntry>> {
        @Override
        public List<DishEntry> apply(DataSnapshot dataSnapshot) {

                List<DishEntry> dishes = new ArrayList<>();

                 Map<String, String> valueMap = (HashMap<String, String>) dataSnapshot.getValue();
                 for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    for (DataSnapshot chidSnap2 : chidSnap.getChildren()){

                        DishEntry dishEntry1 = chidSnap2.getValue(DishEntry.class);
                        dishes.add(dishEntry1);

                        //Log.d(TAG, "apply: " );
                    }
                }
            return dishes;

        }
    }
    //getter
    public LiveData<List<DishEntry>> getLatestUsers() {
        return mDownloadedUserList2;
    }






}
