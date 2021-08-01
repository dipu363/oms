package com.aait.oms.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.fragment.HomePragment;
import com.aait.oms.fragment.ProfileFragment;
import com.aait.oms.orders.MyOrdersActivity;
import com.aait.oms.orders.OrderActivity;
import com.aait.oms.product.ProductListActivity;
import com.aait.oms.users.MyReferenceActivity;
import com.aait.oms.commission.UsersAccountActivity;
import com.aait.oms.util.SQLiteDB;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        NavigationView navigationView = findViewById(R.id.navigationviewId);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawerLayout.addDrawerListener(toggle);
       toggle.syncState();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomePragment()).commit();




    }

    @Override
    protected void onStart() {
        super.onStart();
    /*    FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(HomeActivity.this , SendOtpActivity.class));
            finish();
        }*/

        userprfileupdate();
    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else if(id == R.id.action_settings){
            Toast.makeText(this , "Unable to Reset this app", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.share) {

            try {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Status Background");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.taagidtech.statusbackground");
                startActivity(Intent.createChooser(intent, "Share With"));
            }catch (Exception e) {

                 Toast.makeText(this , "Unable to share this app.\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.rating) {
            try {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.taagidtech.statusbackground");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }catch (Exception e) {
                Toast.makeText(this, "Unable to Rete this app.\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.privacyid) {
            Intent intent = new Intent(HomeActivity.this,Privacy_PolicyActivity.class);
            startActivity(intent);
        } else if (id == R.id.termsid) {
            Intent intent = new Intent(HomeActivity.this,Trams_Condition_Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_home) {
            /*Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);*/
            Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomePragment()).commit();

        } else if (menuItem.getItemId() == R.id.nav_profile) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();

        } else if (menuItem.getItemId() == R.id.nav_order) {
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        }else if (menuItem.getItemId() == R.id.nav_product) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.nav_myorders) {
           /* Objects.requireNonNull(getSupportActionBar()).setTitle("Users List");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new UsersFragment()).commit();*/

            Intent intent = new Intent(HomeActivity.this, MyOrdersActivity.class);
            startActivity(intent);

        } else if (menuItem.getItemId() == R.id.nav_myrefereces) {
           /* Objects.requireNonNull(getSupportActionBar()).setTitle("Users List");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new UsersFragment()).commit();*/
            Intent intent = new Intent(HomeActivity.this, MyReferenceActivity.class);
            startActivity(intent);


        } else if (menuItem.getItemId() == R.id.nav_account) {
           /* Objects.requireNonNull(getSupportActionBar()).setTitle("Users List");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new UsersFragment()).commit();*/
            Intent intent = new Intent(HomeActivity.this, UsersAccountActivity.class);
            startActivity(intent);


        }  else if (menuItem.getItemId() == R.id.nav_logout) {

           // mAuth.signOut();

            SQLiteDB sqLiteDB = new SQLiteDB(this);
            sqLiteDB.updateuserloginstatus(false,1);

            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Successfully Sign out", Toast.LENGTH_LONG).show();
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }


    public void userprfileupdate() {

        SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        String uname ="";
        if(cursor.moveToFirst()){
            uname = cursor.getString(1);
        }
        NavigationView navigationView = findViewById(R.id.navigationviewId);
        View headerview = navigationView.getHeaderView(0);
       // TextView username = headerview.findViewById(R.id.nav_headernametext_id1);
       // TextView useremail = headerview.findViewById(R.id.nav_headeremailtext_id2);
        //ImageView userimage = headerview.findViewById(R.id.nav_user_photo);
        //username.setText(uname);
       // useremail.setText(currentuser.getEmail());
       // Glide.with(this).load(currentuser.getPhotoUrl()).into(userimage);

    }


    public void showAlartDialog(){
        SQLiteDB sqLiteDB = new SQLiteDB(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("EXIT")
                .setMessage("Are you sure you want to close the app ?")
                .setCancelable(false)
                .setIcon(R.drawable.logopng40)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        sqLiteDB.updateuserloginstatus(false,1);
                        finish();

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

    @Override
    public void onBackPressed() {

            showAlartDialog();


    }

   /* @Override
    protected void onPause() {

        showAlartDialog();
        super.onPause();
    }*/

    /*@Override
    protected void onDestroy() {
        super.onDestroy();


    }*/


}