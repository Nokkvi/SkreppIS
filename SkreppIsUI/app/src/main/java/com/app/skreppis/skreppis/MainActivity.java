package com.app.skreppis.skreppis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity
    implements MessageBox.OnStateChangeListener {

    // TODO: Replace this dummy value.
    boolean isDriver;

    Button mFindRideBtn;
    Button mFindPassengerBtn;
    String token;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        token = extras.getString("Token");
        Log.d("Token:", token);
        makepref();
        isDriver = sharedPref.getBoolean(getString(R.string.pref_isdriver), false);
        mbState = sharedPref.getString(getString(R.string.pref_mbstate), "closed");

        if (isDriver) {
            toggleDriver(true);
        } else {
            toggleDriver(false);
        }

        //change to fragment?
        //final TextView messageBox = (TextView) findViewById(R.id.messagebox);
    }

    //TODO: Debug method. Implement alternative way to detect user status
    private void toggleDriver(boolean makeDriver) {
        if(makeDriver) {
            setContentView(R.layout.activity_main_driver);
            manageRideButton();
            mFindPassengerBtn = (Button) findViewById(R.id.bt_find_passenger);
            mFindPassengerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findPassenger();
                }
            });
            sharedPref.edit().putBoolean(getString(R.string.pref_isdriver), true).apply();
        } else {
            setContentView(R.layout.activity_main_passenger);
            manageRideButton();
            mFindPassengerBtn = null;
            sharedPref.edit().putBoolean(getString(R.string.pref_isdriver), false).apply();
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

    protected void findRide() {
        Intent intent = new Intent(this, FindRideActivity.class);
        intent.putExtra("Token", token);
        startActivity(intent);
    }

    public void findPassenger() {
        Intent intent = new Intent(this, FindPassengerActivity.class);
        intent.putExtra("Token", token);
        startActivity(intent);
    }

    public void onStateChange(String state){

    }


/*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem toggle = (MenuItem) findViewById(R.id.menu_toggle_driver);
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean menuToggleDriver() {
        isDriver = sharedPref.getBoolean(getString(R.string.pref_isdriver), false);
        if(isDriver) {
            toggleDriver(false);
        } else {
            toggleDriver(true);
        }
        return true;
    }
}
