package com.aait.oms.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aait.oms.MainActivity;
import com.aait.oms.R;
import com.aait.oms.orders.ConfirmOrderActivity;
import com.aait.oms.util.SQLiteDB;

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


                int loginstatus = 0;
                // FirebaseUser  user = mAuth.getCurrentUser();
                SQLiteDB sqLiteDB = new SQLiteDB(this);
                //sqLiteDB.updateuserotp(currentuser,1);
                Cursor cursor = sqLiteDB.getUserInfo();
                if (cursor.moveToFirst()) {
                    loginstatus = cursor.getInt(4);
                }

                if (loginstatus == 1) {
                    Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }



            } catch (Exception e) {
                // Log.d("sss", "error: ");

            }

            finish();
        };

        mHandler = new Handler();
        int SPLASH_TIME_OUT = 2000;
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
    }

    @Override
    public void finish() {
        mHandler.removeCallbacks(mRunnable);
        super.finish();
    }
}