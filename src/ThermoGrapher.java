import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commonlibs.ServerCallbackObject;
import commonlibs.ThreadedServer;

@SuppressWarnings("serial")
public class ThermoGrapher extends JFrame implements ActionListener, WindowListener, ChangeListener{
    public static LinkedList<Double> values1 = new LinkedList<Double>(); 
    public static LinkedList<Double> values2 = new LinkedList<Double>(); 
    public static LinkedList<Double> values3 = new LinkedList<Double>(); 
    public static LinkedList<Double> values4 = new LinkedList<Double>(); 
    public static LinkedList<Double> values5 = new LinkedList<Double>(); 
	
	private static ThreadedServer server1;
	private static ThreadedServer server2;
	private static ThreadedServer server3;
	private static ThreadedServer server4;
	private static ThreadedServer server5;
	
	private static String clientName1;
	private static String clientName2;
	private static String clientName3;
	private static String clientName4;
	private static String clientName5;
	
	private static GraphMaker gm;
	private JSlider sampleRateSlider;
	private static JCheckBox cb1;
	private static JCheckBox cb2;
	private static JCheckBox cb3;
	private static JCheckBox cb4;
	private static JCheckBox cb5;
	
	public static void main(String[] args) {
		new ThermoGrapher();

		Runnable server1Runnable =
			    new Runnable(){
			        public void run(){
			        	server1 = new ThreadedServer(5000);
			        	server1.startListening(new ServerCallbackObject() {
			        		public void noteReading(double temperature, String name) {
			        			values1.addFirst(temperature);
			        			clientName1 = name;
			        			if (values1.size() > 20) {
			        				values1.removeLast();
			        			}
			        			cb1.setText(clientName1);
			        			replot();
			        		}
			        	});
			        }};
	    Runnable server2Runnable =
			    new Runnable(){
	    			public void run(){
	    				server2 = new ThreadedServer(5001);
	    				server2.startListening(new ServerCallbackObject() {
			        		public void noteReading(double temperature, String name) {
			        			values2.addFirst(temperature);
			        			clientName2 = name;
			        			if (values2.size() > 20) {
			        				values2.removeLast();
			        			}
			        			cb2.setText(clientName2);
			        			replot();
			        		}
			        	});
					}};
					    
		Runnable server3Runnable =
				new Runnable(){
					public void run(){
						server3 = new ThreadedServer(5002);
						server3.startListening(new ServerCallbackObject() {
			        		public void noteReading(double temperature, String name) {
			        			values3.addFirst(temperature);
			        			clientName3 = name;
			        			if (values3.size() > 20) {
			        				values3.removeLast();
			        			}
			        			cb3.setText(clientName3);
			        			replot();
			        		}
			        	});
					}};	    
		Runnable server4Runnable =
				new Runnable(){
					public void run(){
						server4 = new ThreadedServer(5003);
						server4.startListening(new ServerCallbackObject() {
			        		public void noteReading(double temperature, String name) {
			        			values4.addFirst(temperature);
			        			clientName4 = name;
			        			if (values4.size() > 20) {
			        				values4.removeLast();
			        			}
			        			cb4.setText(clientName4);
			        			replot();
			        		}
			        	});
					}};
								    
		Runnable server5Runnable =
				new Runnable(){
					public void run(){
						server5 = new ThreadedServer(5004);
						server5.startListening(new ServerCallbackObject() {
			        		public void noteReading(double temperature, String name) {
			        			values5.addFirst(temperature);
			        			clientName5 = name;
			        			if (values5.size() > 20) {
			        				values5.removeLast();
			        			}
			        			cb5.setText(clientName5);
			        			replot();
			        		}
			        	});					
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
	



	
	private int sampleRate = 10;
	
	public static void replot() {
		GraphMaker.values1 = values1;
		GraphMaker.values2 = values2;
		GraphMaker.values3 = values3;
		GraphMaker.values4 = values4;
		GraphMaker.values5 = values5;
		gm.repaint();
	}
	

	public ThermoGrapher() {
		super("ThermoGrapher");
		this.setLayout(new BorderLayout());
		
		JPanel eastborder = new JPanel();
		eastborder.setLayout(new BoxLayout(eastborder, BoxLayout.PAGE_AXIS));
        
        cb1 = new JCheckBox("<Client 1>", true);
        cb2 = new JCheckBox("<Client 2>", true);
        cb3 = new JCheckBox("<Client 3>", true);
        cb4 = new JCheckBox("<Client 4", true);
        cb5 = new JCheckBox("<Client 5>", true);
        eastborder.add(cb1);
        eastborder.add(cb2);
        eastborder.add(cb3);
        eastborder.add(cb4);
        eastborder.add(cb5);
        this.add(eastborder, BorderLayout.EAST);
        
        cb1.addChangeListener(this);
        cb2.addChangeListener(this);
        cb3.addChangeListener(this);
        cb4.addChangeListener(this);
        cb5.addChangeListener(this);
        
       
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
			
			try {server1.setInterval(this.sampleRate);} catch(NullPointerException n){}
			try {server2.setInterval(this.sampleRate);} catch(NullPointerException n){}
			try {server3.setInterval(this.sampleRate);} catch(NullPointerException n){}
			try {server4.setInterval(this.sampleRate);} catch(NullPointerException n){}
			try {server5.setInterval(this.sampleRate);} catch(NullPointerException n){}
		
		} else if (e.getSource().equals(cb1)) {
			gm.setPlotvisibility(1, cb1.isSelected());
		} else if (e.getSource().equals(cb2)) {
			gm.setPlotvisibility(2, cb2.isSelected());
		} else if (e.getSource().equals(cb3)) {
			gm.setPlotvisibility(3, cb3.isSelected());
		} else if (e.getSource().equals(cb4)) {
			gm.setPlotvisibility(4, cb4.isSelected());
		} else if (e.getSource().equals(cb5)) {
			gm.setPlotvisibility(5, cb5.isSelected());
		}
		
	}

	
}


