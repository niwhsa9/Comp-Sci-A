package graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import math.Mat;

public abstract class GameObject {
	/* Display */
	Mat[] mesh; 				 //point mesh matrixes for polygon or vector-defined objects
	BufferedImage[] sprite;      //spritesheet for raster-image defined objects
	boolean isMeshed = false;	 //choose if object is vector or raster
	Animation[] animations; 	 //various animation sequences associated with object
	//Sound[] sounds;
	
	/* Physics */
	Rectangle hitbox; 		     //rectangular hitbox for all objects
	Mat center; 			     //define object center offset in world coordinates
	Mat model; 					 //model matrix with translations 
	double prevTime;			 //last update time
	double deltaTime; 			 //delta time 
	
	
	
	abstract void update();		 //physics and logic update loop (fast)
	abstract void render();      //graphics update loop (slow) 
}
/*create enum in each child corresponding to states, create enum corresponding to sprite use*/
