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

import java.util.HashMap;

public class RemoteDataSource {
    private static final String LOG_TAG = RemoteDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static RemoteDataSource sInstance;
    private Context mContext;

    // LiveData storing the latest downloaded dishes
    private LiveData<HashMap<String, DishEntry>> mDownloadedUserList2;
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
    private class Deserializer implements Function<DataSnapshot, HashMap<String, DishEntry>> {
        @Override
        public HashMap<String, DishEntry> apply(DataSnapshot dataSnapshot) {

                HashMap<String, DishEntry> dishes = new HashMap<>();

                 //Map<String, String> valueMap = (HashMap<String, String>) dataSnapshot.getValue();
                 for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    for (DataSnapshot childSnap2 : childSnap.getChildren()){

                        DishEntry dishEntry1 = childSnap2.getValue(DishEntry.class);
                        dishEntry1.setRemoteID(childSnap.getKey());
                        dishes.put(childSnap.getKey(), dishEntry1);

                        //Log.d(TAG, "apply: " );
                    }
                }
            return dishes;

        }
    }
    //getter
    public LiveData<HashMap<String, DishEntry>> getLatestUsers() {
        return mDownloadedUserList2;
    }






}
