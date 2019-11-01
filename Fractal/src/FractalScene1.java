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

public class FractalScene1 extends Scene{
	int l = 300;
	int layers = 2;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		genSquareFractal(g2d, new Rectangle( Constants.WindowDims.width/2-l/2,  Constants.WindowDims.height/2-l/2, l, l), layers);
		//g2d.fillRect(0, 0, 500, 500);

	}
	
	public void genSquareFractal(Graphics2D g2d, Rectangle r, int layers) {
		if(layers == 1) return;
		g2d.setStroke(new BasicStroke(2));
		
		g2d.setColor(Color.RED);
		g2d.fill(r);
		g2d.setColor(Color.BLUE);
		g2d.draw(r);
		layers--;
		int w = r.width/2;
		int h = r.height/2;
		genSquareFractal(g2d, new Rectangle(r.x-w/2, r.y-h/2, w, h), layers);
		genSquareFractal(g2d, new Rectangle(r.x+r.width-w/2, r.y-h/2, w, h), layers);
		genSquareFractal(g2d, new Rectangle(r.x-w/2, r.y+r.height-h/2, w, h), layers);
		genSquareFractal(g2d, new Rectangle(r.x+r.width-w/2, r.y+r.height-h/2, w, h), layers);

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
