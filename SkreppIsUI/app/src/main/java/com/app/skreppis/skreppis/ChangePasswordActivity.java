package com.app.skreppis.skreppis;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button mSavePassword;
    private Button mForgotPassword;

    private EditText mOldPasswordField;
    private EditText mNewPasswordField;
    private EditText mRepPasswordField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));

        mOldPasswordField = (EditText) findViewById(R.id.man_pw_old_field);
        mNewPasswordField = (EditText) findViewById(R.id.man_pw_new_field);
        mRepPasswordField = (EditText) findViewById(R.id.man_pw_rep_field);

        mSavePassword = (Button) findViewById(R.id.man_pw_bt);
        mForgotPassword = (Button) findViewById(R.id.forgot_pw_bt);


        mSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, R.string.man_profile_forgotpw, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
