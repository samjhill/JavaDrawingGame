import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestSerial extends JFrame
{

	private JTextArea jta;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public static void main(String[] args)
	{
		new TestSerial();
		
		
	}// end main
	
	public TestSerial()
	{
		super("Test Serialization");
		setSize(400, 400);
		setLocationRelativeTo(null);
		
		JButton button = new JButton("Send");
		button.setMnemonic('S');
		JPanel size = new JPanel();
		size.add(button);
		add(size, BorderLayout.NORTH);
		
		jta = new JTextArea(10, 10);
		jta.setEditable(false);
		add(jta, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		getAction ga = new getAction();
		button.addActionListener(ga);
		
		TestThread tt = new TestThread();
		tt.start();
		
	}
	
	class getAction implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand().equals("Send"))
			{
				System.out.println("Test - successful.");
				jta.append("Sent. \n");
				
				
			}
		}
	}
	
	class TestThread extends Thread
	{
		public void run()
		{
			System.out.println("Test Thread - Successful");
			try
			{
				Socket s = new Socket("localhost",10000);
				oos = new ObjectOutputStream(s.getOutputStream());
			
				ois = new ObjectInputStream(s.getInputStream());
				
				
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
	}
}