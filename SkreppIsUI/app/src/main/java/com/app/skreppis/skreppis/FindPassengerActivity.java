package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FindPassengerActivity extends BaseActivity {

    private Button mToggleActive;
    private Button mSearchPassenger;

    boolean isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_passenger);

        //TODO: finna Active stöðu bílstjóra
        isActive = sharedPref.getBoolean(getString(R.string.pref_isactive), false);

        mToggleActive = (Button) findViewById(R.id.d_toggleactive);
        mSearchPassenger = (Button) findViewById(R.id.d_searchpassengers);

        mToggleActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isActive){
                    Snackbar.make(v, R.string.d_notActive, Snackbar.LENGTH_LONG).show();
                    sharedPref.edit().putBoolean(getString(R.string.pref_isactive), false).apply();
                }else {
                    Snackbar.make(v, R.string.d_isActive, Snackbar.LENGTH_LONG).show();
                    sharedPref.edit().putBoolean(getString(R.string.pref_isactive), true).apply();
                }
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
        startActivity(intent);
    }
}
