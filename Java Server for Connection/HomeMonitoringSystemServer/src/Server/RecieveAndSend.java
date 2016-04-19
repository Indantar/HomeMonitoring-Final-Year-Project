package Server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class RecieveAndSend
{
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader br;
	private static int inputLine;
	private static char[] msg;
	public static void main(String[] args)
	{
		// Wait for client to connect on 63400
		try
		{
			serverSocket = new ServerSocket(80);
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());
			clientSocket = serverSocket.accept();
			while(clientSocket.isConnected() == false){
				System.out.println("Awaiting Connection");
			}
			System.out.println("Connected");		
			// Create a reader
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-16");
			// Get the client message
			while((inputLine = br.read()) != -1)
			{
				msg = Character.toChars(inputLine);
				System.out.print(msg);
			}
			String str = "Message was recieved!";
			osw.write(str,0,str.length());
			osw.flush();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}

