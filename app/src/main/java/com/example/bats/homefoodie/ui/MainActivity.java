package com.example.bats.homefoodie.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.ui.list.DishesAdaper;

public class MainActivity extends AppCompatActivity {

    private DishesAdaper mDishesAdaper;
    private RecyclerView mDishesRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private ProgressBar mLoadingIndicator;
    //private MainActivityViewModel mViewModel;


    UserDao userDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        mDishesRecyclerView = findViewById(R.id.mainactiviy_recyclerview);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        mDishesRecyclerView.setLayoutManager(layoutManager);
        mDishesRecyclerView.setHasFixedSize(true);


        mDishesAdaper = new DishesAdaper(this);
        mDishesRecyclerView.setAdapter(mDishesAdaper);

    }


}















/*
        Executor executor = new Executor() {
            @Override
            public void execute(@NonNull Runnable runnable) {

                HomeFoodieDatabase.getInstance(context).userDao()
                        .insertUser(
                                new UserEntry( "batu", "thebatu@gmail.com", "road to fame", true, "Dest Inc"));
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
                                        new UserEntry( "batu", "thebatu@gmail.com", "road to fame", true, "Dest Inc"));


                         List entry = userDao.getAllUsers();
                        Log.d("test", "myList" + entry.toString());
                        Toast.makeText(context, entry.toString(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        thread.start();
*/
