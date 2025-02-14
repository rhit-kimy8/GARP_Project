package mainApp;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
	ChromosomeEditor chromosomeEditor = new ChromosomeEditor();
	Population population = new Population();

	EvolutionViewer evolutionViewer = new EvolutionViewer();

	private void runApp() {
		// run chromosomeEditor
//		chromosomeEditor.runChromosomeEditor();
		
		// Those two are the test functions to check generating population
//		population.testPopulation(10,10);
//		population.testSpecialPopulation(100,10);
		
		// Those tree are the test functions to check Fitness of chromosomes
//		population.generateSpecialPopulation(100,10);
//		population.generateRandomPopulation(100, 10,0);
//		for (Chromosome c:population.getSpecialPopulation()) System.out.println(c.getBitString());
//		ArrayList<Chromosome> chromosomes = population.getSpecialPopulation();
		
		evolutionViewer.runEvolutionViewer();
	} // runApp

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		System.out.println("GARP - MileStone 2");
		MainApp mainApp = new MainApp();
		mainApp.runApp();
		
	}
}