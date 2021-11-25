package com.aait.oms;

import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aait.oms.fragment.CartFragment;
import com.aait.oms.fragment.Favorite_Product_Fragment;
import com.aait.oms.fragment.LogInFragment;
import com.aait.oms.fragment.ProductFragment;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;


public class MainActivity extends AppCompatActivity {

    // initialize variable
    MeowBottomNavigation meowBottomNavigation;

    SQLiteDB sqLiteDB;
    AppUtils appUtils;
    Cursor favoriteCount,cartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        sqLiteDB = new SQLiteDB(this);
        appUtils = new AppUtils(this);


        favoriteCount = sqLiteDB.getallfavoriteProduct();
        cartCount = sqLiteDB.getAllCardProduct();
        meowBottomNavigation = findViewById(R.id.bottom_navigation_id);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_icon));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_favorite_botton_40));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_add_shopping_cart_black_24dp));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_perm_identity_24));


        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;


                switch (item.getId()) {

                    case 1:
                        fragment = new ProductFragment();
                        break;
                    case 2:
                        fragment = new Favorite_Product_Fragment();
                        meowBottomNavigation.clearCount(2);
                        break;
                    case 3:
                        meowBottomNavigation.clearCount(3);
                        fragment = new CartFragment();
                        break;
                    case 4:
                        fragment = new LogInFragment();


                        break;
                }

                loadFragment(fragment);
            }
        });
        //set Cart count




        // set initali selected fragment
        meowBottomNavigation.show(1, true);
        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
               // Toast.makeText(MainActivity.this, "Click " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(MainActivity.this, "Click " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        favoriteCount = sqLiteDB.getallfavoriteProduct();
        cartCount = sqLiteDB.getAllCardProduct();

        meowBottomNavigation.setCount(2, String.valueOf(favoriteCount.getCount()));
        meowBottomNavigation.setCount(3, String.valueOf(cartCount.getCount()));

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_framlayout_id, fragment)
                .commit();

    }
}