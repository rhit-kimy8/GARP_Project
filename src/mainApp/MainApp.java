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
		ChromosomeEditor chromosomeEditor = new ChromosomeEditor();
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
}
}