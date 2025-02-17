package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class FittestIndividualComponent extends JComponent {
	
	Chromosome c;
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g2 = (Graphics2D) graphics;
		if (c == null) {
			return;
		}
		c.drawOnFitness(g2);
	}
	
	/***
	 * 
	 * @param Chromosome c
	 * <br> Changes current chromosome and paints it to the frame
	 */
	public void setChromosome(Chromosome c) {
		this.c = c;
		//revalidate();
		repaint();
	}
}
