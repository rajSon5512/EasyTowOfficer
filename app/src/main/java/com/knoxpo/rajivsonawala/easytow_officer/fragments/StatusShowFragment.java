package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knoxpo.rajivsonawala.easytow_officer.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class StatusShowFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = StatusShowFragment.class.getSimpleName();
    private static final String ARGS_STATUS = TAG + ".ARGS_STATUS";
    private static final String ARGS_DOCUMENT_ID = TAG + ".ARGS_DOCUMENT_ID";

    public static final String EXTRA_DOCUMENT_ID = TAG + ".EXTRA_DOCUMENT_ID";

    private Spinner  mSpinner;
    private EditText mNoteInfo;
    private TextView mNoteView,mStatus;

    public static StatusShowFragment newInstance(String status, String editingDocumentId) {


        Log.d(TAG, "newInstance: "+status);
        Bundle args = new Bundle();
        args.putString(ARGS_STATUS,status);
        args.putString(ARGS_DOCUMENT_ID, editingDocumentId);
        StatusShowFragment fragment = new StatusShowFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_status,null);

        init(view);

        String showStatus=getArguments().getString(ARGS_STATUS);

        List<String> mStatusList=new ArrayList<String>();
        mStatusList.add("pending");
        mStatusList.add("paid");
        mStatusList.add("After");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,mStatusList);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(arrayAdapter);


        for(int i=0;i<mStatusList.size();i++){

            if(showStatus.matches(mStatusList.get(i))){

                mSpinner.setSelection(i);

            }

        }

        mSpinner.setOnItemSelectedListener(this);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.vehicle_status)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getContext(),"OK",Toast.LENGTH_SHORT).show();

                        sendResult();


                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getContext(),"Cancel",Toast.LENGTH_SHORT).show();

                    }
                }).create();

    }

    private void sendResult() {

        String selectedItem = (String) mSpinner.getSelectedItem();

        Log.d(TAG, "sendResult: "+selectedItem);

        Intent intent=new Intent();

        intent.putExtra(EXTRA_DOCUMENT_ID, getArguments().getString(ARGS_DOCUMENT_ID));
        intent.putExtra(Intent.EXTRA_RETURN_RESULT,selectedItem);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);

    }

    private void init(View view) {
           mSpinner=view.findViewById(R.id.status_spinner);
           mNoteInfo=view.findViewById(R.id.note_information);
           mStatus=view.findViewById(R.id.vehicle_status_view);
           mNoteView=view.findViewById(R.id.note_view);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
