import java.awt.Dimension;

public class Constants {
	/** Game Constants **/
	public static final Dimension WindowDims = new Dimension(800, 800);
	public static final String WindowName = "Pong";
	public static final int GraphicsPeriod = 10;
	public static final int GamePeriod = 10;

	
	/** Ball Constants **/
	public static final double BallVelocityNormal = 300.0;
	public static final double BallVelocitySlow = 20.0;
	public static final double BallVelocityFast = 200.0;
	public static final double BallDiameter = 10;
	public static final double BallStartX = WindowDims.getWidth()/2 - BallDiameter/2;
	public static final double BallStartY = WindowDims.getHeight()/2 - BallDiameter/2;


	/** Paddle Constants **/
	public static final double PaddleVelocityNormal = 300.0;
	public static final double PaddleVelocitySlow = 2.0;
	public static final double PaddleVelocityFast = 10.0;
	public static final double PaddleWidth = 12;
	public static final double PaddleHeight = 60;
	public static final double PaddleStartX = 50;
	public static final double PaddleStartY = WindowDims.getHeight()/2 - PaddleHeight/2;
	public static final double PaddleDeflectionConstant = 0.035;
	
	/** Key Codes **/
	public static final int KEY_W = 87;
	public static final int KEY_S = 83;
	public static final int KEY_UP = 38;
	public static final int KEY_DOWN = 40;
	public static final int KEY_SPACE = 32;





}
