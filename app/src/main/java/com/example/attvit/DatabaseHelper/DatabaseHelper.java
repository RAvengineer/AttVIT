package com.example.attvit.DatabaseHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/*
 * main class SQLiteOpenHelper for handling SQLite
 * */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static   String DATABASE_NAME ="student.db";
    public String TABLE_NAME ;
    public String COL[] = new String[4];
    public SQLiteDatabase db;




    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null , 1);

    }
    /*
     * TO CREATE TABLE IN THE DATABASE
     *
     * */
    @Override
    public void onCreate(SQLiteDatabase pdb) {
        db = pdb;
        createDatabase(TABLE_NAME, COL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String table, String[] value) {

        //CREATE DATABASE AND TABLE
        TABLE_NAME = table ;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        COL = getColumnNames(TABLE_NAME);

        //3 args -> table name, null, contentValues
        // returns -1 if some problem otherwise row number !

        String s = " ( " + COL[0] + " TEXT PRIMARY KEY , ";

        int i;

        for( i = 1 ;i < COL.length -1 ; i++){

            s = s + "col" + COL[i] + " TEXT , ";

        }
        s = s + "col" + COL[i] + " TEXT ) ";
        db.execSQL("create table IF NOT EXISTS " + TABLE_NAME + s );


        String[] c = new String[COL.length];
        for (i = 0; i < COL.length; i++)
            c[i] = "A";

        //1st col name ,  2nd value
        Date currentTime = Calendar.getInstance().getTime();
        contentValues.put(COL[0], currentTime.toString());
        Log.d("value Length", "" + (value.length));
        for (i = 0; i < value.length; i++) {
            for (int j = 1; j < COL.length; j++) {
                if (COL[j].equalsIgnoreCase("col" + value[i])) {
                    c[j] = "P";
                    break;
                }
            }
            Log.d("Student", value[i]);
        }


        for (i = 1; i < COL.length; i++) {
            Log.d("Attendance", COL[i] + " " + c[i]);
            contentValues.put(COL[i], c[i]);
        }

        long result =  db.insert(TABLE_NAME , null , contentValues);

        return result != -1;
    }

    // <editor-fold default="collapsed" desc="create Database">
    public boolean createDatabase(String TABLE_NAME, String[] COL) {
        //IT TAKES QUERY WHATEVER WE PASS
        String s = " ( " + COL[0] + " TEXT PRIMARY KEY , ";

        int i;

        for (i = 1; i < COL.length - 1; i++) {

            s = s + "col" + COL[i] + " TEXT , ";

        }
        s = s + "col" + COL[i] + " TEXT ) ";
        db.execSQL("create table IF NOT EXISTS " + TABLE_NAME + s);
        return true;
        //db.execSQL("create table "+TABLE_NAME+" (ID TEXT PRIMARY KEY , NAME TEXT , SURNAME TEXT ,MARKS TEXT) ");
    }
    // </editor-fold>

    // <editor-fold default="collapsed" desc="get Column Names">

    public String[] getColumnNames(String table_name) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor dbCursor = db.query(table_name, null, null, null, null, null, null);
        return dbCursor.getColumnNames();
    }
    // </editor-fold>

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

