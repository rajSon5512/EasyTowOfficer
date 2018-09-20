package com.knoxpo.rajivsonawala.easytow_officer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.Entry_Fragment;

public class Entry_Activity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return new Entry_Fragment();
    }
}
