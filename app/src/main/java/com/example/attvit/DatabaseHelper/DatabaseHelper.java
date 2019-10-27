package com.example.attvit.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Date;

/*
 * main class SQLiteOpenHelper for handling SQLite
 * */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static   String DATABASE_NAME ="student.db";
    public String TABLE_NAME ;
    public String COL[] = new String[4];


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null , 1);

    }
    /*
     * TO CREATE TABLE IN THE DATABASE
     *
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //IT TAKES QUERY WHATEVER WE PASS
        String s = " ( " + COL[0] + " TEXT PRIMARY KEY , ";

        int i;

        for( i = 1 ;i < COL.length -1 ; i++){

            s = s + COL[i] + " TEXT , ";

        }
        s = s + COL[i] + " TEXT ) ";
        db.execSQL("create table IF NOT EXISTS " + TABLE_NAME + s );
        //db.execSQL("create table "+TABLE_NAME+" (ID TEXT PRIMARY KEY , NAME TEXT , SURNAME TEXT ,MARKS TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }



    public boolean insertData(String table , String value[]){

        //CREATE DATABASE AND TABLE
        TABLE_NAME = table ;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //1st col name ,  2nd value
        Date currentTime = Calendar.getInstance().getTime();
        contentValues.put(COL[0], currentTime.toString());
        for(int i = 1 ; i < value.length  ; i++) {
            contentValues.put(COL[i],value[i]);
        }


        //3 args -> table name, null, contentValues
        // returns -1 if some problem otherwise row number !

        String s = " ( " + COL[0] + " TEXT PRIMARY KEY , ";

        int i;

        for( i = 1 ;i < COL.length -1 ; i++){

            s = s + COL[i] + " TEXT , ";

        }
        s = s + COL[i] + " TEXT ) ";
        db.execSQL("create table IF NOT EXISTS " + TABLE_NAME + s );

        long result =  db.insert(TABLE_NAME , null , contentValues);

        if(result == -1)
            return  false;
        else
            return  true;

    }

    //  Cursor is an interface that provides random read-write access to the result
    //  set returned by a DATABASE query !

    /*
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public  boolean updateData(String id , String name ,String surname , String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME,contentValues,"id = ?",new String[] { id });
        return  true;
    }*/

}

