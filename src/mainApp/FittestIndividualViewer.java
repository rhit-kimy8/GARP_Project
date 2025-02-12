package mainApp;

import java.util.ArrayList;

import javax.swing.JFrame;

public class FittestIndividualViewer {
	private Chromosome fittest;
	private JFrame fitViewer;
	FittestIndividualComponent fitComponent;
	
	 /* Testing code 
	public static void main(String args[]) {
		Chromosome c1 = new Chromosome(100);
//		Chromosome c2 = new Chromosome();
		FittestIndividualViewer v1 = new FittestIndividualViewer(c1);
//		FittestIndividualViewer v2 = new FittestIndividualViewer(c1);
		for (int x = 0; x < 100000; x++) {
			//System.out.println(x);
		}
		//v1.setChromosome(c2);
		for (int x = 0; x < 100000; x++) {
			
		}
		//v1.setChromosome(c1);
	}
	*/
	
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
	//fitViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	fitViewer.setVisible(true);
	}
	
	public void upDate(Chromosome c) {
		this.fittest = c;
		this.fitViewer.setTitle("Fitness: " + this.fittest.getFitness());
		fitComponent.setChromosome(c);
	
	}
	
	public void end() {
		this.fitViewer.dispose();
	}
	/***
	 * 
	 * @param Chromosome c
	 * <br> Calls an identical method in component to change current chromosome and repaint
	 */
//	public void setChromosome(Chromosome c) {
//		this.fitComponent.setChromosome(c);
//	}
	
}
