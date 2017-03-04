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

public class MainActivity extends AppCompatActivity implements MainDriverFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button rideButton = (Button) findViewById(R.id.p_findride);
        //optional passengerbutton

        //change to fragment?
        final TextView messageBox = (TextView) findViewById(R.id.messagebox);

        rideButton.setOnClickListener(new View.OnClickListener() {
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

        return super.onOptionsItemSelected(item);
    }
}
