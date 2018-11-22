package com.example.bats.homefoodie.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;

import java.util.List;

/**
 * Exposes a list of dishes from a list of {@link DishEntry} to a {@link RecyclerView}.
 */
public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishesAdapterViewHolder> {

    private final Context mContext;
    private List<DishWithIngredients> mDishes;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    OnItemClickListener onItemClickListener;


    //constructor
    public DishesAdapter(Context context, OnItemClickListener listener ) {
        mContext = context;
        onItemClickListener = listener;
        //mClickHandler = clickHandler;
        if (mDishes != null){
            for (int i = 0; i < mDishes.size(); i++) {
                expandState.append(i, false);
            }
        }
    }

    interface OnItemClickListener{
        void onItemClick(int id, int position);
    }

    /**
     * @param viewGroup The ViewGroup that these ViewHolders are contained within
     * @param viewType  If your RecyclerView has more than one type of item we
     *                  can use this viewType integer to provide a different layout.
     * @return A new DishesAdapterViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public DishesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId = (R.layout.mainactivity_listview);
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        return new DishesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesAdapterViewHolder holder, int position) {

        DishWithIngredients dishEntry = mDishes.get(position);

        Ingredient ingredient = dishEntry.ingredients.get(position);

        holder.dishName.setText(dishEntry.dishEntry.getName());
        holder.companyName.setText(dishEntry.dishEntry.getName());

        //Load image if exists otherwise load a place holder
//        if (!dishEntry.getImage().isEmpty()) {
//            Picasso.get().load(dishEntry.getImage()).into(holder.dishImage);
//        }

    }


    /**
     * @return items number in the dishEntry array
     */
    @Override
    public int getItemCount() { return mDishes == null ? 0 : mDishes.size(); }

    /**
     * updates the data set for the adapter
     * @param dishEntries dishes to be displayed
     */
    public void swapDishes(List<DishWithIngredients> dishEntries) {

        //if there was no dish data, then recreate all of the list
        if (mDishes == null) {
            mDishes = dishEntries;
            notifyDataSetChanged();
        }
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a forecast item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    public class DishesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView dishImage;
        ImageButton favoriteStar;
        TextView dishName;
        TextView companyName;
        ConstraintLayout card_view1;

        public DishesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.mainpage_image);
            favoriteStar = itemView.findViewById(R.id.star);
            dishName = itemView.findViewById(R.id.mainpage_dish_name);
            companyName = itemView.findViewById(R.id.mainpage_company_name);
            card_view1 = itemView.findViewById(R.id.card_view1);
            itemView.setOnClickListener(this);
        }

        /**
         * gets called when clicking on a dish
         * @param view the View that was clicked
         */
        @Override
        public void onClick(View view) {
            //int itemPosition = getAdapterPosition();
            DishWithIngredients dishEntry = mDishes.get(getAdapterPosition());
            onItemClickListener.onItemClick(dishEntry.dishEntry.getUserId(), dishEntry.dishEntry.getId());
        }

    }

}
