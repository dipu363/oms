package com.aait.oms.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;
import com.aait.oms.apiconfig.ApiClient;
import com.aait.oms.model.BaseResponse;
import com.aait.oms.users.UserModel;
import com.aait.oms.users.UserRequest;
import com.aait.oms.users.UserService;
import com.aait.oms.users.UsersModel;
import com.aait.oms.util.SQLiteDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    //userimage pickup
    static int PReqCode = 1;
    static int REQUESCODE = 1;
    Uri pickedImgUri;
    DatabaseReference db_users;
    StorageReference mStorage;
    private ImageView userImage;
    //image


    private EditText fname, lname,uname,upassword,reference;
    private Button btncountinu;
    private TextView doclogintaxt;
    private ProgressBar loadingProgress;
    List<UserRequest> userslist;
    Spinner spinner;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String existingUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Registration");
        loadingProgress = findViewById(R.id.regProgressBar);
        fname= findViewById(R.id.edit_firstname);
        lname = findViewById(R.id.edit_lastname);
        uname = findViewById(R.id.edit_username);
        reference=findViewById(R.id.reference_id);
        upassword = findViewById(R.id.edt_password);
        userImage = findViewById(R.id.regUserPhoto);
        btncountinu = findViewById(R.id.btnSignUpContinue);
        doclogintaxt = findViewById(R.id.docLogin);
        btncountinu.setOnClickListener(this);
        doclogintaxt.setOnClickListener(this);
        userImage.setOnClickListener(this);
        loadingProgress.setVisibility(View.INVISIBLE);



        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersInfo");
        //for spinner
        spinner = (Spinner) findViewById(R.id.spinner_gender);
        String[] gen = {"Select Gender","Male","Female","Others"};
        ArrayAdapter<CharSequence> genAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, gen );
        genAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(genAdapter);

        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String un = uname.getText().toString().trim();
                if(!un.equals("")){
                    chackusername(un);
                }else{

                    uname.setError("Mobile Number all ready Used or field is Empty.");

                   // Toast.makeText(SignUpActivity.this, "Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reference.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String ref = reference.getText().toString().trim();
               if(!ref.equals("")){
                   chackReference(ref);
               }
               else{

                   reference.setError("Customer ID Not Available, Please Enter a Valid Customer ID.");

               }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
  /*      currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            sendToMain();

        }*/

      /*  SQLiteDB sqLiteDB = new SQLiteDB(this);
        Cursor cursor =  sqLiteDB.getUserInfo();

        if (cursor != null && cursor.moveToFirst()){
            Toast.makeText(this, "Please Logging Out first", Toast.LENGTH_SHORT).show();
            sendToMain();

        }*/


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnSignUpContinue:
                netWorkCheck();
                break;
            case R.id.docLogin:
                Intent intent =new Intent(SignUpActivity.this,LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.regUserPhoto:
                if (Build.VERSION.SDK_INT >= 22) {

                    checkAndRequestForPermission();


                } else {
                    openGallery();
                }





        }

    }

    private void sendToMain(){
        startActivity(new Intent(SignUpActivity.this , HomeActivity.class));
        finish();
    }


    // check mobile net work status and then call checkvalidity method ;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void netWorkCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){

           checkValidity();
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(" NO NetWork")
                    .setMessage("Enable Mobile Network")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void checkValidity(){
        final  String firstname = fname.getText().toString().trim();
        final String lastname = lname.getText().toString().trim();
        final String username = uname.getText().toString().trim();
        final String gender= spinner.getSelectedItem().toString();
        final String ref = reference.getText().toString().trim();
        final String password = upassword.getText().toString().trim();
        int passlenth = password.length();
        //getuserList(username);
        //checking the validity of the email
        if (TextUtils.isEmpty(firstname)) {
            fname.setError("Enter your first name");
            fname.requestFocus();
        } else if (TextUtils.isEmpty(lastname)) {
            lname.setError("Enter your last name");
            lname.requestFocus();

        }else if (TextUtils.isEmpty(username)){
            uname.setError("Enter You Phone Number");
        }
        else if (TextUtils.isEmpty(ref)){
            reference.setError("Enter Reference");
        }
        //checking the validity of the password
        else if (TextUtils.isEmpty(password) ) {
            upassword.setError("Enter your password");
            upassword.requestFocus();
        }
        else if (passlenth < 6 ) {
            upassword.setError("Password Entered At Least 6 Characters ");
            upassword.requestFocus();
        }
        else {


            btncountinu.setVisibility(View.INVISIBLE);
            loadingProgress.setVisibility(View.VISIBLE);
            createUserAccount(username,firstname,lastname,gender,password,ref);
           // saveandupdateUserInfo(firstname,lastname,username,gender,password,ref,pickedImgUri);

            btncountinu.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);


        }


    }


    //create user accountmathod on server

    private void createUserAccount(final String uname,final String fname,final String lname, final String gender,final String password, final String ref) {

        @SuppressLint("SimpleDateFormat") DateFormat formeter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String regdate= formeter.format(date);

        UserModel userModel = new UserModel(uname,fname,lname,102,password,4,ref,gender);
        userModel.setSsCreator(uname);
        userModel.setSsModifier(uname);
        userModel.setPhone1(uname);

    /*  Gson gson = new Gson();
        String json = gson.toJson(userModel);
        JsonObject jsonObject = null;
        jsonObject = new JsonParser().parse(json).getAsJsonObject();
        UserService userService = ApiClient.getRetrofit().create(UserService.class);*/

        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        UserService userService = ApiClient.getRetrofit().create(UserService.class);
        Call<String> call = userService.userSave(jsonObject);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();

                SQLiteDB sqLiteDBHelper = new SQLiteDB(getApplicationContext());
                UserRequest request = new UserRequest();
                request.setUserName(uname);
                request.setMobiPassword(password);
                request.setLogin_status(false);
                sqLiteDBHelper.insertUserinfo(request);

                Toast.makeText(SignUpActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();
            }
        });


    }









    //clear text field
    public void clear(){
        fname.setText("");
        lname.setText("");
        uname.setText("");
        upassword.setText("");
        reference.setText("");
    }

    private  void chackusername(String userid){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> call = service.getuser(userid);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()){
                    BaseResponse baseResponse = response.body();
                    String massage = baseResponse.getMessage();
                    if(baseResponse.getObj()!= null){

                        uname.setText("");
                        uname.requestFocus();
                        //Toast.makeText(SignUpActivity.this, "User Name All ready Used Please Choose Another", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SignUpActivity.this, "User Name has been available", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Failure massage",t.getMessage());

            }
        });
    }
    private  void chackReference(String userid){
        UserService service = ApiClient.getRetrofit().create(UserService.class);
        Call<BaseResponse> call = service.getuser(userid);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()){
                    BaseResponse baseResponse = response.body();
                    //String massage = baseResponse.getMessage();
                    if(baseResponse.getObj()!=null){

                        Toast.makeText(SignUpActivity.this, "Thank you for your reference", Toast.LENGTH_LONG).show();
                    }else{
                        reference.setText("");
                        reference.requestFocus();
                        //Toast.makeText(SignUpActivity.this, "Customer ID not available", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("Failure massage",t.getMessage());

            }
        });
    }


    // update user photo and name
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void saveandupdateUserInfo(final String fname,final String lname,final String uname, final String gender,final String password, final String ref,final Uri pickedImgUri) {
    /*    final String username = uname.getText().toString().trim();
        final String email = uemail.getText().toString().trim();
        final String phone = uphone.getText().toString().trim();
        final String password = upassword.getText().toString().trim();*/
        // first we need to upload user photo to firebase storage and get url


        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        if (pickedImgUri != null) {
            //get user image from firestore
            final StorageReference imageFilePath = mStorage.child(Objects.requireNonNull(pickedImgUri.getLastPathSegment()));


            imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // image uploaded succesfully
                    // now we can get our image url
                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // uri contain user image url
                            //save data to database with image id
                            String imageUrl = String.valueOf(uri);
                            //String userid = currentUser.getUid();


                            UsersModel userModel = new UsersModel(uname,fname,lname,1,password,gender,ref,imageUrl);
                            databaseReference.child(uname).setValue(userModel);
                     /*       //user profile build with user image
                            UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .setPhotoUri(uri)
                                    .build();
                            //update profile with user image
                            currentUser.updateProfile(profleUpdate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                // user info updated successfully
                                                //and go email verification activity
                                                showMessage();
                                                chackEmailVerify();
                                            }

                                        }
                                    });*/


                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //save data without user image
            String imageUrl = "default";
           // String userid = currentUser.getUid();
            UsersModel userModel = new UsersModel(uname,fname,lname,1,password,gender,ref,imageUrl);
            databaseReference.child(uname).setValue(userModel);

        /*    //build profile without user image
            UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            //update user profile without user image

            currentUser.updateProfile(profleUpdate)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                // user info updated successfully
                                showMessage();
                                chackEmailVerify();


                            }

                        }
                    });*/

        }

    }



    private void openGallery() {

        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESCODE);
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(SignUpActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        } else
            openGallery();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData();
            userImage.setImageURI(pickedImgUri);


        }


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}