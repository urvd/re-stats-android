package com.example.re_stats_android.provider;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.re_stats_android.models.IUser;
import com.example.re_stats_android.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import static com.example.re_stats_android.communs.CommunValues.USER_BASE_MAIL;


public class AuthProvider implements IUser {
    private FirebaseAuth firebaseAuth;
    public  AuthProvider(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInUser(final Activity activity, UserModel userModel) {
        try{
            firebaseAuth.signInWithEmailAndPassword(userModel.getUsername() + USER_BASE_MAIL, userModel.getPassword())
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Info: ", "signInWithEmail:success");
                                //mViewModel.setUserState(user);
                            } else {
                                Log.w("Info: ", "signInWithEmail:failure", task.getException());
                            }

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String onIdTokenRevocation() {
        final String[] idToken = new String[1];
        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        try {
            mUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                idToken[0] = task.getResult().getToken();
                                // Send token to your backend via HTTPS
                                // ...
                            } else {
                                Log.w("Info: ", "idToken:failure", task.getException());
                                idToken[0] = null;
                            }
                        }
                    });
            return idToken[0];
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public UserModel getCurrentUser() {
        try{
            String uid  = firebaseAuth.getCurrentUser().getUid();
            CharSequence userName = firebaseAuth.getCurrentUser().getEmail()
                .subSequence(0,firebaseAuth.getCurrentUser().getEmail().length() - USER_BASE_MAIL.length());
            return  UserModel.builder().username(userName.toString()).uid(uid).build();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void signOutUser() {
        firebaseAuth.signOut();
    }
}
