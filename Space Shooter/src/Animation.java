import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;

public class Animation {
	GameObject[] particles; 
	Point2D[] dxdy;
	double startTime;
	double animation = -1;
	double time = 0;
	Random rn = new Random();
	Spaceship s;
	
	double maxFireDist = 200;
	
	
	public void explosion(double x, double y, Color color) {
		animation = 0;
		startTime = time;
		particles = new GameObject[20];
		for(int i = 0 ; i < particles.length; i++) {
			particles[i] = new GameObject(x, y, 6, 6);
			particles[i].color = color; //rand color
			particles[i].speed = 400;
			particles[i].theta = Math.random() * Math.PI*2; 
		}
	}
	
	public void makeFireParticle(int i, boolean init) {
		double xOff = rn.nextGaussian() * 10;
		double yOff = 70;
		if(init) yOff+= Math.abs(rn.nextGaussian()) * 60;
		particles[i] = new GameObject(s.x + xOff, s.y - yOff, 6, 6 );
		particles[i].color = new Color(255, 0, 0);
		
		dxdy[i] = new Point2D.Double(xOff * 2.05, -200);
		
	}
	
	public void fire(Spaceship s) {
		this.s = s;
		animation = 1;
		startTime = time;
		particles = new GameObject[40];
		dxdy = new Point2D[particles.length];
		for(int i = 0 ; i < particles.length; i++) {
			makeFireParticle(i, true);
			//particles[i].speed = 400;
			//particles[i].theta = Math.random() * Math.PI*2; 
		}
	}
	
	
	public void update(double dt) {
		time+=dt;
		
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
				
				
				int alpha = (int) ((1.0 - dist/maxFireDist ) * 255);
				//System.out.println(alpha);
				particles[i].color = new Color(255, 0, 0, Math.abs(alpha));
				
				particles[i].update(dt);
				//System.out.println(s.dx +" , " + s.dy);
				
				if(dist >= maxFireDist) {
					makeFireParticle(i, false);
				}
				
				//reduce color and transparency as it comes out in y
			}
		}
		/*
		if(animation == 1) {
			for(int i = 0; i < particles.length; i++) {
				//particles[i].theta += 0.02;
				particles[i].update(dt);
			}
			
			if(time-startTime >= 1.5) {
				animation = -1;
			}
		} */
	}
	
	public void paintComponent(Graphics g) {
		if(animation != -1) {
			for(int i = 0; i < particles.length; i++) {
				particles[i].paintComponent(g);
			}
		}
	}
	
	
}
