import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class Menu extends PongScene {
	public int selection = -1;
	Queue<Scene> sceneQueue;
    Font titleFont = new Font("Courier", Font.BOLD, 60);
    Font textFont = new Font("Courier", Font.BOLD, 14);
    Font labelFont = new Font("Serif", Font.BOLD, 8);


    JSlider gameLengthSlider = new JSlider(2, 20);
	
	public Menu(Queue<Scene> sceneQueue) {
		initGame(3);

		this.sceneQueue = sceneQueue;
		/*
		setLayout(null);
		gameLengthSlider.setBounds(Constants.WindowDims.width/2-100, Constants.WindowDims.height/2+150, 200, 40);
		add(gameLengthSlider, 0, 0);
		gameLengthSlider.setPaintLabels(true);
		gameLengthSlider.setPaintTicks(true);
	    gameLengthSlider.setPaintTrack(true);
	    gameLengthSlider.setFont(labelFont);
	    gameLengthSlider.setLabelTable(gameLengthSlider.createStandardLabels(2));

	    gameLengthSlider.setMajorTickSpacing(2);
	    gameLengthSlider.setMinorTickSpacing(1); */

		/*slider1.setMajorTickSpacing(100);
	       slider1.setMinorTickSpacing(25);
	       slider1.setPaintLabels(true);
	       slider1.setPaintTicks(true);
	       slider1.setPaintTrack(true);
	       slider1.setAutoscrolls(true);*/
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
		drawCenteredString(g2d, "Select 4 for online multiplayer (L)", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50+50);
		drawCenteredString(g2d, "Select 5 for online multiplayer (R)", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50+100);

		drawCenteredString(g2d, "How to play: use w & s for player 1,",textFont, Constants.WindowDims.width/2, 600);
		drawCenteredString(g2d, "use up & down for player 2",textFont, Constants.WindowDims.width/2, 620);

		//g2d.drawString("Select 3 for Gravity Ball", 50, 300);
		//g2d.drawString("Select 4 for Phase Through Ball", 50, 400);
		


	}
	
	public void update(double dt) {
		//System.out.println(gameLengthSlider.getValue());
		super.update(dt);
		if(Input.keysPressed[49]) {
			//System.out.println("here");
			selection = 1;
		}
		if(Input.keysPressed[50]) selection = 2;
		if(Input.keysPressed[51]) selection = 3;
		if(Input.keysPressed[52]) selection = 4;
		if(Input.keysPressed[53]) selection = 5;

		
		if(selection != -1) {
			PongScene s = new PongScene();
			s.initGame(selection);
			//s.maxScore = gameLengthSlider.getValue();
			sceneQueue.add(s);
			sceneQueue.add(new Menu(sceneQueue));
			isDone = true;
		}
	}
}
