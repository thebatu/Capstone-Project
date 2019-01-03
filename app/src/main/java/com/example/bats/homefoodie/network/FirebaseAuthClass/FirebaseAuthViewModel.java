package com.example.bats.homefoodie.network.FirebaseAuthClass;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthViewModel extends ViewModel {

    private final FirebaseAuthLiveData firebaseAuthLiveData = new FirebaseAuthLiveData();


    public LiveData<FirebaseUser> getFirebaseAuthLiveData() {
        return firebaseAuthLiveData;
    }

    public String getSimpleFirebaseUser(){
        Log.d("MyTag", "ffbase  " +  firebaseAuthLiveData.getUID());
        return firebaseAuthLiveData.getUID();

//
//        String dfjds = getFirebaseAuthLiveData().getValue().getUid();
//
//        if (!getFirebaseAuthLiveData().getValue().getUid().isEmpty()) {
//            return getFirebaseAuthLiveData().getValue().getUid();
//        }else {
//            return null;
//        }
    }
}
