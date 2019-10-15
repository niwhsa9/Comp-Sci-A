import java.io.Serializable;

public class PongPacket implements Serializable {
	Ball ball;
	UserPaddle paddle;
	
	public PongPacket(Ball b, UserPaddle p) {
		this.ball = b;
		this.paddle = p;
	}
	
	
}
