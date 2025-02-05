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
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChromosomeEditor {
	
	
	private ArrayList<Chromosome> chromosomes = new ArrayList<>();
	private ArrayList<ChromosomeButton> jButtons = new ArrayList<>();
	private ArrayList<ChromosomeButton> viewerButtons = new ArrayList<>();
	private String loadString;
	private int chromosomeNumber;
	private String saveString="";
	private double mutationRate;
	
	ChromosomeEditor(){
		JFrame frame = new JFrame("ChromosomeEditor");
		frame.setSize(1200,600);
		JPanel bottomPanel = new JPanel(new GridLayout(2,1));
		bottomPanel.setSize(600,100);
		JPanel upperBottom = new JPanel();
		JPanel lowerBottom = new JPanel();
		JPanel mainPanel = new JPanel(new GridLayout(1,2));
		JPanel panel = new JPanel(new GridLayout(10,10));
		JPanel viewerPanel = new JPanel(new GridLayout(10,10));
		panel.setSize(600,500);
		viewerPanel.setSize(600,500);
		

		JButton saveButton = new JButton("Save File");
		saveButton.setPreferredSize(new Dimension(170,30));
		JButton loadButton = new JButton("Load File");
		loadButton.setPreferredSize(new Dimension(170,30));
		JButton evaluateFitness = new JButton("EvaluateFitness (Flip coin)");
		evaluateFitness.setPreferredSize(new Dimension(220,30));
		JButton mutate = new JButton("Mutate");
		mutate.setPreferredSize(new Dimension(170,30));
		JLabel mutateRate = new JLabel("           M Rate : __ / N");
		mutateRate.setPreferredSize(new Dimension(170,40));
		JTextField mutateRateInput = new JTextField();
		mutateRateInput.setPreferredSize(new Dimension(220,30));
		
		
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		
		
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				jf.setCurrentDirectory(new File("./src/mainApp/ExampleFiles"));
				int checkFile = jf.showOpenDialog(null);
				if (checkFile != JFileChooser.APPROVE_OPTION) {
		            return;  
		        }
				
				if (checkFile == JFileChooser.APPROVE_OPTION) {
					String path = jf.getSelectedFile().getAbsolutePath();
					loadString = readFile(path);
					panel.removeAll();
					viewerPanel.removeAll();
					jButtons.clear();
					chromosomes.clear();
					viewerButtons.clear();					
				}
				chromosomeNumber = loadString.length();
				panel.setLayout(new GridLayout((int)Math.ceil(Math.sqrt(chromosomeNumber))/1,(int)Math.ceil(Math.sqrt(chromosomeNumber))/1));
				viewerPanel.setLayout(new GridLayout((int)Math.ceil(Math.sqrt(chromosomeNumber))/1,(int)Math.ceil(Math.sqrt(chromosomeNumber))/1));
				for (int i = 0; i < chromosomeNumber; i++) {
					int bit = Integer.parseInt(loadString.substring(i, i+1));
					ChromosomeButton jButton = new ChromosomeButton(Integer.toString(i));
					ChromosomeButton viewerButton = new ChromosomeButton(Integer.toString(i));
					Chromosome eachChromosome = new Chromosome();
					jButton.setBackground(eachChromosome.setColorAndGet(bit));
					viewerButton.setBackground(eachChromosome.setColorAndGet(bit)); //jButton.getBackground()
					jButton.setForeground(Color.blue);
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
//					viewerPanel.add(saveButton);
					panel.revalidate();
					viewerPanel.revalidate();
					panel.repaint();
					viewerPanel.repaint();
				}
			}
		});
		
		mutate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method 
				mutationRate = Integer.parseInt(mutateRateInput.getText());
				for (int i = 0; i < chromosomeNumber; i++) {
					boolean check = randomProcess((double)mutationRate/chromosomeNumber);
					if (chromosomes.get(i).getStatusIndex()== 0 || chromosomes.get(i).getStatusIndex()== 1) {
						chromosomes.get(i).mutate(check);
					}
//					jButtons.get(i).clearColor();
					jButtons.get(i).setBackground(chromosomes.get(i).getColor());
					viewerButtons.get(i).setBackground(jButtons.get(i).getBackground());
			}
			}
			
		});
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

	private String readFile(String filename) {
		File file;
		Scanner scanner;
		
		scanner = new Scanner(filename);
		file = new File(filename);
		
		try {
			scanner = new Scanner(file);
		}catch(FileNotFoundException e) {
			System.err.println("File named" + filename + "was not found.");
			e.printStackTrace();
		}
		
		String next;
		next = scanner.nextLine();
		scanner.close();
		return next;
	}
	
	private boolean randomProcess(double possibility) {

		Random r = new Random();// eg: 3% all the doubles > 0.03 will make true, so we shouldn't do any changes
		return (r.nextDouble() > 1-possibility);
	}
	
}

