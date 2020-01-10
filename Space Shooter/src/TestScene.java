import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class TestScene extends Scene {
	Spaceship ship;
	
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	ArrayList<GameObject> stars = new ArrayList<GameObject>();
	Random rn = new Random();
	
	public void makeStars(int n) {
		for(int i = 0; i < n; i++) {
			double x = Math.random() * Constants.WindowDims.width;
			double y = Math.random() * Constants.WindowDims.height;
			double size = rn.nextInt(4);
			double width = 1.5 * size; 
			double height = 1.5 * size;
			double theta = Math.PI/2;
			double speed = 20 * size;
			GameObject star = new GameObject(x, y, width, height);
			star.theta = theta;
			star.speed = speed;
			star.color = Color.white;
			stars.add(star);
		}
	}
	
	TestScene() {
		super();
		
		ship = new Spaceship(400, 400, 10, 10);
		makeStars(200);
	}
	
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0,Constants.WindowDims.width, Constants.WindowDims.height);
		//ship.paintComponent(g2d);
		
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < stars.size(); i++) g2d.fill(stars.get(i).hitbox);
		
		g2d.setColor(Color.RED);
		fillMesh(g2d, ship.mesh, ship.model);
	}
	
	public void update(double dt) {
		ship.update(dt);
		System.out.println(stars.size());
		for(int i = 0; i < stars.size(); i++) {
			if(stars.get(i).isVisible() == false) {
				//stars.remove(i);
				//i--;
				stars.get(i).setBottomY(0);
			}
			else stars.get(i).update(dt);
		}
		
		//tmp.update(dt);
		//System.out.println("here");
		//Syste,.doLayout();.println(ship.)
	}
	
}
