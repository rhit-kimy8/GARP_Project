package mainApp;

import java.util.ArrayList;

import javax.swing.JFrame;

public class FittestIndividualViewer {

	FittestIndividualComponent fitComponent;
	
	 //* Testing code 
	public static void main(String args[]) {
		Chromosome c1 = new Chromosome();
		Chromosome c2 = new Chromosome();
		FittestIndividualViewer v1 = new FittestIndividualViewer(c1);
		FittestIndividualViewer v2 = new FittestIndividualViewer(c1);
		for (int x = 0; x < 100000; x++) {
			//System.out.println(x);
		}
		//v1.setChromosome(c2);
		for (int x = 0; x < 100000; x++) {
			
		}
		//v1.setChromosome(c1);
	}
	
	/***
	 * 
	 * @param Chromosome
	 *  <br> Creates a viewer to see the fittest chromosome of generation
	 */
	public FittestIndividualViewer(Chromosome c) {
	JFrame fitViewer = new JFrame();
	fitViewer.setSize(212,235);
	this.fitComponent = new FittestIndividualComponent();
	this.fitComponent.setChromosome(c);
	fitViewer.add(this.fitComponent);
	fitViewer.setTitle("Fitness: " + c.getFitness());
	fitViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	fitViewer.setVisible(true);
	}
	
	/***
	 * 
	 * @param Chromosome c
	 * <br> Calls an identical method in component to change current chromosome and repaint
	 */
	public void setChromosome(Chromosome c) {
		this.fitComponent.setChromosome(c);
	}
	
}
