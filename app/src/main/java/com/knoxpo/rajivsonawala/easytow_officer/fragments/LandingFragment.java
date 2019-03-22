package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.activities.EntryActivity;
import com.knoxpo.rajivsonawala.easytow_officer.models.NormalUser;
import com.knoxpo.rajivsonawala.easytow_officer.models.Transactions;
import com.knoxpo.rajivsonawala.easytow_officer.models.Vehicle;
import com.knoxpo.rajivsonawala.easytow_officer.models.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;


public class LandingFragment extends Fragment {

    private static final String STATE_TICKETS = "STATE_TICKETS";

    private static final int
            REQUEST_NEW_ENTRY = 0;

    private static final int REQUEST_FOR_STATUS = 1;

    private static final String DIALOG_STATUS = "status";

    private ArrayList<Ticket> mTickets = new ArrayList<Ticket>();
    private RecyclerView mRecyclerView;
    private DetailsAdapter mAdapter;
    private CollectionReference collectionReference;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_landing, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button_menu:
                Intent intent = new Intent(getActivity(), EntryActivity.class);
                startActivityForResult(intent, REQUEST_NEW_ENTRY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {

            // Toast.makeText(getContext(),"hello",Toast.LENGTH_SHORT).show();
            //mTickets = savedInstanceState.getParcelableArrayList(STATE_TICKETS);

        }


        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(Ticket.COLLECTION_NAME);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landing, container, false);

