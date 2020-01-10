import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
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
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		
		if(meshEnable == false) g2d.fill(hitbox);
		//else for(int i = 0; i < mesh.length; i++) g2d.drawPolygon(mesh[i]);
	
		
	}
	public void setDxDy(double dx, double dy) {
		speed = Math.sqrt(dx * dx + dy * dy);
		theta = Math.atan2(dy, dx);
	}
	
	public void update(double dt) {
		dx = speed * Math.cos(theta) * dt;
		dy = speed * Math.sin(theta) * dt;
		phi += dphi * dt;
		x+=dx;
		y+=dy;
		hitbox.setRect(x, y, width, height);
		
		model = Mat.dialationMat3x3(dialation).lmul(Mat.rotationMat3x3(phi).lmul(Mat.translationMat3x3(x, y)));
		//System.out.println(model);
		
	}
	
	
	
	
}
