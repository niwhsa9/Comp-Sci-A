import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;

public class TestScene extends Scene {

	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	Queue<Scene> sceneQueue;
	Font scoreFont = new Font("Serif", Font.BOLD, 30);
	Font fpsFont = new Font("Serif", Font.BOLD, 12);

	ArrayList<Animation> animationManager = new ArrayList<Animation>();
	BufferedImage background;
	
	Snake userSnake = new Snake(Color.GREEN);
	// Missile tmp;// = new Missile(0, 0, 20, 10, ship);

	Random rn = new Random();
	int level = 1;
	// int lives = 3;
	int score = 0;
	static double time = 0;
	int numEnemies;
	boolean gameEnd = false;
	boolean readyForNext = false;
	double readyNextTime;
	double prevFrame = 0;
	double fps = 0;
	
	static boolean shake = false;
	static double shakeTime = 0;


	TestScene(int level, int score, int health, Queue<Scene> sceneQueue) {
		super();
		this.level = level;
		this.score = score;
		this.sceneQueue = sceneQueue;

		try {
			background = ImageIO.read(new File("images/dark.jpg"));
		} catch (Exception e) {

		}

		
	}

	public void paintComponent(Graphics g) {
		//fps = 1.0/(time - prevFrame);
		//prevFrame = time;
		double t = System.currentTimeMillis()/1000.0;
		fps = 1.0/(t- prevFrame);
		prevFrame = t;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		
		g2d.setColor(Color.RED);
		drawCenteredString(g2d, "fps " + (int)Math.round(fps*100)/100, fpsFont, (int)(Constants.WindowDims.width * 0.8), 20);

		drawCenteredString(g2d, "Score " + score, scoreFont, Constants.WindowDims.width / 2, 50);
		//drawCenteredString(g2d, "Health " + ship.health, scoreFont, Constants.WindowDims.width - 100, 50);
		drawCenteredString(g2d, "Level " + level, scoreFont, 100, 50);
		
		userSnake.paintComponent(g);
		for (int i = 0; i < animationManager.size(); i++)
			animationManager.get(i).paintComponent(g);
		
		// tmp.paintComponent(g);

	}

	public double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public void update(double dt) {
		userSnake.update(dt);
		
	}

}
