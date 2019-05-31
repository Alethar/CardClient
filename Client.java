// A Java program for a Client 
import java.net.*;
import java.util.Scanner;
import java.io.*; 
  
public class Client 
{ 
    // initialize socket and input output streams 
    private Socket socket  = null; 
    private Scanner  input   = null; 
    private DataOutputStream out     = null; 
    // constructor to put ip address and port 
    
    public Client(String address, int port, Player p) 
    { 
        try
        { 
            socket = new Socket(address, port);
            // takes input from terminal
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException u) 
        { 
            return;
            
        } 
        catch(IOException i) 
        { 
            return;
        } 
        Thread listenerThread = new Thread(new ServerListener(socket, p));
        listenerThread.start();
        p.setConnected(true);

    }
    public void sendAction(String str, int playerID) {
        try
        {
            out.writeUTF( playerID + str );
        }
        catch ( IOException e )
        {
            System.out.println("Server connection lost");
            System.exit(0);
        }
    }
    /*
    public static void main(String args[]) throws IOException 
    { 
        System.out.print( "input server ip" );
        Scanner in = new Scanner(System.in);
        Client client = new Client(in.nextLine(), 5000); 
    } 
    */
} 
