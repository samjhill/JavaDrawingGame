import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;


public class VoteClient extends JPanel implements EntryInformation {
	// Arraylist of top items
	private ArrayList<Entry> top;
	// Object IO
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	// Network
	private Socket cs = null;
	// Comparative ArrayList<Entry>
	private ArrayList<Entry> historicalVotes;
	private ArrayList<Entry> localEntries;
	
	public VoteClient() {
		try {
			cs = new Socket(JAVA_HOST,PORT_NUMBER);
			
			ois = new ObjectInputStream(cs.getInputStream());
			
			top = new ArrayList<Entry>(0);
			top = (ArrayList)ois.readObject();
			
			// What were the votes before user actions?
			historicalVotes = new ArrayList<Entry>(0);
			for (int i=0; i<top.size(); i++) {
				historicalVotes.add(top.get(i));
			}
						
			// Accumulate 'local entries'
			localEntries = new ArrayList<Entry>(0);
			for (int i=0; i<top.size(); i++) {
				localEntries.add(new Entry(top.get(i).getAuthor(), top.get(i).getTitle(), top.get(i).getImage(), false));
			}
			
			// Correct the votes of local entries
			for (int i=0; i<top.size(); i++) {
				localEntries.get(i).setVotes(top.get(i).getUpvotes(), top.get(i).getDownvotes());
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		// Set Layout
		setLayout(new GridLayout(0,1));
		
		// Add all to JPanel
		for (int i=0; i<localEntries.size(); i++) {
			add(localEntries.get(i));
			localEntries.get(i).correctText();
		}
		
		// Loop through ArrayList looking for changes
		Thread changesMade = new Thread(new Runnable() {
			public void run() {
				while (true) { // loop forever
					for (int i=0; i < historicalVotes.size(); i++) {
						if (historicalVotes.get(i).getVotes() < localEntries.get(i).getVotes()) {
							top.get(i).upvote();
						} else if (historicalVotes.get(i).getVotes() > localEntries.get(i).getVotes()) {
							top.get(i).downvote();
						}
					} // end for loop
					try {
						Thread.sleep(500);
					} catch (InterruptedException ie) { ie.printStackTrace(); }
				} // end forever loop
			}
		});
		changesMade.start();
		
	} // end Constructor
	
}