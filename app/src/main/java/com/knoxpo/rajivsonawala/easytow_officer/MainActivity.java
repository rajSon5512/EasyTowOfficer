package com.knoxpo.rajivsonawala.easytow_officer;

import android.support.v4.app.Fragment;

public class MainActivity extends SingalFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }


}
