import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
/**
 * Countdown Timer
 * 
 * @author Sam Hill
 * @version 4/16/2012
 */
public class Countdown extends JFrame implements TimerConstants
{
    int count = COUNTDOWNTIME;
    JTextField countdown;
    /**
     * Constructor for objects of class Timer
     */
    public Countdown()
    {
    }
    public JPanel countdownTimer()
    {
        JPanel jp = new JPanel();
        jp.setBackground(Color.white);
        jp.setLayout( new BorderLayout());
        
        //font
        Font countFont = new Font("countFont", Font.BOLD, 30);
        Font font = new Font("regular", Font.BOLD, 14);
        Color lightBlue = new Color(4,191,191);
        
        //swing timer
        countdown = new JTextField( count + "" );
        countdown.setEditable( false );
        countdown.setFont( countFont );
        countdown.setHorizontalAlignment(JTextField.CENTER);
        countdown.setForeground( lightBlue );
        countdown.setBackground( Color.white );
        countdown.setBorder(BorderFactory.createLineBorder(Color.white));
        Timer timer = new Timer(1000, new ClockListener());
        timer.setInitialDelay(STARTUPTIME * 1000 + 1000);
        timer.start();
        
        //static field just says "seconds"
        JTextField seconds = new JTextField("seconds left");
        seconds.setFont( font );
        seconds.setForeground( lightBlue );
        seconds.setBackground( Color.white );
        seconds.setHorizontalAlignment(JTextField.CENTER);
        seconds.setBorder(BorderFactory.createLineBorder(Color.white));
        seconds.setEditable( false );
        
        //add text fields to panels
        jp.add(countdown, "North");
        jp.add(seconds, "South");
        
        
        
        //add panels to frame
        jp.setSize(10,85);
        jp.setLocation(500,100);
        jp.setVisible(true);
        
        return jp;
    }

    public static void main(String[] args)
    {
        new Countdown();
    }
    
    //accessors
    /*
     *  This will return true if the countdown timer has reached 0. 
     *  We are going to use this to determine when to shut off drawing controls
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
                setVisible(false);
            }
            else{
                count -= 1;
                countdown.setText(count + "");
            }
            
            if(count <= 10)
            {
                countdown.setForeground(Color.orange);
            }
            
            if(count <= 5)
            {
                countdown.setForeground(Color.red);
                Font countFont = new Font("countFont", Font.BOLD, 34);
                countdown.setFont( countFont );
            }
        }
    }
}
