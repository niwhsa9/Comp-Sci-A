
public class UserPaddle extends Paddle {

	public int upKey = Constants.KEY_S;
	public int downKey = Constants.KEY_W;
	
	public UserPaddle(double x, double y, double width, double height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void setKeys(int upKey, int downKey) {
		this.upKey = upKey;
		this.downKey = downKey;
	}
	
	public void update(double dt) {
		if(Input.keysPressed[upKey]) {
			theta = Math.PI/2;
			speed = Constants.PaddleVelocityNormal;
		} else if(Input.keysPressed[downKey]) {
			theta = Math.PI * 3.0/2.0;
			speed = Constants.PaddleVelocityNormal;
		} else speed = 0;
		super.update(dt);
		
		
	}

}
