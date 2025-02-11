package mainApp;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class: EvolutionViewer
 * @author Helen Xu
 * <br>Purpose: create a window for the evolution viewer. it draws a graph, tracks time with 
 *      a timer, and has function buttons for control
 * <br>Restrictions: Could be optimized
 */
public class EvolutionViewer extends JComponent {
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 350;
	
	private static final Color BEST_FITNESS = new Color(0, 255, 0);
	private static final Color AVERAGE_FITNESS = new Color(0, 0, 255);
	private static final Color WORST_FITNESS = new Color(255, 0, 0);
	
	private static final int DELAY = 100;
	static boolean isRunning = false;
	
	public static String saveString = "";
	
	private Timer timer;
	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> averageFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> worstFitnessLog = new ArrayList<Integer>();
	
	public void addEntry(int best, int average, int worst) {
		this.bestFitnessLog.add(best);
		this.averageFitnessLog.add(average);
		this.worstFitnessLog.add(worst);
	}
	
	public void reset() {
		this.bestFitnessLog.clear();
		this.averageFitnessLog.clear();
		this.worstFitnessLog.clear();
	}
	
	EvolutionViewer() {
	}
	
	public  void runEvolutionViewer() {
		JFrame frame = new JFrame("Evolution Viewer");
		EvolutionViewer viewer = new EvolutionViewer();
		frame.setSize( new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(viewer);
		frame.setVisible(true);
		Population pop = new Population();
		pop.generateRandomPopulation(100, 10);
		for (int i=0; i<pop.population.size();i++) System.out.println(pop.population.get(i).getBitString());
		
	    JPanel controls = new JPanel();
		JButton start = new JButton("Start"); //change to icon later
		
		frame.add(controls, BorderLayout.SOUTH);
		controls.add(start);
		 
		 viewer.timer = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				viewer.repaint();
				pop.truncationSelect(pop.population);
				addEntry(pop.getBest().getFitness(), pop.getAverage(), pop.getWorst());
				System.out.println(pop.getBest().getFitness());
				System.out.println(pop.getAverage());
				System.out.println(pop.getWorst());
}				
			}
			 //stop automatically at a lot of generations
				 );
		 
		start.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isRunning) {
					viewer.timer.start();
					start.setText("Pause");
					isRunning = true;
					viewer.repaint();
				} else {
					viewer.timer.stop();
					start.setText("Continue");
					isRunning = false;
					viewer.repaint();
				}
			}
			
		});
//		
		MenuBar menu = new MenuBar();
		
		Menu population = new Menu("Population");
		menu.add(population);
		MenuItem newPopulation = new MenuItem("New Population");
		population.add(newPopulation);
		
		Menu file = new Menu("File");
		menu.add(file);
		
		MenuItem save = new MenuItem("Save");
		file.add(save);
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the current status of the chromosomes and then save it to the file
				
				if (bestFitnessLog.size() > 0) {
					for (int i=0; i<bestFitnessLog.size(); i++) {
						saveString += bestFitnessLog.get(i) + averageFitnessLog.get(i) + worstFitnessLog.get(i);
					}
				}
			
				JFileChooser jf = new JFileChooser();
				jf.setCurrentDirectory(new File("./src/mainApp/EvolutionSaveFile"));
				int filePath = jf.showSaveDialog(null);
				if (filePath == JFileChooser.APPROVE_OPTION) {
					String path = jf.getSelectedFile().getAbsolutePath();
					try {
						FileWriter fw = new FileWriter(path);
						fw.write(saveString);
						fw.close();
					} catch (IOException e1) {
						System.err.println("File named" + path + "was not found.");
						e1.printStackTrace();
					}	
				}	
			}
		});
		
		Menu help = new Menu("Help");
		menu.add(help);
		MenuItem helpPage = new MenuItem("Help Page");
		help.add(helpPage);
		
		helpPage.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame helpFrame = new JFrame("Help");
				helpFrame.setSize(400,400);
				helpFrame.setVisible(true);
				
				JLabel helpLabel = new JLabel("This is the help page. ");
				helpFrame.add(helpLabel, BorderLayout.NORTH);
				
			}
			
		});
		frame.setMenuBar(menu);
		
		//timer.start();
	}

	protected void paintComponent(Graphics g) {
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
		}
		
		g2.drawString("Fitness Over Generations", 275, 20);
		g2.drawString("Generations", 300, 250);
		
		g2.rotate(-Math.toRadians(90));
		g2.drawString("Fitness", -140, 15);
		g2.rotate(Math.toRadians(90));
		
		
		
		if (bestFitnessLog.size() > 0) {
			for (int i=0; i<bestFitnessLog.size(); i++) {
				g2.setColor(BEST_FITNESS);
				g2.drawLine(i*600/bestFitnessLog.size()+25, bestFitnessLog.get(i), i*600/bestFitnessLog.size()+26, bestFitnessLog.get(i));
				
				g2.setColor(AVERAGE_FITNESS);
				g2.drawLine(i*600/bestFitnessLog.size()+25, averageFitnessLog.get(i), i*600/bestFitnessLog.size()+26, averageFitnessLog.get(i));
				
				g2.setColor(WORST_FITNESS);
				g2.drawLine(i*600/bestFitnessLog.size()+25, worstFitnessLog.get(i), i*600/bestFitnessLog.size()+26, worstFitnessLog.get(i));
				
//				for (int j = 0; j<9; j++) {
//					g2.drawLine(50+i*600/8, 220, 50+i*600/8, 230);
//					
//					String label = Integer.toString(100-20*i);
//					g2.drawString(label, 25, 25+i*200/5 +5);
				}
				
				
			}

		}
		
	}

	
