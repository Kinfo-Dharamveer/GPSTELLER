package com.gpsteller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drivingschool.android.customviews.CustomTextView;
import com.gpsteller.R;
import com.gpsteller.models.LocationCountList;
import com.gpsteller.models.LocationDetailList;

import java.util.ArrayList;

public class LocationDetailAdapter extends RecyclerView.Adapter<LocationDetailAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LocationDetailList> locationDetailListArrayList;

    public LocationDetailAdapter(Context context, ArrayList<LocationDetailList> locationDetailListArrayList) {
        this.context = context;
        this.locationDetailListArrayList = locationDetailListArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.loc_detail_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final LocationDetailList locationDetailList = locationDetailListArrayList.get(i);

        /*viewHolder.tv_licPlate.setText(locationDetailList.getLicePlate());
        viewHolder.locationCount.setText(locationDetailList.getLocaCount());*/
    }

    @Override
    public int getItemCount() {
        return locationDetailListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView tv_licPlate,locationCount;


        public ViewHolder( View itemView) {
            super(itemView);


        }
    }
}
