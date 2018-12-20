package com.knoxpo.rajivsonawala.easytow_officer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import com.knoxpo.rajivsonawala.easytow_officer.fragments.EntryFragment;
import com.knoxpo.rajivsonawala.easytow_officer.models.Vehicle;

public class EntryActivity extends ToolbarActivity implements EntryFragment.Callback {

    private static final String TAG = "" + EntryActivity.class.getSimpleName();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Fragment createFragment() {
        return new EntryFragment();
    }

    @Override
    public void onDetailsEntered(String ticketId) {

        Log.d(TAG, "onDetailsEntered: "+ticketId);
            Intent returnIntent=new Intent();
            returnIntent.putExtra(Intent.EXTRA_RETURN_RESULT, ticketId);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
