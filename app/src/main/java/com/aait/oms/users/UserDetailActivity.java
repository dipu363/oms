package com.aait.oms.users;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.util.PasswordBecrypt;

import java.util.ArrayList;

public class UserDetailActivity extends AppCompatActivity {

    TextView fname,lname,status,email,phone,address,comlyare,promossiondate,rolename;

    ArrayList<String> alldata = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("   Profile");
        fname = findViewById(R.id.user_detail_userfnameid);
        lname = findViewById(R.id.user_detail_userlnameid);
        status = findViewById(R.id.user_detail_statusid);
        email = findViewById(R.id.user_detail_emailid);
        phone = findViewById(R.id.user_detail_phoneid);
        comlyare = findViewById(R.id.user_detail_levelid);
        rolename = findViewById(R.id.user_detail_roleid);
        promossiondate = findViewById(R.id.user_detail_promossiondateid);
        address = findViewById(R.id.user_detail_addressid);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

             alldata = bundle.getStringArrayList("user");
             String stat = alldata.get(3);

            if (stat.equals("1")){

                status.setText("Active");
            }else{
                status.setText("DeActive");
            }

            fname.setText(" "+alldata.get(0));
            lname.setText(" "+alldata.get(1));
            rolename.setText(" "+alldata.get(2));
            address.setText(" "+alldata.get(4));
            phone.setText(" "+alldata.get(5));
            email.setText(" "+alldata.get(6));
            promossiondate.setText(" "+alldata.get(7));
            comlyare.setText(" "+"Level "+ alldata.get(8));

        }


        PasswordBecrypt passwordBecrypt = new PasswordBecrypt();
        try {
           String outPutencode = passwordBecrypt.encrypt("dipu","123");

            Log.d("encode",outPutencode);
            String outputdecode = passwordBecrypt.becrypt(outPutencode,"123");

            Log.d("decode",outputdecode);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}