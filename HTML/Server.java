import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Server implements IPConstants
{
	private Vector<PrintWriter> bm = new Vector<PrintWriter>();
	
	private ArrayList<String> online = new ArrayList<String>();
		
	public static void main(String [] args) 
	{
		new Server();
	}
	
	public Server()
	{
		ServerSocket ss = null;

		try 
		{
			System.out.println("getLocalHost: " + InetAddress.getLocalHost());
			System.out.println("getByName:    " + InetAddress.getByName("localhost"));
			System.out.println("\nChat Server is now running on port " + CHAT_PORT_NUMBER + "\n");

			ss = new ServerSocket(CHAT_PORT_NUMBER);
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
		
		public void addList(String nickname)
		{
			online.add(nickname);
		}
		
		
		
		public void run() 
		{
			BufferedReader ibr;
			PrintWriter opw;
			String clientMsg;
			
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
				    System.out.println("Client connected.");
					clientMsg = "You are connected from " + InetAddress.getLocalHost();
					opw.println(clientMsg);
					opw.flush();
					
					
					clientMsg = ibr.readLine();
					addList(clientMsg);
					
					for(int a = 0; a < online.size(); a++)
					{
						opw.println(online.get(a));
						opw.flush();
					}
					
					opw.flush();
					
					
					
					while(true)
					{
					
						clientMsg = ibr.readLine();

						// loop  through vector, get a pw from each time through loop
						// write to that pw, then go get next from the vector and write to that client
						for(PrintWriter pw: bm)
						{
							System.out.println(clientMsg);
					//		pw.println("User: " + clientMsg);
							pw.println(clientMsg);
							pw.flush();
						}						
					}
				}
				
				
			}
			catch(SocketException se)
			{
				System.out.println("Connection Status: Either A client disconnected or lost connection");
			}
			catch( IOException e ) 
			{
				System.out.println("Server Error: Unknown"); 
				e.printStackTrace();
			}
			
			
		} // end while
		
	 } // end class ThreadServer

} // end ServerChatRoom class