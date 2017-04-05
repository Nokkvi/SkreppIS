package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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

public class SendRequestActivity extends BaseActivity {

    private SkreppIsApi service;
    private AppCompatSpinner mZoneView;
    private EditText mPickupView;
    private Button mSendRequestButton;
    private UrlWrapper urlWrap;
    private String token;
    private String username;
    private String seats;
    private String startZone;
    private String driverName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));

        makepref();

        Bundle extras = getIntent().getExtras();
        token = sharedPref.getString("Token", "token");
        username = sharedPref.getString("Username", "idiot");
        seats = extras.getString("numseats");
        startZone = extras.getString("pickupzone");
        driverName = extras.getString("driverName");

        Log.d("Token", token);
        Log.d("Username", username);

        mZoneView = (AppCompatSpinner) findViewById(R.id.send_request_zonespinner);
        mPickupView = (EditText) findViewById(R.id.send_request_pickup);
        mSendRequestButton = (Button) findViewById(R.id.send_request_button);


        urlWrap = new UrlWrapper();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlWrap.getUrl())
                .addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(SkreppIsApi.class);

        mSendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRequest();
            }
        });

    }

    private void checkRequest(){
        Call<RideRequestResponse> rideRequestResponseCall = service.checkIfRideRequest(token, username);
        rideRequestResponseCall.enqueue(new Callback<RideRequestResponse>() {
            @Override
            public void onResponse(Call<RideRequestResponse> call, Response<RideRequestResponse> response) {
                int statusCode = response.code();

                RideRequestResponse rideRequestResponse = response.body();

                Log.d("CheckIfRequestActivity", "onResponse: "+ statusCode);
                if(statusCode == 404){
                    sendRequest();
                } else {
                    updateRequest();
                }
            }

            @Override
            public void onFailure(Call<RideRequestResponse> call, Throwable t) {
                Log.d("CheckIfRequestActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void updateRequest(){
        String start = startZone;
        String end = mZoneView.getSelectedItem().toString();
        String seatsString = seats;
        String pickupString = mPickupView.getText().toString();
        String driverString = driverName;

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(pickupString)) {
            mPickupView.setError(getString(R.string.error_field_required));
            focusView = mPickupView;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        } else {
            Log.d("Sæti", seatsString);
            int seats = Integer.parseInt(seatsString);
            RideRequest rideRequest = new RideRequest();
            rideRequest.setStart(start);
            rideRequest.setEnd(end);
            rideRequest.setSeats(seats);
            rideRequest.setPickupLoc(pickupString);
            rideRequest.setDriver(driverString);

            Call<RideRequestResponse> rideRequestResponseCall = service.updateRideRequest(" JWT "+token, username, rideRequest);
            rideRequestResponseCall.enqueue(new Callback<RideRequestResponse>() {
                @Override
                public void onResponse(Call<RideRequestResponse> call, Response<RideRequestResponse> response) {
                    int statusCode = response.code();

                    RideRequestResponse rideRequestResponse = response.body();

                    Log.d("UpdRideRequestActivity", "onResponse: "+ statusCode);
                    sendRequestSuccess(token);
                }

                @Override
                public void onFailure(Call<RideRequestResponse> call, Throwable t) {
                    Log.d("UpdRideRequestActivity", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void sendRequest(){
        String start = startZone;
        String end = mZoneView.getSelectedItem().toString();
        String seatsString = seats;
        String pickupString = mPickupView.toString();
        String driverString = driverName;
        Log.d("pickupString", pickupString);
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(pickupString)) {
            mPickupView.setError(getString(R.string.error_field_required));
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
            rideRequest.setDriver(driverString);
            Log.d("pickupString", pickupString);
            Log.d("Token", "Token: "+ token);

            Call<RideRequestResponse> rideRequestResponseCall = service.requestRide(" JWT "+token ,rideRequest);
            rideRequestResponseCall.enqueue(new Callback<RideRequestResponse>() {
                @Override
                public void onResponse(Call<RideRequestResponse> call, Response<RideRequestResponse> response) {
                    int statusCode = response.code();

                    RideRequestResponse rideRequestResponse = response.body();

                    Log.d("RideRequestActivity", "onResponse: "+ statusCode);
                    sendRequestSuccess(token);
                }

                @Override
                public void onFailure(Call<RideRequestResponse> call, Throwable t) {
                    Log.d("RideRequestActivity", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private boolean sendRequestSuccess(String token) {
        Intent intent = new Intent(this, FindRideActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
        finish();
        return true;
    }

}
