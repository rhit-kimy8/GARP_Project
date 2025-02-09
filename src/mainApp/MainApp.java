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
	ChromosomeEditor chromosomeEditor = new ChromosomeEditor();
	Population population = new Population();

	private void runApp() {
//		chromosomeEditor.runChromosomeEditor();
		population.testGeneration(10);
		population.testMaxOnes(100);
	} // runApp

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		System.out.println("GARP - MileStone 2");
		System.out.println();
		MainApp mainApp = new MainApp();
		mainApp.runApp();
}
}