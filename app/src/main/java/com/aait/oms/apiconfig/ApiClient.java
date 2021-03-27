package com.aait.oms.apiconfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://aborong.com/orderapi/orderapi/";
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








//    public static Retrofit getClient(){
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
//
//
//
//        if(retrofit==null){
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(okHttpClient)
//                    .build();
//        }
//        return retrofit;
//    }

//    public static SupplierInterface getSupplierservice(){
//        SupplierInterface supplierInterface = getClient().create(SupplierInterface.class);
//        return supplierInterface;
//    };
/*
    public static ProductInterface getrootcatService(){
        ProductInterface productInterface = getClient().create(ProductInterface.class);
        return productInterface;
    };
    public static ProductInterface getgetsubcattService(){
        ProductInterface productInterface = getClient().create(ProductInterface.class);
        return productInterface;
    }; */
//    public static ProductInterface getProductService(){
//        ProductInterface productInterface = getClient().create(ProductInterface.class);
//        return productInterface;
//    };
}
