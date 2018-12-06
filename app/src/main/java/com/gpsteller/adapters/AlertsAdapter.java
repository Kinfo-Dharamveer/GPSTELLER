package com.gpsteller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drivingschool.android.customviews.CustomTextView;
import com.gpsteller.R;
import com.gpsteller.models.AlertsModels;

import java.util.ArrayList;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.MyViewHolder> {

    private ArrayList<AlertsModels> alertsModelsArrayList;
    private Context context;


    public AlertsAdapter(Context context,ArrayList<AlertsModels> alertsModelsArrayList ) {
        this.alertsModelsArrayList = alertsModelsArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final AlertsModels alertsModels = alertsModelsArrayList.get(i);

        myViewHolder.tv_serial.setText(alertsModels.getSrNo());
        myViewHolder.tvCat.setText(alertsModels.getCat());
        myViewHolder.tv_type.setText(alertsModels.getType());
        myViewHolder.tv_vehicle.setText(alertsModels.getVehicle());
        myViewHolder.tv_time.setText(alertsModels.getTime());


    }

    @Override
    public int getItemCount() {
        return alertsModelsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView tv_serial,tvCat,tv_type,tv_vehicle,tv_time;
        private Button btn_del;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_serial = itemView.findViewById(R.id.tv_serial);
            tvCat = itemView.findViewById(R.id.tvCat);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_vehicle = itemView.findViewById(R.id.tv_vehicle);
            tv_time = itemView.findViewById(R.id.tv_time);

            btn_del = itemView.findViewById(R.id.btn_del);

        }
    }
}
