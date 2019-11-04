
public class Complex {
	double a;
	double bi;
	public Complex(double a, double bi) {
		this.a = a;
		this.bi = bi;
	}
	
	double mag() {
		return Math.sqrt(a * a + bi * bi);
	}
	
	Complex square() {
		return new Complex(a * a - bi*bi, 2 * a * bi);
		
	}
	Complex add(Complex c) {
		return new Complex(a + c.a, bi + c.bi);
	}
}
