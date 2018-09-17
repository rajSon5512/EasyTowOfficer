package com.knoxpo.rajivsonawala.easytow_officer.models;

public class SideItem {

    private long mId;
      private String mTitle;

    SideItem(long id,String title){

        mId=id;
        mTitle=title;

    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }


}
