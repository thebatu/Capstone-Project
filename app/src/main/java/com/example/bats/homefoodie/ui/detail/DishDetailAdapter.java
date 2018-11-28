package com.example.bats.homefoodie.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * adapter to display a mDish with it's ingredients
 */
public class DishDetailAdapter extends RecyclerView.Adapter<DishDetailAdapter.DishesAdapterViewHolder>
            implements DishSwapper
{

    private DishEntry mDish;
    Context mContext;

    public DishDetailAdapter(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public DishesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutId = (R.layout.detail_ingredient_list);
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        //ButterKnife.bind(this, view);
        return new DishesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesAdapterViewHolder holder, int position) {
        List<Ingredient> ingredient =  mDish.getIngredientList();

        holder.tv_ingredients_list.setText(ingredient.get(position).getName());
        //holder.dish_name.setText(ingredient.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDish == null ? 0 : mDish.getIngredientList().size();
    }

    public void swapDishes(DishEntry dishEntry){

        mDish = dishEntry;
        notifyDataSetChanged();
    }


    public class DishesAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredients_list)
        TextView tv_ingredients_list;

        public DishesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }












}
