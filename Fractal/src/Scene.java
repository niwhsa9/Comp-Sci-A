import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Scene extends JPanel{
	boolean isDone = false; 
	public int GamePeriod = 2000 /*20*/;

	public Scene() {
		
	}
	
	public void update(double dt) {
	}
	
	public void delay(int millis) {
		long start = System.currentTimeMillis();
		int q = 0;
		while(System.currentTimeMillis() - start < millis) q++;
		
	}
	
	public void drawCenteredString(Graphics2D g2d, String s, Font f, int x, int y) {
		int w = g2d.getFontMetrics(f).stringWidth(s);
		int h = g2d.getFontMetrics(f).getHeight();
		g2d.setFont(f);
		g2d.drawString(s, x - w/2, y - h/2);

	}
	
}
