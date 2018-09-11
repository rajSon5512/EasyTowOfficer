package com.knoxpo.rajivsonawala.easytow_officer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class OtpActivity extends SingleFragmentActivity {

    private static final String EXTRA_SOMETHING =" "+OtpActivity.class.getSimpleName();
    private String verificationId;

    public static Intent getStartIntent(String verificationID, Context context){

        Intent intent = new Intent(context, OtpActivity.class);
        intent.putExtra(OtpActivity.EXTRA_SOMETHING, verificationID);

      return intent;
    }

    @Override
    public Fragment createFragment() {

        return OtpActivityFragment.newInstance(getIntent().getStringExtra(EXTRA_SOMETHING));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verificationId=getIntent().getStringExtra(EXTRA_SOMETHING);

      //  Toast.makeText(this,""+verificationId,Toast.LENGTH_SHORT).show();

    }
}
