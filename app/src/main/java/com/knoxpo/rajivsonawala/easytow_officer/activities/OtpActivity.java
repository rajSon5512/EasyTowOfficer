package com.knoxpo.rajivsonawala.easytow_officer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.knoxpo.rajivsonawala.easytow_officer.fragments.OtpActivityFragment;

public class OtpActivity extends SingleFragmentActivity {

    private static final String EXTRA_SOMETHING =" "+OtpActivity.class.getSimpleName();
    private String verificationId;
    private String mobileNumber;

    public static Intent getStartIntent(String verificationID, Context context,String mobileNumber){

        Intent intent = new Intent(context, OtpActivity.class);
        intent.putExtra(OtpActivity.EXTRA_SOMETHING, verificationID);
        intent.putExtra("MobileNumber",mobileNumber);

      return intent;
    }

    @Override
    public Fragment createFragment() {

        return OtpActivityFragment.newInstance(getIntent().getStringExtra(EXTRA_SOMETHING),getIntent().getStringExtra("MobileNumber"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verificationId=getIntent().getStringExtra(EXTRA_SOMETHING);
        mobileNumber=getIntent().getStringExtra("MobileNumber");


     //   Toast.makeText(this,""+mobileNumber,Toast.LENGTH_SHORT).show();

    }
}
