import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.JSlider;

public class Menu extends Scene {
	public int selection = -1;
	Queue<Scene> sceneQueue;
    Font titleFont = new Font("Courier", Font.BOLD, 60);
    Font textFont = new Font("Courier", Font.BOLD, 14);
    Font labelFont = new Font("Serif", Font.BOLD, 8);
    Font scoreFont = new Font("Serif", Font.BOLD, 30);



    JSlider gameLengthSlider = new JSlider(2, 20);
	
	public Menu(Queue<Scene> sceneQueue) {
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
		g2d.fillRect(0, 0, 1920, 1080);
		g2d.setColor(Color.RED);
		drawCenteredString(g2d, "Fractals", titleFont, Constants.WindowDims.width/2, 150);
		drawCenteredString(g2d, "by Ashwin Gupta", textFont, Constants.WindowDims.width/2, 160);
		drawCenteredString(g2d, "Select 1 for Squares", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2);
		drawCenteredString(g2d, "Select 2 for Perpendicular Lines", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50);
		drawCenteredString(g2d, "Select 3 for Spinny Tree", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50+50);
		drawCenteredString(g2d, "Select 4 for Sierpinski's Carpet", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50+100);
		drawCenteredString(g2d, "Select 5 for Sierpinski's Gasket", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50+150);
		drawCenteredString(g2d, "Select 6 for Cantor's Set", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50+200);
		drawCenteredString(g2d, "Select 7 for Koch Star", scoreFont, Constants.WindowDims.width/2, Constants.WindowDims.height/2+50+250);

	
		


	}
	
	public void update(double dt) {
		//System.out.println(gameLengthSlider.getValue());
		super.update(dt);
		//System.out.println("updating menu");
		if(Input.keysPressed[49]) {
			//System.out.println(Input.keysPressed[0]);
			selection = 1;
		}
		if(Input.keysPressed[50]) selection = 2;
		if(Input.keysPressed[51]) selection = 3;
		if(Input.keysPressed[52]) selection = 4;
		if(Input.keysPressed[53]) selection = 5;
		if(Input.keysPressed[54]) selection = 6;
		if(Input.keysPressed[55]) selection = 7;


		
		if(selection != -1) {
			Scene scene = null;
			switch(selection) {
				case 1:
					scene = new FractalScene1();
					break;
				case 2:
					scene = new FractalScene2();
					break;
				case 3:
					scene = new FractalScene3();
					break;
				case 4:
					scene = new FractalScene4();
					break;
				case 5:
					scene = new FractalScene5();
					break;
				case 6:
					scene = new FractalScene6();
					break;
				case 7:
					scene = new FractalScene7();
					break;
			}
			//s.maxScore = gameLengthSlider.getValue();
			sceneQueue.add(scene);
			sceneQueue.add(new Menu(sceneQueue));
			isDone = true;
		}
	}
}
