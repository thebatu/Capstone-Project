package com.example.bats.homefoodie;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bats.homefoodie.database.HomeFoodieDatabase;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.database.userDatabase.UserEntry;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    UserDao userDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;


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



    }


}
