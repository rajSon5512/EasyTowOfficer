package com.knoxpo.rajivsonawala.easytow_officer.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class SmcParking {


    public static final String COLLECTION_NAME="smc_parking";
    public static final String NAME="name";
    public static final String LAT="latitude";
    public static final String LONG="longitude";


    private String documentId;
    private String name;
    private String latitude;
    private String longitude;

    public SmcParking(DocumentSnapshot documentSnapshot){

        this.documentId=documentSnapshot.getId();
        this.name=documentSnapshot.getString(this.NAME);
        this.latitude=documentSnapshot.getString(this.latitude);
        this.longitude=documentSnapshot.getString(this.longitude);
    }



    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }


}
