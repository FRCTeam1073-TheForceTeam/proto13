package frisbee;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import calcs.Calcs;
public class Drawer extends JComponent {
	private static final long serialVersionUID = 1L;
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Calcs calc = new Calcs();
		final int radius = 7;
		for (int n = 0; n < calc.X.length - 1; n++) {
			g2.draw(new Ellipse2D.Double(calc.X[n], calc.Y[n], radius, radius-5));
			
			
			for (int x = 1; x<20; x++){
				g2.drawString( x + " m", 63, 598-40*x);
				g2.drawString( x + " m", 100 + 40*x, 620);
				g2.draw(new Line2D.Double(100 + 40*x, 0, 100 + 40*x, 600));
				g2.draw(new Line2D.Double(100, 600 - 40*x, 860, 600 - 40*x));
			}
						
			//X axis
			g2.draw(new Line2D.Double( 100, 600, 1500, 600));
			
//			
//			g2.draw(new Line2D.Double( 200, 600, 200, 650));
//			g2.draw(new Line2D.Double( 300, 600, 300, 650));
//			g2.draw(new Line2D.Double( 400, 600, 400, 650));
//			g2.draw(new Line2D.Double( 500, 600, 500, 650));
//			g2.draw(new Line2D.Double( 600, 600, 600, 650));
//			g2.draw(new Line2D.Double( 700, 600, 700, 650));
//			g2.draw(new Line2D.Double( 800, 600, 800, 650));
//			g2.draw(new Line2D.Double( 900, 600, 900, 650));
//			g2.draw(new Line2D.Double( 1000, 600, 1000, 650));
//			g2.draw(new Line2D.Double( 1100, 600, 1100, 650));
//			g2.draw(new Line2D.Double( 1200, 600, 1200, 650));
//			g2.draw(new Line2D.Double( 1300, 600, 1300, 650));
			//Y axis
			g2.draw(new Line2D.Double( 100, 600, 100, 0));
			
//			
//			g2.draw(new Line2D.Double( 100, 550, 0, 550));
//			g2.draw(new Line2D.Double( 100, 500, 0, 500));
//			g2.draw(new Line2D.Double( 100, 450, 0, 450));
//			g2.draw(new Line2D.Double( 100, 400, 0, 400));
//			g2.draw(new Line2D.Double( 100, 350, 0, 350));
//			g2.draw(new Line2D.Double( 100, 300, 0, 300));
//			g2.draw(new Line2D.Double( 100, 250, 0, 250));
//			g2.draw(new Line2D.Double( 100, 200, 0, 200));
//			g2.draw(new Line2D.Double( 100, 150, 0, 150));
//			g2.draw(new Line2D.Double( 100, 100, 0, 100));
//			g2.draw(new Line2D.Double( 100, 50, 0, 50));
//			
			
//			g2.drawString("5 m", 70, 550);
//			g2.drawString("10 m", 70, 500);
//			g2.drawString("15 m", 70, 450);
//			g2.drawString("20 m", 70, 400);
//			g2.drawString("25 m", 70, 350);
//			g2.drawString("30 m", 70, 300);
//			g2.drawString("35 m", 70, 250);
//			g2.drawString("40 m", 70, 200);
//			g2.drawString("45 m", 70, 150);
//			g2.drawString("50 m", 70, 100);
//			g2.drawString("11 m", 70, 50);
//			g2.drawString("12 m", 70, 0);
			
//			g2.drawString("1 m", 200, 620);
//			g2.drawString("2 m", 300, 620);
//			g2.drawString("3 m", 400, 620);
//			g2.drawString("4 m", 500, 620);
//			g2.drawString("5 m", 600, 620);
//			g2.drawString("6 m", 700, 620);
//			g2.drawString("7 m", 800, 620);
//			g2.drawString("8 m", 900, 620);
//			g2.drawString("9 m", 1000, 620);
//			g2.drawString("10 m", 1100, 620);
//			g2.drawString("11 m", 1200, 620);
//			g2.drawString("12 m", 1300, 620);	
		}
	}
}
