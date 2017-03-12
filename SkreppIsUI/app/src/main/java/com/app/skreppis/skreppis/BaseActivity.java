package com.app.skreppis.skreppis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {
    // Kalla á makepref í onCreate falli hvers activity, ekki skilgreina hér.
    // Annars verður leki
    SharedPreferences sharedPref;

    public void makepref(){
        sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);
    }

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
                intent = new Intent(this, LoginActivity.class);
                //TODO: loka á núverandi notanda.
                startActivity(intent);
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
