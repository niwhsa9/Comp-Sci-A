import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PongScene extends Scene {
	int level = 0;
	int numLevel = 4;
	int aliveBalls = 1;
	
	int score = 0;
	int bricksBroken = 0;
	int lives = 3;

	public final int numOfPaddle = 1;
	public final int numOfBall = 1;
	public int numBricks = 0;
	public int layers = 5;
	GameObject[] gameObjects;
	Paddle[] paddles;
	Ball[] balls;
	Brick[] bricks;
	
	Queue<Scene> sceneQueue;


	PowerUpManager[] powerUpManagers;
	
	//Animation animationManager;
	ArrayList<Animation> animationManager = new ArrayList<Animation>();

	boolean demo = false;
	boolean online = false;
	boolean gameEnd = false;
	public double timeLeft;
	BufferedImage[] background = new BufferedImage[numLevel];

	Font scoreFont = new Font("Serif", Font.BOLD, 30);
	int scoreL = 0;
	int scoreR = 0;
	int maxScore = 6;
	public boolean master = false;
	// BufferedImage background
	// public void backgroundTimer;

	public void initGame(int selection) {
		// PongScene game = new PongScene(int selection);
		level = selection;
		balls = new Ball[numOfBall];
		paddles = new Paddle[numOfPaddle];
		powerUpManagers = new PowerUpManager[numOfPaddle];

		if (selection == 1) {
			numBricks = 32;//32;//32;
			bricks = Brick.generateBricks(9, 9, 4, 1);
			gameObjects = new GameObject[numOfPaddle + numOfBall + bricks.length]; 	
		} else if(selection == 2) {
			numBricks = 3;//16;//16;
			bricks = Brick.generateBricks(9, 9, 2, 1);
			gameObjects = new GameObject[numOfPaddle + numOfBall + bricks.length]; 	
		}else if(selection == 3) {
			numBricks = 3;//5;
			bricks = Brick.generateBricks(15, 15, 4, 1);
			gameObjects = new GameObject[numOfPaddle + numOfBall + bricks.length]; 	
		} else if(selection == 4) {
			numBricks = 3;//5;
			bricks = Brick.generateBricks(13, 6, 6, 1);
			gameObjects = new GameObject[numOfPaddle + numOfBall + bricks.length]; 	
		}
		
		else {
			numBricks = 5;
			bricks = Brick.generateBricks(20, 20, 1, 0);
			gameObjects = new GameObject[numOfPaddle + numOfBall + bricks.length]; 	
		}
		
		
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

		powerUpManagers[0] = new PowerUpManager(paddles[0], this, -10);
		//animationManager = new Animation();
	}	

	public PongScene(Queue<Scene> sceneQueue, int score) {
		this.sceneQueue = sceneQueue;
		this.score = score;
		try {
			for(int i = 0; i < numLevel; i++) {
				background[i] = ImageIO.read(new File("sprites\\" + (i+1) + ".jpg"));
			}
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
		g2d.drawImage(background[(level-1)%numLevel], 0, 0, null);
		g2d.setColor(Color.WHITE);
		g2d.setFont(scoreFont);

		if (!gameEnd) {
			int q = 0 ;
			for (GameObject gameObject : gameObjects) gameObject.paintComponent(g);
				
			for (PowerUpManager pm : powerUpManagers)
				if (pm != null)
					pm.paintComponent(g);
			for(int i = 0; i < animationManager.size(); i++) animationManager.get(i).paintComponent(g);
			
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
			drawCenteredString(g2d, "Score: " + score, scoreFont, Constants.WindowDims.width / 2, 50);
			drawCenteredString(g2d, "Lives:" + lives, scoreFont, Constants.WindowDims.width-60, Constants.WindowDims.height-40);
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
						ball.theta = Math.PI * 3.0 / 2.0 + Constants.PaddleWidth / paddle.width
								* Constants.PaddleDeflectionConstant * (ball.centerX() - paddle.centerX());

						
						ball.speed = Constants.BallVelocityNormal;
						SoundDriver.playHit();
					}
				}

				if (aliveBalls == 1 && ball.isAlive && ball.centerY() > Constants.WindowDims.height) {
					ball.reset();
					lives--;
					paddles[0].setCenterX(Constants.WindowDims.width/2 );
					SoundDriver.playReset();
					//if(ball != balls[0]) balls[0] = ball;
					//ball.isAlive = false;
				} else if(ball.isAlive && ball.centerY() > Constants.WindowDims.height) {
					aliveBalls--;
					ball.isAlive = false;
				}
			}

			for (Ball ball : balls) {
				for (Brick brick : bricks) {
					if (brick.isAlive && ball.hitbox.intersects(brick.hitbox)) {
						Animation anim = new Animation();
						anim.explosion(brick.x, brick.y, brick.color);
						animationManager.add(anim);
						brick.hit();
						if(Math.abs(ball.topY() - brick.bottomY()) < ball.height || Math.abs(ball.bottomY() - (brick.topY())) < ball.height)
							ball.setDxDy(ball.dx, ball.dy * -1); //ball hit bottom or top
						if(Math.abs(ball.rightX() - brick.leftX()) < ball.width || Math.abs(ball.leftX() - brick.rightX()) < ball.width) 
							ball.setDxDy(ball.dx * -1, ball.dy); //ball hit bottom or top
						score+=10;
						bricksBroken++;
						 //ball hit top
						//ball.y = brick.y + brick.height + 1;
						
						ball.speed = Constants.BallVelocityNormal;
						SoundDriver.playBreak();
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
			
			for(int i = 0; i < animationManager.size(); i++) {
				animationManager.get(i).update(dt);
			}
			
			

			if (lives == 0) {
				sceneQueue.add(new Menu(sceneQueue));
				gameEnd();
			} else if(bricksBroken == numBricks) {
				PongScene nextLevel = new PongScene(sceneQueue, score);
				nextLevel.initGame(level+1);
				sceneQueue.add(nextLevel);
				isDone = true;
			}
		}
		if (gameEnd && Input.keysPressed[Constants.KEY_SPACE]) {
			isDone = true;
		}

	}
}
