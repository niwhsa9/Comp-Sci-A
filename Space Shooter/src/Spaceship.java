import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Spaceship extends GameObject {
	
	boolean risingEdgeThrust = false;
	boolean risingEdgeShoot = false;

	TrapezoidMotionProfile tmp = new TrapezoidMotionProfile(0, 0, 0, 0);
	ArrayList<GameObject> particles = new ArrayList<GameObject>();
	Mat thrustDir;
	ArrayList<GameObject> bullets = new ArrayList<GameObject>();
	Animation fire;
	
	int absX;
	int absY;


	Spaceship(double x, double y, double w, double h) {
		super(x, y, w, h, true);
		// TODO Auto-generated constructor stub
		xData = new int[2][];
		yData = new int[2][];
		layers = new Color[2];
		xData[0] = new int[]{0, 1, 1, 2, 3, 4, 7, 7, 6, 5, 4, 4, 3, 3, 2, 2, 1, 1, 2, -2, -1, -1, -2, -2, -3, -3, -4, -4, -5, -6, -7, -7, -4, -3, -2, -1, -1, 0};
		yData[0] = new int[]{7, 5, 3, 2, 2, 1, 1, -1, -1, 0, 0, -2, -2, 0, -1, -3, -4, -5, -6, -6, -5, -4, -3, -1, 0, -2, -2, 0, 0, -1, -1, 1, 1, 2, 2, 3, 5, 7};
		xData[1] = new int[] {-1, 1, 1, -1};
		yData[1] = new int[] {5, 5, 4, 4};
		layers[0] = new Color(128, 128, 128);
		layers[1] = Color.blue;
		
		loadMesh();
		dialation = 10.0;
		
		
		fire = new Animation();

		update(0.01);
		
		fire.fire(this);
		
		//phi = Math.PI/2;
		//dphi = Math.PI/5;
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fire.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		Scene.fillMesh(g2d, mesh, model, layers);
	
		g2d.setColor(Color.BLUE);

		//g2d.fill(getPolygon(0));
	//	g2d.fill(hitbox);

	}
	
	public void update(double dt) {
		fire.update(dt);
		
		//Mat lCorner = new Mat(3, 1, new double[] {x})
		
		if(Input.keysPressed[Constants.KEY_THRUST] && risingEdgeThrust == false && tmp.getPercent()>0.9) {
			tmp = new TrapezoidMotionProfile(300, 500, 1200, 500);
			thrustDir = new Mat(3, 1, new double[] {Math.cos(theta), Math.sin(theta), 1});
			dphi = 0;
			
		} 
		//else speed = 0;
		if(tmp.getPercent()>=0.98) {
			if(Input.keysPressed[65]) dphi = -Constants.SHIP_ROTATE_VELO;
			else if (Input.keysPressed[68]) dphi = Constants.SHIP_ROTATE_VELO;
			else dphi = 0;
		} else {
			if(Input.keysPressed[65]) dphi = -Constants.SHIP_ROTATE_VELO/4;
			else if (Input.keysPressed[68]) dphi = Constants.SHIP_ROTATE_VELO/4;
			else dphi = 0;
		}
		
		if(Input.keysPressed[Constants.KEY_SHOOT] && risingEdgeShoot == false) {
			Mat posL = new Mat(3, 1, new double[] {- 30, 20, 1}); 
			Mat posR = new Mat(3, 1, new double[] {30, 20, 1}); 
			posL = posL.lmul(Mat.rotationMat3x3(phi));
			posR = posR.lmul(Mat.rotationMat3x3(phi));

			
			GameObject bulletL = new GameObject(x+posL.getElem(0, 0),y+posL.getElem(1, 0), Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
			GameObject bulletR = new GameObject(x+posR.getElem(0, 0),y+posR.getElem(1, 0), Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT);
			bulletL.phi = phi;
			bulletL.color = Color.green;
			bulletL.speed = Constants.BULLET_SPEED;
			bulletL.theta = theta;
			
			bulletR.phi = phi;
			bulletR.color = Color.green;
			bulletR.speed = Constants.BULLET_SPEED;
			bulletR.theta = theta;
			
			bulletL.cor = new Point2D.Double(x, y);
			bulletR.cor = new Point2D.Double(x, y);

			//bullet.clone();
			bullets.add(bulletL);
			bullets.add(bulletR);
		}
		
		theta = phi + Math.PI/2;
		speed = tmp.update(dt);
		
		Polygon p = getPolygon(0);
		if(p.intersects(new Rectangle(0, 0, Constants.WindowDims.width, 1))) {
			y = 80;
			tmp.isDone = true;
		} else if(p.intersects(new Rectangle(0, 0, 1, Constants.WindowDims.height))) {
			x = 80;
			tmp.isDone = true;
		} else if(p.intersects(new Rectangle(0, Constants.WindowDims.height , Constants.WindowDims.width, 1))) {
			y = Constants.WindowDims.height-80;
			tmp.isDone = true;
		} else if(p.intersects(new Rectangle(Constants.WindowDims.width, 0, 1, Constants.WindowDims.height))) {
			x = Constants.WindowDims.width-80;
			tmp.isDone = true;
		}
		
		
		risingEdgeThrust = Input.keysPressed[Constants.KEY_THRUST];
		risingEdgeShoot = Input.keysPressed[Constants.KEY_SHOOT];
		super.update(dt);
		
		//DISPLAY BOOST PARTICLE (BLUE CIRCLES) WEHN BOOSTING, DIMINISHES AS BOOST ENDS (SEED WITH VELO)
		//DISPLAY SMALL FIRE PARTICLES OTHERWISE WHEN STATIONARY 
	}

	
}
