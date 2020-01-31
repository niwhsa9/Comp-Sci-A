import java.awt.Graphics;
import java.awt.Graphics2D;

public class Missile extends GameObject {

	Spaceship s;
	double omega;
	
	Missile(double x, double y, double w, double h, Spaceship s) {
		super(x, y, w, h);
		this.s = s;
		// TODO Auto-generated constructor stub
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
	}
	
	public void update(double dt) {
		super.update(dt);
		this.theta += omega * dt;
		this.phi = this.theta;
		double reqTheta = Math.atan2(s.y - y, s.x - x);
		omega = Constants.MISSILE_MAX_OMEGA * omega * (reqTheta-theta);
		
	}

}
