package com.example.bats.homefoodie.ui.create_dish;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.bats.homefoodie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDish extends AppCompatActivity {

    @BindView(R.id.parent_linear_layout2)
    LinearLayout parentLayout;
    @BindView(R.id.activity_add_dish)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        ButterKnife.bind(this);
    }

    /**
     * remove add_ingredient_row from the stack of the containing layout
     * @param v view of the current activity
     */
    public void onDelete(View v) {
        if (parentLayout.getChildCount() > 0) {
            parentLayout.removeView((View) v.getParent());
        }
    }

    /**
     * method inflates a row of editText and an image wrapped in Constraints Layout.
     * and adds it before the add dish button
     * @param view the current activities view.
     */
    public void onAddIngredient(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row_add_ingredient, null);
        // Add the new row before the add dish button.
        parentLayout.addView(rowView, parentLayout.getChildCount() - 1);
        focusOnView();
    }

    /**
     * scroll smoothly to the button of the button add ingredient{@add_field_button}
     */
    private void focusOnView() {
        new Handler().post(() -> ObjectAnimator.ofInt(scrollView, "scrollY",
                scrollView.getBottom()).setDuration(700).start());
    }


}
