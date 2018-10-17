package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.knoxpo.rajivsonawala.easytow_officer.R;

import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoryFragment extends Fragment implements View.OnClickListener {

    private static final String DIALOG_DATE = "dialog_date";
    private Button mStartButton;
    private Button mEndButton;
    private static final int REQUEST_DATE=1;

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
        return v;
    }

    private void init(View v) {

        mStartButton=v.findViewById(R.id.start_date);
        mEndButton=v.findViewById(R.id.end_date);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.start_date:
            case R.id.end_date:
                Toast.makeText(getContext(),"Click",Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager=getFragmentManager();
                DatePickerFragment datePickerFragment=DatePickerFragment.newInstance();
                datePickerFragment.setTargetFragment(this,REQUEST_DATE);
                datePickerFragment.show(fragmentManager,DIALOG_DATE);
                break;


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_DATE && resultCode== Activity.RESULT_OK){

            Calendar calendar=(Calendar)data.getSerializableExtra("selected_date");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
            Date date1=calendar.getTime();
            mStartButton.setText(simpleDateFormat.format(date1));

        }

    }
}
