import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Paddle extends GameObject {
	double knownBallY = 0;
	double knownBallTheta = 0;
	public double deflectionDir = 0; //set to 0 or math.pi
	
	public void updateKnownBall(double y, double theta) {
		knownBallY = y;
		knownBallTheta = theta;
	}
	
	public void setDeflectionDir(double d) {
		this.deflectionDir = d;
	}
	
	public Paddle(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.speed = 0;//Constants.PaddleVelocityNormal;
		this.activeSpeed = Constants.PaddleVelocityNormal;

	} 
	
	public void reset() {
		setCenterY(Constants.WindowDims.height/2);
	}
	
	public void update(double dt) {
		super.update(dt);
		if(leftX() <= 0) setLeftX(0); 
		if(rightX() >= Constants.WindowDims.width) setRightX(Constants.WindowDims.width); 

	}
	

}
