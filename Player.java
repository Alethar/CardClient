import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * 
 * decides what to display on the GUI note: only inputs from the server are
 * displayed on the gui any decisions made by client are 1. sent to client if
 * valid 2. rejected with a cannot be played message decides if cards are valid
 * or not, sends them to Client class to send to server
 * 
 * The logic of the Client program
 *
 * @author Benjamin
 * @version May 28, 2019
 * @author Period: 3
 * @author Assignment: UnoClient
 *
 * @author Sources: none
 */
public class Player
{
    private String ip = "";

    private Client client;

    private ArrayList<String> hand = new ArrayList<String>();

    private int[] playercards = new int[4];

    private int idNum = 10;

    private int number; // current number

    private String color; // current color

    private int predraw = 0; // when predraw below number, will not print out
                             // drawing.

    GUI gui;

    private String pile;

    public boolean checkInput = false;

    public volatile String input;

    private boolean order = true;// true is clockwise

    private boolean connected = false;


    /**
     * constructor that joins a server and initializes GUI
     */
    public Player()
    {
        // String ip = "10.0.1.7"; Testing purposes
        gui = new GUI( this );
        while ( true )
        {
            ip = gui.getIPAddress();
            try
            {
                client = new Client( ip, this );
                break;
            }
            catch ( Exception e )
            {
                gui.drawIPFail();
            }
        }
        while ( true )
        {
            try
            {
                Thread.sleep( 50 );
            }
            catch ( InterruptedException e )
            {
            }
            if ( checkInput )
            {
                takeInput( input.substring( 1 ) );
            }
        }
        // write to gui

    }


    /**
     * 
     * depending on what server inputs, different actions are executed, such as
     * changing the current color, or changing contents of hand
     * 
     * @param serverInput
     *            input to study for actions
     */
    public void takeInput( String serverInput )
    {
        if ( isInteger( serverInput.substring( 0, 1 ) ) )
        {// assign id num
            int playerid = Integer.parseInt( serverInput.substring( 0, 1 ) );
            if ( serverInput.substring( 1, 2 ).equals( "u" ) )
            {// uno
             // Display uno on screen with player num
                gui.printConsole( "Player "
                    + ( Integer.parseInt( serverInput.substring( 0, 1 ) ) + 1 ) + " says UNO" );
            }
            else if ( idNum == Integer.parseInt( serverInput.substring( 0, 1 ) )
                && serverInput.substring( 1, 2 ).equals( "a" ) )
            {// "AUTO", or your turn has been automatic due to no playable
             // cards
                gui.printConsole(
                    "Due to having no playable cards, you draw a card and skip your turn" );
            }
            else if ( serverInput.substring( 1, 2 ).equals( "d" ) )
            {// draw
                if ( playerid == idNum )
                {
                    playercards[playerid]++;
                    draw( serverInput.substring( 2 ) );
                    if ( !( predraw < 28 ) )
                    {

                        System.out.println( "- You draw, with " + hand.size() + " now in hand." );
                        gui.printConsole( "You draw" );
                        gui.drawPlayer( playerid );
                        gui.drawHand();
                        predraw++;
                    }
                    else
                    {
                        predraw++;
                    }

                }

                else
                {
                    // display that player draws
                    playercards[playerid]++;
                    if ( !( predraw < 28 ) )
                    {
                        System.out.println( "- Player " + ( playerid + 1 ) + " draws, with "
                            + playercards[playerid] + " now in hand." );
                        gui.printConsole( "Player " + ( playerid + 1 ) + " draws" );
                        gui.drawPlayer( playerid );
                    }
                    else
                    {
                        predraw++;
                    }

                }

            }

            else if ( serverInput.substring( 1, 2 ).equals( "p" ) )
            {// displays card going onto pile
                playercards[playerid]--;
                if ( playerid == idNum )
                {
                    System.out.println( "- You play " + serverInput.substring( 2 ) + " with "
                        + playercards[playerid] + " now in hand." );
                    pile = serverInput.substring( 2 );
                    gui.drawHand();
                    if(playercards[playerid] == 0) {
                        gui.drawEndGame( idNum );
                    }
                }
                else
                {
                    System.out.println(
                        "Player " + ( playerid + 1 ) + " plays " + serverInput.substring( 2 )
                            + " with " + playercards[playerid] + " now in hand." );
                    pile = serverInput.substring( 2 );
                    if(playercards[playerid] == 0) {
                        gui.drawEndGame( playerid );
                    }
                }
                for ( int x = 0; x < 4; x++ )
                {
                    gui.drawPlayer( x );
                }
                gui.drawPile( pile );
                if ( playercards[playerid] == 0 )
                {
                    gui.drawEndGame( playerid );
                }
            }
            else if ( serverInput.substring( 1, 2 ).equals( "t" ) )
            {
                if ( playerid == idNum )
                {
                    turn();
                    gui.printConsole( "Your turn!" );
                }
                else
                {
                    gui.printConsole( "Player " + ( playerid + 1 ) + "'s turn." );
                }
            }
        }
        else if ( serverInput.substring( 0, 1 ).equals( "r" ) )// reverse
        {
            order = !order;
        }
        else if ( serverInput.substring( 0, 1 ).equals( "s" ) )// starting card
        {
            pile = serverInput.substring( 1 );
            number = Integer.parseInt( serverInput.substring( 1, 2 ) );
            color = serverInput.substring( 2 );
            // display start game
            // set up game gui
            // update
            gui.initDrawGame();
            for ( int x = 0; x < 4; x++ )
            {
                gui.drawPlayer( x );
            }
            gui.drawPile( serverInput.substring( 1 ) );

        }
        else if ( serverInput.substring( 0, 1 ).equals( "k" ) )
        {// skip
            System.out.println( "Turn skipped!" );
        }
        else if ( serverInput.substring( 0, 1 ).equals( "c" ) )// change color
        {
            color = serverInput.substring( 1 );
            // update
            gui.drawColor();
        }
        else if ( serverInput.substring( 0, 1 ).equals( "n" ) )// change number
        {
            number = Integer.parseInt( serverInput.substring( 1, 2 ) );
            // update
            gui.drawNumber();

        }
        else if ( serverInput.substring( 0, 1 ).equals( "i" ) )// assign id
        {
            if ( idNum == 10 )
            {
                idNum = Integer.parseInt( serverInput.substring( 1, 2 ) );
                System.out.println( "You are: Player " + ( idNum + 1 ) + "!" );
                gui.drawConnected();
            }

        }
        else if ( serverInput.substring( 0, 1 ).equals( "e" ) )
        {
            gui.drawEndGame( Integer.parseInt( serverInput.substring( 1 ) ) );
        }

    }


