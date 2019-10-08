import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JPanel;

public class Ball extends GameObject{

	//enum PowerUp
	//double powerUpTimer 
	public boolean collisionLock = false;
	public double deflectionDir = 0; //set to 0 or math.pi
	public double startX;
	public double startY;
	
	Ball(double x, double y, double w, double h) {
		super(x, y, w, h);
		speed = Constants.BallVelocityNormal;
		theta = Math.PI;
		startX = x;
		startY = y;
	}
	
	public void reset() {
		this.x = startX;
		this.y = startY;
		this.theta = ((int)Math.round(Math.random()) == 1) ? 0 : Math.PI;
	}
	
	@Override
	public void update(double dt) {
		if(topY() <= 0) theta*=-1;
		if(bottomY() >= Constants.WindowDims.height) theta*=-1;

			
		super.update(dt);
	}
	
	
	
	
	
}
