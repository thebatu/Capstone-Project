package com.example.bats.homefoodie.network.FirebaseAuthClass;

import android.arch.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class to fetch user info from firebase and sign in user, if user does not exist,
 * do sign User Anonymously
 */
public class FirebaseAuthLiveData extends LiveData {
    //get firebase singleton
    private FirebaseAuth mAuth =  FirebaseAuth.getInstance();

    // get current user, if user is not registered sign User Anonymously
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

    //add liveData auth listener on active
    @Override
    protected void onActive() {
        super.onActive();
        mAuth.addAuthStateListener(authStateListener);
    }

    //remove liveData auth listener on active
    @Override
    protected void onInactive() {
        super.onInactive();
        mAuth.removeAuthStateListener(authStateListener);
    }

    //get user UID (universal user ID)
    public String getUID(){
        return mAuth.getUid();
    }


}

