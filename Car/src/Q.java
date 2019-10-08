import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Q { //782x463
	
	public static class Panel extends JPanel {
		Mat[][] object = new Mat[XData.x.length][];
		Mat model = Mat.translationMat3x3(0, 0);
		int temp = 0;
		int centerX = 782/2;
		int centerY = 463/2;
		//Mat origin = new Mat(3, 1, new double[] {centerX, -centerY, 1});
		boolean once = false;
		
		
		final int rollLength = 5;
		double[] fps = new double[rollLength];
		int fpsNow = 0;
		long prevTime = 0;
		int c = 0;
		boolean fpsLock = true;
		
		
		public Panel() { 
			
			for(int i = 0; i < XData.x.length; i++) {
				object[i] = new Mat[XData.x[i].length];
				for(int q = 0; q < XData.x[i].length; q++) {
					object[i][q] = new Mat(3, 1, new double[] {XData.x[i][q]-centerX,YData.y[i][q]-centerY,1});
					//object[i][q] = object[i][q].lmul(Mat.dialationMat3x3(1.0)).lmul(Mat.translationMat3x3(500, 500));
					//model = 
					object[i][q] = object[i][q].lmul(Mat.dialationMat3x3(0.6));
				}
				
				
			}
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true) {
						try { 
							Thread.sleep(8);
							revalidate();
							repaint();
							if(fpsLock) {
								while(System.currentTimeMillis() - prevTime <= 14);
							}
							//o
							temp += 5;
							//model = model.lmul(origin);
							//model = Mat.translationMat3x3(temp, 0);
							//model = model.leftMultiply(new Mat() double[]{});
							model = Mat.rotationMat3x3(temp/200.0).lmul(Mat.translationMat3x3(temp, 500));
							
							//System.out.println("trying");
							fps[c%rollLength] = 1.0/((System.currentTimeMillis() - prevTime)/1000.0);
							prevTime = System.currentTimeMillis();
							c ++;
							//System.out.println(fps);
						} catch(Exception e) {};
					}
			}}).start();
		}
		
		@Override
		public void paintComponent(Graphics g) {

			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 1000, 1000);
			for(int i = 0; i < object.length; i++) {
				g2d.setColor(Color.RED);
				
				drawPolygon(g2d, object[i], null);
			}
			if(c % rollLength == 0) {
				fps = Sort.mergeSort(fps);
				fpsNow = (int) fps[rollLength/2];
			}
		    g2d.drawString(""+fpsNow, 100, 20);
			//once = true;
			
		}
		
		public void drawPolygon(Graphics2D g2d, Mat[] polyData, Mat transform) {
			Polygon p = new Polygon();
			for(int i = 0; i < polyData.length; i++) {
				//polyData[i] = Mat.dialationMat3x3(0.5).multiply(polyData[i]);
			    //polyData[i] = polyData[i].lmul(Mat.translationMat3x3(10, 10));
				Mat q = new Mat(3, 1, new double[] {polyData[i].getElem(0, 0), polyData[i].getElem(1, 0), polyData[i].getElem(2, 0)});
				q = q.lmul(model);
				p.addPoint((int)q.getElem(0, 0),(int)q.getElem(1, 0));
			}
			g2d.drawPolygon(p);
		}
		
	
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mat m1 = new Mat(1,3, new double[] {3,4,2});  
		Mat m2 = new Mat(3,4, new double[] {13,9,7,15,8,7,4,6,6,4,0,3});  
		
		
		System.out.println(m1);
		System.out.println(m2);
		System.out.println(m2.leftMultiply(m1));

		double[] d = Sort.mergeSort(new double[] {2, 1, 0, 8 , 12, 5, 2, 1, 0});
		for(int i = 0; i < d.length; i++) System.out.println(d[i]);
		
		//Mat testPt = new Mat(3, 1, new double[] {5, 5, 1});
		//System.out.println(testPt.lmul(Mat.rotationMat3x3(Math.PI/2)));
		
		
		JFrame window = new JFrame();
		window.setSize(1000, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		Panel j = new Panel();
		window.add(j);
		
		
		
		
	}

}