import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends GameObject {
	int level = 1;
	boolean isAlive = true;
	
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
		
	}

	
	public void update(double dt) {
		super.update(dt);
		
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

			}
		}
		phi = theta; 
	}
	
	

}
