import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;


/**
 * 
 * decides what to display on the GUI note: only inputs from the server are
 * displayed on the gui any decisions made by client are 1. sent to client if
 * valid 2. rejected with a cannot be played message
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
    private boolean connected;

    private Client client;

    private ArrayList<String> hand = new ArrayList<String>();

    private int[] playercards = new int[4];

    private int idNum = 10;

    private int number;

    private int predraw = 0;

    private String color;

    private String pile;

    Scanner in = new Scanner( System.in );

    public boolean checkInput = false;

    public volatile String input;

    private boolean order = true;// true is clockwise


    /*
     * either numbercolor or action s for skip w for wild r for reverse p2 for
     * plus 2 p4 for plus 4
     * 
     */
    public Player()
    {

        // take input
        System.out.println( "Enter IP address" );
        String ip = in.nextLine();
        System.out.println( "Enter port" );
        int port = in.nextInt();
        client = new Client( ip, port, this );
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


    public void takeInput( String serverInput )
    {

        if ( isInteger( serverInput.substring( 0, 1 ) ) )
        {// assign id num
            int playerid = Integer.parseInt( serverInput.substring( 0, 1 ) );
            if ( serverInput.substring( 1, 2 ).equals( "u" ) )
            {// uno
             // Display uno on screen with player num
                System.out.println( serverInput.substring( 0, 1 ) + " Uno" );
            }
            else if ( serverInput.substring( 1, 2 ).equals( "a" ) )
            {// "AUTO", or your turn has been automatic due to no playable
             // cards
                System.out.println(
                    "Due to having no playable cards, you draw until you have a playable card and play it." );
            }
            else if ( serverInput.substring( 1, 2 ).equals( "d" ) )
            {// draw
                if ( playerid == idNum )
                {
                    draw( serverInput.substring( 2 ) );
                    if ( !( predraw < 28 ) )
                    {
                        playercards[playerid]++;
                        System.out
                            .println( "You draw, with " + playercards[playerid] + " now in hand." );
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
                        System.out.println( "Player " + ( playerid + 1 ) + " draws, with "
                            + playercards[playerid] + " now in hand." );
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
                    System.out
                        .println( "You play, with " + playercards[playerid] + " now in hand." );
                }
                else
                {
                    System.out.println( "Player " + ( playerid + 1 ) + " plays, with "
                        + playercards[playerid] + " now in hand." );
                }
            }
            else if ( serverInput.substring( 1, 2 ).equals( "t" ) )
            {
                if ( playerid == idNum )
                {
                    turn();
                }
                else
                {
                    System.out.println( "Player " + ( playerid + 1 ) + "'s turn." );
                }
            }
        }
        else if ( serverInput.substring( 0, 1 ).equals( "r" ) )// reverse
        {
            order = !order;
            System.out.println( "Reverse" );
        }
        else if ( serverInput.substring( 0, 1 ).equals( "s" ) )// starting card
        {
            pile = serverInput.substring( 1 );
            // display start game
            // set up game gui
            // update
            System.out.println( "Game Start! Starting Card: " + serverInput.substring( 1 ) );
        }
        else if ( serverInput.substring( 0, 1 ).equals( "c" ) )// change color
        {
            color = serverInput.substring( 1 );
            // update
            System.out.println( "Color: " + color );
        }
        else if ( serverInput.substring( 0, 1 ).equals( "n" ) )// change number
        {
            number = Integer.parseInt( serverInput.substring( 1, 2 ) );
            // update
            System.out.println( "Number: " + number );
        }
        else if ( serverInput.substring( 0, 1 ).equals( "i" ) )// assign id
        {
            if ( idNum == 10 )
            {
                idNum = Integer.parseInt( serverInput.substring( 1, 2 ) );
                System.out.println( "You are: Player " + ( idNum + 1 ) + "!" );
            }

        }

    }


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


    public boolean playCard( int index )
    {
        if ( isPlayable( index ) )
        {
            client.sendAction( "p" + index, idNum );
            hand.remove( index );// TODO UPDATE gui
            System.out.println( "Played" );
            return true;
        }
        return false;
    }


    public boolean isPlayable( int index )
    {
        if ( isInteger( hand.get( index ).substring( 0, 1 ) ) )
        {
            if ( Integer.parseInt( hand.get( index ).substring( 0, 1 ) ) == number
                || hand.get( index ).substring( 1 ).equals( color ) )// if
                                                                     // number
                                                                     // matches
                                                                     // current
                                                                     // number
            {
                return true;
            }
        }
        else// TODO is action card
        {
            if ( hand.get( index ).length() > 1
                && hand.get( index ).substring( 0, 2 ).equals( "p2" ) )
            {
                if ( isInteger( pile.substring( 0, 1 ) )
                    && hand.get( index ).substring( 2 ).equals( pile.substring( 1 ) ) )
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
                return true;
            }

        }
        return false;
    }


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


    public void uno()
    {
        client.sendAction( "uno", idNum );
    }


    public void draw( String card )
    {
        hand.add( card );
    }


    public void turn()
    {
        System.out.println( "Your Turn." );
        try
        {
            Thread.sleep( 50 );
        }
        catch ( InterruptedException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.print( "Hand: " );
        if ( hand.size() > 1 )
        {
            System.out.print( ( 1 ) + ":" + hand.get( 0 ) );
            for ( int x = 1; x < hand.size(); x++ )
            {
                System.out.print( ", " + ( x + 1 ) + ":" + hand.get( x ) );
            }
        }
        else
        {
            System.out.print( hand.get( 0 ) );
        }
        System.out.println( "" );
        System.out.println( "Enter card position:" );

        int handpos = in.nextInt() - 1;

        if ( !playCard( handpos ) )
        {
            System.out.println( "Unplayable: pick another card." );
            turn();
            return;
        }
        else
        {
            if ( hand.get( handpos ).equals( "p4" ) || hand.get( handpos ).equals( "wild" ) )
            {
                System.out.println( "Enter Color:" );
                String color = in.nextLine();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public String getLine()
    {
        return "";

    }


    public int getInt()
    {
        return 0;
    }


    public ArrayList<String> getHand()
    {
        return hand;
    }


    public static void main( String[] args )
    {
        Player p = new Player();
    }


    public void setIDNum( int id )
    {
        idNum = id;
    }


    public int getIDNum()
    {
        return idNum;
    }


    public void setConnected( boolean connected )
    {
        this.connected = connected;
    }
}
