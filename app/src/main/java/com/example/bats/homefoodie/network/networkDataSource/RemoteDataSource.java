package com.example.bats.homefoodie.network.networkDataSource;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.util.Log;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.userDatabase.UserEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoteDataSource {
    private static final String LOG_TAG = RemoteDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static RemoteDataSource sInstance;
    private Context mContext;

    // LiveData storing the latest downloaded dishes
    private LiveData<UserEntry> mDownloadedUserList2;
    private AppExecutors mExecutors;
    private final DatabaseReference HOT_STOCK_REF;


    public RemoteDataSource(Context mContext, AppExecutors appExecutors) {
        this.mContext = mContext;
        mExecutors = appExecutors;
        //firebase listener to /users

        HOT_STOCK_REF =  FirebaseDatabase.getInstance().getReference(mContext.getString(R.string.users));
        FirebaseQueryLiveDataService liveData = new FirebaseQueryLiveDataService(HOT_STOCK_REF);
        mDownloadedUserList2 =
                Transformations.map(liveData, new Deserializer());
    }

    /**
     * Get the singleton for this class
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


    private class Deserializer implements Function<DataSnapshot, UserEntry> {
        @Override
        public UserEntry apply(DataSnapshot dataSnapshot) {

            return dataSnapshot.getValue(UserEntry.class);
            //return dataSnapshot.getValue(DishWithIngredients.class);
        }
    }

    public LiveData<UserEntry> getLatestUsers() {
        return mDownloadedUserList2;
    }






}
