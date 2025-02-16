package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GenerationViewer extends JComponent {
	private ArrayList<Chromosome> population;
	JFrame frame;
	
	public GenerationViewer(ArrayList<Chromosome> pop) {
		this.population = pop;
		frame = new JFrame();
		frame.setSize(350,380);
		frame.setLocation(1060, 230);
		frame.add(GenerationViewer.this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void runGenerationViewer() {
		repaint();
	}
	
	public void end() {
		frame.dispose();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int scaleX = 0;
		int scaleY = 0;
		int x = 0;
		int y = 0;
		for (int k = 0; k< 10; k++) {
			for (int l = 0; l<10;l++) {
				for (int i = 0; i<10; i++) {
					for (int j = 0; j<10; j++) {
						if (Character.getNumericValue(population.get(l+10*k).getBitString().charAt(j+10*i)) == 1) {
							g2d.setColor(Color.GREEN);
							g2d.fillRect(3*x, 3*y, 3, 3);
						}else {
							g2d.setColor(Color.BLACK);
							g2d.fillRect(3*x, 3*y, 3, 3);
						}
						x ++;
					}
					x = scaleX;
					y++;
				}
				scaleX += 11;
				y = scaleY;
		}
			scaleX = 0;
			scaleY += 11;	
		}
	}
	

}
