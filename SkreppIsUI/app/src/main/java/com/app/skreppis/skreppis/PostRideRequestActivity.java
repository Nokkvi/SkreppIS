package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.RideRequest;
import com.app.skreppis.skreppis.models.RideRequestResponse;
import com.app.skreppis.skreppis.models.UrlWrapper;

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
    private EditText mPickupView;
    String token;
    String username;
    private UrlWrapper urlWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride_request);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        username = extras.getString("Username");

        mStartView = (AppCompatSpinner) findViewById(R.id.post_ride_request_zonespinner1);
        mEndView = (AppCompatSpinner) findViewById(R.id.post_ride_request_zonespinner2);
        mSeatsView = (AppCompatSpinner) findViewById(R.id.post_ride_request_seatspinner);
        mPickupView = (EditText) findViewById(R.id.post_ride_request_pickup);

        Button mPostRequestButton = (Button) findViewById(R.id.post_ride_request_button);

        mPostRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRideRequest();
            }
        });

        urlWrap = new UrlWrapper();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlWrap.getUrl())
                .addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(SkreppIsApi.class);
    }

    protected boolean PostRideRequestSuccess(String token){
        Intent intent = new Intent(this, FindRideActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
        finish();
        return true;
    }

    private void checkRideRequest(){
        Call<RideRequestResponse> rideRequestResponseCall = service.checkIfRideRequest(token, username);
        rideRequestResponseCall.enqueue(new Callback<RideRequestResponse>() {
            @Override
            public void onResponse(Call<RideRequestResponse> call, Response<RideRequestResponse> response) {
                int statusCode = response.code();

                RideRequestResponse rideRequestResponse = response.body();

                Log.d("CheckIfRequestActivity", "onResponse: "+ statusCode);
                if(statusCode == 404){
                    postRideRequest();
                } else {
                    updateRideRequest();
                }
            }

            @Override
            public void onFailure(Call<RideRequestResponse> call, Throwable t) {
                Log.d("CheckIfRequestActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void updateRideRequest(){
        String start = mStartView.getSelectedItem().toString();
        String end = mEndView.getSelectedItem().toString();
        String seatsString = mSeatsView.getSelectedItem().toString();
        String pickupString = mPickupView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(pickupString)) {
            mPickupView.setError(getString(R.string.error_field_required));
            focusView = mPickupView;
            cancel = true;
        }

        if(!seatsString.matches("[0-9]+")){
            mPickupView.setError(getString(R.string.error_only_numbers));
            focusView = mPickupView;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        } else {
            int seats = Integer.parseInt(seatsString);
            RideRequest rideRequest = new RideRequest();
            rideRequest.setStart(start);
            rideRequest.setEnd(end);
            rideRequest.setSeats(seats);
            rideRequest.setPickupLoc(pickupString);
            rideRequest.setDriver("");

            Call<RideRequestResponse> rideRequestResponseCall = service.updateRideRequest(" JWT "+token, username, rideRequest);
            rideRequestResponseCall.enqueue(new Callback<RideRequestResponse>() {
                @Override
                public void onResponse(Call<RideRequestResponse> call, Response<RideRequestResponse> response) {
                    int statusCode = response.code();

                    RideRequestResponse rideRequestResponse = response.body();

                    Log.d("UpdRideRequestActivity", "onResponse: "+ statusCode);
                    PostRideRequestSuccess(token);
                }

                @Override
                public void onFailure(Call<RideRequestResponse> call, Throwable t) {
                    Log.d("UpdRideRequestActivity", "onFailure: " + t.getMessage());
                }
            });
        }


    }

    private void postRideRequest(){
        String start = mStartView.getSelectedItem().toString();
        String end = mEndView.getSelectedItem().toString();
        String seatsString = mSeatsView.getSelectedItem().toString();
        String pickupString = mPickupView.getText().toString();
        Log.d("pickupString", pickupString);
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(pickupString)) {
            mPickupView.setError(getString(R.string.error_field_required));
            focusView = mPickupView;
            cancel = true;
        }

        if(!seatsString.matches("[0-9]+")){
            mPickupView.setError(getString(R.string.error_only_numbers));
            focusView = mPickupView;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            int seats = Integer.parseInt(seatsString);
            RideRequest rideRequest = new RideRequest();
            rideRequest.setStart(start);
            rideRequest.setEnd(end);
            rideRequest.setSeats(seats);
            rideRequest.setPickupLoc(pickupString);
            Log.d("pickupString", pickupString);
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
}
