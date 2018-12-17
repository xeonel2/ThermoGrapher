import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GraphMaker extends JPanel{
	
	int width, height;
	
	//Need to override paintComponent method to make a canvas like in swing
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        makeGraphLayout(g);
    }
    
    public GraphMaker(int height, int width) {
    	setPreferredSize(new Dimension(height, width));
    	
    	this.width = width;
    	this.height = height;
        this.repaint();
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
    	
    	this.repaint();
    }
    
}
