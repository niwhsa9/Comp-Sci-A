import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PhaseThroughBall extends Ball{
	double vMax = 0;
	PhaseThroughBall(Dimension windowDims, Dimension worldDims, double x, double y, double w, double h,double vMax) {
		super(windowDims, worldDims, x, y, w, h);
		// TODO Auto-generated constructor stub
		this.vMax = vMax;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString("X: " + (int) ball.getX() + " Y: " + (int) ball.getY(), 700, 700);

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
		
		if(ball.getX()+ball.getWidth()+ vx * dt <= 0) ball.setRect(windowDims.getWidth(), ball.getY(), ball.getWidth(), ball.getHeight());
		else if((ball.getX()+ vx * dt >= windowDims.getWidth())) ball.setRect(0-ball.getWidth(), ball.getY(), ball.getWidth(), ball.getHeight());
		else xCollision = false;
		
		if(ball.getY() + ball.getHeight() + vy * dt <= 0) ball.setRect(ball.getX(), windowDims.getHeight(), ball.getWidth(), ball.getHeight());
		else if((ball.getY() ) + vy * dt >= windowDims.getHeight()) ball.setRect(ball.getX(), 0-ball.getHeight(), ball.getWidth(), ball.getHeight());
		else yCollision = false;
		
		if((xCollision || yCollision)) randomColor();//&& ((prevXSign/vx < 0) || (prevYSign/vy < 0))) randomColor();
		
		super.update(dt);
		prevXSign = (int) (vx/Math.abs(vx));
		prevYSign = (int) (vy/Math.abs(vy));
		//System.out.println("updating");
	}

}
