package Server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class MultiThreadRecieveAndSend implements Runnable
{
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader br;
	private static char[] msg;
	
	MultiThreadRecieveAndSend(Socket sSocket){
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
				new Thread(new MultiThreadRecieveAndSend(sock)).start();
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	public void run() 
	{
		try
		{
			System.out.println("Accepted Client : Address - "+ clientSocket.getInetAddress().getHostName());
			// Create a reader
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//			OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8");
			while(true)
			{
				// Get the client message
				while((msg = br.readLine().toCharArray()) != null)
				{
					System.out.println(msg);
//					osw.write(msg.toString(),0,msg.toString().length());
//					osw.flush();
				}
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}	
	}
}


