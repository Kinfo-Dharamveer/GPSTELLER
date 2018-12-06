package com.gpsteller.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.gpsteller.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GroupReportsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spin_selectGroup,spin_selectFix,spin_ReportType,spin_HTMLReport;
    static EditText stTime,eTime;
    private Button btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_reports);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spin_selectGroup = findViewById(R.id.spin_selectGroup);
        spin_selectFix = findViewById(R.id.spin_selectFix);
        spin_ReportType = findViewById(R.id.spin_ReportType);
        spin_HTMLReport = findViewById(R.id.spin_HTMLReport);

        stTime = findViewById(R.id.stTime);
        eTime = findViewById(R.id.eTime);
        btnReport = findViewById(R.id.btnReport);

        stTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current date
                showTimePickerDialog(v);
                showDatePickerDialog(v);
            }
        });

        eTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current date
                showTimePickerDialog(view);
                showDatePickerDialog(view);
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroupReportsActivity.this,ReportsActivity.class));
            }
        });

        // Spinner click listener
        spin_selectGroup.setOnItemSelectedListener(this);
        spin_selectFix.setOnItemSelectedListener(this);
        spin_ReportType.setOnItemSelectedListener(this);
        spin_HTMLReport.setOnItemSelectedListener(this);

        // Spinner Drop Down elements 1
        List<String> groupCate = new ArrayList<String>();
        groupCate.add("JSL FLEET");
        groupCate.add("KABO");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,groupCate);
        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spin_selectGroup.setAdapter(dataAdapter);


        // Spinner Drop Down elements 2
        List<String> selectFixInterval = new ArrayList<String>();
        selectFixInterval.add("Today");
        selectFixInterval.add("This Week");
        selectFixInterval.add("Last 2 Weeks");
        selectFixInterval.add("Last 3 Week");
        selectFixInterval.add("Month");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterFixInt = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,selectFixInterval);
        //Drop down layout style - list view with radio button
        dataAdapterFixInt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spin_selectFix.setAdapter(dataAdapterFixInt);


        // Spinner Drop Down elements 3
        List<String> selectReportType = new ArrayList<String>();
        selectReportType.add("Group Location Count");
        selectReportType.add("Alert Count");
        selectReportType.add("Group Fuel Expense Report");
        selectReportType.add("Vehicle Group Expense Report");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterReportType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,selectReportType);
        //Drop down layout style - list view with radio button
        adapterReportType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spin_ReportType.setAdapter(adapterReportType);


        // Spinner Drop Down elements 3
        List<String> selectHTMLReport = new ArrayList<String>();
        selectHTMLReport.add("View HTML Report");
        selectHTMLReport.add("Email Report");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterHTMPReport = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,selectHTMLReport);
        //Drop down layout style - list view with radio button
        adapterHTMPReport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spin_HTMLReport.setAdapter(adapterHTMPReport);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        return true;
    }



    public void showTimePickerDialog(View v){
        DialogFragment newFrag = new VehicleReportsActivity.TimePickerFragment();
        newFrag.show(getSupportFragmentManager(),"timePicker");
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new VehicleReportsActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        switch (adapterView.getId()){

            case R.id.spin_selectGroup:

                break;
            case R.id.spin_selectFix:

                break;
            case R.id.spin_ReportType:

                break;
            case R.id.spin_HTMLReport:

                break;

        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //Create a new instance of TimePickerDialog and return it

            return new TimePickerDialog(getActivity(),this,hour,minute, android.text.format.DateFormat.is24HourFormat(getActivity()));

        }



        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            stTime.setText(stTime.getText() + " -" + hourOfDay + ":"	+ minute);

        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),this,year,month,day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            stTime.setText(day + "/" + (month + 1) + "/" + year);
        }
    }




}
