package com.knoxpo.rajivsonawala.easytow_officer.Fragment;

import com.knoxpo.rajivsonawala.easytow_officer.Activities.*;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.knoxpo.rajivsonawala.easytow_officer.R;

import java.util.ArrayList;

public class LandingFragment extends Fragment {

    private TextView mPhoneTV;
    private Button mSignOutBtn;
    private ArrayList<String> mVehicle=new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landing, container, false);
       // init(v);

/*
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
*/

        return v;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_landing,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.add_button_menu){


            Intent intent=new Intent(getActivity(),EntryActivity.class);
            startActivityForResult(intent,1);
            //getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){

            if(resultCode==Activity.RESULT_OK){

                if(data.hasExtra("result")){

                    String tempString=data.getExtras().getString("result");
                    Toast.makeText(getContext(),"vehicle Number:"+data.getExtras().getString("result"),Toast.LENGTH_LONG).show();
                    mVehicle.add(tempString);
                }

            }

        }


    }


    /*private void init(View v){
        mPhoneTV = v.findViewById(R.id.tv_phone);
        mSignOutBtn = v.findViewById(R.id.btn_sign_out);
    }
*/
}
