package com.aait.oms.apiconfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

/*    //Retrofit2 json convater okhttp3 logininterceptor use this implementation in your build gradle
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.retrofit2:converter-scalars:2.1.0'*/



    private static final String BASE_URL = "http://aborong.com/orderapi/orderapi/";
//    private static final String BASE_URL = "http://100.43.0.38:8080/orderapi/orderapi/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {


            retrofit = new Retrofit.Builder()
                    //when send post request
                    //if error faced Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path $
                    //Error say's you want to get result in String body.
                    //If you want to do this, Just add ScalarsConverterFactory.create() in your Retrofit.Builder.
                    //Use retrofit Implementation in app level build.gradle.
                    //implementation 'com.squareup.retrofit2:converter-scalars:2.1.0' update version
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
