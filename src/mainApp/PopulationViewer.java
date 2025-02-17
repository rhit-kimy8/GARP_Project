package mainApp;

import javax.swing.JFrame;

public class PopulationViewer {
	
	private Population p;
	private PopulationComponent popComp;
	private JFrame popFrame;
	/*
	 * Test code for popViewer
	public static void main(String[] args) {
		Population p = new Population();
		//popLen, bitStringLen
		p.generateRandomPopulation(1000, 20, 0);
		PopulationViewer popView = new PopulationViewer(p);
		p.mutatePopulation(p.getPopulation(), 100);
		popView.upDatePopulation(p);
	}
	*/
	
	/***
	 * 
	 * @param Population p
	 * <br> Visualizes the given population
	 */
	public PopulationViewer(Population p) {
	this.popFrame = new JFrame();
	this.popFrame.setSize(320, 345);
	this.p = p;
	this.popComp = new PopulationComponent();
	upDatePopulation(this.p);
	this.popFrame.add(popComp);
	this.popFrame.setTitle("Generation: " + p.getGenerationCount());
	this.popFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.popFrame.setVisible(true);
	//popFrame.dispose();
	}
	
	public void upDatePopulation(Population p) {
		this.popFrame.setTitle("Generation: 12 " + p.getGenerationCount());
		this.popComp.upDatePopulation(p);
	}

	public void repaint() {
		// TODO Auto-generated method stub
		popComp.repaint();
	}
}
