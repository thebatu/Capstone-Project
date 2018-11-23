package com.example.bats.homefoodie.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.utilities.InjectorUtils;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DishDetailFragment extends Fragment {

    //id of selected dish
    private int userID;
    DishDetailFragmentViewModel dishDetailFragmentViewModel;
    //adapter related declarations
    //@BindView(R.id.detail_dish_recycler)
    RecyclerView mDetailRecyclerView;

    private DishDetailAdapter mDishDetailAdapter;
    private int mPosition = RecyclerView.NO_POSITION;
    //progressbar indicator
  //  @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_recycler, container, false);

        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getContext();

        assert getArguments() != null;
        userID = getArguments().getInt("userID");

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mDetailRecyclerView = view.findViewById(R.id.detail_dish_recycler);
        mDetailRecyclerView.setLayoutManager(layoutManager);
        mDetailRecyclerView.setHasFixedSize(true);

        mDishDetailAdapter = new DishDetailAdapter(mContext);
        mDetailRecyclerView.setAdapter(mDishDetailAdapter);


        //get the viewModel for dish detail
        DishDetailFragmentViewModelFactory factory = InjectorUtils.provideDishesFragmentViewModelFactory(getActivity(), userID);
        dishDetailFragmentViewModel = ViewModelProviders.of(this, factory).get(DishDetailFragmentViewModel.class);

        //get the dish for a user and send it to the adapter
        DishEntry dishEntry = dishDetailFragmentViewModel.getSingleDishForUser();
        if (dishEntry != null){
            mDishDetailAdapter.swapDishes(dishEntry);

        }


        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
    }



}
