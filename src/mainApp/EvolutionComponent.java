package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class EvolutionComponent extends JComponent {

	private static final Color BEST_FITNESS = new Color(0, 255, 0);
	private static final Color AVERAGE_FITNESS = new Color(0, 0, 255);
	private static final Color WORST_FITNESS = new Color(255, 0, 0);
	
	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> averageFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> worstFitnessLog = new ArrayList<Integer>();
	
	private int bestLength = 0;
	private int averageLength = 0;
	private int worstLength = 0;
	public EvolutionComponent() {
	}
	
	public void addEntry(int best, int average, int worst) {
		this.bestFitnessLog.add(best);
		this.averageFitnessLog.add(average);
		this.worstFitnessLog.add(worst);
		System.out.println(best);
		System.out.println(average);
		System.out.println(worst);
		bestLength = bestFitnessLog.size();
		averageLength = averageFitnessLog.size();
		worstLength = worstFitnessLog.size();
	}
	
	public void reset() {
		this.bestFitnessLog.clear();
		this.averageFitnessLog.clear();
		this.worstFitnessLog.clear();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.drawRect(50, 25, 600, 200);
		
		g2.drawString("LEGEND", 700, 50);
		g2.setColor(BEST_FITNESS);
		g2.fillRect(680, 65, 10, 10);
		g2.drawString("Best fitness", 700, 75);
		g2.setColor(AVERAGE_FITNESS);
		g2.fillRect(680, 90, 10, 10);
		g2.drawString("Average Fitness", 700, 100);
		g2.setColor(WORST_FITNESS);
		g2.fillRect(680, 115, 10, 10);
		g2.drawString("Worst fitness", 700, 125);
		
		g2.setColor(Color.BLACK);
		for (int i = 0; i<5; i++) {
			g2.drawLine(45, 25+i*200/5, 55, 25+i*200/5);
			String label = Integer.toString(10-2*i);
			g2.drawString(label, 25, 25+i*200/5 +5);
			
		}
		
		for (int i = 0; i<9; i++) {
			g2.drawLine(50+i*600/8, 220, 50+i*600/8, 230);
			g2.drawString(Integer.toString(10*(i)/8), 50+i*600/8-5, 245);
		}
		
		g2.drawString("Fitness Over Generations", 275, 20);
		g2.drawString("Generations", 300, 260);
		
		g2.rotate(-Math.toRadians(90));
		g2.drawString("Fitness", -140, 15);
		g2.rotate(Math.toRadians(90));
		
		System.out.println("hi " + this.bestFitnessLog.size());
		drawGraph(g2);
	
		
		
		
		//if (bestFitnessLog.size() > 0) {
//			for (int i=0; i<bestFitnessLog.size(); i++) {
//				System.out.println("in the loop");
//				g2.drawLine(50, 50, 600, 600);
//				g2.setColor(BEST_FITNESS);
//				g2.drawLine(i*600/bestFitnessLog.size()+50, bestFitnessLog.get(i)*20, (i+1)*600/bestFitnessLog.size()+50, bestFitnessLog.get(i+1)*20);
//				
//				g2.setColor(AVERAGE_FITNESS);
//				g2.drawLine(i*600/bestFitnessLog.size()+50, averageFitnessLog.get(i)*20, (i+1)*600/bestFitnessLog.size()+50, averageFitnessLog.get(i+1)*20);
//				
//				g2.setColor(WORST_FITNESS);
//				g2.drawLine(i*600/bestFitnessLog.size()+50, worstFitnessLog.get(i)*20, (i+1)*600/bestFitnessLog.size()+50, worstFitnessLog.get(i+1)*20);
//				for (int j = 0; j<9; j++) {
//					g2.drawLine(50+i*600/8, 220, 50+i*600/8, 230);
//					
////					String label = Integer.toString(100-20*i);
////					g2.drawString(label, 25, 25+i*200/5 +5);
		
//		System.out.println("array in method" + bestFitnessLog + bestFitnessLog.size());
//		System.out.println(best +" " + average +" " + worst +" " +bestNext +" " +averageNext +" " +worstNext);
//		
//		if (bestFitnessLog.size() > 0) {
//		g2.drawLine(50, 50, 600, 600);
//		g2.setColor(BEST_FITNESS);
//		g2.drawLine(best*600/bestFitnessLog.size()+50, best*20, 7*600/bestFitnessLog.size()+50, bestNext*20);
////		
//		g2.setColor(AVERAGE_FITNESS);
//		g2.drawLine(average*600/bestFitnessLog.size()+50, average*20, (7)*600/bestFitnessLog.size()+50, averageNext*20);
//		
//		g2.setColor(WORST_FITNESS);
//		g2.drawLine(worst*600/bestFitnessLog.size()+50, worst*20, (i+1)*600/bestFitnessLog.size()+50, worstNext*20);
	}
//				}
//				
//				
//			}

	public void update() {
		repaint();
	}
	
	
	public void drawGraph(Graphics2D g2) {
		System.out.println("size" + this.bestFitnessLog.size());
		if (bestFitnessLog.size() > 1 && averageFitnessLog.size() > 1 && worstFitnessLog.size() > 1) {
		for (int i=0; i<bestLength-1; i++) {
			System.out.println("in the loop");
			g2.drawLine(50, 50, 600, 600);
			g2.setColor(BEST_FITNESS);
			g2.drawLine(i*600/bestFitnessLog.size()+50, bestFitnessLog.get(i)*20, (i+1)*600/bestFitnessLog.size()+50, bestFitnessLog.get(i+1)*20);
			
			g2.setColor(AVERAGE_FITNESS);
			g2.drawLine(i*600/bestFitnessLog.size()+50, averageFitnessLog.get(i)*20, (i+1)*600/bestFitnessLog.size()+50, averageFitnessLog.get(i+1)*20);
			
			g2.setColor(WORST_FITNESS);
			g2.drawLine(i*600/bestFitnessLog.size()+50, worstFitnessLog.get(i)*20, (i+1)*600/bestFitnessLog.size()+50, worstFitnessLog.get(i+1)*20);
		
		
//		g2.setColor(AVERAGE_FITNESS);
//		g2.drawLine(average*600/bestFitnessLog.size()+50, average*20, (7)*600/bestFitnessLog.size()+50, averageNext*20);
//		
//		g2.setColor(WORST_FITNESS);
//		g2.drawLine(worst*600/bestFitnessLog.size()+50, worst*20, (i+1)*600/bestFitnessLog.size()+50, worstNext*20);
	}
	}
}
}



