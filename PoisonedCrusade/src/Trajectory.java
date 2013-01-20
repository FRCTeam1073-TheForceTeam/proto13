import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;

public class Trajectory extends JFrame implements ChangeListener {

	//Create 3 trajectories
	private static final long serialVersionUID = 635305340570780384L;
	GraphPanel graph = new GraphPanel();
	Calcs calc = new Calcs();
	Calcs overshoot = new Calcs();
	Calcs undershoot = new Calcs();

	// Create sliders
	
	JSlider velocityx10 = new JSlider(JSlider.HORIZONTAL, 0, 4000, 1000);
	JSlider angle = new JSlider(JSlider.HORIZONTAL, 0, 90, 20);
	JSlider error = new JSlider(JSlider.HORIZONTAL, 0, 20, 5);

	public Trajectory() {
		// Set frame properties
		super("Disc Trajectory Model");
		setSize(975, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		
		// Set slider properties and add to frame

		velocityx10.setMajorTickSpacing(500);
		velocityx10.setMinorTickSpacing(100);
		velocityx10.setPaintLabels(true);
		velocityx10.setPaintTicks(true);

		angle.setMajorTickSpacing(10);
		angle.setMinorTickSpacing(2);
		angle.setPaintLabels(true);
		angle.setPaintTicks(true);

		error.setMajorTickSpacing(5);
		error.setMinorTickSpacing(1);
		error.setPaintLabels(true);
		error.setPaintTicks(true);

		velocityx10.addChangeListener(this);
		angle.addChangeListener(this);
		error.addChangeListener(this);

		JLabel vLabel = new JLabel("Velocity, rpm", JLabel.CENTER);
		JLabel aLabel = new JLabel("Theta, degrees", JLabel.CENTER);
		JLabel eLabel = new JLabel("Error, percent of velocity", JLabel.CENTER);

		Container pane = getContentPane();

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		JPanel title = new JPanel();
		title.setMaximumSize(new Dimension(1000, 300));
		JLabel text = new JLabel("Team 1073's Disc Physics Simulator", JLabel.CENTER);
		text.setFont(new Font("bla", Font.BOLD, 35));
		title.add(text);
		pane.add(title);

		JPanel vPanel = new JPanel();
		vPanel.setMaximumSize(new Dimension(1000, 200));
		GridLayout gl = new GridLayout(2, 2);
		gl.setHgap(100);
		vPanel.setLayout(gl);
		vPanel.add(vLabel);
		vPanel.add(aLabel);
		vPanel.add(eLabel);
		vPanel.add(velocityx10);
		vPanel.add(angle);
		vPanel.add(error);
		pane.add(vPanel);

		pane.add(Box.createRigidArea(new Dimension(25, 25)));

		pane.add(graph);

		setContentPane(pane);
	}

	public void stateChanged(ChangeEvent evt) {
		JSlider source = (JSlider) evt.getSource();

		calc.X.clear();
		calc.Y.clear();
		overshoot.X.clear();
		overshoot.Y.clear();
		undershoot.X.clear();
		undershoot.Y.clear();
		graph.updateGraph(velocityx10.getValue(), angle.getValue(), error.getValue());
		graph.validate();
		graph.repaint();

	}

	class GraphPanel extends JPanel {
		double velocity;
		double theta;
		double percent;

		GraphPanel() {
			velocity = 1000;
			theta = 20;
			percent = 15;
		}

		public void paintComponent(Graphics g) {

			// Centering grid
			final int START = (getWidth() / 2) - 300;
			// grid length of 600
			final int BASE = 600;
			// 50 by 50 grids
			final int SCALE = 50;

			Graphics2D g2 = (Graphics2D) g;
			calc.simulate(velocity, theta);
			overshoot.simulate(velocity * (1 + (percent) / 100), theta);
			undershoot.simulate(velocity * (1 - (percent) / 100), theta);
			final int radius = 8;
			g2.setColor(new Color(238, 238, 238));
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setColor(Color.black);
			// Make a graph

			// vertical grid
			for (int n = 0; n <= BASE / SCALE; n++) {
				g2.drawLine(START + n * SCALE, BASE - 300, START + n * SCALE, 0);
				g2.drawString(n + "", START + n * SCALE, 320);
			}

			// horizontal grid
			for (int n = 6; n <= BASE / SCALE; n++) {
				g2.drawLine(START, n * SCALE - 300, BASE + START, n * SCALE - 300);
				g2.drawString((BASE / SCALE - n) + "", START - 30, n * SCALE - 300);
			}

			g2.drawString("Distance, meters", START + 255, 340);

			// Plot points

			g2.drawString("velocity = " + velocity + " rpm", START + 50, 360);
			g2.drawString("angle = " + theta + " degrees", START + 450, 360);

			g2.setColor(Color.red);
			for (int n = 0; n < overshoot.X.size() - 1; n++) {
				if (START + SCALE * overshoot.X.get(n) <= BASE + START && BASE - SCALE * overshoot.Y.get(n) <= BASE)
					g2.drawOval((int) (START + SCALE * overshoot.X.get(n)), (int) (BASE - SCALE * overshoot.Y.get(n)) - 300, radius, radius - 5);
			}
			for (int n = 0; n < undershoot.X.size() - 1; n++) {
				if (START + SCALE * undershoot.X.get(n) <= BASE + START && BASE - SCALE * undershoot.Y.get(n) <= BASE)
					g2.drawOval((int) (START + SCALE * undershoot.X.get(n)), (int) (BASE - SCALE * undershoot.Y.get(n)) - 300, radius, radius - 5);
			}

			g2.setColor(Color.black);

			for (int n = 0; n < calc.X.size() - 1; n++) {
				if (START + SCALE * calc.X.get(n) <= BASE + START && BASE - SCALE * calc.Y.get(n) <= BASE)
					g2.drawOval((int) (START + SCALE * calc.X.get(n)), (int) (BASE - SCALE * calc.Y.get(n)) - 300, radius, radius - 5);
			}
		}

		void updateGraph(double Velocity, double Theta, int Error) {
			velocity = Velocity;
			theta = Theta;
			percent = Error;
		}
	}

	public static void main(String[] arguments) {
		@SuppressWarnings("unused")
		Trajectory cs = new Trajectory();
	}
}