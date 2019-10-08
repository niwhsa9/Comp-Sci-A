package core;

import javax.swing.SwingUtilities;

import graphics.Scene;
import graphics.Window;

public class GameLoop {
	static Window window;
	//Scene[] scenes = new Scene[];
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				window = new Window("Game", 500, 500);
			}
		});
	}

}
//pass dt
//control fps if enabled