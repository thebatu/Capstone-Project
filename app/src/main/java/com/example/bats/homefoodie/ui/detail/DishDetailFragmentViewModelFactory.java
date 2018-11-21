package com.example.bats.homefoodie.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.bats.homefoodie.HomefoodieRepository;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link HomefoodieRepository}
 */
public class DishDetailFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private HomefoodieRepository mRepository;
    private int userID;

    public DishDetailFragmentViewModelFactory(HomefoodieRepository mRepository, int userID) {
        this.mRepository = mRepository;
        this.userID = userID;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DishDetailFragmentViewModel(mRepository, userID);

    }
}
