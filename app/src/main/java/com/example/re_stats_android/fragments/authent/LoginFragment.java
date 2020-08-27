package com.example.re_stats_android.fragments.authent;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.re_stats_android.HomeBaseActivity;
import com.example.re_stats_android.R;
import com.example.re_stats_android.models.UserModel;

import static com.example.re_stats_android.communs.CommunValues.ButtonGriser;
import static com.example.re_stats_android.communs.CommunValues.ButtonSubmit;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private EditText username;
    private EditText password;
    private Button btn_login;
    private Button btn_redirect_inscrip;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        btn_login = view.findViewById(R.id.validate);
        if(submitRequirement()) {
            btn_login.setBackgroundColor(ButtonSubmit);
        } else {
            btn_login.setBackgroundColor(ButtonGriser);
        }
        btn_redirect_inscrip = view.findViewById(R.id.inscrip_redirect);
        onActivityCreated(savedInstanceState);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConnecting(username.getText().toString(), password.getText().toString());
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
        if(submitRequirement()) {
            btn_login.setBackgroundColor(ButtonSubmit);
        } else {
            btn_login.setBackgroundColor(ButtonGriser);
        }
    }

    /*    @Override
        public void onStart() {
            super.onStart();
            if (mViewModel.isEqualToMock()) {
                startActivity(new Intent(getActivity(), HomeBaseActivity.class));
            }
        }*/
    boolean submitRequirement(){
        return (username.getText().toString() != null || !username.getText().toString().isEmpty())
                && (password.getText().toString() != null || !password.getText().toString().isEmpty());
    }

    void isConnecting(String usr, String psw){
        if(usr.equals("user") && psw.equals("user")) {
            mViewModel.getUserState().setUsername(usr);
            mViewModel.getUserState().setPassword(psw);
            startActivity(new Intent(getActivity(), HomeBaseActivity.class));
        }
    }

}