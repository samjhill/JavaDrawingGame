import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Entry extends JPanel implements Serializable, EntryInformation, Comparable {
	// Integers for Upvotes & Downvotes
	private int upvotes;
	private int downvotes;
	// Specific information about drawing
	private String author;
	private String title;
	// Image and Icon Image
	private ImageIcon entryQ;
	private transient ImageIcon scaledImageIcon;
	private transient JButton entryButton;
	private transient JPanel imagePanel;
	// Specialized JPanels for display
	private transient JPanel voteButtons;
	private transient JPanel entryInformation;
	private transient JPanel jp_score;
	// Upvote, Downvote, and Score objects
	private transient JButton b_upvote;
	private transient JButton b_downvote;
	private transient JLabel score;
	// Miscellaneous Items
	private transient static final int spacing = 3;
	// Object IO
	private transient ObjectOutputStream oos = null;
	private transient ObjectInputStream ois = null;
	// Unique Identifier
	private UUID UniqueID;
	private Date creationDate;
	// ActionListeners
	private transient ActionListener al_upvote;
	private transient ActionListener al_downvote;
	// Colors
	private transient Color whiteBackground = new Color(255,255,255);
	// Create Data Upon Running Constructor?
	private boolean createData = false;
	// Constructing Constructor
	public Entry(String author, String title, ImageIcon entry, boolean createData) {
		// Assign the 3 parameters to objects
		this.author = author;
		this.title = title;
		this.entryQ = entry;
		this.createData = createData;
		// Set Unique ID
		UniqueID = UUID.randomUUID();
		// Set Millisecond of creation
		creationDate = new Date();
		// Set votes up
		upvotes = 0;
		downvotes = 0;
		// Configure main JPanel items
		setLayout(new BorderLayout(spacing, spacing));
		setBackground(whiteBackground);
		// Display Image
		imagePanel = new JPanel();
		entryButton = new JButton();
		entryButton.setIcon(scaledImageIcon = new ImageIcon(entry.getImage().getScaledInstance( 88, 66,  java.awt.Image.SCALE_SMOOTH)));
		imagePanel.add(entryButton);
		add(imagePanel, BorderLayout.WEST);
		// Configure entryInformation JPanel
		entryInformation = new JPanel();
		entryInformation.setLayout(new GridLayout(2,1,spacing,spacing));		
		entryInformation.setBackground(whiteBackground);
		entryInformation.add(new JLabel(this.title), BorderLayout.NORTH); // Add Title
		entryInformation.add(new JLabel(this.author), BorderLayout.SOUTH); // Add Author
		add(entryInformation, BorderLayout.CENTER); // Add to main JPanel
		// Configure voteButtons JPanel
		voteButtons = new JPanel();
		voteButtons.setLayout(new BorderLayout(spacing,spacing));
		voteButtons.setBackground(whiteBackground);
		b_upvote = new JButton(); // Setup JButtons, add them.
		b_upvote.setBorder(null);
		b_downvote = new JButton();
		b_upvote.setIcon(unactivated_upvote); // Utilize Icon
		b_downvote.setIcon(unactivated_downvote);
		voteButtons.add(b_upvote, BorderLayout.NORTH);
		voteButtons.add(b_downvote, BorderLayout.SOUTH);
		jp_score = new JPanel();
		jp_score.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp_score.setBackground(whiteBackground);
		score = new JLabel("¥"); // Setup and add the score
		jp_score.add(score);
		voteButtons.add(jp_score, BorderLayout.CENTER);
		add(voteButtons, BorderLayout.EAST);
		// Action Listeners
		// UPvote button
		b_upvote.addActionListener(al_upvote = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if ((b_upvote.getIcon() == unactivated_upvote)&&(b_downvote.getIcon() == unactivated_downvote)) {
					b_upvote.setIcon(upvote);
					upvotes++;
				} else if ((b_upvote.getIcon() == upvote)&&(b_downvote.getIcon() == unactivated_downvote)) {
					b_upvote.setIcon(unactivated_upvote);
					upvotes--;
				} else {
					b_downvote.setIcon(unactivated_downvote);
					b_upvote.setIcon(upvote);
					downvotes--;
					upvotes++;
				}
				// Set Text
				if (upvotes-downvotes != 0) {
					score.setText(new Integer(upvotes-downvotes).toString());
				} else {
					score.setText("¥");
				}
				// Setup Counterpart
				b_downvote.setIcon(unactivated_downvote);
				// Normalize Buttons
				b_upvote.setBorder(null);
				b_downvote.setBorder(null);
				b_upvote.setBackground(whiteBackground);
				b_downvote.setBackground(whiteBackground);
				// Rewrite Object
				reprint();
			} // end actionPerformed
		});
		// DOWNvote button
		b_downvote.addActionListener(al_downvote = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if ((b_downvote.getIcon() == unactivated_downvote)&&(b_upvote.getIcon() == unactivated_upvote)) {
					b_downvote.setIcon(downvote);
					downvotes++;
				} else if ((b_downvote.getIcon() == downvote)&&(b_upvote.getIcon() == unactivated_upvote)) {
					b_downvote.setIcon(unactivated_downvote);
					downvotes--;
				} else {
					b_upvote.setIcon(unactivated_upvote);
					b_downvote.setIcon(downvote);
					downvotes++;
					upvotes--;
				}
				// Normalize Buttons
				b_upvote.setBorder(null);
				b_downvote.setBorder(null);
				b_upvote.setBackground(whiteBackground);
				b_downvote.setBackground(whiteBackground);
				// Set Text
				if (upvotes-downvotes != 0) {
					score.setText(new Integer(upvotes-downvotes).toString());
				} else {
					score.setText("¥");
				}
				// Set Counterpart
				b_upvote.setIcon(unactivated_upvote);
				// Rewrite Object
				reprint();
			} // end actionPerformed
		});
		// Write the object to the "database"
		reprint();
		normalize();
	} // End Constructor()
	
	public void reprint() {
		if (createData) {
			try {
				oos = new ObjectOutputStream(new FileOutputStream(FILE_FOLDER+UniqueID+".entry", false));
				oos.writeObject(this);
			} catch (IOException ioe) {
				System.out.println(ioe.getCause());
			}
		}
	}
	
	public void normalize() {
		b_upvote.setBorder(null);
		b_downvote.setBorder(null);
		b_upvote.setBackground(whiteBackground);
		b_downvote.setBackground(whiteBackground);
		entryButton.setBackground(whiteBackground);
		entryButton.setBorder(null);
		imagePanel.setBackground(whiteBackground);
	}
	
	public int getVotes() {
		return upvotes-downvotes;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getTitle() {
		return title;
	}
	
	public ImageIcon getImage() {
		return entryQ;
	}
	
	public UUID getUUID() {
		return UniqueID;
	}
	
	public void upvote() {
		upvotes++;
		reprint();
	}
	
	public void downvote() {
		downvotes++;
		reprint();
	}
	
	public int getUpvotes() {
		return upvotes;
	}
	
	public int getDownvotes() {
		return downvotes;
	}
	
	public void setVotes(int upvotes, int downvotes) {
		this.upvotes = upvotes;
		this.downvotes = downvotes;
		reprint();
	}
	
	public void correctText() {
		if (upvotes-downvotes != 0) {
			score.setText(new Integer(upvotes-downvotes).toString());
		} else {
			score.setText("¥");
		}
	}
	
	public int compareTo(Object e) {
		if (this.getVotes() > ((Entry)e).getVotes()) {
			return 1;
		} else if (this.getVotes() < ((Entry)e).getVotes()) {
			return -1;
		} else {
			if (this.getUUID().timestamp() > ((Entry)e).getUUID().timestamp()) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	
} // end Entry class