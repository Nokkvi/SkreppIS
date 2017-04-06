package com.app.skreppis.skreppis.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.skreppis.skreppis.R;

import com.app.skreppis.skreppis.SendRequestActivity;
import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.PassengerListItemResponse;
import com.app.skreppis.skreppis.models.RideRequestListItemResponse;
import com.app.skreppis.skreppis.models.RideRequestResponse;
import com.app.skreppis.skreppis.models.UrlWrapper;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Elisabet on 03/04/2017.
 */

public class RideRequestListAdapter extends RecyclerView.Adapter<RideRequestListAdapter.PassengerListViewHolder>{
    List<RideRequestListItemResponse> rideRequestListItemResponseList;
    String tok;

    public RideRequestListAdapter(List<RideRequestListItemResponse> _rideRequestListItemResponseList, String token){
        rideRequestListItemResponseList = _rideRequestListItemResponseList;
        tok = token;
        Log.d("Hullahoop", token);
    }

    @Override
    public PassengerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item, parent, false);
        PassengerListViewHolder passengerListViewHolder = new PassengerListViewHolder(itemView, tok);
        return passengerListViewHolder;
    }

    @Override
    public void onBindViewHolder(PassengerListViewHolder holder, int position) {
        holder.passengerName.setText(rideRequestListItemResponseList.get(position).getPassenger());
        holder.driver.setText(rideRequestListItemResponseList.get(position).getDriver());
        holder.passengerpickuploc.setText(rideRequestListItemResponseList.get(position).getPickuploc());

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

        String tokan;
        private UrlWrapper urlWrap;
        SkreppIsApi service;

        @BindView(R.id.cardViewPassenger)
        CardView passengerItem;

        @BindView(R.id.passengername)
        TextView passengerName;

        @BindView(R.id.drivername)
        TextView driver;

        @BindView(R.id.passengerpickuploc)
        TextView passengerpickuploc;

        @BindView(R.id.bt_request_accept)
        Button acceptButton;





        public PassengerListViewHolder(View itemView, String tok) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            tokan = tok;
        }

        @OnClick(R.id.bt_request_accept)
        public void click(View view)  {

            String pName;
            pName = passengerName.getText().toString();

            urlWrap = new UrlWrapper();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(urlWrap.getUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            System.out.print("bound");

            service = retrofit.create(SkreppIsApi.class);

            Call<RideRequestResponse> rideRequestResponseCall = service.acceptRideRequest(" JWT "+tokan, pName);
            rideRequestResponseCall.enqueue(new Callback<RideRequestResponse>() {
                @Override
                public void onResponse(Call<RideRequestResponse> call, Response<RideRequestResponse> response) {
                    int statusCode = response.code();

                    RideRequestResponse rideRequestResponse = response.body();

                    Log.d("AcceptRideRequest", "onResponse: "+ statusCode);
                }

                @Override
                public void onFailure(Call<RideRequestResponse> call, Throwable t) {
                    Log.d("AcceptRideRequest", "onFailure: "+ t.getMessage());
                }
            });
        }
    }

}
