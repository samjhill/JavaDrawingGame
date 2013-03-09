/**


	UPDATED: VERSION 1.00 - ZOMG THIS FILE CREATED IN 10 MINUTES FUTURE!


*/



import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientPanel extends JPanel
{
	

	private JButton send, set;
	private JTextArea listMessage;
	private JTextField yourInput, getNickname;
	
	
	
	private String inputString;
	private String nickname = "";
	private String confirmNickname = "";
	
	private Socket s = null;
	
	private PrintWriter output;
	
	public JPanel topPanel()
	{
		JLabel nicknameLabel = new JLabel("Nickname: ");
		getNickname = new JTextField(10);
		set = new JButton("Set");

		GetAction ga = new GetAction();
		set.addActionListener(ga);
		
		
		JPanel top = new JPanel();
		top.add(nicknameLabel);
		top.add(getNickname);
		top.add(set);
		
		return top;
		
		
	} // end topPanel - accessor class
	
	
	public JPanel middlePanel()
	{
		listMessage = new JTextArea(26, 20); 
		JScrollPane listMessageScroll	= new JScrollPane(listMessage);
		
		
		
		listMessage.setEditable(false);
		listMessage.setWrapStyleWord(true);
		listMessage.setLineWrap(true);
		
		
		JPanel middle = new JPanel();
		middle.add(listMessageScroll);
		
		return middle;
	}
	
	public JPanel bottomPanel()
	{
		yourInput = new JTextField(15);
		
		send = new JButton("Send");
		send.setMnemonic('S');
		send.setEnabled(false);
		
		
		GetAction ga = new GetAction();
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
						output.println(confirmNickname + ": " + inputString);
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
		
		
		
		JPanel bottom = new JPanel();
		
		bottom.add(yourInput);
		bottom.add(send);
		return bottom;
	}
	
	
	class GetAction implements ActionListener, IPConstants
	{
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand().equals("Set"))
			{
//				listMessage.append("Connecting: " + JAVA_HOST + ":" + CHAT_PORT_NUMBER + "...\n");
//				ClientChat cc = new ClientChat(JAVA_HOST, CHAT_PORT_NUMBER);

				ClientChat cc = new ClientChat("localhost", 16789);
				
				getNickname.setEditable(false);
				set.setEnabled(false);
				
				confirmNickname = getNickname.getText();
				
				cc.start();
			}
			
			if(ae.getActionCommand().equals("Send"))
			{
				inputString = yourInput.getText();
				yourInput.setText("");
				yourInput.requestFocus();
				
				try
				{
					output.println(confirmNickname + ": " + inputString);
					output.flush();
				}
				catch(NullPointerException npe)
				{
					listMessage.append("Error: You haven't connected to server yet.\n" +
											 "Please enter IP and port address correct.");
				}
				
			}
			
		}
		
	}
	
	class ClientChat extends Thread
	{
		private String hasIP;
		private int hasPort;
		private String getInput;
		
		public ClientChat(String _getIP, int _getPort)
		{
			
			hasIP = _getIP;
			hasPort = _getPort;
			
			
		} // end constructor
		
		
		public void run()
		{

			try
			{

				s = new Socket(hasIP, hasPort);
				
				BufferedReader input		= 	new BufferedReader(
													new InputStreamReader(
													s.getInputStream()));
														
				output	=	new PrintWriter(
								new OutputStreamWriter(
								s.getOutputStream()));
								
				boolean nicknameDone = false;

				
				if(s.isConnected())
				{
					
				
					listMessage.append("Connected!\n");
					send.setEnabled(true);
					
					
					while(true)
					{
						
						String msg = input.readLine();
						listMessage.append(msg + "\n");
						
					}
					
				}

			}
			catch(UnknownHostException uhe)
			{
				System.out.println(uhe.getMessage());
				listMessage.append("Error: Unknown Host\n");

			}
			catch(SocketException se)
			{
				listMessage.append("Error: Connection failed: Server disconnected");
			}
			
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
				listMessage.append("Error: Connection failed: refused\n");

			}
			catch(NumberFormatException nfe)
			{
				System.out.println(nfe.getMessage());
				listMessage.append("Error: IP or Port must be number only.\n");

			}
			
				
			
		} // end run
	}
	
	
	
} // end ClientPanel class