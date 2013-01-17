import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;

public class Trajectory extends JFrame implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 635305340570780384L;
	GraphPanel graph = new GraphPanel();
	Calcs calc = new Calcs();
	Calcs overshoot = new Calcs();
	Calcs undershoot = new Calcs();

	JSlider velocityx10 = new JSlider(JSlider.HORIZONTAL, 0, 2000, 1000);
	JSlider angle = new JSlider(JSlider.HORIZONTAL, 0, 90, 20);

	public Trajectory() {
		super("Disc Trajectory Model");
		setSize(975, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// Add listeners for the different sliders

		velocityx10.setMajorTickSpacing(250);
		velocityx10.setMinorTickSpacing(50);
		velocityx10.setPaintLabels(true);
		velocityx10.setPaintTicks(true);
		// Dimension d = velocityx10.getMaximumSize() / 20;
		// velocityx10.setPreferredSize(d);

		angle.setMajorTickSpacing(20);
		angle.setMinorTickSpacing(5);
		angle.setPaintLabels(true);
		angle.setPaintTicks(true);

		velocityx10.addChangeListener(this);
		angle.addChangeListener(this);

		JLabel vLabel = new JLabel("Velocity, rpm", JLabel.CENTER);
		JLabel aLabel = new JLabel("Theta, degrees", JLabel.CENTER);

		Container pane = getContentPane();

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		JPanel title = new JPanel();
		title.setMaximumSize(new Dimension(1000,300));
		JLabel text = new JLabel("Team 1073's Disc Physics Simulator", JLabel.CENTER);
		text.setFont(new Font("bla", Font.BOLD, 35));
		title.add(text);
		pane.add(title);
		
		JPanel vPanel = new JPanel();
		vPanel.setMaximumSize(new Dimension(1000,200));
		GridLayout gl = new GridLayout(2, 2);
		gl.setHgap(100);
		vPanel.setLayout(gl);
		vPanel.add(vLabel);
		vPanel.add(aLabel);
		vPanel.add(velocityx10);
		vPanel.add(angle);
		pane.add(vPanel);

		pane.add(Box.createRigidArea(new Dimension(25,25)));
		
		pane.add(graph);

		setContentPane(pane);
	}

	public void stateChanged(ChangeEvent evt) {
		JSlider source = (JSlider) evt.getSource();
		if (source.getValueIsAdjusting() != true) {
			calc.X.clear();
			calc.Y.clear();
			overshoot.X.clear();
			overshoot.Y.clear();
			undershoot.X.clear();
			undershoot.Y.clear();
			graph.updateGraph(velocityx10.getValue(), angle.getValue());
			graph.validate();
			graph.repaint();
		}
	}

	class GraphPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		double velocity;
		double theta;

		GraphPanel() {
			velocity = 1000;
			theta = 20;
		}

		public void paintComponent(Graphics g) {

			// Centering grid
			final int START = (getWidth()/2)-300;
			// grid length of 600
			final int BASE = 600;
			// 50 by 50 grids
			final int SCALE = 50;

			Graphics2D g2 = (Graphics2D) g;
			calc.simulate(velocity, theta);
			overshoot.simulate(velocity * 1.05, (theta));
			undershoot.simulate(velocity * .95, (theta));
			final int radius = 10;
			g2.setColor(new Color(238, 238, 238));
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setColor(Color.black);
			// Make a graph

			for (int n = 0; n <= BASE / SCALE; n++) {
				g2.drawLine(START + n * SCALE, BASE, START + n * SCALE, 0);
				g2.drawString(n + "", START + n * SCALE, 620);
				g2.drawLine(START, n * SCALE, BASE + START, n * SCALE);
				g2.drawString((BASE / SCALE - n) + "", START-30, n * SCALE);
			}
			g2.drawString("Distance, meters", START+255, 640);

			// Plot points

			//g2.drawString("velocity = " + velocity + " rpm", START+640, 275);
			//g2.drawString("angle = " + theta + " degrees", START+640, 325);
			
			g2.drawString("velocity = " + velocity + " rpm", START+50, 660);
			g2.drawString("angle = " + theta + " degrees", START+450, 660);

			g2.setColor(Color.red);
			for (int n = 0; n < overshoot.X.size() - 1; n++) {
				if (START + SCALE * overshoot.X.get(n) <= BASE + START
						&& BASE - SCALE * overshoot.Y.get(n) <= BASE)
					g2.drawOval((int) (START + SCALE * overshoot.X.get(n)),
							(int) (BASE - SCALE * overshoot.Y.get(n)), radius,
							radius - 5);
			}
			for (int n = 0; n < undershoot.X.size() - 1; n++) {
				if (START + SCALE * undershoot.X.get(n) <= BASE + START
						&& BASE - SCALE * undershoot.Y.get(n) <= BASE)
					g2.drawOval((int) (START + SCALE * undershoot.X.get(n)),
							(int) (BASE - SCALE * undershoot.Y.get(n)), radius,
							radius - 5);
			}

			g2.setColor(Color.black);

			for (int n = 0; n < calc.X.size() - 1; n++) {
				if (START + SCALE * calc.X.get(n) <= BASE + START
						&& BASE - SCALE * calc.Y.get(n) <= BASE)
					g2.drawOval((int) (START + SCALE * calc.X.get(n)),
							(int) (BASE - SCALE * calc.Y.get(n)), radius,
							radius - 5);
			}
		}

		void updateGraph(double Velocity, double Theta) {
			velocity = Velocity;
			theta = Theta;
		}
	}

	public static void main(String[] arguments) {
		@SuppressWarnings("unused")
		Trajectory cs = new Trajectory();
	}
}