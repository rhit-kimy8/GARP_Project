package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class PopulationComponent extends JComponent {

	private Population p;
	@Override
	protected void paintComponent(Graphics graphics) {
		if (this.p == null) {
			return;
		}
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		for (int yCord = 0; yCord < 300; yCord += 30) {
			g2.translate(0, yCord);
			for (int xCord = 0; xCord < 300; xCord += 30) {
				g2.translate(xCord, 0);
				int index = ((xCord / 30) + (yCord / 3));
				p.getPopulation().get(index).drawOnPopulation(g2, 2, 2);
				g2.translate(-xCord, 0);
			}
			g2.translate(0, -yCord);
		}
	}
	
	public void upDatePopulation(Population p) {
		this.p = p;
		repaint();
	}
}
