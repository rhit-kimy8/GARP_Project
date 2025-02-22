package mainApp;

import java.util.ArrayList;

import javax.swing.JFrame;

public class FittestIndividualViewer {
	private Chromosome fittest;
	private JFrame fitViewer;
	FittestIndividualComponent fitComponent;
	
	/***
	 * 
	 * @param Chromosome
	 *  <br> Creates a viewer to see the fittest chromosome of generation
	 */
	public FittestIndividualViewer(Chromosome c) {
	this.fittest = c;
	fitViewer = new JFrame();
	fitViewer.setSize(212,235);
	this.fitComponent = new FittestIndividualComponent();
	this.fitComponent.setChromosome(c);
	fitViewer.add(this.fitComponent);
	fitViewer.setTitle("Fitness: " + c.getFitness());
	fitViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	fitViewer.setLocation(1060,0);
	fitViewer.setVisible(true);
	}
	
	public void upDate(Chromosome c) {
		this.fittest = c;
		this.fitViewer.setTitle("Fitness: " + this.fittest.getFitness());
		fitComponent.setChromosome(c);
	
	}
	
	public void end() {
		this.fitViewer.setVisible(false);
	}
	/***
	 * 
	 * @param Chromosome c
	 * <br> Calls an identical method in component to change current chromosome and repaint
	 */
}
