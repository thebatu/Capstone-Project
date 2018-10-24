package com.example.bats.homefoodie.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.bats.homefoodie.database.dishDatabase.DishEntry;

import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishesAdapterViewHolder> {

    private final Context mContext;
    private List<DishEntry> mDishes;

    private final DishesAdapterOnItemClickHandler mClickHandler;

    public DishesAdapter(Context context, DishesAdapterOnItemClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public DishesAdapter.DishesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull DishesAdapter.DishesAdapterViewHolder dishesAdapterViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void swapDishes(List<DishEntry> dishEntries) {
        //if there was no dish data, then recreate all of the list
        if (mDishes == null) {
            mDishes = dishEntries;
            notifyDataSetChanged();
        }
    }


    public interface DishesAdapterOnItemClickHandler {
        void onClick();
    }


    public class DishesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public DishesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
