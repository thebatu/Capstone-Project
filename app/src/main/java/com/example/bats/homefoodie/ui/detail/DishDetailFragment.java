package com.example.bats.homefoodie.ui.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bats.homefoodie.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DishDetailFragment extends Fragment {

    //id of selected dish
    private int currentDishId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert getArguments() != null;
        currentDishId = getArguments().getInt("dishID");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_detail, container, false);
    }

}
