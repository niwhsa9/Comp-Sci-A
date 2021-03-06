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

public class PongScene extends Scene{
	public final int numOfPaddle = 2;
	public final  int numOfBall = 1;
	GameObject[] gameObjects;
	Paddle[] paddles;
	Ball[] balls;
	PowerUpManager[] powerUpManagers;
	
	boolean demo = false;
	boolean online = false;
	boolean gameEnd = false;
	public double timeLeft;
    BufferedImage background; 
    Font scoreFont = new Font("Serif", Font.BOLD, 30);
    int scoreL = 0;
    int scoreR = 0;
    int maxScore = 6;
    public boolean master = false;
	//BufferedImage background
	//public void backgroundTimer;
    
    public void initGame(int selection) {
    	//PongScene game = new PongScene(int selection);
    	
    	gameObjects = new GameObject[numOfPaddle+numOfBall];
    	balls = new Ball[numOfBall];
    	paddles = new Paddle[numOfPaddle];
    	powerUpManagers = new PowerUpManager[numOfPaddle];
		if(selection == 1) {
			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new AIPaddle(Constants.WindowDims.width-Constants.PaddleStartX-Constants.PaddleWidth, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			//System.out.println(Constants.WindowDims.width-Constants.PaddleStartX-Constants.PaddleWidth);
			
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
			
			powerUpManagers[0] = new PowerUpManager(paddles[0], balls[0], -5);
			
			
		} else if(selection == 2) {
			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new UserPaddle(Constants.WindowDims.width-Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
			
			((UserPaddle) paddles[1]).setKeys(Constants.KEY_DOWN, Constants.KEY_UP);
			paddles[1].setDeflectionDir(Math.PI);
			
			powerUpManagers[0] = new PowerUpManager(paddles[0], balls[0], -5);
			powerUpManagers[1] = new PowerUpManager(paddles[1], balls[0], 5);

		} else if(selection == 3) {
			gameObjects[0] = new AIPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new AIPaddle(Constants.WindowDims.width-Constants.PaddleStartX-Constants.PaddleWidth, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
			paddles[0].setDeflectionDir(0);
			balls[0].theta = Math.PI/2 - 0.4;
			demo = true;
		} else if(selection == 4) {
			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new Paddle(Constants.WindowDims.width-Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
			
			//((UserPaddle) paddles[1]).setKeys(Constants.KEY_DOWN, Constants.KEY_UP);

			paddles[1].setDeflectionDir(Math.PI);
			//powerUpManagers[0] = new PowerUpManager(paddles[0], balls[0], -5);
			//powerUpManagers[1] = new PowerUpManager(paddles[1], balls[0], 5);
			master = true;
			online = true;
			PongClient.connect(this);
			
			//while(PongClient.update(me, myBall)) {}
			//PongClient.update(null, null);
			
		} else if(selection == 5) {
			gameObjects[2] = new Paddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[0] = new UserPaddle(Constants.WindowDims.width-Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
			
			((UserPaddle) paddles[0]).setKeys(Constants.KEY_DOWN, Constants.KEY_UP);

			paddles[0].setDeflectionDir(Math.PI);
			
			//powerUpManagers[0] = new PowerUpManager(paddles[0], balls[0], -5);
			//powerUpManagers[1] = new PowerUpManager(paddles[1], balls[0], 5);
			
			online = true;
			PongClient.connect(this);
		}
    }
    
    public PongScene() {
    	try {
			background = ImageIO.read(new File("sprites\\galaxy2.jpg"));
    		String fileName = "galaxy" + ((new Random()).nextInt(3)+1)+".jpg";
			//background = ImageIO.read(new File("sprites\\"+fileName));

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
			for(PowerUpManager pm : powerUpManagers) if(pm != null)pm.paintComponent(g);
		} else {
			String winner = "Left side";
			if(scoreR > scoreL) winner = "Right side";
			drawCenteredString(g2d, winner + " wins!", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2);
			drawCenteredString(g2d, "Press Space to Restart", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2 + 50);
		}
		
		if(!demo) {
			g2d.setColor(Color.WHITE);
			g2d.drawString(""+scoreL, Constants.WindowDims.width/2 - 70, 100);
			g2d.drawString(""+scoreR, Constants.WindowDims.width/2 + 70, 100);
		}

	}
	
	public void gameEnd() {
		//if(gameEnd ) 
		gameEnd = true;
		SoundDriver.playGameOver();
	}

	public synchronized void update(double dt) {
		//Check collisions 
		
		try {
		//System.out.println( InetAddress.getLocalHost());
		} catch(Exception e) {} 
		if(!gameEnd) {
		for(Ball ball : balls) {
			for(Paddle paddle : paddles) {
				if(ball.hitbox.intersects(paddle.hitbox) && ball.collisionLock == false) {
					
					double base = paddle.deflectionDir;//Math.PI-Math.floor((ball.theta+0.01)/Math.PI);
					double sign = (base > 0) ? 1 : -1;
					ball.theta = sign*(base + Constants.PaddleHeight/paddle.height * Constants.PaddleDeflectionConstant * (paddle.centerY()-ball.centerY())); 
					//System.out.println("theta"+ ball.theta +"delta" +(paddle.centerY()-ball.centerY()));
					//Smash
					if(!demo && Math.sin(paddle.theta)*paddle.speed * Math.sin(ball.theta)*ball.speed > 0 ) {
						ball.theta = Math.atan2((Math.sin(ball.theta)),(Constants.SmashMultiplier*Math.cos(ball.theta)));
						ball.speed = Math.pow(Math.pow(ball.speed*Math.sin(ball.theta),2) + Math.pow(Constants.SmashMultiplier*ball.speed*Math.cos(ball.theta),2),0.5);
					} else ball.speed = Constants.BallVelocityNormal;
					SoundDriver.playHit();
				}
			}
			
			if(ball.centerX() < (0-ball.width*2) ) {
				ball.reset();
				scoreR++;
				SoundDriver.playReset();
			} else if(ball.centerX() >= (Constants.WindowDims.width + ball.width*2)) {
				ball.reset();
				scoreL++;
				SoundDriver.playReset();

			}
		}
		for(Paddle paddle : paddles) {
			paddle.updateKnownBall(balls[0].centerY(), balls[0].theta);
		}
		
		for(GameObject gameObject : gameObjects) {
			gameObject.update(dt);			
		}
		
		for(PowerUpManager pm : powerUpManagers) {
			if(pm != null) pm.update(dt);
		}
		
		if(scoreL > maxScore || scoreR > maxScore) {
			gameEnd();
		}
		}
		if(gameEnd && Input.keysPressed[Constants.KEY_SPACE]) {
			isDone = true;
		}
		if(online) {
			
			PongPacket recieve = PongClient.getLast();
			if(recieve != null) {
				paddles[1] = recieve.paddle;
				gameObjects[2] = paddles[1];

			    //recieve.paddle.x = Constants.WindowDims.width-Constants.PaddleStartX-Constants.PaddleWidth;
				if(master != true) balls[0] = recieve.ball;
			
				if(master != true)  gameObjects[1] = balls[0];
				//if(master != true) gameObjects[1].update(0.2);
				//gameObjects[2].update(System.currentTimeMillis()/1000.0-recieve.sendTime);
				//if(master) System.out.println("actual " + balls[0].x);
				//System.out.println(System.currentTimeMillis()/1000.0-recieve.sendTime);
			}
			//System.out.println(recieve.paddle.y);
			//System.exit(0);
		}

	}
}
