package com.app.skreppis.skreppis;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FindRideActivity extends BaseActivity {

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

}
