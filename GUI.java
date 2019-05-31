import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI 
{
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private GridBagConstraints gbc = new GridBagConstraints();
    private Player player;
    private String[] allPng = {"red0.png", "red1.png", "red2.png", "red3.png", 
        "red4.png", "red5.png", "red6.png", "red7.png", "red8.png", "red9.png", 
        "redskip.png", "redreverse.png", "redp2.png", "blue0.png", "blue1.png", 
        "blue2.png", "blue3.png", "blue4.png", "blue5.png", "blue6.png", 
        "blue7.png", "blue8.png", "blue9.png", "blueskip.png", "bluereverse.png", 
        "bluep2.png", "yellow0.png", "yellow1.png", "yellow2.png", 
        "yellow3.png", "yellow4.png", "yellow5.png", "yellow6.png", 
        "yellow7.png", "yellow8.png", "yellow9.png", "yellowskip.png", 
        "yellowreverse.png", "yellowp2.png", "green0.png", "green1.png", 
        "green2.png", "green3.png", "green4.png", "green5.png", "green6.png", 
        "green7.png", "green8.png", "green9.png", "greenskip.png", 
        "greenreverse.png", "greenp2.png"};
    public GUI(Player p) {
        player = p;
    }
    
    /*public void addToContainer(Container c) {
        
        c.setLayout(new GridBagLayout());
        
        button1 = new JButton("Leaderboard");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        c.add(button1, gbc);
        button2 = new JButton("Game Info");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        c.add(button2, gbc);
        button3 = new JButton("Settings");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        c.add(button3, gbc);
        button4 = new JButton("PLAY");
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipadx = 100;
        gbc.ipady = 50;
        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(button4, gbc);
        c.setBackground(Color.BLACK);
    }*/
    public void drawPile(String card) {
        
    }
    public void updateHand() {
        if(player.g)
        JFrame frame = new JFrame();
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(new ImageIcon(.getImage()));
        label.setSize(300, 400);
        frame.add(panel.add(label));
        frame.setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent event) {
        //implement
    }
}
