package com.aait.oms.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aait.oms.R;


public class AppUtils {

    Context context;

    public AppUtils(Context context) {
        this.context = context;
    }


    public  void appToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public NetworkInfo deviceNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public void  networkAlertDialog( ){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(" No Network")
                .setMessage("Enable Mobile Network")
                .setIcon(R.drawable.logopng40)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }




    public void checkAndRequestForPermission(AppCompatActivity activity ,int pReqcode ,int requestcode) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(context, "Please accept for required permission", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        pReqcode);
            }

        } else
            openGallery(activity,requestcode);


    }

    public void openGallery(AppCompatActivity activity,int requestcode) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, requestcode);


    }

    public void showExitAlartDialog(AppCompatActivity activity){
        SQLiteDB sqLiteDB = new SQLiteDB(context);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("EXIT")
                .setMessage("Are you sure you want to close the app ?")
                .setCancelable(false)
                .setIcon(R.drawable.logopng40)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        sqLiteDB.updateuserloginstatus(false,1);
                        activity.finish();


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }




}
