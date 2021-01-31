package com.aait.oms.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aait.oms.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    private EditText fname, lname,uname,upassword,reference;
    private Button btncountinu;
    private TextView doclogintaxt;
    private ProgressBar loadingProgress;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("REGISTRATION");
        loadingProgress = findViewById(R.id.regProgressBar);
        fname= findViewById(R.id.edit_firstname);
        lname = findViewById(R.id.edit_lastname);
        uname = findViewById(R.id.edit_username);
        reference=findViewById(R.id.reference_id);
        upassword = findViewById(R.id.edt_password);
        btncountinu = findViewById(R.id.btnSignUpContinue);
        doclogintaxt = findViewById(R.id.docLogin);
        btncountinu.setOnClickListener(this);
        doclogintaxt.setOnClickListener(this);
        loadingProgress.setVisibility(View.INVISIBLE);
        //for spinner
        spinner = (Spinner) findViewById(R.id.spinner_gender);
        String[] gen = {"Male","Female","Others"};
        ArrayAdapter<CharSequence> genAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_text, gen );
        genAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setAdapter(genAdapter);

    }


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
        }

    }


    // check mobile net work status and then call checkvalidity method ;
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


    private void checkValidity(){
        final  String firstname = fname.getText().toString().trim();
        final String lastemail = lname.getText().toString().trim();
        final String username = uname.getText().toString().trim();
        final String gender= spinner.getSelectedItem().toString();
        final String ref = reference.getText().toString().trim();
        final String password = upassword.getText().toString().trim();
        int passlenth = password.length();
        //checking the validity of the email
        if (TextUtils.isEmpty(firstname)) {
            fname.setError("Enter your first name");
            fname.requestFocus();
        } else if (TextUtils.isEmpty(lastemail)) {

            lname.setError("Enter your last name");
            lname.requestFocus();

        }

        //checking the validity of the password
        else if (TextUtils.isEmpty(username)) {
            uname.setError("Enter your User Name");
            uname.requestFocus();
        } else if (TextUtils.isEmpty(password) ) {
            upassword.setError("Enter your password");
            upassword.requestFocus();
        }
        else if (passlenth < 4 ) {
            upassword.setError("Password Entered At Least 4 Characters ");
            upassword.requestFocus();
        }
        else {
            btncountinu.setVisibility(View.INVISIBLE);
            loadingProgress.setVisibility(View.VISIBLE);
            CreateUserAccount(firstname,lastemail,username,gender,password,ref);
            clear();


        }


    }


    //create user accountmathod

    private void CreateUserAccount(final String fname,final String lname,final String uname, final String gender,String password,String reference) {
        Toast.makeText(this,"Congratulation ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,SupplierListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        clear();

    }

    //clear text field
    public void clear(){
        fname.setText("");
        lname.setText("");
        uname.setText("");
        upassword.setText("");
        reference.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}