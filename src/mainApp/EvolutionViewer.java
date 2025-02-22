package mainApp;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
 * @author Helen Xu, Haocheng Tang
 * <br>Purpose: create a window for the evolution viewer. it draws a graph, tracks time with 
 *      a timer, and has function buttons for control
 * <br>Restrictions: ????????
 */
public class EvolutionViewer extends JComponent {
	private static final int FRAME_WIDTH = 1070;
	private static final int FRAME_HEIGHT = 380;
	
	private static final int GRAPH_WIDTH = 850;
	private static final int GRAPH_HEIGHT = 200;
	private static final Color BEST_FITNESS = new Color(0, 255, 0);
	private static final Color AVERAGE_FITNESS = new Color(0, 0, 255);
	private static final Color WORST_FITNESS = new Color(255, 0, 0);
	private static final Color HAMMING_DISTANCE = new Color(255,255,0);
	
	private static final int DELAY = 50;
	static boolean isRunning = false;
	private boolean isDone = true;
	private double fitness;
	
	public static String saveString = "";
	private int lengthOfChromosome;
	private FittestIndividualViewer FIV;
	private PopulationViewer PV;
	
	
	private Timer timer;
	private ArrayList<Integer> bestFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> averageFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> worstFitnessLog = new ArrayList<Integer>();
	private ArrayList<Integer> hammingDistanceLog = new ArrayList<Integer>();
	Population pop = new Population();
	

	EvolutionViewer() {
	}
	
	public void addEntry(int best, int average, int worst, int diff) {
		this.bestFitnessLog.add(best);
		this.averageFitnessLog.add(average);
		this.worstFitnessLog.add(worst);
		this.hammingDistanceLog.add(diff);
		repaint();
	}
	
	public void reset() {
		this.bestFitnessLog.clear();
		this.averageFitnessLog.clear();
		this.worstFitnessLog.clear();
		this.hammingDistanceLog.clear();
	}
	
	public void spacePanel(JPanel panel) {
		JLabel space = new JLabel("    ");
		panel.add(space);
	}
	
