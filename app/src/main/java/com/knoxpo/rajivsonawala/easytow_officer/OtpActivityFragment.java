package com.knoxpo.rajivsonawala.easytow_officer;

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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpActivityFragment extends Fragment {


    private static final String KEY = ""+OtpActivityFragment.class.getSimpleName() ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.otp_activtiy_fragment,container,false);

        String verificationId=(String)getArguments().getString(KEY);

        Toast.makeText(getActivity(),""+verificationId,Toast.LENGTH_SHORT).show();


        return view;

    }

    public static OtpActivityFragment newInstance(String verificationId) {

        OtpActivityFragment simpleFragment=new OtpActivityFragment();
        Bundle args = new Bundle();
        args.putString(KEY,verificationId);
        OtpActivityFragment fragment = new OtpActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }


}




