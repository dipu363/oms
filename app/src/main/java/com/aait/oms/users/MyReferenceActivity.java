package com.aait.oms.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.OrderMasterModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReferenceActivity extends AppCompatActivity {

    ListView myreflistview;
    MyReferenceAdapter refAdapter;
    List<UsersModel> refuserslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reference);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("My References");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        myreflistview = findViewById(R.id.myreferencelistviewid);
        refuserslist = new ArrayList<UsersModel>();



        getrefUsresList(this);


    }

  public void   getrefUsresList(Context context){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> call = service.getuserReferences("absfaruk");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.isSuccessful()){
                    BaseResponse userslist = response.body();
                    refuserslist = userslist.getItems();
                    List<UsersModel> allrefuser = new ArrayList<UsersModel>();

                    for(int i = 0 ; i<refuserslist.size(); i++) {
                        Object getrow = refuserslist.get(i);
                        LinkedTreeMap<Object, Object> t = (LinkedTreeMap) getrow;

                        String userid = String.valueOf(t.get("userName"));
                        String fname = String.valueOf(t.get("fname"));
                        String lname = String.valueOf(t.get("lname"));
                        String status = String.valueOf(t.get("active"));

                        UsersModel usrs = new UsersModel(userid, fname, lname, status);
                        allrefuser.add(usrs);

                    }

                    refAdapter = new MyReferenceAdapter(context,allrefuser);
                    myreflistview.setAdapter(refAdapter);


                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

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