    /**
     * 
     * Checks if string is integer
     * 
     * @param s
     *            string to check
     * @return if string is integer or not
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


    /**
     * 
     * Checks if current card is playable against the current color and number
     * 
     * @param index
     *            index of card to check
     * @return isplayable or not
     */
    public boolean isPlayable( int index )
    {
        if ( isInteger( hand.get( index ).substring( 0, 1 ) ) )
        {
            if ( Integer.parseInt( hand.get( index ).substring( 0, 1 ) ) == number
                || hand.get( index ).substring( 1 ).equals( color ) )
            {
                return true;
            }
        }
        else
        {
            if ( hand.get( index ).substring( 0, 1 ).equals( "w" ) )
            {
                return true;
            }
            else if ( hand.get( index ).length() > 1
                && hand.get( index ).substring( 0, 2 ).equals( "p2" ) )
            {
                if ( hand.get( index ).substring( 2 ).equals( color ) )
                {
                    return true;
                }
            }
            else if ( hand.get( index ).length() > 1
                && hand.get( index ).substring( 0, 2 ).equals( "p4" ) )
            {
                if ( isP4Playable() )
                {
                    return true;
                }
            }
            else
            {
                if ( hand.get( index ).substring( 1 ).equals( color ) )
                {
                    return true;
                }
            }

        }
        return false;
    }


    /**
     * 
     * checks if all other non-p4 cards are playable, are returns false if so
     * 
     * @return if p4 is playable
     */
    public boolean isP4Playable()
    {
        for ( int x = 0; x < hand.size(); x++ )
        {
            if ( !( hand.get( x ).equals( "p4" ) ) && isPlayable( x ) )
            {
                return false;
            }
        }
        return true;
    }


    /**
     * 
     * sends uno to the server
     */
    public void uno()
    {
        client.sendAction( "uno", idNum );
    }


    /**
     * 
     * draws a card and adds to top of hand
     * 
     * @param card
     *            card to add to hand
     */
    public void draw( String card )
    {
        hand.add( card );
    }


    /**
     * 
     * represents a player turn, waits for gui to give a player input/choice
     */
    public void turn()
    {
        try
        {
            Thread.sleep( 50 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }

        int handpos = gui.getDraw();
        String card = hand.get( handpos );
        if ( !isPlayable( handpos ) )
        {
            System.out.println( "Unplayable: pick another card." );
            turn();
            return;
        }
        else
        {
            if ( card.equals( "p4" ) || card.substring( 0, 1 ).equals( "w" ) )
            {
                System.out.println( "Enter Color:" );
                String color = gui.getColor();
                hand.remove( handpos );
                client.sendAction( "p" + handpos + color, idNum );
            }
            else
            {
                hand.remove( handpos );
                client.sendAction( "p" + handpos, idNum );
            }
        }
        try
        {
            Thread.sleep( 50 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        if(getHand().size() == 0) {
            gui.drawEndGame( idNum );
        }
    }


    /**
     * 
     * accessor method
     * 
     * @return hand
     */
    public ArrayList<String> getHand()
    {
        return hand;
    }


    /**
     * 
     * main class, initializes a new player object
     * 
     * @param args
     *            not used
     */
    public static void main( String[] args )
    {
        Player p = new Player();
    }


    /**
     * 
     * setter method
     * 
     * @param id
     *            to set idnum to
     */
    public void setIDNum( int id )
    {
        idNum = id;
    }


    /**
     * 
     * accessor method
     * 
     * @return idnum
     */
    public int getIDNum()
    {
        return idNum;
    }


    /**
     * 
     * accessor method
     * 
     * @return client
     */
    public Client getClient()
    {
        return client;
    }


    /**
     * 
     * accessor method
     * 
     * @return player cards
     */
    public int[] getPlayerHands()
    {
        return playercards;
    }


    /**
     * 
     * accessor method
     * 
     * @return current color
     */
    public String getColor()
    {
        return color;
    }


    /**
     * 
     * accessor method
     * 
     * @return current number
     */
    public int getNumber()
    {
        return number;
    }


    /**
     * 
     * setter
     * 
     * @param b
     *            to set connected to
     */
    public void setConnected( boolean b )
    {
        connected = true;

    }
}
