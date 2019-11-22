import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Random;

public class PowerUpManager implements Serializable{ //powerup state machine
	Paddle paddle;
	Ball ball;
	GameObject powerUp;
	PongScene level;
	
	Random rand = new Random();
	double time = 0;
	double lastPowerUpTime = 0;
	double powerUpStartTime;
	boolean hasPowerUp;
	boolean powerUpAlive;
	int selectedPowerUp;
	double powerUpTime;
	
	
	public PowerUpManager(Paddle paddle, PongScene pongScene, double offset) {
		this.paddle = paddle;
		this.ball = ball;
		this.level = pongScene;
		lastPowerUpTime = offset;
		
	}
	
	public void spawnPowerUp() {
		//change ball/paddle property, record start time,activate internal animation sequence for time, check time in update
		//System.out.println("here");
		powerUp = new GameObject(Constants.WindowDims.width/2, Constants.WindowDims.height/2, 20, 20);
		selectedPowerUp = rand.nextInt(3);
		//selectedPowerUp = 2;
		powerUp.setCenterX(0);
		powerUp.setCenterY(0);
		powerUp.speed = Constants.PowerUpTravelSpeed;
		powerUp.theta = Math.PI/2 + (Math.random()*2-1) * Constants.PowerUpEjectRange;
	
		if(selectedPowerUp == 0) {
			powerUpTime = 5.0;
			powerUp.color = Color.GREEN;
			
		}
		if(selectedPowerUp == 1) {
			powerUpTime = 5.0;
			powerUp.color = Color.BLUE;
		}
		if(selectedPowerUp == 2) {
			powerUpTime = 5.0;
			powerUp.color = Color.RED;
		}
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(powerUpAlive) {
			g2d.setColor(powerUp.color);
			g2d.fill(powerUp.hitbox);
		}
	}
	
	void update(double dt) {
		time += dt;
		//check paddle powerup collision
		if(powerUpAlive) powerUp.update(dt); //odd behavior
		if(time-lastPowerUpTime > Constants.PowerUpSpawnRate) {
			lastPowerUpTime = time;
			//powerUpStartTime = time;
			//hasPowerUp = true;
			powerUpAlive = true;
			spawnPowerUp();
		}
		if(powerUpAlive && powerUp.hitbox.intersects(paddle.hitbox)) {
			powerUpStartTime = time;
			hasPowerUp = true;
			powerUpAlive = false;
			SoundDriver.playPowerUp();
		}
		
		if(hasPowerUp && (time-powerUpStartTime > powerUpTime)) {
			hasPowerUp = false;
			selectedPowerUp = -1;
			paddle.activeSpeed = Constants.PaddleVelocityNormal;
			paddle.height = Constants.PaddleHeight;
			paddle.width = Constants.PaddleWidth;
		}
		if(hasPowerUp && selectedPowerUp == 0) {
			
			paddle.activeSpeed = Constants.PaddleVelocityFast;
		}
		if(hasPowerUp && selectedPowerUp == 1) {
			paddle.width = Constants.PaddleWidth * 2;
		}
		if(hasPowerUp && selectedPowerUp == 2) {
			int numBalls = 10;
	        GameObject[] q = new GameObject[2+level.bricks.length + numBalls];
	        Ball[] b = new Ball[1 + numBalls];
	        
			q[0] = level.gameObjects[0];
			q[1] = level.gameObjects[1];
			
			for(int i = 2; i < 2+numBalls; i++) {
				q[i] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter,
					Constants.BallDiameter);
				
				b[i-1] = (Ball) q[i];
				b[i-1].x = Math.random() * Constants.WindowDims.width;
				b[i-1].theta = Math.random() * 2*Math.PI;
			}
			
			for(int i = 2+numBalls; i < 2+numBalls + level.bricks.length; i++) q[i] = level.bricks[i-(2+numBalls)];
			//System.exit(0);
			b[0] = (Ball) level.gameObjects[1];
			level.gameObjects = q;
			level.balls = b;
			level.aliveBalls = numBalls+1;
			
			hasPowerUp = false;
			selectedPowerUp = -1;
		}
	}
}
