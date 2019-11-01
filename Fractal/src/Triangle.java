import java.awt.Polygon;

public class Triangle {
	Line[] seg = new Line[3];
	Polygon drawable;
	public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		seg[0] = new Line(x1, y1, x2, y2);
		seg[1] = new Line(x2, y2, x3, y3);
		seg[2] = new Line(x3, y3, x1, y1);
		drawable = new Polygon(new int[] {x1, x2, x3}, new int[] {y1, y2, y3}, 3);
	}
	
	public Triangle(Mat p1, Mat p2, Mat p3) {
		seg[0] = new Line(p1, p2);
		seg[1] = new Line(p2, p3);
		seg[2] = new Line(p3, p1);
		drawable = new Polygon(new int[] {(int)p1.getElem(0, 0), (int)p2.getElem(0, 0), (int)p3.getElem(0, 0)}, 
				new int[] {(int)p1.getElem(1, 0), (int)p2.getElem(1, 0), (int)p3.getElem(1, 0)}, 3);

	}
	public Polygon getDrawable() {
		return drawable;
	}
}
