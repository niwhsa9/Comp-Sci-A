import java.io.Serializable;

public class UserPaddle extends Paddle {

	public int rightKey = Constants.KEY_D;
	public int leftKey = Constants.KEY_A;
	public boolean slave = false;
	
	public UserPaddle(double x, double y, double width, double height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void setKeys(int upKey, int downKey) {
		this.rightKey = upKey;
		this.leftKey = downKey;
	}
	
	public void update(double dt) {
	
		if(Input.keysPressed[rightKey]) {
			theta = 0;
			speed = activeSpeed;
		} else if(Input.keysPressed[leftKey]) {
			theta = Math.PI;
			speed = activeSpeed;
		} else speed = 0;
		
		super.update(dt);
		
		
	}

}
