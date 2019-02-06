import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class GraphMaker extends JPanel{
	private final Color[] lineColors = new Color[] {Color.BLUE, Color.YELLOW, Color.RED, Color.ORANGE, Color.CYAN};
	
	public static LinkedList<Double> values1 = new LinkedList<Double>(); 
    public static LinkedList<Double> values2 = new LinkedList<Double>(); 
    public static LinkedList<Double> values3 = new LinkedList<Double>(); 
    public static LinkedList<Double> values4 = new LinkedList<Double>(); 
    public static LinkedList<Double> values5 = new LinkedList<Double>(); 
    
    public static LinkedList<Double> averageValues = new LinkedList<Double>(); 
	
	private Boolean[] plotvisibility = new Boolean[]{true, true, true, true, true};

	int width, height;

	//Need to override paintComponent method to make a canvas like in swing
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	removeAll();
	
        makeGraphLayout(g);
        
        plotClients(g);
//        this.repaint();
    }
    
    public GraphMaker(int height, int width) {
    	setPreferredSize(new Dimension(height, width));
    	
    	this.width = width;
    	this.height = height;
    }
    
    //Creates a an empty graph which can be used to plot
    public void makeGraphLayout(Graphics g) {
    	//Draw X axis and Y Axis
    	
    	for(int i=40; i<460; i += 20) {
    		g.drawLine(i,490,i,470);
    	}
    	
    	for(int i=40; i<460; i += 20) {
    		g.drawLine(10,i,30,i);
    	}
    	
    	//the box around it
    	g.drawLine(20, 20, 480, 20);
    	g.drawLine(20, 20, 20, 480);
    	g.drawLine(20, 480, 480, 480);
    	g.drawLine(480, 20, 480, 480);
//    	g.drawLine(23,248,0,248);
    
    }
    
    public Graphics prepareForPlot() {
    	Graphics g = this.getGraphics();
    	this.removeAll();
    	this.makeGraphLayout(g);
//    	this.repaint();
		return g;
    }
    
    public void plotClients(Graphics g) {
//		Graphics g = gm.prepareForPlot();
		plotClient(1, values1, g);
		plotClient(2, values2, g);
		plotClient(3, values3, g);
		plotClient(4, values4, g);
		plotClient(5, values5, g);
		plotAverage(g);
		
//		this.repaint();
    }
    
    public void plotClient(int clientNumber,  LinkedList<Double> readings, Graphics g) {
    	
    	if(!plotvisibility[clientNumber - 1]) {
    		System.out.println(clientNumber + ": Not visible");
    		return;}
    	
    	System.out.println("Plotting: " + clientNumber + " Readingsize: " + readings.size());
    	if (readings.size() > 1) {
    		for(int x = readings.size() - 1; x > 0 ; x--) {
    			Double latestreading = readings.get(x)/1000;
    			Double olderreading = readings.get(x-1)/1000;
    			int x1 = x*23;
    			int y1 = latestreading.intValue() * 460 / 100;
    			int x2 = (x-1)*23;
    			int y2 = olderreading.intValue() * 460 / 100;
    			g.setColor(lineColors[clientNumber - 1]);
    			g.drawLine(x1, y1, x2, y2);
    			System.out.println("Drawing lie: " + x1 + ":" + y1 + ":" + x2 + ":" + y2);
    		}
//    		this.repaint();
    	}
    }
    
    public void plotAverage(Graphics g) {
    	g.setColor(Color.BLACK);
		g.drawLine(20, (460 -FindMinValueinDataFrame()), 460, (460 - FindMaxValueinDataFrame()));
    }

	public void setPlotvisibility(int clientNumber, Boolean visibility) {
		this.plotvisibility[clientNumber - 1] = visibility;
	}
	
	public int FindMinValueinDataFrame() {
		//Calculating min among the whole data frame of different lists
		int minvalue = 460;
		int m1 = ((getMin(values1) == -1) ? 460 : getMin(values1));
		minvalue = (m1 < minvalue) ? m1 : minvalue;
		int m2 = ((getMin(values2) == -1) ? 460 : getMin(values2));
		minvalue = (m2 < minvalue) ? m2 : minvalue;
		int m3 = ((getMin(values3) == -1) ? 460 : getMin(values3));
		minvalue = (m3 < minvalue) ? m3 : minvalue;
		int m4 = ((getMin(values4) == -1) ? 460 : getMin(values4));
		minvalue = (m4 < minvalue) ? m4 : minvalue;
		int m5 = ((getMin(values5) == -1) ? 460 : getMin(values5));
		minvalue = (m5 < minvalue) ? m5 : minvalue;			
		return minvalue * 46 /10000;
	}
	
	public int FindMaxValueinDataFrame() {
		//Calculating max among the whole data frame of different lists
		int maxvalue = 0;
		int m1 = ((getMax(values1) == -1) ? 0 : getMax(values1));
		maxvalue = (m1 > maxvalue) ? m1 : maxvalue;
		int m2 = ((getMax(values2) == -1) ? 0 : getMax(values2));
		maxvalue = (m2 > maxvalue) ? m2 : maxvalue;
		int m3 = ((getMax(values3) == -1) ? 0 : getMax(values3));
		maxvalue = (m3 > maxvalue) ? m3 : maxvalue;
		int m4 = ((getMax(values4) == -1) ? 0 : getMax(values4));
		maxvalue = (m4 > maxvalue) ? m4 : maxvalue;
		int m5 = ((getMax(values5) == -1) ? 0 : getMax(values5));
		maxvalue = (m5 > maxvalue) ? m5 : maxvalue;		
		
		return maxvalue * 46 /10000;
	}
	
	public int getMin(LinkedList<Double> values) {
		int minval = 460;
		if(values.size() == 0) {
			return minval;
		}
		
		minval = values.get(0).intValue();
		
		for(int x = 0; x < values.size(); x++) {
			minval = (minval < values.get(x).intValue()) ? minval : values.get(x).intValue();
		}
		return minval;
	}
	
	public int getMax(LinkedList<Double> values) {
		int maxval = 0;
		if(values.size() == 0) {
			return maxval;
		}
		
		maxval = values.get(0).intValue();
		
		for(int x = 0; x < values.size(); x++) {
			maxval = (maxval > values.get(x).intValue()) ? maxval : values.get(x).intValue();
		}
		return maxval;
	}
   
}
