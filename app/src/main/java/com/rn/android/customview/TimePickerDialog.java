package com.rn.android.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;


import com.metiz.pelconnect.R;

import java.util.Calendar;

public class TimePickerDialog extends DialogFragment {

    private android.app.TimePickerDialog.OnTimeSetListener listener;



    public void setListener(android.app.TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.dialog_time_picker, null);

        final TimePicker simpleTimePicker = (TimePicker)dialog.findViewById(R.id.dialog_time_picker);


        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        simpleTimePicker.setHour(hour+4);
        simpleTimePicker.setMinute(minute);


        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            }
        });

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onTimeSet(null,simpleTimePicker.getHour(),simpleTimePicker.getMinute());
                    }
                });

        return builder.create();
    }

}