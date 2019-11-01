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

public class FractalScene7 extends Scene{
	//int l = WindowDims.;
	int layers = 1;
	int yShift = 250;
	int xShift = 150;
	int yShift2 = -100;

	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		//sierpinski(g2d, new Rectangle( Constants.WindowDims.width/3,  Constants.WindowDims.height/3, Constants.WindowDims.width/3, Constants.WindowDims.height/3), layers);
	//	kochStar(g2d, new Triangle(100,100 + yShift, Constants.WindowDims.width/2, Constants.WindowDims.height-100+ yShift, Constants.WindowDims.width-100,100+ yShift), layers);
	//	kochStar(g2d, new Triangle(Constants.WindowDims.width-100,Constants.WindowDims.height - 100+ yShift2, Constants.WindowDims.width/2, 100+ yShift2, 100,Constants.WindowDims.height - 100 + yShift2), layers);

		//g2d.~(0, 0, 500, 500);
		//kochSeg(g2d, new Line(600, 200, 200, 200), layers);
		kochSeg(g2d, new Line(500 + xShift, 0 + yShift, 0+ xShift, 0 + yShift), layers);
		kochSeg(g2d, new Line(250+ xShift, 500 + yShift, 500+ xShift, 0 + yShift), layers);
		kochSeg(g2d, new Line(0+ xShift, 0 + yShift,250+ xShift, 500 + yShift), layers);


	}
	
	//public double midpoint() {
	//	return 
	//}
	
	public Polygon triangle(int x0, int y0, int x1, int y1, int x2, int y2) {
		return new Polygon(new int[] {x0, x1, x2}, new int[] {y0, y1, y2}, 3);
	}
	
	//public Polygon 
	
	//carpet, gasket, cantor set , koch star 
	/*
	public void kochStar(Graphics2D g2d, Triangle p, int layers) {
		if(layers == 1) return;
		//Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.RED);
		
		//g2d.setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
		g2d.fill(p.getDrawable());
		g2d.draw(p.getDrawable());
		
		Mat p0 = (p.seg[0].unit().multiply(p.seg[0].getMag()/3.0)).add(p.seg[0].start);
		Mat p2 = (p.seg[0].unit().multiply(p.seg[0].getMag() * 2.0/3.0)).add(p.seg[0].start);
		Mat p1 = (p.seg[0].perpUnit().multiply(p.seg[0].getMag()/3.0)).add(p.seg[0].start).add(p.seg[0].unit().multiply(p.seg[0].getMag()/2.0));
		layers --;
		Triangle t1 = new Triangle(p0, p1, p2);
		kochStar(g2d, t1,layers);
		
		p0 = (p.seg[1].unit().multiply(p.seg[1].getMag()/3.0)).add(p.seg[1].start);
		p2 = (p.seg[1].unit().multiply(p.seg[1].getMag() * 2.0/3.0)).add(p.seg[1].start);
		p1 = (p.seg[1].perpUnit().multiply(p.seg[1].getMag()/3.0)).add(p.seg[1].start).add(p.seg[1].unit().multiply(p.seg[1].getMag()/2.0));
		Triangle t2 = new Triangle(p0, p1, p2);
		kochStar(g2d, t2,layers);
		
		p0 = (p.seg[2].unit().multiply(p.seg[2].getMag()/3.0)).add(p.seg[2].start);
		p2 = (p.seg[2].unit().multiply(p.seg[2].getMag() * 2.0/3.0)).add(p.seg[2].start);
		p1 = (p.seg[2].perpUnit().multiply(p.seg[2].getMag()/3.0)).add(p.seg[2].start).add(p.seg[2].unit().multiply(p.seg[2].getMag()/2.0));
		Triangle t3 = new Triangle(p0, p1, p2);
		kochStar(g2d, t3,layers);
		/*
		p0 = (p.seg[2].unit().multiply(p.seg[2].getMag()/3.0)).add(p.seg[2].start);
		p2 = (p.seg[2].unit().multiply(p.seg[2].getMag() * 2.0/3.0)).add(p.seg[2].start);
		p1 = (p.seg[2].perpUnit().multiply(p.seg[2].getMag()/3.0)).add(p.seg[2].start).add(p.seg[2].unit().multiply(p.seg[2].getMag()/2.0));
		Triangle t3 = new Triangle(p0, p1, p2);
		kochStar(g2d, t3,layers);
		
		
	
		

	}  */
	
	public void kochSeg(Graphics2D g2d, Line l, int layers) {
		if(layers == 1) {
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.RED);
			g2d.draw(l.getDrawable());
			return;
		}
		
		Mat p0 = (l.unit().multiply(l.getMag()/3.0)).add(l.start);
		Mat p2 = (l.unit().multiply(l.getMag() * 2.0/3.0)).add(l.start);
		Mat p1 = (l.perpUnit().multiply(l.getMag()/3.0)).add(l.start).add(l.unit().multiply(l.getMag()/2.0));
		layers --;
		kochSeg(g2d, new Line(l.start, p0),layers);
		kochSeg(g2d, new Line(p0, p1),layers);
		kochSeg(g2d, new Line(p1, p2),layers);
		kochSeg(g2d, new Line(p2, l.end),layers);


		//kochSeg(g2d, )
		//g2d.draw(l);
		
	}
	
	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		super.update(dt);
		//layers++;
		delay(2000);
		layers++;
		if(layers == 8) isDone = true;
		
		
	}
}
