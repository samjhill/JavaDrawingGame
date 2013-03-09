import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
public class VoteClient extends JPanel implements EntryInformation {
	// Arraylist of top items
	private ArrayList<Entry> top;
	private ArrayList<JButton> displayButtons;
	// Object IO
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	// Network
	private Socket cs = null;
	private InputStream is = null;
	private int posRecieved;
	
	public VoteClient() {
		top = new ArrayList<Entry>(0);
		for (int i=0; i < VOTE_ENTRY_SIZE; i++) {
			posRecieved = -1;
			try {
				cs = new Socket("localhost",VOTE_PORT);
				oos = new ObjectOutputStream(cs.getOutputStream());
				
				is=cs.getInputStream();
				ois = new ObjectInputStream(is);
				
				while (posRecieved == -1) {
					oos.writeInt(i);
					oos.flush();
					if (ois.available() > 0) {
						posRecieved = ois.readInt();
					}
				}
				////////recieve entries
				String author = (String)ois.readObject();//author
				String title = (String)ois.readObject();//title
				boolean writeData = (Boolean)ois.readObject();//boolean
				///image stuff
				BufferedImage image = ImageIO.read(is);//image
				ImageIcon ii = new ImageIcon(image);//imageIcon
				////////end recieve entries
				    	
				//create entries
				Entry entry = new Entry(author,title,ii,writeData);
				top.add(entry);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
		} // end master loop

		setLayout(new GridLayout(4,4));
		
		// Create 16 (4X4 JButtons)
		displayButtons = new ArrayList<JButton>(VOTE_ENTRY_SIZE);
		for (int i=0; i<VOTE_ENTRY_SIZE; i++) {
			displayButtons.add(new JButton());
			add(displayButtons.get(i));
			displayButtons.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					
				}
			}); 
		}
		
		// Add all to JPanel
		for (int i=0; i<top.size(); i++) {
			displayButtons.get(i).setIcon(new ImageIcon(top.get(i).getImage().getImage().getScaledInstance( 200, 150,  java.awt.Image.SCALE_SMOOTH)));
		}
	} // end Constructor
	
	
	public static void main(String[] args)
	{
	    JFrame frame = new JFrame();
	    frame.add(new VoteClient());
	    frame.setSize(new Dimension(800,600));
	    frame.setVisible(true);
	}
	
}