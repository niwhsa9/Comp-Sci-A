package graphics;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.awt.font.ShapeGraphicAttribute;

import javax.swing.JPanel;

import math.Mat;

public abstract class Scene extends JPanel implements KeyListener { //user creates scene instances that inherit from this e.g. BossFighScene
	GameObject[] gameobjects;
	Animation[] backgrounds;	
	//Sound[] sounds;
	int selectedBackground = 0;
	
	boolean debug = false;
	
	public Scene(int fpsMax, double worldX, double worldY, int displayX, int displayY) {
		
	}
	
	/* Render Vector Defined Game Object */
	public void drawPolygon(Graphics2D g2d, GameObject s) {
		/*
		Mat[] polyData, Mat transform
		Polygon p = new Polygon();
		for(int i = 0; i < polyData.length; i++) {
			//polyData[i] = Mat.dialationMat3x3(0.5).multiply(polyData[i]);
		    //polyData[i] = polyData[i].lmul(Mat.translationMat3x3(10, 10));
			Mat q = new Mat(3, 1, new double[] {polyData[i].getElem(0, 0), polyData[i].getElem(1, 0), polyData[i].getElem(2, 0)});
			q = q.lmul(model);
			p.addPoint((int)q.getElem(0, 0),(int)q.getElem(1, 0));
		}
		g2d.drawPolygon(p);
		*/
	}
	
	
	
	/* Turn on visual/print debuggers like FPS counter */
	public void debug(boolean state) {
		debug = state;
	}
	

}
