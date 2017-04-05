package com.app.skreppis.skreppis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.ChangeActiveRequest;
import com.app.skreppis.skreppis.models.ChangeActiveResponse;
import com.app.skreppis.skreppis.models.UrlWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends AppCompatActivity {
    // Kalla á makepref í onCreate falli hvers activity, ekki skilgreina hér.
    // Annars verður leki
    SharedPreferences sharedPref;
    UrlWrapper urlWrap;
    SkreppIsApi service;

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
        sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);
        String token = sharedPref.getString("Token", "token");
        String username = sharedPref.getString("Username", "idiot");
        Log.d("Token", token);
        Log.d("Username", username);

        switch (id) {
            case R.id.menu_home:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("Token", token);
                intent.putExtra("Username", username);
                startActivity(intent);
                return true;
            case R.id.menu_manageprofile:
                intent = new Intent(this, ManageProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_ridehistory:
                intent = new Intent(this, RideHistoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_about:
                intent = new Intent(this, AboutWindowActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                intent = new Intent(this, LoginActivity.class);

                urlWrap = new UrlWrapper();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(urlWrap.getUrl())
                        .addConverterFactory(GsonConverterFactory.create()).build();

                service = retrofit.create(SkreppIsApi.class);

                ChangeActiveRequest changeActiveRequest = new ChangeActiveRequest();
                changeActiveRequest.setActive(false);

                Call<ChangeActiveResponse> changeActiveResponseCall = service.changeActive(" JWT "+token, username, changeActiveRequest);
                changeActiveResponseCall.enqueue(new Callback<ChangeActiveResponse>() {
                    @Override
                    public void onResponse(Call<ChangeActiveResponse> call, Response<ChangeActiveResponse> response) {
                        int statusCode = response.code();
                        Log.d("ChangeActiveActivity", "onResponse: "+ statusCode);
                        ChangeActiveResponse changeActiveResponse = response.body();
                    }

                    @Override
                    public void onFailure(Call<ChangeActiveResponse> call, Throwable t) {
                        Log.d("ChangeActiveActivity", "onFailure: " + t.getMessage());
                    }
                });
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
