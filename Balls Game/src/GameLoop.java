import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.SwingUtilities;

public class GameLoop {

	static Window window;
	static Dimension windowDims = new Dimension(800 ,800);// = Dimension2D.;
	static Ball gametype = null;
	static Menu menu;

	// Scene[] scenes = new Scene[];
	public static void main(String[] args) {

		/* Graphics Thread */
		new Thread(new Runnable() {

			@Override
			public void run() {
				window = new Window("Game", (int)windowDims.getWidth(), (int)windowDims.getHeight());
				window.getContentPane().setPreferredSize(windowDims);
				window.pack();
				menu = new Menu();
				Input input = new Input();
				window.add(menu);
				window.addKeyListener(input);

				while(menu.selection < 0) {
					menu.update();
					menu.revalidate();
					menu.repaint();
				}
				window.remove(menu);
				
				if (menu.selection == 1) gametype = new BouncyBall(windowDims, null, 400, 400, 50, 50, -500, 300);	
			    if(menu.selection == 2) gametype = new UserBall(windowDims, null, 400, 400, 50, 50, 500);
			    if(menu.selection == 3) gametype = new GravityBall(windowDims, null, 300, 100, 50, 50, 100, 500);
			    if(menu.selection == 4)  gametype = new PhaseThroughBall(windowDims, null, 400, 400, 50, 50, 500);
			    
				window.add(gametype);
				while (true) {
					try {
						gametype.revalidate();
						gametype.repaint();
						Thread.sleep(10);
					} catch (Exception e) {
					}
				}
			}
		}).start();

		/* World Thread */
		while(gametype == null) System.out.print("");
		double prevTime = System.currentTimeMillis()/1000.0;
		while (true) {
			try {
				double currentTime = System.currentTimeMillis()/1000.0;
				gametype.update(currentTime-prevTime);
				prevTime = currentTime;
				Thread.sleep(2);
			} catch (Exception e) {
			}
		}

	}

}
