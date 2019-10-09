import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Queue;

import javax.swing.JPanel;

public class Menu extends PongScene {
	public int selection = -1;
	Queue<Scene> sceneQueue;
    Font titleFont = new Font("Courier", Font.BOLD, 60);
    Font textFont = new Font("Courier", Font.BOLD, 14);

	
	public Menu(Queue<Scene> sceneQueue) {
		initGame(3);

		this.sceneQueue = sceneQueue;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setColor(Color.RED);
		drawCenteredString(g2d, "PONG", titleFont, Constants.WindowDims.width/2, 150);
		drawCenteredString(g2d, "by Ashwin Gupta", textFont, Constants.WindowDims.width/2, 160);
		drawCenteredString(g2d, "Select 1 for single player", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2);
		drawCenteredString(g2d, "Select 2 for local multiplayer", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50);
		
		drawCenteredString(g2d, "How to play: use w & s for player 1,",textFont, Constants.WindowDims.width/2, 500);
		drawCenteredString(g2d, "use up & down for player 2",textFont, Constants.WindowDims.width/2, 520);

		//g2d.drawString("Select 3 for Gravity Ball", 50, 300);
		//g2d.drawString("Select 4 for Phase Through Ball", 50, 400);
		


	}
	
	public void update(double dt) {
		super.update(dt);
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
