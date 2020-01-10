import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class TestScene extends Scene {
	Spaceship ship;
	
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	TrapezoidMotionProfile tmp = new TrapezoidMotionProfile(20, 4, 1, 1);
	TestScene() {
		super();
		
		ship = new Spaceship(400, 400, 10, 10);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0,Constants.WindowDims.width, Constants.WindowDims.height);
		//ship.paintComponent(g2d);
		g2d.setColor(Color.RED);
		drawMesh(g2d, ship.mesh, ship.model);
	}
	
	public void update(double dt) {
		ship.update(dt);
		tmp.update(dt);
		//System.out.println("here");
		//Syste,.doLayout();.println(ship.)
	}
	
}
