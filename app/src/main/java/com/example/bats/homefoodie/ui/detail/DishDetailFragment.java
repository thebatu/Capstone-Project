package com.example.bats.homefoodie.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.ui.MainViewModelFactory;
import com.example.bats.homefoodie.ui.list.DishesViewModel;
import com.example.bats.homefoodie.utilities.InjectorUtils;

import java.util.List;
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
    @BindView(R.id.detail_dish_name)
    TextView dish_name;
    @BindView(R.id.detail_dish_price)
    TextView dish_price;
    @BindView(R.id.detail_kitchen_name)
    TextView kitchen_name;
    @BindView(R.id.detail_dish_description)
    TextView dish_description;
    @BindView(R.id.the_dish_name)
    TextView the_dish_name;
    @BindView(R.id.the_kitchen_name)
    TextView the_kitchen_name;

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

        assert getArguments() != null;
        String remoteDishId = getArguments().getString("remoteDishId");

        //check device orientation
        int orientation = getResources().getConfiguration().orientation;

        //if horizontal
        if (orientation == 2) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mDetailRecyclerView.setLayoutManager(gridLayoutManager);
        }

        //if vertical
        if (orientation == 1) {
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mDetailRecyclerView.setLayoutManager(layoutManager);
        }

        mDetailRecyclerView.setHasFixedSize(true);
        mDishDetailAdapter = new DishDetailAdapter(getContext());
        mDetailRecyclerView.setAdapter(mDishDetailAdapter);


        MainViewModelFactory factory = InjectorUtils.provideDishesViewModelFactory(Objects
                .requireNonNull(this
                        .getActivity()));
        //get the dishesViewModel
        DishesViewModel mDishesViewModel = ViewModelProviders.of(this, factory).get
                (DishesViewModel.class);
        //get the viewModel of the mainActivity which contains a list of dishes. find the
        // corresponding dish to ID and display it
        LiveData<List<DishEntry>> aDish = mDishesViewModel.getSingleDish(remoteDishId);
        //observe the livedata of a single clicked on dish
        aDish.observe(getActivity(), dish -> {
            if (dish != null) {
                showMainDishDataView();
                dish_name.setText(dish.get(0).getName());
                dish_price.setText(String.valueOf(dish.get(0).getPrice()));
                dish_description.setText(dish.get(0).getDescription());
                kitchen_name.setText(dish.get(0).getKitchen_name());
                mDishDetailAdapter.swapDishes(dish.get(0));
                the_dish_name.setText("Dish name: ");
                the_kitchen_name.setText("Kitchen name: ");
            } else {
                showLoading();
            }

        });

        Log.d("tag", "kjhkh");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbind binding library
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
