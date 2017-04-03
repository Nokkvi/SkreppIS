package com.app.skreppis.skreppis;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.skreppis.skreppis.adapters.DriverListAdapter;
import com.app.skreppis.skreppis.adapters.RideRequestListAdapter;
import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.DriverList;
import com.app.skreppis.skreppis.models.RideRequestList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.skreppis.skreppis.R.id.driverList;
import static com.app.skreppis.skreppis.R.id.passengerList;

public class SearchPassengerCriteriaActivity extends BaseActivity {

    @BindView(passengerList)
    RecyclerView rideRequestListView;
    private AppCompatSpinner mStartView;
    private SkreppIsApi service;
    RideRequestListAdapter adapter;
    String token;

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

        // ButterKnife.bind(this); different from driverCriteria
        //set layout manager for list view // different from driverCriteria

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(    "http://192.168.1.102:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        rideRequestListView.setLayoutManager(staggeredGridLayoutManager);

        //create recyclerview adapter

        Call<RideRequestList> rideRequestListData = service.getPassengerList();

        rideRequestListData.enqueue(new Callback<RideRequestList>() {
            @Override
            public void onResponse(Call<RideRequestList> call, Response<RideRequestList> response) {
                int statusCode = response.code();

                RideRequestList rideRequestList = response.body();

                Log.d("RideRequestList", "onResponse: "+ statusCode);
                adapter = new RideRequestListAdapter(rideRequestList.getResults());
                rideRequestListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RideRequestList> call, Throwable t) {
                Log.d("RideRequestList", "onFailure: "+ t.getMessage());
            }
        });
    }


    private void SearchForPassenger(){
        adapter.clear();

        String start = mStartView.getSelectedItem().toString();

        Call<RideRequestList> rideRequestListCall = service.getPassengerListSearch(start);

        rideRequestListCall.enqueue(new Callback<RideRequestList>() {
            @Override
            public void onResponse(Call<RideRequestList> call, Response<RideRequestList> response) {
                int statusCode = response.code();

                RideRequestList rideRequestList = response.body();
                Log.d("rideRequestList", "rideRequestList: " + rideRequestList);

                Log.d("RideRequestListSearch", "onResponse: "+ statusCode);

                if(rideRequestList != null){
                    adapter = new RideRequestListAdapter(rideRequestList.getResults());
                    rideRequestListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<RideRequestList> call, Throwable t) {
                Log.d("RideRequestListSearch", "onFailure: "+ t.getMessage());
            }
        });

    }
}
