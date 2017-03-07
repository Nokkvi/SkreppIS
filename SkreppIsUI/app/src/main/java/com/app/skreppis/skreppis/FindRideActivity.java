package com.app.skreppis.skreppis;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FindRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);

        Button findButton = (Button) findViewById(R.id.p_finddriver);
        Button requestButton = (Button) findViewById(R.id.p_requestdriver);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.test_text1, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.test_text2, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        if (id == R.id.menu_about) {
            return true;
        }
        if (id == R.id.menu_toggle_driver) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
