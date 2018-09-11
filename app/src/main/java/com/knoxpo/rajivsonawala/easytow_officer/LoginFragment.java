package com.knoxpo.rajivsonawala.easytow_officer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private TextView mMobileNumber;
    private Button mLoginButton;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        createCallbacks();
    }

    private void createCallbacks(){
        //Set mCallbacks
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
               // Log.d(TAG, phoneAuthCredential.toString());
                Log.d(TAG, "onVerificationCompleted");
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                e.printStackTrace();

            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                Log.d(TAG, "Verfication ID: "+ verificationId);
                //startActivity
                /**
                 * send verificationId as Extra to OtpActivity.
                 *
                 * Intent intent = new Intent(getActivity(), OtpActivity.class);
                 * intent.putExtra(OtpActivity.EXTRA_SOMETHING, verficationId);
                 * startActivity(intent);
                 *
                 * OR
                 *
                 */
                  Intent intent = OtpActivity.getStartIntent(verificationId,getContext());
                  startActivity(intent);
                  getActivity().finish();

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_activtiy_fragment, container, false);

        mLoginButton = view.findViewById(R.id.login_button);

        mMobileNumber = view.findViewById(R.id.mobile_number);

        mLoginButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                startPhoneNumberVerification("+91"+mMobileNumber.getText().toString());
                break;
        }
    }

    private void startPhoneNumberVerification(String mobileNumber) {
        Log.d(TAG, "Starting phone number verification for: "+ mobileNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks
        );
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user=task.getResult().getUser();

                            Log.d(TAG, "Logged in user phone: " + user.getPhoneNumber());

                            Intent intent = new Intent(getActivity(), LandingActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        }else{

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getActivity(),"Wrong Number",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
