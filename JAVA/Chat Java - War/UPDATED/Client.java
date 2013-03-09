import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame
{
	public JTextArea listMessage, whosOnline;
	public JTextField yourInput;
	public JButton connect, send;
	public String inputString;
	public String nickname = "";
	public Socket s = null;
	
	private PrintWriter output;
	
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
		
//		yourInput.addKeyListener(this);
		
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
						output.println(nickname + ": " + inputString);
						output.flush();
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
			
			if(ae.getActionCommand().equals("Send"))
			{
				inputString = yourInput.getText();
				yourInput.setText("");
				yourInput.requestFocus();
				
				try
				{
					output.println(nickname + ": " + inputString);
					output.flush();
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
				
				BufferedReader input		= 	new BufferedReader(
													new InputStreamReader(
													s.getInputStream()));
														
				output	=	new PrintWriter(
								new OutputStreamWriter(
								s.getOutputStream()));
								
				boolean nicknameDone = false;
				boolean whosOnlineLoop = false;
				
				if(s.isConnected())
				{
					
				
					listMessage.append("Connected!\n");
					send.setEnabled(true);
					
					while(true)
					{
										
						String msg = input.readLine();
						
						if(msg.equals("!coming"))
						{
							whosOnlineLoop = false;
						}
						else
						{
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
								output.println(nickname);
								nicknameDone = true;
							}
						}
						
						while(!whosOnlineLoop)
						{
							msg = input.readLine();
							if(msg.equals("!end"))
							{
								whosOnlineLoop = true;
								break;
							}
							
							whosOnline.append(msg + "\n");
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
			
				
			
		} // end run
	}
		
} // end class