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

	private void runApp() {
		// run chromosomeEditor
//		chromosomeEditor.runChromosomeEditor();
		
		// Those two are the test functions to check generating population
//		population.testPopulation(10,10);
//		population.testSpecialPopulation(100,10);
		
		// Those tree are the test functions to check Fitness of chromosomes
		population.generateSpecialPopulation(100,10);
		ArrayList<String> chromosomes = population.getSpecialPopulation();
//		System.out.println(population.maxOnesFitness(chromosomes));
//		System.out.println(population.matchTargetFitness(chromosomes, "0001000000"));
//		System.out.println(population.maxConsecutiveOnesFitness(chromosomes));
		
		// This test shows a single chromosome with 100 ones has fitness value of 100 and consecutive 1 of 100.
//		ArrayList<String> singleChromosome = new ArrayList<>();
//		singleChromosome.add("1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
//		System.out.println(population.maxOnesFitness(singleChromosome));
//		System.out.println(population.maxConsecutiveOnesFitness(singleChromosome));
		
		// This is test for truncationSelect
		population.truncationSelect(chromosomes);
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
