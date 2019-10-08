import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Paddle extends GameObject {
	double knownBallY = 0;
	public double deflectionDir = 0; //set to 0 or math.pi
	
	public void updateKnownBall(double y) {
		knownBallY = y;
	}
	
	public void setDeflectionDir(double d) {
		this.deflectionDir = d;
	}
	
	public Paddle(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.speed = Constants.PaddleVelocityNormal;

	} 
	
	public void update(double dt) {
		super.update(dt);
		if(topY() <= 0) setTopY(0); 
		if(bottomY() >= Constants.WindowDims.height) setBottomY(Constants.WindowDims.height); 

	}
	

}
