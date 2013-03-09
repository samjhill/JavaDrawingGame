import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Server
{
	private Vector<PrintWriter> bm = new Vector<PrintWriter>();
	
	private ArrayList<String> online = new ArrayList<String>();
	
	private ObjectOutputStream sendMessage;
	private ObjectInputStream getMessage;
	
		
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
	
	
	class ServerUpdater extends Thread
	{
		private Socket serverUpdater;
		private ObjectOutputStream oos;
		public ServerUpdater(Socket s, ObjectOutputStream oos)
		{
			serverUpdater = s;
			this.oos = oos;
		}
		
		
		public void run()
		{
			
			try
			{
			
				
				while(true)
				{	
					ServerMessage sm = new ServerMessage();
					
					sm.online = online;
					oos.writeObject(sm); // send a list of who's online
					oos.flush();
				
					Thread.sleep(300000); // Update every 5 minutes
				}
			}
			catch(InterruptedException ie)
			{
				System.out.println(ie.getMessage());
			}
			catch(UnknownHostException uhe)
			{
				System.out.println(uhe.getMessage());
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		}
	
	} // end ServerUpdater
	
	class ServerRun extends Thread 
	{
		Socket connected;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		

		public ServerRun(Socket cs) 
		{
			try
			{	
				connected = cs;
				sendMessage = new ObjectOutputStream(cs.getOutputStream());
				getMessage = new ObjectInputStream(cs.getInputStream());
			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
			
			ServerUpdater su = new ServerUpdater(cs,oos);
			su.start();
		}
		
		public void addList(String nickname)
		{
			online.add(nickname);
		}
		
		public void run() 
		{
			try 
			{
							
				ClientMessage cm = (ClientMessage) getMessage.readObject();
				String clientMsg;
				// add opw to vector
		//		bm.add(opw);
				
				
				if(connected.isConnected())
				{
					
					ServerMessage sm = new ServerMessage();
					clientMsg = "You are connected from " + InetAddress.getLocalHost();
					sm.message = clientMsg;
					
					oos.writeObject(sm);
					oos.flush();

					
					

	//				clientMsg = ibr.readLine();
	//				addList(clientMsg);

					while(true)
					{
						
						
			//			clientMsg = ibr.readLine();

						// loop  through vector, get a pw from each time through loop
						// write to that pw, then go get next from the vector and write to that client
						for(PrintWriter pw: bm)
						{
							System.out.println(clientMsg);
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
			catch( IOException ioe ) 
			{
				System.out.println("Server Error: Unknown"); 
				ioe.printStackTrace();
			}
			catch(ClassNotFoundException cnfe)
			{
				System.out.println(cnfe.getMessage());
			}
			
			
		} // end while
		
	 } // end class ThreadServer

} // end ServerChatRoom class


