package com.knoxpo.rajivsonawala.easytow_officer.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.knoxpo.rajivsonawala.easytow_officer.R;

public class DatePickerFragment extends DialogFragment {

    private DatePicker datePicker;

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

        return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle(R.string.data_picker)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
    }
}
