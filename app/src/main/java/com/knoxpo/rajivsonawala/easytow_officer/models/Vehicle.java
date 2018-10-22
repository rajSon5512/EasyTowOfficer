package com.knoxpo.rajivsonawala.easytow_officer.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class Vehicle implements Parcelable {

    public static final String COLLECTION_NAME = "vehicles";

    private String mId;
    private String mVehicleNumber;
    private String mOwnerName;
     private String mMobileNumber;
    private int mVehicleType;

    private int mFine;
    private Date mDate;

    public Vehicle(DocumentSnapshot document){
        mId = document.getId();
        mVehicleNumber=document.get("vehicle_number").toString();
        mOwnerName=document.get("owner_name").toString();
        mMobileNumber=document.get("owner_number").toString();
        mVehicleType=Integer.parseInt(document.get("vehicle_type").toString());
        mDate=(Date)document.get("date");

        if(mDate==null){

                mDate=new Date();

            }


        Log.d(TAG, "Vehicle: "+mOwnerName);
        Log.d(TAG, "Vehicle: "+mVehicleType);



        if(mVehicleType==2){

            mFine=100;

        }else if(mVehicleType==3){

            mFine=150;

        }else if(mVehicleType==4){

            mFine=200;
        }

    }

    protected Vehicle(Parcel in) {
        mId = in.readString();
        mVehicleNumber = in.readString();
        mOwnerName = in.readString();
        mMobileNumber = in.readString();
        mVehicleType = in.readInt();
        mFine = in.readInt();
        mDate = new Date(in.readLong());
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public String getId() {
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

    public Date getmDate(){

        return mDate;
    }

    public void setDate(Date date){

        mDate=date;
        Log.d(TAG, "setDate: "+mDate);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.mId);
        parcel.writeString(this.mVehicleNumber);
        parcel.writeString(this.mOwnerName);
        parcel.writeString(this.mMobileNumber);
        parcel.writeInt(this.mVehicleType);
        parcel.writeInt(this.mFine);
        parcel.writeLong(mDate.getTime());
    }



}
