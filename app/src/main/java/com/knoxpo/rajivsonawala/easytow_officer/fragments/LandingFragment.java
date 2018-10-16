package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.activities.EntryActivity;
import com.knoxpo.rajivsonawala.easytow_officer.models.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;


public class LandingFragment extends Fragment {

    private static final int
            REQUEST_NEW_ENTRY = 0;

    private ArrayList mVehicles = new ArrayList();
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

        if(savedInstanceState!=null){

           // Toast.makeText(getContext(),"hello",Toast.LENGTH_SHORT).show();
            mVehicles=savedInstanceState.getParcelableArrayList("myvehicles");

        }


        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("tickets");

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


        String uuid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        Timestamp timestamp=new Timestamp(new Date());

        collectionReference.whereEqualTo("raised_by",uuid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                        Log.d(TAG, "onSuccess: "+documents.size());

                        for(int i=0;i<documents.size();i++){

                            Log.d(TAG, "onSuccess: "+documents.size());

                            Log.d(TAG, "onSuccess: "+documents.get(i).get("vehicle_id"));
                            String vehicleNumber=documents.get(i).get("vehicle_id").toString();
                            firebaseFirestore.collection("vehicles").document(vehicleNumber)
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    Entry entry=new Entry(documentSnapshot);

                                    mVehicles.add(entry);
                                    mRecyclerView.getAdapter().notifyDataSetChanged();

                                }
                            });


                        }
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

            Entry entry=data.getParcelableExtra(Intent.EXTRA_RETURN_RESULT);
             mVehicles.add(entry);
            printArrayList();
            mAdapter.notifyDataSetChanged();

            /*String entryName = data.getStringExtra(Intent.EXTRA_RETURN_RESULT);
            Toast.makeText(getContext(), "vehicle Number:" + data.getStringExtra(Intent.EXTRA_RETURN_RESULT), Toast.LENGTH_LONG).show();
            mVehicles.add(entryName);
            printArrayList();
            mAdapter.notifyDataSetChanged();*/
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
            holder.bind((Entry)mVehicles.get(position));
        }

        @Override
        public int getItemCount() {
            return mVehicles.size();
        }
    }

    private class DetailVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mIndexNumber;
        private TextView mDetails;
        private ImageButton mDelete;
        private ImageButton mRight;
        private TextView mOwnerName,mMobileNumber,mDate;



        DetailVH(View itemView) {
            super(itemView);
            mIndexNumber = itemView.findViewById(R.id.entry_no);
            mDetails = itemView.findViewById(R.id.vehicle_details);
            mDelete = itemView.findViewById(R.id.entry_delete_button);
            mRight = itemView.findViewById(R.id.true_button);
            mMobileNumber=itemView.findViewById(R.id.mobile_number);
            mOwnerName=itemView.findViewById(R.id.owner_name_view);
            mDate=itemView.findViewById(R.id.date_and_time);
            mDelete.setOnClickListener(this);

        }


        public void bind(Entry entry) {


            mIndexNumber.setText(String.valueOf(getAdapterPosition() + 1));
            mDetails.setText(entry.getmVehicleNumber());
            mMobileNumber.setText(entry.getmMobileNumber());
            Log.d(TAG, "bind: "+entry.getmOwnerName());
            Log.d(TAG, "bind: "+entry.getmMobileNumber());
            mOwnerName.setText(entry.getmOwnerName());

        }



        @Override
        public void onClick(View v) {
            mVehicles.remove(getAdapterPosition());
            //mAdapter.notifyItemRemoved(getAdapterPosition());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void printArrayList() {

        for (int i = 0; i < mVehicles.size(); i++) {
            Log.d("temp", ": " + mVehicles.get(i));
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("myvehicles",mVehicles);

    }



}
