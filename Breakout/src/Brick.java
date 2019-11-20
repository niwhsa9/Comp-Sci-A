import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Brick extends GameObject {
	boolean isAlive = true;
	
	BufferedImage brick;
	
	Brick(double x, double y, double w, double h) {
		super(x, y, w, h);
		this.color = Color.blue;
		//brick = new 
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.setStroke(new BasicStroke(5));
		g2d.draw(this.hitbox);
	}
	
	//in paint component, outline brcks
	
	public void hit() {
		isAlive = false;
		setDxDy(0, Constants.BrickFallDy);
	}
	
	public static Brick[] generateBricks(int col, int row, int gapFreq, int gapLen) {
		Brick[] brick = new Brick[col * row /*- gapFreq * gapLen*/];
		int width = Constants.WindowDims.width/col;
		int height = (int) (width * 0.5); 
		for(int r = 0; r < row; r++) {
			for(int c = 0; c < col; c++) {
				brick[r*col + c] = new Brick(c * width, r * height, width, height);
			}
		}
		return brick;
		
	}

}
