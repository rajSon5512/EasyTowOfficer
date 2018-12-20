package com.knoxpo.rajivsonawala.easytow_officer.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import static android.support.constraint.Constraints.TAG;

public class NormalUser implements Parcelable {

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

    }


    public static final String COLLECTION_NAME = "normalUser";

    private String mId;
    private String mVehicleNumber;
    private String mOwnerName;
    private String mMobileNumber;
    private int mVehicleType;

    public NormalUser(DocumentSnapshot document) {
        mId = document.getId();

        mVehicleNumber = document.get("vehicle_number").toString();
        mOwnerName = document.get("owner_name").toString();
        mMobileNumber = document.get("mobile").toString();
        mVehicleType = Integer.parseInt(document.get("vehicle_type").toString());
        Log.d(TAG, "Vehicle: " + mOwnerName);
        Log.d(TAG, "Vehicle: " + mVehicleType);
    }


    protected NormalUser(Parcel in) {
        mId = in.readString();
        mVehicleNumber = in.readString();
        mOwnerName = in.readString();
        mMobileNumber = in.readString();
        mVehicleType = in.readInt();
    }



    public static final Creator<NormalUser> CREATOR = new Creator<NormalUser>() {
        @Override
        public NormalUser createFromParcel(Parcel in) {
            return new NormalUser(in);
        }

        @Override
        public NormalUser[] newArray(int size) {
            return new NormalUser[size];
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

}
