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
		System.out.println("GARP - MileStone 1");			
	} // runApp

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
}
}

//TO DO FOR MILESTONE 1
//more example files to show that we can handle the required sizes
//finish JUnit testing
//**seed the rand, we have to do it for next milestone anyway so lets figure that out now
//two comments on the garp specification (? one done alr)
//**fitness calculator (bonus but helps for next milestone) maxOne fitness function
//finish milestone report
//let helen finish the uml thing when the coding is all done