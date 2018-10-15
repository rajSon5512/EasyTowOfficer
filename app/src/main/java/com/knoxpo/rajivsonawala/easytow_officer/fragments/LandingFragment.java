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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.activities.EntryActivity;
import com.knoxpo.rajivsonawala.easytow_officer.models.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;


public class LandingFragment extends Fragment {

    private static final int
            REQUEST_NEW_ENTRY = 0;

    private ArrayList mVehicles = new ArrayList();
    private RecyclerView mRecyclerView;
    private DetailsAdapter mAdapter;
    private FirebaseFirestore db;
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

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Vehicles");

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

    private void init(View v) {
        mRecyclerView = v.findViewById(R.id.rv_entry);
        mAdapter = new DetailsAdapter();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NEW_ENTRY && resultCode == Activity.RESULT_OK && data.hasExtra(Intent.EXTRA_RETURN_RESULT)) {

            Entry entry=data.getParcelableExtra(Intent.EXTRA_RETURN_RESULT);

            mVehicles.add(entry.getmVehicleNumber());
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
            holder.bind((String) mVehicles.get(position));
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


        DetailVH(View itemView) {
            super(itemView);
            mIndexNumber = itemView.findViewById(R.id.entry_no);
            mDetails = itemView.findViewById(R.id.vehicle_details);
            mDelete = itemView.findViewById(R.id.entry_delete_button);
            mRight = itemView.findViewById(R.id.true_button);
            mDelete.setOnClickListener(this);

        }


        public void bind(String entry) {
            mIndexNumber.setText(String.valueOf(getAdapterPosition() + 1));
            mDetails.setText(entry);
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

  /*  @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("myVehicle",mVehicles);

    }*/
}
