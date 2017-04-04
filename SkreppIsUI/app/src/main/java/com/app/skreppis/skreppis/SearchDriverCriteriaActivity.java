package com.app.skreppis.skreppis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.skreppis.skreppis.adapters.DriverListAdapter;
import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.DriverList;
import com.app.skreppis.skreppis.models.DriverListItemResponse;

import butterknife.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;

public class SearchDriverCriteriaActivity extends BaseActivity {

    @BindView(R.id.driverList)
    RecyclerView driverListView;
    private SkreppIsApi service;
    private AppCompatSpinner mZoneView;
    private AppCompatSpinner mSeatsView;
    DriverListAdapter adapter;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_driver_criteria);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        Log.d("Token:", token);

        mZoneView = (AppCompatSpinner) findViewById(R.id.find_ride_zonespinner);
        mSeatsView = (AppCompatSpinner) findViewById(R.id.find_ride_seatspinner);


        Button mSearchButton = (Button) findViewById(R.id.driver_search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchForDriver();
            }
        });

        ButterKnife.bind(this);
        //set layout manager for list view

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://192.168.1.102:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        driverListView.setLayoutManager(staggeredGridLayoutManager);

        //create recyclerview adapter

        Call<DriverList> driverListData = service.getDriverList();

        driverListData.enqueue(new Callback<DriverList>() {
            @Override
            public void onResponse(Call<DriverList> call, Response<DriverList> response) {
                int statusCode = response.code();

                DriverList driverList = response.body();

                Log.d("DriverList", "onResponse: "+ statusCode);
                adapter = new DriverListAdapter(driverList.getResults());
                driverListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DriverList> call, Throwable t) {
                Log.d("DriverList", "onFailure: "+ t.getMessage());
            }
        });
    }

    private void SearchForDriver(){
        adapter.clear();

        String zone = mZoneView.getSelectedItem().toString();
        String seats = mSeatsView.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;

        Call<DriverList> driverListCall = service.getDriverListSearch(zone, seats);

        driverListCall.enqueue(new Callback<DriverList>() {
            @Override
            public void onResponse(Call<DriverList> call, Response<DriverList> response) {
                int statusCode = response.code();

                DriverList driverList = response.body();
                System.out.println(driverList);

                Log.d("DriverListSearch", "onResponse: "+ statusCode);
                if(driverList != null){
                    adapter = new DriverListAdapter(driverList.getResults());
                    driverListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DriverList> call, Throwable t) {
                Log.d("DriverListSearch", "onFailure: "+ t.getMessage());
            }
        });

    }
}
