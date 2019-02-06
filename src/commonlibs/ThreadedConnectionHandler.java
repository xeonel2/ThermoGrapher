/* The Connection Handler Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 */

package commonlibs;

import java.net.*;
import java.util.Date;
import java.io.*;

public class ThreadedConnectionHandler extends Thread
{
    private Socket clientSocket = null;				// Client socket object
    private ObjectInputStream is = null;			// Input stream
    private ObjectOutputStream os = null;			// Output stream
    private DateTimeService theDateService;
    private int interval = 10;
    private ServerCallbackObject callback;
    
	public void setInterval(int interval) {
		this.interval = interval;
	}

	// The constructor for the connection handler
    public ThreadedConnectionHandler(Socket clientSocket, ServerCallbackObject callback) {
        this.clientSocket = clientSocket;
        //Set up a service object to get the current date and time
        theDateService = new DateTimeService();
        this.callback = callback;
        
    }

    // Will eventually be the thread execution method - can't pass the exception back
    public void run() {
         try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
            while (this.readCommand()) {}
         } 
         catch (IOException e) 
         {
        	System.out.println("XX. There was a problem with the Input/Output Communication:");
            e.printStackTrace();
         }
    }

    // Receive and process incoming string commands from client socket 
    private boolean readCommand() {
    	ClientToServerMessage s = null;
        try {
            s = (ClientToServerMessage) is.readObject();
        } 
        catch (Exception e){    // catch a general exception
        	this.closeSocket();
            return false;
        }
        System.out.println("01. <- Received a String object from the client (" + s.getClientName() + ").");
        
        // At this point there is a valid String object
        // invoke the appropriate function based on the command 
        switch (s.getTypeOfMessage()) {
        case "reading":
            System.out.println(s.getTimeOfReading().toString() + " : " + s.getTemperature());
            ServerToClientMessage scm = new ServerToClientMessage("setfreq", this.interval);
            this.callback.noteReading(s.getTemperature(), s.getClientName());
            this.send(scm);
            break;
            
        default:
        	this.sendError("Invalid command: " + s);
        }       

        return true;
    }

    // Use our custom DateTimeService Class to get the date and time
    private void getDate() {	// use the date service to get the date
        Date currentDateTimeText = theDateService.getDateAndTime();
        this.send(currentDateTimeText);
    }

    // Send a generic object back to the client 
    private void send(Object o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client.");
            this.os.writeObject(o);
            this.os.flush();
        } 
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
    }
    
    // Send a pre-formatted error message to the client 
    public void sendError(String message) { 
    	ServerToClientMessage scm = new ServerToClientMessage("errormessage", message);
        this.send("Error:" + message);	//remember a String IS-A Object!
    }
    
    // Close the client socket 
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        } 
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }
}