package com.app.skreppis.skreppis;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.AddZoneRequest;
import com.app.skreppis.skreppis.models.AddZoneResponse;
import com.app.skreppis.skreppis.models.DropZoneResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nökkvi on 4.4.2017.
 */

public class SetZonesActivity extends BaseActivity{

    String token;
    String username;
    AppCompatSpinner mAddZoneView;
    SkreppIsApi service;
    String zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_zones);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        username = extras.getString("Username");
        Log.d("Token:", token);
        Log.d("Username:", username);

        mAddZoneView = (AppCompatSpinner) findViewById(R.id.set_zones_zonespinner);

        Button mAddZoneButton = (Button) findViewById(R.id.set_zones_button);

        Button mDeleteZonesButton = (Button) findViewById(R.id.drop_zone_button);

        mAddZoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddZone();
                Snackbar.make(view, "Zone "+zone+" added to your route", Snackbar.LENGTH_LONG).show();
            }
        });

        mDeleteZonesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteZones();
                Snackbar.make(view, R.string.d_dropZones, Snackbar.LENGTH_LONG).show();
            }
        });

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://192.168.1.106:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);
    }

    private void AddZone(){
        zone = mAddZoneView.getSelectedItem().toString();

        AddZoneRequest addZoneRequest = new AddZoneRequest();
        addZoneRequest.setName(zone);
        Log.d("Zone", zone);

        Call<AddZoneResponse> addZoneResponseCall = service.addZone(" JWT "+token, username, addZoneRequest);
        addZoneResponseCall.enqueue(new Callback<AddZoneResponse>() {
            @Override
            public void onResponse(Call<AddZoneResponse> call, Response<AddZoneResponse> response) {
                int statusCode = response.code();

                AddZoneResponse addZoneResponse = response.body();

                Log.d("AddZone", "onResponse: "+ statusCode);
            }

            @Override
            public void onFailure(Call<AddZoneResponse> call, Throwable t) {
                Log.d("AddZone", "onFailure: "+ t.getMessage());
            }
        });
    }

    private void DeleteZones(){
        Call<DropZoneResponse> dropZoneResponseCall = service.dropZones(" JWT "+token, username);
        dropZoneResponseCall.enqueue(new Callback<DropZoneResponse>() {
            @Override
            public void onResponse(Call<DropZoneResponse> call, Response<DropZoneResponse> response) {
                int statusCode = response.code();

                DropZoneResponse dropZoneResponse = response.body();
                //A 404 error is expected here because we deleted all the records for this particular driver
                Log.d("DropZone", "onResponse: "+ statusCode);
            }

            @Override
            public void onFailure(Call<DropZoneResponse> call, Throwable t) {
                Log.d("DropZone", "onFailure: "+ t.getMessage());
            }
        });
    }
}
