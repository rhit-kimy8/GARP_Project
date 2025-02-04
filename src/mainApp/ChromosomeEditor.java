package mainApp;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ChromosomeEditor {
	
	
	private ArrayList<Chromosome> chromosomes = new ArrayList<>();
	private ArrayList<JButton> jButtons = new ArrayList<>();
	private String loadString;
	private int chromosomeNumber;
	private ChromosomeEditor chromosomeEditor;
	
	ChromosomeEditor(){
		JFrame frame = new JFrame();
		frame.setSize(600,600);
		Panel panel = new Panel();
		Panel bottomPanel = new Panel();
		panel.setSize(600,500);

		JButton saveButton = new JButton("Save File");
		JButton loadButton = new JButton("Load File");
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				jf.setCurrentDirectory(new File("."));
				int filePath = jf.showSaveDialog(null);
				if (filePath == JFileChooser.APPROVE_OPTION) {
					String path = jf.getSelectedFile().getAbsolutePath();
					chromosomeEditor.writeFile(path);
				}
				
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				jf.setCurrentDirectory(new File("."));
				int checkFile = jf.showOpenDialog(null);
				if (checkFile == JFileChooser.APPROVE_OPTION) {
					String path = jf.getSelectedFile().getAbsolutePath();
					loadString = chromosomeEditor.readFile(path);
				}
			}
		});
		bottomPanel.add(saveButton);
		bottomPanel.add(loadButton);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		
		
		
		
		
		
		
		chromosomeNumber = loadString.length();
		for (int i = 0; i < chromosomeNumber; i++) {
			JButton jButton = new JButton(Integer.toString(i));
			Chromosome eachChromosome = new Chromosome();
			jButton.setBackground(eachChromosome.getColor());
			jButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub	
					jButton.setBackground(eachChromosome.clickChangeColor());
				}
				
			});
			jButtons.add(jButton);
			chromosomes.add(eachChromosome);
		}
}
	
	
	private void writeFile(String path) {
		File file;
		file = new File(path);
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);
			pw.println(loadString);
		} catch (FileNotFoundException e) {
			System.err.println("File named" + loadString + "could not found.");
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
}
