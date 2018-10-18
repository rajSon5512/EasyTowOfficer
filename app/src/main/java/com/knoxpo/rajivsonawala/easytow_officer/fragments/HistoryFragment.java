package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.knoxpo.rajivsonawala.easytow_officer.R;

import java.net.DatagramPacket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class HistoryFragment extends Fragment implements View.OnClickListener {

    private static final String DIALOG_DATE = "dialog_date";
    private Button mStartButton;
    private Button mEndButton;
    private static final int REQUEST_START_DATE=0;
    private static final int REQUEST_END_DATE=1;
    private ImageButton mGoButton;

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
        return v;
    }

    private void init(View v) {

        mStartButton=v.findViewById(R.id.start_date);
        mEndButton=v.findViewById(R.id.end_date);
        mGoButton=v.findViewById(R.id.go_button);
    }

    @Override
    public void onClick(View view) {

        FragmentManager fragmentManager;
        DatePickerFragment datePickerFragment;

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

                Date startDate=null;
                Date endDate=null;
                DateFormat format=new SimpleDateFormat("dd-MM-yyyy");
                try {

                     endDate=format.parse(mEndButton.getText().toString());
                     startDate=format.parse(mStartButton.getText().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if((startDate.before(endDate) || startDate.equals(endDate)) && (endDate.before(new Date()) || endDate.equals(new Date()))){

                    Toast.makeText(getContext(),"TRUE",Toast.LENGTH_SHORT).show();


                    }else{

                        Toast.makeText(getContext(),"Invalid Date Selected.",Toast.LENGTH_SHORT).show();

                    }

                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Calendar calendar=(Calendar)data.getSerializableExtra("selected_date");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
        Date date1=calendar.getTime();

        if(requestCode==REQUEST_START_DATE && resultCode== Activity.RESULT_OK){

            mStartButton.setText(simpleDateFormat.format(date1));

        }else if(requestCode==REQUEST_END_DATE && resultCode==Activity.RESULT_OK){

             mEndButton.setText(simpleDateFormat.format(date1));

        }
    }

}
