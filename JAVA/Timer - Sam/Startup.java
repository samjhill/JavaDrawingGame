import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
/**
 * Start Timer - this is used as a warning that the game is about to start
 * 
 * @author Sam Hill
 * @version 4/16/2012
 */
public class Startup extends JFrame implements TimerConstants
{
    int count = STARTUPTIME;
    JTextField countdown;
    /**
     * Constructor for objects of class Timer
     */
    public Startup()
    {
        JPanel jp = new JPanel();
        jp.setBackground(Color.white);
        
        //font
        Font countFont = new Font("countFont", Font.BOLD, 16);
        Color lightBlue = new Color(4,191,191);
        
        //swing timer
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
        
        //add text fields to panels
        jp.add(countdown);
        
        //add panels to frame
        setSize(100,85);
        add(jp);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new Startup();
    }
    
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
    }
    public int getCount()
    {
        return count;
    }

    class ClockListener implements ActionListener
    {
        public ClockListener(){}
        public void actionPerformed(ActionEvent ae)
        {
            if(count < 1)
            {
                ((Timer)ae.getSource()).stop();
                Font countFont = new Font("countFont", Font.BOLD, 20);
                countdown.setFont( countFont );
                countdown.setText("Start!");
            }
            else{
                count -= 1;
                countdown.setText(count + "");
            }
            
        }
    }
}
