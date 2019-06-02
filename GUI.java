import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class GUI extends JFrame
{
    private JPanel titletitlePanel;
    
    private JPanel titlecontentPanel;

    private JButton button;

    private boolean myTurn;

    private BufferedReader input;

    private Player p;

    private Client client;
    
    private String ip = "";

    private String previp = "";


    /**
     * Create the application.
     */
    public GUI( Player p )
    {
        this.p = p;
        setLayout( new BorderLayout() );
        titletitlePanel = new JPanel();
        titletitlePanel.setLayout( new BoxLayout( titletitlePanel, BoxLayout.Y_AXIS ) );

        titletitlePanel.setSize( new Dimension( 1000, 100 ) );
        
        JLabel titleLabel = new JLabel( "U N O" );
        titleLabel.setFont( new Font( "sansserif", Font.BOLD, 50 ) );
        titleLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        JSeparator titleS = new JSeparator( SwingConstants.HORIZONTAL );
        titleS.setForeground( new Color( 50, 50, 50 ) );
        titleS.setSize( new Dimension( 10, 5 ) );

        getContentPane().add( titletitlePanel, BorderLayout.NORTH );

        titletitlePanel.add( titleLabel, BorderLayout.NORTH );
        /*
         * JSeparator titleS = new JSeparator( SwingConstants.HORIZONTAL );
         * titleS.setForeground( new Color( 50, 50, 50 ) ); titleS.setSize( new
         * Dimension( 5, 5 ) );
         */
        // mainPanel.add( titleS );
        

        titletitlePanel.setBackground( new Color( 200, 200, 200 ) );
        
        titlecontentPanel = new JPanel();
        
        titlecontentPanel.setLayout( null );
        
        JLabel contentWelcome = new JLabel("Welcome to Uno");
        contentWelcome.setFont( new Font("sansserif", Font.PLAIN, 30) );
        contentWelcome.setAlignmentX( Component.CENTER_ALIGNMENT );
        contentWelcome.setBounds( 380, 130, 250, 100 );
        titlecontentPanel.add( contentWelcome );
        
        JLabel contentIPPrompt = new JLabel("Enter IP Address");
        contentIPPrompt.setFont( new Font("sansserif", Font.PLAIN, 20) );
        contentIPPrompt.setAlignmentX( Component.CENTER_ALIGNMENT );
        contentIPPrompt.setBounds( 420, 200, 200, 100 );
        titlecontentPanel.add( contentIPPrompt );
        String ip = "";
        JTextField contentIPInput = new JTextField("IP Address");
        contentIPInput.setBounds( 400, 270, 200, 20 );
        contentIPInput.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent buttonpress ) {
                final String ip = contentIPInput.getText();
                setIP(ip);
                System.out.println(ip + " entered");
            }
        });
        titlecontentPanel.add( contentIPInput );
        
        JButton content = new JButton("Connect");
        content.setBounds( 410, 310, 180, 20 );
        content.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent buttonpress ) {
                final String ip = contentIPInput.getText();
                setIP(ip);
                System.out.println(ip + " entered");
            }
        });

        titlecontentPanel.add( content );
        
        //contentIPInput.getAccessibleContext()
        
        getContentPane().add( titlecontentPanel, BorderLayout.CENTER );
        getContentPane().setForeground( new Color( 100, 100, 100 ) );
        getContentPane().setBackground( new Color( 100, 100, 100 ) );
        setResizable( false );
        setVisible( true );
        setSize( new Dimension( 1000, 800 ) );
        

    }
    
    
    public void drawGame() {
        
    }


    /**
     * 
     * Used online method from stackoverflow TODO find out what it works
     * 
     * @param srcImg
     * @param w
     *            the width
     * @param h
     * @return
     */
    private Image getScaledImage( Image srcImg, int w, int h )
    {
        BufferedImage resizedImg = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        g2.drawImage( srcImg, 0, 0, w, h, null );
        g2.dispose();

        return resizedImg;
    }
    
    /**
     * 
     * waits until player inputs a IP address, then returns it
     * @return
     */
    public String getIPAddress() {
        
        while(ip == previp) {
            try
            {
                Thread.sleep( 150 );
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
        }
        previp = ip;
        return ip;
    }
    
    public void drawIPFail() {
        System.out.println("Connection failed, try again");
        JLabel connectionFail = new JLabel("Connection failed, try again");
        connectionFail.setFont( new Font("sansserif", Font.PLAIN, 60) );
        connectionFail.setBounds( 410, 350, 180, 40 );
        Component[] comps = titlecontentPanel.getComponents();
        for(Component c:comps) {
            if(c instanceof JTextField) {
                ((JTextField)c).setText( "Not a Valid IP: Retype" );
            }
        }
        titlecontentPanel.add( connectionFail );
        refresh(titlecontentPanel);
        
    }
    
    
    public void refresh( JComponent mainPanel )
    {
        mainPanel.invalidate();
        mainPanel.validate();
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {

    }


    private void actionPerformedEvent( ActionEvent et )
    {
        /*
         * String command = et.getActionCommand(); if ( command.equals(
         * "Deck Please" ) ) { for ( String card : p.getHand() ) { JButton card1
         * = new JButton( card ); card1.addActionListener( this );
         * mainPanel.add( card1 );
         * 
         * } } for ( int x = 0; x < p.getHand().size(); x++ ) { if (
         * command.equals( p.getHand().get( x ) ) ) { if ( command.contains(
         * "p4" ) ) { switchColorPlus4(); } else if ( command.contains( "w" ) )
         * { switchColor(); } if ( p.isPlayable( x ) ) {
         * p.getClient().sendAction( p.getHand().get( x ), p.getIDNum() ); } }
         * 
         * }
         */

    }


    void switchColorPlus4()
    {
    }


    void switchColor()
    {

    }
    
    /*public GUI() throws IOException
    {
        // JFrame frame = new JFrame("Uno");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout( new BorderLayout() );

        // mainPanel.setPreferredSize( new Dimension( 1000, 45 ) );
        mainPanel.setPreferredSize( new Dimension( 500, 400 ) );

        // JButton deck = new JButton("Test");
        // deck.addActionListener( this );
        // mainPanel.add( deck );
        /*
         * JLabel testL = new JLabel( "U N O" ); testL.setFont( new Font(
         * "Monaco", Font.PLAIN, 30 ) ); testL.setAlignmentX(
         * Component.CENTER_ALIGNMENT );
         * 
         * mainPanel.add( testL ); JSeparator testS = new JSeparator(
         * SwingConstants.HORIZONTAL ); testS.setForeground( new Color( 50, 50,
         * 50 ) ); testS.setSize( new Dimension( 5, 5 ) );mainPanel.add( testS
         * );
         */
        // TODO JTEXTFIELD

        /*setVisible( true );

        add( mainPanel, BorderLayout.NORTH );

        BufferedImage bi = ImageIO.read( new File( "blue0.png" ) );
        JLabel picLabel = new JLabel(
            new ImageIcon( getScaledImage( new ImageIcon( bi ).getImage(), 100, 100 ) ) );
        picLabel.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                System.out.println( "CLick" );

            }


            public void mouseEntered( MouseEvent e )
            {
                System.out.println( "Enter" );
                mainPanel.remove( picLabel );
                mainPanel.add( picLabel, BorderLayout.SOUTH );
                refresh( mainPanel );

            }


            public void mouseExited( MouseEvent e )
            {
                System.out.println( "Exit" );
                mainPanel.remove( picLabel );
                mainPanel.add( picLabel, BorderLayout.NORTH );
                refresh( mainPanel );

            }

        } );
        mainPanel.add( picLabel, BorderLayout.NORTH );

    }*/

    /**
     * Launch the application. For testing purposes only.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable()
        {
            public void run()
            {
                try
                {
                    GUI window = new GUI( null );
                    /*
                     * window.getContentPane().setBackground( new Color( 200,
                     * 200, 200 ) );
                     */
                    /*
                     * window.setSize( new Dimension( 1000, 800 ) );
                     * window.pack(); window.setVisible( true );
                     */
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
        } );
    }
    
    public void setIP(String ip) {
        this.ip = ip;
    }

}