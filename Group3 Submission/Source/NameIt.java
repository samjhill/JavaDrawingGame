/*

    For Drawpad - after timer ends

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameIt extends JPanel
{
    private JTextField name, title;
    private JLabel nameLabel, titleLabel;
    private JButton send;
    private boolean clicked;
    private String submissionAuthor;
    private String submissionTitle;
    private Image icon;
    
    public NameIt(Image _icon)
    {
        icon = _icon;
    }
    public JPanel top()
    {
        // Label and Textfield for Nickname
        nameLabel = new JLabel("Your nickname");
        name = new JTextField(20);
        
        
        // Label and Textfield for Title
        titleLabel = new JLabel("Title of your Doodle");
        title = new JTextField(20);
        
        // Panel with GridLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4,0));
        
        topPanel.add(nameLabel);
        topPanel.add(name);
        topPanel.add(titleLabel);
        topPanel.add(title);
        
        return topPanel;
    }
    
    public JPanel bottom()
    {
        // JButton for Send
        send = new JButton("Send");
        send.setMnemonic('S');
        
        
        // JPanel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(send);
        
        // ActionListener
        GetAction ag = new GetAction();
        send.addActionListener(ag);
        
        
        return bottomPanel;
    }
    
    // ActionListener
    class GetAction implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if(ae.getActionCommand().equals("Send"))
            {
                clicked = true;
                String submissionAuthor = name.getText();
                String submissionTitle = title.getText();
                    
                Entry testEntry = new Entry(submissionAuthor, submissionTitle, new ImageIcon(icon), true);
                    new EntrySender(testEntry, icon);
                
                
                send.setText("Image Saved");
                
                JOptionPane.showMessageDialog(null, "Your Image", "About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(icon));
                
                send.setEnabled(false);
                validate();

                
            }
        }
    }
    
    public String getTitle()
    {   
        // Get title input
        return title.getText();
    }
    
    public String getName()
    {
        // Get name input
        return name.getText();
    }
    
    public boolean isClicked()
    {
          // Get boolean of click
        return clicked;
    }
}