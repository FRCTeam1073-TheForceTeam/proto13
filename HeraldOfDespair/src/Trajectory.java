import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;

public class Trajectory extends JFrame implements ChangeListener {

	GraphPanel graph = new GraphPanel();
	Calcs calc = new Calcs();

	JSlider velocityx10 = new JSlider(JSlider.VERTICAL, 0, 200, 100);
	JSlider angle = new JSlider(JSlider.VERTICAL, 0, 90, 20);
	

	public Trajectory() {
		super("Disc Trajectory Model");
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// Add listeners for the different sliders

		velocityx10.setMajorTickSpacing(20);
		velocityx10.setMinorTickSpacing(5);
		velocityx10.setPaintLabels(true);
		velocityx10.setPaintTicks(true);
		
		angle.setMajorTickSpacing(20);
		angle.setMinorTickSpacing(5);
		angle.setPaintLabels(true);
		angle.setPaintTicks(true);
		
		velocityx10.addChangeListener(this);
		angle.addChangeListener(this);

		JLabel vLabel = new JLabel("Velocity, m/s");
		JLabel aLabel = new JLabel("Theta, degrees");

		GridLayout grid = new GridLayout(1,3);

		FlowLayout right = new FlowLayout(FlowLayout.LEFT);

		Container pane = getContentPane();

		pane.setLayout(grid);

		JPanel vPanel = new JPanel();
		vPanel.setLayout(right);
		vPanel.add(vLabel);
		vPanel.add(velocityx10);
		pane.add(vPanel);

		JPanel aPanel = new JPanel();
		aPanel.setLayout(right);
		aPanel.add(aLabel);
		aPanel.add(angle);
		pane.add(aPanel);

		pane.add(graph);

		setContentPane(pane);
	}

	public void stateChanged(ChangeEvent evt) {
		JSlider source = (JSlider) evt.getSource();
		if (source.getValueIsAdjusting() != true) {
			calc.X.clear();
			calc.Y.clear();
			graph.updateGraph(velocityx10.getValue()/10, angle.getValue());
			graph.validate();
			graph.repaint();
		}
	}

	class GraphPanel extends JPanel {
		double velocity;
		double theta;

		GraphPanel() {
			velocity = 100;
			theta = 20;
		}

		public void paintComponent(Graphics g) {
			
			final int BASE = 600;
			final int SCALE = 50;
			
			Graphics2D g2 = (Graphics2D) g;
			calc.simulate(velocity, theta);
			final int radius = 10;
			g2.setColor(Color.white);
			g2.fillRect(0, 0, 700, 700);
			g2.setColor(Color.black);
			// Make a graph
			
			for(int n = 0; n <= BASE/SCALE; n++) {
				g2.drawLine(50 + n*SCALE, BASE,50 + n*SCALE, 0);
				g2.drawString(n+"", 50 + n*SCALE, 620);
				g2.drawLine(50, n*SCALE, BASE+50, n*SCALE);
				g2.drawString((BASE/SCALE-n)+"", 20, n*SCALE);
			}
			g2.drawString("Units: Meters", 325, 640);
			
			// Plot points
			for (int n = 0; n < calc.X.size() - 1; n++) 
				g2.drawOval((int)(50+SCALE*calc.X.get(n)), (int)(BASE - SCALE*calc.Y.get(n)), radius, radius-5);
		}

		void updateGraph(double Velocity, double Theta) {
			velocity = Velocity;
			theta = Theta;
		}
	}

	public static void main(String[] arguments) {
		Trajectory cs = new Trajectory();
	}
}