package com.knoxpo.rajivsonawala.easytow_officer;

import android.support.v4.app.Fragment;

public class LandingActivity extends SingalFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new LandingFragment();
    }
}
