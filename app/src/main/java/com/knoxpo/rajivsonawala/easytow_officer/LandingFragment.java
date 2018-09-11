package com.knoxpo.rajivsonawala.easytow_officer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LandingFragment extends Fragment {

    private TextView mPhoneTV;
    private Button mSignOutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landing, container, false);
        init(v);

        mPhoneTV.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }

    private void init(View v){
        mPhoneTV = v.findViewById(R.id.tv_phone);
        mSignOutBtn = v.findViewById(R.id.btn_sign_out);
    }

}
