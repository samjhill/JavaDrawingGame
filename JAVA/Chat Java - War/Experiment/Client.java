import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Client extends JFrame
{
	public JTextArea listMessage, whosOnline;
	public JTextField yourInput;
	public JButton connect, send;
	public String inputString;
	public String nickname = "";
	public Socket s = null;
	
	public ArrayList<String> online = new ArrayList<String>();
	
	private PrintWriter output;
	private ObjectOutputStream sendMessage; 
	private ObjectInputStream getMessage;
	
	public static void main(String[] args)
	{
		new Client(args);
	} // end main
	
	
	public Client(String[] args)
	{
		super("Lab 4: Instant Message");
		setResizable(false);
		
		JLabel ipLabel		= new JLabel("IP Address: ");
		JLabel portLabel	= new JLabel("Port Address: ");
		
		JTextField ip		= new JTextField(10);
		JTextField port	= new JTextField(5);
		
		connect = new JButton("Connect");
		connect.setMnemonic('C');
		
		send = new JButton("Send");
		send.setMnemonic('S');
		send.setEnabled(false);
		
		listMessage = new JTextArea(20, 20);
		yourInput = new JTextField(15);
		whosOnline  = new JTextArea(10, 10);
		
		JScrollPane listMessageScroll	= new JScrollPane(listMessage);
		JScrollPane whosOnlineScroll	= new JScrollPane(whosOnline);
		
		listMessage.setEditable(false);
		listMessage.setWrapStyleWord(true);
		listMessage.setLineWrap(true);
		
		whosOnline.setEditable(false);
		whosOnline.setLineWrap(true);
		
		JPanel center	= new JPanel();
		JPanel bottom	= new JPanel();
		JPanel top		= new JPanel();
		
		top.add(ipLabel);
		top.add(ip);
		top.add(portLabel);
		top.add(port);
		top.add(connect);
		
		center.add(listMessageScroll, BorderLayout.CENTER);
		center.add(whosOnlineScroll, BorderLayout.EAST);
		
		bottom.add(yourInput, BorderLayout.WEST);
		bottom.add(send, BorderLayout.EAST);
		
		add(top, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				try
				{
					if(s.isConnected())
					{
						try
						{
							String disconnect = nickname + " has left the chat room.";
							output.println(disconnect);
							output.flush();
							
							output.close();
							s.close();
							
							System.exit(0);
						}
						catch(NullPointerException npe)
						{
							listMessage.append("Error: You haven't connected to server yet.\n" +
													 "Please enter IP and port address correct.");
						}
						catch(IOException ioe)
						{
							System.out.println(ioe.getMessage());
							ioe.printStackTrace();
						}
					}
					else
					{
						System.exit(0);
					}
				}
				catch(NullPointerException npe)
				{
					System.exit(0);
				}
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		GetAction ga = new GetAction(ip, port);
		connect.addActionListener(ga);
		send.addActionListener(ga);
		
		
		yourInput.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e) 
			{
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					inputString = yourInput.getText();
					yourInput.setText("");
					yourInput.requestFocus();
					
					try
					{
						
//						output.println(nickname + ": " + inputString);
//						output.flush();
					}
					catch(NullPointerException npe)
					{
						listMessage.append("Error: You haven't connected to server yet.\n" +
												 "Please enter IP and port address correct.");
					}
					
				}
			}
		});
		
		
		
	} // end constructor
	
	public void send(String message)
	{
		try
		{
			ClientMessage cm = new ClientMessage();
			cm.message = (nickname + ": " + message);
			sendMessage.writeObject(cm);
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
	}
	
	
	class GetAction implements ActionListener
	{
		private JTextField getIP;
		private JTextField getPort;
		
		
		public GetAction(JTextField _ip, JTextField _port)
		{
			try
			{
				getIP = _ip;
				getPort = _port;
			}
			catch(NullPointerException npe)
			{
				listMessage.append("Error: You haven't connected to server yet.\n");
			}
		}
	
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand().equals("Connect"))
			{
				String sendIP = getIP.getText();
				String sendPort = getPort.getText();
				
				if(sendIP.length() > 0 && sendPort.length() > 0)
				{
					listMessage.append("Connecting: " + sendIP + ":" + sendPort + "...\n");
					ClientChat cc = new ClientChat(sendIP, sendPort);
					
					connect.setEnabled(false);
					yourInput.requestFocus();
					cc.start();
					
				}
				else
				{
					listMessage.append("Error: Please enter IP and port address\n");
				}
			}
	//////////////////////////////////////////////////////////////////////////////////		
			if(ae.getActionCommand().equals("Send"))
			{
				inputString = yourInput.getText();
				yourInput.setText("");
				yourInput.requestFocus();
				
				try
				{
					send(inputString);

//					output.println(nickname + ": " + inputString);
//					output.flush();
				}
				catch(NullPointerException npe)
				{
					listMessage.append("Error: You haven't connected to server yet.\n" +
											 "Please enter IP and port address correct.");
				}
				
			}
		}
		
	} // end getAction
	
	class ClientChat extends Thread
	{
		private String hasIP;
		private String hasPort;
		private String getInput;
		
		public ClientChat(String _getIP, String _getPort)
		{
			
			hasIP = _getIP;
			hasPort = _getPort;
			
			
		} // end constructor
		
		
		public void run()
		{
			System.out.println("Client is running");
			
			try
			{
				int convertInt = Integer.parseInt(hasPort);
				s = new Socket(hasIP, convertInt);
					
				boolean nicknameDone = false;
				
				listMessage.append("Connected!\n");
				send.setEnabled(true);
					
				while(true)
				{
					getMessage = new ObjectInputStream(s.getInputStream());
					ServerMessage sm = (ServerMessage) getMessage.readObject();			
					if(sm.online != null)
					{
						for(int a = 0; a < sm.online.size(); a++)
						{
							whosOnline.append(sm.online.get(a) + "\n");
						}
					}
					else if(sm.message != null)
					{
						String msg = sm.message;
						listMessage.append(msg + "\n");
					}
								
					while(!nicknameDone)
					{
						if(nickname.length() > 0)
						{
							break;
						}
						else
						{
							nickname = JOptionPane.showInputDialog(null, "Please enter your nickname", "");
							ClientMessage cm = new ClientMessage();
							cm.nickname = nickname;
							sendMessage.writeObject(cm);
							sendMessage.flush();
							nicknameDone = true;
						}
					}
				}
			}
			catch(UnknownHostException uhe)
			{
				System.out.println(uhe.getMessage());
				listMessage.append("Error: Unknown Host\n");
				connect.setEnabled(true);
			}
			catch(SocketException se)
			{
				listMessage.append("Error: Connection failed: Server disconnected");
			}
			
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
				listMessage.append("Error: Connection failed: refused\n");
				connect.setEnabled(true);
			}
			catch(NumberFormatException nfe)
			{
				System.out.println(nfe.getMessage());
				listMessage.append("Error: IP or Port must be number only.\n");
				connect.setEnabled(true);
			}
			catch(ClassNotFoundException cnfe)
			{
				System.out.println(cnfe.getMessage());
				listMessage.append("Error: Class not found (ServerMessage.java is missing)");
			}
			
				
			
		} // end run
	}
		
} // end class