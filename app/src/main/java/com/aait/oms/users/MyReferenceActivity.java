package com.aait.oms.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.orders.OrderMasterModel;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;

import javax.crypto.Cipher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReferenceActivity extends AppCompatActivity {

    ListView myreflistview;
    MyReferenceAdapter refAdapter;
    List<UsersModel> refuserslist;
    ArrayList<String> senddatatorefdetails;

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
        senddatatorefdetails = new ArrayList<String>();



        getrefUsresList(this);

        myreflistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                UsersViewModel user = refAdapter.userslist.get(position);
                senddatatorefdetails.clear();
                senddatatorefdetails.add(user.getFname());
                senddatatorefdetails.add(user.getLname());
                senddatatorefdetails.add(user.getRolename());
                senddatatorefdetails.add(user.getStatus());
                senddatatorefdetails.add(user.getAddress());
                senddatatorefdetails.add(user.getPhone1());
                senddatatorefdetails.add(user.getEmail());
                senddatatorefdetails.add(user.getCommPromotionDate());
                senddatatorefdetails.add(user.getCommLayer());



                Intent intent = new Intent(MyReferenceActivity.this,UserDetailActivity.class);
                intent.putExtra("user", senddatatorefdetails);
                startActivity(intent);


            }
        });


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
                    List<UsersViewModel> allrefuser = new ArrayList<UsersViewModel>();

            /*        for(int i = 0 ; i<refuserslist.size(); i++) {
                        Object getrow = refuserslist.get(i);
                        LinkedTreeMap<Object, Object> t = (LinkedTreeMap) getrow;

                        String userid = String.valueOf(t.get("username"));
                        String fname = String.valueOf(t.get("fname"));
                        String lname = String.valueOf(t.get("lname"));
                        String status = String.valueOf(t.get("status"));

                        UsersModel usrs = new UsersModel(userid, fname, lname, status);
                        allrefuser.add(usrs);
                    }*/

                    Gson gson = new Gson();
                    String json = gson.toJson(refuserslist);
                   /* JsonObject jsonObject = null;
                    jsonObject = new JsonParser().parse(json).getAsJsonObject();*/
                    Type typeMyType = new TypeToken<ArrayList<UsersViewModel>>(){}.getType();
                    allrefuser = gson.fromJson(json, typeMyType);

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


/*
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;*/

/*    public TrippleDes() throws Exception {
        myEncryptionKey = "ThisIsSpartaThisIsSparta";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }*/



/*
    public String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }*/
}