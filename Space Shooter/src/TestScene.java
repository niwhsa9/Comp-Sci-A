import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;

public class TestScene extends Scene {
	Spaceship ship;

	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	ArrayList<Enemy> enemy = new ArrayList<Enemy>();
	ArrayList<GameObject> stars = new ArrayList<GameObject>();
	Queue<Scene> sceneQueue;
	Font scoreFont = new Font("Serif", Font.BOLD, 30);
	ArrayList<Animation> animationManager = new ArrayList<Animation>();

	Random rn = new Random();
	int level = 1;
	int lives = 3;
	int score = 0;
	int prevWhole = 0;
	double time = 0;
	int numEnemies;
	boolean gameEnd = false;

	public void makeStars(int n) {
		for (int i = 0; i < n; i++) {

			double x = Math.random() * Constants.WindowDims.width;
			double y = Math.random() * Constants.WindowDims.height;
			double size = rn.nextInt(4);
			double width = 1 * size * 1;
			double height = 1 * size * 1;
			double theta = Math.PI / 2;
			double speed = 30 * size;
			GameObject star = new GameObject(x, y, width, height);
			star.theta = theta;
			star.speed = speed;
			star.color = Color.white;
			stars.add(star);
		}
	}

	TestScene(int level, Queue<Scene> sceneQueue) {
		super();
		this.level = level;
		this.sceneQueue = sceneQueue;

		ship = new Spaceship(400, 400, 0, 0);

		switch (level) {
		case 1:
			for (int i = 0; i < 5; i++) {
				enemy.add(new Enemy(0 - (70 * i), 0 - 70 * i, 50, 50, level));
				gameObjects.add(enemy.get(i));
			}
			numEnemies = 5;
			break;
		case 2:
			for (int i = 0; i < 2; i++) {
				enemy.add(new Enemy(0 - (70 * i), 0 - 70 * i, 50, 50, level));
				gameObjects.add(enemy.get(i));
			}
			numEnemies = 2;
			break;
		default:
			sceneQueue.add(new Menu(sceneQueue));
			isDone = true;
			break;
		}
		gameObjects.add(ship);

		makeStars(800);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Constants.WindowDims.width, Constants.WindowDims.height);
		g2d.setColor(Color.RED);

		// ship.paintComponent(g2d);
		if (!gameEnd) {
			drawCenteredString(g2d, "Score " + score, scoreFont, Constants.WindowDims.width / 2, 50);
			drawCenteredString(g2d, "Lives " + lives, scoreFont, Constants.WindowDims.width - 100, 50);
			drawCenteredString(g2d, "Level " + level, scoreFont, 100, 50);

			g2d.setColor(Color.WHITE);
			for (int i = 0; i < stars.size(); i++)
				g2d.fill(stars.get(i).hitbox);

			ship.paintComponent(g2d);

			for (int i = 0; i < ship.bullets.size(); i++) {
				ship.bullets.get(i).paintComponent(g);
			}

			for (int i = 0; i < gameObjects.size(); i++)
				gameObjects.get(i).paintComponent(g2d);

			for (int i = 0; i < animationManager.size(); i++)
				animationManager.get(i).paintComponent(g);
		} else {
			
			drawCenteredString(g2d, "Game Over. You scored: " + score, scoreFont, Constants.WindowDims.width / 2, 400);
			drawCenteredString(g2d, "Press space to return to menu", scoreFont, Constants.WindowDims.width / 2, 500);

		}

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

	public void update(double dt) {
		time += dt;
		if (!gameEnd) {
			if ((int) time != prevWhole) {
				score += 10;
				prevWhole = (int) time;
			}
			// ship.update(dt);
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
						enemy.get(q).isAlive = false;

						ship.bullets.remove(i);
						i--;

						Animation a = new Animation();
						a.explosion(enemy.get(q).centerX(), enemy.get(q).centerY(), Color.MAGENTA);
						animationManager.add(a);
						score += 100;
						numEnemies--;

					}

				}
			}
			for (int q = 0; q < enemy.size(); q++) {
				if (enemy.get(q).isAlive && ship.getPolygon(0).intersects(enemy.get(q).hitbox)) {
					lives--;
					enemy.get(q).isAlive = false;
					Animation a = new Animation();
					a.explosion(enemy.get(q).centerX(), enemy.get(q).centerY(), Color.MAGENTA);
					animationManager.add(a);
					ship.hurt();
					numEnemies--;
				}
			}

			for (int i = 0; i < animationManager.size(); i++) {
				animationManager.get(i).update(dt);
			}
			if (numEnemies == 0) {
				level++;
				sceneQueue.add(new TestScene(level, sceneQueue));
				isDone = true;
			}
			if (lives == 0)
				gameEnd = true;
		} else {
			if (Input.keysPressed[Constants.KEY_SPACE]) {
				sceneQueue.add(new Menu(sceneQueue));
				isDone = true;
			}
		}

		// tmp.update(dt);
		// System.out.println("here");
		// Syste,.doLayout();.println(ship.)
	}

}
