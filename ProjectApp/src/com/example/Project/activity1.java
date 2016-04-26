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

    private ToggleButton togBtn;
    private SeekBar fan;
    private Button showDataBtn;
    private Button btnMainMenu;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txtSp;
    private static TextView txtSpeed;
    private String SERVER_ADDRESS = "192.168.0.3";
    private FragmentTransaction fragt;
    private int SERVER_PORT = 80;
    public static String newTemp = "0";
    public static boolean ledState = false;
    public static String ls = "off";
    public static final int TIME_OUT = 5000;
    private fragmentViewData fvd;
    private fragmentViewGraph fvg;
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
                            dbh.enterData(ls,newTemp,getFanSpeed());
                            Log.d("Debug",ls+","+newTemp);
                            if (ls.equals("on"))
                                ledState = true;
                            else
                                ledState = false;
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
            txtSp = (TextView) findViewById(R.id.textViewSp);
            txtSpeed = (TextView) findViewById(R.id.textViewSpeed);
            txt2 = (TextView) findViewById(R.id.textView3);
            txt3 = (TextView) findViewById(R.id.textView4);
            txt4 = (TextView) findViewById(R.id.textView5);
            togBtn = (ToggleButton) findViewById(R.id.toggleButton);
            btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
            showDataBtn = (Button) findViewById(R.id.showDataBtn);
            fan = (SeekBar) findViewById(R.id.seekBar);
            fan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,boolean bl){
                    double speed = ((double)progress / 100.0);
                    txtSpeed.setText(String.valueOf(speed));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
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
        fragt = getFragmentManager().beginTransaction();
        fvd = fragmentViewData.newInstance();
        fragt.replace(R.id.ControlRoom,fvd);
        fragt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragt.addToBackStack(null);
        fragt.commit();
    }
    public void showGraph(View v)
    {
        fragt = getFragmentManager().beginTransaction();
        fvg = fragmentViewGraph.newInstance();
        fragt.replace(R.id.ControlRoom,fvg);
        fragt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragt.addToBackStack(null);
        fragt.commit();
    }
    public void changeLedState(View v)
    {
        if(ls.equals("on"))
            ls = "off";
        else
            ls ="on";
    }
    public static String getFanSpeed(){
        return txtSpeed.getText().toString();
    }
    public static String getState() {
        return ls;
    }
    public static void setData(String ls2,String t){
        if (ls2.equals("on"))
            ls = "on";
        else
            ls = "off";
        newTemp = t;

    }
}