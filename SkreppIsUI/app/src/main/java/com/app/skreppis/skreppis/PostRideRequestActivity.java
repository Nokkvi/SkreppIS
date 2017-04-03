package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.RideRequest;
import com.app.skreppis.skreppis.models.RideRequestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRideRequestActivity extends BaseActivity {

    SkreppIsApi service;
    private AppCompatSpinner mStartView;
    private AppCompatSpinner mEndView;
    private AppCompatSpinner mSeatsView;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride_request);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");

        mStartView = (AppCompatSpinner) findViewById(R.id.post_ride_request_zonespinner1);
        mEndView = (AppCompatSpinner) findViewById(R.id.post_ride_request_zonespinner2);
        mSeatsView = (AppCompatSpinner) findViewById(R.id.post_ride_request_seatspinner);

        Button mPostRequestButton = (Button) findViewById(R.id.post_ride_request_button);

        mPostRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRideRequest();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.106:8000")
                .addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(SkreppIsApi.class);
    }

    protected boolean PostRideRequestSuccess(String token){
        Intent intent = new Intent(this, FindRideActivity.class);
        intent.putExtra("Token", token);
        startActivity(intent);
        finish();
        return true;
    }

    private void postRideRequest(){
        String start = mStartView.getSelectedItem().toString();
        String end = mEndView.getSelectedItem().toString();
        String seatsString = mSeatsView.getSelectedItem().toString();
        int seats = Integer.parseInt(seatsString);

        RideRequest rideRequest = new RideRequest();
        rideRequest.setStart(start);
        rideRequest.setEnd(end);
        rideRequest.setSeats(seats);
        Log.d("Token", "Token: "+ token);

        Call<RideRequestResponse> rideRequestResponseCall = service.requestRide(" JWT "+token ,rideRequest);
        rideRequestResponseCall.enqueue(new Callback<RideRequestResponse>() {
            @Override
            public void onResponse(Call<RideRequestResponse> call, Response<RideRequestResponse> response) {
                int statusCode = response.code();

                RideRequestResponse rideRequestResponse = response.body();

                Log.d("RideRequestActivity", "onResponse: "+ statusCode);
                PostRideRequestSuccess(token);
            }

            @Override
            public void onFailure(Call<RideRequestResponse> call, Throwable t) {
                Log.d("RideRequestActivity", "onFailure: " + t.getMessage());
            }
        });
    }
}
