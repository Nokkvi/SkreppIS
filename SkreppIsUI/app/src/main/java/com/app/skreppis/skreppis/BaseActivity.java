package com.app.skreppis.skreppis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_manageprofile:
                intent = new Intent(this, ManageProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_ridehistory:
                intent = new Intent(this, RideHistoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_about:
                intent = new Intent(this, PopUpWindow.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                return true;
            case R.id.menu_toggle_driver:
                menuToggleDriver();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean menuToggleDriver() {
        return true;
    }
}
