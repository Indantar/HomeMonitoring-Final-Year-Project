package Server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Random;

public class serverToPhone implements Runnable
{
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader br;
	private static OutputStreamWriter osw;
	private static String msg;
	private static String msgSend;
	
	serverToPhone(Socket sSocket){
		clientSocket = sSocket;
	}
	public static void main(String[] args) throws IOException
	{
		// Wait for client to connect on 80
		serverSocket = new ServerSocket(80);
		try
		{
			while(true)
			{
				InetAddress ip;
				ip = InetAddress.getLocalHost();
				System.out.println("Waiting for connection on: " + ip.getHostAddress());
				Socket sock = serverSocket.accept();
				new Thread(new serverToPhone(sock)).start();
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	public void run() 
	{
		Random rand = new Random();
		try
		{
			System.out.println("Accepted Client : Address - "+ clientSocket.getInetAddress().getHostName());
			// Create a reader
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			osw = new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8");
			while(true)
			{
				// Get the client message
				msg = br.readLine();
				if(!msg.equals("Closing"))
				{
					//msgSend = String.valueOf(rand.nextInt(40)) + "\n";
					msgSend = msg;
					System.out.println("Sending Message: " + msgSend);
					osw.write(msgSend,0,msgSend.length());
					osw.flush();
				}
				else{
					clientSocket.close();
				}
				Thread.sleep(1000);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}
}