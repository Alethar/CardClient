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
    private JFrame frame;

    private JButton button;

    private boolean myTurn;

    private BufferedReader input;

    private Player p;

    private Client client;


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
                    window.pack();
                    window.setLayout( new BorderLayout() );
                    window.setSize( new Dimension( 1000, 800 ) );
                    /*
                     * window.getContentPane().setBackground( new Color( 200,
                     * 200, 200 ) );
                     */
                }
                catch ( Exception e )
                {
                    e.printStackTrace();
                }
            }
        } );
    }


    public GUI() throws IOException
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
        //TODO JTEXTFIELD
        
        setVisible( true );

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
     * Create the application.
     */
    public GUI( Player p )
    {

        /*
         * JFrame frame = new JFrame( "Uno" ); setBackground( new Color( 0, 120,
         * 0 ) ); setForeground( Color.GREEN ); mainPanel.setPreferredSize( new
         * Dimension( 100, 100 ) ); JButton deck = new JButton( "Deck Please" );
         * deck.addActionListener( this ); mainPanel.add( deck );
         */
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

}