package mainApp;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Class: MainApp
 * @author W25R101
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp {
	static String str;
	// test

	private void runApp() {
		System.out.println("Write your cool arcade game here!");			
	} // runApp

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	//test commit
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
		JFrame frame = new JFrame();
		frame.setSize(600,600);
		Panel panel = new Panel();
		Panel bottomPanel = new Panel();
		panel.setSize(600,500);
		JButton chromosome = new JButton("Chromosome");
		JButton saveButton = new JButton("Save File");
		JButton loadButton = new JButton("Load File");
		
		chromosome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				str = chromosome.getText();
			}
			
		});
		
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				jf.setCurrentDirectory(new File("."));
				int filePath = jf.showSaveDialog(null);
				if (filePath == JFileChooser.APPROVE_OPTION) {
					String path = jf.getSelectedFile().getAbsolutePath();
					mainApp.writeFile(path);
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
					mainApp.readFile(path);
				}
			}
		});
		bottomPanel.add(chromosome);
		bottomPanel.add(saveButton);
		bottomPanel.add(loadButton);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} // main
	
	private void readFile(String filename) {
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
		while(scanner.hasNext()) {
			next = scanner.nextLine();
			System.out.println(next);
		}
		scanner.close();
	}
	
	private void writeFile(String path) {
		File file;
		file = new File(path);
		PrintWriter pw;
		try {
			pw = new PrintWriter(file);
			pw.println(str);
		} catch (FileNotFoundException e) {
			System.err.println("File named" + str + "could not found.");
			e.printStackTrace();
			return;
		}
		pw.close();
	}
}