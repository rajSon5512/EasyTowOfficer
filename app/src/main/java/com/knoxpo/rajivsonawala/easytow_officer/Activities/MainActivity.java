package com.knoxpo.rajivsonawala.easytow_officer.Activities;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.*;


import android.support.v4.app.Fragment;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.LoginFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }


}
