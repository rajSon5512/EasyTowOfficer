package com.knoxpo.rajivsonawala.easytow_officer.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.knoxpo.rajivsonawala.easytow_officer.fragments.HistoryFragment;
import com.knoxpo.rajivsonawala.easytow_officer.fragments.LandingFragment;
import com.knoxpo.rajivsonawala.easytow_officer.fragments.ProfileFragment;
import com.knoxpo.rajivsonawala.easytow_officer.fragments.SidebarFragment;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.models.SideItemLab;

public class DrawerActivity extends ToolbarActivity implements SidebarFragment.CallBack {


    private DrawerLayout mDrawerLayout;
    private static final String TAG = "" + DrawerActivity.class.getSimpleName();

   /* @Override
    public Fragment createFragment() {
        return new LandingFragment();
    }*/

    @Override
    protected int getLayout() {
        return R.layout.activity_drawer;
    }

    @Override
    public Fragment createFragment() {
        return new LandingFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        Fragment sideBar = getSupportFragmentManager().findFragmentById(R.id.nav_container);

        if (sideBar == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_container, new SidebarFragment())
                    .commit();

        }
    }

    private void init() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
    }

    private void loadMainFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

        }
        mDrawerLayout.closeDrawers();
    }


    public void onMenuClicked(long menuId) {
        Fragment fragmentToSwitch = null;
        if (menuId == SideItemLab.SIDE_ITEM_MAIN_SCREEN) {
            Log.d(TAG, "onMenuClicked: " + SideItemLab.SIDE_ITEM_MAIN_SCREEN);
            fragmentToSwitch = new LandingFragment();
        } else if (menuId == SideItemLab.SIDE_HISTORY_SCREEN) {
            Log.d(TAG, "onMenuClicked: " + SideItemLab.SIDE_HISTORY_SCREEN);
            fragmentToSwitch = new HistoryFragment();
        } else if (menuId == SideItemLab.SIDE_PROFILE) {
            Log.d(TAG, "onMenuClicked: " + SideItemLab.SIDE_PROFILE);
            fragmentToSwitch = new ProfileFragment();
        } else if (menuId == SideItemLab.SIDE_SIGN_OUT) {

            Log.d(TAG, "onMenuClicked: " + SideItemLab.SIDE_SIGN_OUT);


            mDrawerLayout.closeDrawers();
            return;
        } else {
            fragmentToSwitch = null;
        }

        loadMainFragment(fragmentToSwitch);

    }


}
