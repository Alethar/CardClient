import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class ServerListener extends Thread
{

    private DataInputStream in;

    private volatile boolean stopRun;

    private Player player;


    /**
     * initializes
     * 
     * @param socket
     *            socket to use
     * @param player
     *            player to send msgs to
     */
    public ServerListener( Socket socket, Player player )
    {
        try
        {
            in = new DataInputStream( socket.getInputStream() );
        }

        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.player = player;
    }


    /**
     * constantly checks for messages and sends them to player
     */
    public void run()
    {
        while ( !stopRun )
        {
            try
            {
                String serverInput = in.readUTF();

                int serverAuth = Integer.parseInt( serverInput.substring( 0, 1 ) );
                if ( serverAuth == 4 )
                {
                    if ( player.isInteger( serverInput.substring( 0, 1 ) )
                        && serverInput.substring( 1, 2 ).equals( "t" ) )// when
                                                                        // is
                                                                        // your
                                                                        // turn.
                                                                        // Allows
                                                                        // player
                                                                        // to
                                                                        // take
                                                                        // a
                                                                        // turn
                                                                        // while
                                                                        // server
                                                                        // is
                                                                        // updating
                                                                        // info
                    {
                        player.checkInput = true;
                        player.input = serverInput;
                    }
                    else
                    {
                        player.takeInput( serverInput.substring( 1 ) );
                    }
                }
                else
                {

                }
            }
            catch ( IOException e )
            {
                System.out.println( "Server connection lost" );
                System.exit( 0 );
            }
        }
    }


    /**
     * ends thread
     */
    public void end()
    {
        stopRun = true;
    }

}
