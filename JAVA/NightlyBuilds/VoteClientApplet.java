
import java.awt.*;
import javax.swing.*;

/**
 * Class VoteClientApplet - write a description of the class here
 * 
 * @author Sam Hill
 * @version 5/10/2012
 */
public class VoteClientApplet extends JApplet
{
     /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        // this is a workaround for a security conflict with some browsers
        // including some versions of Netscape & Internet Explorer which do 
        // not allow access to the AWT system event queue which JApplets do 
        // on startup to check access. May not be necessary with your browser. 
        JRootPane rootPane = this.getRootPane();    
        rootPane.putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
    
        // provide any initialisation necessary for your JApplet
       
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it 
     * should start its execution. It is called after the init method and 
     * each time the JApplet is revisited in a Web page. 
     */
    public void start()
    {
        add(new VoteClient());
        setVisible(true);
    }

    /** 
     * Called by the browser or applet viewer to inform this JApplet that
     * it should stop its execution. It is called when the Web page that
     * contains this JApplet has been replaced by another page, and also
     * just before the JApplet is to be destroyed. 
     */
    public void stop()
    {
        
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * is being reclaimed and that it should destroy any resources that it
     * has allocated. The stop method will always be called before destroy. 
     */
    public void destroy()
    {
        
    }

}
