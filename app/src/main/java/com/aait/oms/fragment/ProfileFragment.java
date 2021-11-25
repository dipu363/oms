package com.aait.oms.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.users.UserModel;
import com.aait.oms.users.UserService;
import com.aait.oms.util.SQLiteDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextView fname, lname, userName, roleId, active, referencedBy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // your task in hare start now;

        fname = view.findViewById(R.id.profile_userfnameid);
        lname = view.findViewById(R.id.profile_userlnameid);
        active = view.findViewById(R.id.profile_statusid);
        userName = view.findViewById(R.id.profile_usernameid);
        roleId = view.findViewById(R.id.profile_roleid);
        referencedBy = view.findViewById(R.id.profile_referenceid);
        SQLiteDB sqLiteDB = new SQLiteDB(getContext());
        Cursor cursor = sqLiteDB.getUserInfo();
        String uname = "";

        if (cursor.moveToFirst()) {
            uname = cursor.getString(1);
        }
        readuserinfo(uname);
        return view;
    }

    public void readuserinfo(String uid) {
        UserService userService = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> call = userService.getuser(uid);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    Object getrow = baseResponse.getObj();
                    Gson gson = new Gson();
                    String json = gson.toJson(baseResponse.getObj());
                    Type typeMyType = new TypeToken<UserModel>() {
                    }.getType();
                    UserModel user = gson.fromJson(json, typeMyType);

                    int role = user.getRoleId();
                    String stat = user.getStatus();

                    if (role == 102) {
                        roleId.setText("General Customer");
                    } else {
                        roleId.setText("Role Not define");
                    }
                    if (stat.equals("1")) {
                        active.setText("Active");
                    } else {
                        active.setText("unknown");
                    }
                    fname.setText(user.getFname());
                    lname.setText(user.getLname());
                    userName.setText("User Name: " + user.getUsername());
                    referencedBy.setText("Referenced By: " + user.getReferenced());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }
}