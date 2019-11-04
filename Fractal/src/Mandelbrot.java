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
import java.awt.Color.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Mandelbrot extends Scene{
	//int l = WindowDims.;
	int layers = 2;
	double xView = 0;
	double yView = 0;
	double zoom = 1.0;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		mandelbrot(g2d);
	}
	
	/*feed complex number a+bi*/
	public int isMandelbrot(Complex c) {
		int n = 0;
		Complex accum = new Complex(0,0 );
		int max = 100;
		double bound = 2.0;
		while(n < max && accum.mag() < bound) {
			accum = accum.square().add(c); 
			n++;
		}
		return n;
	}
	
	public void mandelbrot(Graphics2D g2d) {
		for(int a = 0; a < Constants.WindowDims.width; a++) {
			for(int b = 0; b < Constants.WindowDims.height; b++) {
				double aT = ((double)a/(Constants.WindowDims.width*0.5*zoom))-1.0 + xView;
				double bT = ((double)b/(Constants.WindowDims.height*0.5*zoom))-1.0 + yView;

				g2d.setColor(Color.getHSBColor(isMandelbrot(new Complex(aT, bT))/10.0f, 1.0f,1.0f)); 
				g2d.fillRect(a, b, 1, 1);
			}
		}
	}

	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		if(Input.keysPressed[32]) isDone = true;
		if(Input.keysPressed[87]) yView-=0.02;
		if(Input.keysPressed[83]) yView+=0.02;
		if(Input.keysPressed[65]) xView-=0.02;
		if(Input.keysPressed[68]) xView+=0.02;

		if(Input.keysPressed[69]) zoom+=0.02;
		if(Input.keysPressed[81]) zoom-=0.02;


		//delay(2000);
		layers++;
		//if(layers == 10) isDone = true;
		
	}
}
