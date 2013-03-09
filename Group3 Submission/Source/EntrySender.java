import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;

public class EntrySender implements EntryInformation {
    // IO Things
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private FileInputStream fis = null;
    private Socket cs = null;
    // Entry
    private Entry newEntry = null;
    
    public EntrySender(Entry newEntry, Image myImage) {
        this.newEntry = newEntry;
        try {
            cs = new Socket(JAVA_HOST, IMAGE_PORT);
            
            oos = new ObjectOutputStream(cs.getOutputStream());
            ImageIO iio = null;
            
            oos.writeObject(newEntry.getAuthor()); // Author
            oos.writeObject(newEntry.getTitle()); // Title
            oos.writeObject(true); // Boolean
            
            //added by Sam 5/16 2:30am
            //we need to 'draw the image' onto the buffered image
            //doesnt fix file size problem.
            //http://www.java-forums.org/advanced-java/4035-copy-image-imageicon-into-file-disk.html
            BufferedImage bi = new BufferedImage(myImage.getWidth(null), myImage.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(myImage, 0, 0, null);
            g2.dispose();
            
            //I noticed that the test entries Matt created were .jpg - would using .jpg instead of png fix anything?
            ImageIO.write(bi, "png", cs.getOutputStream());     
            oos.flush();
            oos.close();
            cs.close();
        } catch (UnknownHostException uhe) { uhe.printStackTrace();
        } catch (IOException ioe) { ioe.printStackTrace(); }
        
    }
    
}