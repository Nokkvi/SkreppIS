package com.app.skreppis.skreppis;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.skreppis.skreppis.R;

import static android.R.attr.x;
import static android.R.attr.y;

public class ManageProfileActivity extends BaseActivity {

    ImageView imageView;

    Drawable drawable;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        imageView = (ImageView) findViewById(R.id.profileImage);

        drawable = ContextCompat.getDrawable(this, R.drawable.profile_pic);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap newBitmap = convertImage(bitmap);
        imageView.setImageBitmap(newBitmap);

    }

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


}
