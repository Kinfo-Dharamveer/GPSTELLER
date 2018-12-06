package com.gpsteller.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
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

import com.gpsteller.AppConstants;
import com.gpsteller.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VehicleReportsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spin_selectVehicle,spin_selectfixint,spin_selectReportType,spin_viewhtmlReport;
    static EditText startTime,endTime;
    private Button btnReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_reports);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spin_selectVehicle = findViewById(R.id.spin_selectVehicle);
        spin_selectfixint = findViewById(R.id.spin_selectfixint);
        spin_selectReportType = findViewById(R.id.spin_selectReportType);
        spin_viewhtmlReport = findViewById(R.id.spin_viewhtmlReport);

        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        btnReports = findViewById(R.id.btnReports);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current date
                showTimePickerDialog(v);
                showDatePickerDialog(v);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current date
                showTimePickerDialog(view);
                showDatePickerDialog(view);
            }
        });




        // Spinner click listener
        spin_selectVehicle.setOnItemSelectedListener(this);
        spin_selectfixint.setOnItemSelectedListener(this);
        spin_selectReportType.setOnItemSelectedListener(this);
        spin_viewhtmlReport.setOnItemSelectedListener(this);


        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VehicleReportsActivity.this,ReportsActivity.class);
                intent.putExtra(AppConstants.SPIN_KEY,spin_selectReportType.getSelectedItem().toString());
                startActivity(intent);
            }
        });


        // Spinner Drop Down elements 1
        List<String> categories = new ArrayList<String>();
        categories.add("Excavator NAP");
        categories.add("Excavator Yaw");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spin_selectVehicle.setAdapter(dataAdapter);


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
        spin_selectfixint.setAdapter(dataAdapterFixInt);


        // Spinner Drop Down elements 3
        List<String> selectReportType = new ArrayList<String>();
        selectReportType.add("Location Count Report");
        selectReportType.add("Location Detail Report");
        selectReportType.add("Alert Count Report");
        selectReportType.add("Alert Detail Report");
        selectReportType.add("Vehicle Start/Stop Summary");
        selectReportType.add("Fuel Expense Report");
        selectReportType.add("Vehicle Expense Report");
        selectReportType.add("Distance Covered Report");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterReportType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,selectReportType);
        //Drop down layout style - list view with radio button
        adapterReportType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spin_selectReportType.setAdapter(adapterReportType);


        // Spinner Drop Down elements 3
        List<String> selectHTMLReport = new ArrayList<String>();
        selectHTMLReport.add("View HTML Report");
        selectHTMLReport.add("Email Report");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterHTMPReport = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,selectHTMLReport);
        //Drop down layout style - list view with radio button
        adapterHTMPReport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spin_viewhtmlReport.setAdapter(adapterHTMPReport);


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()){

            case R.id.spin_selectVehicle:

                break;
            case R.id.spin_selectfixint:

                break;
            case R.id.spin_selectReportType:

                break;
            case R.id.spin_viewhtmlReport:

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showTimePickerDialog(View v){
        DialogFragment newFrag = new TimePickerFragment();
        newFrag.show(getSupportFragmentManager(),"timePicker");
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog( Bundle savedInstanceState) {

            //Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //Create a new instance of TimePickerDialog and return it

            return new TimePickerDialog(getActivity(),this,hour,minute, android.text.format.DateFormat.is24HourFormat(getActivity()));

        }



        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            startTime.setText(startTime.getText() + " -" + hourOfDay + ":"	+ minute);

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

            startTime.setText(day + "/" + (month + 1) + "/" + year);
        }
    }


}
