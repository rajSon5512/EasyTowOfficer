package com.knoxpo.rajivsonawala.easytow_officer.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.knoxpo.rajivsonawala.easytow_officer.Fragment.EntryFragment;

public class EntryActivity extends SingleFragmentActivity implements EntryFragment.Callback {

    private static final String TAG = "" + EntryActivity.class.getSimpleName();
    private String mVehicleDetails;


    @Override
    public Fragment createFragment() {
        return new EntryFragment();
    }

    @Override
    public void getDetails(String text) {

        if (!text.isEmpty()) {

            mVehicleDetails=text;
            Intent returnIntent=new Intent();
            returnIntent.putExtra("result",mVehicleDetails);

            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }

    }


}
