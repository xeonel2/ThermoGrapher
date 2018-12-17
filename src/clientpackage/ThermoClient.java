package clientpackage;

import commonlibs.Client;

public class ThermoClient {
	public static void main(String[] args) {
		if(args.length==2){
    	
    		Client clientApp = new Client(args[0],Integer.parseInt(args[1]));
    		clientApp.getDate();
		} else {
			System.out.println("Invalid Args..");
		}
	}
}
