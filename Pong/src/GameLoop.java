import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.SwingUtilities;

public class GameLoop {

	/* Top Level Graphics */
	static Window window;
	static Menu menu;
	static Scene scene;
	
	/* Game Objects */
	public static final int numOfPaddle = 2;
	public static final  int numOfBall = 1;
	static GameObject gameObjects[] = new GameObject[numOfPaddle+numOfBall];
	static Ball balls[] = new Ball[numOfBall];
	static Paddle paddles[] = new Paddle[numOfPaddle];


	public static void main(String[] args) {
		/** Graphics Thread **/
		new Thread(new Runnable() {

			@Override
			public void run() {
				//Initialize window
				window = new Window(Constants.WindowName, (int)Constants.WindowDims.getWidth(), (int)Constants.WindowDims.getHeight());
				window.getContentPane().setPreferredSize(Constants.WindowDims);
				window.pack();
				menu = new Menu();
				Input input = new Input();
				window.add(menu);
				window.addKeyListener(input);

				//Wait for user to select game type
				while(menu.selection < 0) {
					menu.update();
					menu.revalidate();
					menu.repaint();
				}
				
				//Run game
				window.remove(menu);
				scene = new Scene();
				window.add(scene);
				while (true) {
					try {
						scene.revalidate();
						scene.repaint();
						Thread.sleep(Constants.GraphicsPeriod);
					} catch (Exception e) {}
				} 
			}
		}).start();

		/** World Thread **/
		while(scene == null) System.out.print("");
		
		
		scene.gameObjects = gameObjects;
		scene.paddles = paddles;
		scene.balls = balls;
		//gameObjects[0] = new Paddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
		if(menu.selection == 1) {
			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new AIPaddle(Constants.WindowDims.width-Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);

			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
		} else if(menu.selection == 2) {
			gameObjects[0] = new UserPaddle(Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			gameObjects[1] = new Ball(Constants.BallStartX, Constants.BallStartY, Constants.BallDiameter, Constants.BallDiameter);
			gameObjects[2] = new UserPaddle(Constants.WindowDims.width-Constants.PaddleStartX, Constants.PaddleStartY, Constants.PaddleWidth, Constants.PaddleHeight);
			
			balls[0] = (Ball) gameObjects[1];
			paddles[0] = (Paddle) gameObjects[0];
			paddles[1] = (Paddle) gameObjects[2];
			
			((UserPaddle) paddles[1]).setKeys(Constants.KEY_DOWN, Constants.KEY_UP);
			paddles[1].setDeflectionDir(Math.PI);
		}
		// Game loop
		double prevTime = System.currentTimeMillis()/1000.0;
		while (true) {
			try {
				double currentTime = System.currentTimeMillis()/1000.0;
				scene.update(currentTime-prevTime);
				prevTime = currentTime;
				Thread.sleep(Constants.GamePeriod);
			} catch (Exception e) {}
		}
		
		/** Network Thread **/

	}

}
