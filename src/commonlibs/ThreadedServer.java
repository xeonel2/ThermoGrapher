/* The Date Server Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 */

package commonlibs;


import java.net.*;
import java.io.*;

public class ThreadedServer
{
	private int portNumber = 5050;
	boolean listening = false;
	ServerSocket serverSocket;
	
	public ThreadedServer(int port) {
		
		this.portNumber = port;
		this.listening = true;
        this.serverSocket = null;
	}
	
	public void startListening() {
        
        // Set up the Server Socket
        try 
        {
            this.serverSocket = new ServerSocket(portNumber);
            System.out.println("New Server has started listening on port: " + portNumber );
        } 
        catch (IOException e) 
        {
            System.out.println("Cannot listen on port: " + portNumber + ", Exception: " + e);
//            System.exit(1);
        }
        
        // Server is now listening for connections or would not get to this point
        while (listening) // almost infinite loop - loop once for each client request
        {
            Socket clientSocket = null;
            try{
            	System.out.println("**. Listening for a connection...");
                clientSocket = serverSocket.accept();
                System.out.println("00. <- Accepted socket connection from a client: ");
                System.out.println("    <- with address: " + clientSocket.getInetAddress().toString());
                System.out.println("    <- and port number: " + clientSocket.getPort());
            } 
            catch (IOException e){
                System.out.println("XX. Accept failed: " + portNumber + e);
                listening = false;   // end the loop - stop listening for further client requests
            }	
            
            ThreadedConnectionHandler con = new ThreadedConnectionHandler(clientSocket);
            con.start(); 
            System.out.println("02. -- Finished communicating with client:" + clientSocket.getInetAddress().toString());
        }
        // Server is no longer listening for client connections - time to shut down.
        try 
        {
            System.out.println("04. -- Closing down the server socket gracefully.");
            serverSocket.close();
        } 
        catch (IOException e) 
        {
            System.err.println("XX. Could not close server socket. " + e.getMessage());
        }
    }

}