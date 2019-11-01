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

public class FractalScene2 extends Scene{
	int l = 300;
	int layers = 2;
	int thickness = 3;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		/*if(layers % 2 == 1)*/ genLineFractal(g2d, new Rectangle( Constants.WindowDims.width/2-l/2,  Constants.WindowDims.height/2-thickness/2, l, thickness), layers, false);
		//else genLineFractal(g2d, new Rectangle( Constants.WindowDims.width/2-thickness/2,  Constants.WindowDims.height/2-l/2, thickness, l), layers);
	}
	
	public void genLineFractal(Graphics2D g2d, Rectangle r, int layers, boolean flip) {
		if(layers == 1) return;
		g2d.setStroke(new BasicStroke(2));
		
		g2d.setColor(Color.RED);
		g2d.fill(r);
		g2d.setColor(Color.BLUE);
		g2d.draw(r);
		layers--;
		flip = !flip;
		int len = (int)(r.width * (9.6/10.0)); 
		if(flip) {
			genLineFractal(g2d, new Rectangle(r.x-thickness, r.y - len/2, thickness, len), layers,flip);
			genLineFractal(g2d, new Rectangle(r.x+r.width, r.y - len/2, thickness, len), layers,flip);
		} else {
			len = r.height/2;
			genLineFractal(g2d, new Rectangle(r.x-len/2, r.y - thickness, len, thickness), layers,flip);
			genLineFractal(g2d, new Rectangle(r.x-len/2, r.y + r.height, len, thickness), layers,flip);
		}
	}
	
	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		if(Input.keysPressed[32]) isDone = true;

		delay(2000);

		layers++;
		if(layers == 12) isDone = true;
		
	}
}
