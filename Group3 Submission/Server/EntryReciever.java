import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import java.util.*;

public class EntryReciever implements EntryInformation {
	// IO Things
	private Socket cs = null;
	private ServerSocket ss = null;
	
	private EntryReciever() {
		try {
			ss = new ServerSocket(IMAGE_PORT);
			System.out.println("EntryReciever is running on port "+IMAGE_PORT);
			while (true) {
				cs = ss.accept();
				System.out.println("Entry Recieved");
				ThreadedEntryReciever ter = new ThreadedEntryReciever(cs);
				ter.start();
			}
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
	
	public class ThreadedEntryReciever extends Thread {
		// Object IO
		private ObjectOutputStream oos = null;
		private ObjectInputStream ois = null;
		private Socket cs;
		// Entry
		private Entry newEntry = null;
		
		public ThreadedEntryReciever(Socket cs) {
			this.cs = cs;
		}
		
		public void run() {
			try {
				System.out.println("New Client");
				ois = new ObjectInputStream(cs.getInputStream());
				oos = new ObjectOutputStream(cs.getOutputStream());
				String author = (String)ois.readObject(); // Author
				String title = (String)ois.readObject(); // Title
				boolean writeData = (Boolean)ois.readObject(); // Write Data
				
				BufferedImage image = ImageIO.read(cs.getInputStream());
				System.out.println("Recieved image: " + image.toString());
				ImageIcon ii = new ImageIcon(image);
				System.out.println("Converted image to imageIcon " + ii.toString());
				
				newEntry = new Entry(author, title, ii, writeData); // Create a new Entry
				System.out.println("New Entry created.");
				newEntry.reprint(); // Write object to file
				System.out.println("Object written to file.");
				ois.close();
				oos.close();
				
			} catch (IOException ioe) { ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) { cnfe.printStackTrace(); }
		}
	}
	
	public static void main(String[] args) {
		new EntryReciever();
	}
	
} 
 
