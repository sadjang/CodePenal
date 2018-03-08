package com.example.sadjang.codepenal.bdcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tantine1 on 10/27/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "codeverif.db";
    public static final String TABLE_NAME = "code_table";
    public static final String COL_2 = "CODE";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, CODE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean insertData(String code) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, code);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {

            return false;
        } else {
            return true;
        }
    }

    public Boolean getAllData(String code) {
        Cursor res;
        SQLiteDatabase db = this.getWritableDatabase();

        res = db.rawQuery("select * from " + TABLE_NAME, null);

        if (res.getCount() == 0) {
            return false;
        } else {
            return true;
        }


    }


}
