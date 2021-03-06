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

public class FractalScene5 extends Scene{
	//int l = WindowDims.;
	int layers = 2;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		//sierpinski(g2d, new Rectangle( Constants.WindowDims.width/3,  Constants.WindowDims.height/3, Constants.WindowDims.width/3, Constants.WindowDims.height/3), layers);
		sierpinski(g2d, new int[] {0, Constants.WindowDims.width/2, Constants.WindowDims.width}, new int[] {0, Constants.WindowDims.height, 0}, layers);

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
	public void sierpinski(Graphics2D g2d, int xPoints[], int yPoints[], int layers) {
		if(layers == 1) return;
		Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.RED);
		g2d.fill(p);
		
		Polygon core = triangle((xPoints[0] + xPoints[1])/2, (yPoints[0] + yPoints[1])/2, 
				(xPoints[1] + xPoints[2])/2, (yPoints[0] + yPoints[1])/2, (xPoints[2] + xPoints[0])/2, (yPoints[2] + yPoints[0])/2);
		g2d.setColor(Color.BLACK);
		g2d.fill(core);
		//g2d.setColor(Color.BLUE);
		//g2d.draw(q);
		layers--;
		
		Polygon t1 = triangle((xPoints[0]+xPoints[1])/2, (yPoints[0]+yPoints[1])/2, 
				(xPoints[0] + xPoints[2])/2, yPoints[1], (xPoints[1] + xPoints[2])/2, (yPoints[1] + yPoints[2])/2);
		
		Polygon t2 = triangle(xPoints[0], yPoints[0], (xPoints[0] + xPoints[1])/2, (yPoints[0]+yPoints[1])/2,
				(xPoints[0] + xPoints[2])/2, yPoints[0]);
		
		Polygon t3 = triangle((xPoints[0] + xPoints[2])/2, yPoints[0], (xPoints[1] + xPoints[2])/2, (yPoints[1] + yPoints[2])/2, xPoints[2], yPoints[2]);
		
		
		sierpinski(g2d, t1.xpoints, t1.ypoints, layers);
		sierpinski(g2d, t2.xpoints, t2.ypoints, layers);
		sierpinski(g2d, t3.xpoints, t3.ypoints, layers);


	//	sierpinski(g2d, ,layers)
		/*
		int w = r.width/3;
		int h = r.height/3;
		for(int i = 0; i < 9; i++) {
			//System.out.println(i);
			sierpinski(g2d, new Rectangle((i%3)*w + r.x, (i/3) * h + r.y, w, h), layers);
		}*/
		

	}
	
	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		if(Input.keysPressed[32]) isDone = true;

		delay(2000);
		layers++;
		if(layers == 8) isDone = true;
		
	}
}
