package com.app.skreppis.skreppis.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.skreppis.skreppis.R;
import com.app.skreppis.skreppis.SearchDriverCriteriaActivity;
import com.app.skreppis.skreppis.SendRequestActivity;
import com.app.skreppis.skreppis.models.DriverListItemResponse;

import java.sql.Driver;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.onClick;

/**
 * Created by Nökkvi on 25.3.2017.
 */

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.DriverListViewHolder>{
    List<DriverListItemResponse> driverListItemResponseList;
    private String zone;
    private String seats;

    public DriverListAdapter(List<DriverListItemResponse> _driverListItemResponseList, String z, String s){
        driverListItemResponseList = _driverListItemResponseList;
        zone = z;
        seats = s;
    }

    @Override
    public DriverListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_item, parent, false);
        DriverListViewHolder driverListViewHolder = new DriverListViewHolder(itemView, zone, seats);
        return driverListViewHolder;
    }

    @Override
    public void onBindViewHolder(DriverListViewHolder holder, int position) {
        holder.driverName.setText(driverListItemResponseList.get(position).getDriverName());
        holder.driverPhone.setText(driverListItemResponseList.get(position).getDriverPhone());
        holder.driverSeats.setText(driverListItemResponseList.get(position).getDriverSeats());


    }

    @Override
    public int getItemCount() {
        // We need the object model to be passed to this layer
        return driverListItemResponseList.size();
    }

    public void clear() {
        int size = this.driverListItemResponseList.size();
        this.driverListItemResponseList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static class DriverListViewHolder extends RecyclerView.ViewHolder {

        private String zone;
        private String seats;

        public DriverListViewHolder(View itemView, String z, String s){
            super(itemView);

            zone = z;
            seats = s;
        }

        @BindView(R.id.cardViewDriver)
        CardView driverItem;

        @BindView(R.id.drivername)
        TextView driverName;

        @BindView(R.id.driverphone)
        TextView driverPhone;

        @BindView(R.id.driverseats)
        TextView driverSeats;

        public DriverListViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.driver_list_item)
        public void click(View view)  {
            Context context = view.getContext();
            Intent i = new Intent(context, SendRequestActivity.class);
            i.putExtra("pickupzone", zone);
            i.putExtra("numseats", seats);
            context.startActivity(i);
        }
    }

}
