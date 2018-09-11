package com.knoxpo.rajivsonawala.easytow_officer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public class OtpActivity extends SingalFragmentActivity {

    private static final String EXTRA_SOMETHING =" "+OtpActivity.class.getSimpleName();

    public static Intent getStartIntent(String verificationID, Context context){

        Intent intent = new Intent(context, OtpActivity.class);
        intent.putExtra(OtpActivity.EXTRA_SOMETHING, verificationID);

      return intent;
    }

    @Override
    public Fragment createFragment() {
        return new OtpActivityFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String verificationId=getIntent().getStringExtra(EXTRA_SOMETHING);

        Toast.makeText(this,""+verificationId,Toast.LENGTH_SHORT).show();

    }
}
