import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PongScene extends Scene {
	int level = 0;

	public final int numOfPaddle = 1;
	public final int numOfBall = 1;
	public int numBricks = 30;
	public int layers = 5;
	GameObject[] gameObjects;
	Paddle[] paddles;
	Ball[] balls;
	Brick[] bricks;

	PowerUpManager[] powerUpManagers;

	boolean demo = false;
	boolean online = false;
	boolean gameEnd = false;
	public double timeLeft;
	BufferedImage[] background = new BufferedImage[1];

	Font scoreFont = new Font("Serif", Font.BOLD, 30);
	int scoreL = 0;
	int scoreR = 0;
	int maxScore = 6;
	public boolean master = false;
	// BufferedImage background
	// public void backgroundTimer;

	public void initGame(int selection) {
		// PongScene game = new PongScene(int selection);
		balls = new Ball[numOfBall];
		paddles = new Paddle[numOfPaddle];
		powerUpManagers = new PowerUpManager[numOfPaddle];

		if (selection == 1) {
			gameObjects = new GameObject[numOfPaddle + numOfBall + 100]; 
			bricks = Brick.generateBricks(10, 10, 2, 1);

			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth,
					Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter,
					Constants.BallDiameter);
			// System.out.println(Constants.WindowDims.width-Constants.PaddleStartX-Constants.PaddleWidth);
			for(int i = 2; i < 2 + bricks.length; i++) gameObjects[i] = bricks[i-2];
			//System.exit(0);
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			// paddles[1] = (Paddle) gameObjects[2];

			powerUpManagers[0] = new PowerUpManager(paddles[0], balls[0], 5);
		}
	}

	public PongScene() {
		try {
			background[0] = ImageIO.read(new File("sprites\\galaxy2.jpg"));
			String fileName = "galaxy" + ((new Random()).nextInt(3) + 1) + ".jpg";
			// background = ImageIO.read(new File("sprites\\"+fileName));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		g2d.drawImage(background[level], 0, 0, null);
		g2d.setColor(Color.WHITE);
		g2d.setFont(scoreFont);

		if (!gameEnd) {
			int q = 0 ;
			for (GameObject gameObject : gameObjects) gameObject.paintComponent(g);
				
			for (PowerUpManager pm : powerUpManagers)
				if (pm != null)
					pm.paintComponent(g);
		} else {
			String winner = "Left side";
			if (scoreR > scoreL)
				winner = "Right side";
			drawCenteredString(g2d, winner + " wins!", scoreFont, Constants.WindowDims.width / 2,
					Constants.WindowDims.height / 2);
			drawCenteredString(g2d, "Press Space to Restart", scoreFont, Constants.WindowDims.width / 2,
					Constants.WindowDims.height / 2 + 50);
		}

		if (!demo) {
			g2d.setColor(Color.WHITE);
			g2d.drawString("" + scoreL, Constants.WindowDims.width / 2 - 70, 100);
			g2d.drawString("" + scoreR, Constants.WindowDims.width / 2 + 70, 100);
		}

	}

	public void gameEnd() {
		// if(gameEnd )
		gameEnd = true;
		SoundDriver.playGameOver();
	}

	public synchronized void update(double dt) {
		// Check collisions

		try {
			// System.out.println( InetAddress.getLocalHost());
		} catch (Exception e) {
		}
		if (!gameEnd) {
			for (Ball ball : balls) {
				for (Paddle paddle : paddles) {
					if (ball.hitbox.intersects(paddle.hitbox) && ball.collisionLock == false) {

						double base = paddle.deflectionDir;// Math.PI-Math.floor((ball.theta+0.01)/Math.PI);
						double sign = (base > 0) ? 1 : -1;
						ball.theta = Math.PI * 3.0 / 2.0 + Constants.PaddleHeight / paddle.height
								* Constants.PaddleDeflectionConstant * (ball.centerX() - paddle.centerX());

						
						ball.speed = Constants.BallVelocityNormal;
						SoundDriver.playHit();
					}
				}

				if (ball.centerY() > Constants.WindowDims.height) {
					ball.reset();
					scoreR++;
					SoundDriver.playReset();
				}
			}

			for (Ball ball : balls) {
				for (Brick brick : bricks) {
					if (brick.isAlive && ball.hitbox.intersects(brick.hitbox)) {
						brick.hit();
						if(Math.abs(ball.topY() - brick.bottomY()) < 5 || Math.abs(ball.bottomY() - (brick.topY())) < 5)
							ball.setDxDy(ball.dx, ball.dy * -1); //ball hit bottom or top
						if(Math.abs(ball.rightX() - brick.leftX()) < 5 || Math.abs(ball.leftX() - brick.rightX()) < 5) 
							ball.setDxDy(ball.dx * -1, ball.dy); //ball hit bottom or top

						 //ball hit top
						//ball.y = brick.y + brick.height + 1;
						
						ball.speed = Constants.BallVelocityNormal;
						SoundDriver.playHit();
					}
				}
			}

			for (GameObject gameObject : gameObjects) {
				gameObject.update(dt);
			}

			for (PowerUpManager pm : powerUpManagers) {
				if (pm != null)
					pm.update(dt);
			}

			if (scoreL > maxScore || scoreR > maxScore) {
				gameEnd();
			}
		}
		if (gameEnd && Input.keysPressed[Constants.KEY_SPACE]) {
			isDone = true;
		}

	}
}
