import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FractalScene3 extends Scene{
	int l = 300;
	int layers = 10;
	int thickness = 3;
	double theta = 0.5;
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		//genTreeFractal(g2d, new Line(Constants.WindowDims.width/2-thickness/2,  Constants.WindowDims.height, Constants.WindowDims.width/2-thickness/2,  Constants.WindowDims.height/2), layers);
		
		genTreeFractal(g2d, new Line(Constants.WindowDims.width/2-thickness/2,  Constants.WindowDims.height, Constants.WindowDims.width/2-thickness/2, 600), layers);

		//genTreeFractal2(g2d, new Line(Constants.WindowDims.width/2-thickness/2,  Constants.WindowDims.height, Constants.WindowDims.width/2-thickness/2,  600), layers, theta);

		//g2d.fillRect(0, 0, 500, 500);

	}
	
	public void genTreeFractal(Graphics2D g2d, Line line, int layers) {
		if(layers == 1) return;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.RED);
		//g2d.setColor(new Color((int) (Math.random() * 255),(int) (Math.random() * 255),(int) (Math.random() * 255)));

		//g2d.fill(r);
		//g2d.setColor(Color.BLUE);
		g2d.draw(line.getDrawable());
		//Line l1 = new Line(line.end, line.unit().lmul(Mat.rotationMat3x3(theta)).add(line.end));
		Line l1 = new Line(line.end, line.end.add(line.unit().multiply(line.getMag()/1.4).lmul(Mat.rotationMat3x3(theta))));
		Line l2 = new Line(line.end, line.end.add(line.unit().multiply(line.getMag()/1.4).lmul(Mat.rotationMat3x3(-theta))));
		g2d.draw(l1.getDrawable());
		g2d.draw(l2.getDrawable());
		//theta += layers * 0.01;
		layers--;

		genTreeFractal(g2d, l1, layers);
		genTreeFractal(g2d, l2, layers);
		
		
		//Rectangle r1 = new Rectangle()

	}
	
	public void genTreeFractal2(Graphics2D g2d, Line line, int layers, double angle) {
		if(layers == 1) return;
		g2d.setStroke(new BasicStroke(2));
		//g2d.setColor(Color.RED);
		g2d.setColor(new Color((int) (Math.random() * 255),(int) (Math.random() * 255),(int) (Math.random() * 255)));
		//g2d.fill(r);
		//g2d.setColor(Color.BLUE);
		g2d.draw(line.getDrawable());
		//Line l1 = new Line(line.end, line.unit().lmul(Mat.rotationMat3x3(theta)).add(line.end));
		Line l1 = new Line(line.end, line.end.add(line.unit().multiply(line.getMag()/1.2).lmul(Mat.rotationMat3x3(angle))));
		Line l2 = new Line(line.end, line.end.add(line.unit().multiply(line.getMag()/1.2).lmul(Mat.rotationMat3x3(-angle))));
		g2d.draw(l1.getDrawable());
		g2d.draw(l2.getDrawable());
		//theta += layers * 0.01;
		layers--;

		genTreeFractal2(g2d, l1, layers, angle + 0.1);
		genTreeFractal2(g2d, l2, layers, angle + 0.1);
		
		
		//Rectangle r1 = new Rectangle()

	}
	
	
	public void gameEnd() {
	}

	public synchronized void update(double dt) {
		theta += 0.05;
		//layers++;
		//if(layers == 10) isDone = true;
		
	}
}
