package com.example.bats.homefoodie.ui.detail;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.utilities.InjectorUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DishDetailFragment extends Fragment {

    //id of selected dish
    private int userID;
    DishDetailFragmentViewModel dishDetailFragmentViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert getArguments() != null;
        userID = getArguments().getInt("userID");

        DishDetailFragmentViewModelFactory factory = InjectorUtils.provideDishesFragmentViewModelFactory(getActivity(), userID);
        dishDetailFragmentViewModel = ViewModelProviders.of(this, factory).get(DishDetailFragmentViewModel.class);
        DishEntry dishEntry = dishDetailFragmentViewModel.getSingleDishForUser();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_detail, container, false);
    }

}
