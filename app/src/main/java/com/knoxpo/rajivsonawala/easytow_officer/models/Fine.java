package com.knoxpo.rajivsonawala.easytow_officer.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class Fine {

    public static final String COLLECTION_NAME = "fines";

    public static final String FIELD_VALUE = "value";

    private String mId;
    private double mFine;

    public Fine(DocumentSnapshot documentSnapshot){
        mId = documentSnapshot.getId();
        mFine = documentSnapshot.getDouble(FIELD_VALUE);
    }

    public String getId() {
        return mId;
    }

    public double getFine() {
        return mFine;
    }

    public void setFine(double fine) {
        mFine = fine;
    }
}
