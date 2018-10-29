package com.example.bats.homefoodie.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.database.userDatabase.UserEntry;
import com.example.bats.homefoodie.ui.MainViewModelFactory;
import com.example.bats.homefoodie.utilities.InjectorUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    DishesViewModel mDishesViewModel;

    //adapter related declarations
    private DishesAdapter mDishesAdapter;
    private RecyclerView mDishesRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    private ProgressBar mLoadingIndicator;

    //private MainActivityViewModel mViewModel;
    UserDao userDao;
    DishDao dishDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        mDishesRecyclerView = findViewById(R.id.mainactiviy_recyclerview);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mDishesRecyclerView.setLayoutManager(layoutManager);
        mDishesRecyclerView.setHasFixedSize(true);

        mDishesAdapter = new DishesAdapter(this);
        mDishesRecyclerView.setAdapter(mDishesAdapter);

        MainViewModelFactory factory = InjectorUtils.provideDishesViewModelFactory(this
                .getApplicationContext());
        mDishesViewModel = ViewModelProviders.of(this, factory).get(DishesViewModel.class);

        mDishesViewModel.getAllDishes().observe(this, dishEateries -> {
            mDishesAdapter.swapDishes(dishEateries);
            if (mPosition == RecyclerView.NO_POSITION) {
                mPosition = 0;
            }
            mDishesRecyclerView.smoothScrollToPosition(mPosition);

            // Show all Dishes list or the loading screen based on whether the dishes data exists
            // and is loaded
            if (dishEateries != null && dishEateries.size() != 0) showMainDishDataView();
            else showLoading();

        });

    }


    /**
     * This method will make the View for the Dishes list data visible and hide the error message
     * and
     * loading indicator.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
     * each view is currently visible or invisible.
     */
    private void showMainDishDataView() {
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, make sure the dishes list data is visible
        mDishesRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the loading indicator visible and hide the weather View and error
     * message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
     * each view is currently visible or invisible.
     */
    private void showLoading() {
        // Then, hide the dishes list data
        mDishesRecyclerView.setVisibility(View.INVISIBLE);
        // Finally, show the loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dishDao = HomeFoodieDatabase.getInstance(context).dishDao();

                    LiveData<List<UserEntry> > bbb= userDao.getAllUsers();;

                    Log.d("test", "myList" + bbb.toString());

                    Toast.makeText(context, bbb.toString(), Toast.LENGTH_LONG).show();

                }
            });
            thread.start();

        }
    });



    /**
     * handles clicks on an item. clicks are sent from the dishes adapter.
     */
//    @Override
//    public void onClick(int clickedOnPos, DishEntry dish) {
//        Toast.makeText(this, "Clicked on a dish", Toast.LENGTH_LONG).show();
//
//    }
}





/*
        Executor executor = new Executor() {
            @Override
            public void execute(@NonNull Runnable runnable) {

                HomeFoodieDatabase.getInstance(context).userDao()
                        .insertUser(
                                new UserEntry( "batu", "thebatu@gmail.com", "road to fame", true,
                                 "Dest Inc"));
            }

        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    userDao = HomeFoodieDatabase.getInstance(context).userDao();


                        userDao.insertUser(
                                        new UserEntry( "batu", "thebatu@gmail.com", "road to
                                        fame", true, "Dest Inc"));


                         List entry = userDao.getAllUsers();
                        Log.d("test", "myList" + entry.toString());
                        Toast.makeText(context, entry.toString(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        thread.start();
*/
