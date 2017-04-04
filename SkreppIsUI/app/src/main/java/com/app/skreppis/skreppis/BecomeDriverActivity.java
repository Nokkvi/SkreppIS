package com.app.skreppis.skreppis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.DriverCreateRequest;
import com.app.skreppis.skreppis.models.DriverCreateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nökkvi on 4.4.2017.
 */

public class BecomeDriverActivity extends BaseActivity{
    String token;
    String username;
    SkreppIsApi service;
    private EditText mCarSeatsView;
    private CheckBox mSmokingAllowedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_driver);

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        Log.d("Token:", token);
        username = extras.getString("Username");
        Log.d("Username:", username);

        mCarSeatsView = (EditText) findViewById(R.id.p_become_driver_seats);
        mSmokingAllowedView = (CheckBox) findViewById(R.id.p_become_driver_smoking);

        Button mBecomeDriverButton = (Button) findViewById(R.id.passenger_become_driver_btn);


        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://192.168.1.106:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);

        mBecomeDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                becomeDriver();
                success();
            }
        });
    }

    private void success(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    private void becomeDriver(){
        String car_seats = mCarSeatsView.getText().toString();
        boolean smokingAllowed;
        if(mSmokingAllowedView.isChecked()){
            smokingAllowed = true;
        }else{
            smokingAllowed = false;
        }

        DriverCreateRequest driverCreateRequest = new DriverCreateRequest();
        driverCreateRequest.setActive(true);
        driverCreateRequest.setCar_seats(car_seats);
        driverCreateRequest.setSmoking_allowed(smokingAllowed);

        Call<DriverCreateResponse> driverCreateResponseCall = service.createDriver(" JWT "+token, driverCreateRequest);
        driverCreateResponseCall.enqueue(new Callback<DriverCreateResponse>() {
            @Override
            public void onResponse(Call<DriverCreateResponse> call, Response<DriverCreateResponse> response) {
                int statusCode = response.code();

                DriverCreateResponse driverCreateResponse = response.body();

                Log.d("DriverCreateActivity", "onResponse: "+ statusCode);
            }

            @Override
            public void onFailure(Call<DriverCreateResponse> call, Throwable t) {
                Log.d("DriverCreateActivity", "onFailure: "+ t.getMessage());
            }
        });
    }
}
