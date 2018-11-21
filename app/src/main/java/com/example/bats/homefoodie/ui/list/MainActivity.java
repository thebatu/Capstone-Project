package com.example.bats.homefoodie.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishDao;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;
import com.example.bats.homefoodie.database.userDatabase.UserDao;
import com.example.bats.homefoodie.ui.MainViewModelFactory;
import com.example.bats.homefoodie.ui.detail.DishDetailFragment;
import com.example.bats.homefoodie.utilities.InjectorUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * MainActivity that displays dishes and handles clicks on dishes.
 */
public class MainActivity extends AppCompatActivity implements DishesAdapter.OnItemClickListener  {

    //mainActivity viewModel
    DishesViewModel mDishesViewModel;

    //adapter related declarations
    private DishesAdapter mDishesAdapter;
    private RecyclerView mDishesRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    //progressbar indicator
    private ProgressBar mLoadingIndicator;

    //private MainActivityViewModel mViewModel;
    UserDao userDao;
    DishDao dishDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        mDishesRecyclerView = findViewById(R.id.mainactiviy_recyclerview);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mDishesRecyclerView.setLayoutManager(layoutManager);
        mDishesRecyclerView.setHasFixedSize(true);

        mDishesAdapter = new DishesAdapter(this, this);
        mDishesRecyclerView.setAdapter(mDishesAdapter);

        MainViewModelFactory factory = InjectorUtils.provideDishesViewModelFactory(this
                .getApplicationContext());
        mDishesViewModel = ViewModelProviders.of(this, factory).get(DishesViewModel.class);
        mDishesViewModel.getAllDishes().observe(this, new Observer<List<DishWithIngredients>>()
        {
            @Override
            public void onChanged(@Nullable List<DishWithIngredients> dishWithIngredients) {
                if (dishWithIngredients == null) {
                    showLoading();
                }else {
                    showMainDishDataView();
                    mDishesAdapter.swapDishes(dishWithIngredients);
                }
            }

        });
    }


    /**
     * This method will make the View for the Dishes list data visible and hide the error message
     * and
     * loading indicator.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
     * each view is currently visible or invisible.
     */
    private void showMainDishDataView() {
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, make sure the dishes list data is visible
        mDishesRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the loading indicator visible and hide the weather View and error
     * message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
     * each view is currently visible or invisible.
     */
    private void showLoading() {
        // Then, hide the dishes list data
        mDishesRecyclerView.setVisibility(View.INVISIBLE);
        // Finally, show the loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    /**
     * Callback for clicks on a dish, the interface is declared in the adapter.
     * @param userID id of the user who owns the dish.
     * @param position position of the dish returned from the adapter.
     */
    @Override
    public void onItemClick(int userID, int position) {
        Toast.makeText(context, "Clicked on item " + position + "  " + userID, Toast.LENGTH_LONG).show();

        //pass the ID of the dish to fragment
        Bundle bundle = new Bundle();
        bundle.putInt("userID", userID);

        //create details screen upon click on a dish
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DishDetailFragment newFragment = new DishDetailFragment();
        newFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.container, newFragment).setCustomAnimations
                (android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(null)
                .commit();


    }

}
