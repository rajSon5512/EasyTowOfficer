package com.knoxpo.rajivsonawala.easytow_officer.Fragment;

import android.content.Context;
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

import com.knoxpo.rajivsonawala.easytow_officer.R;

import static android.support.constraint.Constraints.TAG;

public class EntryFragment extends Fragment {


    public static final String TEMP=""+EntryFragment.class.getSimpleName();
    private EditText mVehicleDetails;
    private Callback mCallback;


    public interface Callback{

        void getDetails(String text);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback=(Callback) getContext();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback=null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.entry_page,container,false);

        mVehicleDetails=v.findViewById(R.id.vehicle_details_box);

      return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_entry_activity,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.entry_done_button:

                String value=mVehicleDetails.getText().toString();
                Log.d(TEMP, "onOptionsItemSelected: "+value);
                mCallback.getDetails(value);

                return true;

             default:
                 return super.onOptionsItemSelected(item);


        }


    }
}
