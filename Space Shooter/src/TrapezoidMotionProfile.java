
public class TrapezoidMotionProfile {
	
	
	double maxVelo;
	double maxAccel;
	double posSetpoint;
	double maxDecel;
	double time = 0;
	double prevVelo = 0;
	double velo = 0;
	double accel = 0;
	double distance = 0;
	
	TrapezoidMotionProfile(double posSetpoint, double maxVelo, double maxAccel, double maxDecel) {
		this.posSetpoint = posSetpoint;
		this.maxVelo = maxVelo;
		this.maxAccel = maxAccel;
		this.maxDecel = maxDecel*-1;
		accel = maxAccel;
	}
	
	public double update(double dt) {
		time+=dt;
		distance+= prevVelo * dt;
		//check if I need to start slowing down 
		//System.out.println(posSetpoint-distance);
		//System.out.println((velo * velo/(2*maxDecel)));
		if( (-velo * velo/(2*maxDecel)) >= posSetpoint - distance) {
			accel = maxDecel;
			System.out.println("here");
		}
		//check if I've reached v-max
		else if(velo >= maxVelo) accel = 0;
		
		velo += accel * dt;
		
		prevVelo = velo;
		System.out.println("dist: " + distance + " velo: " + velo + " accel: " + accel);
		return velo;
	}
}
