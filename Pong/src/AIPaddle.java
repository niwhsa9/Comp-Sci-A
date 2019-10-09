import java.awt.geom.Dimension2D;

public class AIPaddle extends Paddle {
	public static final double trackError = 10;
	
	public AIPaddle(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.speed = 0;
		this.deflectionDir = Math.PI;
	}
	

	
	public void update(double dt) {
		//System.out.println(this.x);
		super.update(dt);
		
		
		if(Math.abs(knownBallY-centerY()) <= trackError || Math.cos(knownBallTheta) * Math.cos(deflectionDir) > 0) speed = 0;
		else speed = Constants.BallVelocityNormal;
				
		if(knownBallY > centerY()) theta = Math.PI/2.0;
		else if(knownBallY <= centerY()) theta = 3.0*Math.PI/2.0;
	}

}
