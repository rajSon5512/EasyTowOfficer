package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.knoxpo.rajivsonawala.easytow_officer.R;

import java.util.Calendar;
import java.util.Date;

import static com.google.android.gms.flags.FlagSource.G;

public class DatePickerFragment extends DialogFragment {

    public static final String DATE = "selected_date";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance() {

        Bundle args = new Bundle();

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker,null);

        mDatePicker=v.findViewById(R.id.history_date_picker);

        return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.data_picker)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                                int year=mDatePicker.getYear();
                                int month=mDatePicker.getMonth();
                                int day=mDatePicker.getDayOfMonth();

                            Calendar calendar=Calendar.getInstance();

                            calendar.set(calendar.YEAR,year);
                            calendar.set(calendar.MONTH,month);
                            calendar.set(calendar.DAY_OF_MONTH,day);
                            sendResult(Activity.RESULT_OK,calendar);
                        }
                    }).create();
    }

    private void sendResult(int resultcode, Calendar calendar) {

        if(getTargetFragment()==null){
            return;
        }

        Intent intent=new Intent();
        intent.putExtra(DATE,calendar);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultcode,intent);

    }
}
