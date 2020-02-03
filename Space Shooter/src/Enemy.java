import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Enemy extends GameObject {
	int level = 1;
	boolean isAlive = true;
	double prevMissile = 0;
	Missile m;
	ArrayList<GameObject> bullets = new ArrayList<GameObject>();

	
	Enemy(double x, double y, double w, double h, int level) {
		super(x, y, w, h);
		this.level = level;
		this.color = Color.MAGENTA;
		// TODO Auto-generated constructor stub
	}
	double t = 0;
	
	boolean enterFlag = false;
	
	public void paintComponent(Graphics g) {
		
		if(isAlive)	super.paintComponent(g);
		if(m != null) m.paintComponent(g);
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paintComponent(g);
		}
	}

	
	public void update(double dt) {
		super.update(dt);
		if(m != null) m.update(dt);
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(dt);
			if(TestScene.ship.getPolygon(0).intersects(bullets.get(i).hitbox)) {
				TestScene.ship.hurt();
			
			}
		}
		
		if(isAlive) {
		if(level == 1) {
			if(y < 200 && enterFlag == false) {
				theta = Math.PI/4;
				speed = 100;
				
			}
			else { 
				enterFlag = true;
				//super.setDxDy(300*Math.sin(t), 300*Math.cos(t));
				super.setDxDy(400*Math.sin(2*t)*Math.cos(t), 400*Math.sin(2*t)*Math.sin(t));

				t+=dt;
				if(t - prevMissile > 3.5 && (m == null || m.isDone)) {
					prevMissile = t;
					m = new Missile(x, y, 20, 5, TestScene.ship);
				}

			}
		}  else {
			if(y < 200 && enterFlag == false) {
				theta = Math.PI/4;
				speed = 100;
				
			}
			else { 
				enterFlag = true;
				//super.setDxDy(300*Math.sin(t), 300*Math.cos(t));
				super.setDxDy(200*Math.sin(t), 200*Math.cos(t));

				t+=dt;
				
				if(t - prevMissile > 3.5 && (m == null || m.isDone)) {
					prevMissile = t;
					m = new Missile(x, y, 20, 5, TestScene.ship);
				}

			}
		}
		phi = theta; 
		}
	}
	
	

}
