package com.example.bats.homefoodie.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.utilities.InjectorUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DishDetailFragment extends Fragment {

    //id of selected dish
    private int userID;
    DishDetailFragmentViewModel dishDetailFragmentViewModel;
    //adapter related declarations
    @BindView(R.id.detail_dish_recycler)
    RecyclerView mDetailRecyclerView;
    private DishDetailAdapter mDishDetailAdapter;
    Unbinder unbinder;

    //progressbar indicator
   @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    Context mContext;

    @BindView(R.id.detail_dish_name)
    TextView dish_name;
    @BindView(R.id.detail_dish_price)
    TextView dish_price;
    @BindView(R.id.detail_kitchen_name)
    TextView kitchen_name;
    @BindView(R.id.detail_dish_description)
    TextView dish_description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getContext();

        assert getArguments() != null;
        userID = getArguments().getInt("userID");


        //check device orientation
        int orientation = getResources().getConfiguration().orientation;

        //if horizontal
        if (orientation == 2) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
            mDetailRecyclerView.setLayoutManager(gridLayoutManager);
        }

        //if vertical
        if (orientation == 1){
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
            mDetailRecyclerView.setLayoutManager(layoutManager);
        }



//        LinearLayoutManager layoutManager =
//                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        //setup recycler view and adapter
//        mDetailRecyclerView.setLayoutManager(layoutManager);
        mDetailRecyclerView.setHasFixedSize(true);
        mDishDetailAdapter = new DishDetailAdapter(mContext);
        mDetailRecyclerView.setAdapter(mDishDetailAdapter);

        //get the viewModel for dish detail
        DishDetailFragmentViewModelFactory factory = InjectorUtils.
                provideDishesFragmentViewModelFactory(Objects.requireNonNull(getActivity()), userID);
        dishDetailFragmentViewModel = ViewModelProviders.of(this, factory)
                .get(DishDetailFragmentViewModel.class);

        //get the dish for a user and send it to the adapter
        DishEntry dishEntry = dishDetailFragmentViewModel.getSingleDishForUser();
        if (dishEntry != null){
            showMainDishDataView();
            mDishDetailAdapter.swapDishes(dishEntry);
        }else {
            showLoading();
        }

        //assign values to views
        assert dishEntry != null;
        dish_name.setText(dishEntry.getName());
        dish_price.setText(String.valueOf(dishEntry.getPrice()));
        dish_description.setText(dishEntry.getDescription());
        kitchen_name.setText(dishEntry.getKitchen_name());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This method will make the View for the Dishes list data visible and hide the error message
     * and loading indicator.
     */
    private void showMainDishDataView() {
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, make sure the dishes list data is visible
        mDetailRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the loading indicator visible and hide the weather View and error
     * message.
     */
    private void showLoading() {
        // Then, hide the dishes list data
        mDetailRecyclerView.setVisibility(View.INVISIBLE);
        // Finally, show the loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }
}
