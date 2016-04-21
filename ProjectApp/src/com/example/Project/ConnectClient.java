package com.example.Project;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import android.net.wifi.WifiManager.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;


public class ConnectClient extends AsyncTask<String, Void, String> {

    private Socket socket;
    private PrintWriter printWriter;

    private String param;
    private Context context;
    private Integer PORT;
    private String IP;

    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static String message;

    public ConnectClient(String par, String ip, Integer prt, Context ctx){
        super();
        this.context = ctx;
        this.param = par;
        this.PORT = prt;
        this.IP = ip;
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(context, "start " + param, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            InetAddress inetAddress = InetAddress.getByName(IP);
            SocketAddress socketAddress  = new InetSocketAddress(inetAddress,PORT);
            Log.d("Debug Original Values","Connecting");
            socket = new Socket();  //connect to server
            Log.d("Debug Original Values","Connecting");
            socket.connect(socketAddress);
            Log.d("Debug Original Values","Getting output stream");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.write("Send me data\n");
            printWriter.flush();
            while(true)
            {
                message = bufferedReader.readLine();
                if(!message.equals("stop")){
                    printWriter.write("Message Recieved\n");
                    printWriter.flush();
                    Log.d("Debug","Message Recieved" + message);
                }
                else
                {
                    Log.d("Debug","Exiting");
                    printWriter.write("Closing\n");
                    socket.close();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        Toast.makeText(context, "In progress", Toast.LENGTH_SHORT).show();
    }

    public String getMessage(){
        return message;
    }
}
