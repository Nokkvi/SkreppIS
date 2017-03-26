package com.app.skreppis.skreppis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.skreppis.skreppis.R;

import static android.R.attr.x;
import static android.R.attr.y;

public class ManageProfileActivity extends BaseActivity {

    Button mChangeUnameBt;
    Button mSaveUnameBt;
    Button mChangeFnameBt;
    Button mSaveFnameBt;
    Button mChangeLnameBt;
    Button mSaveLnameBt;
    Button mChangePhoneBt;
    Button mSavePhoneBt;
    Button mChangePasswordBt;
    Button mBecomeDriverBt;
    Button mDebugDriverBt;
    Button mChangeSeatsBt;
    Button mSaveSeatsBt;
    Button mChangeSmokeBt;
    Button mSaveSmokeBt;

    View mUnameView;
    View mUnameCView;
    View mFnameView;
    View mFnameCView;
    View mLnameView;
    View mLnameCView;
    View mPhoneView;
    View mPhoneCView;
    View mSeatsView;
    View mSeatsCView;
    View mSmokeView;
    View mSmokeCView;
    View mDriverView;
    //ImageView imageView;
    boolean isDriver;
    //Drawable drawable;
    //Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makepref();
        isDriver = true;
        setContentView(R.layout.activity_manage_profile);


        mChangeUnameBt = (Button) findViewById(R.id.btn_man_profile_ch_uname);
        mSaveUnameBt = (Button) findViewById(R.id.btn_man_profile_sv_uname);
        mChangeFnameBt = (Button) findViewById(R.id.btn_man_profile_ch_fname);
        mSaveFnameBt = (Button) findViewById(R.id.btn_man_profile_sv_fname);
        mChangeLnameBt = (Button) findViewById(R.id.btn_man_profile_ch_lname);
        mSaveLnameBt = (Button) findViewById(R.id.btn_man_profile_sv_lname);
        mChangePhoneBt = (Button) findViewById(R.id.btn_man_profile_ch_phone);
        mSavePhoneBt = (Button) findViewById(R.id.btn_man_profile_sv_phone);
        mChangeSeatsBt = (Button) findViewById(R.id.btn_man_profile_ch_seats);
        mSaveSeatsBt = (Button) findViewById(R.id.btn_man_profile_sv_seats);
        mChangeSmokeBt = (Button) findViewById(R.id.btn_man_profile_ch_smoke);
        mSaveSmokeBt = (Button) findViewById(R.id.btn_man_profile_sv_smoke);
        mBecomeDriverBt = (Button) findViewById(R.id.bt_man_profile_become_driver);
        mDebugDriverBt = (Button) findViewById(R.id.bt_man_profile_debug_driver);
        mChangePasswordBt = (Button) findViewById(R.id.bt_man_profile_ch_password);

        mUnameView = findViewById(R.id.man_uname_view);
        mUnameCView = findViewById(R.id.man_uname_cview);
        mFnameView = findViewById(R.id.man_fname_view);
        mFnameCView = findViewById(R.id.man_fname_cview);
        mLnameView = findViewById(R.id.man_lname_view);
        mLnameCView = findViewById(R.id.man_lname_cview);
        mPhoneView = findViewById(R.id.man_phone_view);
        mPhoneCView = findViewById(R.id.man_phone_cview);
        mSeatsView = findViewById(R.id.man_seats_view);
        mSeatsCView = findViewById(R.id.man_seats_cview);
        mSmokeView = findViewById(R.id.man_smoke_view);
        mSmokeCView = findViewById(R.id.man_smoke_cview);
        mDriverView = findViewById(R.id.man_driver_view);

        driverMode();

        mChangeUnameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(true, mUnameView, mUnameCView);
            }
        });
        mSaveUnameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(false, mUnameView, mUnameCView);
            }
        });
        mChangeFnameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(true, mFnameView, mFnameCView);
            }
        });
        mSaveFnameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(false, mFnameView, mFnameCView);
            }
        });
        mChangeLnameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(true, mLnameView, mLnameCView);
            }
        });
        mSaveLnameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(false, mLnameView, mLnameCView);
            }
        });
        mChangePhoneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(true, mPhoneView, mPhoneCView);
            }
        });
        mSavePhoneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(false, mPhoneView, mPhoneCView);
            }
        });
        mChangeSeatsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(true, mSeatsView, mSeatsCView);
            }
        });
        mSaveSeatsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(false, mSeatsView, mSeatsCView);
            }
        });
        mChangeSmokeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(true, mSmokeView, mSmokeCView);
            }
        });
        mSaveSmokeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapVisible(false, mSmokeView, mSmokeCView);
            }
        });
        mBecomeDriverBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDriver = true;
                driverMode();
            }
        });
        mDebugDriverBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDriver = false;
                driverMode();
            }
        });
        mChangePasswordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        //imageView = (ImageView) findViewById(R.id.profileImage);

        //drawable = ContextCompat.getDrawable(this, R.drawable.profile_pic);
        //bitmap = ((BitmapDrawable) drawable).getBitmap();
        //Bitmap newBitmap = convertImage(bitmap);
        //imageView.setImageBitmap(newBitmap);

    }

    private void changePassword(){
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    //Swaps visibility of two views.
    private void swapVisible(boolean show, View v1, View v2){
        v1.setVisibility(show ? View.GONE : View.VISIBLE);
        v2.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void driverMode() {
        if(isDriver){
            mBecomeDriverBt.setVisibility(View.GONE);
            mDebugDriverBt.setVisibility(View.VISIBLE);
            mDriverView.setVisibility(View.VISIBLE);
        }else{
            mBecomeDriverBt.setVisibility(View.VISIBLE);
            mDebugDriverBt.setVisibility(View.GONE);
            mDriverView.setVisibility(View.GONE);
        }
    }
/*
    //For the profile picture to appear in grayscale
    //happy days
    //prob not that efficient tho
    public static Bitmap convertImage(Bitmap original) {
        Bitmap finalImage = Bitmap.createBitmap(original.getWidth(),
                original.getHeight(), original.getConfig() );

        int A, R, G, B;
        int colorPixel;
        int width = original.getWidth();
        int height = original.getHeight();


        for(int x = 0; x < height; x++) {
            for(int y = 0; y < width; y++) {
                colorPixel = original.getPixel(x, y);
                A = Color.alpha(colorPixel);
                R = Color.red(colorPixel);
                G = Color.green(colorPixel);
                B = Color.blue(colorPixel);

                R = (R + G + B) / 3;
                G = R;
                B = R;

                finalImage.setPixel(x, y, Color.argb(A,R,G,B));

            }
        }
        return finalImage;
    }
*/

}
