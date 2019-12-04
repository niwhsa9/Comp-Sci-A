import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Animation {
	GameObject[] particles; 
	double startTime;
	double animation = -1;
	double time = 0;
	
	
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
	}
	
	public void paintComponent(Graphics g) {
		if(animation != -1) {
			for(int i = 0; i < particles.length; i++) {
				particles[i].paintComponent(g);
			}
		}
	}
	
	
}
