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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class: EvolutionViewer
 * @author Helen Xu
 * <br>Purpose: create a window for the evolution viewer. it draws a graph, tracks time with 
 *      a timer, and has function buttons for control
 * <br>Restrictions: Could be optimized
 */
public class EvolutionViewer extends JComponent {
	private static final int FRAME_WIDTH = 1250;
	private static final int FRAME_HEIGHT = 350;
	
	private static final int GRAPH_WIDTH = 850;
	private static final int GRAPH_HEIGHT = 200;
	private static final Color BEST_FITNESS = new Color(0, 255, 0);
	private static final Color AVERAGE_FITNESS = new Color(0, 0, 255);
	private static final Color WORST_FITNESS = new Color(255, 0, 0);
	
	private static final int DELAY = 50;
	static boolean isRunning = false;
	private boolean isDone = true;
	private double fitness;
	
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
	
	public void runEvolutionViewer() {
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
		JLabel speicalPop = new JLabel("Special Pop?");
		JCheckBox specialCheck = new JCheckBox();
		JLabel crossover = new JLabel("Crossover?");
		JCheckBox crossoverCheck = new JCheckBox();
		JLabel elitism = new JLabel("Elitism %");
		JTextField elitismInput = new JTextField();
		elitismInput.setPreferredSize(new Dimension(30,20));
		JLabel selection = new JLabel("Selection");
		String[] selectionList = {"Truncation", "Wheel Select", "Ranked Select"};
		JComboBox selectionInput = new JComboBox(selectionList);
		selectionInput.setPreferredSize(new Dimension(100,20));
		JLabel fitnessFunction = new JLabel("Fitness Function");
		String[] functionList = {"Max Ones Fitness" , "Match Target Fitness", "Max Consecutive Ones Fitness"};
		JComboBox functionInput = new JComboBox(functionList);
		functionInput.setPreferredSize(new Dimension(140,20));
		

		
		controls.add(populationSize);
		controls.add(populationSizeInput);
		controls.add(chromosomeLength);
		controls.add(chromosomeLengthInput);
		controls.add(speicalPop);
		controls.add(specialCheck);
		controls.add(mutationRate);
		controls.add(mutationRateInput);
		controls.add(selection);
		controls.add(selectionInput);
		controls.add(fitnessFunction);
		controls.add(functionInput);
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
				if(((double)pop.getBest(pop.population).getFitness()/lengthOfChromosome*10.0 >= fitness) || (pop.getGenerationCount() > 100)) {
					isDone = true;
					isRunning = false;
					EvolutionViewer.this.timer.stop();
					System.out.println("Evolution Terminated");
					start.setText("Restart");
					FIV.end();
					pop.removePopulation();
				}
				}				
			}
			 //stop automatically at a lot of generations
				 );
		 
		start.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lengthOfChromosome = Integer.parseInt(chromosomeLengthInput.getText());
				if (populationSizeInput.getText().isBlank() || chromosomeLengthInput.getText().isBlank() || mutationRateInput.getText().isBlank() || elitismInput.getText().isBlank()) {	
					System.err.println("One or more variables is not defined: population size, chromosome length, mutation rate, elitism rate");
					return;
				}
				String function = functionInput.getSelectedItem().toString();
				if(isDone && pop.getGenerationCount() == 0 && !specialCheck.isSelected()) {
					if (function.equals("Max Ones Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
						fitness = 9.0;
					}else if (function.equals("Match Target Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
					}else if (function.equals("Max Consecutive Ones Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
					}
					FIV = new FittestIndividualViewer(pop.getBest(pop.population));
				}else {
					if (function.equals("Max Ones Fitness")) {
						pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
						fitness = 9.0;
					}else if (function.equals("Match Target Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
					}else if (function.equals("Max Consecutive Ones Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
					}
					FIV = new FittestIndividualViewer(pop.getBest(pop.population));
				}
				
				if (!isRunning) {
					EvolutionViewer.this.timer.start();
					start.setText("Pause");
					isRunning = true;
					EvolutionViewer.this.repaint();
					
					if((isDone && pop.getGenerationCount()!=0) && !specialCheck.isSelected()) {
						reset();
						pop.setGenerationCount();
						String function1 = functionInput.getSelectedItem().toString();
						if (function1.equals("Max Ones Fitness")) {
							pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
							fitness = 9.0;
						}else if (function1.equals("Match Target Fitness")) {
							pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
						}else if (function1.equals("Max Consecutive Ones Fitness")) {
							pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
						}
					}else {
						reset();
						pop.setGenerationCount();
						String function1 = functionInput.getSelectedItem().toString();
						if (function1.equals("Max Ones Fitness")) {
							pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
							fitness = 9.0;
						}else if (function1.equals("Match Target Fitness")) {
							pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
						}else if (function1.equals("Max Consecutive Ones Fitness")) {
							pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
						}
					}
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
		newPopulation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				populationSizeInput.setText("");
				chromosomeLengthInput.setText("");
				mutationRateInput.setText("");
				crossoverCheck.setSelected(false);
				elitismInput.setText("");
				//set start button back to beginning
				frame.repaint();
				
			}
			
		});
		
		
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
				
				JLabel helpLabel = new JLabel("Need Help?");
				helpFrame.add(helpLabel);
				JTextArea text = new JTextArea(20,30);
				
				   text.setLineWrap(true); // Enable line wrapping
			        text.setWrapStyleWord(true); // Wrap at word boundaries
			        JScrollPane scrollPane = new JScrollPane(text);

			        helpFrame.getContentPane().add(scrollPane);
			        helpFrame.pack();
			        helpFrame.setVisible(true);

			        // Add text to the JTextArea
			        text.setText("What is population size? \n"
						+ "Population size is how many chromosomes will be generated. \n"
						+ "What is chromosome length? \n"
						+ "Chromosome length is the length of the bitstring for each chromosome. \n"
						+ "What are the selection options? \n" 
						+ "Truncation cuts out inferior chromosomes and replaces them with new mutated chromosomes the next generation. \n"
						+ "Wheel select one at random, the probability based off the fitness level. \n"
						+ "Ranked select will be implemented soon! \n"
						+ "What does crossover select do? \n"
						+ "By choosing crossover select, the bitstring will crossover at a specific point. \n"
						+ "What is elistism? \n"
						+ "This is the percent of the most 'fit' that will be kept for each generation. \n"
						+ "NOTE: Avoid selecting truncation and crossover, as both have very similar functions.");
			        
			        text.setEditable(false);
			        
				helpFrame.add(helpLabel, BorderLayout.NORTH);
				
			}
			
		});
		frame.setMenuBar(menu);
		
		//timer.start();
}
	private void drawFitnessGraph(Graphics2D g2, int graphX, int graphY, int graphWidth, int graphHeight, int maxFitness) {
	    int fitnessLogSize = bestFitnessLog.size();
	    // check whether we can make a line through points in graph
	    if (fitnessLogSize < 2) {
	    	return;
	    }
	    
	    // this will split each scaleX and scaleY for each points. ex) if we have 800, and two points, the scale X will be still 800 and 
	    // plot function between first to the end
	    // This scaleY will calculate how the point is located based on its fitness but it will calculate by fitness, percentage
	    // scaleY = height * (fitness / maxFitness); calculate the portion in column
	    double scaleY = (double) graphHeight / maxFitness;  
	    double scaleX = (double) graphWidth / (fitnessLogSize - 1);
	    
	    // from those scaleX and scaleY, we define each size of points, eventually make lines btw them from first to the end
	    g2.setColor(Color.GREEN);
	    for (int i = 1; i < fitnessLogSize; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (bestFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (bestFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }
	    g2.setColor(Color.BLUE);
	    for (int i = 1; i < fitnessLogSize; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (averageFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (averageFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }
	    g2.setColor(Color.RED);
	    for (int i = 1; i < fitnessLogSize; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (worstFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (worstFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }
		g2.setColor(Color.BLACK);
		for (int i = 8;i>-1; i--) {
			int count = pop.getGenerationCount();
			String label = Integer.toString(count-count/8*i);
			if (i==8) label = "0";
			g2.drawString(label, 50+(8-i)*GRAPH_WIDTH/8-5, 240);
		}
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.drawRect(50, 25, GRAPH_WIDTH, GRAPH_HEIGHT);
		
		g2.drawString("LEGEND", 800, 130);
		g2.setColor(BEST_FITNESS);
		g2.fillRect(780, 145, 10, 10);
		g2.drawString("Best fitness", 800, 155);
		g2.setColor(AVERAGE_FITNESS);
		g2.fillRect(780, 170, 10, 10);
		g2.drawString("Average Fitness", 800, 180);
		g2.setColor(WORST_FITNESS);
		g2.fillRect(780, 195, 10, 10);
		g2.drawString("Worst fitness", 800, 205);
		drawFitnessGraph(g2, 50, 25, GRAPH_WIDTH, GRAPH_HEIGHT, lengthOfChromosome);
		
		g2.setColor(Color.BLACK);
		for (int i = 0; i<6; i++) {
			g2.drawLine(45, 25+i*GRAPH_HEIGHT/5, 55, 25+i*GRAPH_HEIGHT/5);
			String label = Double.toString(10-2*i);
			g2.drawString(label, 25, 25+i*GRAPH_HEIGHT/5 +5);
			
		}
		
		for (int i = 0; i<9; i++) {
			g2.drawLine(50+i*GRAPH_WIDTH/8, 220, 50+i*GRAPH_WIDTH/8, 230);
		}
		
		g2.drawString("Fitness Over Generations", 410, 20);
		g2.drawString("Generations", 450, 250);
		
		g2.rotate(-Math.toRadians(90));
		g2.drawString("Fitness", -140, 15);
		g2.rotate(Math.toRadians(90));
		
		}
		
	}

	
