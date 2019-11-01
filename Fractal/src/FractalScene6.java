import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FractalScene6 extends Scene{
	//int l = WindowDims.;
	int layers = 2;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		//sierpinski(g2d, new Rectangle( Constants.WindowDims.width/3,  Constants.WindowDims.height/3, Constants.WindowDims.width/3, Constants.WindowDims.height/3), layers);
	//	sierpinski(g2d, new int[] {0, Constants.WindowDims.width/2, Constants.WindowDims.width}, new int[] {0, Constants.WindowDims.height, 0}, layers);
		cantorSet(g2d, new Line(0, 30, Constants.WindowDims.width, 30), layers);
		//g2d.fillRect(0, 0, 500, 500);

	}
	
	//public double midpoint() {
	//	return 
	//}
	
	public Polygon triangle(int x0, int y0, int x1, int y1, int x2, int y2) {
		return new Polygon(new int[] {x0, x1, x2}, new int[] {y0, y1, y2}, 3);
	}
	
	//public Polygon 
	
	//carpet, gasket, cantor set , koch star 
	public void cantorSet(Graphics2D g2d, Line line, int layers) {
		if(layers == 1) return;
		
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.RED);
		
		g2d.draw(line.getDrawable());
		layers--;
		
		Line l1 = new Line(line.getSX(), line.getSY() + 20, line.getSX() + (line.getEX()-line.getSX())/3, line.getEY() + 20);
		Line l2 = new Line(line.getSX() +(line.getEX()-line.getSX())*2.0/3.0, line.getSY()+20, line.getEX(), line.getEY() + 20);
		cantorSet(g2d, l1, layers);
		cantorSet(g2d, l2, layers);

	
	}
	
	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		if(Input.keysPressed[32]) isDone = true;
		delay(2000);
		layers++;
		if(layers == 10) isDone = true;
		
	}
}
