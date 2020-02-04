import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Missile extends GameObject {

	Spaceship s;
	double omega;
	boolean aquired = false;
	boolean isDone = false;
	Animation collision = new Animation(); 
	Animation trail = new Animation();
	
	Missile(double x, double y, double w, double h, Spaceship s) {
		super(x, y, w, h);
		this.s = s;
		this.color = Color.cyan;
		// TODO Auto-generated constructor stub
		SoundDriver.playMissile();
	}
	
	public void paintComponent(Graphics g) {
		if(!isDone) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			if(aquired) this.color = Color.red;
		}
		collision.paintComponent(g);
		trail.paintComponent(g);
	}
	
	public void update(double dt) {
		super.update(dt);
		double reqTheta = Math.atan2(s.y - y, s.x - x);
		if(!aquired) {
			this.theta += omega * dt;
			this.phi = this.theta;
			omega = Constants.MISSILE_MAX_OMEGA  * (reqTheta-theta);
			this.speed = Constants.MISSILE_SPEED; //HAVE SPEED FOLLOW A TMP
		}
		if(Math.abs(reqTheta - theta) < 0.2 && new Mat(2, 1, new double[] {x, y}).sub(new Mat(2, 1, new double[] {s.x, s.y})).getMag() <= 200 )   aquired = true;
		
		if(!isDone && s.getPolygon(0).intersects(this.hitbox)) {
			s.health --;
			isDone = true;
			collision.directionalExplosion(x, y, Math.PI+theta, Color.RED);
			//collision.explosion(x, y, new Color(255, 0, 0));
			s.hurt(10);
			
		}
		if(leftX() > Constants.WindowDims.width || rightX() < 0 || topY() > Constants.WindowDims.height || bottomY() < 0) isDone = true;
		collision.update(dt);
		trail.update(dt);
		
	}

}
