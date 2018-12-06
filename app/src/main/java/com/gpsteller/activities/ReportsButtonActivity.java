package com.gpsteller.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gpsteller.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReportsButtonActivity extends AppCompatActivity {

    private Button btnSelectVehicle,btnSelectGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_button);


        btnSelectVehicle = findViewById(R.id.btnSelectVehicle);
        btnSelectGroup = findViewById(R.id.btnSelectGroup);

        btnSelectVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsButtonActivity.this, VehicleReportsActivity.class));
            }
        });


        btnSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsButtonActivity.this, GroupReportsActivity.class));

            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

}
