package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.models.NormalUser;
import com.knoxpo.rajivsonawala.easytow_officer.models.Ticket;
import com.knoxpo.rajivsonawala.easytow_officer.models.Vehicle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class HistoryFragment extends Fragment implements View.OnClickListener {

    private static final String DIALOG_DATE = "dialog_date";
    private Button mStartButton;
    private Button mEndButton;
    private static final int REQUEST_START_DATE=0;
    private static final int REQUEST_END_DATE=1;
    private ImageButton mGoButton;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList mVehicleHistoryList=new ArrayList();
    private Date mDateForEntry = null;
    private ArrayList<Date>  dateSequence=new ArrayList<Date>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_history,container,false);
        init(v);

        mStartButton.setOnClickListener(this);
        mEndButton.setOnClickListener(this);
        mGoButton.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new HistoryAdapter());

        return v;
    }

    private void init(View v) {

        mStartButton=v.findViewById(R.id.start_date);
        mEndButton=v.findViewById(R.id.end_date);
        mGoButton=v.findViewById(R.id.go_button);
        mRecyclerView=v.findViewById(R.id.rv_entry);
    }


    public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder>{

        private LayoutInflater inflater;

        public HistoryAdapter(){

            inflater=LayoutInflater.from(getActivity());

        }

        @NonNull
        @Override
        public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v=inflater.inflate(R.layout.item_entry,parent,false);
            return new HistoryViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
            holder.bind((NormalUser) mVehicleHistoryList.get(position));
        }

        @Override
        public int getItemCount() {
            return mVehicleHistoryList.size();
        }
    }


    public class HistoryViewHolder  extends RecyclerView.ViewHolder{

        private TextView mIndexNumber;
        private TextView mDetails;
        private ImageButton mDelete;
        private ImageButton mRight;
        private TextView mOwnerName, mMobileNumber, mDate;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            mIndexNumber = itemView.findViewById(R.id.entry_no);
            mDetails = itemView.findViewById(R.id.vehicle_details);
            mDelete = itemView.findViewById(R.id.entry_delete_button);
            mRight = itemView.findViewById(R.id.true_button);
            mMobileNumber = itemView.findViewById(R.id.mobile_number);
            mOwnerName = itemView.findViewById(R.id.owner_name_view);
            mDate = itemView.findViewById(R.id.date_and_time);

            mRight.setVisibility(itemView.GONE);
            mDelete.setVisibility(itemView.GONE);
        }

        public void bind(NormalUser vehicle){

            mIndexNumber.setText(String.valueOf(getAdapterPosition() + 1));

            mDetails.setText(vehicle.getmVehicleNumber());
            mMobileNumber.setText(vehicle.getmMobileNumber());
            mOwnerName.setText(vehicle.getmOwnerName());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
             mDate.setText(simpleDateFormat.format(dateSequence.get(getAdapterPosition())));

        }


    }




    @Override
    public void onClick(View view) {

        FragmentManager fragmentManager;
        final DatePickerFragment datePickerFragment;

        switch (view.getId()){

            case R.id.start_date:

                Toast.makeText(getContext(),"Click",Toast.LENGTH_SHORT).show();
                fragmentManager=getFragmentManager();
                datePickerFragment=DatePickerFragment.newInstance();
                datePickerFragment.setTargetFragment(this,REQUEST_START_DATE);
                datePickerFragment.show(fragmentManager,DIALOG_DATE);
                break;

            case R.id.end_date:
                Toast.makeText(getContext(),"Click",Toast.LENGTH_SHORT).show();
                fragmentManager=getFragmentManager();
                datePickerFragment=DatePickerFragment.newInstance();
                datePickerFragment.setTargetFragment(this,REQUEST_END_DATE);
                datePickerFragment.show(fragmentManager,DIALOG_DATE);
                break;

            case R.id.go_button:

                if(mVehicleHistoryList.size()!=0){

                    mVehicleHistoryList.clear();
                    mRecyclerView.getAdapter().notifyDataSetChanged();

                }


                Date startDate=null;
                Date endDate=null;
                final DateFormat format=new SimpleDateFormat("dd-MM-yyyy");
                try {

                     endDate=format.parse(mEndButton.getText().toString());
                     startDate=format.parse(mStartButton.getText().toString());

                     Calendar calendar=Calendar.getInstance();
                     calendar.setTime(endDate);

                     calendar.set(Calendar.HOUR,24);
                     calendar.set(Calendar.MINUTE,00);
                     calendar.set(Calendar.SECOND,00);

                     endDate=calendar.getTime();

                    Log.d(TAG, "DATE: "+startDate+" End Date"+endDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if((startDate.before(endDate) || (startDate.equals(endDate)) && (endDate.before(new Date())) || endDate.equals(new Date()))){

                    Log.d(TAG, "Start Date: "+startDate+"End date:"+endDate);

                    Toast.makeText(getContext(),"TRUE",Toast.LENGTH_SHORT).show();

                    FirebaseFirestore.getInstance().collection(Ticket.COLLECTION_NAME)
                            .whereGreaterThanOrEqualTo(Ticket.FIELD_DATE,startDate)
                            .whereLessThanOrEqualTo(Ticket.FIELD_DATE,endDate)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<DocumentSnapshot> documentSnapshots=queryDocumentSnapshots.getDocuments();
                            Log.d(TAG, "Documents Size: "+documentSnapshots.size());

                            for(int i=0;i<documentSnapshots.size();i++){

                                String vehiclenumber=documentSnapshots.get(i).get(Ticket.FIELD_VEHICLE_ID).toString();
                                final Date documentDate=(Date)documentSnapshots.get(i).get("date");

                                Log.d(TAG, "documentDate: "+documentDate);
                                dateSequence.add(documentDate);

                                FirebaseFirestore.getInstance().collection(Vehicle.COLLECTION_NAME)
                                        .document(vehiclenumber).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshotForVehicle) {

                                                String id=documentSnapshotForVehicle.getString("owner_id");

                                                FirebaseFirestore.getInstance()
                                                        .collection(NormalUser.COLLECTION_NAME)
                                                        .document(id)
                                                        .get()
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                                NormalUser vehicle=new NormalUser(documentSnapshot);
                                                                //      vehicle.setDate(documentDate);
                                                                mVehicleHistoryList.add(vehicle);
                                                                mRecyclerView.getAdapter().notifyDataSetChanged();
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
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            e.printStackTrace();
                        }

                    });


                }else{

                        Toast.makeText(getContext(),"Invalid Date Selected.",Toast.LENGTH_SHORT).show();

                    }

                Log.d(TAG, "cases : 1"+startDate.before(endDate)+" 2" +startDate.equals(endDate)+" 3"+endDate.before(new Date())+" 4"+endDate.equals(new Date()));

                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Calendar calendar=(Calendar)data.getSerializableExtra(DatePickerFragment.DATE);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
        Date date1=calendar.getTime();

        if(requestCode==REQUEST_START_DATE && resultCode== Activity.RESULT_OK){

            mStartButton.setText(simpleDateFormat.format(date1));

        }else if(requestCode==REQUEST_END_DATE && resultCode==Activity.RESULT_OK){

             mEndButton.setText(simpleDateFormat.format(date1));

        }
    }

}
;