package com.example.Project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by g00284823 on 19/04/2016.
 */
public class DatabaseHelper  extends SQLiteOpenHelper
{
    public static final String tblName= "Login";

    private static final String dbName 					= "LoginDetails";
    private static final int    dbVersion 				= 11;


    private static final String tblID 				= "tblID";
    private static final String tblUserName			= "UserName";
    private static final String tblPass				= "Password";

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

    public int ReturnUserName(String user,String pass){
        StringBuilder sbCheck = new StringBuilder();
        sbCheck.append(user);
        sbCheck.append("-");
        sbCheck.append(pass);
        String correct = sbCheck.toString();
        Log.d("Debug Original Values",correct);
        String query = "SELECT * FROM Login;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor myCursor = db.rawQuery(query, null);
        if(myCursor.moveToFirst())
        {
            do
            {
                StringBuilder sb = new StringBuilder();
                sb.append(myCursor.getString(1));
                sb.append(" -");
                sb.append(myCursor.getString(2));
                String entered = sb.toString();
                Log.d("Debug Values",entered);
                if(correct.equals(entered)){
                    Log.d("Debug","Correct");
                    return 1;
                }
                Log.d("Debug","Wrong Entry");
            }while(myCursor.moveToNext());
        }
        return 0;
    }
}
