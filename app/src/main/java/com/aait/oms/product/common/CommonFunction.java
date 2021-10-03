package com.aait.oms.product.common;

import android.content.Context;
import android.util.Log;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductInterface;
import com.aait.oms.product.UOMModel;
import com.aait.oms.users.UserModel;
import com.aait.oms.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonFunction {
    Context context;


    String uomCode;
    public CommonFunction(Context context) {
        this.context = context;
    }


    public String getuomname( int id){
        AppUtils appUtils = new AppUtils(context);



        ProductInterface apiService =  ApiClient.getRetrofit().create(ProductInterface.class);
        retrofit2.Call<BaseResponse> uom = apiService.getumlname(id);
        uom.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(retrofit2.Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();

                if(baseResponse.getMessage().equals("") ) {

                    appUtils.appToast("Data Not Found");
                } else{
                    // Log.e("success",response.body().toString());
                     //BaseResponse baseResponse = response.body();
                    assert baseResponse != null;

                    Gson gson = new Gson();
                    String json = gson.toJson(baseResponse.getObj());
                    Type typeMyType = new TypeToken<UOMModel>(){}.getType();
                    UOMModel uom = gson.fromJson(json, typeMyType);


                   uomCode = uom.getUomCode();
                   String uomCodename = uom.getName();
                  int uomCodeid = uom.getUomId();

                    System.out.println(uom.toString());





                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });


    return uomCode;

    }

}
