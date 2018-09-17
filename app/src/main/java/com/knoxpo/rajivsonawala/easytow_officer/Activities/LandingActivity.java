package com.knoxpo.rajivsonawala.easytow_officer.Activities;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.LandingFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.LandingFragment;
import com.knoxpo.rajivsonawala.easytow_officer.R;

public class LandingActivity extends AppCompatActivity  {



    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawerLayout=findViewById(R.id.drawer_layout);


        Fragment sideBar=getSupportFragmentManager().findFragmentById(R.id.landing_nav_container);

        if(sideBar==null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.landing_nav_container,new LandingFragment())
                    .commit();

        }




    }



}
