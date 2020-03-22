import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Snake extends GameObject {
	ArrayList<Vec2> turningPoint = new ArrayList<Vec2>();
	Snake(Color c) {
		super(300, 300, Constants.SNAKE_WIDTH, 0);
		this.color = c;
		this.speed = Constants.SNAKE_SPEED_NORMAL;
		turningPoint.add(new Vec2(x, y));
		this.theta = Math.PI/2;
	}
	
	
	
	void fillBodyRect(Graphics2D g2d, Vec2 u, Vec2 v, int i) {
		Vec2 seg = v.sub(u);
		int segMag = (int)seg.mag();
		Vec2 segDir = seg.normalize();
		
		int xp =(int) Math.min(u.x, v.x) ;
		int yp = (int) Math.min(u.y, v.y);
		int w = (int)((Math.abs(segDir.x) == 1) ? segMag : width);
		int h = (int)((Math.abs(segDir.y) == 1) ? segMag : width);
		
		xp -= ((Math.abs(segDir.x) == 1) ? 0 : width/2); 
		yp -= ((Math.abs(segDir.y) == 1) ? 0 : width/2); 

		g2d.fillRect(xp, yp, w, h);
		
		//if(i == turningPoint.size()-1) System.out.println( (xp + w) + " " + (yp + h) );
		
		if(i == turningPoint.size()-1)System.out.println(seg.mag());
		
	}
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int q = turningPoint.size(); 

		synchronized (this) {
			turningPoint.add(new Vec2(x, y));
		}
		
		g2d.setColor(color);
		
		for(int i = 1; i < turningPoint.size(); i++) {
			fillBodyRect(g2d,turningPoint.get(i-1), turningPoint.get(i), i);
			//if(i == 3) System.out.println(turningPoint.get(i-1).sub(turningPoint.get(i)).mag());
			//g2d.fillRect((int)turningPoint.get(i-1).x, (int)turningPoint.get(i-1).y, (int)(segMag*wMask) + wM, (int)(segMag*hMask) + hM);
			//fillRect(g2d, (int)turningPoint.get(i-1).x - (width/2)*Math.abs(hMask), (int)turningPoint.get(i-1).y - (width/2)*Math.abs(wMask), (segMag*wMask) + wM, (segMag*hMask) + hM);
			//System.out.println("x: " + turningPoint.get(i-1).x + " y " + turningPoint.get(i-1).y + " w: " + width*wMask + wM + " h " + width*hMask + hM);
			
			
			g2d.fillRect( (int)(turningPoint.get(i).x - width/2 ), (int)(turningPoint.get(i).y - width/2), (int)width, (int)width);

		}
		
		
		synchronized (this) {
			turningPoint.remove(q);
		}
	}
	
	public void update(double dt) {
		super.update(dt);
		if(Input.keysPressed[Constants.KEY_D] && theta != 0) {
			theta = 0; 
			synchronized(this) {
				turningPoint.add(new Vec2(x, y));
				
			}
		}
		if(Input.keysPressed[Constants.KEY_W] && theta != 3*Math.PI/2) {
			theta = 3*Math.PI/2; 
			synchronized(this) {
				//turningPoint.add(new Vec2(x, y));

				turningPoint.add(new Vec2(x, y));
			
			}
		}
		if(Input.keysPressed[Constants.KEY_A] && theta != Math.PI) {
			theta = Math.PI; 
			synchronized(this) {
				turningPoint.add(new Vec2(x, y));
				
			}
		}
		if(Input.keysPressed[Constants.KEY_S] && theta != Math.PI/2) {
			theta = Math.PI/2; 
			synchronized(this) {
				turningPoint.add(new Vec2(x, y));
			
			}
		}
		//System.out.println("updating " + x + " , " + y);
	}
	

}
