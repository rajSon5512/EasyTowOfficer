package com.knoxpo.rajivsonawala.easytow_officer.Fragment;

import com.knoxpo.rajivsonawala.easytow_officer.Activities.*;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.knoxpo.rajivsonawala.easytow_officer.R;

import java.util.ArrayList;

public class LandingFragment extends Fragment {

    private TextView mPhoneTV;
    private Button mSignOutBtn;
    private ArrayList<String> mVehicle=new ArrayList<String>();
    private RecyclerView mRecyclerView;
    private DetailsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landing, container, false);

        mRecyclerView=v.findViewById(R.id.landing_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DetailsAdapter();
        mRecyclerView.setAdapter(mAdapter);

       // init(v);

/*
        mPhoneTV.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
*/

        return v;
    }






    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_landing,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.add_button_menu){


            Intent intent=new Intent(getActivity(),EntryActivity.class);
            startActivityForResult(intent,1);
            //getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){

            if(resultCode==Activity.RESULT_OK){

                if(data.hasExtra("result")){

                    String tempString=data.getExtras().getString("result");
                    Toast.makeText(getContext(),"vehicle Number:"+data.getExtras().getString("result"),Toast.LENGTH_LONG).show();
                    mVehicle.add(tempString);
                    printArrayList();
                    mAdapter.notifyDataSetChanged();
                }

            }

        }


    }


    /*private void init(View v){
        mPhoneTV = v.findViewById(R.id.tv_phone);
        mSignOutBtn = v.findViewById(R.id.btn_sign_out);
    }
    */

    private class DetailsAdapter extends RecyclerView.Adapter<DetailVH>{

        private LayoutInflater mInflater;

        public DetailsAdapter(){

            mInflater= LayoutInflater.from(getActivity());

        }


        @NonNull
        @Override
        public DetailVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.fragment_item_view, parent, false);

            return new DetailVH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailVH holder, int position) {

            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return mVehicle.size();
        }
    }

    private class DetailVH extends RecyclerView.ViewHolder{

        TextView mIndexNumber;
        TextView mDetails;
        ImageButton mDelete;
        ImageButton mRight;


        public DetailVH(View itemView) {
            super(itemView);
            mIndexNumber=itemView.findViewById(R.id.entry_no);
            mDetails=itemView.findViewById(R.id.vehicle_details);
            mDelete=itemView.findViewById(R.id.entry_delete_button);
            mRight=itemView.findViewById(R.id.true_button);

        }


        public void bind(int position) {

            String temp=mVehicle.get(position);
            mIndexNumber.setText(mVehicle.indexOf(temp)+1);
            mDetails.setText(temp);

        }
    }

    public void printArrayList(){

        for(int i=0;i<mVehicle.size();i++){
            Log.d("temp", ": "+mVehicle.get(i));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("temp", "onDestroy: ");
    }
}
