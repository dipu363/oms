package com.aait.oms.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import com.aait.oms.users.UserRequest;

public class SQLiteDB extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sqlite_oms.db";

    //  table name
    private static final String TABLE_USER_INFO= "userinfo";


    //  User info Table Columns names

    private static final String USER_ID = "Id";
    private static final String USER_NAME = "userName";
    private static final String OTP_UID = "otpUID";
    private static final String USER_PASSWORD = "userPassword";
    private static final String LOGIN_STATUS = "login_status";




    public static final String CREATE_USER_INFO_TABLE = "CREATE TABLE " + TABLE_USER_INFO + "("
            + USER_ID + " INTEGER  NOT NULL," + USER_NAME + " TEXT ," + OTP_UID + " TEXT ," + USER_PASSWORD + " TEXT ," + LOGIN_STATUS + " BOOLEAN " +")";

    public SQLiteDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_INFO_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_USER_INFO);
        // Create tables again
        onCreate(db);


    }




    // insert data on the user info table
    public void insertUserinfo(UserRequest userRequest) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID,1);
        values.put(USER_NAME,userRequest.getUserName());
        //values.put(OTP_UID,userRequest.getOtpUID());
        values.put(USER_PASSWORD,userRequest.getMobiPassword());
        values.put(LOGIN_STATUS,userRequest.isLogin_status());
        db.insert(TABLE_USER_INFO,null,values);
        db.close();
    }


    public Cursor getUserInfo(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userinfo",null);
        return cursor ;
    }

    public  void deleteuserinfo(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_INFO,USER_ID + "=?",new String[]{String.valueOf(userid)});
        db.close();
    }


    public void updateuserotp( String otp,int id ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(OTP_UID,otp);
        db.update(TABLE_USER_INFO,value,USER_ID +" = ?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void updateuserloginstatus( boolean loginstatus ,int id ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(LOGIN_STATUS,loginstatus);
        db.update(TABLE_USER_INFO,value,USER_ID +" = ?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void updateuserunamepassstatus( String uname ,String pass, boolean loginstatus ,int id ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(USER_NAME,uname);
        value.put(USER_PASSWORD,pass);
        value.put(LOGIN_STATUS,loginstatus);
        db.update(TABLE_USER_INFO,value,USER_ID +" = ?",new String[]{String.valueOf(id)});
        db.close();
    }
}