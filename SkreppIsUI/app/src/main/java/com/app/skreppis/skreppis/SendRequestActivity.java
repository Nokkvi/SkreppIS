package com.app.skreppis.skreppis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.UrlWrapper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendRequestActivity extends AppCompatActivity {

    private SkreppIsApi service;
    private AppCompatSpinner mZoneView;
    private EditText mPickupView;
    private Button mSendRequestButton;
    private UrlWrapper urlWrap;
    private String token;
    private String username;
    private String seats;
    private String startZone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));

        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        username = extras.getString("Username");
        seats = extras.getString("numseats");
        startZone = extras.getString("pickupzone");

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
                sendRequest();
            }
        });

    }

    private void sendRequest(){
        String start = startZone;
        String end = mZoneView.getSelectedItem().toString();
        String seatsString = seats;
        String pickupString = mPickupView.toString();
        int seats = Integer.parseInt(seatsString);


    }

}
