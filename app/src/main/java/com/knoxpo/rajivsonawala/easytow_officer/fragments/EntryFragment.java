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

import com.knoxpo.rajivsonawala.easytow_officer.R;
import com.knoxpo.rajivsonawala.easytow_officer.activities.OcrCaptureActivity;

public class EntryFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = EntryFragment.class.getSimpleName();

    private EditText mVehicleDetails;
    private Callback mCallback;
    private ImageButton mImageButton;
    private int requestcode=1;

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
                String value = mVehicleDetails.getText().toString();
                Log.d(TAG, "onOptionsItemSelected: " + value);
                mCallback.onDetailsEntered(value);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1 && resultCode== Activity.RESULT_OK){

           mVehicleDetails.setText(data.getStringExtra("MyString"));

        }

    }
}
