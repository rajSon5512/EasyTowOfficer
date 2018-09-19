package com.knoxpo.rajivsonawala.easytow_officer.Activities;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.HistoryFragment;
import com.knoxpo.rajivsonawala.easytow_officer.Fragment.LandingFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.LandingFragment;
import com.knoxpo.rajivsonawala.easytow_officer.Fragment.ProfileFragment;
import com.knoxpo.rajivsonawala.easytow_officer.Fragment.SidebarFragment;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.models.SideItem;
import com.knoxpo.rajivsonawala.easytow_officer.models.SideItemLab;

public class LandingActivity extends AppCompatActivity implements SidebarFragment.CallBack {

    private DrawerLayout mDrawerLayout;
    private static final String TAG=""+LandingActivity.class.getSimpleName();

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
            loadMainFragment(new LandingFragment());
        }



    }

    private void loadMainFragment(Fragment fragment) {

        if(fragment!=null){

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();

        }
        mDrawerLayout.closeDrawers();

    }


    public void onMenuClicked(long menuId) {
        Fragment fragmentToSwitch = null;
        if(menuId == SideItemLab.SIDE_ITEM_MAIN_SCREEN){
            Log.d(TAG, "onMenuClicked: "+SideItemLab.SIDE_ITEM_MAIN_SCREEN);
            fragmentToSwitch = new LandingFragment();
        }else if(menuId == SideItemLab.SIDE_HISTORY_SCREEN){
            Log.d(TAG, "onMenuClicked: "+SideItemLab.SIDE_HISTORY_SCREEN);
            fragmentToSwitch = new HistoryFragment();
        }else if(menuId== SideItemLab.SIDE_PROFILE){
            Log.d(TAG, "onMenuClicked: "+SideItemLab.SIDE_PROFILE);
            fragmentToSwitch=new ProfileFragment();
        }
        else if(menuId==SideItemLab.SIDE_SIGN_OUT){

            Log.d(TAG, "onMenuClicked: "+SideItemLab.SIDE_SIGN_OUT);


            mDrawerLayout.closeDrawers();
            return;
        }
        else{
                fragmentToSwitch = null;
        }

        loadMainFragment(fragmentToSwitch);

    }



}
