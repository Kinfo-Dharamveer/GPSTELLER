package com.gpsteller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivingschool.android.customviews.CustomTextView;
import com.gpsteller.R;
import com.gpsteller.models.AlertDetailList;
import com.gpsteller.models.AlertsModels;

import java.util.ArrayList;

public class AlertDetailAdapter extends RecyclerView.Adapter<AlertDetailAdapter.Holder> {

    private ArrayList<AlertDetailList> alertDetailArrayList;
    private Context context;


    public AlertDetailAdapter(ArrayList<AlertDetailList> alertDetailArrayList, Context context) {
        this.alertDetailArrayList = alertDetailArrayList;
        this.context = context;
    }

    @Override
    public AlertDetailAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.alert_detail_row,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertDetailAdapter.Holder holder, int i) {

        final AlertDetailList alertDetailList = alertDetailArrayList.get(i);

        holder.driver_name.setText(alertDetailList.getDriverName());
        holder.alert_cat.setText(alertDetailList.getAlertCat());
        holder.alert_type.setText(alertDetailList.getAlertType());
        holder.time.setText(alertDetailList.getTime());
     //   holder.status.setText(alertDetailList.getStatus());

    }

    @Override
    public int getItemCount() {
        return alertDetailArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private CustomTextView driver_name,alert_cat,alert_type,time,alert_det;

        public Holder(@NonNull View itemView) {
            super(itemView);

            driver_name = itemView.findViewById(R.id.driver_name);
            alert_cat = itemView.findViewById(R.id.alert_cat);
            alert_type = itemView.findViewById(R.id.alert_type);
            time = itemView.findViewById(R.id.time);
            alert_det = itemView.findViewById(R.id.alert_det);

        }
    }
}
