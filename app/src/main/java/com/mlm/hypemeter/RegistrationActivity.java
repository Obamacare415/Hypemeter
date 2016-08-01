package com.mlm.hypemeter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Created by Martin on 2/15/2016.
 */
public class RegistrationActivity extends Activity {

    private TextView displayDate;
    private int pickerYear;
    private int pickerMonth;
    private int pickerDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        displayDate = (TextView) findViewById(R.id.bday_shown);

        Button reggedButton = (Button) findViewById(R.id.reg_button);

        reggedButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                finish();
            }
        });

        TextView alreadyReg = (TextView) findViewById(R.id.already_reg);

        alreadyReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

                finish();
            }
        });

        Button bdayButton = (Button) findViewById(R.id.bday_button);

        bdayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickFragment();
                newFragment.show(getFragmentManager(), "datePicker");

            }

            class DatePickFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

                    @Override
                    public Dialog onCreateDialog(Bundle savedInstanceState) {
                        // Use the current date as the default date in the picker
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // Create a new instance of DatePickerDialog and return it
                        return new DatePickerDialog(getActivity(), this, year, month, day);
                    }

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Do something with the date chosen by the user
                        pickerYear = year;
                        pickerMonth = monthOfYear;
                        pickerDay = dayOfMonth;

                        displayDate = (TextView) findViewById(R.id.bday_shown);

                        displayDate.setText(
                                new StringBuilder()
                                        // Month is 0 based, add 1
                                        .append(pickerMonth + 1).append("/")
                                        .append(pickerDay).append("/")
                                        .append(pickerYear).append(" "));
                    }

            }
        });

    }
}
