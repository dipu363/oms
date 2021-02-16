package com.aait.oms.users;

import com.aait.oms.model.BaseResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("users/adduser")
    Call<String> saveUser(@Body JsonObject json);


 /*   @GET("user/findByUser")
    Call<String> getuser(@Query("userName") String id);*/


    @GET("user/findByUser")
    Call<BaseResponse> getuser(@Query("userName") String userName);

    /*@GET("Account/RegisterMobileNumber")
    Call<JsonObject> sendSMSToMobileNumber(@Query("mobileNumber") String mobileNumber);*/


  /*  @GET("GetDivision/{id}")
    Call<List<MpoModel>> getMpofromDivision(@Path("id") long id);
    @GET("GetDivision/{id}")
    Call<List<MpoModel>> getMpofromDivision(@Path("id") long id);

    @GET("Login") //i.e https://api.demo.com/Search?
    Call<Products> getUserDetails(@Query("email") String emailID);*/


}
