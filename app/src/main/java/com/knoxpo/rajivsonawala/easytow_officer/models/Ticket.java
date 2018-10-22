package com.knoxpo.rajivsonawala.easytow_officer.models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Ticket {

    public static final String COLLECTION_NAME = "tickets";

    /**
     * Field names
     */
    public static final String FIELD_FINE = "fine";
    public static final String FIELD_CURRENT_STATUS = "current_status";
    public static final String FIELD_DATE = "date";
    public static final String FIELD_RAISED_BY = "raised_by";
    public static final String FIELD_VEHICLE_ID = "vehicle_id";

    /**
     * Member variables
     */
    private String mId;
    private String mCurrentStatus;
    private Date mDate;
    private String mRaisedBy;
    private String mVehicleId;
    private double mFine;

    private Vehicle mVehicle;


    /**
     * Content Firebase document snapshot to JAVA POJO
     * @param snapshot
     */
    public Ticket(DocumentSnapshot snapshot){
        mId = snapshot.getId();
        mCurrentStatus = snapshot.getString(FIELD_CURRENT_STATUS);
        mDate = snapshot.getDate(FIELD_DATE);
        mRaisedBy = snapshot.getString(FIELD_RAISED_BY);
        mVehicleId = snapshot.getString(FIELD_VEHICLE_ID);
        mFine = snapshot.getDouble(FIELD_FINE);
    }

    public String getId() {
        return mId;
    }

    public String getCurrentStatus() {
        return mCurrentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        mCurrentStatus = currentStatus;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getRaisedBy() {
        return mRaisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        mRaisedBy = raisedBy;
    }

    public String getVehicleId() {
        return mVehicleId;
    }

    public void setVehicleId(String vehicleId) {
        mVehicleId = vehicleId;
    }

    public double getFine() {
        return mFine;
    }

    public void setFine(double fine) {
        mFine = fine;
    }

    public Vehicle getVehicle() {
        return mVehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        mVehicle = vehicle;
    }

    /**
     * Map to upload to Firebase Firestore
     * @return
     */
    public Map<String, Object> toMap(){
        Map<String, Object> values = new  HashMap<>();
        values.put(FIELD_CURRENT_STATUS, mCurrentStatus);
        values.put(FIELD_DATE, mDate);
        values.put(FIELD_RAISED_BY, mRaisedBy);
        values.put(FIELD_VEHICLE_ID, mVehicleId);
        values.put(FIELD_FINE, mFine);

        return values;
    }

}
