package com.example.Project;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;

/**
 * Created by g00284823 on 22/10/2015.
 */
public class activity1 extends Activity {

    ToggleButton togBtn;
    Button btnMainMenu;
    Button updateButton;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    String SERVER_ADDRESS = "192.168.0.4";
    int SERVER_PORT = 80;
    public String newTemp = "0";
    ConnectClient cc;

    Thread t = new Thread()
    {
        @Override
        public void run()
        {
            try
            {
                while (!isInterrupted())
                {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            txt4.setText(newTemp);
                        }
                    });
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    };
    Thread upTemp = new Thread(){
        @Override
        public void run()
        {
            try
            {
                while(!isInterrupted())
                {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            newTemp = cc.getMessage();
                        }
                    });
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            setContentView(R.layout.activity1);
            txt2 = (TextView) findViewById(R.id.textView3);
            txt3 = (TextView) findViewById(R.id.textView4);
            txt4 = (TextView) findViewById(R.id.textView5);
            togBtn = (ToggleButton) findViewById(R.id.toggleButton);
            btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
            updateButton = (Button) findViewById(R.id.updateButton);
            t.start();
        }
        catch(Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void goToMain(View v)
    {
        finish();
    }
    public void update(View v){
        cc = new ConnectClient("Updating",SERVER_ADDRESS,SERVER_PORT,getApplicationContext());
        cc.execute();
        upTemp.start();
    }
}