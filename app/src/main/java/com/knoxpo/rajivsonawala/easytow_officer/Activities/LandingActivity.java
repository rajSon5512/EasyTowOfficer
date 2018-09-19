package com.knoxpo.rajivsonawala.easytow_officer.Activities;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.HistoryFragment;
import com.knoxpo.rajivsonawala.easytow_officer.Fragment.LandingFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.LandingFragment;
import com.knoxpo.rajivsonawala.easytow_officer.Fragment.ProfileFragment;
import com.knoxpo.rajivsonawala.easytow_officer.Fragment.SidebarFragment;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.models.SideItemLab;

public class LandingActivity extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;


   /* @Override
    public Fragment createFragment() {
        return new LandingFragment();
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_landing);


        mDrawerLayout=findViewById(R.id.drawer_layout);


        Fragment sideBar=getSupportFragmentManager().findFragmentById(R.id.nav_container);

        if(sideBar==null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_container,new SidebarFragment())
                    .commit();

        }

        Fragment landingFragment = getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if(landingFragment == null){
            loadMainFragment(new ProfileFragment());
        }



    }

    private void loadMainFragment(Fragment fragment) {

        if(fragment==null){

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();

        }
        mDrawerLayout.closeDrawers();

    }


    public void onMenuClicked(long menuId) {
        Fragment fragmentToSwitch = null;
        if(menuId == SideItemLab.SIDE_ITEM_MAIN_SCREEN){
            fragmentToSwitch = new LandingFragment();
        }else if(menuId == SideItemLab.SIDE_HISTORY_SCREEN){
            fragmentToSwitch = new HistoryFragment();
        }else{
            fragmentToSwitch = null;
        }

        loadMainFragment(fragmentToSwitch);

    }



}
