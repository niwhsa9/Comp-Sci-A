
public class Vec2 {
	double x; 
	double y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y; 
	}
	
	public String toString() {
		return "<" + x + ", " + y + ">";
	}
	
	double dot(Vec2 v) {
		return v.x * x + v.y * y;
	}
	
	double mag() {
		return Math.sqrt(this.x*this.x + this.y*this.y);
	}
	
	Vec2 normalize() {
		double m = mag();
		if(m == 0) return new Vec2(0, 0);
		return new Vec2(this.x/m, this.y/m);
	}
	
	Vec2 add(Vec2 v) {
		return new Vec2(this.x + v.x, this.y + v.y);
	}
	
	Vec2 sub(Vec2 v) {
		return new Vec2(this.x - v.x, this.y - v.y);
	}
	
	
	
	
}
