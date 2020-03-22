import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;

public class GameObject implements Serializable{
	public Rectangle2D hitbox;
	public double speed, theta, phi, x, y, width, height, dx, dy, dphi, dialation;
	Color color;
	public double activeSpeed;
	double sendTime;
	
	public int[][] xData;
	public int[][] yData;
	Mat[][] mesh;
	boolean meshEnable = false;
	Mat model = new Mat(3, 3);//.rotationMat3x3(temp/205.0).lmul(Mat.translationMat3x3(temp, 500));;
	Color[] layers;
	Point2D cor;
	
	
	//enum PowerUp
	//double powerUpTimer 
	
	GameObject(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		hitbox = new Rectangle2D.Double(x, y, width, height);
		color = new Color(255, 255, 255);
	}
	
	GameObject(GameObject g) {
		this.hitbox = g.hitbox;
		this.activeSpeed = g.activeSpeed;
		this.speed = g.speed;
		this.theta = g.theta;
		phi = g.phi;
		dphi = g.dphi;
		x = g.x;
		y = g.y;
		width = g.width;
		height = g.height;
		dx = g.dx;
		dy = g.dy;
		dphi = g.phi;
		dialation = g.dialation;
		color = g.color;
		activeSpeed = g.activeSpeed;
		sendTime = g.sendTime;
		
		
	}
	
	GameObject(double x, double y, double w, double h, boolean meshEnable) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		hitbox = new Rectangle2D.Double(x, y, width, height);
		color = new Color(255, 255, 255);
		this.meshEnable = meshEnable;
	}
	
	public double centerX() {
		return x + width/2;
	}
	public double centerY() {
		return y + height/2;
	}
	
	public double topY() {
		return y;
	}
	
	public double bottomY() {
		return y + height;
	}
	
	public double leftX() {
		return x;
	}
	
	public double rightX() {
		return x + width;
	}
	
	public void setTopY(double y) {
		this.y = y; 
	}
	
	public void setBottomY(double y) {
		this.y = y - height;
	}
	
	public void setLeftX(double x) {
		this.x = x;
	}
	
	
	public void setRightX(double x) {
		this.x = x - width;
	}
	
	public void setCenterX(double x) {
		this.x = x - width/2;
	}
	
	public void setCenterY(double x) {
		this.x = this.y - height/2;
	}
	
	public void loadMesh() {
		mesh = new Mat[xData.length][];
		for(int layer = 0; layer < xData.length; layer++) {
			mesh[layer] = new Mat[xData[layer].length];
			for(int point = 0; point < xData[layer].length; point++) {
				mesh[layer][point] = new Mat(3, 1, new double[] { xData[layer][point], yData[layer][point], 1});
			}
		}
			
	}
	
	public void getMeshPoly() {
		
	}
	
	public boolean isVisible() {
		if( (leftX() <= Constants.WindowDims.width && rightX() >= 0) 
				&& (bottomY() >= 0  && topY() <= Constants.WindowDims.height)) return true;
		return false;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);

		
		
		if(phi == 0) g2d.fill(hitbox);
		else {
			AffineTransform transform = new AffineTransform();
			transform.rotate(phi, centerX(), centerY());
			AffineTransform original = g2d.getTransform();
			g2d.transform(transform);
			g2d.fill(hitbox);
			g2d.setTransform(original);
		}
		
		//else for(int i = 0; i < mesh.length; i++) g2d.drawPolygon(mesh[i]);
	
		
	}
	public void setDxDy(double dx, double dy) {
		speed = Math.sqrt(dx * dx + dy * dy);
		theta = Math.atan2(dy, dx);
	}
	
	public Mat getVeloVector() {
		return new Mat(3, 1, new double[] {dx, dy, 1});
	}
	
	public Polygon getPolygon(int l) {
		Polygon p = new Polygon();
		for(int i = 0; i < mesh[l].length; i++) {
		//polyData[i] = Mat.dialationMat3x3(0.5).multiply(polyData[i]);
	    //polyData[i] = polyData[i].lmul(Mat.translationMat3x3(10, 10));
		Mat q = new Mat(3, 1, new double[] {mesh[l][i].getElem(0, 0),mesh[l][i].getElem(1, 0), mesh[l][i].getElem(2, 0)});

		q = q.lmul(model);
		//System.out.println(q);

		p.addPoint((int)q.getElem(0, 0),(int)q.getElem(1, 0));
		}
		return p;
	}
	
	public void update(double dt) {
		dx = speed * Math.cos(theta) ;
		dy = speed * Math.sin(theta) ;
		phi += dphi * dt;
		x+=dx * dt;
		y+=dy * dt;
		hitbox.setRect(x, y, width, height);
		
		model = Mat.dialationMat3x3(dialation).lmul(Mat.rotationMat3x3(phi).lmul(Mat.translationMat3x3(x, y)));
		//System.out.println(model);
		
	}
	
	
	
	
}
