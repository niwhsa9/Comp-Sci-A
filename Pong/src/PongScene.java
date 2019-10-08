import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PongScene extends Scene{
	public final int numOfPaddle = 2;
	public final  int numOfBall = 1;
	GameObject[] gameObjects;
	Paddle[] paddles;
	Ball[] balls;
	
	boolean gameEnd = false;
	public double timeLeft;
    BufferedImage background; 
    Font scoreFont = new Font("Serif", Font.BOLD, 30);
    int scoreL = 0;
    int scoreR = 0;
	//BufferedImage background
	//public void backgroundTimer;
    
    public void initGame(int selection) {
    	//PongScene game = new PongScene(int selection);
    	
    	gameObjects = new GameObject[numOfPaddle+numOfBall];
    	balls = new Ball[numOfBall];
    	paddles = new Paddle[numOfPaddle];
		if(selection == 1) {
			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new AIPaddle(Constants.WindowDims.width-Constants.PaddleStartX-Constants.PaddleWidth, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);

			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
		} else if(selection == 2) {
			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new UserPaddle(Constants.WindowDims.width-Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
			
			((UserPaddle) paddles[1]).setKeys(Constants.KEY_DOWN, Constants.KEY_UP);
			paddles[1].setDeflectionDir(Math.PI);
		}
    }
    
    public PongScene() {
    	try {
			background = ImageIO.read(new File("sprites\\galaxy.jpg"));
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
		g2d.drawImage(background, 0, 0, null);
		g2d.setColor(Color.WHITE);
		g2d.setFont(scoreFont);
		
		if(!gameEnd) {
			for(GameObject gameObject : gameObjects) gameObject.paintComponent(g);	
		} else {
			String winner = "Left side";
			if(scoreR > scoreL) winner = "Right side";
			drawCenteredString(g2d, winner + " wins!", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2);
			drawCenteredString(g2d, "Press Space to Restart", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2 + 50);
		}
		
		g2d.drawString(""+scoreL, Constants.WindowDims.width/2 - 70, 100);
		g2d.drawString(""+scoreR, Constants.WindowDims.width/2 + 70, 100);
	

	}
	
	public void gameEnd() {
		//if(gameEnd ) 
		gameEnd = true;
	}

	public synchronized void update(double dt) {
		//Check collisions 
		if(!gameEnd) {
		for(Ball ball : balls) {
			for(Paddle paddle : paddles) {
				if(ball.hitbox.intersects(paddle.hitbox) && ball.collisionLock == false) {
					double base = paddle.deflectionDir;//Math.PI-Math.floor((ball.theta+0.01)/Math.PI);
					double sign = (base > 0) ? 1 : -1;
					ball.theta = sign*(base + Constants.PaddleDeflectionConstant * (paddle.centerY()-ball.centerY())); 
					//System.out.println("theta"+ ball.theta +"delta" +(paddle.centerY()-ball.centerY()));
				}
			}
			
			if(ball.centerX() < (0-ball.width*2) ) {
				ball.reset();
				scoreR++;
			} else if(ball.centerX() >= (Constants.WindowDims.width + ball.width*2)) {
				ball.reset();
				scoreL++;
			}
		}
		for(Paddle paddle : paddles) {
			paddle.updateKnownBall(balls[0].centerY());
		}
		
		
		for(GameObject gameObject : gameObjects) {
			gameObject.update(dt);			
		}
		
		if(scoreL > 1 || scoreR > 1) {
			gameEnd();
		}
		}
		if(gameEnd && Input.keysPressed[Constants.KEY_SPACE]) {
			isDone = true;
		}

	}
}
