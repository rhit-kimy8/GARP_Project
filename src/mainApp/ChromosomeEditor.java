package mainApp;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Class: ChromosomeEditor
 * @author Haocheng Tang,Eden
 * <br>Purpose: create a window for the simulation. It contains a few functional buttons and editor and viewer
 * <br>Restrictions: Maybe not so functional for future purposes
 */
public class ChromosomeEditor {
	
	
	private ArrayList<Chromosome> chromosomes = new ArrayList<>();
	//buttons for the edit part
	private ArrayList<ChromosomeButton> jButtons = new ArrayList<>();
	private ArrayList<ChromosomeButton> viewerButtons = new ArrayList<>();
	private String loadString;
	private int chromosomeNumber;
	private String saveString="";
	private double mutationRate;
	//right now not be used, probably will be used in the future
	private String changingBits;
	
	
	ChromosomeEditor(){
	}
	
	/**
	 * ensures: initializes the window
	 */
	public void runChromosomeEditor() {
		//frame for the whole window
		JFrame frame = new JFrame("ChromosomeEditor");
		frame.setSize(1000,600);
		//panel for the whole functional buttons
		JPanel bottomPanel = new JPanel(new GridLayout(2,1));
		bottomPanel.setSize(1000,100);
		//the panel for mutation
		JPanel upperBottom = new JPanel();
		//the panel for the save and load
		JPanel lowerBottom = new JPanel();
		// the panel for the two parts of chromosomes
		JPanel mainPanel = new JPanel(new GridLayout(1,2));
		// the panel for the left side editing chromosomes
		JPanel panel = new JPanel(new GridLayout(10,10));
		panel.setSize(500,500);
		//the panel for the right side viewing chromosomes
		JPanel viewerPanel = new JPanel(new GridLayout(10,10));
		viewerPanel.setSize(500,500);
		
		//buttons' settings
		JButton saveButton = new JButton("Save File");
		saveButton.setPreferredSize(new Dimension(bottomPanel.getWidth()/4,30));
		JButton loadButton = new JButton("Load File");
		loadButton.setPreferredSize(new Dimension(bottomPanel.getWidth()/4,30));
		JButton evaluateFitness = new JButton("EvaluateFitness (Flip coin)");
		evaluateFitness.setPreferredSize(new Dimension(bottomPanel.getWidth()/3,30));
		JButton mutate = new JButton("Mutate");
		mutate.setPreferredSize(new Dimension(bottomPanel.getWidth()/4,30));
		JLabel mutateRate = new JLabel("           M Rate : __ / N");
		mutateRate.setPreferredSize(new Dimension(bottomPanel.getWidth()/4,40));
		JTextField mutateRateInput = new JTextField();
		mutateRateInput.setPreferredSize(new Dimension(bottomPanel.getWidth()/3,30));
		
		
		// save button 
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the current status of the chromosomes and then save it to the file
				for (Chromosome chromosome:chromosomes) {
				saveString += Integer.toString(chromosome.getStatusIndex()); 
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
		
		
		//load button
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
							loadString = readFile(path);
							panel.removeAll();
							viewerPanel.removeAll();
							jButtons.clear();
							chromosomes.clear();
							viewerButtons.clear();
							break;
							}
					}catch (FileNotFoundException | InvalidBitStringInformationException | NoSuchElementException e1) {
						continue;
					}
				}
				
				chromosomeNumber = loadString.length();
				//create the layout for the chromosomes according to the numbers. It depends on sqrt(n). eg: 45 will be 7*7, so it will be 7 chromosomes in each row, 6 rows in total and the last line contains 3 chromosomes.
				panel.setLayout(new GridLayout((int)Math.ceil(Math.sqrt(chromosomeNumber))/1,(int)Math.ceil(Math.sqrt(chromosomeNumber))/1));
				viewerPanel.setLayout(new GridLayout((int)Math.ceil(Math.sqrt(chromosomeNumber))/1,(int)Math.ceil(Math.sqrt(chromosomeNumber))/1));
				for (int i = 0; i < chromosomeNumber; i++) {
					// convert each String number to Integer
					int bit = Integer.parseInt(loadString.substring(i, i+1));
					ChromosomeButton jButton = new ChromosomeButton(Integer.toString(i));
					ChromosomeButton viewerButton = new ChromosomeButton(Integer.toString(i));
					Chromosome eachChromosome = new Chromosome();
					jButton.setBackground(eachChromosome.setColorAndGet(bit));
					viewerButton.setBackground(eachChromosome.setColorAndGet(bit)); 
					jButton.setForeground(Color.blue);
					viewerButton.setForeground(Color.blue);
					//whenever the edit chromosome is clicked, it will clean the painted square color, and change the color according to 0-1-0-1..Same for the viewer
					jButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub	
							jButton.clearColor();
							jButton.setBackground(eachChromosome.clickChangeColor());
							viewerButton.clearColor();
							viewerButton.setBackground(jButton.getBackground());
						}
					});
					jButtons.add(jButton);
					viewerButtons.add(viewerButton);
					chromosomes.add(eachChromosome);
					panel.add(jButton);
					viewerPanel.add(viewerButton);
					panel.revalidate();
					viewerPanel.revalidate();
					panel.repaint();
					viewerPanel.repaint();
				}
			}
		});
		//mutate button
		mutate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// get the mutationrate and then convert it to double
				if (mutateRateInput.getText().isEmpty() || mutateRateInput.getText().isBlank()) {
					System.err.println("The mutation rate is undetermined");
					return;
				}
				if (panel.getComponentCount()==0) {
					System.err.println("The chromosomes are undetermined");
					return;
				}
				mutationRate = Integer.parseInt(mutateRateInput.getText());
				for (int i = 0; i < chromosomeNumber; i++) {
					// check whether it is 0 or 1 and then mutate it.
					boolean check = randomProcess((double)mutationRate/chromosomeNumber);
					if (chromosomes.get(i).getStatusIndex()== 0 || chromosomes.get(i).getStatusIndex()== 1) {
						chromosomes.get(i).mutate(check);
					}
					if (chromosomes.get(i).getStatusIndex() == 2) {
						continue;
					}
					jButtons.get(i).setBackground(chromosomes.get(i).getColor(Integer.toString(chromosomes.get(i).getStatusIndex())));
					viewerButtons.get(i).setBackground(jButtons.get(i).getBackground());
			}
			}
			
		});
		
		
		
		//flipcoin button
		evaluateFitness.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for (int i = 0; i < chromosomeNumber; i++) {
					if (chromosomes.get(i).getStatusIndex() == 2) {
						if(randomProcess(0.5)) {
							jButtons.get(i).paintSquareColor(chromosomes.get(i).getIndexColor(1),i);
							viewerButtons.get(i).setBackground(jButtons.get(i).getSquareColor());
							frame.revalidate();
							frame.repaint();
							
						}
						else {
							jButtons.get(i).paintSquareColor(chromosomes.get(i).getIndexColor(0),i);
							viewerButtons.get(i).setBackground(jButtons.get(i).getSquareColor());
							frame.revalidate();
							frame.repaint();
						}
					}
					panel.revalidate();
					panel.repaint();
					viewerPanel.revalidate();
					viewerPanel.repaint();
				}
			}	
		});
		
		upperBottom.add(mutate);
		upperBottom.add(mutateRate);
		upperBottom.add(mutateRateInput);
		lowerBottom.add(saveButton);
		lowerBottom.add(loadButton);
		lowerBottom.add(evaluateFitness);
		bottomPanel.add(upperBottom);
		bottomPanel.add(lowerBottom);

		mainPanel.add(panel);
		mainPanel.add(viewerPanel);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		frame.add(mainPanel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
}
	/**
	 * ensures: Return file for bitString of chromosome from directory of file
	 * @param directory of the file where user wants to get
	 * <br>requires: String
	 * @throws FileNotFoundException 
	 * @throws InvalidBitStringInformationException 
	 * @throws NoSuchElementException
	 */
	public String readFile(String filename) throws FileNotFoundException, InvalidBitStringInformationException, NoSuchElementException{
		try {
			File file;
			Scanner scanner;
			file = new File(filename);
			scanner = new Scanner(file);
			
			String next;
			next = scanner.nextLine();
			
			if (next.isEmpty()) throw new FileNotFoundException ();
			if (next.contains(" ")) throw new InvalidBitStringInformationException();
			
			for (int i = 0; i < next.length(); i++) {
				Character num = next.charAt(i);
				if (Character.getNumericValue(num) >2) {
					throw new InvalidBitStringInformationException();
				}
			}
			scanner.close();
			return next;
			
		}catch(FileNotFoundException e) {
			System.err.println("The given file " + filename + " cannot be read.");
			throw e;
		}catch(InvalidBitStringInformationException e) {
			System.err.println("The given file " + filename +" has invalid bitString information");
			throw e;
		}catch(NoSuchElementException e) {
			System.err.println("The given file " + filename + " is empty.");
			throw e;
		}

	}
	/**
	 * ensures: Simulate the random process according to the possibility
	 */
	public boolean randomProcess(double possibility) {

		Random r = new Random();// eg: 3% all the doubles > 0.03 will make true, so we shouldn't do any changes
		return (r.nextDouble() > 1-possibility);
	}
	
	
}