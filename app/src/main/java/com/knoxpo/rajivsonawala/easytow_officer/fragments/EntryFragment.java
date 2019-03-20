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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.activities.OcrCaptureActivity;
import com.knoxpo.rajivsonawala.easytow_officer.models.Fine;
import com.knoxpo.rajivsonawala.easytow_officer.models.NormalUser;
import com.knoxpo.rajivsonawala.easytow_officer.models.SmcParking;
import com.knoxpo.rajivsonawala.easytow_officer.models.Ticket;
import com.knoxpo.rajivsonawala.easytow_officer.models.Transactions;
import com.knoxpo.rajivsonawala.easytow_officer.models.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    /**
     * static declarations
     */
    private static final String TAG = EntryFragment.class.getSimpleName();

    private static final int REQUEST_CAMERA_INFO =1;
    private NormalUser mNormalUser;
    private Spinner mSpinner;
    private List<String> destination= new ArrayList<>();

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(getContext(), ""+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onItemSelected: "+adapterView.getItemAtPosition(i));

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface Callback {
        void onDetailsEntered(String ticketId);
    }


    private Callback mCallback;

    /**
     * Views variables
     */
    private EditText mVehicleDetailsET;
    private ImageButton mImageButton;
    private TextView mOwnerNameTV, mOwnerMobileNumberTV, mVehicleTypeTV, mFineTV;
    private Button mFetchInfoBtn;

    /**
     * Models
     */
    private Vehicle mVehicle;
    private Fine mFine;

    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private DocumentReference documentReference;


    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.camera_Button:
                Intent intent=new Intent(getActivity(),OcrCaptureActivity.class);
                startActivityForResult(intent, REQUEST_CAMERA_INFO);
                break;

            case R.id.fetch_info_button:

                String vehicleNumber = mVehicleDetailsET.getText().toString();

                documentReference=db.collection("vehicles").document(vehicleNumber);

                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if(task.isSuccessful()){

                                final DocumentSnapshot documentSnapshot=task.getResult();

                                if(documentSnapshot.exists()){

                                    mVehicle =new Vehicle(documentSnapshot);

                                    /*Log.d(TAG, "owner_name: "+ mVehicle.getmOwnerName());
                                    Log.d(TAG, "onComplete: "+mVehicle.getUUID());
*/

                                    db.collection(NormalUser.COLLECTION_NAME).document(mVehicle.getUUID())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                DocumentSnapshot documentSnapshot1=task.getResult();

                                                if(documentSnapshot1.exists()){

                                                    mNormalUser=new NormalUser(documentSnapshot1);

                                                    Log.d(TAG, "newData: "+mNormalUser.getmOwnerName());
                                                    Log.d(TAG, "newData: "+mNormalUser.getmVehicleType());
                                                    Log.d(TAG, "newData: "+mNormalUser.getId());
                                                    Log.d(TAG, "newData: "+mNormalUser.getmMobileNumber());
                                                    Log.d(TAG, "newData: "+mNormalUser.getmVehicleNumber());

                                                    db.collection(Fine.COLLECTION_NAME).document
                                                            (String.valueOf(mNormalUser.getmVehicleType())
                                                    )
                                                            .get()
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    mFine = new Fine(documentSnapshot);
                                                                    mOwnerNameTV.setText(mNormalUser.getmOwnerName());
                                                                    mOwnerMobileNumberTV.setText(mNormalUser.getmMobileNumber());
                                                                    mVehicleTypeTV.setText(String.valueOf(mNormalUser.getmVehicleType()));
                                                                    mFineTV.setText(mFine.getFine()+"");
                                                                }
                                                            });

                                                    Log.d(TAG, "Fetch_Info:Completed");
                                                    getActivity().invalidateOptionsMenu();
                                                }


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });


                                }
                                else {

                                    Log.d(TAG, "Fetch_Info:Failed ");
                                    getActivity().invalidateOptionsMenu();
                                    Toast.makeText(getContext(),"Vehicle Not Register.",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Log.d(TAG, "Fetch_Info:Failed ");
                                Toast.makeText(getContext(),"Vehicle Not Register.",Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                break;
        }
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
        collectionReference=db.collection("tickets");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry, container, false);
        init(v);

        mImageButton.setOnClickListener(this);
        mFetchInfoBtn.setOnClickListener(this);
        //mSpinner.setOnItemSelectedListener(this);

        //destination=new ArrayList<>();

        //destination=fetchSmcDestinations();

        //ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,destination);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, destination);

        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(arrayAdapter1);

        mSpinner.setOnItemSelectedListener(this);

        fetchSmcDestinations();


        return v;
    }

    private void fetchSmcDestinations() {

       // final List<String> smcParkingslist=new ArrayList<>();

       FirebaseFirestore.getInstance().collection(SmcParking.COLLECTION_NAME)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {

                       if(task.isSuccessful()){

                           QuerySnapshot querySnapshot=task.getResult();

                           List<DocumentSnapshot> documentSnapshot=querySnapshot.getDocuments();

                           destination.clear();
                           for(DocumentSnapshot document:documentSnapshot){

                               SmcParking smcParking=new SmcParking(document);
                               destination.add(smcParking.getName());
                               Log.d(TAG, "onComplete: "+smcParking.getName());

                               ((ArrayAdapter)mSpinner.getAdapter()).notifyDataSetChanged();
                           }

                       }

                   }
               });


    //return smcParkingslist;
    }

    private void init(View v) {

        mVehicleDetailsET = v.findViewById(R.id.vehicle_details_box);
        mImageButton=v.findViewById(R.id.camera_Button);
        mFetchInfoBtn =v.findViewById(R.id.fetch_info_button);
        mVehicleTypeTV =v.findViewById(R.id.vehicle_type);
        mOwnerNameTV =v.findViewById(R.id.owner_name);
        mOwnerMobileNumberTV =v.findViewById(R.id.owner_mobile_number);
        mFineTV =v.findViewById(R.id.fine);
        mSpinner=v.findViewById(R.id.locationselecter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_entry_activity, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem=menu.findItem(R.id.entry_done_button);
        menuItem.setVisible(mVehicle!=null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.entry_done_button:
                fireStoreAdd();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void fireStoreAdd() {

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> ticketData = new HashMap<>();

        ticketData.put(Ticket.FIELD_VEHICLE_ID, mVehicle.getId());
        ticketData.put(Ticket.FIELD_RAISED_BY, uid);
        ticketData.put(Ticket.FIELD_DATE, FieldValue.serverTimestamp());
        ticketData.put(Ticket.FIELD_CURRENT_STATUS, "pending");
        ticketData.put(Ticket.FIELD_FINE, mFine.getFine());
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
        FirebaseFirestore.getInstance().collection(Ticket.COLLECTION_NAME)
                .add(ticketData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Success");
                        mCallback.onDetailsEntered(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");
                        e.printStackTrace();
                    }
                });


        //fireTickets(mVehicle.getId(),mFine.getFine());

    }

    private void fireTickets(String vehicleNumber, double fine) {

        Map<String,String> transaction=new HashMap<String,String>();

        Date date1=new Date();
        transaction.put(Transactions.DATE,date1.toString());
        transaction.put(Transactions.STATUS,"Pending");
        transaction.put(Transactions.TAXAMOUNT,""+fine);
        transaction.put(Transactions.VNUMBER,vehicleNumber);


        FirebaseFirestore.getInstance().collection(Transactions.COLLECTION_NANE)
                .add(transaction).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if(task.isSuccessful()){

                    Log.d("fireTickets", "Transaction transfer ");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("fireTickets", "onFailure: "+e.getMessage());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1 && resultCode== Activity.RESULT_OK){

           mVehicleDetailsET.setText(data.getStringExtra("MyString"));

        }

    }



}
