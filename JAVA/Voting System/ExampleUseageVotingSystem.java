import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class ExampleUseageVotingSystem extends JFrame {
	public ExampleUseageVotingSystem(VoteClient vc) {
		add(vc);
	}
	public static void main(String[] args) {
		
		VoteClient vc = new VoteClient();
		ExampleUseageVotingSystem euvs = new ExampleUseageVotingSystem(vc);
		euvs.setSize(275, 76*7);
		euvs.setLocation(200, 200);
		euvs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		euvs.setVisible(true);
		
		
		/*
		ExampleUseageVotingSystem euvs = new ExampleUseageVotingSystem();
		euvs.setSize(275, 98*7);
		euvs.setLocation(200,200);
		euvs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Vote myVote = new Vote();
		
		euvs.add(myVote);
		euvs.setVisible(true);
		*/
		
		
		
		/////////////
		// Make a ton of Entries
		/////////////
		
		/*
		Entry a = new Entry("Matthew", "Entry 1", new ImageIcon("myDrawing.png"), true);
		Entry one = new Entry("Matthew", "New Entry", new ImageIcon("1.jpg"), true);
		Entry two = new Entry("Matthew", "New Entry", new ImageIcon("2.jpg"), true);
		Entry three = new Entry("Matthew", "New Entry", new ImageIcon("3.jpg"), true);
		Entry four = new Entry("Matthew", "New Entry", new ImageIcon("4.jpg"), true);
		Entry five = new Entry("Matthew", "New Entry", new ImageIcon("5.jpg"), true);
		Entry six = new Entry("Matthew", "New Entry", new ImageIcon("6.jpg"), true);
		Entry seven = new Entry("Matthew", "New Entry", new ImageIcon("7.jpg"), true);
		Entry eight = new Entry("Matthew", "New Entry", new ImageIcon("8.jpg"), true);
		Entry nine = new Entry("Matthew", "New Entry", new ImageIcon("9.jpg"), true);
		
		ExampleUseageVotingSystem vote = new ExampleUseageVotingSystem();
		vote.setSize(275, 98*7);
		vote.setLocation(200,200);
		vote.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vote.setVisible(true);
		
		vote.add(a);
		vote.add(one);
		vote.add(two);
		vote.add(three);
		vote.add(four);
		vote.add(five);
		vote.add(six);
		vote.setVisible(true);
		
		
		
		*/
		
	}
}