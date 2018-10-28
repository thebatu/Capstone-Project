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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;

import java.util.List;

/**
 * Exposes a list of dishes from a list of {@link DishEntry} to a {@link RecyclerView}.
 */
public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishesAdapterViewHolder> {

    private final Context mContext;
    private List<DishEntry> mDishes;
    private final DishesAdapterOnItemClickHandler mClickHandler;
    private SparseBooleanArray expandState = new SparseBooleanArray();


    //constructor
    public DishesAdapter(Context context, DishesAdapterOnItemClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        //set initial expanded state to false
        for (int i = 0; i < mDishes.size(); i++) {
            expandState.append(i, false);
        }
    }

    /**
     * @param viewGroup The ViewGroup that these ViewHolders are contained within
     * @param viewType  If your RecyclerView has more than one type of item we
     *                  can use this viewType integer to provide a different layout.
     * @return A new DishesAdapterViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public DishesAdapter.DishesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                    int viewType) {

        int layoutId = (R.layout.mainactivity_listview);
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        return new DishesAdapterViewHolder(view);
    }


    /**
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at
     *                 the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull DishesAdapter.DishesAdapterViewHolder
                                         holder, int position) {

        DishEntry dishEntry = mDishes.get(position);
        holder.dishName.setText(dishEntry.getName());
        holder.companyName.setText(dishEntry.getName());

        //Load image if exists otherwise load a place holder
//        if (!dishEntry.getImage().isEmpty()) {
//            Picasso.get().load(dishEntry.getImage()).into(holder.dishImage);
//        }

        //check if view is expanded
        final boolean isExpanded = expandState.get(position);
        //holder.buttonLayout.setRotation(expandState.get(i) ? 180f : 0f);

        @Override
        public void onClick ( final View v){
            onClickButton(holder.expandableLayout, holder.buttonLayout, i);
        }


    }


    /**
     * @return items number in the dishEntry array
     */
    @Override
    public int getItemCount() {
        if (mDishes != null) {
            return mDishes.size();
        } else {
            return 0;
        }
    }

    /**
     * updates the data set for the adapter
     *
     * @param dishEntries dishes to be displayed
     */
    public void swapDishes(List<DishEntry> dishEntries) {
        //if there was no dish data, then recreate all of the list
        if (mDishes == null) {
            mDishes = dishEntries;
            notifyDataSetChanged();
        }
    }

    /**
     * click interface
     */
    public interface DishesAdapterOnItemClickHandler {
        void onClick(int clickedOnPos, DishEntry dish);
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a forecast item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    public class DishesAdapterViewHolder extends RecyclerView.ViewHolder  {
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

            //itemView.setOnClickListener(this);
        }

        /**
         * gets called when clicking on a dish
         *
         * @param view the View that was clicked
         */
//        @Override
//        public void onClick(View view) {
//            int itemPosition = getAdapterPosition();
//            DishEntry dishEntry = mDishes.get(getAdapterPosition());
//            mClickHandler.onClick(itemPosition, dishEntry);
//
//        }


    }

    private void onClickButton(final LinearLayout expandableLayout, final RelativeLayout buttonLayout, final int i) {

        //Simply set View to Gone if not expanded
        //Not necessary but I put simple rotation on button layout
        if (expandableLayout.getVisibility() == View.VISIBLE) {
            //createRotateAnimator(buttonLayout, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        } else {
            //createRotateAnimator(buttonLayout, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(i, true);
        }
    }
}
