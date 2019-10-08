import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JPanel;

public class Ball extends JPanel{
	Rectangle2D ball;
	double vx, vy;
	Color color;
	Dimension windowDims;
	Dimension worldDims;
	
	Ball(Dimension windowDims, Dimension worldDims, double x, double y, double w, double h) {
		ball = new Rectangle2D.Double(x, y, w, h);
		color = new Color(255, 0, 0);
		this.worldDims = worldDims;
		this.windowDims = windowDims;
	}
	
	void randomColor() {
		Random r = new Random();
		color = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
	}
	
	Rectangle2D translateDims(Rectangle2D object) {	
		return object;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//System.out.println("concur 1");
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 1920, 1080);
		g2d.setColor(color);
		g2d.fill(translateDims(ball));
	}
	
	public void update(double dt) {
		ball.setRect(ball.getX()+vx*dt, ball.getY()+vy*dt, ball.getWidth(), ball.getHeight()); 
	}
	
	
	
	
}
