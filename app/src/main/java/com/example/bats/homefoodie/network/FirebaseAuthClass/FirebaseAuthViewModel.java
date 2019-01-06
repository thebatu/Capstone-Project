package com.example.bats.homefoodie.network.FirebaseAuthClass;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

/**
 * Clas to fetch
 */
public class FirebaseAuthViewModel extends ViewModel {

    private final FirebaseAuthLiveData firebaseAuthLiveData = new FirebaseAuthLiveData();

    public LiveData<FirebaseUser> getFirebaseAuthLiveData() {
        return firebaseAuthLiveData;
    }

    public String getSimpleFirebaseUser(){
        return firebaseAuthLiveData.getUID();

    }
}
