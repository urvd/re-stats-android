package com.example.re_stats_android.fragments.authent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.re_stats_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;
import static com.example.re_stats_android.communs.CommunValues.USER_BASE_MAIL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InscriptionFragment extends Fragment {
    private EditText username;
    private EditText password;
    private EditText passwordConfirm;
    private Button btn_valid;
    FirebaseAuth mAuth;

    public InscriptionFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static InscriptionFragment newInstance(String param1, String param2) {
        return new InscriptionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        btn_valid = view.findViewById(R.id.validate);
        mAuth = FirebaseAuth.getInstance();

        btn_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitRequiment()) {
                    signUpUser();
                }
            }
        });
        return view;
    }

    private boolean submitRequiment(){
        return !username.getText().toString().isEmpty()
                && !passwordConfirm.getText().toString().isEmpty()
                && !password.getText().toString().isEmpty();
    }

    private void signUpUser() {
        mAuth.createUserWithEmailAndPassword(username.getText().toString()+USER_BASE_MAIL,
                password.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(), "Account Succesfully created, now you can sign in !!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}