import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.image.*;
import javax.imageio.*;

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
			ss = new ServerSocket(VOTE_PORT);
			System.out.println("VoteServer is running on port : " + VOTE_PORT);
			while (true) { // loop forever
				try {
					cs = ss.accept();
					VoteThreaded vt = new VoteThreaded(cs);
					vt.start();
				} catch (Exception e) {}
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
		private OutputStream os = null;
		private int pos = -1;
		
		public VoteThreaded(Socket cs) {
			// Assign variables
			topEntries = top();
			this.cs = cs;
		} // constructor
		public void run() {
			try {
				os = cs.getOutputStream();
				oos = new ObjectOutputStream(os);
				ois = new ObjectInputStream(cs.getInputStream());
				pos = ois.readInt(); 
				oos.writeInt(pos);
				oos.flush();
				sender(pos);
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} // run
		
		public void sender(int pos) {
			try {
				oos.writeObject(topEntries.get(pos).getAuthor());
				oos.writeObject(topEntries.get(pos).getTitle());
				oos.writeObject(false);
				// Retrieve and rewrite image
				Image myImage = topEntries.get(pos).getImage().getImage();
				BufferedImage bi = new BufferedImage(myImage.getWidth(null), myImage.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D g2 = bi.createGraphics();
				g2.drawImage(myImage, 0, 0, null);
				g2.dispose();
				ImageIO.write(bi, "png", os);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	} // threaded class
	
	public static void main(String[] args) {
		VoteServer vs = new VoteServer();
	}
	
	// Note: The find method is derived from Stackoverflow.com
	public File[] find() {
		File directory = new File("entries/");
		return directory.listFiles(new FilenameFilter() { 
			public boolean accept(File find, String filename) { 
				return filename.endsWith(".entry"); 
			}
		});
	}
	
	public ArrayList<Entry> top() {
		entries = find();
		ArrayList<Entry> topEntries = new ArrayList<Entry>(VOTE_ENTRY_SIZE);
		// Get a "random" set;
		ArrayList<Integer> randomSelection = new ArrayList<Integer>(VOTE_ENTRY_SIZE);
		Random ranNum = new Random();
		for (int i=0; i<VOTE_ENTRY_SIZE; i++) {
			randomSelection.add(ranNum.nextInt((Integer)entries.length-1));
		}
		// Add the "randomly" selected entries to the ArrayList
		for (int i=0; i<VOTE_ENTRY_SIZE; i++) {
			try {
				ois = new ObjectInputStream(new FileInputStream(entries[randomSelection.get(i)]));
				topEntries.add((Entry)ois.readObject());
			} catch (InvalidClassException ice) {
			} catch (FileNotFoundException fnfe) {
			} catch (ClassNotFoundException cnfe) {
			} catch (IOException ioe) {}
		}
		return topEntries;
	} // end top();
}