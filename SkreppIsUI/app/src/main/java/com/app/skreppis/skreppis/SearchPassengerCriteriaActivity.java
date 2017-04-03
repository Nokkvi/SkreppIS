package com.app.skreppis.skreppis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.RideRequestList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPassengerCriteriaActivity extends BaseActivity {

    private AppCompatSpinner mStartView;
    String token;
    private SkreppIsApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_passenger_criteria);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        Log.d("Token:", token);

        mStartView = (AppCompatSpinner) findViewById(R.id.find_passenger_zonespinner);

        Button mSearchButton = (Button) findViewById(R.id.passenger_search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchForPassenger();
            }
        });

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://192.168.1.106:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);

        Call<RideRequestList> rideRequestListCall = service.getPassengerList();

        rideRequestListCall.enqueue(new Callback<RideRequestList>() {
            @Override
            public void onResponse(Call<RideRequestList> call, Response<RideRequestList> response) {
                int statusCode = response.code();

                RideRequestList rideRequestList = response.body();

                Log.d("RideRequestList", "onResponse: "+ statusCode);
            }

            @Override
            public void onFailure(Call<RideRequestList> call, Throwable t) {
                Log.d("RideRequestList", "onFailure: "+ t.getMessage());
            }
        });
    }


    private void SearchForPassenger(){
        String start = mStartView.getSelectedItem().toString();

        Call<RideRequestList> rideRequestListCall = service.getPassengerListSearch(start);

        rideRequestListCall.enqueue(new Callback<RideRequestList>() {
            @Override
            public void onResponse(Call<RideRequestList> call, Response<RideRequestList> response) {
                int statusCode = response.code();

                RideRequestList rideRequestList = response.body();

                Log.d("RideRequestListSearch", "onResponse: "+ statusCode);
            }

            @Override
            public void onFailure(Call<RideRequestList> call, Throwable t) {
                Log.d("RideRequestListSearch", "onFailure: "+ t.getMessage());
            }
        });

    }
}
