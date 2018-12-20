package com.knoxpo.rajivsonawala.easytow_officer.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.knoxpo.rajivsonawala.easytow_officer.R;

public abstract class ToolbarActivity extends SingleFragmentActivity {

    private Toolbar mToolbar;

    @Override
    protected int getLayout() {
        return R.layout.activity_toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setSupportActionBar(mToolbar);
    }

    private void init(){
        mToolbar = findViewById(R.id.toolbar);
    }

    protected final Toolbar getToolbar(){
        return mToolbar;
    }
}
