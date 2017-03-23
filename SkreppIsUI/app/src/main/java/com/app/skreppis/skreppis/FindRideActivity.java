package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
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
                goToDriverSearchCriteria();
            }
        });
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRideRequest();
            }
        });
    }

    public void goToDriverSearchCriteria() {
        Intent intent = new Intent(this, SearchDriverCriteriaActivity.class);
        startActivity(intent);
    }

    public void goToRideRequest() {
        Intent intent = new Intent(this, PostRideRequestActivity.class);
        startActivity(intent);
    }



}
