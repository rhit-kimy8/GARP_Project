package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.nio.file.spi.FileSystemProvider;

import javax.swing.JComponent;

public class PopulationComponent extends JComponent {

	private Population p;
	@Override
	protected void paintComponent(Graphics graphics) {
		int popLen = p.getPopulation().size();
		int xAmount = ((int) ((Math.pow(popLen, .5)) + .99));
		int yAmount = ((int) ((Math.pow(popLen, .5))));
		float xLen = 300 / xAmount;
		float yLen = 300 / yAmount;
		if (this.p == null) {
			return;
		}
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		g2.translate(3, 5);
		//Draws individual chromosomes
		for (double yCord = 0; yCord < 300; yCord += yLen) {
			g2.translate(0, yCord);
			for (double xCord = 0; xCord < 300; xCord += xLen) {
				g2.translate(xCord, 0);
				int index = (int) (((xCord / xLen)) + (xAmount * yCord / yLen));
				if (index >= popLen) {
					break;
				}
				p.getPopulation().get(index).drawOnPopulation(g2, xLen, yLen, popLen);
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