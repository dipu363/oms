package com.aait.oms.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.aait.oms.R;
import com.aait.oms.product.ProductInGridViewActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    Handler mHandler;
    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove the title ber
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //remove the notification ber
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash_screen);


        mRunnable = () -> {
            try {


                Intent intent = new Intent(SplashScreenActivity.this, ProductInGridViewActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                // Log.d("sss", "error: ");

            }

            finish();
        };

        mHandler = new Handler();
        int SPLASH_TIME_OUT = 3000;
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
    }

    @Override
    public void finish() {
        mHandler.removeCallbacks(mRunnable);
        super.finish();
    }
}