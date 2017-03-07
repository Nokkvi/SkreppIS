package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // TODO: Replace this dummy value.
    boolean isDriver = false;

    Button mFindRideBtn;
    Button mFindPassengerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDriver) {
            toggleDriver(true);
        } else {
            toggleDriver(false);
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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
            isDriver = true;
        } else {
            setContentView(R.layout.activity_main_passenger);
            manageRideButton();
            mFindPassengerBtn = null;
            isDriver = false;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void findRide() {
        Intent intent = new Intent(this, FindRideActivity.class);
        startActivity(intent);
    }

    public void findPassenger() {
        Intent intent = new Intent(this, FindPassengerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_manageprofile) {
            return true;
        }
        if (id == R.id.menu_ridehistory) {
            return true;
        }
        if (id == R.id.menu_logout) {
            return true;
        }
        if (id == R.id.menu_about){
            return true;
        }
        if (id == R.id.menu_toggle_driver) {
            if(isDriver){
                toggleDriver(false);
            }else {
                toggleDriver(true);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
