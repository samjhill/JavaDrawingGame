import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class VoteServer implements EntryInformation {
	// Array of Entries
	private File[] entries;
	// File IO
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	// Net IO
	private ServerSocket ss = null;
	private Socket cs = null;
	// ArrayList of chosen entries
	private ArrayList<Entry> top;
	
	public VoteServer() {
		// Set up server
		try {
			ss = new ServerSocket(PORT_NUMBER);
			while (true) { // loop forever
				cs = ss.accept();
				top = top();
				VoteThreaded vt = new VoteThreaded(top, cs);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	} // end Constructor
	
	class VoteThreaded extends Thread {
		private ArrayList<Entry> topEntries;
		private Socket cs = null;
		private ObjectOutputStream oos = null;
		private ObjectInputStream ois = null;
		
		public VoteThreaded(ArrayList<Entry> top, Socket cs) {
			// Assign variables
			topEntries = top;
			this.cs = cs;
			
			try {
				oos = new ObjectOutputStream(cs.getOutputStream());
				ois = new ObjectInputStream(cs.getInputStream());
				oos.writeObject(top);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		VoteServer vs = new VoteServer();
	}
	
	// Note: The find method is derived from Stackoverflow.com
	public File[] find() {
		File directory = new File("Entries/");
		return directory.listFiles(new FilenameFilter() { 
			public boolean accept(File find, String filename) { 
				return filename.endsWith(".entry"); 
			}
		});
	}
	
	public ArrayList<Entry> top() {
		entries = find();
		ArrayList<Entry> topEntries = new ArrayList<Entry>(7);
		Entry comparedEntry = null;
		for (int i=0; i < entries.length; i++) {
			try {
				ois = new ObjectInputStream(new FileInputStream(entries[i]));
				comparedEntry = (Entry)ois.readObject();
			} catch (FileNotFoundException fnfe) { 
				fnfe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} 
			boolean shouldKeepGoing = true; 
			while (shouldKeepGoing) {
				for (int j=0; j < 7; j++) {
					if (shouldKeepGoing) {
						if (topEntries.size()==0) {
							topEntries.add(comparedEntry);
							shouldKeepGoing = false;
						} else if (j==topEntries.size()) {
							topEntries.add(comparedEntry);
							shouldKeepGoing = false;
						} else if (comparedEntry.getVotes() > ((Entry)topEntries.get(j)).getVotes()) {
							topEntries.add(j, comparedEntry);
							shouldKeepGoing = false;
						}
					}
				}
			} // should keep going loop 
		} // end master loop
		
		if (topEntries.size() > 7) {
			for (int i=7; i < topEntries.size(); i++) {
				topEntries.remove(i);
			}
		}
		return topEntries;
	} // end top();
}