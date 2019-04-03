package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.models.Users;

public class ProfileFragment extends Fragment {

    private EditText mobileNumber;
    private TextView ownerName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_profile,container,false);

        init(v);

        fetchData();

        return v;

    }

    private void init(View v) {

        mobileNumber=v.findViewById(R.id.owner_phoneNumber);
        ownerName=v.findViewById(R.id.vehicle_owner_textview);
    }

    private void fetchData() {

        final String userId= FirebaseAuth.getInstance().getUid();

        Log.d("USERID", "fetchData: "+userId);

        FirebaseFirestore.getInstance().collection("users")
                .document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isComplete()){

                            DocumentSnapshot documentSnapshot=task.getResult();

                            Users users=new Users(documentSnapshot);

                            //ownerName.setText(users.getName());

                            Log.d("USERID", "onComplete: "+users.getName());
                            Log.d("USERID", "onComplete: "+users.getMobileNumber());

                            ownerName.setText(users.getName());
                            mobileNumber.setText(users.getMobileNumber());

                        }


                    }
                });

    }

}
