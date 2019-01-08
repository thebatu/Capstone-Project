package com.example.bats.homefoodie.network.FirebaseAuthClass;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Clas to fetch
 */
public class FirebaseAuthViewModel extends ViewModel {

    private final FirebaseAuthLiveData firebaseAuthLiveData = new FirebaseAuthLiveData();

    public LiveData<FirebaseUser>  getFirebaseAuthLiveData() {
        return firebaseAuthLiveData;
    }

    public String getSimpleFirebaseUser(){
        return firebaseAuthLiveData.getUID();
    }


}
