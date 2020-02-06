import java.awt.Dimension;

public class Constants {
	/** Game Constants **/
	public static final Dimension WindowDims = new Dimension(1000, 800);
	public static final String WindowName = "Pong";
	public static final int GraphicsPeriod = 10;
	public static final int GamePeriod = 10;
	
	/** Spaceship Constants **/
	public static final double THRUST_ACCEL = 1000;
	public static final double SHIP_ROTATE_VELO = Math.PI/1.1 ;
	public static final double BULLET_SPEED = 500;
	public static final double BULLET_WIDTH = 5;
	public static final double BULLET_HEIGHT = 10;




	/** Missile Constants **/
	public static final double MISSILE_SPEED = 200;
	public static final double MISSILE_MAX_OMEGA = Math.PI;

	
	/** Ball Constants **/
	


	/** Paddle Constants **/
	
	
	/** Key Codes **/
	public static final int KEY_W = 87;
	public static final int KEY_S = 83;
	public static final int KEY_A = 65;
	public static final int KEY_D = 68;

	public static final int KEY_UP = 38;
	public static final int KEY_DOWN = 40;
	public static final int KEY_SPACE = 32;
	
	public static final int KEY_THRUST = KEY_W;
	public static final int KEY_SHOOT = KEY_SPACE;




}
