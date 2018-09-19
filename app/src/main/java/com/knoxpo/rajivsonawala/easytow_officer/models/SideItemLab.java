package com.knoxpo.rajivsonawala.easytow_officer.models;

import android.util.Log;

import java.util.ArrayList;

public class SideItemLab {

    public static final String TAG=""+SideItemLab.class.getSimpleName();

    public static final long
            SIDE_ITEM_MAIN_SCREEN = 0,
            SIDE_HISTORY_SCREEN = 1,
            SIDE_SIGN_OUT=2;

    private static SideItemLab sInstance;

    public static SideItemLab getInstance() {
        if(sInstance== null){
            sInstance = new SideItemLab();
        }
        return sInstance;
    }

    private ArrayList<SideItem> mSideItems;

    private SideItemLab(){
        mSideItems = new ArrayList<>();
        mSideItems.add(new SideItem(SIDE_ITEM_MAIN_SCREEN, "HOME"));
        mSideItems.add(new SideItem(SIDE_HISTORY_SCREEN, "HISTORY"));
        mSideItems.add(new SideItem(SIDE_SIGN_OUT,"SIGN OUT"));
    }

    public ArrayList<SideItem> getSideItems(){
        return mSideItems;
    }

    public int getSize(){

        Log.d(TAG, "getSize: "+mSideItems.size());
        return mSideItems.size();
    }

}
