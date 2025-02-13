package mainApp;

import javax.swing.JFrame;

public class PopulationViewer {
	
	private Population p;
	private PopulationComponent popComp;
	
	public static void main(String[] args) {
		Population p = new Population();
		p.generateRandomPopulation(100, 100);
		PopulationViewer popView = new PopulationViewer(p);
		p.mutatePopulation(p.getPopulation(), 100);
		popView.upDatePopulation(p);
	}
	
	public PopulationViewer(Population p) {
	JFrame popFrame = new JFrame();
	popFrame.setSize(350, 350);
	this.p = p;
	this.popComp = new PopulationComponent();
	upDatePopulation(this.p);
	popFrame.add(popComp);
	popFrame.setTitle("Generation: ");
	popFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	popFrame.setVisible(true);
	}
	
	public void upDatePopulation(Population p) {
		this.popComp.upDatePopulation(p);
	}
}
