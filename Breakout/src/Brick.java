import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Brick extends GameObject {
	boolean isAlive = true;
	boolean isVisible = true;
	int lives;
	
	BufferedImage brick;
	
	Brick(double x, double y, double w, double h) {
		super(x, y, w, h);
		this.color = Color.blue;
		Random rn = new Random();
		int q = rn.nextInt(5);
		switch(q) {
			case 0: 
				color = new Color(1.0f, 0.0f, 0.0f);
				break;
			case 1:
				color = new Color(0.0f, 1.0f, 0.0f);
				break;
			case 2:
				color = new Color(0.0f, 0.0f, 1.0f);
				break;
			case 3: 
				color = new Color(1.0f, 0.0f, 1.0f);
				break;
			case 4: 
				color = new Color(1.0f, 1.0f, 0.0f);
				break;
		}
		//brick = new 
	}
	
	public void paintComponent(Graphics g) {
		if(isVisible) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		//g2d.setStroke(new BasicStroke(2));
		g2d.draw(this.hitbox);
		}
	}
	
	
	//in paint component, outline brcks
	
	public void hit() {
		isAlive = false;
		setDxDy(0, Constants.BrickFallDy);
		
		//color = new Color(0.0f, 0.0f, 1.0f, 0.4f);
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
	}
	
	public static Brick[] generateBricks(int col, int row, int gapFreq, int gapLen) {
		Brick[] brick = new Brick[col * row/* - 50 /*- (col*row)/gapFreq * gapLen*/];
		int gapW = 5;
		int gapH = 5;
		int width = Constants.WindowDims.width/(col) - gapW;
		int height = (int) (width * 0.5); 
		
		
		for(int r = 0; r < row; r++) {
			for(int c = 0; c < col; c++) {
				brick[r*col + c] = new Brick(c * width + gapW * c, r * height + gapH * r, width, height);
				if((c*r) % gapFreq <= gapLen - 1) {
					brick[r*col+c].isVisible = false;
					brick[r*col+c].isAlive = false;
				}
			}
		}
		return brick;
		
	}

}