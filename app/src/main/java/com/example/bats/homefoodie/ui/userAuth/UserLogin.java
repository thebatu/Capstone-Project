package com.example.bats.homefoodie.ui.userAuth;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bats.homefoodie.R;
import com.example.bats.homefoodie.network.FirebaseAuthClass.FirebaseAuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = UserLogin.class.getSimpleName();
    @BindView(R.id.sign_in_button)
    com.google.android.gms.common.SignInButton mSignIn;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1;
    String id_token;
    AuthCredential credential;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions
                .DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient =
                GoogleSignIn.getClient(this, gso);

        //signIn button listener
        mSignIn.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }

    //signIn button callback
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            try{
                assert account != null;
                id_token = account.getIdToken();
                credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                linkAccounts(credential);
            }catch (Exception e){
                Log.d(TAG, "Id token get failed" + e.toString());
            }


            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            Toast.makeText(this, "SIGNED IN SUCCESSFUL", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

     public void linkAccounts(AuthCredential credential){
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        Toast.makeText(UserLogin.this, "Success LINKING ACCOUNTS.",
                                Toast.LENGTH_SHORT).show();
                        //updateUI(user);
                    } else {
                        Toast.makeText(UserLogin.this, "FAILED to LINK ACCOUNT.",
                                Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "linkWithCredential:failure", task.getException());

                        //updateUI(null);
                    }

                    // ...
                });

    }








}
