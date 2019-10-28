import java.awt.Point;
import java.awt.geom.Line2D;

public class Line {
	Mat start;
	Mat end;
	double thick;
	public Line(Mat start, Mat end) {
		this.start = start;
		this.end = end;
	}
	
	public Line(double x0, double y0, double x1, double y1) {
		start = new Mat(3, 1, new double[] {x0, y0, 1});
		end = new Mat(3, 1, new double[] {x1, y1, 1});
		//new Point()
	}
	
	public Line2D getDrawable() {
		return new Line2D.Double(new Point((int)start.getElem(0, 0), (int)start.getElem(1, 0)), new Point((int)end.getElem(0, 0), (int)end.getElem(1, 0)));
	}
	
	public double getMag() {
		Mat vec = end.sub(start);
		return Math.sqrt(vec.getElem(0, 0) * vec.getElem(0, 0) + vec.getElem(1, 0) * vec.getElem(1, 0)); 
	}
	
	public Mat unit() {
		return (end.sub(start)).multiply(1.0/getMag());
	}
	
	public static Mat getUnitVecInDir(double theta) {
		return new Mat(3, 1, new double[] {Math.cos(theta), Math.sin(theta), 1});
	}
	
	//public static Mat 
}
