package com.example.bats.homefoodie.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.ui.MainViewModelFactory;
import com.example.bats.homefoodie.ui.detail.DishDetailFragment;
import com.example.bats.homefoodie.utilities.InjectorUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity that displays dishes and handles clicks on dishes.
 */
public class MainActivity extends AppCompatActivity implements DishesAdapter.OnItemClickListener  {


    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("Users/Joe");

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

    //LinearLayoutManager linearLayoutManager;
    Context context;

    private  final  String FragmentTAG = "1";

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

        MainViewModelFactory factory = InjectorUtils.provideDishesViewModelFactory(this
                .getApplicationContext());
        mDishesViewModel = ViewModelProviders.of(this, factory).get(DishesViewModel.class);
        mDishesViewModel.getAllDishes().observe(this, dishWithIngredients -> {
            if (dishWithIngredients == null) {
                showLoading();
            }else {
                showMainDishDataView();
                mDishesAdapter.swapDishes(dishWithIngredients);
            }
        });

        insertUser();
    }

    private void insertUser() {
        String user_name = "bats";
        String user_age = "38";

        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put("USER_NAME", user_name);
        dataToSave.put("USER_AGE", user_age);
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAGTAG", "Document has been saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAGTAG", "Failed to save docuement");
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

        fragmentTransaction.setCustomAnimations
                (R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,
                        R.anim.exit_to_right).add(R.id.container, newFragment, FragmentTAG)
                .addToBackStack(null)
                .commit();

    }

}
