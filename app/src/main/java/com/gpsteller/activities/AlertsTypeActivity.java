package com.gpsteller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.gpsteller.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AlertsTypeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnAlerts;
    private Spinner sp_serialno,sp_Info,sp_Alerttype,sp_vehRegNo,sp_Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerts_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAlerts = findViewById(R.id.btnAlerts);

        sp_serialno = findViewById(R.id.sp_serialno);
        sp_Info = findViewById(R.id.sp_Info);
        sp_Alerttype = findViewById(R.id.sp_Alerttype);
        sp_vehRegNo = findViewById(R.id.sp_vehRegNo);
        sp_Time = findViewById(R.id.sp_Time);

        // Spinner click listener
        sp_serialno.setOnItemSelectedListener(this);
        sp_Info.setOnItemSelectedListener(this);
        sp_Alerttype.setOnItemSelectedListener(this);
        sp_vehRegNo.setOnItemSelectedListener(this);
        sp_Time.setOnItemSelectedListener(this);

        btnAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AlertsTypeActivity.this,AlertsActivity.class));
            }
        });


        // Spinner Drop Down elements 1
        List<String> serials = new ArrayList<String>();
        for (int i=1;i<=100;i++){
            serials.add(i+"");
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,serials);
        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_serialno.setAdapter(dataAdapter);


        // Spinner Drop Down elements 2
        List<String> category2 = new ArrayList<String>();
        category2.add("Alert Category");
        category2.add("Info");
        category2.add("Warn");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterCate = new ArrayAdapter<String>(this,R.layout.spinner_item,category2);
        //Drop down layout style - list view with radio button
        adapterCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_Info.setAdapter(adapterCate);

        // Spinner Drop Down elements 3
        List<String> alertType = new ArrayList<String>();
        alertType.add("Alert Type");
        alertType.add("Package Expired");
        alertType.add("Maintenance Expired");
        alertType.add("Vehicle Ignition On");
        alertType.add("No Activity Recorded");
        alertType.add("Vehicle Ignition Off");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterAlert = new ArrayAdapter<String>(this,R.layout.spinner_item,alertType);
        //Drop down layout style - list view with radio button
        adapterAlert.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_Alerttype.setAdapter(adapterAlert);


        // Spinner Drop Down elements 3
        List<String> vehicleRegNo = new ArrayList<String>();
        vehicleRegNo.add("Vehicle Reg. Number");
        vehicleRegNo.add("Excavator Yaw");
        vehicleRegNo.add("Excavator NAP");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterVehicle = new ArrayAdapter<String>(this,R.layout.spinner_item,vehicleRegNo);
        //Drop down layout style - list view with radio button
        adapterVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_vehRegNo.setAdapter(adapterVehicle);


        // Spinner Drop Down elements 3
        List<String> time = new ArrayList<String>();
        time.add("Mon 12 Nov 2018 09:30:00");
        time.add("Mon 12 Nov 2018 09:30:00");
        time.add("Mon 12 Nov 2018 09:30:00");
        time.add("Mon 12 Nov 2018 09:30:00");
        time.add("Mon 12 Nov 2018 09:30:00");
        time.add("Mon 12 Nov 2018 09:30:00");
        time.add("Mon 12 Nov 2018 09:30:00");

        // Creating adapter for spinner
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this,R.layout.spinner_item,time);
        //Drop down layout style - list view with radio button
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_Time.setAdapter(adapterTime);



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
}
