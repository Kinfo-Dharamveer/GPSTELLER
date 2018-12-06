package com.gpsteller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drivingschool.android.customviews.CustomTextView;
import com.gpsteller.R;
import com.gpsteller.models.LocationCountList;

import java.util.ArrayList;

public class LocationCountAdapter extends RecyclerView.Adapter<LocationCountAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LocationCountList> locationCountLists;

    public LocationCountAdapter(Context context, ArrayList<LocationCountList> locationCountLists) {
        this.context = context;
        this.locationCountLists = locationCountLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.reports_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final LocationCountList locationCountList = locationCountLists.get(i);

        viewHolder.tv_licPlate.setText(locationCountList.getLicePlate());
        viewHolder.locationCount.setText(locationCountList.getLocaCount());
    }

    @Override
    public int getItemCount() {
        return locationCountLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView tv_licPlate,locationCount;
        public ViewHolder( View itemView) {
            super(itemView);

            tv_licPlate = itemView.findViewById(R.id.tv_licPlate);
            locationCount = itemView.findViewById(R.id.locationCount);

        }
    }
}
