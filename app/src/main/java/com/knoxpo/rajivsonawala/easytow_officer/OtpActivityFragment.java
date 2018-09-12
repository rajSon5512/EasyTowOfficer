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
import android.widget.EditText;
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

public class OtpActivityFragment extends Fragment implements View.OnClickListener {


    private static final String KEY = ""+OtpActivityFragment.class.getSimpleName() ;
    private static final String MKEY = "MobileNumber"+OtpActivityFragment.class.getSimpleName();
    private EditText otpNumber;
    private Button nextButton;
    private Button ResendButton;
    private String mverificationID;
    private FirebaseAuth mAuth;
    private String mMobileNumber;
    private PhoneAuthProvider.ForceResendingToken mResendingToken;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth=FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.otp_activtiy_fragment,container,false);

        mverificationID=getArguments().getString(KEY);

        mMobileNumber=getArguments().getString(MKEY);

       // Toast.makeText(getActivity(),"Mobile Number"+mMobileNumber,Toast.LENGTH_SHORT).show();


        otpNumber=view.findViewById(R.id.otp_number);
        nextButton=view.findViewById(R.id.next_button);
        ResendButton=view.findViewById(R.id.resend_button);

        nextButton.setOnClickListener(this);
        ResendButton.setOnClickListener(this);

        Toast.makeText(getActivity(),"Fragment :"+ mverificationID ,Toast.LENGTH_SHORT).show();

        return view;

    }

    public static OtpActivityFragment newInstance(String verificationId,String MobileNumber) {

        Bundle args = new Bundle();
        args.putString(KEY,verificationId);
        args.putString(MKEY,MobileNumber);
        OtpActivityFragment fragment = new OtpActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.next_button:
                    String code=otpNumber.getText().toString();
                Log.d(KEY, "onClick: next Clicked"+code);
                if(code.isEmpty()){

                        Toast.makeText(getActivity(),"OTP Empty",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    verifyPhoneNumberWithCode(mverificationID,code);
                    break;

            case R.id.resend_button:
                    break;

        }

    }

    private void verifyPhoneNumberWithCode(String mverificationID, String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mverificationID,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Log.d(KEY, "signInWithCredential:success");

                            FirebaseUser user=task.getResult().getUser();

                            Intent intent = new Intent(getActivity(),LandingActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else{

                            Log.w(KEY, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(getActivity(),"Wrong OTP",Toast.LENGTH_SHORT).show();

                            }

                        }

                    }
                });


    }



}




