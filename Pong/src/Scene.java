import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Scene extends JPanel{
	GameObject[] gameObjects;
	Paddle[] paddles;
	Ball[] balls;
	boolean gameEnd = false;
	public double timeLeft;
    BufferedImage background; 
    int scoreL = 0;
    int scoreR = 0;
	//BufferedImage background
	//public void backgroundTimer;
    
    public Scene() {
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
		for(GameObject gameObject : gameObjects) gameObject.paintComponent(g);		
		
	}
	
	public void gameEnd() {
		//if(gameEnd ) 
	}

	public synchronized void update(double dt) {
		//Check collisions 
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

	}
}
