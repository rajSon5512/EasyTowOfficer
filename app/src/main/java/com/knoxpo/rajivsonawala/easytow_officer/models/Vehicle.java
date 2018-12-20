package com.knoxpo.rajivsonawala.easytow_officer.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class Vehicle implements Parcelable {

    public static final String COLLECTION_NAME = "vehicles";

    private String mId;
    private String  mUUID;
    private String mVehicleNumber;
    private String mOwnerName;
    private String mMobileNumber;
    private int mVehicleType;

    public Vehicle(DocumentSnapshot document) {
        mId = document.getId();

        mUUID=document.get("owner_id").toString();
        mVehicleNumber = document.get("vehicle_number").toString();
        mOwnerName = document.get("owner_name").toString();
        mMobileNumber = document.get("owner_number").toString();
        mVehicleType = Integer.parseInt(document.get("vehicle_type").toString());
        Log.d(TAG, "Vehicle: " + mOwnerName);
        Log.d(TAG, "Vehicle: " + mVehicleType);
    }

    public String getUUID() {
        return mUUID;
    }

    protected Vehicle(Parcel in) {
        mId = in.readString();
        mVehicleNumber = in.readString();
        mOwnerName = in.readString();
        mMobileNumber = in.readString();
        mVehicleType = in.readInt();
        mUUID=in.readString();
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
        parcel.writeString(this.mUUID);
    }

}
