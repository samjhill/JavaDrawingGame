import javax.swing.*;

public interface EntryInformation extends IPConstants {
	// Data file definition
	static final String FILE_FOLDER = "entries/";
	// Upvotes and Downvotes
	static final ImageIcon upvote = new ImageIcon("upvote.png");
	static final ImageIcon downvote = new ImageIcon("downvote.png");
	static final ImageIcon unactivated_upvote = new ImageIcon("unactivated_upvote.png");
	static final ImageIcon unactivated_downvote = new ImageIcon("unactivated_downvote.png");
	// EntryPhotos Variables
	static final int DELAY = 5000;
	// Server data
	static final int PORT_NUMBER = IMAGE_PORT_NUMBER;
	static final String HOST = JAVA_HOST;
}