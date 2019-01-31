package com.example.bats.homefoodie.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.ui.create_dish.AddDish;
import com.example.bats.homefoodie.ui.userAuth.UserAuthGoogle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {

    //current user
    FirebaseUser fbu;
    static final int SIGN_IN_USER = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // *** Enable local phone persistence ***
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //update fbu variable with the current user
        check_status();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("Dishes");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                Intent a = new Intent(BaseActivity.this, UserAuthGoogle.class);
                startActivity(a);
                return true;
            case R.id.add_dish:
                check_status();
                if (fbu != null) {
                    if (!fbu.isAnonymous()) {
                        Intent b = new Intent(BaseActivity.this, AddDish.class);
                        startActivity(b);
                        return true;
                    } else {
                        Intent aa = new Intent(BaseActivity.this, UserAuthGoogle.class);
                        startActivityForResult(aa, SIGN_IN_USER);
                    }
                } else {
                    Intent aa = new Intent(BaseActivity.this, UserAuthGoogle.class);
                    startActivityForResult(aa, SIGN_IN_USER);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //update fbu variable with the current user
    private void check_status() {
        fbu = FirebaseAuth.getInstance().getCurrentUser();

    }
}
