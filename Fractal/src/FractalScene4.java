import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FractalScene4 extends Scene{
	//int l = WindowDims.;
	int layers = 5;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		//sierpinski(g2d, new Rectangle( Constants.WindowDims.width/3,  Constants.WindowDims.height/3, Constants.WindowDims.width/3, Constants.WindowDims.height/3), layers);
		sierpinski(g2d, new Rectangle( 0,  0, Constants.WindowDims.width, Constants.WindowDims.height), layers);

		//g2d.fillRect(0, 0, 500, 500);

	}
	//carpet, gasket, cantor set , koch star 
	public void sierpinski(Graphics2D g2d, Rectangle r, int layers) {
		if(layers == 1) return;
		g2d.setStroke(new BasicStroke(2));
		Rectangle q = new Rectangle( r.width/3 + r.x, r.height/3 + r.y, r.width/3, r.height/3);
		g2d.setColor(Color.RED);
		g2d.fill(q);
		g2d.setColor(Color.BLUE);
		//g2d.draw(q);
		layers--;
		int w = r.width/3;
		int h = r.height/3;
		for(int i = 0; i < 9; i++) {
			//System.out.println(i);
			sierpinski(g2d, new Rectangle((i%3)*w + r.x, (i/3) * h + r.y, w, h), layers);
		}
		

	}
	
	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		//layers++;
		//if(layers == 10) isDone = true;
		
	}
}
