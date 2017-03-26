package com.app.skreppis.skreppis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

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

public class SearchDriverCriteriaActivity extends BaseActivity {

    @BindView(R.id.driverList)
    RecyclerView driverList;
    private SkreppIsApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Ready");
        super.onCreate(savedInstanceState);
        System.out.println("Start");
        setContentView(R.layout.activity_search_driver_criteria);

        ButterKnife.bind(this);
        //set layout manager for list view

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://192.168.1.106:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        driverList.setLayoutManager(staggeredGridLayoutManager);

        //create recyclerview adapter

        Call<DriverList> driverListData = service.getDriverList();

        driverListData.enqueue(new Callback<DriverList>() {
            @Override
            public void onResponse(Call<DriverList> call, Response<DriverList> response) {
                int statusCode = response.code();

                DriverList driverList1 = response.body();

                Log.d("DriverList", "onResponse: "+ statusCode);
                DriverListAdapter adapter = new DriverListAdapter(driverList1.getResults());
                System.out.println(response);
                driverList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<DriverList> call, Throwable t) {
                Log.d("DriverList", "onFailure: "+ t.getMessage());
            }
        });






    }
}
