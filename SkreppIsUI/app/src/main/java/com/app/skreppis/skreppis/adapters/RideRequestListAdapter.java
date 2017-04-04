package com.app.skreppis.skreppis.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.skreppis.skreppis.R;

import com.app.skreppis.skreppis.models.PassengerListItemResponse;
import com.app.skreppis.skreppis.models.RideRequestListItemResponse;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elisabet on 03/04/2017.
 */

public class RideRequestListAdapter extends RecyclerView.Adapter<RideRequestListAdapter.PassengerListViewHolder>{
    List<RideRequestListItemResponse> rideRequestListItemResponseList;

    public RideRequestListAdapter(List<RideRequestListItemResponse> _rideRequestListItemResponseList){
        rideRequestListItemResponseList = _rideRequestListItemResponseList;
    }

    @Override
    public PassengerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item, parent, false);
        PassengerListViewHolder passengerListViewHolder = new PassengerListViewHolder(itemView);
        return passengerListViewHolder;
    }

    @Override
    public void onBindViewHolder(PassengerListViewHolder holder, int position) {
        holder.passengerName.setText(rideRequestListItemResponseList.get(position).getPassengerName());
        holder.passengerPhone.setText(rideRequestListItemResponseList.get(position).getPassengerPhone());
        holder.passengerRideRequests.setText(rideRequestListItemResponseList.get(position).getPassengerRideRequests());

    }

    @Override
    public int getItemCount() {
        // We need the object model to be passed to this layer
        return rideRequestListItemResponseList.size();
    }

    public void clear() {
        int size = this.rideRequestListItemResponseList.size();
        this.rideRequestListItemResponseList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static class PassengerListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardViewPassenger)
        CardView passengerItem;

        @BindView(R.id.passengername)
        TextView passengerName;

        @BindView(R.id.passengerphone)
        TextView passengerPhone;

        @BindView(R.id.passengerriderequests)
        TextView passengerRideRequests;




        public PassengerListViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
