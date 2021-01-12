package com.aait.oms.apiconfig;

import com.aait.oms.interfaces.ProductInterface;
import com.aait.oms.interfaces.SupplierInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://aborong.com/orderapi/orderapi/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();



        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static SupplierInterface getSupplierservice(){
        SupplierInterface supplierInterface = getClient().create(SupplierInterface.class);
        return supplierInterface;
    };

    public static ProductInterface getProductService(){
        ProductInterface productInterface = getClient().create(ProductInterface.class);
        return productInterface;
    };
}
