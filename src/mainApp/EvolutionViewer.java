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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class: EvolutionViewer
 * @author Helen Xu
 * <br>Purpose: create a window for the evolution viewer. it draws a graph, tracks time with 
 *      a timer, and has function buttons for control
 * <br>Restrictions: Could be optimized
 */
public class EvolutionViewer extends JComponent {
	private static final int FRAME_WIDTH = 930;
	private static final int FRAME_HEIGHT = 350;
	
	private static final Color BEST_FITNESS = new Color(0, 255, 0);
	private static final Color AVERAGE_FITNESS = new Color(0, 0, 255);
	private static final Color WORST_FITNESS = new Color(255, 0, 0);
	
	private static final int DELAY = 50;
	static boolean isRunning = false;
	private boolean isDone = true;
	static double fitness = 9.0;
	
	public static String saveString = "";
	private int lengthOfChromosome;
	private FittestIndividualViewer FIV;
	
	private Timer timer;
	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> averageFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> worstFitnessLog = new ArrayList<Integer>();
	Population pop = new Population();
	
	public void addEntry(int best, int average, int worst) {
		this.bestFitnessLog.add(best);
		this.averageFitnessLog.add(average);
		this.worstFitnessLog.add(worst);
		repaint();
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
		frame.setSize( new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
		
	    JPanel controls = new JPanel();
		JButton start = new JButton("Start"); //change to icon later
		JLabel populationSize = new JLabel("Population Size");
		JTextField populationSizeInput = new JTextField();
		populationSizeInput.setPreferredSize(new Dimension(30,20));
		JLabel mutationRate = new JLabel("Mutation Rate (N/pop)");
		JTextField mutationRateInput = new JTextField();
		mutationRateInput.setPreferredSize(new Dimension(30,20));
		JLabel chromosomeLength = new JLabel("Chromosome Length");
		JTextField chromosomeLengthInput = new JTextField();
		chromosomeLengthInput.setPreferredSize(new Dimension(30,20));
		JLabel crossover = new JLabel("Crossover?");
		JCheckBox crossoverCheck = new JCheckBox();
		crossoverCheck.setForeground(Color.white);
		JLabel elitism = new JLabel("Elitism %");
		JTextField elitismInput = new JTextField();
		elitismInput.setPreferredSize(new Dimension(30,20));
		JLabel selection = new JLabel("Selection");
		String[] selectionList = {"Truncation", "Wheel Select", "Ranked Select"};
		JComboBox selectionInput = new JComboBox(selectionList);
		selectionInput.setPreferredSize(new Dimension(120,20));
		
		
		controls.add(populationSize);
		controls.add(populationSizeInput);
		controls.add(chromosomeLength);
		controls.add(chromosomeLengthInput);
		controls.add(mutationRate);
		controls.add(mutationRateInput);
		controls.add(selection);
		controls.add(selectionInput);
		controls.add(crossover);
		controls.add(crossoverCheck);
		controls.add(elitism);
		controls.add(elitismInput);
		controls.add(start);
		frame.add(controls, BorderLayout.SOUTH);
		 
		 this.timer = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.repaint();
				pop.truncationSelect(pop.population, crossoverCheck.isSelected(),(double)Integer.parseInt(mutationRateInput.getText())/pop.population.size(),(int)Integer.parseInt(elitismInput.getText())*pop.population.size()/100);
				FIV.upDate(pop.getBest(pop.population));
				addEntry(pop.getBest(pop.population).getFitness(), pop.getAverage(pop.population), pop.getWorst(pop.population));
				isDone = false;
				if((double)pop.getBest(pop.population).getFitness()/lengthOfChromosome*10.0 == fitness) {
					isDone = true;
					isRunning = false;
					EvolutionViewer.this.timer.stop();
					System.out.println("Yahpiee");
					start.setText("Restart");
				}
				}				
			}
			 //stop automatically at a lot of generations
				 );
		 
		start.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (populationSizeInput.getText().isBlank() || chromosomeLengthInput.getText().isBlank() || mutationRateInput.getText().isBlank() || elitismInput.getText().isBlank()) {	
					System.err.println("One or more variables is not defined: population size, chromosome length, mutation rate, elitism rate");
					return;
				}
				if(isDone) {
					pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()));
					FIV = new FittestIndividualViewer(pop.getBest(pop.population));
					System.out.println("make pop");
				}
				lengthOfChromosome = Integer.parseInt(chromosomeLengthInput.getText());
				
				if (!isRunning) {
					EvolutionViewer.this.timer.start();
					start.setText("Pause");
					isRunning = true;
					EvolutionViewer.this.repaint();
				} else {
					EvolutionViewer.this.timer.stop();
					start.setText("Continue");
					isRunning = false;
					EvolutionViewer.this.repaint();
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
	private void drawFitnessGraph(Graphics2D g2, int graphX, int graphY, int graphWidth, int graphHeight, int maxFitness) {
	    int numGenerations = bestFitnessLog.size();
	    //System.out.println("bestFitness - 1: " + bestFitnessLog.get(numGenerations - 1));
	    //check whether we can make a line through points in graph
	    if (numGenerations < 2) {
	    	return;
	    }
	    	
	    if (numGenerations > 300) {
	    	System.exit(0);
	    }
	      
	    double scaleY = (double) graphHeight / maxFitness;  
	    double scaleX = (double) graphWidth / (numGenerations - 1);

	    g2.setColor(Color.GREEN);
	    for (int i = 1; i < numGenerations; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (bestFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (bestFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }

	    g2.setColor(Color.BLUE);
	    for (int i = 1; i < numGenerations; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (averageFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (averageFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }

	    g2.setColor(Color.RED);
	    for (int i = 1; i < numGenerations; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (worstFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (worstFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }

		g2.setColor(Color.BLACK);
		for (int i = 0; i<9; i++) {
			String label = Integer.toString(numGenerations*i);
			g2.drawString(label, 50+i*850/8-5, 240);
		}
		
	
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.drawRect(50, 25, 850, 200);
		
		g2.drawString("LEGEND", 800, 50);
		g2.setColor(BEST_FITNESS);
		g2.fillRect(780, 65, 10, 10);
		g2.drawString("Best fitness", 800, 75);
		g2.setColor(AVERAGE_FITNESS);
		g2.fillRect(780, 90, 10, 10);
		g2.drawString("Average Fitness", 800, 100);
		g2.setColor(WORST_FITNESS);
		g2.fillRect(780, 115, 10, 10);
		g2.drawString("Worst fitness", 800, 125);
		drawFitnessGraph(g2, 50, 25, 700, 200, lengthOfChromosome);
		
		g2.setColor(Color.BLACK);
		for (int i = 0; i<6; i++) {
			g2.drawLine(45, 25+i*200/5, 55, 25+i*200/5);
			String label = Double.toString(10-2*i);
			g2.drawString(label, 25, 25+i*200/5 +5);
			
		}
		
		for (int i = 0; i<9; i++) {
			g2.drawLine(50+i*850/8, 220, 50+i*850/8, 230);
		}
		
		g2.drawString("Fitness Over Generations", 410, 20);
		g2.drawString("Generations", 450, 250);
		
		g2.rotate(-Math.toRadians(90));
		g2.drawString("Fitness", -140, 15);
		g2.rotate(Math.toRadians(90));
		
		}
		
	}

	
