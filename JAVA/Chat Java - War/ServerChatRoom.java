/*

	Name:		Warrance Yu
	Prof:		M. Floeser
	Class:	Java 3 - Information Technology III
	Info:		Lab 4 - Chat
			
			More info: 	Team with Randall Dix for this lab 4
			
*/


import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class ServerChatRoom
{
	private Vector<PrintWriter> bm = new Vector<PrintWriter>();
	
	private ArrayList<String> online = new ArrayList<String>();
		
	public static void main(String [] args) 
	{
		new ServerChatRoom();
	}
	
	public ServerChatRoom()
	{
		ServerSocket ss = null;

		try 
		{
			System.out.println("getLocalHost: " + InetAddress.getLocalHost());
			System.out.println("getByName:    " + InetAddress.getByName("localhost"));
			System.out.println("\nServer is now running...\n");

			ss = new ServerSocket(2222);
			Socket cs = null;
			
			while(true)
			{ 		
			  cs = ss.accept(); 				
			  ServerRun ths = new ServerRun(cs);
			  			  
			  ths.start();
			} // end while
		}
		catch( BindException be ) 
		{
			System.out.println("Server already running on this computer, stopping.");
		}
		catch( IOException ioe ) 
		{
			System.out.println("IO Error");
			ioe.printStackTrace();
		}

	} // end main
	
	class ServerRun extends Thread 
	{
		Socket connected;

		public ServerRun(Socket cs) 
		{
			connected = cs;
		}
		
		public void run() 
		{
			BufferedReader ibr;
			PrintWriter opw;
			String clientMsg;
			
		//	count++;
		//	online.add("Client " + count);
			
			try 
			{
				ibr	=	new BufferedReader(
							new InputStreamReader( 
							connected.getInputStream()));
								
				opw	=	new PrintWriter(
							new OutputStreamWriter(
							connected.getOutputStream()));
				
				
				// add opw to vector
				bm.add(opw);
				
				
				if(connected.isConnected())
				{
					clientMsg = "You are connected from " + InetAddress.getLocalHost();
					opw.println(clientMsg);
					opw.flush();
					
					
					for(int a = 0; a < online.size(); a++)
					{
						System.out.println(online.get(a));
					}
					
					while(true)
					{
					
						clientMsg = ibr.readLine();

						// loop  through vector, get a pw from each time through loop
						// write to that pw, then go get next from the vector and write to that client
						for(PrintWriter pw: bm)
						{
							pw.println(clientMsg);
							pw.flush();
						}
					}
				}
			}
			catch(SocketException se)
			{
				System.out.println("Connection Error: Lost connection");
			}
			catch( IOException e ) 
			{
				System.out.println("Server Error: Unknown"); 
				e.printStackTrace();
			}
			
			
		} // end while
		
	 } // end class ThreadServer

} // end ServerChatRoom class