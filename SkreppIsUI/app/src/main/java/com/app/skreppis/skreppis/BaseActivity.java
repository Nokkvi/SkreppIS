package com.app.skreppis.skreppis;

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

        switch (id) {
            case R.id.menu_manageprofile:
                return true;
            case R.id.menu_ridehistory:
                return true;
            case R.id.menu_about:
                return true;
            case R.id.menu_logout:
                return true;
            case R.id.menu_toggle_driver:
                menuToggleDriver();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean menuToggleDriver(){
        return true;
    }
}