	public void runEvolutionViewer() {
		JFrame frame = new JFrame("Evolution Viewer");
		frame.setSize( new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
		
	    JPanel controls = new JPanel(new GridLayout(2,1));
	    JPanel control1 = new JPanel();
	    JPanel control2 = new JPanel();
		JButton start = new JButton("Start"); 
		
		JLabel populationSize = new JLabel("Population Size");
		JTextField populationSizeInput = new JTextField();
		populationSizeInput.setPreferredSize(new Dimension(40,20));
		JLabel mutationRate = new JLabel("Mutation Rate (N/pop)");
		JTextField mutationRateInput = new JTextField();
		mutationRateInput.setPreferredSize(new Dimension(40,20));
		JLabel chromosomeLength = new JLabel("Chromosome Length");
		JTextField chromosomeLengthInput = new JTextField();
		chromosomeLengthInput.setPreferredSize(new Dimension(40,20));
		JLabel speicalPop = new JLabel("Special Population?");
		JCheckBox specialCheck = new JCheckBox();
		JLabel crossover = new JLabel("Crossover?");
		JCheckBox crossoverCheck = new JCheckBox();
		JLabel elitism = new JLabel("Elitism %");
		JTextField elitismInput = new JTextField();
		elitismInput.setPreferredSize(new Dimension(40,20));
		JLabel fitnessScale = new JLabel("Fitness");
		JTextField fitnessInput = new JTextField();
		fitnessInput.setPreferredSize(new Dimension(40,20));
		JLabel generation = new JLabel("Generation Size");
		JTextField generationInput = new JTextField();
		generationInput.setPreferredSize(new Dimension(40,20));
		JLabel selection = new JLabel("Selection");
		String[] selectionList = {"Truncation Selection", "Wheel Selection", "Ranked Selection"};
		JComboBox selectionInput = new JComboBox(selectionList);
		selectionInput.setPreferredSize(new Dimension(150,20));
		JLabel fitnessFunction = new JLabel("Fitness Function");
		String[] functionList = {"Max Ones Fitness" , "Match Target Fitness", "Max Consecutive Ones Fitness", "Binary Number Fitness"};
		JComboBox functionInput = new JComboBox(functionList);
		functionInput.setPreferredSize(new Dimension(140,20));
		

		spacePanel(control1);
		control1.add(populationSize);
		control1.add(populationSizeInput);
		spacePanel(control1);
		control1.add(chromosomeLength);
		control1.add(chromosomeLengthInput);
		spacePanel(control1);
		control1.add(speicalPop);
		control1.add(specialCheck);
		spacePanel(control1);
		control1.add(mutationRate);
		control1.add(mutationRateInput);
		spacePanel(control1);
		control1.add(generation);
		control1.add(generationInput);
		spacePanel(control2);
		control2.add(selection);
		control2.add(selectionInput);
		spacePanel(control2);
		control2.add(fitnessFunction);
		control2.add(functionInput);
		spacePanel(control2);
		control2.add(crossover);
		control2.add(crossoverCheck);
		spacePanel(control2);
		control2.add(elitism);
		control2.add(elitismInput);
		spacePanel(control2);
		control2.add(fitnessScale);
		control2.add(fitnessInput);
		spacePanel(control2);
		control2.add(start);
		controls.add(control1);
		controls.add(control2);
		frame.add(controls, BorderLayout.SOUTH);	
		 
		this.timer = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.repaint();
				if (selectionInput.getSelectedItem().toString().equals("Truncation Selection")) {
				pop.truncationSelect(pop.population, crossoverCheck.isSelected(),(double)Integer.parseInt(mutationRateInput.getText())/pop.population.size(),(int)Integer.parseInt(elitismInput.getText())*pop.population.size()/100);
				}else if (selectionInput.getSelectedItem().toString().equals("Wheel Selection")) {
					pop.RouletteSelection(pop.population, crossoverCheck.isSelected(),(double)Integer.parseInt(mutationRateInput.getText())/pop.population.size(),(int)Integer.parseInt(elitismInput.getText())*pop.population.size()/100);
				}else {
					pop.RankSelection(pop.population, crossoverCheck.isSelected(),(double)Integer.parseInt(mutationRateInput.getText())/pop.population.size(),(int)Integer.parseInt(elitismInput.getText())*pop.population.size()/100);
				}
				FIV.upDate(pop.getBest(pop.population));
				PV.upDatePopulation(pop);
				addEntry(pop.getBest(pop.population).getFitness(), pop.getAverage(pop.population), pop.getWorst(pop.population), pop.calculateHammingDis());
				isDone = false;
				//if(((double)pop.getBest(pop.population).getFitness() >= fitness) || (pop.getGenerationCount() > Integer.parseInt(generationInput.getText())-1)) {
				if(((double)pop.getBest(pop.population).getFitness() >= pop.getBest(pop.population).getBitString().length()) || (pop.getGenerationCount() > Integer.parseInt(generationInput.getText())-1)) {
					isDone = true;
					isRunning = false;
					EvolutionViewer.this.timer.stop();
					System.out.println("Evolution Terminated");
					start.setText("Restart");
				}
				}				
			}
			 //stop automatically at a lot of generations
				 );
		 
		start.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (populationSizeInput.getText().isEmpty() || chromosomeLengthInput.getText().isEmpty() || mutationRateInput.getText().isEmpty() || elitismInput.getText().isEmpty() || generationInput.getText().isEmpty() || fitnessInput.getText().isEmpty()) {	
					System.err.println("One or more variables is not defined: population size, chromosome length, mutation rate, elitism rate, generation size");
					return;
				}
				if (!(populationSizeInput.getText() instanceof String) || !(chromosomeLengthInput.getText() instanceof String) || !(mutationRateInput.getText() instanceof String) || !(elitismInput.getText() instanceof String) || !(generationInput.getText() instanceof String) || !(fitnessInput.getText() instanceof String)) {	
					System.err.println("One or more variables has unvalid variable: population size, chromosome length, mutation rate, elitism rate, generation size");
					return;
				}
				if (Integer.parseInt(populationSizeInput.getText()) <= 0  || Integer.parseInt(chromosomeLengthInput.getText()) <= 0 || Integer.parseInt(mutationRateInput.getText()) < 0 || Integer.parseInt(elitismInput.getText()) < 0 || Integer.parseInt(fitnessInput.getText()) < 0) {	
					System.err.println("One or more variables has unvalid variable: population size, chromosome length, mutation rate, elitism rate");
					return;
				}
				fitness = Integer.parseInt(fitnessInput.getText());
				lengthOfChromosome = Integer.parseInt(chromosomeLengthInput.getText());
				String function = functionInput.getSelectedItem().toString();
				if(isDone && pop.getGenerationCount() == 0 && !specialCheck.isSelected()) {
					if (function.equals("Max Ones Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
					}else if (function.equals("Match Target Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
					}else if (function.equals("Max Consecutive Ones Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
					}
					else if (function.equals("Binary Number Fitness")) {
						pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),3);
					}
					FIV = new FittestIndividualViewer(pop.getBest(pop.population));
					PV = new PopulationViewer(pop);
				}else if(isDone && pop.getGenerationCount() == 0 && specialCheck.isSelected()){
					if (function.equals("Max Ones Fitness")) {
						pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
					}else if (function.equals("Match Target Fitness")) {
						pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
					}else if (function.equals("Max Consecutive Ones Fitness")) {
						pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
					}
					else if (function.equals("Binary Number Fitness")) {
						pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),3);
					}
					FIV = new FittestIndividualViewer(pop.getBest(pop.population));
					PV = new PopulationViewer(pop);
				}
				
				if (!isRunning) {
					EvolutionViewer.this.timer.start();
					start.setText("Pause");
					isRunning = true;
					EvolutionViewer.this.repaint();
					if((isDone && pop.getGenerationCount()!=0) && !specialCheck.isSelected()) {
						FIV.end();
						PV.end();
						reset();
						pop.setGenerationCount();
						pop.removePopulation();
						String function1 = functionInput.getSelectedItem().toString();
						if (function1.equals("Max Ones Fitness")) {
							pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
						}else if (function1.equals("Match Target Fitness")) {
							pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
						}else if (function1.equals("Max Consecutive Ones Fitness")) {
							pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
						}
						else if (function1.equals("Binary Number Fitness")) {
							pop.generateRandomPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),3);
						}
						FIV = new FittestIndividualViewer(pop.getBest(pop.population));
						PV = new PopulationViewer(pop);
					}else if((isDone && pop.getGenerationCount()!=0) && specialCheck.isSelected()){
						FIV.end();
						PV.end();
						reset();
						pop.setGenerationCount();
						pop.removePopulation();
						String function1 = functionInput.getSelectedItem().toString();
						if (function1.equals("Max Ones Fitness")) {
							pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),0);
						}else if (function1.equals("Match Target Fitness")) {
							pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),1);
						}else if (function1.equals("Max Consecutive Ones Fitness")) {
							pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),2);
						}
						else if (function1.equals("Binary Number Fitness")) {
							pop.generateSpecialPopulation(Integer.parseInt(populationSizeInput.getText()), Integer.parseInt(chromosomeLengthInput.getText()),3);
						}
						FIV = new FittestIndividualViewer(pop.getBest(pop.population));
						PV = new PopulationViewer(pop);
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
				PV.end();
				FIV.end();
				populationSizeInput.setText("");
				chromosomeLengthInput.setText("");
				mutationRateInput.setText("");
				crossoverCheck.setSelected(false);
				specialCheck.setSelected(false);
				elitismInput.setText("");
				fitnessInput.setText("");
				generationInput.setText("");
				//set start button back to beginning
				frame.repaint();
				
			}
			
		});
		
		
		Menu file = new Menu("File");
		menu.add(file);
		MenuItem openFile = new MenuItem("Open File");
		file.add(openFile);
		
		openFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				while(true) {
					try {
						JFileChooser jf = new JFileChooser();
						jf.setCurrentDirectory(new File("./src/mainApp/ExampleFiles"));
						int checkFile = jf.showOpenDialog(null);
						if (checkFile != JFileChooser.APPROVE_OPTION) {
							return;  
						}
						// whenever we load a file, we should clean the past datas, and load new datas
						if (checkFile == JFileChooser.APPROVE_OPTION) {
							String path = jf.getSelectedFile().getAbsolutePath();
//							loadString = readFile(path);
//							panel.removeAll();
//							viewerPanel.removeAll();
//							jButtons.clear();
//							chromosomes.clear();
//							viewerButtons.clear();
							break;
							}
					}catch (NoSuchElementException e1) {
						continue;
					}
				}
			}
		}
			);
		
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
				jf.setCurrentDirectory(new File("./src/mainApp/ExampleFiles"));
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
	    g2.setColor(BEST_FITNESS);
	    for (int i = 1; i < fitnessLogSize; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (bestFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (bestFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }
	    g2.setColor(AVERAGE_FITNESS);
	    for (int i = 1; i < fitnessLogSize; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (averageFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (averageFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }
	    g2.setColor(WORST_FITNESS);
	    for (int i = 1; i < fitnessLogSize; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (worstFitnessLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (worstFitnessLog.get(i) * scaleY);
	        g2.drawLine(x1, y1, x2, y2);
	    }
	    g2.setColor(HAMMING_DISTANCE);
	    for (int i = 1; i < fitnessLogSize; i++) {
	        int x1 = graphX + (int) ((i - 1) * scaleX);
	        int y1 = graphY + graphHeight - (int) (hammingDistanceLog.get(i - 1) * scaleY);
	        int x2 = graphX + (int) (i * scaleX);
	        int y2 = graphY + graphHeight - (int) (hammingDistanceLog.get(i) * scaleY);
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
		
		g2.drawString("LEGEND", 940, 120);
		g2.setColor(BEST_FITNESS);
		g2.fillRect(920, 135, 10, 10);
		g2.setColor(AVERAGE_FITNESS);
		g2.fillRect(920, 160, 10, 10);
		g2.setColor(WORST_FITNESS);
		g2.fillRect(920, 185, 10, 10);		
		g2.setColor(HAMMING_DISTANCE);
		g2.fillRect(920, 210, 10, 10);
		g2.setColor(Color.BLACK);
		g2.drawString("Best fitness", 940, 145);
		g2.drawString("Average Fitness", 940, 170);
		g2.drawString("Worst fitness", 940, 195);
		g2.drawString("Hamming Distance", 940, 220);
		drawFitnessGraph(g2, 50, 25, GRAPH_WIDTH, GRAPH_HEIGHT, lengthOfChromosome);
		
		g2.setColor(Color.BLACK);
		for (int i = 0; i<6; i++) {
			g2.drawLine(45, 25+i*GRAPH_HEIGHT/5, 55, 25+i*GRAPH_HEIGHT/5);
			String label = Integer.toString(lengthOfChromosome-lengthOfChromosome/6*i);
			if (i == 5) label = "0";
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

	
