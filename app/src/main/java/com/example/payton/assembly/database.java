package com.example.payton.assembly;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;


public class database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "people.db";
    public static final String TABLE_NAME = "people_table";
    public static final String COL1 = "USERNAME";
    public static final String COL2 = "PASSWORD";

    public database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT UNIQUE, PASSWORD TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, username);
        contentValues.put(COL2, password);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1) {
            return false;
        }else{
            return true;
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM people_table WHERE USERNAME = "+username+"", null);
        if (c.getCount()>0) {
            return false;
        }else {
            return true;
        }
    }

    public boolean checkPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM people_table WHERE USERNAME = "+username+" AND PASSWORD = "+password+"", null);
        if (c.getCount()==0){
            return false;
        }
        return true;

        /*SQLiteDatabase db = this.getWritableDatabase();
        IF EXISTS db.rawQuery("SELECT * FROM people_table WHERE USERNAME = "+username+" AND PASSWORD = "+password+"", null);
        IF EXISTS (SELECT * FROM people_table WHERE WHERE USERNAME = "+username+" AND PASSWORD = "+password+")
        BEGIN
                return false;
                END
        ELSE
                BEGIN
return true;
                END


        //Cursor c = db.rawQuery("SELECT * FROM people_table WHERE USERNAME = "+username+" AND PASSWORD = "+password+"", null);
        if (c.getCount()>0) {
            return false;
        }else {
            return true;
        }*/
    }


}