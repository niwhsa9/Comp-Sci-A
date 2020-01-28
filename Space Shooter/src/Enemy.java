
public class Enemy extends GameObject {
	int level = 1;
	
	Enemy(double x, double y, double w, double h, int level) {
		super(x, y, w, h);
		this.level = level;
		// TODO Auto-generated constructor stub
	}
	double t = 0;
	
	boolean enterFlag = false;

	
	public void update(double dt) {
		super.update(dt);
		
		if(level == 1) {
			if(y < 500 && enterFlag == false) {
				theta = Math.PI/4;
				speed = 100;
				
			}
			else { 
				enterFlag = true;
				super.setDxDy(100*Math.sin(t), 80*Math.cos(t));
				t+=dt;

			}
		}
		phi = theta; 
	}
	
	

}
