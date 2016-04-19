package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class server implements Runnable
{
	static Socket sc;
	server(Socket sc){
		this.sc = sc;
	}
	
	public static void main(String[] args) throws Exception
	{
		ServerSocket ss = new ServerSocket(80);
		InetAddress ip;
		ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());
		System.out.println("Listening");
		sc = ss.accept();
		while(sc.isConnected() == false){
			System.out.println("Awaiting Connection");
		}
		System.out.println("Connected");
		new Thread(new server(sc)).start();
	}

	@Override
	public void run() 
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			PrintWriter out = new PrintWriter(sc.getOutputStream());
			String str  = br.readLine();
			System.out.println("Message Recieved: "+ str);
			out.println("Message was Recieved");
//			DataOutputStream dOut = new DataOutputStream(sc.getOutputStream());
//			dOut.writeByte(1);
//			dOut.writeUTF("Message has been recieved!");
//			dOut.flush();
//			BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
//			PrintWriter out = new PrintWriter(sc.getOutputStream());
//			String input = in.readLine();
//			System.out.println("Message Recieved: " + input);
//			String output = "Message Recieved";
//			out.println(output);
//			out.close();
//			in.close();
//			sc.close();
		}
		catch( IOException ee){
			System.out.println(ee);
		}
	}
}
