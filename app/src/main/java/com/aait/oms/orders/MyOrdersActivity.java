package com.aait.oms.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.product.ProductModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {

    ListView myorderlistview;
    MyOrdersAdapter myOrdersAdapter;
    List<OrderMasterModel> myOrderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("My Orders");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        myorderlistview = findViewById(R.id.myorderlistviewid);
        myOrderList = new ArrayList<OrderMasterModel>();
        getmyorderlist(this);



    }



    public void  getmyorderlist(Context context){

        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        Call<BaseResponse> call = service.getUserOrders("absfaruk");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if (response.isSuccessful()){

                    BaseResponse baseResponse = response.body();

                    myOrderList = baseResponse.getItems();
                    List<OrderMasterModel> ordermodel = new ArrayList<>();

                    for(int i = 0 ; i<myOrderList.size(); i++){
                        Object getrow =myOrderList.get(i);
                        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getrow;

                        String orderid = String.valueOf(t.get("orderId"));
                        String comid = String.valueOf(t.get("companyId"));
                        String branchId = String.valueOf(t.get("branchId"));
                        String userName = String.valueOf(t.get("userName"));
                        String orderdate = String.valueOf(t.get("orderDate"));
                        String shipadd = String.valueOf(t.get("shippingAddress"));
                        String orderstatus = String.valueOf(t.get("activStatus"));
                        String delstatus = String.valueOf(t.get("deliveryStatus"));

                      OrderMasterModel orders = new OrderMasterModel(orderid,comid,branchId,userName,orderdate,shipadd,orderstatus,delstatus);
                        ordermodel.add(orders);

                    }



                    myOrdersAdapter = new MyOrdersAdapter(context,ordermodel);
                    myorderlistview.setAdapter(myOrdersAdapter);

                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                Log.d("Failure",t.getMessage());

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}