        init(v);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM, 1);

        Date date = calendar.getTime();

        Log.d(TAG, "onViewCreated: " + date);

        collectionReference
                .whereEqualTo(Ticket.FIELD_RAISED_BY, uid)
                .whereEqualTo(Ticket.FIELD_CURRENT_STATUS,Ticket.DEFUALT_STATUS)
                //.whereGreaterThan(Ticket.FIELD_DATE,date)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        final List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                        Log.d(TAG, "onSuccess: " + documents.size());

                        for (int i = 0; i < documents.size(); i++) {

                            final Ticket ticket = new Ticket(documents.get(i));

                            FirebaseFirestore.getInstance()
                                    .collection(Vehicle.COLLECTION_NAME)
                                    .document(ticket.getVehicleId())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            /*Vehicle vehicle = new Vehicle(documentSnapshot);
                                            ticket.setVehicle(vehicle);
                                            */

                                            String id=documentSnapshot.getString("owner_id");

                                            FirebaseFirestore.getInstance()
                                                    .collection(NormalUser.COLLECTION_NAME)
                                                    .document(id)
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                     @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {


                                                            NormalUser normalUser=new NormalUser(documentSnapshot);
                                                            ticket.setVehicle(normalUser);
                                                            mRecyclerView.getAdapter().notifyDataSetChanged();

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Log.d(TAG, "onFailure: Failed");
                                                }
                                            });

                                        }
                                    });
                            mTickets.add(ticket);



                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, e.getMessage());
                    }
                });


    }

    private void init(View v) {
        mRecyclerView = v.findViewById(R.id.rv_entry);
        mAdapter = new DetailsAdapter();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_NEW_ENTRY && resultCode == Activity.RESULT_OK && data.hasExtra(Intent.EXTRA_RETURN_RESULT)) {

           String ticketId = data.getStringExtra(Intent.EXTRA_RETURN_RESULT);
         //   mTickets.add(vehicle);

            Log.d(TAG, "onActivityResult: "+ticketId);

            FirebaseFirestore.getInstance()
                    .collection(Ticket.COLLECTION_NAME)
                    .document(ticketId)
                  .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            final Ticket ticket = new Ticket(documentSnapshot);

                            FirebaseFirestore.getInstance()
                                    .collection(Vehicle.COLLECTION_NAME)
                                    .document(ticket.getVehicleId())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                      /*      Vehicle vehicle = new Vehicle(documentSnapshot);
                                      */

                                            String id=documentSnapshot.getString("owner_id");

                                            FirebaseFirestore.getInstance().collection
                                                    (NormalUser.COLLECTION_NAME)
                                                    .document(id)
                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                    NormalUser vehicle=new NormalUser(documentSnapshot);
                                                    ticket.setVehicle(vehicle);
                                                    mTickets.add(0, ticket);
                                                    //mAdapter.notifyDataSetChanged();
                                                    mAdapter.notifyItemInserted(0);

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Log.d(TAG, "onFailure: "+e.getMessage());
                                                }
                                            });

                                        }
                                    });
                        }
                    });

            printArrayList();

            /*String entryName = data.getStringExtra(Intent.EXTRA_RETURN_RESULT);
            Toast.makeText(getContext(), "vehicle Number:" + data.getStringExtra(Intent.EXTRA_RETURN_RESULT), Toast.LENGTH_LONG).show();
            mVehicles.add(entryName);
            printArrayList();
            mAdapter.notifyDataSetChanged();*/
        } else if (requestCode == REQUEST_FOR_STATUS && resultCode == Activity.RESULT_OK && data.hasExtra(Intent.EXTRA_RETURN_RESULT)) {

            String newStatus = data.getStringExtra(Intent.EXTRA_RETURN_RESULT);
            final String documentId = data.getStringExtra(StatusShowFragment.EXTRA_DOCUMENT_ID);

            Log.d(TAG, "onActivityResult: "+newStatus);

            int indexToChange = -1;
            for(int i=0;i<mTickets.size();i++){
                if(mTickets.get(i).getId().equals(documentId)){
                    //Ticket found to update status on
                    mTickets.get(i).setCurrentStatus(newStatus);
                    indexToChange = i;
                    break;
                }
            }

            FirebaseFirestore.getInstance().collection(Ticket.COLLECTION_NAME)
                    .document(documentId)
                    .update(Ticket.FIELD_CURRENT_STATUS, newStatus);

            FirebaseFirestore.getInstance().collection(Ticket.COLLECTION_NAME)
                    .document(documentId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if(task.isSuccessful()){

                                DocumentSnapshot documentSnapshot=task.getResult();

                                FirebaseFirestore.getInstance().collection(Transactions.COLLECTION_NANE)
                                        .whereEqualTo(Transactions.DATE,documentSnapshot.get(Ticket.FIELD_DATE))
                                        .whereEqualTo (Transactions.VNUMBER,documentSnapshot.getString(Ticket.FIELD_VEHICLE_ID))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                if(task.isSuccessful()){

                                                    QuerySnapshot transactionDocument=task.getResult();

                                                    List<DocumentSnapshot> transactionDocumentDocuments=transactionDocument.getDocuments();


                                                    Log.d(TAG, "onComplete: "+transactionDocumentDocuments.size());


/*
                                                    for(int i=0;i<transactionDocumentDocuments.size();i++){

                                                        String tid=transactionDocumentDocuments.get(i).getId();

                                                        Log.d(TAG, "onTransactionId: "+tid);

                                                    }
*/


                                                }


                                            }
                                        });


                            }

                        }
                    });


            if(indexToChange >= 0){
                mAdapter.notifyItemChanged(indexToChange);
            }
        }

    }

    private class DetailsAdapter extends RecyclerView.Adapter<DetailVH> {

        private LayoutInflater mInflater;

        DetailsAdapter() {
            mInflater = LayoutInflater.from(getActivity());
        }


        @NonNull
        @Override
        public DetailVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.item_entry, parent, false);
            return new DetailVH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailVH holder, int position) {
            holder.bind(mTickets.get(position));
        }

        @Override
        public int getItemCount() {
            return mTickets.size();
        }
    }

    private class DetailVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mIndexNumber;
        private TextView mDetails;
        private ImageButton mDelete;
        private ImageButton mRight;
        private TextView mOwnerName, mMobileNumber, mDate;
        private Button mStatus;
        private Ticket mBoundTicket;

        DetailVH(View itemView) {
            super(itemView);
            mIndexNumber = itemView.findViewById(R.id.entry_no);
            mDetails = itemView.findViewById(R.id.vehicle_details);
            mDelete = itemView.findViewById(R.id.entry_delete_button);
            mRight = itemView.findViewById(R.id.true_button);
            mMobileNumber = itemView.findViewById(R.id.mobile_number);
            mOwnerName = itemView.findViewById(R.id.owner_name_view);
            mDate = itemView.findViewById(R.id.date_and_time);
            mDelete.setOnClickListener(this);
            mStatus = itemView.findViewById(R.id.vehicle_status);
            mStatus.setOnClickListener(this);
            mRight.setOnClickListener(this);

        }


        public void bind(Ticket ticket) {
            mBoundTicket = ticket;

            mIndexNumber.setText(String.valueOf(getAdapterPosition()+1));
            NormalUser vehicle = ticket.getVehicle();
            mDetails.setText(vehicle.getmVehicleNumber());
            mMobileNumber.setText(vehicle.getmMobileNumber());
            Log.d(TAG, "bind: " + vehicle.getmOwnerName());
            Log.d(TAG, "bind: " + vehicle.getmMobileNumber());
            mOwnerName.setText(vehicle.getmOwnerName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            mDate.setText(simpleDateFormat.format(ticket.getDate()));
            mStatus.setText(ticket.getCurrentStatus());
        }


        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.entry_delete_button:

                    if(mTickets.remove(getAdapterPosition())!=null){

                        mAdapter.notifyItemRemoved(getAdapterPosition());
                        collectionReference.document(mBoundTicket.getId()).delete();
                      }

                    break;

                case R.id.vehicle_status:
                    FragmentManager fragmentManager = getFragmentManager();
                    StatusShowFragment statusShowFragment = StatusShowFragment.newInstance(
                            mBoundTicket.getCurrentStatus(),
                            mBoundTicket.getId()
                    );
                    statusShowFragment.setTargetFragment(LandingFragment.this, REQUEST_FOR_STATUS);
                    statusShowFragment.show(fragmentManager, DIALOG_STATUS);

                    if(mTickets.remove(getAdapterPosition())!=null){

                        FirebaseFirestore.getInstance().collection(Ticket.COLLECTION_NAME)
                                .document(mBoundTicket.getId())
                                .update(Ticket.FIELD_CURRENT_STATUS,"PAID");

                        fireTickets(mBoundTicket.getVehicleId(),mBoundTicket.getFine(),"PAID");


                        mAdapter.notifyItemRemoved(getAdapterPosition());

                    }
                    
                    break;

                case R.id.true_button:
                    Toast.makeText(getContext(),"Raj",Toast.LENGTH_SHORT).show();


                    if(mTickets.remove(getAdapterPosition())!=null){

                        FirebaseFirestore.getInstance().collection(Ticket.COLLECTION_NAME)
                                .document(mBoundTicket.getId())
                                .update(Ticket.FIELD_CURRENT_STATUS,"PAID");

                        fireTickets(mBoundTicket.getVehicleId(),mBoundTicket.getFine(),"PAID");


                        mAdapter.notifyItemRemoved(getAdapterPosition());

                    }

                    /*//mAdapter.notifyDataSetChanged();

                   // mTickets.remove(getAdapterPosition());

                    mAdapter.notifyItemRemoved(getAdapterPosition());

                    *//* mRight.setEnabled(false);
                    mDelete.setEnabled(false);
                    mStatus.setText("PAID");
                    mStatus.setEnabled(false);*//*
*/

                    break;

            }
        }
    }


    private void fireTickets(String vehicleNumber, double fine,String status) {

        Map<String,String> transaction=new HashMap<String,String>();

        Date date1=new Date();
        transaction.put(Transactions.DATE,date1.toString());
        transaction.put(Transactions.STATUS,status);
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

        mAdapter.notifyDataSetChanged();
    }

    public void printArrayList() {

        for (int i = 0; i < mTickets.size(); i++) {
            Log.d("temp", ": " + mTickets.get(i));
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //outState.putParcelableArrayList(STATE_TICKETS, mTickets);
        super.onSaveInstanceState(outState);
    }
}
