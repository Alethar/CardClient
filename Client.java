
// A Java program for a Client
import java.net.*;
import java.util.Scanner;
import java.io.*;


public class Client
{
    // initialize socket and input output streams
    private Socket socket = null;

    private DataOutputStream out = null;

    private int port = 5000;
    // constructor to put ip address and port


    /**
     * constructor creates new serverlistener after connecting to server
     * 
     * @param address
     *            ip to connect to
     * @param p
     *            player
     * @throws Exception
     *             things go wrong
     */
    public Client( String address, Player p ) throws Exception
    {
        int timeout = 100;
        InetSocketAddress iA = new InetSocketAddress( address, port );
        socket = new Socket();
        socket.connect( iA, timeout );
        out = new DataOutputStream( socket.getOutputStream() );
        Thread listenerThread = new Thread( new ServerListener( socket, p ) );
        listenerThread.start();
        p.setConnected( true );

    }


    /**
     * 
     * sends string to server
     * 
     * @param str
     *            to send
     * @param playerID
     *            person sending
     */
    public void sendAction( String str, int playerID )
    {
        try
        {

            out.writeUTF( playerID + str );
        }
        catch ( IOException e )
        {
            System.out.println( "Server connection lost" );
            System.exit( 0 );
        }
    }
    /*
     * public static void main(String args[]) throws IOException {
     * System.out.print( "input server ip" ); Scanner in = new
     * Scanner(System.in); Client client = new Client(in.nextLine(), 5000); }
     */
}
