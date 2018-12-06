package com.gpsteller.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gpsteller.R;
import com.gpsteller.adapters.AlertsAdapter;
import com.gpsteller.models.AlertsModels;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AlertsActivity extends AppCompatActivity {

    private RecyclerView recyc_Alerts;
    private AlertsAdapter alertsAdapter;
    private ArrayList<AlertsModels> alertsModelsArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyc_Alerts = findViewById(R.id.recyc_Alerts);

        alertsAdapter = new AlertsAdapter(this,alertsModelsArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyc_Alerts.setLayoutManager(layoutManager);
        recyc_Alerts.setItemAnimator(new DefaultItemAnimator());
        recyc_Alerts.setAdapter(alertsAdapter);

        prepareAlert();

    }

    private void prepareAlert() {

        AlertsModels alertsModels = new AlertsModels("1","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels);

        AlertsModels alertsModels2 = new AlertsModels("2","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels2);

        AlertsModels alertsModels3 = new AlertsModels("3","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels3);

        AlertsModels alertsModels4 = new AlertsModels("4","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels4);

        AlertsModels alertsModels5 = new AlertsModels("5","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels5);

        AlertsModels alertsModels6 = new AlertsModels("6","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels6);

        AlertsModels alertsModels7 = new AlertsModels("7","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels7);

        AlertsModels alertsModels8 = new AlertsModels("8","Info","Package Expired","Excavator Yaw","Mon, 12 Nov 2018 09:30:00");
        alertsModelsArrayList.add(alertsModels8);



        alertsAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

}
