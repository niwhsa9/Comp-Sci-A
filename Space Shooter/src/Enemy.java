import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy extends GameObject {
	int level = 1;
	boolean isAlive = true;
	double prevMissile = 0;
	double prevBullet = 0;
	Missile m;
	ArrayList<GameObject> bullets = new ArrayList<GameObject>();
	BufferedImage img;
	double startX;
	int health = 10;
	int maxHealth = 10;
	
	Enemy(double x, double y, double w, double h, int level, int health) {
		super(x, y, w, h);
		this.level = level;
		this.color = Color.MAGENTA;
		startX = x;
		this.health = health;
		this.maxHealth = health;
		// TODO Auto-generated constructor stub
		try {
			Random rn = new Random();
			int r= rn.nextInt(3);
			img = ImageIO.read(new File("images/alien" + r+".png"));
			if(r == 0) color = Color.green;
			if(r == 1) color = Color.red;
			if(r == 2) color = Color.red;

		}
		catch(Exception e) {
			System.out.println("failed to load");
		}
	}
	double t = 0;
	double q = 0;
	
	boolean enterFlag = false;
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(isAlive)	{
			//super.paintComponent(g);
			
			
			
			
			AffineTransform transform = new AffineTransform();
			transform.rotate(phi, centerX(), centerY());
			AffineTransform original = g2d.getTransform();
			g2d.transform(transform);
			g2d.drawImage(img, (int)x, (int)y, (int)width, (int)height, null);
			g2d.setTransform(original);
			
			g2d.setColor(Color.red);
			
			double barWidth = ((double)health/(double)maxHealth) * width;
			g2d.fillRect((int)x, (int)(y+height+5), (int)barWidth, 7);
			g2d.setColor(Color.white);
			g2d.drawRect((int)x, (int)(y+height+5), (int)width, 7);
		}
		if(m != null) m.paintComponent(g);
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paintComponent(g);
		}
		
		

		
	}

	public void missileRoutine(double t) {
		if(t - prevMissile > 6.5 && (m == null || m.isDone)) {
			prevMissile = t;
			m = new Missile(x, y, 20, 5, TestScene.ship);
		}
	}
	public void bulletRoutine(double t) {
		if(t - prevBullet > 1.0) {
			prevBullet = t;
			GameObject bullet = new GameObject(x, y, 10, 5);
			bullet.theta = Math.atan2(TestScene.ship.y - y, TestScene.ship.x - x) + (Math.random()-0.5)*Math.PI/4;
			bullet.speed = 300;
			bullet.phi = bullet.theta;
			bullet.color = Color.yellow;
			bullets.add(bullet);
		}
	}
	
	public void update(double dt) {
		super.update(dt);
		if(m != null) m.update(dt);
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(dt);
			if(TestScene.ship.getPolygon(0).intersects(bullets.get(i).hitbox)) {
				TestScene.ship.hurt(1);
				bullets.remove(i);
				
				i--;
			
			}
			if(!bullets.get(i).isVisible()) {
				bullets.remove(i);
				i--;
			}
		}
		
		if(isAlive) {
		if(level == 1) {
			if(y < 200 && enterFlag == false) {
				theta = Math.PI/4;
				speed = 100;
				q+=dt;
				
			}
			else { 
				enterFlag = true;
				//super.setDxDy(300*Math.sin(t), 300*Math.cos(t));
				super.setDxDy(400*Math.sin(2*t)*Math.cos(t), 400*Math.sin(2*t)*Math.sin(t));
				t+=dt;

				missileRoutine(t);
				bulletRoutine(t);
				

			}
		}  else if(level == 2){
			if(y < 300 && enterFlag == false) {
				if(startX < Constants.WindowDims.width/2) theta = Math.PI/3;
				else theta = Math.PI - Math.PI/6;
				speed = 100;
				q+=dt;
				bulletRoutine(q);
				
			}
			else { 
				enterFlag = true;
				//super.setDxDy(300*Math.sin(t), 300*Math.cos(t));
				super.setDxDy(200*Math.sin(t), 200*Math.cos(t));

				t+=dt;
				
				missileRoutine(t);
				bulletRoutine(t);

			}
		} else if (level == 3) {
			
		}
		phi = theta + Math.PI/2; 
		}
	}
	
	

}
