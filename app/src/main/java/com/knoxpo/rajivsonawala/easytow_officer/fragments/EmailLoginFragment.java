package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.activities.DrawerActivity;

import static android.support.constraint.Constraints.TAG;

public class EmailLoginFragment extends Fragment implements View.OnClickListener {

    private EditText mEmailAddress;
    private EditText mPassword;
    private FirebaseAuth mAuth;
    private Button mLoginButton;
    private TextView mForgetPasswordMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth= FirebaseAuth.getInstance();



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_officer_login,container,false);
        init(view);

        mLoginButton.setOnClickListener(this);
        mForgetPasswordMessage.setOnClickListener(this);

        return view;
    }

    private void init(View view) {

        mEmailAddress=view.findViewById(R.id.email_id);
        mPassword=view.findViewById(R.id.password);
        mLoginButton=view.findViewById(R.id.login_action_button);
        mForgetPasswordMessage=view.findViewById(R.id.forget_password_message);

    }


    @Override
    public void onClick(View view) {

        String email=mEmailAddress.getText().toString();
        String password=mPassword.getText().toString();

        switch(view.getId()){

           case R.id.login_action_button:
                if(email.matches("")|| password.matches("")){

                    Toast.makeText(getContext(),"Email/Passord Field Empty",Toast.LENGTH_LONG).show();

                }else{


                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){

                                        Log.d(TAG, "SignWithEmail:Suceessful");
                                        Toast.makeText(getContext(),"Successfull Login",Toast.LENGTH_LONG).show();

                                        Intent intent=new Intent(getActivity(),DrawerActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }else{

                                        Log.d(TAG,"SignWithEmail:Fail---"+task.getException());

                                    }

                                }
                            });


                }

                break;

           case R.id.forget_password_message:

               String emailId=mEmailAddress.getText().toString();

               if(emailId.matches("")){

                        Toast.makeText(getContext(),"Email Field Empty",Toast.LENGTH_SHORT).show();


                }else{

                    mAuth.sendPasswordResetEmail(mEmailAddress.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        Toast.makeText(getContext(),"Check Your Email ",Toast.LENGTH_SHORT).show();

                                    }else{

                                        Log.d(TAG, "ForgetPassword:Failed"+task.getException());

                                    }

                                }
                            });


               }

       }


    }


}
