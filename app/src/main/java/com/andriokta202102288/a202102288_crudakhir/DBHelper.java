package com.andriokta202102288.a202102288_crudakhir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME="projectakhir.db";

    public DBHelper(Context context) { super(context,"projectakhir.db" , null, 1);}


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table konseling(nik TEXT primary key, nama TEXT, tgllahir TEXT, alamat TEXT, keluhan TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists konseling");
    }
    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result ==1) return false;
        else
            return true;
    }
    //check username function
    public Boolean checkusername(String username){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    //check username password function
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }




    public Boolean insertDataKonseling (String nik, String nama, String tgllahir, String alamat, String keluhan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nik",nik);
        values.put("nama",nama);
        values.put("tgllahir", tgllahir);
        values.put("alamat", alamat);
        values.put("keluhan", keluhan);
        long result = db.insert("konseling", null,values);
        if (result == 0) return false;
        else
            return true;
    }
    public Cursor tampilDataKonseling(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from konseling", null);
        return cursor;

    }

    public boolean hapusDataKonseling(String kode){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from konseling where nik=?", new String[]{kode});
        if (cursor.getCount()>0) {
            long result = db.delete("konseling", "nik=?", new String[]{kode});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public Boolean editDataKonseling (String nik, String nama, String tgllahir, String alamat, String keluhan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama",nama);
        values.put("tgllahir", tgllahir);
        values.put("alamat", alamat);
        values.put("keluhan", keluhan);
        Cursor cursor = db.rawQuery("Select * from konseling where nik=?", new String[]{nik});
        if (cursor.getCount()>0) {
            long result = db.update("konseling", values, "nik=?", new String[]{nik});
            if (result == 1) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    public Boolean checkkodenik(String nik) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from konseling where nik=?", new String[]{nik});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}