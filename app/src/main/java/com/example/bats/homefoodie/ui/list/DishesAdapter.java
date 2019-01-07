package com.example.bats.homefoodie.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Exposes a list of dishes from a list of {@link DishEntry} to a {@link RecyclerView}.
 */
public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishesAdapterViewHolder> {

    private final Context mContext;
    private HashMap<String, DishEntry> mDishes;
    private ArrayList<DishEntry> mDishEntries;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    OnItemClickListener onItemClickListener;
    private final static int FADE_DURATION = 1000;


    //constructor
    public DishesAdapter(Context context, OnItemClickListener listener ) {
        mContext = context;
        onItemClickListener = listener;

    }

    interface OnItemClickListener{
        void onItemClick(String id, String position);
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

        DishEntry dishEntry = mDishEntries.get(position);

        holder.dishName.setText(dishEntry.getName());
        holder.companyName.setText(dishEntry.getName());
        holder.price.setText(Integer.toString(dishEntry.getPrice()));

        // Set the view to fade in
        setFadeAnimation(holder.itemView);


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
    public void swapDishes(HashMap<String, DishEntry> dishEntries) {

        //if there was no dish data, then recreate all of the list

            mDishes = dishEntries;
            mDishEntries = new ArrayList<>(dishEntries.values());
            notifyDataSetChanged();
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
        TextView price;
        ConstraintLayout card_view1;

        public DishesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.mainpage_image);
            favoriteStar = itemView.findViewById(R.id.star);
            dishName = itemView.findViewById(R.id.mainpage_dish_name);
            companyName = itemView.findViewById(R.id.mainpage_company_name);
            price = itemView.findViewById(R.id.price_int);
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
            DishEntry dishEntry = mDishEntries.get(getAdapterPosition());

            assert dishEntry != null;
            onItemClickListener.onItemClick(dishEntry.getUserId(), dishEntry.getRemoteID());
        }

    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}
