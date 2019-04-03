package com.knoxpo.rajivsonawala.easytow_officer.models;


import com.google.firebase.firestore.DocumentSnapshot;

public class Users {

    private String name;
    private String mobileNumber;

    public Users(DocumentSnapshot documentSnapshot){

        this.name=documentSnapshot.getString("name");
        this.mobileNumber=documentSnapshot.getString("phone");
    }


    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}



