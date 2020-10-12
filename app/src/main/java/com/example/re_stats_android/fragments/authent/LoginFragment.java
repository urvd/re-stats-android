package com.example.re_stats_android.fragments.authent;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.re_stats_android.HomeBaseActivity;
import com.example.re_stats_android.R;
import com.example.re_stats_android.models.IUser;
import com.example.re_stats_android.models.UserModel;
import com.example.re_stats_android.provider.AuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_ACCOUNT;
import static com.example.re_stats_android.communs.CommunValues.ARG_PARAM_ACCOUNT_ID;
import static com.example.re_stats_android.communs.CommunValues.ButtonGriser;
import static com.example.re_stats_android.communs.CommunValues.ButtonSubmit;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private EditText username;
    private EditText password;
    private Button btn_valid;
    private Button btn_redirect_inscrip;
    Activity activity;
    IUser userProvider;

    private FirebaseAuth mAuth;
//    FirebaseDatabase database;
//    DatabaseReference ref;

/*    public LoginFragment() {

    }*/

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setTargetFragment(@Nullable Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        userProvider = new AuthProvider();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        btn_valid = view.findViewById(R.id.validate);
        mAuth = FirebaseAuth.getInstance();
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("users");

        if(submitRequirement()) {
            btn_valid.setBackgroundColor(ButtonSubmit);
        } else {
            btn_valid.setBackgroundColor(ButtonGriser);
        }
        btn_redirect_inscrip = view.findViewById(R.id.inscrip_redirect);

        btn_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitRequirement()) {
                    userProvider.signInUser(getActivity(),
                            UserModel.builder().username(username.getText().toString())
                                    .password(password.getText().toString()).build());
                    UserModel currentUser = userProvider.getCurrentUser();
                    if(currentUser != null){


                        Bundle args = new Bundle();
                        args.putString(ARG_PARAM_ACCOUNT, currentUser.getUsername());
                        args.putString(ARG_PARAM_ACCOUNT_ID, currentUser.getUid());
                        startActivity(new Intent(activity, HomeBaseActivity.class), args);
                    }else{
                        Toast.makeText(getActivity(), "Authentication failed !",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btn_redirect_inscrip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AuthentFragment authentFragment = new AuthentFragment(getFragmentManager());
                //Navigation.
                authentFragment.startFragment(new InscriptionFragment());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mViewModel.setUserState(new UserModel());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        userProvider = new AuthProvider();
        if(userProvider.getCurrentUser() != null){
            UserModel currentUser = userProvider.getCurrentUser();
            if (currentUser != null) {

                Bundle args = new Bundle();

                args.putString(ARG_PARAM_ACCOUNT, currentUser.getUsername());
                args.putString(ARG_PARAM_ACCOUNT_ID, currentUser.getUid());
                startActivity(new Intent(getActivity(), HomeBaseActivity.class),args);
            }
        }else{
            Toast.makeText(getActivity(), "Re Authentication !",
                    Toast.LENGTH_LONG).show();
            //userProvider.signOutUser();
        }
    }
    boolean submitRequirement(){
        return (username.getText().toString() != null || !username.getText().toString().isEmpty())
                && (password.getText().toString() != null || !password.getText().toString().isEmpty());
    }



}