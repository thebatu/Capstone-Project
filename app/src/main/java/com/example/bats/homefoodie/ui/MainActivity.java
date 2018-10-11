package com.example.bats.homefoodie.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.data.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.data.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.data.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.data.database.userDatabase.UserDao;
import com.example.bats.homefoodie.data.database.userDatabase.UserEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserDao userDao;
    DishDao dishDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    userDao = HomeFoodieDatabase.getInstance(context).userDao();


                        userDao.insertUser(
                                        new UserEntry( "batu", "thebatu@gmail.com", "road to fame", true, "Dest Inc"));

                        dishDao = HomeFoodieDatabase.getInstance(context).dishDao();

                        DishEntry dishEntry = new DishEntry();
                        dishEntry.setUserId(1);
                        dishEntry.setName("test dish");
                        dishEntry.setPrice(5);

                        dishDao.insertDish(dishEntry);

                        List userDishes = dishDao.getDishForUser(1);

                        Log.d("test", "myList" + userDishes.toString());
                        Toast.makeText(context, userDishes.toString(), Toast.LENGTH_LONG).show();


                        List entry = userDao.getAllUsers();
                        Log.d("test", "myList" + entry.toString());
                        Toast.makeText(context, entry.toString(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        thread.start();



    }


}
