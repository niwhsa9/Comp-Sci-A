import java.awt.Polygon;

public class Spaceship extends GameObject {
	
	boolean risingEdgeThrust = false;
	TrapezoidMotionProfile tmp = new TrapezoidMotionProfile(0, 0, 0, 0);

	Spaceship(double x, double y, double w, double h) {
		super(x, y, w, h, true);
		// TODO Auto-generated constructor stub
		xData = new int[2][];
		yData = new int[2][];
		xData[0] = new int[]{0, 1, 1, 2, 3, 4, 7, 7, 6, 5, 4, 4, 3, 3, 2, 2, 1, 1, 2, -2, -1, -1, -2, -2, -3, -3, -4, -4, -5, -6, -7, -7, -4, -3, -2, -1, -1, 0};
		yData[0] = new int[]{7, 5, 3, 2, 2, 1, 1, -1, -1, 0, 0, -2, -2, 0, -1, -3, -4, -5, -6, -6, -5, -4, -3, -1, 0, -2, -2, 0, 0, -1, -1, 1, 1, 2, 2, 3, 5, 7};
		xData[1] = new int[] {-1, 1, 1, -1};
		yData[1] = new int[] {5, 5, 4, 4};
		
		loadMesh();
		dialation = 10.0;
		
		//phi = Math.PI/2;
		//dphi = Math.PI/5;
		
		
	}
	
	public void update(double dt) {
		
		
		if(Input.keysPressed[Constants.KEY_THRUST] && risingEdgeThrust == false && tmp.getPercent()>0.9) {
			tmp = new TrapezoidMotionProfile(300, 500, 1200, 500);
		}
		//else speed = 0;
		
		if(Input.keysPressed[65]) dphi = Constants.SHIP_ROTATE_VELO;
		else if (Input.keysPressed[68]) dphi = -Constants.SHIP_ROTATE_VELO;
		else dphi = 0;
		
		theta = phi + Math.PI/2;
		speed = tmp.update(dt);

		
		risingEdgeThrust = Input.keysPressed[Constants.KEY_THRUST];
		super.update(dt);
	}

	
}
