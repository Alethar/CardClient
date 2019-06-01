import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements ActionListener
{
    private JFrame frame;
    private JButton button;
    private boolean myTurn;
    private Socket sock;
    private BufferedReader input;
    Player p = new Player();
    JPanel mainPanel = new JPanel();
    JButton deck = new JButton("Deck Please");
    JButton red = new JButton("Red");
    JButton green = new JButton("Green");
    JButton yellow = new JButton("Blue");
    JButton blue = new JButton("Yellow");
    Client client = new Client()
    

    /**
     * Launch the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable()
        {
            public void run()
            {
                try
                {
                    GUI window = new GUI();
                    window.frame.setVisible( true );
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
        } );
    }


    /**
     * Create the application.
     */
    public GUI()
    {
        JFrame frame = new JFrame("Uno");
        setBackground( new Color(0,120,0) );
        setForeground( Color.GREEN );
        
        initialize();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        
        
        mainPanel.setPreferredSize(new Dimension(100, 100));
        JButton deck = new JButton("Deck Please");
        deck.addActionListener( this );
        mainPanel.add( deck );
        
    }
    
    private void actionPerformedEvent(ActionEvent et)
    {
        String command = et.getActionCommand();
        if(command.equals( "Deck Please" ))
        {
            for(String card: p.getHand())
            {
                JButton card1 = new JButton(card);
                card1.addActionListener(this);
                mainPanel.add( card1 );
                
            }
        }
        for(int x = 0; x < p.getHand().size(); x++)
        {
            if(command.equals( p.getHand().get( x ) ))
            {
                if(command.contains( "p4" ))
                {
                    switchColorPlus4();
                }
                else if(command.contains( "w" ))
                {
                    switchColor();
                }
                if(p.isPlayable(x))
                {
                    p.getClient().sendAction(p.getHand().get( x ), p.getIDNum());
                }
            }
            
        }
        
    }
    
    void switchColorPlus4()
    {
        mainPanel.add( red );
        mainPanel.add( green );
        mainPanel.add( blue );
        mainPanel.add( yellow );
    }
    void switchColor()
    {
        
    }
}