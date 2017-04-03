package com.app.skreppis.skreppis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.app.skreppis.skreppis.interfaces.SkreppIsApi;
import com.app.skreppis.skreppis.models.AuthRequest;
import com.app.skreppis.skreppis.models.AuthResponse;
import com.app.skreppis.skreppis.models.PassengerCreateRequest;
import com.app.skreppis.skreppis.models.PassengerCreateResponse;
import com.app.skreppis.skreppis.models.RegisterRequest;
import com.app.skreppis.skreppis.models.RegisterResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_CONTACTS;

public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText mUnameView;
    private EditText mEmailView;
    private EditText mcEmailView;
    private EditText mPasswordView;
    private EditText mPWRetypeView;
    private EditText mfNameView;
    private EditText mlNameView;
    private EditText mPhoneView;

    private View mRegProgressView;

    private View mRegisterFormView;
    SkreppIsApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the register form.
        mUnameView = (EditText) findViewById(R.id.register_uname_field);
        mEmailView = (EditText) findViewById(R.id.register_email_field);
        //populateAutoComplete();
        mcEmailView = (EditText) findViewById(R.id.register_cemail_field);
        mPasswordView = (EditText) findViewById(R.id.register_pw_field);
        mPWRetypeView = (EditText) findViewById(R.id.register_pwretype_field);
        mfNameView = (EditText) findViewById(R.id.register_fname_field);
        mlNameView = (EditText) findViewById(R.id.register_lname_field);
        mPhoneView = (EditText) findViewById(R.id.register_phone_field);

        Button mRegisterButton = (Button) findViewById(R.id.bt_register_user);


        mRegisterFormView = findViewById(R.id.register_form);
        mRegProgressView = findViewById(R.id.register_progress);

        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(R.string.ip_tala))
                .addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(SkreppIsApi.class);
    }

    protected boolean registerSuccess(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
/*
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }
    */
/*
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }
*/
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.

        mUnameView.setError(null);
        mEmailView.setError(null);
        mcEmailView.setError(null);
        mPasswordView.setError(null);
        mPWRetypeView.setError(null);
        mfNameView.setError(null);
        mlNameView.setError(null);

        // Store values at the time of the login attempt.
        String uname = mUnameView.getText().toString();
        String email = mEmailView.getText().toString();
        String cemail = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String pwretype = mPWRetypeView.getText().toString();
        String fname = mfNameView.getText().toString();
        String lname = mfNameView.getText().toString();
        final String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check if username is filled out
        if (TextUtils.isEmpty(uname)) {
            mUnameView.setError(getString(R.string.error_field_required));
            focusView = mUnameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for matching passwords
        if (!TextUtils.equals(password, pwretype)) {
            mPWRetypeView.setError(getString(R.string.error_field_required));
            focusView = mPWRetypeView;
            cancel = true;
        }

        // Check if first name is filled out
        if (TextUtils.isEmpty(fname)) {
            mfNameView.setError(getString(R.string.error_field_required));
            focusView = mfNameView;
            cancel = true;
        }

        // Check if last name is filled out
        if (TextUtils.isEmpty(lname)) {
            mfNameView.setError(getString(R.string.error_field_required));
            focusView = mlNameView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for matching emails
        if (!TextUtils.equals(email, cemail)) {
            mcEmailView.setError(getString(R.string.error_field_required));
            focusView = mcEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            showProgress(true);
            final RegisterRequest registerRequest = new RegisterRequest();

            registerRequest.setUsername(uname);
            registerRequest.setPassword(password);
            registerRequest.setEmail(email);
            registerRequest.setEmail2(cemail);
            registerRequest.setFirst_name(fname);
            registerRequest.setLast_name(lname);

            Call<RegisterResponse> registerResponseCall = service.getRegistered(registerRequest);

            registerResponseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    int statusCode = response.code();

                    RegisterResponse registerResponse = response.body();

                    showProgress(false);

                    Log.d("RegisterActivity", "onResponse: "+ statusCode);
                    if(statusCode == 201){
                        AuthRequest authRequest = new AuthRequest();
                        authRequest.setUsername(registerResponse.getUsername());
                        authRequest.setPassword(registerRequest.getPassword());
                        Call<AuthResponse> authResponseCall = service.Auth(authRequest);
                        authResponseCall.enqueue(new Callback<AuthResponse>() {
                            @Override
                            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                int statusCode = response.code();
                                Log.d("AuthActivity", "onResponse: "+ statusCode);
                                AuthResponse authResponse = response.body();
                                System.out.println(authResponse.getToken());
                                showProgress(false);
                                PassengerCreateRequest passengerCreateRequest = new PassengerCreateRequest();
                                passengerCreateRequest.setPhone_number(phone);
                                Call<PassengerCreateResponse> createPassengerResponseCall = service.createPassenger(" JWT "+authResponse.getToken(), passengerCreateRequest);
                                createPassengerResponseCall.enqueue(new Callback<PassengerCreateResponse>() {
                                    @Override
                                    public void onResponse(Call<PassengerCreateResponse> call, Response<PassengerCreateResponse> response) {
                                        int statusCode = response.code();
                                        Log.d("CreatePassenger", "onResponse: "+ statusCode);
                                    }

                                    @Override
                                    public void onFailure(Call<PassengerCreateResponse> call, Throwable t) {
                                        Log.d("CreatePassenger", "onFailure: " + t.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<AuthResponse> call, Throwable t) {
                                Log.d("Authorization", "onFailure: " + t.getMessage());
                            }
                        });
                        registerSuccess();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Log.d("RegisterActivity", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mRegProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mRegProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        //addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
    /*
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }
    */

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */



}
