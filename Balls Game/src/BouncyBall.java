import java.awt.Dimension;
import java.awt.geom.Dimension2D;

public class BouncyBall extends Ball {
	
	BouncyBall(Dimension windowDims, Dimension worldDims, double x, double y, double w, double h, double vx, double vy) {
		super(windowDims, worldDims, x, y, w, h);
		this.vx = vx;
		this.vy = vy;
	}
	
	public void update(double dt) {
	
		boolean collision = true;
		if(ball.getX()+ vx * dt <= 0) vx *= -1;
		else if((ball.getX()+ball.getWidth())+ vx * dt >= windowDims.getWidth()) vx *= -1;
		else if(ball.getY() + vy * dt <= 0) vy*= -1;
		else if((ball.getY() + ball.getHeight()) + vy * dt >= windowDims.getHeight()) vy*=-1;
		else collision = false;
		if(collision) randomColor();
		super.update(dt);
		//System.out.println("updating");
	}
	

}
