package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.activities.OcrCaptureActivity;

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class EntryFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = EntryFragment.class.getSimpleName();
    private EditText mVehicleDetails;
    private Callback mCallback;
    private ImageButton mImageButton;
    private int requestcode=1;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private String value;


    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.camera_Button:
                Intent intent=new Intent(getActivity(),OcrCaptureActivity.class);
                startActivityForResult(intent,requestcode);
                break;
        }


    }
    public interface Callback {
        void onDetailsEntered(String text);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) getContext();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        db=FirebaseFirestore.getInstance();
        collectionReference=db.collection("VehicleNumber");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry, container, false);
        init(v);

        mImageButton.setOnClickListener(this);

        return v;
    }

    private void init(View v) {

        mVehicleDetails = v.findViewById(R.id.vehicle_details_box);
        mImageButton=v.findViewById(R.id.camera_Button);
     }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_entry_activity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.entry_done_button:
                value = mVehicleDetails.getText().toString();
                Log.d(TAG, "onOptionsItemSelected: " + value);
                fireStoreAdd(value);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void fireStoreAdd(String entryName) {

        Map<String, Object> vehiclelist = new HashMap<>();

        vehiclelist.put("text", entryName);
        Log.d(TAG, "fireStoreAdd: " + entryName);

      /*  FirebaseFirestore.getInstance()
                .collection("")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    }
                });

        FirebaseFirestore.getInstance()
                .collection("")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    }
                });
*/
        FirebaseFirestore.getInstance().collection("VehicleNumber")
                .add(vehiclelist)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Success");
                        mCallback.onDetailsEntered(value);
                        documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");
                        e.printStackTrace();
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1 && resultCode== Activity.RESULT_OK){

           mVehicleDetails.setText(data.getStringExtra("MyString"));

        }

    }
}
