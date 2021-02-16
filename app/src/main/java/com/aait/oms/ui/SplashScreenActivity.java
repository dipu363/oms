package com.aait.oms.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.aait.oms.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 1000;
    Handler mHandler;
    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove the title ber
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //remove the notification ber
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash_screen);


        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {

                   // Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                    Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
//                    Log.d("sss", "error: ");

                }

                finish();
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
    }

    @Override
    public void finish() {
        mHandler.removeCallbacks(mRunnable);
        super.finish();
    }
}