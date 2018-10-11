package com.knoxpo.rajivsonawala.easytow_officer.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class Entry {

    private String mId;
    private String mText;

    public Entry(DocumentSnapshot document){
        mId = document.getId();
        mText = document.getString("text");
    }

    public String getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }
}
