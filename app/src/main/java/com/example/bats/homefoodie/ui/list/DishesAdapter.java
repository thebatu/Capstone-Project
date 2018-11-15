package com.example.bats.homefoodie.ui.list;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;
import com.example.bats.homefoodie.database.dishDatabase.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposes a list of dishes from a list of {@link DishEntry} to a {@link RecyclerView}.
 */
public class DishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int INGREDIENT_ID = 1 ;
    private static final int DISHES_ID = 2 ;
    private final Context mContext;
    private List<DishWithIngredients> mDishes;
    //private final DishesAdapterOnItemClickHandler mClickHandler;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    OnItemClickListener onItemClickListener;
    private Context context;


    //constructor
    public DishesAdapter(Context context, OnItemClickListener listener ) {
        mContext = context;
        onItemClickListener = listener;
        //mClickHandler = clickHandler;
        //set initial expanded state to false
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId = (R.layout.mainactivity_listview);
        Context context = viewGroup.getContext();

        switch(viewType){
            case INGREDIENT_ID: {
                int layout_ingredients = R.layout.ingredients;
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(layout_ingredients, viewGroup, false);
                return new IngredientsHolder(view);
            }
            case DISHES_ID: {
                int layout_dishes = R.layout.dishes;
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(layout_dishes, viewGroup, false);
                return new DishesHolder(view);
            }
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDishes.size()) {
            return INGREDIENT_ID;
        } else {
            return DISHES_ID;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case INGREDIENT_ID: {
                String ingredient_name = mDishes.get(position).ingredients.get(position).getName();
                ((IngredientsHolder) holder).ing_name.setText(ingredient_name);
                break;
            }
            case DISHES_ID: {
                DishWithIngredients dishEntry = mDishes.get(position);
                ((DishesHolder) holder).dishName.setText(dishEntry.dishEntry.getName());
                ((DishesHolder) holder).companyName.setText(dishEntry.dishEntry.getName());
                break;
            }
            default:
                Toast.makeText(context, "Error in Adapter", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class IngredientsHolder extends RecyclerView.ViewHolder {
        public TextView ing_name;
        public IngredientsHolder(@NonNull View itemView) {
            super(itemView);
            ing_name = itemView.findViewById(R.id.ing_name);

        }
    }


    public class DishesHolder extends RecyclerView.ViewHolder {
        ImageView dishImage;
        ImageButton favoriteStar;
        TextView dishName;
        TextView companyName;
        ConstraintLayout card_view1;
        View down_arrow;
        ConstraintLayout expanded_menu;
        Button btn_transperent;

        public DishesHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.mainpage_image);
            favoriteStar = itemView.findViewById(R.id.star);
            dishName = itemView.findViewById(R.id.mainpage_dish_name);
            companyName = itemView.findViewById(R.id.mainpage_company_name);
            card_view1 = itemView.findViewById(R.id.card_view1);
            down_arrow = itemView.findViewById(R.id.down_arrow);
            expanded_menu = itemView.findViewById(R.id.expanded_menu);
            btn_transperent = itemView.findViewById(R.id.btn_transparent);
        }
    }















//    @Override
//    public void onBindViewHolder(@NonNull DishesAdapterViewHolder holder, int position) {
//
//        DishWithIngredients dishEntry = mDishes.get(position);
//
//        Ingredient ingredient = dishEntry.ingredients.get(position);
//
//
//        holder.dishName.setText(dishEntry.dishEntry.getName());
//        holder.companyName.setText(dishEntry.dishEntry.getName());
//
//        //Load image if exists otherwise load a place holder
////        if (!dishEntry.getImage().isEmpty()) {
////            Picasso.get().load(dishEntry.getImage()).into(holder.dishImage);
////        }
//
//        //check if view is expanded
//        final boolean isExpanded = expandState.get(position);
//        //set the initial state of the expandable section
//        holder.expanded_menu.setVisibility(isExpanded?View.VISIBLE:View.GONE);
//        holder.down_arrow.setRotation(expandState.get(position) ? 180f : 0f);
//
//        holder.down_arrow.setOnClickListener(view -> onClickButton(
//                holder.expanded_menu, holder.down_arrow,  position));
//
//        holder.btn_transperent.setOnClickListener(view -> onClickButton(
//                holder.expanded_menu, holder.btn_transperent,  position));
//    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at
     *                 the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set
     */



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
    public void swapDishes(List<DishWithIngredients> dishEntries) {

        //if there was no dish data, then recreate all of the list
        if (mDishes == null) {
            mDishes = dishEntries;

            List<List<Ingredient>> ingredients = new ArrayList<>();
            mDishes.forEach(dish -> ingredients.add(dish.ingredients));


            notifyDataSetChanged();
        }
    }

//    /**
//     * click interface
//     */
//    public interface DishesAdapterOnItemClickHandler {
//        void onClick(int clickedOnPos, DishEntry dish);
//    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a forecast item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    public class DishesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView dishImage;
        ImageButton favoriteStar;
        TextView dishName;
        TextView companyName;
        ConstraintLayout card_view1;
        View down_arrow;
        ConstraintLayout expanded_menu;
        Button btn_transperent;


        public DishesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.mainpage_image);
            favoriteStar = itemView.findViewById(R.id.star);
            dishName = itemView.findViewById(R.id.mainpage_dish_name);
            companyName = itemView.findViewById(R.id.mainpage_company_name);
            card_view1 = itemView.findViewById(R.id.card_view1);
            down_arrow = itemView.findViewById(R.id.down_arrow);
            expanded_menu = itemView.findViewById(R.id.expanded_menu);
            btn_transperent = itemView.findViewById(R.id.btn_transparent);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            DishWithIngredients dishEntry = mDishes.get(getAdapterPosition());

            onItemClickListener.onItemClick(itemPosition, dishEntry.dishEntry.getId());
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


    public class IngredientsAdapter extends RecyclerView.ViewHolder  {
        public TextView ing_name;

        public IngredientsAdapter(@NonNull View itemView) {
            super(itemView);
            ing_name = itemView.findViewById(R.id.ing_name);


        }
    }




        /**
     * handles click on the arrow to expand cardView. it sets visibility to hidden or visible
     * calls the method to rotate the arrow.
     * @param expandableLayout the layout to hide or show
     * @param buttonLayout the button to rotate
     * @param position position to add to the list of corresponding positions{@link
     * SparseBooleanArray expandState}
     */
    private void onClickButton(final ConstraintLayout expandableLayout,
                               final View buttonLayout, final int position) {

        //Simply set View to Gone if not expanded
        //Not necessary but I put a simple rotation on button layout
        if (expandableLayout.getVisibility() == View.VISIBLE) {
            createRotateAnimator(buttonLayout, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(position, false);
        } else {
            createRotateAnimator(buttonLayout, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(position, true);
        }
    }

    //Code to rotate button
    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }



}
