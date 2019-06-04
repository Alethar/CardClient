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

    private JPanel gamecontentPanel;
    // includes a JTextField consoleBox, JLabels everywhere, and JImages hand1,
    // hand2, etc.

    private JLabel output;

    private JLabel pilePic;

    private Player p;

    private Client client;

    private String ip = "";

    private String previp = "";

    private boolean cardPlayed;

    private int cardPos;

    private int[] playerRanges;

    private JLabel colorLabel;

    private JLabel numberLabel;

    private String color = null;


    /**
     * draws out a title screen
     */
    public GUI( Player p )
    {
        this.p = p;
        client = p.getClient();
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

        JLabel contentWelcome = new JLabel( "Welcome to Uno" );
        contentWelcome.setFont( new Font( "sansserif", Font.PLAIN, 30 ) );
        contentWelcome.setAlignmentX( Component.CENTER_ALIGNMENT );
        contentWelcome.setBounds( 380, 130, 250, 100 );
        titlecontentPanel.add( contentWelcome );

        JLabel contentIPPrompt = new JLabel( "Enter IP Address" );
        contentIPPrompt.setFont( new Font( "sansserif", Font.PLAIN, 20 ) );
        contentIPPrompt.setAlignmentX( Component.CENTER_ALIGNMENT );
        contentIPPrompt.setBounds( 420, 200, 200, 100 );
        titlecontentPanel.add( contentIPPrompt );
        String ip = "";
        JTextField contentIPInput = new JTextField( "IP Address" );
        contentIPInput.setBounds( 400, 270, 200, 20 );
        contentIPInput.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent buttonpress )
            {
                final String ip = contentIPInput.getText();
                setIP( ip );
            }
        } );
        titlecontentPanel.add( contentIPInput );

        JButton content = new JButton( "Connect" );
        content.setBounds( 410, 310, 180, 20 );
        content.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent buttonpress )
            {
                final String ip = contentIPInput.getText();
                setIP( ip );
            }
        } );

        titlecontentPanel.add( content );

        // contentIPInput.getAccessibleContext()
        getContentPane().add( titlecontentPanel, BorderLayout.CENTER );
        getContentPane().setBackground( new Color( 100, 100, 100 ) );
        setResizable( false );
        setVisible( true );
        setSize( new Dimension( 1000, 850 ) );

    }


    /**
     * 
     * draws a scrren informing the viewer that they are connected to the server
     */
    public void drawConnected()
    {
        JLabel conn = new JLabel( "Connected! Wait for four players to connect." );
        titlecontentPanel.setBorder( BorderFactory.createLineBorder( Color.WHITE ) );
        conn.setFont( new Font( "sansserif", Font.PLAIN, 20 ) );
        conn.setBounds( 300, 200, 5000, 200 );

        for ( Component c : titlecontentPanel.getComponents() )
        {
            c.setVisible( false );
        }
        titlecontentPanel.add( conn );

    }


    /**
     * 
     * initialized the static JLabels on the main game page
     */
    public void initDrawGame()
    {
        client = p.getClient();
        titlecontentPanel.setVisible( false );
        remove( titlecontentPanel );
        getContentPane().remove( titlecontentPanel );
        gamecontentPanel = new JPanel();
        gamecontentPanel.setLayout( null );
        gamecontentPanel.setSize( 1000, 450 );
        gamecontentPanel.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
        output = new JLabel( "Game Start!" );
        output.setFont( new Font( "sansserif", Font.PLAIN, 30 ) );
        output.setBounds( 20, 20, 1000, 30 );
        gamecontentPanel.add( output );
        int idNum = p.getIDNum();
        playerRanges = new int[4];
        int place = 0;

        for ( int x = 0; x < 4; x++ )
        {
            if ( x != idNum )
            {
                playerRanges[x] = place;
                place++;
            }
            else
            {
                playerRanges[x] = 4;
            }
        }
        JLabel pileLabel = new JLabel( "Current Card:" );
        pileLabel.setFont( new Font( "sansserif", Font.PLAIN, 20 ) );
        pileLabel.setBounds( 220, 320, 150, 20 );
        gamecontentPanel.add( pileLabel );
        getContentPane().add( gamecontentPanel, BorderLayout.CENTER );
        // JLabel Deck
        BufferedImage bidp = null;
        try
        {
            bidp = ImageIO.read( new File( "cardback.png" ) );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JLabel deckPic = new JLabel(
            new ImageIcon( getScaledImage( new ImageIcon( bidp ).getImage(),
                bidp.getWidth() / 5,
                bidp.getHeight() / 5 ) ) );
        deckPic.setBounds( 650, 320, bidp.getWidth() / 5, bidp.getHeight() / 5 );
        deckPic.setVisible( true );
        gamecontentPanel.add( deckPic );
        JButton unoButton = new JButton();
        unoButton.setText( "U N O" );
        unoButton.setBounds( 885, 680, 100, 75 );
        unoButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent buttonpress )
            {
                int i = p.getIDNum();
                client.sendAction( "uno", i );
            }
        } );
        gamecontentPanel.add( unoButton );
        colorLabel = new JLabel( "Color: " );// + p.getColor());
        colorLabel.setFont( new Font( "sansserif", Font.PLAIN, 15 ) );
        numberLabel = new JLabel( "Number: " );// + p.getColor());
        numberLabel.setFont( new Font( "sansserif", Font.PLAIN, 15 ) );
        colorLabel.setBounds( 500, 350, 150, 15 );
        numberLabel.setBounds( 500, 400, 150, 15 );
        gamecontentPanel.add( colorLabel );
        gamecontentPanel.add( numberLabel );
        for ( int x = 0; x < 3; x++ )
        {

            JLabel playerName = new JLabel( "Player " + ( findID( x ) + 1 ) + ": " );
            playerName.setFont( new Font( "sansserif", Font.PLAIN, 20 ) );
            playerName.setBounds( 25 + x * 325, 120, 150, 20 );
            gamecontentPanel.add( playerName );
        }

        refresh( gamecontentPanel );
        /*
         * contentIPInput.addActionListener( new ActionListener() {
         * 
         * @Override public void actionPerformed( ActionEvent buttonpress ) {
         * final String ip = contentIPInput.getText(); setIP( ip );
         * System.out.println( ip + " entered" ); } } );
         */
    }


    /**
     * 
     * converts from position on gui to player id
     * 
     * @param guiid
     *            the position they are in on the gui (card backs)
     * @return playerID
     */
    public int findID( int guiid )
    {
        for ( int x = 0; x < playerRanges.length; x++ )
        {
            if ( playerRanges[x] == guiid )
            {
                return x;
            }
        }
        return 0;
    }


    /**
     * 
     * deletes previous cards in hand, redraws your current hand with
     * mouseevents that change vars in Player
     */
    public void drawHand()
    {
        for ( Component c : gamecontentPanel.getComponents() )
        {
            if ( c.getName() != null && isInteger( c.getName() ) )
            {
                gamecontentPanel.remove( c );
            }
        }
        int cardNum = p.getHand().size();
        int startIndex = 25;
        int diff = ( 900 - 25 ) / ( cardNum + 1 );
        for ( int x = 0; x < cardNum; x++ )
        {

            String str = p.getHand().get( x );
            if ( isInteger( str.substring( 0, 1 ) ) )
            {
                int num = Integer.parseInt( str.substring( 0, 1 ) );
                String color = str.substring( 1 ).toLowerCase();
                BufferedImage cardimage = null;
                try
                {
                    cardimage = ImageIO.read( new File( color + num + ".png" ) );
                }
                catch ( Exception e )
                {
                    System.out.println( e );
                    System.out.println( color + num + ".png" );
                    System.exit( 0 );
                }
                int width = 100;
                int height = cardimage.getHeight() * 100 / cardimage.getWidth();
                JLabel handPic = new JLabel( new ImageIcon(
                    getScaledImage( new ImageIcon( cardimage ).getImage(), width, height ) ) );
                handPic.setBounds( startIndex + x * diff, 600, width, height );
                handPic.setName( x + "" );
                handPic.addMouseListener( new MouseAdapter()
                {
                    @Override
                    public void mouseClicked( MouseEvent e )
                    {
                        cardPos = Integer.parseInt( handPic.getName() );
                        cardPlayed = true;
                    }

                } );
                gamecontentPanel.add( handPic );
                gamecontentPanel.revalidate();
                gamecontentPanel.repaint();
                refresh( gamecontentPanel );
            }
            else
            {
                String color = "";
                String card = "";

                if ( str.substring( 0, 1 ).equals( "w" ) )
                {
                    card = "wild";
                }
                else if ( str.substring( 0, 1 ).equals( "s" ) )
                {
                    card = "skip";
                    color = str.substring( 1 );
                }
                else if ( str.substring( 0, 1 ).equals( "r" ) )
                {
                    card = "reverse";
                    color = str.substring( 1 );
                }
                else if ( str.substring( 0, 2 ).equals( "p4" ) )
                {
                    card = "wildp4";
                }
                else if ( str.substring( 0, 2 ).equals( "p2" ) )
                {
                    card = "p2";
                    color = str.substring( 2 );
                }

                BufferedImage cardimage = null;
                try
                {
                    cardimage = ImageIO.read( new File( color + card + ".png" ) );
                }
                catch ( Exception e )
                {
                    System.out.println( e );
                    System.out.println( color + card + ".png" );
                    System.exit( 0 );
                }
                int width = 100;
                int height = cardimage.getHeight() * 100 / cardimage.getWidth();
                JLabel handPic = new JLabel( new ImageIcon(
                    getScaledImage( new ImageIcon( cardimage ).getImage(), width, height ) ) );
                handPic.setBounds( startIndex + x * diff, 600, width, height );

                handPic.setName( x + "" );
                handPic.addMouseListener( new MouseAdapter()
                {
                    @Override
                    public void mouseClicked( MouseEvent e )
                    {
                        cardPos = Integer.parseInt( handPic.getName() );
                        cardPlayed = true;
                    }

                } );
                gamecontentPanel.add( handPic );
                gamecontentPanel.revalidate();
                gamecontentPanel.repaint();
                refresh( gamecontentPanel );
            }
        }

    }

    /*
     * public GUI() throws IOException { // JFrame frame = new JFrame("Uno");
     * JPanel mainPanel = new JPanel(); mainPanel.setLayout( new BorderLayout()
     * );
     * 
     * // mainPanel.setPreferredSize( new Dimension( 1000, 45 ) );
     * mainPanel.setPreferredSize( new Dimension( 500, 400 ) );
     * 
     * // JButton deck = new JButton("Test"); // deck.addActionListener( this );
     * // mainPanel.add( deck ); /* JLabel testL = new JLabel( "U N O" );
     * testL.setFont( new Font( "Monaco", Font.PLAIN, 30 ) );
     * testL.setAlignmentX( Component.CENTER_ALIGNMENT );
     * 
     * mainPanel.add( testL ); JSeparator testS = new JSeparator(
     * SwingConstants.HORIZONTAL ); testS.setForeground( new Color( 50, 50, 50 )
     * ); testS.setSize( new Dimension( 5, 5 ) );mainPanel.add( testS );
     */
    // TODO JTEXTFIELD


    /*
     * setVisible( true );
     * 
     * add( mainPanel, BorderLayout.NORTH );
     * 
     * BufferedImage bi = ImageIO.read( new File( "blue0.png" ) ); JLabel
     * picLabel = new JLabel( new ImageIcon( getScaledImage( new ImageIcon( bi
     * ).getImage(), 100, 100 ) ) ); picLabel.addMouseListener( new
     * MouseAdapter() { public void mouseClicked( MouseEvent e ) {
     * System.out.println( "CLick" );
     * 
     * }
     * 
     * 
     * public void mouseEntered( MouseEvent e ) { System.out.println( "Enter" );
     * mainPanel.remove( picLabel ); mainPanel.add( picLabel, BorderLayout.SOUTH
     * ); refresh( mainPanel );
     * 
     * }
     * 
     * 
     * public void mouseExited( MouseEvent e ) { System.out.println( "Exit" );
     * mainPanel.remove( picLabel ); mainPanel.add( picLabel, BorderLayout.NORTH
     * ); refresh( mainPanel );
     * 
     * }
     * 
     * } ); mainPanel.add( picLabel, BorderLayout.NORTH );
     * 
     * }
     */
    /**
     * 
     * draws the players amount of facedown cards
     * 
     * @param playerID
     *            id of player to draw
     */
    public void drawPlayer( int playerID )
    {
        for ( Component c : gamecontentPanel.getComponents() )
        {
            if ( c.getName() != null && c.getName().substring( 0, 1 ).equals( "a" )
                && c.getName().substring( 1, 2 ).equals( playerID + "" ) )
            {
                gamecontentPanel.remove( c );
            }
        }
        int guiID = playerRanges[playerID];
        if ( playerID == p.getIDNum() )
        {
            drawHand();
        }
        else
        {
            int startIndex = 15 + 325 * guiID;
            int cardNum = p.getPlayerHands()[playerID];
            int buffer = 300 / ( cardNum + 1 );
            BufferedImage bidp = null;
            try
            {
                bidp = ImageIO.read( new File( "cardback.png" ) );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            int width = bidp.getWidth() / 5;
            int height = bidp.getHeight() / 5;
            for ( int x = 0; x < cardNum; x++ )
            {
                JLabel deckPic = new JLabel( new ImageIcon(
                    getScaledImage( new ImageIcon( bidp ).getImage(), width, height ) ) );

                deckPic.setBounds( startIndex + x * buffer, 150, width, height );
                deckPic.setVisible( true );
                deckPic.setName( "a" + playerID + x );
                gamecontentPanel.add( deckPic );
            }
            gamecontentPanel.revalidate();
            gamecontentPanel.repaint();
            refresh( gamecontentPanel );
        }

    }


    /**
     * 
     * updates current color
     */
    public void drawColor()
    {
        colorLabel.setText( "Color: " + p.getColor() );
    }


    /**
     * 
     * updates current number
     */
    public void drawNumber()
    {
        numberLabel.setText( "Number: " + p.getNumber() );
    }


    /**
     * 
     * checks if array of components contains a certain component
     * 
     * @param cons
     *            array to check
     * @param c
     *            component to search for
     * @return contains or not
     */
    public boolean containsComp( Component[] cons, Component c )
    {
        for ( Component a : cons )
        {
            if ( a.equals( c ) )
            {
                return true;
            }
        }
        return false;
    }


    /**
     * 
     * draws the current card on the pile
     * 
     * @param str
     *            the card to draw
     */
    public void drawPile( String str )
    {
        if ( containsComp( gamecontentPanel.getComponents(), pilePic ) )
        {
            gamecontentPanel.remove( pilePic );
        }
        if ( isInteger( str.substring( 0, 1 ) ) )
        {
            int num = Integer.parseInt( str.substring( 0, 1 ) );
            String color = str.substring( 1 ).toLowerCase();
            BufferedImage cardimage = null;
            try
            {
                cardimage = ImageIO.read( new File( color + num + ".png" ) );
                // color + num + ".png"
            }
            catch ( Exception e )
            {
                System.out.println( e );
                System.out.println( color + num + ".png" );
                System.exit( 0 );
            }
            pilePic = new JLabel(
                new ImageIcon( getScaledImage( new ImageIcon( cardimage ).getImage(),
                    100,
                    cardimage.getHeight() * 100 / cardimage.getWidth() ) ) );
            pilePic.setBounds( 360, 320, 100, cardimage.getHeight() * 100 / cardimage.getWidth() );
            pilePic.setVisible( true );
            gamecontentPanel.add( pilePic );
            gamecontentPanel.revalidate();
            gamecontentPanel.repaint();
            refresh( gamecontentPanel );
        }
        else
        {
            String color = "";
            String card = "";
            if ( str.substring( 0, 1 ).equals( "w" ) )
            {
                card = "wild";
            }
            else if ( str.substring( 0, 1 ).equals( "s" ) )
            {
                card = "skip";
                color = str.substring( 1 );
            }
            else if ( str.substring( 0, 1 ).equals( "r" ) )
            {
                card = "reverse";
                color = str.substring( 1 );
            }
            else if ( str.substring( 0, 2 ).equals( "p2" ) )
            {
                card = "p2";
                color = str.substring( 2 );
            }
            else if ( str.substring( 0, 2 ).equals( "p4" ) )
            {
                card = "wildp4";
            }
            BufferedImage cardimage = null;
            try
            {
                cardimage = ImageIO.read( new File( color + card + ".png" ) );
                // color + num + ".png"
            }
            catch ( Exception e )
            {
                System.out.println( e );
                System.out.println( color + card + ".png" );
                System.exit( 0 );
            }
            pilePic = new JLabel(
                new ImageIcon( getScaledImage( new ImageIcon( cardimage ).getImage(),
                    100,
                    cardimage.getHeight() * 100 / cardimage.getWidth() ) ) );
            pilePic.setBounds( 360, 320, 100, cardimage.getHeight() * 100 / cardimage.getWidth() );
            pilePic.setVisible( true );
            gamecontentPanel.add( pilePic );
            gamecontentPanel.revalidate();
            gamecontentPanel.repaint();
            refresh( gamecontentPanel );
        }

    }


    /*
     * * BufferedImage bi = ImageIO.read( new File( "blue0.png" ) ); JLabel
     * picLabel = new JLabel( new ImageIcon( getScaledImage( new ImageIcon( bi
     * ).getImage(), 100, 100 ) ) ); picLabel.addMouseListener( new
     * MouseAdapter() { public void mouseClicked( MouseEvent e ) {
     * System.out.println( "CLick" );
     * 
     * }
     * 
     * 
     * public void mouseEntered( MouseEvent e ) { System.out.println( "Enter" );
     * mainPanel.remove( picLabel ); mainPanel.add( picLabel, BorderLayout.SOUTH
     * ); refresh( mainPanel );
     * 
     * }
     * 
     * 
     * public void mouseExited( MouseEvent e ) { System.out.println( "Exit" );
     * mainPanel.remove( picLabel ); mainPanel.add( picLabel, BorderLayout.NORTH
     * ); refresh( mainPanel );
     * 
     * }
     * 
     * } ); mainPanel.add( picLabel, BorderLayout.NORTH );
     * 
     * }
     */
    /**
     * 
     * deletes gamescreen and draws out engScreen
     * 
     * @param id
     */
    public void drawEndGame( int id )
    {
        remove( gamecontentPanel );
        JPanel endGame = new JPanel();
        JLabel winner;
        if ( id == p.getIDNum() )
        {
            winner = new JLabel( "You Won!" );
        }
        else
        {
            winner = new JLabel( "Player " + ( id + 1 ) + " Won!" );
        }
        winner.setFont( new Font( "sansserif", Font.BOLD, 40 ) );
        endGame.add( winner );
        add( endGame, BorderLayout.CENTER );
        gamecontentPanel.revalidate();
        gamecontentPanel.repaint();
        refresh( gamecontentPanel );
        try
        {
            Thread.sleep( 10000 );
        }
        catch ( Exception e )
        {
        }
        System.exit( 0 );
    }


    /**
     * 
     * to be called after a wild or p4 card is played, created four JButtons and
     * when an answer is processed deletes them
     * 
     * @return color player wants
     */

    public String getColor()
    {
        JButton g = new JButton( "Green" );
        g.setBackground( Color.GREEN );
        JButton r = new JButton( "Red" );
        r.setBackground( Color.RED );
        JButton b = new JButton( "Blue" );
        b.setBackground( Color.BLUE );
        JButton y = new JButton( "Yellow" );
        y.setBackground( Color.YELLOW );
        g.addActionListener( new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                final String c = "green";
                color = c;
            }

        } );
        r.addActionListener( new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                final String c = "red";
                color = c;
            }

        } );
        b.addActionListener( new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                final String c = "blue";
                color = c;
            }

        } );
        y.addActionListener( new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                final String c = "yellow";
                color = c;
            }

        } );
        g.setBounds( 80, 30, 150, 100 );
        r.setBounds( 310, 30, 150, 100 );
        b.setBounds( 540, 30, 150, 100 );
        y.setBounds( 770, 30, 150, 100 );
        gamecontentPanel.add( g );
        gamecontentPanel.add( r );
        gamecontentPanel.add( b );
        gamecontentPanel.add( y );
        gamecontentPanel.revalidate();
        gamecontentPanel.repaint();
        refresh( gamecontentPanel );
        while ( color == null )
        {
            try
            {
                Thread.sleep( 50 );
            }
            catch ( InterruptedException e1 )
            {
            }
        }
        String c = color;
        color = null;
        gamecontentPanel.remove( g );
        gamecontentPanel.remove( r );
        gamecontentPanel.remove( b );
        gamecontentPanel.remove( y );
        gamecontentPanel.revalidate();
        gamecontentPanel.repaint();
        refresh( gamecontentPanel );
        return c;
    }


    /**
     * 
     * freezes the current thread until player inputs a card. checks cardPlayed
     * until it is true, then returns cardPos.
     * 
     * @return cardpos
     */
    public int getDraw()
    {
        cardPlayed = false;
        while ( true )
        {
            printConsole( "Your Turn!" );
            refresh( gamecontentPanel );
            try
            {
                Thread.sleep( 100 );
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
            if ( cardPlayed == true )
            {
                return cardPos;
            }
        }
    }


    /**
     * 
     * Scales an image down or up
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
        Graphics2D g2d = resizedImg.createGraphics();
        g2d.setRenderingHint( RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        g2d.drawImage( srcImg, 0, 0, w, h, null );
        g2d.dispose();
        return resizedImg;
    }


    /**
     * 
     * waits until player inputs a IP address, then returns it
     * 
     * @return ip address
     */
    public String getIPAddress()
    {

        while ( ip == previp )
        {
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


    /**
     * When ip is wrong, sends a promt that ip is not valid
     */
    public void drawIPFail()
    {

        Component[] comps = titlecontentPanel.getComponents();
        for ( Component c : comps )
        {
            if ( c instanceof JTextField )
            {
                ( (JTextField)c ).setText( "Not a Valid IP: Retype" );
            }
        }
        refresh( titlecontentPanel );

    }


    /**
     * 
     * prints message to the JLabel at top left of screen
     * 
     * @param str
     */
    public void printConsole( String str )
    {
        output.setText( str );
    }


    /**
     * 
     * reloads all of the components. needs to be called after updating image
     * positions
     * 
     * @param mainPanel
     *            panel to refresh
     */
    public void refresh( JComponent mainPanel )
    {
        mainPanel.invalidate();
        mainPanel.validate();
    }


    /*
     * String command = et.getActionCommand(); if ( command.equals(
     * "Deck Please" ) ) { for ( String card : p.getHand() ) { JButton card1 =
     * new JButton( card ); card1.addActionListener( this ); mainPanel.add(
     * card1 );
     * 
     * } } for ( int x = 0; x < p.getHand().size(); x++ ) { if ( command.equals(
     * p.getHand().get( x ) ) ) { if ( command.contains( "p4" ) ) {
     * switchColorPlus4(); } else if ( command.contains( "w" ) ) {
     * switchColor(); } if ( p.isPlayable( x ) ) { p.getClient().sendAction(
     * p.getHand().get( x ), p.getIDNum() ); } }
     * 
     * }
     */
    /*
     * setVisible( true );
     * 
     * add( mainPanel, BorderLayout.NORTH );
     * 
     * BufferedImage bi = ImageIO.read( new File( "blue0.png" ) ); JLabel
     * picLabel = new JLabel( new ImageIcon( getScaledImage( new ImageIcon( bi
     * ).getImage(), 100, 100 ) ) ); picLabel.addMouseListener( new
     * MouseAdapter() { public void mouseClicked( MouseEvent e ) {
     * System.out.println( "CLick" );
     * 
     * }
     * 
     * 
     * public void mouseEntered( MouseEvent e ) { System.out.println( "Enter" );
     * mainPanel.remove( picLabel ); mainPanel.add( picLabel, BorderLayout.SOUTH
     * ); refresh( mainPanel );
     * 
     * }
     * 
     * 
     * public void mouseExited( MouseEvent e ) { System.out.println( "Exit" );
     * mainPanel.remove( picLabel ); mainPanel.add( picLabel, BorderLayout.NORTH
     * ); refresh( mainPanel );
     * 
     * }
     * 
     * } ); mainPanel.add( picLabel, BorderLayout.NORTH );
     * 
     * }
     */
    /**
     * 
     * setter method
     * 
     * @param ip
     *            the ip
     */
    public void setIP( String ip )
    {
        this.ip = ip;
    }


    /**
     * 
     * checks if String is int
     * 
     * @param s
     *            string to check
     * @return if int or not
     */
    public static boolean isInteger( String s )
    {
        try
        {
            Integer.parseInt( s );
            return true;
        }
        catch ( NullPointerException e )
        {
            return false;
        }
        catch ( NumberFormatException e )
        {
            return false;
        }

    }

}