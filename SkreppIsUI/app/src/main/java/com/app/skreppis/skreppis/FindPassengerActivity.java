package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.LoginResponse;
import com.app.skreppis.skreppis.models.ToggleActiveResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindPassengerActivity extends BaseActivity {

    private Button mToggleActive;
    private Button mSearchPassenger;
    private Button mSetZones;
    String token;
    boolean isActive;
    SkreppIsApi service;
    Boolean out;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_passenger);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        Log.d("Token:", token);
        username = extras.getString("Username");
        Log.d("Username:", username);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://192.168.1.106:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);

        //TODO: finna Active stöðu bílstjóra
        makepref();
        //isActive = sharedPref.getBoolean(getString(R.string.pref_isactive), false);

        mToggleActive = (Button) findViewById(R.id.d_toggleactive);
        mSearchPassenger = (Button) findViewById(R.id.d_searchpassengers);
        mSetZones = (Button) findViewById(R.id.d_setzones);



        mToggleActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleActive();
                //isActive = out;
                if(!isActive){
                    Snackbar.make(v, R.string.d_notActive, Snackbar.LENGTH_LONG).show();
                    //sharedPref.edit().putBoolean(getString(R.string.pref_isactive), false).apply();
                }else {
                    Snackbar.make(v, R.string.d_isActive, Snackbar.LENGTH_LONG).show();
                    //sharedPref.edit().putBoolean(getString(R.string.pref_isactive), true).apply();
                }
            }
        });
        mSetZones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSetZones();
            }
        });
        mSearchPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSearch();
            }
        });

    }

    private void gotoSearch(){
        Intent intent = new Intent(this, SearchPassengerCriteriaActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    private void gotoSetZones(){
        Intent intent = new Intent(this, SetZonesActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    private void toggleActive(){
        Call<ToggleActiveResponse> toggleActiveResponseCall = service.toggleActive(" JWT "+token, username);
        toggleActiveResponseCall.enqueue(new Callback<ToggleActiveResponse>() {
            @Override
            public void onResponse(Call<ToggleActiveResponse> call, Response<ToggleActiveResponse> response) {
                int statusCode = response.code();

                ToggleActiveResponse toggleActiveResponse = response.body();
                Log.d("ToggleActiveActivity", "onResponse: "+ statusCode);
                isActive = toggleActiveResponse.isActive();

            }

            @Override
            public void onFailure(Call<ToggleActiveResponse> call, Throwable t) {
                Log.d("ToggleActiveActivity", "onFailure: "+ t.getMessage());
            }
        });
    }
}
