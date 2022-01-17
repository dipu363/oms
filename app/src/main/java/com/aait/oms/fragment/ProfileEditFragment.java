package com.aait.oms.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.users.UserModel;
import com.aait.oms.users.UserRequest;
import com.aait.oms.users.UserService;
import com.aait.oms.users.UsersViewModel;
import com.aait.oms.util.AppUtils;
import com.aait.oms.util.ApplicationData;
import com.aait.oms.util.SQLiteDB;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditFragment extends Fragment {


    final static String TAG = "ProfileEditFragment";
    TextInputEditText fname,lname,phone,email,address,dob ,religion,marital,blood;
    Button btnSubmit ,btnBack;
    AppUtils appUtils;
    SQLiteDB sqLiteDB;
    String user;

    public ProfileEditFragment() {
        // Required empty public constructor
    }

    public static ProfileEditFragment newInstance(String param1, String param2) {
        ProfileEditFragment fragment = new ProfileEditFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUtils = new AppUtils(getContext());
        sqLiteDB = new SQLiteDB(getContext());
        Cursor cursor = sqLiteDB.getUserInfo();
        if (cursor.moveToFirst()) {
            user = cursor.getString(1);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        fname= view.findViewById(R.id.profile_edit_firstname);
        lname= view.findViewById(R.id.profile_edit_lastname);
        phone= view.findViewById(R.id.profile_edit_phone);
        email= view.findViewById(R.id.profile_edit_email);
        address= view.findViewById(R.id.profile_edit_address);
        dob= view.findViewById(R.id.profile_edit_dob);
        religion= view.findViewById(R.id.profile_edit_religion);
        marital= view.findViewById(R.id.profile_edit_marital);
        blood= view.findViewById(R.id.profile_edit_blood_group);
        btnSubmit= view.findViewById(R.id.btn_profile_edit_update);
        btnBack= view.findViewById(R.id.btn_profile_back);

        getUserInfo(user);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpdate();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
            }
        });

        return view;

    }



    private void profileUpdate() {


        String firstName = fname.getText().toString().trim();
        String lastname = lname.getText().toString().trim();
        String phoneNo = phone.getText().toString().trim();
        String emailid = email.getText().toString().trim();
        String addrs = address.getText().toString().trim();
        String dofb = dob.getText().toString().trim();
        String maritalstatus = marital.getText().toString().trim();
        String relig = religion.getText().toString().trim();
        String bloodG = blood.getText().toString().trim();

        UsersViewModel userModel = new UsersViewModel();
        userModel.setUsername(user);
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
                    Log.d("ProfileEditingActivity :: ", "msg "+ response.message());
                }else{
                    appUtils.appToast("Update Successful");
                    Log.d("ProfileEditingActivity :: ", "msg "+ response.message());
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



    public void getUserInfo(String user){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> userres = service.getuser(user);

        userres.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {


                if (response.isSuccessful() ){
                    BaseResponse res = response.body();
                    Gson gson = new Gson();
                    String json = gson.toJson(res.getObj());
                   /* JsonObject jsonObject = null;
                    jsonObject = new JsonParser().parse(json).getAsJsonObject();*/
                    Type typeMyType = new TypeToken<UserModel>() {}.getType();
                    UserModel user = gson.fromJson(json, typeMyType);

                    fname.setText(user.getFname());
                    lname.setText(user.getLname());
                    phone.setText(user.getPhone2());
                    email.setText(user.getEmail());
                    address.setText(user.getAddress());
                    dob.setText(user.getDob());
                    religion.setText(user.getReligion());
                    marital.setText(user.getMaritalStatus());
                    blood.setText(user.getBloodGroup());

                }
            }


            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(TAG ,"getUserInfo :: "+t.getMessage());
            }
        });

    }

}