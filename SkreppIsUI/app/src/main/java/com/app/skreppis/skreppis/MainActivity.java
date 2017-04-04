package com.app.skreppis.skreppis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.DriverListItemResponse;
import com.app.skreppis.skreppis.models.UrlWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity
    implements MessageBox.OnStateChangeListener {

    // TODO: Replace this dummy value.
    boolean isDriver;

    Button mFindRideBtn;
    Button mFindPassengerBtn;
    Button mBecomeDriverBtn;
    String token;
    String username;
    private UrlWrapper urlWrap;
    SkreppIsApi service;

    /* Þetta er staðan sem messagebox er í.
        "Closed"            The messagebox is closed
        "DriverActive"		Show when driver is active and not busy
        "DriverRequest"		Show when driver gets request from passenger
        "PassengerWaiting"	Show driver when driving towards passenger
        "PassengerCancels"	Show driver when a passenger cancels ride
        "DriverDestination"	Show driver when driving passenger towards destination
        "RequestSent"		Show Passenger while driver is responding to request
        "RequestRejected"	Show Passenger if Driver rejects request
        "RequestTimeout"	Show Passenger if Driver does not respond in time
        "RequestSuccess"	Show Passenger while driver is traveling to pick up
        "DestEta"			Show Passenger while travelling to destination
        "DriverCancelsE"	Show if driver cancels before picking you up
        "DriverCancelsL"	Show if driver cancels after picking you up
        "YouCancelE"		Show Passenger if they cancel early
        "YouCancelL"		Show Passenger if they cancel late
        "Arrival"			Show Passenger upon arrival
     */
    String mbState;

    Fragment mMessageBox;

    //TODO: Test dæmi
    AppCompatSpinner mMessageBoxTestSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        username = extras.getString("Username");
        Log.d("Token:", token);
        Log.d("Username:", username);
        makepref();
        isDriver = sharedPref.getBoolean(getString(R.string.pref_isdriver), false);
        mbState = sharedPref.getString(getString(R.string.pref_mbstate), "Closed");

        urlWrap = new UrlWrapper();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlWrap.getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.print("bound");

        service = retrofit.create(SkreppIsApi.class);

        Call<DriverListItemResponse> driverListItemResponseCall = service.getDriver(token, username);
        driverListItemResponseCall.enqueue(new Callback<DriverListItemResponse>() {
            @Override
            public void onResponse(Call<DriverListItemResponse> call, Response<DriverListItemResponse> response) {
                int statusCode = response.code();

                DriverListItemResponse driver = response.body();

                Log.d("Driver", "onResponse: "+ statusCode);
                if(statusCode == 200){
                    toggleDriver(true);
                } else{
                    toggleDriver(false);
                }
            }

            @Override
            public void onFailure(Call<DriverListItemResponse> call, Throwable t) {
                Log.d("Driver", "onResponse: "+ t.getMessage());
            }
        });

    }

    //TODO: Debug method. Implement alternative way to detect user status
    private void toggleDriver(boolean makeDriver) {
        if(makeDriver) {
            setContentView(R.layout.activity_main_driver);
            manageRideButton();

            mBecomeDriverBtn = null;

            //TODO: Fjarlægja þennan spinner
            manageTestSpinner();

            mFindPassengerBtn = (Button) findViewById(R.id.bt_find_passenger);
            mFindPassengerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findPassenger();
                }
            });
            sharedPref.edit().putBoolean(getString(R.string.pref_isdriver), true).apply();
            onStateChange(mbState);
        } else {
            setContentView(R.layout.activity_main_passenger);
            manageRideButton();

            manageBecomeDriverButton();

            //TODO: Fjarlægja þennan spinner
            manageTestSpinner();

            mFindPassengerBtn = null;
            sharedPref.edit().putBoolean(getString(R.string.pref_isdriver), false).apply();
            onStateChange(mbState);
        }
    }

    //TODO: helper function for above. move to oncreate once above is removed
    private void manageRideButton() {
        mFindRideBtn = (Button) findViewById(R.id.bt_find_ride);
        mFindRideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findRide();
            }
        });
    }


    private void manageBecomeDriverButton(){
        mBecomeDriverBtn = (Button) findViewById(R.id.bt_become_driver);
        mBecomeDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                becomeDriver();
            }
        });
    }


    //TODO: Fjarlægja þetta method
    private void manageTestSpinner(){
        mMessageBoxTestSpinner = (AppCompatSpinner) findViewById(R.id.test_spinner);
        mMessageBoxTestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    return;
                }else{
                    String state = mMessageBoxTestSpinner.getItemAtPosition(position).toString();
                    sharedPref.edit().putString(getString(R.string.pref_mbstate), state).apply();
                    mbState = sharedPref.getString(getString(R.string.pref_mbstate), "closed");
                    onStateChange(mbState);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;

            }
        });
    }

    protected void findRide() {
        Intent intent = new Intent(this, FindRideActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    public void findPassenger() {
        Intent intent = new Intent(this, FindPassengerActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    public void becomeDriver() {
        Intent intent = new Intent(this, BecomeDriverActivity.class);
        intent.putExtra("Token", token);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    public void onStateChange(String state){
        int parent;
        if (isDriver){
            parent = R.id.messagebox_container_driver;
        }else {
            parent = R.id.messagebox_container_passenger;
        }
        if (state.equals("Closed")) {
            if(mMessageBox != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(mMessageBox)
                        .commit();
            }
            return;
        }
        mMessageBox = MessageBox.newInstance(state);
        getSupportFragmentManager().beginTransaction()
                .replace(parent, mMessageBox)
                .commit();
    }

/*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem toggle = (MenuItem) findViewById(R.id.menu_toggle_driver);
        return super.onPrepareOptionsMenu(menu);
    }*/
}
