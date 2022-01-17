package com.aait.oms.users;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditingActivity extends AppCompatActivity {

    EditText fname,lname,phone,email,address,dob,marital,religion,blood;
    Button btnSubmit;
    ProgressBar prograss;
    String user = "";

    AppUtils appUtils;
    SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editing);

        fname = findViewById(R.id.profile_edit_firstname);
        lname = findViewById(R.id.profile_edit_lastname);
        phone = findViewById(R.id.profile_edit_phone);
        email = findViewById(R.id.profile_edit_email);
        address = findViewById(R.id.profile_edit_address);
        dob = findViewById(R.id.profile_edit_dob);
        marital = findViewById(R.id.profile_edit_marital);
        religion = findViewById(R.id.profile_edit_religion);
        blood = findViewById(R.id.profile_edit_blood_group);
        btnSubmit = findViewById(R.id.btn_profile_edit);
        prograss = findViewById(R.id.profileProgressBar);

        prograss.setVisibility(View.INVISIBLE);
        sqLiteDB = new SQLiteDB(this);
        Cursor cursor = sqLiteDB.getUserInfo();
        appUtils = new AppUtils(this);


        if (cursor.moveToFirst()) {
            user = cursor.getString(1);
            fname.setText(cursor.getString(5));
            lname.setText(cursor.getString(6));
            phone.setText(cursor.getString(7));
            email.setText(cursor.getString(8));
            address.setText(cursor.getString(9));
            dob.setText(cursor.getString(10));
            marital.setText(cursor.getString(11));
            religion.setText(cursor.getString(12));
            blood.setText(cursor.getString(13));
        }



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prograss.setVisibility(View.VISIBLE);
                profileUpdate(user);
            }
        });


    }

    private void profileUpdate(String username) {

        String firstName = fname.getText().toString().trim();
        String lastname = lname.getText().toString().trim();
        String phoneNo = phone.getText().toString().trim();
        String emailid = email.getText().toString().trim();
        String addrs = address.getText().toString().trim();
        String dofb = dob.getText().toString().trim();
        String maritalstatus = marital.getText().toString().trim();
        String relig = religion.getText().toString().trim();
        String bloodG = blood.getText().toString().trim();

        UserModel userModel = new UserModel();

        userModel.setUserid(user);
        userModel.setFname(firstName);
        userModel.setLname(lastname);
        userModel.setPhone2(phoneNo);
        userModel.setEmail(emailid);
        userModel.setAddress(addrs);
        userModel.setDob(dofb);
        userModel.setMaritalStatus(maritalstatus);
        userModel.setReligion(relig);
        userModel.setBloodGroup(bloodG);




        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        UserService userService = ApiClient.getRetrofit().create(UserService.class);
        Call<String> call = userService.userUpdate(jsonObject);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    appUtils.appToast("Update Successful");
                    Log.d("ProfileEditingActivity :: ", "profileUpdate Successful ");
                }else{
                    appUtils.appToast("Update Successful");
                    Log.d("ProfileEditingActivity :: ", "profileUpdate Fail ");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                t.getMessage();
                t.getStackTrace();

            }
        });

       // sqLiteDB.updateUserInfo(userModel,user);

      //  appUtils.appToast("Update Successful");
       // prograss.setVisibility(View.INVISIBLE);

    }






}