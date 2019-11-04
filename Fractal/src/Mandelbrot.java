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

public class Mandelbrot extends Scene{
	//int l = WindowDims.;
	int layers = 2;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		mandelbrot(g2d);
	}
	
	/*feed complex number a+bi*/
	public boolean isMandelbrot(Complex c) {
		int n = 0;
		Complex accum = new Complex(0,0 );
		int max = 100;
		double bound = 2.0;
		while(n < max && accum.mag() < bound) {
			accum = accum.square().add(c); 
			n++;
		}
		if(n == max) return false;
		else return true;
	}
	
	public void mandelbrot(Graphics2D g2d) {
		for(int a = 0; a < Constants.WindowDims.width; a++) {
			for(int b = 0; b < Constants.WindowDims.height; b++) {
				double aT = ((double)a/(Constants.WindowDims.width*0.5))-1.0;
				double bT = ((double)b/(Constants.WindowDims.height*0.5))-1.0;

				g2d.setColor(Color.WHITE);
				if(isMandelbrot(new Complex(aT, bT))) g2d.fillRect(a, b, 1, 1);
			}
		}
	}

	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		if(Input.keysPressed[32]) isDone = true;
		//delay(2000);
		layers++;
		//if(layers == 10) isDone = true;
		
	}
}
