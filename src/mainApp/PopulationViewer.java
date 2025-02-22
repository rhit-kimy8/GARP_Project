package mainApp;

import javax.swing.JFrame;

public class PopulationViewer {
	
	private Population p;
	private PopulationComponent popComp;
	private JFrame popFrame;
	
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
	this.popFrame.setLocation(1060, 230);
	this.popFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.popFrame.setVisible(true);
	}
	
	public void upDatePopulation(Population p) {
		this.popFrame.setTitle("Generation: " + p.getGenerationCount());
		this.popComp.upDatePopulation(p);
	}
	
	public void end() {
		this.popFrame.dispose();
	}

	public void repaint() {
		// TODO Auto-generated method stub
		popComp.repaint();
	}
}
