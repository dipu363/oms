package com.aait.oms.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {
    String orderid;
    List<OrderDetailsModel> orderDetailsList;
    ListView orderdeataillistview;
    TextView totaltextview;
    OrderDetailsAdapter orderDetailsAdapter;
    AppUtils appUtils;
    Double total = 0d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("  Order Details");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        orderdeataillistview = findViewById(R.id.orderdetaillistid);
        totaltextview = findViewById(R.id.orderdetailtotaltextid);
        orderDetailsList = new ArrayList<>();
        appUtils = new AppUtils(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderid = bundle.getString("orderid");
        }
        getorderdetails(this, orderid);
    }


    public void getorderdetails(Context context, String orderid) {
        OrderService service = ApiClient.getRetrofit().create(OrderService.class);
        Call<BaseResponse> call = service.getOrderdetails(Integer.parseInt(orderid));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = response.body();
                    orderDetailsList = baseResponse.getItems();
                    List<OrderDetailsModel> detaillist = new ArrayList<>();
                    Gson gson = new Gson();
                    String json = gson.toJson(orderDetailsList);
                    Type typeMyType = new TypeToken<ArrayList<OrderDetailsModel>>() {
                    }.getType();
                    detaillist = gson.fromJson(json, typeMyType);
                    if (detaillist.size() > 0) {
                        orderDetailsAdapter = new OrderDetailsAdapter(context, detaillist);
                        orderdeataillistview.setAdapter(orderDetailsAdapter);
                        for (int i = 0; i < detaillist.size(); i++) {
                            OrderDetailsModel itemtotal = detaillist.get(i);
                            total = total + itemtotal.getItemTotal();
                        }
                        totaltextview.setText("Order Total: " + String.format("%.2f", total));
                    } else {
                        appUtils.appToast("Item Not found");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}