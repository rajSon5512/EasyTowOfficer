package com.knoxpo.rajivsonawala.easytow_officer.activities;

import android.support.v4.app.Fragment;

import com.knoxpo.rajivsonawala.easytow_officer.fragments.EmailLoginFragment;

public class EmailLoginActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new EmailLoginFragment();
    }
}
