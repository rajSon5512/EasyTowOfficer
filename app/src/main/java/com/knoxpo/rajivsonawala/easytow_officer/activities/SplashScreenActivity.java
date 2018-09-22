package com.knoxpo.rajivsonawala.easytow_officer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.knoxpo.rajivsonawala.easytow_officer.R;

public class SplashScreenActivity extends AppCompatActivity{

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen_layout);

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(user==null){

                    Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();

                }else{

                    Intent intent=new Intent(SplashScreenActivity.this,DrawerActivity.class);
                    startActivity(intent);
                    SplashScreenActivity.this.finish();

                }

            }
        },SPLASH_TIME_OUT);


    }

}

