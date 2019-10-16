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
	
	Random rand = new Random();
	double time = 0;
	double lastPowerUpTime = -5;
	double powerUpStartTime;
	boolean hasPowerUp;
	boolean powerUpAlive;
	int selectedPowerUp;
	double powerUpTime;
	
	
	public PowerUpManager(Paddle paddle, Ball ball, double offset) {
		this.paddle = paddle;
		this.ball = ball;
		lastPowerUpTime = offset;
	}
	
	public void spawnPowerUp() {
		//change ball/paddle property, record start time,activate internal animation sequence for time, check time in update
		//System.out.println("here");
		powerUp = new GameObject(Constants.WindowDims.width/2, Constants.WindowDims.height/2, 20, 20);
		selectedPowerUp = rand.nextInt(2);
		powerUp.setCenterX(0);
		powerUp.setCenterY(0);
		powerUp.speed = Constants.PowerUpTravelSpeed;
		powerUp.theta = Math.PI + paddle.deflectionDir + (Math.random()*2-1) * Constants.PowerUpEjectRange;
	
		if(selectedPowerUp == 0) {
			powerUpTime = 5.0;
			powerUp.color = Color.RED;
			
		}
		if(selectedPowerUp == 1) {
			powerUpTime = 5.0;
			powerUp.color = Color.BLUE;
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

		}
		if(hasPowerUp && selectedPowerUp == 0) {
			
			paddle.activeSpeed = Constants.PaddleVelocityFast;
		}
		if(hasPowerUp && selectedPowerUp == 1) {
			
			paddle.height = Constants.PaddleHeight * 2;
		}
	}
}
