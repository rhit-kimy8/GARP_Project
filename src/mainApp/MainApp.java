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