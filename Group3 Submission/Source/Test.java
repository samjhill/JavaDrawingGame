import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test extends JFrame
{
	public static void main(String[] args)
	{
		
		
		
		new Test();
	}
	
	public Test()
	{
		super("Test");
		setSize(500, 700);
		setLocationRelativeTo(null);
		
		ClientPanel cp = new ClientPanel();
		
		add(cp.topPanel(), BorderLayout.NORTH);
		add(cp.middlePanel(), BorderLayout.CENTER);
		add(cp.bottomPanel(), BorderLayout.SOUTH);
		
		
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}