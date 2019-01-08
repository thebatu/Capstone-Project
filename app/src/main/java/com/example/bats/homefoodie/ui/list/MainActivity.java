package com.example.bats.homefoodie.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;
import com.example.bats.homefoodie.network.FirebaseAuthClass.FirebaseAuthViewModel;
import com.example.bats.homefoodie.ui.BaseActivity;
import com.example.bats.homefoodie.ui.MainViewModelFactory;
import com.example.bats.homefoodie.ui.detail.DishDetailFragment;
import com.example.bats.homefoodie.utilities.InjectorUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity that displays dishes and handles clicks on dishes.
 */
public class MainActivity extends BaseActivity implements DishesAdapter.OnItemClickListener  {

    //mainActivity viewModel
    DishesViewModel mDishesViewModel;
    @BindView(R.id.mainactiviy_recyclerview)
    RecyclerView mDishesRecyclerView;

    //adapter related declarations
    private DishesAdapter mDishesAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    //progressbar indicator
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    Context context;
    private final  String FragmentTAG = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        //check device orientation
        int orientation = getResources().getConfiguration().orientation;
        //if horizontal
        if (orientation == 2) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
            mDishesRecyclerView.setLayoutManager(gridLayoutManager);
        }

        //if vertical
        if (orientation == 1){
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            mDishesRecyclerView.setLayoutManager(layoutManager);
        }

        mDishesRecyclerView.setHasFixedSize(true);
        mDishesAdapter = new DishesAdapter(this, this);
        mDishesRecyclerView.setAdapter(mDishesAdapter);

        DatabaseReference ref2 =
        FirebaseDatabase.getInstance().getReference("/dishes");

        //Check if user is signedIn. if not signIn user anonymously.
        FirebaseAuthViewModel firebaseAuthViewModel =
                ViewModelProviders.of(MainActivity.this).get(FirebaseAuthViewModel.class);

        firebaseAuthViewModel
                .getFirebaseAuthLiveData()
                .observe(this, firebaseUser -> {
                    Log.d("MyTag", "fbase  " + firebaseUser.getUid());
                });

//
//        DishEntry dishEntry = new DishEntry(firebaseAuthViewModel.getSimpleFirebaseUser(), "fish&chips", 6,
//                "best fish and chips in the world made with super care", "Mama's kitchen");
//
//        DishEntry dishEntry2 = new DishEntry(firebaseAuthViewModel.getSimpleFirebaseUser(), "pizza", 10, "the pizza that rocks the city of Gotham", "uncles ben's kitchen" );
//        DishEntry dishEntry3 = new DishEntry(firebaseAuthViewModel.getSimpleFirebaseUser(), "beef", 1000, "the beef of the beef", "beef kitchen" );
//
//
//        ArrayList tt = new ArrayList();
//        tt.add(new Ingredient(1, "brown rice", "7 cups"));
//        tt.add(new Ingredient(1, "red rice", "300 cups"));
//        tt.add(new Ingredient(1, "meat", "spoons"));
//        tt.add(new Ingredient(1, "fish", "1 big fish"));
//        tt.add(new Ingredient(1, "vigor", "1 cup"));
//        tt.add(new Ingredient(1, "seeds", "1 kilo"));
//        tt.add(new Ingredient(1, "meat", "5 kilos"));
//
//        dishEntry.setIngredientList(tt);
//        dishEntry2.setIngredientList(tt);
//        dishEntry3.setIngredientList(tt);
//
//        Map<String, DishEntry> dd = new HashMap<>();
//        Map<String, DishEntry> dd2 = new HashMap<>();
//
//        dd.put(firebaseAuthViewModel.getSimpleFirebaseUser(), dishEntry);
//        dd2.put(firebaseAuthViewModel.getSimpleFirebaseUser(), dishEntry3);
//        dd.put(firebaseAuthViewModel.getSimpleFirebaseUser(), dishEntry2);
//        ref2.push().setValue(dd);
//        ref2.push().setValue(dd2);
//

        //Factory to get the viewModel for dishes
        MainViewModelFactory factory = InjectorUtils.provideDishesViewModelFactory(this
                .getApplicationContext());
        mDishesViewModel = ViewModelProviders.of(this, factory).get(DishesViewModel.class);

        LiveData<HashMap<String, DishEntry>> hotStockLiveData = mDishesViewModel.getAllDishesLiveData();
        Log.d("HUM" , "GEE" );
        if (hotStockLiveData.getValue() == null){
            showLoading();
        }else {
            showMainDishDataView();
        }
        hotStockLiveData.observe(this, listOfDishes -> {
            if (listOfDishes == null) {
                showLoading();
            } else {
                showMainDishDataView();
                mDishesAdapter.swapDishes(listOfDishes);
            }

        });
    }

    /**
     * This method will make the View for the Dishes list data visible and hide the error message
     * and loading indicator.
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
     * @param ClickedOnForADishCallBack position of the dish returned from the adapter.
     */
    @Override
    public void onItemClick(String userID, String ClickedOnForADishCallBack) {
        //Toast.makeText(context, "Clicked on item " + position + "  " + userID, Toast.LENGTH_LONG).show();

        //pass the ID of the dish to fragment
        Bundle bundle = new Bundle();
        bundle.putString("remoteDishId", ClickedOnForADishCallBack);

        //create details screen upon click on a dish
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DishDetailFragment detailFragment = new DishDetailFragment();
        detailFragment.setArguments(bundle);

        fragmentTransaction.setCustomAnimations
                (R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,
                        R.anim.exit_to_right).add(R.id.container, detailFragment, FragmentTAG)
                .addToBackStack(null)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDishesRecyclerView.scrollToPosition(mPosition);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

    }
}
