import java.io.Serializable;

public class PongPacket implements Serializable {
	Ball ball;
	Paddle paddle;
	double sendTime;
	boolean master = false;
	
	public PongPacket(Ball b, Paddle p) {
		this.ball = b;
		this.paddle = p;
		this.sendTime = System.currentTimeMillis()/1000.0;
	}
	public PongPacket(Ball b, Paddle p, boolean master) {
		this.ball = b;
		this.paddle = p;
		this.sendTime = System.currentTimeMillis()/1000.0;
		this.master = master;
	}
	
	
}
