import java.awt.Dimension;

public class Constants {
	/** Game Constants **/
	public static final Dimension WindowDims = new Dimension(800, 800);
	public static final String WindowName = "Pong";
	public static final int GraphicsPeriod = 10;
	public static final int GamePeriod = 10;
	
	/** Power Up Constants **/
	public static final double PowerUpSpawnRate = 10;
	public static final double PowerUpTravelSpeed = 100;
	public static final double PowerUpEjectRange = 0.8;

	/** Brick Constants **/
	public static final double BrickFallDy = 400;


	
	/** Ball Constants **/
	public static final double BallVelocityNormal = 400.0;
	public static final double BallVelocitySlow = 20.0;
	public static final double BallVelocityFast = 200.0;
	public static final double BallDiameter = 10;
	public static final double BallStartX = WindowDims.getWidth()/2 - BallDiameter/2;
	public static final double BallStartY = WindowDims.getHeight()/2 + BallDiameter * 3;


	/** Paddle Constants **/
	public static final double PaddleVelocityNormal = 400.0;
	public static final double PaddleVelocitySlow = 2.0;
	public static final double PaddleVelocityFast = 500.0;
	public static final double PaddleWidth = 60;
	public static final double PaddleHeight = 12;
	public static final double PaddleStartX = WindowDims.getWidth()/2 - PaddleWidth/2;
	public static final double PaddleStartY = WindowDims.getHeight() - PaddleHeight*4;
	public static final double PaddleDeflectionConstant = 0.035;
	public static final double SmashMultiplier = 1.5; 
	
	/** Key Codes **/
	public static final int KEY_W = 87;
	public static final int KEY_S = 83;
	public static final int KEY_A = 65;
	public static final int KEY_D = 68;

	public static final int KEY_UP = 38;
	public static final int KEY_DOWN = 40;
	public static final int KEY_SPACE = 32;





}
