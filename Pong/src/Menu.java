import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Queue;

import javax.swing.JPanel;

public class Menu extends Scene {
	public int selection = -1;
	Queue<Scene> sceneQueue;
	
	public Menu(Queue<Scene> sceneQueue) {
		this.sceneQueue = sceneQueue;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 1920, 1080);
		g2d.setColor(Color.RED);
	//	g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
		g2d.drawString("Select 1 for single player", 50, 100);
		g2d.drawString("Select 2 for local multiplayer", 50, 200);
		//g2d.drawString("Select 3 for Gravity Ball", 50, 300);
		//g2d.drawString("Select 4 for Phase Through Ball", 50, 400);



	}
	
	public void update(double dt) {
		if(Input.keysPressed[49]) {
			//System.out.println("here");
			selection = 1;
		}
		if(Input.keysPressed[50]) selection = 2;
		if(Input.keysPressed[51]) selection = 3;
		if(Input.keysPressed[52]) selection = 4;
		
		if(selection != -1) {
			PongScene s = new PongScene();
			s.initGame(selection);
			sceneQueue.add(s);
			sceneQueue.add(new Menu(sceneQueue));
			isDone = true;
		}
	}
}
