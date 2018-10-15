package com.knoxpo.rajivsonawala.easytow_officer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentSnapshot;

public class Entry implements Parcelable {

    private String mId;
    private String mVehicleNumber;
    private String mOwnerName;
     private String mMobileNumber;
    private int mVehicleType;
    private int mFine;

    public Entry(DocumentSnapshot document){
        mId = document.getId();
        mVehicleNumber=document.get("vehicle_number").toString();
        mOwnerName=document.get("owner_name").toString();
        mMobileNumber=document.get("owner_number").toString();
        mVehicleType=Integer.parseInt(document.get("vehicle_type").toString());

        if(mVehicleType==2){

            mFine=100;

        }else if(mVehicleType==3){

            mFine=150;

        }else if(mVehicleType==4){

            mFine=200;
        }

    }

    public String getId() {
        return mId;
    }

    public String getmId() {
        return mId;
    }

    public String getmVehicleNumber() {
        return mVehicleNumber;
    }

    public String getmOwnerName() {
        return mOwnerName;
    }

    public String getmMobileNumber() {
        return mMobileNumber;
    }

    public int getmVehicleType() {
        return mVehicleType;
    }

    public int getmFine(){

        return mFine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.mId);
        parcel.writeString(this.mVehicleNumber);
        parcel.writeString(this.mMobileNumber);
        parcel.writeInt(this.mVehicleType);
        parcel.writeInt(this.mFine);

    }
}
