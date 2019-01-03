package com.example.bats.homefoodie.network.FirebaseAuthClass;

import android.arch.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthLiveData extends LiveData {
    private FirebaseAuth mAuth =  FirebaseAuth.getInstance();

    private FirebaseAuth.AuthStateListener authStateListener =
            firebaseAuth -> {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    firebaseAuth.signInAnonymously().addOnSuccessListener(authResult -> {
                        FirebaseUser user = mAuth.getCurrentUser();
                        setValue(user);
                    });
                }
            };

    @Override
    protected void onActive() {
        super.onActive();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mAuth.removeAuthStateListener(authStateListener);
    }

    public String getUID(){
        return mAuth.getUid();
    }


}

