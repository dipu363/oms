package com.aait.oms.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.Menu;

import com.aait.oms.R;
import com.aait.oms.fragment.ProfileFragment;
import com.aait.oms.orders.MyOrdersActivity;
import com.aait.oms.orders.OrderActivity;
import com.aait.oms.product.ProductListActivity;
import com.aait.oms.supplier.SupplierListActivity;
import com.aait.oms.users.MyReferenceActivity;
import com.aait.oms.users.UsersAccountActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
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
        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();




    }

    @Override
    protected void onStart() {
        super.onStart();
    /*    FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            startActivity(new Intent(HomeActivity.this , SendOtpActivity.class));
            finish();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            /*Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AdminHomeFragment()).commit();*/
        } else if (menuItem.getItemId() == R.id.nav_profile) {
           /* Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AdminProfileFragment()).commit();*/

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
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
         /*   FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(com.dipu.milkzone.AdminPanelActivity.this, LoginAs.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Successfully Sign out", Toast.LENGTH_LONG).show();
            finish();*/
        }

        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }
}