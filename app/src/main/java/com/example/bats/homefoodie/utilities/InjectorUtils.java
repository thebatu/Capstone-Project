package com.example.bats.homefoodie.utilities;

import android.content.Context;

import com.example.bats.homefoodie.AppExecutors;
import com.example.bats.homefoodie.HomefoodieRepository;
import com.example.bats.homefoodie.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.network.HomeFoodieNetworkDataSource;
import com.example.bats.homefoodie.ui.MainViewModelFactory;
import com.example.bats.homefoodie.ui.detail.DishDetailFragmentViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for HomeFoodie
 */
public class InjectorUtils {

    public static HomefoodieRepository provideRepository(Context context) {

        HomeFoodieDatabase database = HomeFoodieDatabase.getInstance(context
                .getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();

        HomeFoodieNetworkDataSource networkDataSource =
                HomeFoodieNetworkDataSource.getInstance(context.getApplicationContext(), executors);

        return HomefoodieRepository.getsInstance(database.dishDao(), database.userDao(),
                database.ingredientDao(), networkDataSource, executors);
    }

    public static HomeFoodieNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return HomeFoodieNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static MainViewModelFactory provideDishesViewModelFactory(Context context) {
        HomefoodieRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

    public static DishDetailFragmentViewModelFactory provideDishesFragmentViewModelFactory(Context context, int userID) {
        HomefoodieRepository repository = provideRepository(context.getApplicationContext());
        return new DishDetailFragmentViewModelFactory(repository, userID);
    }


}
