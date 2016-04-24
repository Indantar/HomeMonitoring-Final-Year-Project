package com.example.Project;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by g00284823 on 22/10/2015.
 */
public class activity1 extends Activity {

    ToggleButton togBtn;
    Button btnMainMenu;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    String SERVER_ADDRESS = "192.168.0.3";
    int SERVER_PORT = 80;
    public String newTemp = "0";
    public static boolean ledState = false;
    public String ls = "off";
    public String[] data;
    public static final int TIME_OUT = 5000;
    ConnectClient cc;
    DatabaseHelper dbh = new DatabaseHelper(this);

    Thread upTemp = new Thread()
    {
        @Override
        public void run()
        {
            try
            {
                while(!isInterrupted())
                {
                    Thread.sleep(TIME_OUT);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(cc.getMessage() != null) {
                                data = cc.getMessage().split(":");
                                newTemp = data[0];
                                ls = data[1];
                                if (ls.equals("on"))
                                    ledState = true;
                                else
                                    ledState = false;
                            }
                            dbh.enterData(ls,newTemp);
                            Log.d("Debug",ls+","+newTemp);
                            togBtn.setChecked(ledState);
                            txt4.setText(newTemp);
                        }
                    });
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            setContentView(R.layout.activity1);
            txt2 = (TextView) findViewById(R.id.textView3);
            txt3 = (TextView) findViewById(R.id.textView4);
            txt4 = (TextView) findViewById(R.id.textView5);
            togBtn = (ToggleButton) findViewById(R.id.toggleButton);
            btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
            cc = new ConnectClient("Updating",SERVER_ADDRESS,SERVER_PORT,getApplicationContext());
            cc.execute();
            upTemp.start();
        }
        catch(Exception e)
        {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void goToMain(View v)
    {
        cc.stop();
        finish();
    }
    public void showData(View v)
    {
        PopupWindow popWindow = PopUp();

        popWindow.showAtLocation(,Gravity.CENTER,200,200);
        Log.d("Debug","Window"+popWindow.isShowing());
    }
    public void changeLedState(View v)
    {
        ledState = !ledState;
    }
    public static boolean getState(){
        return ledState;
    }
    public PopupWindow PopUp(){
        PopupWindow PopupWindow = new PopupWindow(this);
        ArrayList<String> data = dbh.returnData();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,data);
        ListView listViewData = new ListView(this);
        listViewData.setAdapter(dataAdapter);
        PopupWindow.setFocusable(true);
        PopupWindow.setWidth(250);
        PopupWindow.setContentView(listViewData);
        return PopupWindow;
    }
}