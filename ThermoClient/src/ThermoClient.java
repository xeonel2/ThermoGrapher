
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import commonlibs.Client;
import commonlibs.DateTimeService;
import commonlibs.ServerToClientMessage;

public class ThermoClient {
	private double reading = 0;
	private String serverIP;
	private int serverPort;
	private String clientName;
	private static int interval = 10;
	private static ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
	private static ThermoClient tc;
	private static Client clientApp;
	
	private static Runnable rn = new Runnable() {
		public void run() {
			double tvalue = tc.getOnBoardTemperature();
			Date timeOfReading = (new DateTimeService()).getDateAndTime();
    		ServerToClientMessage response = clientApp.sendReading(tvalue, timeOfReading);
    		tc.dealWithResponse(response);
    		tc.flashUserLED();
    		System.out.println("TemperatureValue: " + tvalue + " Time: " + timeOfReading.getTime());
			ses.schedule(rn, interval, TimeUnit.SECONDS);
			
		}
	};
	
	public ThermoClient(String serverIP, int serverPort, String clientName) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.clientName = clientName;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public static void main(String[] args) {
		if(args.length==3){
			
			tc = new ThermoClient(args[0],Integer.parseInt(args[1]), args[2]);
    		clientApp = new Client(tc.getServerIP(),tc.getServerPort(), tc.getClientName());
      		
    		ses.schedule(rn, 0, TimeUnit.SECONDS);
    		
		} else {
			System.out.println("Invalid Args..");
		}
	}
	
    public void dealWithResponse(ServerToClientMessage scm) {
    	switch (scm.getTypeOfMessage()) {
        case "setfreq":
            System.out.println("Setting update Frequency: " + scm.getSampleFrequency());
            interval = scm.getSampleFrequency();
            break;
        case "errormessage":
        	System.out.println("Error message: " + scm.getErrorMessage());
        	break;
        default:
        	System.out.print("Invalid Respones: " + scm.getTypeOfMessage()); 
        }  
    }
	
	public double getReading() {
		return reading;
	}

	public String getServerIP() {
		return serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public int getInterval() {
		return interval;
	}

	public double getOnBoardTemperature() {
			File temperatureFile = new File("/sys/class/thermal/thermal_zone0/temp");
			try {
				Scanner sc = new Scanner(temperatureFile);
				reading = sc.nextDouble();
				sc.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		return reading;
	}
	
	public void flashUserLED() {
		try {
			//This would flash led for 1 second and is not blocking to the program
			String[] cmd = {"/bin/bash","-c","echo rpi| sudo -S ~/flashLED.sh"};
			Runtime.getRuntime().exec(cmd);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
