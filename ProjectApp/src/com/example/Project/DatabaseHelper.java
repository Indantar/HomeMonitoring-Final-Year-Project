package com.example.Project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by g00284823 on 19/04/2016.
 */
public class DatabaseHelper  extends SQLiteOpenHelper
{
    public static final String tblName= "Login";
    public static final String tblNameData = "Data";

    private static final String dbName 					= "LoginDetails";
    private static final int    dbVersion 				= 13;

    private static final String tblID 				= "tblID";
    private static final String tblID2 				= "tblID2";
    private static final String tblUserName			= "UserName";
    private static final String tblPass				= "Password";
    private static final String tblTemp             = "Temperature";
    private static final String tblLed             = "Led";
    private static final String tblTime             = "Time";


    private static DatabaseHelper inst;

    public DatabaseHelper(Context context){
        super(context,dbName,null,dbVersion);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (inst == null) {
            inst = new DatabaseHelper(context.getApplicationContext());
        }
        return inst;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + tblName + "(" + tblID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                tblUserName + " TEXT," + tblPass + " TEXT" + ")";

        String insertValues = "INSERT INTO " + tblName + "(" + tblUserName +","+ tblPass +  ") VALUES ('dawidkotwicki','123');";
        db.execSQL(createTable);
        db.execSQL(insertValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int newT, int old) {
        if (old != newT) {
            db.execSQL("DROP TABLE IF EXISTS " + tblName + ";");
            onCreate(db);
        }
    }

    public int ReturnUserName(String user,String pass)
    {
        StringBuilder sbCheck = new StringBuilder();
        sbCheck.append(user);
        sbCheck.append("-");
        sbCheck.append(pass);
        String correct = sbCheck.toString();
        Log.d("Debug Original Values",correct);
        String query = "SELECT * FROM Login;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(query, null);
        if(cur.moveToFirst())
        {
            do
            {
                StringBuilder sb = new StringBuilder();
                sb.append(cur.getString(1));
                sb.append(" -");
                sb.append(cur.getString(2));
                String entered = sb.toString();
                Log.d("Debug Values",entered);
                if(correct.equals(entered)){
                    Log.d("Debug","Correct");
                    return 1;
                }
                Log.d("Debug","Wrong Entry");
            }while(cur.moveToNext());
        }
        return 0;
    }
    public void enterData(String ls,String temp){
        SQLiteDatabase db = this.getWritableDatabase();
        String createTable = "CREATE TABLE IF NOT EXISTS " + tblNameData + "(" + tblID2 + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                tblTemp + " TEXT," + tblLed + " TEXT," + tblTime + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(createTable);
//        String insertValues = "INSERT INTO " + tblNameData + "(" + tblTemp + "," + tblLed + "," + tblTime +") VALUES ("+ temp + ", " + ls + ", DATETIME('now'));";
//        db.execSQL(insertValues);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues putDate = new ContentValues();
        putDate.put(tblTemp, temp);
        putDate.put(tblLed, ls);
        putDate.put(tblTime, sdf.format(date));
        db.insert(tblNameData,null,putDate);
    }

    public ArrayList<String> returnData()
    {
        ArrayList<String> returnData = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Data;";
        Cursor cur = db.rawQuery(query, null);
        if(cur.moveToFirst())
        {
            do
            {
                StringBuilder sb = new StringBuilder();
                sb.append(cur.getString(0) + "\t");
                sb.append(cur.getString(1) + "\t");
                sb.append(cur.getString(2) + "\t");
                sb.append(cur.getString(3) + "\t");
                returnData.add(sb.toString());
            }
            while(cur.moveToNext());
        }
        return returnData;
    }
}
