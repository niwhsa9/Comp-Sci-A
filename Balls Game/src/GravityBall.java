import java.awt.Dimension;

public class GravityBall extends Ball{
	final double ELASTICITY = 0.7;
	public double accY;
	
	GravityBall(Dimension windowDims, Dimension worldDims, double x, double y, double w, double h, double vx, double accY) {
		super(windowDims, worldDims, x, y, w, h);
		// TODO Auto-generated constructor stub
		this.vx = vx;
		this.accY = accY;
	}
	
	
	public void update(double dt) {
	
		boolean collision = true;
		if(ball.getX()+ vx * dt <= 0) vx *= -1;
		else if((ball.getX()+ball.getWidth())+ vx * dt >= windowDims.getWidth()) vx *= -1;
		else if(ball.getY() + vy * dt <= 0) vy*= -1*ELASTICITY;
		else if((ball.getY() + ball.getHeight()) + vy * dt >= windowDims.getHeight()) vy*=-1*ELASTICITY;
		else collision = false;
		if(collision && Math.abs(vx) + Math.abs(vy) > 5) randomColor();
		
		vy += accY * dt;
		if(ball.getY() >= windowDims.getHeight()-ball.getHeight()-2 && vx > 0) vx-=0.2;
		else if(ball.getY() >= windowDims.getHeight()-ball.getHeight()-2) vx+=0.2;
		
		super.update(dt);
		//System.out.println("updating");
	}

}
