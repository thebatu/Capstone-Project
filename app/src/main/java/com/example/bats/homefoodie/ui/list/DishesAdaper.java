package com.example.bats.homefoodie.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class DishesAdaper extends RecyclerView.Adapter<DishesAdaper.DishesAdapterViewHolder> {

    private final Context mContext;


    public DishesAdaper(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public DishesAdaper.DishesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DishesAdaper.DishesAdapterViewHolder dishesAdapterViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }





    public class DishesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public DishesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }




        @Override
        public void onClick(View view) {

        }
    }
}
