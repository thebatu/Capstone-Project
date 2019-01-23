package com.example.bats.homefoodie.ui.create_dish;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.bats.homefoodie.R;

public class AddDish extends AppCompatActivity {

    private LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);



        parentLayout = (LinearLayout) findViewById(R.id.parent_linear_layout2);

    }


    public void onDelete(View v) {
        parentLayout.removeView((View) v.getParent());
    }

    public void onAddField(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLayout.addView(rowView, parentLayout.getChildCount() -1 );
    }



}
