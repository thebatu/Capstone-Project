package com.example.bats.homefoodie.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.database.dishDatabase.DishEntry;
import com.example.bats.homefoodie.database.dishDatabase.DishWithIngredients;

import java.util.List;
import java.util.zip.Inflater;

public class DishesExpandableList extends BaseExpandableListAdapter {

    private  Context mContext;
    private List<DishWithIngredients> mDishes;



    @Override
    public int getGroupCount() {
        return mDishes.size();
    }

    @Override
    public int getChildrenCount(int position) {
        return mDishes.get(position).ingredients.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDishes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDishes.get(groupPosition).ingredients.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View resultView = convertView;
        RecyclerView.ViewHolder holder;
        Context context = convertView.getContext();

        if (resultView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            resultView = layoutInflater.inflate(R.layout.dishes, null);
            TextView tt = resultView.findViewById(R.id.mainpage_dish_name);
        }

        Object item = getGroup(groupPosition);
        tt.setText(item.toString());
        return resultView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {

        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
