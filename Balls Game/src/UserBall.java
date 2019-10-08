import java.awt.Dimension;

public class UserBall extends Ball{
	double vMax;
	UserBall(Dimension windowDims, Dimension worldDims, double x, double y, double w, double h, double vMax) {
		super(windowDims, worldDims, x, y, w, h);
		this.vMax = vMax;
	}
	
	public void update(double dt) {
		//System.out.println(Input.keysP);
		if(Input.keysPressed[38]) vy = -vMax;
		else if(Input.keysPressed[40]) vy = vMax;
		else vy = 0;
		
		if(Input.keysPressed[37]) vx = -vMax;
		else if(Input.keysPressed[39]) vx = vMax;
		else vx = 0;
		
		boolean xCollision = true;
		boolean yCollision = true;
		int prevXSign = 1;
		int prevYSign = 1;
		
		if(ball.getX()+ vx * dt <= 0) vx = 0;
		else if((ball.getX()+ball.getWidth())+ vx * dt >= windowDims.getWidth()) vx = 0;
		else xCollision = false;
		
		if(ball.getY() + vy * dt <= 0) vy = 0;
		else if((ball.getY() + ball.getHeight()) + vy * dt >= windowDims.getHeight()) vy = 0;
		else yCollision = false;
		
		//if((xCollision || yCollision)) randomColor();//&& ((prevXSign/vx < 0) || (prevYSign/vy < 0))) randomColor();
		
		super.update(dt);
		prevXSign = (int) (vx/Math.abs(vx));
		prevYSign = (int) (vy/Math.abs(vy));
		//System.out.println("updating");
	}
}
