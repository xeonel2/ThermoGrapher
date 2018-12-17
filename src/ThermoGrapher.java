import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import commonlibs.ThreadedServer;

@SuppressWarnings("serial")
public class ThermoGrapher extends JFrame implements ActionListener, WindowListener, ChangeListener{
	
	public static void main(String[] args) {
		new ThermoGrapher();

		Runnable server1Runnable =
			    new Runnable(){
			        public void run(){
			        	ThreadedServer server = new ThreadedServer(5000);
			        	server.startListening();
			        }};
	    Runnable server2Runnable =
			    new Runnable(){
	    			public void run(){
	    				ThreadedServer server = new ThreadedServer(5001);
	    				server.startListening();
					}};
					    
		Runnable server3Runnable =
				new Runnable(){
					public void run(){
						ThreadedServer server = new ThreadedServer(5002);
						server.startListening();
					}};	    
		Runnable server4Runnable =
				new Runnable(){
					public void run(){
						ThreadedServer server = new ThreadedServer(5003);
						server.startListening();
					}};
								    
		Runnable server5Runnable =
				new Runnable(){
					public void run(){
						ThreadedServer server = new ThreadedServer(5004);
						server.startListening();
					}};	  
					
		Thread thread1 = new Thread(server1Runnable, "Server1");
		Thread thread2 = new Thread(server2Runnable, "Server2");
		Thread thread3 = new Thread(server3Runnable, "Server3");
		Thread thread4 = new Thread(server4Runnable, "Server4");
		Thread thread5 = new Thread(server5Runnable, "Server5");
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
	}
	
	private JButton b;
	private GraphMaker gm;
	private JSlider sampleRateSlider;
	
	private int sampleRate = 10;


	public ThermoGrapher() {
		super("ThermoGrapher");
		this.setLayout(new BorderLayout());
		
		b = new JButton("Test Button");
        this.add(b, BorderLayout.EAST);
        
        gm = new GraphMaker(500,500);
        this.add(gm, BorderLayout.CENTER);
        
        sampleRateSlider = new JSlider(JSlider.HORIZONTAL,
                2, 30, 10);
        sampleRateSlider.setMinorTickSpacing(1);
        sampleRateSlider.setPaintLabels(true);
        this.add(sampleRateSlider, BorderLayout.SOUTH);
        sampleRateSlider.addChangeListener(this);
        
		this.pack();
        this.setVisible(true);
	}
	
	public int getSampleRate() {
		return sampleRate;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0); 
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0); 
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if (e.getSource().equals(sampleRateSlider)){
			//Changing the sample rate to this
			this.sampleRate = sampleRateSlider.getValue();
		}
		// TODO Auto-generated method stub
		
	}

	
}


