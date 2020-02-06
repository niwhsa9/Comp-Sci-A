import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Boss extends Enemy {
	Missile[] missile = new Missile[6];
	double prevMissile = 0;
	double prevBullet = 0;
	boolean hurt = false;
	double prevHurt = 0;
	double time = 0;
	int prevHealth = 0;
	Animation thrust;
	Animation debris;

	Boss(double x, double y, double w, double h, int level, int health) {

		super(x, y, w, h, 4, health);

		prevHealth = health;
		// thrust = new Animation();
		// thrust.fire(this);
		// TODO Auto-generated constructor stub
		missile[0] = new Missile(x + width, y, 20, 5, -Math.PI / 4, TestScene.ship);
		missile[1] = new Missile(x + width, y + height / 2, 20, 5, 0, TestScene.ship);
		missile[2] = new Missile(x + width, y + height, 20, 5, Math.PI / 4, TestScene.ship);
		missile[3] = new Missile(x, y, 20, 5, Math.PI + Math.PI / 4, TestScene.ship);
		missile[4] = new Missile(x, y + height / 2, 20, 5, Math.PI, TestScene.ship);
		missile[5] = new Missile(x, y + height, 20, 5, Math.PI - Math.PI / 4, TestScene.ship);
		debris = new Animation();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < missile.length; i++)
			missile[i].paintComponent(g);
		this.theta = Math.PI / 2;

		if (hurt) {
			// layers[0] = new Color(255, 0, 0, 50);
			g2d.setColor(new Color(255, 0, 0, 50));
			g2d.fillOval((int) x, (int) y, (int) width, (int) height);

		}

		debris.paintComponent(g);
	}

	public void update(double dt) {
		super.update(dt);
		debris.update(dt);
		time += dt;
		
		if(TestScene.ship.getPolygon(0).intersects(this.hitbox)) TestScene.ship.hurt(10);

		if (y < 200)
			speed = 50;
		else {
			speed = 0;

			if(health != prevHealth && hurt == false) {
				debris.directionalExplosion(centerX(), centerY(), Math.PI/2, Color.RED);
				debris.isDone = false;
				SoundDriver.playBreak();

			}
			if (health != prevHealth) {
				prevHealth = health;
				hurt = true;
				prevHurt = time;
			}
			if (time - prevHurt > 1.5)
				hurt = false;

			for (int i = 0; i < missile.length; i++)
				missile[i].update(dt);

			if (time - prevMissile > 3.0) {
				boolean flag = true;
				for (int i = 0; i < missile.length; i++) {
					if (missile[i] == null)
						break;
					if (missile[i].isDone != true) {
						flag = false;
						break;
					}

				}
				if (flag) {
					missile[0] = new Missile(x + width, y, 20, 5, -Math.PI / 4, TestScene.ship);
					missile[1] = new Missile(x + width, y + height / 2, 20, 5, 0, TestScene.ship);
					missile[2] = new Missile(x + width, y + height, 20, 5, Math.PI / 4, TestScene.ship);
					missile[3] = new Missile(x, y, 20, 5, Math.PI + Math.PI / 4, TestScene.ship);
					missile[4] = new Missile(x, y + height / 2, 20, 5, Math.PI, TestScene.ship);
					missile[5] = new Missile(x, y + height, 20, 5, Math.PI - Math.PI / 4, TestScene.ship);
					prevMissile = time;
				}
			}

			if (time - prevBullet > 3.0) {
				for (int i = 0; i < 10; i++) {
					prevBullet = time;
					GameObject bullet = new GameObject(centerX(), centerY(), 10, 5);
					bullet.theta = Math.random() * Math.PI * 2;
					bullet.speed = 300;
					bullet.phi = bullet.theta;
					bullet.color = Color.yellow;
					bullets.add(bullet);
				}
			}
		}

	}

}
