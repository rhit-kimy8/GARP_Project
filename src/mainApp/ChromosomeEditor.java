package mainApp;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;

public class ChromosomeEditor {
	
	
	private ArrayList<Chromosome> chromosomes = new ArrayList<>();
	private ArrayList<JButton> jButtons = new ArrayList<>();
	private String loadString;
	private int chromosomeNumber;
	private String saveString="";
	
	ChromosomeEditor(){
		JFrame frame = new JFrame();
		frame.setSize(600,600);
		JPanel bottomPanel = new JPanel();
		JPanel panel = new JPanel(new GridLayout(10,10));
		panel.setSize(600,500);

		JButton saveButton = new JButton("Save File");
		JButton loadButton = new JButton("Load File");
		JButton evaluateFitness = new JButton("EvaluateFitness (Flip coin)");
		
		
		
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
						// TODO Auto-generated catch block
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
				}
				chromosomeNumber = loadString.length();
				panel.setLayout(new GridLayout((int)Math.ceil(Math.sqrt(chromosomeNumber))/1,(int)Math.ceil(Math.sqrt(chromosomeNumber))/1));
				for (int i = 0; i < chromosomeNumber; i++) {
					int bit = Integer.parseInt(loadString.substring(i, i+1));
					JButton jButton = new JButton(Integer.toString(i));
					Chromosome eachChromosome = new Chromosome();
					jButton.setBackground(eachChromosome.setColorAndGet(bit));
					jButton.setForeground(Color.blue);
					jButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub	
							jButton.setBackground(eachChromosome.clickChangeColor());
						}
						
					});
					jButtons.add(jButton);
					chromosomes.add(eachChromosome);
					panel.add(jButton);
					panel.revalidate();
					panel.repaint();
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
							jButtons.get(i).setBackground(chromosomes.get(i).setColorAndGet(1));
						}
						else {
							jButtons.get(i).setBackground(chromosomes.get(i).setColorAndGet(0));
						}
					}
					panel.revalidate();
					panel.repaint();
				}
			}
			
		});
		bottomPanel.add(saveButton);
		bottomPanel.add(loadButton);
		bottomPanel.add(evaluateFitness);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		frame.add(panel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
}
	
	
	private void writeFile(String path) {
		File file;
		file = new File(path);
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);
			pw.println(loadString);
		} catch (FileNotFoundException e) {
			System.err.println("File named" + path + "could not found.");
			e.printStackTrace();
			return;
		}
		pw.close();
	
		
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
		Random r = new Random();
		return r.nextDouble() > possibility;
	}
	
	private void flipCoinPaint (JButton jButton, Graphics g) {
	}
	

	
}

