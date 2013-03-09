import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

/**
 * Start Timer - this is used as a warning that the game is about to start
 * 
 * @author Sam Hill
 * @version 4/16/2012
 *
 * @ Last revision/checked May 16, 2012
 */
 
public class Startup extends JFrame implements TimerConstants
{
    int count = STARTUPTIME;
    JPanel jp;
    JTextField countdown;
	 
    /**
     * Constructor for objects of class Timer
     */
    public Startup()
    {
    } // end default constructor
	 
    public JPanel startupTimer()
    {
        jp = new JPanel();
        jp.setBackground(Color.white);
        
        // Setting up for Font Style
        Font countFont = new Font("countFont", Font.BOLD, 25);
        Color lightBlue = new Color(4,191,191);
        
        // Setting up and display swing timer
        countdown = new JTextField( 8 );
        countdown.setText( count + "" );
        countdown.setEditable( false );
        countdown.setFont( countFont );
		  
        countdown.setHorizontalAlignment(JTextField.CENTER);
        countdown.setForeground( lightBlue );
        countdown.setBackground( Color.white );
        countdown.setBorder(BorderFactory.createLineBorder(Color.white));
       
		  Timer timer = new Timer(1000, new ClockListener());
        timer.start();
        
        // Add text fields to panels
        jp.add(countdown);
        
        // Add panels to frame
        jp.setSize(100,85);
        jp.setVisible(true);
        
        return jp;
    } // end startupTimer()
	 
    public static void main(String[] args)
    {
        new Startup();
    } // end main
    
    //accessors
    /*
     *  This will return true if the countdown timer has reached 0. 
     *  We are going to use this to determine when to turn on drawing controls
     */
    public boolean isFinished()
    {
        if(count < 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    } // end isFinished()
	 
    public int getCount()
    {
        return count;
    } // end getCount()


	// ActionListener Class
    class ClockListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if(count < 1)
            {
                ((Timer)ae.getSource()).stop();
                Font countFont = new Font("countFont", Font.BOLD, 30);
                countdown.setFont( countFont );
                countdown.setText("Start!");
                setVisible(false);
            }
            else
				{
                count -= 1;
                countdown.setText(count + "");
            }
        } // end actionPerformed
    }// end ClockListener class 
}
