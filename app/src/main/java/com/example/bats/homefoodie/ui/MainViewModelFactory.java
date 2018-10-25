package com.example.bats.homefoodie.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.example.bats.homefoodie.HomefoodieRepository;
import com.example.bats.homefoodie.ui.list.DishesViewModel;


/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link HomefoodieRepository}
 */
public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private HomefoodieRepository mRepository;

    public MainViewModelFactory(HomefoodieRepository mRepository) {
        this.mRepository = mRepository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create( Class<T> modelClass) {
            return (T) new DishesViewModel(mRepository);

    }
}
