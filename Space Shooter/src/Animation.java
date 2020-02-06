import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Animation {
	GameObject[] particles; 
	Point2D[] dxdy;
	double startTime;
	int animation = -1;
	double time = 0;
	Random rn = new Random();
	GameObject s;
	boolean boosted = false;
	boolean isDone = false;
	
	double maxFireDist = 200;
	
	
	public void explosion(double x, double y, Color color) {
		isDone = false;
		animation = 0;
		startTime = time;
		particles = new GameObject[40];
		for(int i = 0 ; i < particles.length; i++) {
			particles[i] = new GameObject(x, y, 6, 6);
			particles[i].color = color; //rand color
			particles[i].speed = 300;
			particles[i].theta = Math.random() * Math.PI*2; 
		}
	}
	
	public void directionalExplosion(double x, double y, double theta, Color color) {
		isDone = false;
		animation = 3;
		startTime = time;
		particles = new GameObject[40];
		for(int i = 0 ; i < particles.length; i++) {
			particles[i] = new GameObject(x, y, 6, 6);
			particles[i].color = color; //rand color
			particles[i].speed = Math.abs(rn.nextGaussian()+1.0) * 100;
			particles[i].theta = theta + rn.nextGaussian() * Math.PI/8;
		}
	}
	
	public void makeFireParticle(int i, boolean init) {
		double xOff = rn.nextGaussian() * 10;
		double yOff = 65;
		//if(s instanceof Boss) yOff = 0;
		//if(s instanceof Boss) xOff -= s.width/2;
		//if(init) yOff+= Math.abs(rn.nextGaussian()) * 60;
		
		Mat o= new Mat(3, 1, new double[] {xOff, -yOff, 1});
		o = o.lmul(Mat.rotationMat3x3(s.theta - Math.PI/2));
			
				// pos.lmul(Mat.rotationMat3x3(s.theta - Math.PI/2));

		Mat pos = new Mat(3, 1, new double[] {s.x ,s.y, 1});
		pos = pos.add(o);
		
		particles[i] = new GameObject(pos.data[0],pos.data[1], 5, 5);
		
		particles[i].color = new Color(255, 0, 0);
		
		if(s instanceof Spaceship && s.speed > 0 && ((Spaceship)s).tmp.getPercent() < 0.5) particles[i].color = new Color(0, 0, 255);
		
		dxdy[i] = new Point2D.Double(xOff * 2.05, Math.min(-500 * Math.abs(rn.nextGaussian()), -200) );
		
	}
	
	
	public void fire(GameObject s) {
		this.s = s;
		animation = 1;
		startTime = time;
		particles = new GameObject[70];
		dxdy = new Point2D[particles.length];
		for(int i = 0 ; i < particles.length; i++) {
			makeFireParticle(i, true);
			//particles[i].speed = 400;
			//particles[i].thet11a = Math.random() * Math.PI*2; 
		}
	}
	
	public void boost(Spaceship s) {
		this.s = s;
		animation = 2;
		startTime = time;
		particles = new GameObject[20];
		for(int i = 0; i < particles.length; i++) {
			
		}
		
	}
	
	public void update(double dt) {
		time+=dt;
		
		if(isDone) return;
		
		if(animation == 0) {
			for(int i = 0; i < particles.length; i++) {
				//particles[i].theta += 0.02;
				particles[i].update(dt);
			}
			if(time-startTime >= 1.5) {
				animation = -1;
			}
		}
		if(animation == 1) {
			for(int i = 0; i < particles.length; i++) {
				double dist = (new Mat(2, 1, new double[] {particles[i].x, particles[i].y})).sub(new Mat(2, 1, new double[] {s.x, s.y})).getMag();
				Mat velo = (new Mat(3, 1, new double[] {dxdy[i].getX(), dxdy[i].getY(), 1})).lmul(Mat.rotationMat3x3(s.theta - Math.PI/2));
				//dxdy[i] = new Point2D.Double(velo.data[0], velo.data[1]);
				
				particles[i].phi = particles[i].theta;
				particles[i].setDxDy(velo.data[0] + s.dx,velo.data[1] + s.dy);
				
				
				int alpha = Math.max( (int) ((1.0 - dist/maxFireDist ) * 255), 20);
				//System.out.println(alpha);
				double p = dist/maxFireDist;
			
				if(particles[i].color.getBlue() == 255) {
					particles[i].color = new Color( 0, 0, 255, Math.abs(alpha));
				} else 
					particles[i].color = new Color( (int)(255), (int)Math.min(p*255, 255), 0, Math.abs(alpha));
				
				
				particles[i].update(dt);
				//System.out.println(s.dx +" , " + s.dy);
				
				if(dist >= maxFireDist) {
					makeFireParticle(i, false);
				}
				if(s.speed > 0) {
					maxFireDist = 200;
					//dxdy[i].setLocation(dxdy[i].x, dxdy[i] +);
				} else {
					maxFireDist = 200;
				}
				//reduce color and transparency as it comes out in y
			}
		}
		if(animation == 2) {
			
		}
		if(animation == 3) {
			for(int i = 0; i < particles.length; i++) {
			//particles[i].theta += 0.02;
				particles[i].update(dt);
				particles[i].color = new Color(particles[i].color.getRed(), particles[i].color.getGreen(), particles[i].color.getBlue()
						,(int)Math.abs(( Math.min(1.5-(time-startTime), 1.0) ) * 255)
						);  //(int)Math.abs(((1.5-(time-startTime)) * 255))
			}
			if(time-startTime >= 1.5) {
				animation = -1;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		if(animation != -1) {
			if(isDone) return;
			boolean done = true;
			for(int i = 0; i < particles.length; i++) {
				try {
					if(particles[i].isVisible() && particles[i].color.getAlpha()>1) done = false;
					particles[i].paintComponent(g);
				} catch(Exception e) {
					System.out.println("NULL PTR: " + i + time +"|" + startTime + " anim " + animation);
				}
			}
			if(done) isDone = true;
		}
	}
	
	
}
