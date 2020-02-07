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
	static Spaceship ship;

	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	ArrayList<GameObject> stars = new ArrayList<GameObject>();
	Queue<Scene> sceneQueue;
	Font scoreFont = new Font("Serif", Font.BOLD, 30);
	Font fpsFont = new Font("Serif", Font.BOLD, 12);

	ArrayList<Animation> animationManager = new ArrayList<Animation>();
	BufferedImage background;
	// Missile tmp;// = new Missile(0, 0, 20, 10, ship);

	Random rn = new Random();
	int level = 1;
	// int lives = 3;
	int score = 0;
	int prevWhole = 0;
	static double time = 0;
	int numEnemies;
	boolean gameEnd = false;
	boolean readyForNext = false;
	double readyNextTime;
	double prevFrame = 0;
	double fps = 0;
	
	static boolean shake = false;
	static double shakeTime = 0;

	public void makeStars(int n) {
		for (int i = 0; i < n; i++) {

			double x = Math.random() * Constants.WindowDims.width;
			double y = Math.random() * Constants.WindowDims.height;
			double size = rn.nextInt(6);
			double width = 1 * size * 1;
			double height = 1 * size * 1;
			double theta = Math.PI / 2;
			double speed = 6 * size * size;
			GameObject star = new GameObject(x, y, width, height);
			star.theta = theta;
			star.speed = speed;
			
			star.color = new Color( (int)(255), (int)Math.abs((255 * Math.random())), 255, 255);

			
			//star.color = new Color(255, 255, 255, 128);
			stars.add(star);
		}
	}

	TestScene(int level, int score, int health, Queue<Scene> sceneQueue) {
		super();
		this.level = level;
		this.score = score;
		this.sceneQueue = sceneQueue;

		try {
			background = ImageIO.read(new File("images/dark.jpg"));
		} catch (Exception e) {

		}

		ship = new Spaceship(400, 400, 0, 0, health);
		// tmp = new Missile(0, 0, 20, 10, ship);

		switch (level) {
		case 1:
			for (int i = 0; i < 5; i++) {
				enemy.add(new Enemy(0 - (70 * i), 0 - 70 * i, 90, 90, level, 2));
				gameObjects.add(enemy.get(i));
			}
		
			numEnemies = 5;
			break;
		case 2:
			for (int i = 0; i < 4; i++) {
				enemy.add(new Enemy(0 - (70 * i), 0 - 70 * i, 90, 90, level, 3));
				gameObjects.add(enemy.get(i));
			}
			for (int i = 4; i < 8; i++) {
				enemy.add(new Enemy(Constants.WindowDims.width + (70 * i - 90), 0 - 70 * i, 90, 90, level, 5));
				gameObjects.add(enemy.get(i));
			}
			numEnemies = 8;
			break;
		case 3: 
			Boss b = new Boss(300, -200, 200, 200, level, 80);
			b.setCenterX(Constants.WindowDims.width/2);
			
			enemy.add(b);

			gameObjects.add(b);
			numEnemies = 1;
			break;
		default:
			gameEnd = true;
			// sceneQueue.add(new Menu(sceneQueue));
			// isDone = true;
			break;
		}
		gameObjects.add(ship);

		makeStars(200);
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
		
		
		if(shake) g2d.translate(Math.random()*10, Math.random()*10);
		
		//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		g2d.drawImage(background, 0, 0, (int) Constants.WindowDims.width, (int) Constants.WindowDims.height, null);

		//g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		g2d.setColor(Color.RED);

		// ship.paintComponent(g2d);
		drawCenteredString(g2d, "fps " + (int)Math.round(fps*100)/100, fpsFont, (int)(Constants.WindowDims.width * 0.8), 20);

		drawCenteredString(g2d, "Score " + score, scoreFont, Constants.WindowDims.width / 2, 50);
		drawCenteredString(g2d, "Health " + ship.health, scoreFont, Constants.WindowDims.width - 100, 50);
		drawCenteredString(g2d, "Level " + level, scoreFont, 100, 50);
		
		for (int i = 0; i < stars.size(); i++) {
			g2d.setColor(stars.get(i).color);
			g2d.fill(stars.get(i).hitbox);
		}

		ship.paintComponent(g2d);
		
		for (int i = 0; i < ship.bullets.size(); i++) {
			ship.bullets.get(i).paintComponent(g);
		}

		for (int i = 0; i < gameObjects.size(); i++)
			gameObjects.get(i).paintComponent(g2d);
		
		if(readyForNext) {
			//ship.speed = 0;
			g2d.setColor(Color.RED);
			drawCenteredString(g2d, "Next level in " + Math.max(Math.round((3-(time-readyNextTime)) * 100)/100.0,0), scoreFont, Constants.WindowDims.width / 2, 400);
		}

		if (gameEnd) {
			g2d.setColor(Color.RED);

			if (ship.health < 0)
				drawCenteredString(g2d, "Game Over. You scored: " + score, scoreFont, Constants.WindowDims.width / 2,
						400);
			else
				drawCenteredString(g2d, "You won! You scored: " + score, scoreFont, Constants.WindowDims.width / 2,
						400);

			drawCenteredString(g2d, "Press enter to return to menu", scoreFont, Constants.WindowDims.width / 2, 500);

		}
		for (int i = 0; i < animationManager.size(); i++)
			animationManager.get(i).paintComponent(g);

		// tmp.paintComponent(g);

	}

	public double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public double secondSmallest(double[] a) {
		a = Sort.mergeSort(a);
		return a[1];
	}

	public double smallest(Mat[] wall, Mat particle, double theta, Mat ship) {
		Mat velo = new Mat(3, 1, new double[] { Math.cos(theta), Math.sin(theta), 1 });

		HashMap<Double, Mat> map = new HashMap<Double, Mat>();
		double[] d = new double[4];
		for (int i = 0; i < wall.length; i++) {
			double q = particle.sub(wall[i]).getHomogenous().getMag();
			map.put(q, wall[i].sub(particle.sub(velo.multiply(100)).getHomogenous()).getHomogenous());
			d[i] = q;
		}
		d = Sort.mergeSort(d);
		/*
		 * for(int i = 0; i < d.length; i++) { System.out.print(d[i] + ", "); }
		 * //System.out.println("\n");
		 */

		for (int i = 1; i < d.length; i++) {

			if (map.get(d[i]).data[0] * velo.data[0] < 0 || map.get(d[i]).data[1] * velo.data[1] < 0) {

				// System.out.println("choice " + d[i]+ " wall vec " + map.get(d[i]).data[0] +
				// ", " + map.get(d[i]).data[1] );

				// System.exit(0);
				return d[i];
			}
		}
		// System.out.println("bad");

		return 0;
	}

	public Point2D traceToBorder(double x, double y, double theta) {
		Mat particle = new Mat(3, 1, new double[] { x, y, 1 });
		Mat top = new Mat(3, 1, new double[] { -y / Math.tan(theta) + x, 0, 1 });
		Mat bot = new Mat(3, 1, new double[] { (Constants.WindowDims.height + y) / Math.tan(theta) + x,
				Constants.WindowDims.height, 1 });
		Mat left = new Mat(3, 1, new double[] { 0, Math.tan(theta) * (0 - x) + y, 1 });
		Mat right = new Mat(3, 1,
				new double[] { Constants.WindowDims.width, Math.tan(theta) * (Constants.WindowDims.width - x) + y, 1 });

		/*
		 * System.out.println("top: " + particle.sub(top).getHomogenous().getMag() +
		 * " bot " + particle.sub(bot).getHomogenous().getMag() +"left: " +
		 * particle.sub(left).getHomogenous().getMag() + " right " +
		 * particle.sub(right).getHomogenous().getMag() );
		 */

		double border = smallest(new Mat[] { top, bot, left, right }, particle, theta,
				new Mat(3, 1, new double[] { x, y, 1 }));

		Point2D b;
		if (border == particle.sub(top).getHomogenous().getMag()) {
			b = new Point2D.Double(-y / Math.tan(theta) + x, 0);
			// System.out.println("top x: " + b.getX());
		} else if (border == particle.sub(bot).getHomogenous().getMag()) {
			b = new Point2D.Double((Constants.WindowDims.height + y) / Math.tan(theta) + x,
					Constants.WindowDims.height);
			// System.out.println("bot x: " + b.getX());

		} else if (border == particle.sub(left).getHomogenous().getMag()) {
			b = new Point2D.Double(0, Math.tan(theta) * (0 - x) + y);
			// System.out.println("left y: " + b.getY());

		} else {
			b = new Point2D.Double(Constants.WindowDims.width, Math.tan(theta) * (Constants.WindowDims.width - x) + y);
			// System.out.println("right y: " + b.getY());

		}

		return b;

		// return null;

	}

	public static void shake() {
		shake = true;
		shakeTime = time;
	}
	
	public void update(double dt) {
		time += dt;
	
		// ship.update(dt);
		if(time - shakeTime > 0.6) {
			shake = false;
		}
		
		for (int i = 0; i < gameObjects.size(); i++)
			gameObjects.get(i).update(dt);

		// System.out.println(stars.size());
		for (int i = 0; i < stars.size(); i++) {
			stars.get(i).theta = ship.theta + Math.PI;
			// Point2D q = traceToBorder(stars.get(i).x, stars.get(i).y,
			// stars.get(i).theta); //remove

			if (stars.get(i).isVisible() == false) {
				Point2D b = traceToBorder(stars.get(i).x, stars.get(i).y, stars.get(i).theta);
				stars.get(i).x = b.getX();
				stars.get(i).y = b.getY();

			} else
				stars.get(i).update(dt);
		}
		for (int i = 0; i < ship.bullets.size(); i++) {
			ship.bullets.get(i).update(dt);
			for (int q = 0; q < enemy.size(); q++) {
				if (enemy.get(q).isAlive && ship.bullets.get(i).hitbox.intersects(enemy.get(q).hitbox)) {

					ship.bullets.remove(i);
					i--;
					enemy.get(q).health--;
					
					
					if(enemy.get(q).health <= 0) {
						enemy.get(q).isAlive = false;
						Animation a = new Animation();
						a.explosion(enemy.get(q).centerX(), enemy.get(q).centerY(), new Color(128, 128, 128));
						animationManager.add(a);
						score += 100;
						numEnemies--;
						TestScene.shake();
					
						SoundDriver.playExplosion();
					} else {
						//System.out.println("here");
						double theta = -Math.atan2(ship.bullets.get(i).y - enemy.get(i).y, ship.bullets.get(i).x - enemy.get(i).x);
						SoundDriver.playBreak();
						Animation r = new Animation();
						//System.out.println("here");
						r.directionalExplosion(enemy.get(q).centerX(), enemy.get(q).centerY(), theta, new Color(128, 128, 128));
						animationManager.add(r);
					}
					continue;
				}

			}
		}

		if (!gameEnd) {
			if ((int) time != prevWhole) {
				score += 10;
				prevWhole = (int) time;
			}
			if (!readyForNext && numEnemies == 0) {
				//
				// System.out.println("here1");

				readyForNext = true;
				readyNextTime = time;
				
			}
			if (readyForNext && time - readyNextTime > 3) {
				// System.out.println("here2");
				level++;
				sceneQueue.add(new TestScene(level,score, ship.health, sceneQueue));
				isDone = true;
			}

			if (ship.health < 0) {
				Animation a = new Animation();
				a.explosion(ship.x, ship.y, new Color(128, 128, 128));
				animationManager.add(a);
				ship.visible = false;
				gameEnd = true;
			}
			for (int q = 0; q < enemy.size(); q++) {
				if (enemy.get(q).isAlive && ship.getPolygon(0).intersects(enemy.get(q).hitbox)) {
					// ship.health--;
					if(!(enemy.get(q) instanceof Boss)) {
					enemy.get(q).isAlive = false;
					Animation a = new Animation();
					a.explosion(enemy.get(q).centerX(), enemy.get(q).centerY(), new Color(128, 128, 128));
					animationManager.add(a);
					ship.hurt(10);
					numEnemies--;
					shake();
					}
				}
			}
		}

		if (gameEnd) {
			if (Input.keysPressed[10]) {
				sceneQueue.add(new Menu(sceneQueue));
				isDone = true;
			}
		}
		for (int i = 0; i < animationManager.size(); i++) {
			animationManager.get(i).update(dt);
		}

		// tmp.update(dt);
		// System.out.println("here");
		// Syste,.doLayout();.println(ship.)
	}

}
