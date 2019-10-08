import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Menu extends JPanel {
	public int selection = -1;
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 1920, 1080);
		g2d.setColor(Color.RED);
	//	g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
		g2d.drawString("Select 1 for Bouncy Ball", 50, 100);
		g2d.drawString("Select 2 for User Control Ball", 50, 200);
		g2d.drawString("Select 3 for Gravity Ball", 50, 300);
		g2d.drawString("Select 4 for Phase Through Ball", 50, 400);



	}
	
	void update() {
		if(Input.keysPressed[49]) {
			//System.out.println("here");
			selection = 1;
		}
		if(Input.keysPressed[50]) selection = 2;
		if(Input.keysPressed[51]) selection = 3;
		if(Input.keysPressed[52]) selection = 4;

	}
}
